package com.example.it3197_casestudy.one_map_controller;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.example.it3197_casestudy.geofencing.GeofenceRequester;
import com.example.it3197_casestudy.geofencing.SimpleGeofenceStore;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.model.EventLocationDetail;
import com.example.it3197_casestudy.model.MashUpData;
import com.example.it3197_casestudy.model.MyItem;
import com.example.it3197_casestudy.ui_logic.SuggestLocationActivity;
import com.example.it3197_casestudy.util.MySharedPreferences;
import com.example.it3197_casestudy.util.Settings;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.ClusterManager.OnClusterClickListener;
import com.google.maps.android.clustering.ClusterManager.OnClusterItemClickListener;

public class GetMashUpData extends AsyncTask<Object, Object, Object> implements Settings{
	private ProgressDialog dialog;
	final private SuggestLocationActivity activity;
	private ArrayList<MashUpData> mashUpDataArrList;
	private String themeName;
    // Declare a variable for the cluster manager.
    private ClusterManager<MyItem> mClusterManager;
    
	public GetMashUpData(SuggestLocationActivity activity, String themeName){
		this.activity = activity;
		this.themeName = themeName;
	}
	
	@Override
	protected void onPreExecute() {
		dialog = ProgressDialog.show(activity,
				"Retrieving data", "Please wait...", true);
	}
	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return getMashUpData();
	}
	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);
		//((SuggestLocationActivity) activity).setMashUpDataArrList(mashUpDataArrList);

	    // Initialize the manager with the context and the map.
	    // (Activity extends context, so we can pass 'this' in the constructor.)
	    mClusterManager = new ClusterManager<MyItem>(activity, activity.getMap());
	    activity.getMap().setInfoWindowAdapter(mClusterManager.getMarkerManager());
	    
	    //mClusterManager.getClusterMarkerCollection().setOnInfoWindowAdapter(new MyCustomAdapterForClusters());
	    //mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(new MyCustomAdapterForItems());

	    
	    ArrayList<MyItem> myItemArrList = new ArrayList<MyItem>();
		for(int i=0;i<mashUpDataArrList.size();i++){
			try {
	        	/*CoordinateConvertor coordinateConvertor = new CoordinateConvertor(activity,mashUpDataArrList.get(i).getLat(),mashUpDataArrList.get(i).getLng());
	        	coordinateConvertor.execute();*/
				SVY21Coordinate currentCoordinates = new SVY21Coordinate(mashUpDataArrList.get(i).getLat(),mashUpDataArrList.get(i).getLng());
				double lat = currentCoordinates.asLatLon().getLatitude();
				double lng = currentCoordinates.asLatLon().getLongitude();
				
		        MyItem offsetItem = new MyItem(mashUpDataArrList.get(i).getName(), mashUpDataArrList.get(i).getAddressStreetName(), mashUpDataArrList.get(i).getHyperlink(), lat, lng);
		        myItemArrList.add(offsetItem);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	    // Point the map's listeners at the listeners implemented by the cluster
	    // manager.
	    activity.getMap().setOnCameraChangeListener(mClusterManager);
	    activity.getMap().setOnMarkerClickListener(mClusterManager);
	    mClusterManager.setOnClusterClickListener(new OnClusterClickListener<MyItem>() {
	        @Override
	        public boolean onClusterClick(Cluster<MyItem> cluster) {
	            //clickedCluster = cluster; // remember for use later in the Adapter
	    		activity.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(cluster.getPosition(), activity.getMap().getCameraPosition().zoom + 1));
	            return false;
	        }
	    });
	    mClusterManager.setOnClusterItemClickListener(new OnClusterItemClickListener<MyItem>(){
			@Override
			public boolean onClusterItemClick(MyItem item) {
				// TODO Auto-generated method stub
				activity.getTvEventLocationName().setText(item.getTitle());
				activity.getTvEventLocationAddress().setText(item.getAddress());
				activity.getTvEventLocationHyperlink().setText(item.getHyperLink());
				activity.getTvEventLocationLat().setText(String.valueOf(item.getPosition().latitude));
				activity.getTvEventLocationLng().setText(String.valueOf(item.getPosition().longitude));
				activity.getTableLayoutEventLocationInformation().setVisibility(View.VISIBLE);
				return false;
			}
	    });
	    
	    for(int i=0;i<myItemArrList.size();i++){
	    	mClusterManager.addItem(myItemArrList.get(i));
	    }
	    
	    activity.setThings(mClusterManager);
		dialog.dismiss();
	}
	
	public String getMashUpData(){
		String responseBody = "";
		// Instantiate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("http://www.onemap.sg/API/services.svc/mashupData?token=qo/s2TnSUmfLz+32CvLC4RMVkzEFYjxqyti1KhByvEacEdMWBpCuSSQ+IFRT84QjGPBCuz/cBom8PfSm3GjEsGc8PkdEEOEr&themeName=" + themeName);
		
		// Instantiate a GET HTTP method
		try {
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseBody = httpclient.execute(httpget, responseHandler);
		} catch (Exception e) {
			errorOnExecuting();
		}
		System.out.println(responseBody);
		return responseBody;
	}
	
	public void parseJSONResponse(String responseBody) {
		JSONObject json;
		JSONArray data_array;
		MashUpData mashUpData;
		try{
			json = new JSONObject(responseBody);
			data_array = json.getJSONArray("SrchResults");
			int size = data_array.getJSONObject(0).getInt("FeatCount");
			
			mashUpDataArrList = new ArrayList<MashUpData>();
			
			for(int i=1;i<data_array.length();i++){
				JSONObject dataJob = new JSONObject(data_array.getString(i));
				mashUpData = new MashUpData();
				if(dataJob.has("NAME")){
					mashUpData.setName(dataJob.getString("NAME"));
				}
				if(dataJob.has("ADDRESSSTREETNAME")){
					mashUpData.setAddressStreetName(dataJob.getString("ADDRESSSTREETNAME"));
				}
				if(dataJob.has("ADDRESSBLOCKHOUSENUMBER")){
					mashUpData.setAddressBlkHouseNumber(dataJob.getString("ADDRESSBLOCKHOUSENUMBER"));
				}
				if(dataJob.has("ADDRESSBUILDINGNAME")){
					mashUpData.setAddressBuildingName(dataJob.getString("ADDRESSBUILDINGNAME"));
				}
				if(dataJob.has("ADDRESSFLOORNUMBER")){
					mashUpData.setAddressFloorNumber(dataJob.getString("ADDRESSFLOORNUMBER"));
				}
				if(dataJob.has("ADDRESSUNITNUMBER")){
					mashUpData.setAddressUnitNumber(dataJob.getString("ADDRESSUNITNUMBER"));
				}
				if(dataJob.has("ADDRESSPOSTALCODE")){
					mashUpData.setAddressPostalCode(dataJob.getString("ADDRESSPOSTALCODE"));
				}
				if(dataJob.has("DESCRIPTION")){
					mashUpData.setDescription(dataJob.getString("DESCRIPTION"));
				}
				if(dataJob.has("HYPERLINK")){
					mashUpData.setHyperlink(dataJob.getString("HYPERLINK"));
				}
				if(dataJob.has("XY")){
					String XY = dataJob.getString("XY");
					double X = Double.parseDouble(XY.substring(0, XY.indexOf(",")));
					double Y = Double.parseDouble(XY.substring(XY.indexOf(",") + 1, XY.length()));
					mashUpData.setLat(Y);
					mashUpData.setLng(X);
				}
				if(dataJob.has("ICON_NAME")){
					mashUpData.setIconName(dataJob.getString("ICON_NAME"));
				}
				if(dataJob.has("PHOTOURL")){
					mashUpData.setPhotoUrl(dataJob.getString("PHOTOURL"));
				}
				mashUpDataArrList.add(mashUpData);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
			errorOnExecuting();
		}
		
	}
	
	private void errorOnExecuting(){
		this.cancel(true);
		new Handler(Looper.getMainLooper()).post(new Runnable() {
	        public void run() {
	            dialog.dismiss();
	            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	            builder.setTitle("Error in retrieving data ");
	            builder.setMessage("Unable to retrieve data. Please try again.");
	            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
			            cancel(true);
					}
				});
	            builder.create().show();
	        }
	    });
	}

}
