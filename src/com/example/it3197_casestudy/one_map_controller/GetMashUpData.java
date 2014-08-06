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

import com.example.it3197_casestudy.geofencing.GeofenceRequester;
import com.example.it3197_casestudy.geofencing.SimpleGeofenceStore;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.model.EventLocationDetail;
import com.example.it3197_casestudy.model.MashUpData;
import com.example.it3197_casestudy.ui_logic.SuggestLocationActivity;
import com.example.it3197_casestudy.util.MySharedPreferences;
import com.example.it3197_casestudy.util.Settings;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GetMashUpData extends AsyncTask<Object, Object, Object> implements Settings{
	private ProgressDialog dialog;
	private SuggestLocationActivity activity;
	private ArrayList<MashUpData> mashUpDataArrList;
	private String themeName;
    
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
		for(int i=0;i<mashUpDataArrList.size();i++){
			try {
	        	/*CoordinateConvertor coordinateConvertor = new CoordinateConvertor(activity,mashUpDataArrList.get(i).getLat(),mashUpDataArrList.get(i).getLng());
	        	coordinateConvertor.execute();*/
				SVY21Coordinate currentCoordinates = new SVY21Coordinate(mashUpDataArrList.get(i).getLat(),mashUpDataArrList.get(i).getLng());
				double lat = currentCoordinates.asLatLon().getLatitude();
				double lng = currentCoordinates.asLatLon().getLongitude();
				
		        LatLng currentPositon = new LatLng(lat, lng);
				activity.getMap().addMarker(new MarkerOptions().title(mashUpDataArrList.get(i).getName()).snippet(mashUpDataArrList.get(i).getDescription()).position(currentPositon));
				
				activity.getMap().
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
