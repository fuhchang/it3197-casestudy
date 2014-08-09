package com.example.it3197_casestudy.one_map_controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.androidmapsextensions.ClusterGroup;
import com.androidmapsextensions.ClusterOptions;
import com.androidmapsextensions.ClusterOptionsProvider;
import com.androidmapsextensions.ClusteringSettings;
import com.androidmapsextensions.GoogleMap;
import com.androidmapsextensions.GoogleMap.OnInfoWindowClickListener;
import com.androidmapsextensions.GoogleMap.OnMarkerClickListener;
import com.androidmapsextensions.Marker;
import com.androidmapsextensions.MarkerOptions;
import com.example.it3197_casestudy.geofencing.GeofenceRequester;
import com.example.it3197_casestudy.geofencing.SimpleGeofenceStore;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.model.EventLocationDetail;
import com.example.it3197_casestudy.model.MashUpData;
import com.example.it3197_casestudy.model.MyItem;
import com.example.it3197_casestudy.ui_logic.SuggestLocationActivity;
import com.example.it3197_casestudy.util.MySharedPreferences;
import com.example.it3197_casestudy.util.Settings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

public class GetMashUpData extends AsyncTask<Object, Object, Object> implements Settings{
	private ProgressDialog dialog;
	final private SuggestLocationActivity activity;
	private ArrayList<MashUpData> mashUpDataArrList;
	private String themeName;
    // Declare a variable for the cluster manager.
    
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
		
	    ArrayList<MyItem> myItemArrList = new ArrayList<MyItem>();
		for(int i=0;i<mashUpDataArrList.size();i++){
			try {
				SVY21Coordinate currentCoordinates = new SVY21Coordinate(mashUpDataArrList.get(i).getLat(),mashUpDataArrList.get(i).getLng());

				final double lat = currentCoordinates.asLatLon().getLatitude();
				final double lng = currentCoordinates.asLatLon().getLongitude();
				activity.getMap().addMarker(new MarkerOptions().title(mashUpDataArrList.get(i).getName()).snippet(mashUpDataArrList.get(i).getAddressStreetName()).position(new LatLng(lat,lng)));
				activity.getMap().setOnInfoWindowClickListener(new OnInfoWindowClickListener(){

					@Override
					public void onInfoWindowClick(Marker marker) {
						// TODO Auto-generated method stub
						String name = marker.getTitle();
						String address = marker.getSnippet();
						String hyperlink = "";
						double lat = marker.getPosition().latitude;
						double lng = marker.getPosition().longitude;
						Intent output = new Intent();
						output.putExtra("eventLocationName", name);
						output.putExtra("eventLocationAddress", address);
						output.putExtra("eventLocationHyperLink", hyperlink);
						output.putExtra("eventLocationLat", lat);
						output.putExtra("eventLocationLng", lng);
						activity.setResult(Activity.RESULT_OK,output);
						activity.finish();
					}
				});
				
		        MyItem offsetItem = new MyItem(mashUpDataArrList.get(i).getName(), mashUpDataArrList.get(i).getAddressStreetName(), mashUpDataArrList.get(i).getHyperlink(), lat, lng);
		        myItemArrList.add(offsetItem);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	    activity.setMyItemArrList(myItemArrList);
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
			//int size = data_array.getJSONObject(0).getInt("FeatCount");

			mashUpDataArrList = new ArrayList<MashUpData>();
			if(!data_array.getJSONObject(0).has("ErrorMessage")){
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
			else{
				errorOnExecuting();	
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
