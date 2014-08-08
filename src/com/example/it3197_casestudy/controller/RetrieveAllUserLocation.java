package com.example.it3197_casestudy.controller;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.example.it3197_casestudy.model.User;
import com.example.it3197_casestudy.model.UserLocation;
import com.example.it3197_casestudy.ui_logic.HeatMapActivity;
import com.example.it3197_casestudy.util.Settings;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

public class RetrieveAllUserLocation extends AsyncTask<Object, Object, Object> implements Settings {
	HeatMapActivity activity;
	GoogleMap map;
	ArrayList<UserLocation> locationList;
	ArrayList<LatLng> latLngList;
	LatLng location;
	
	HeatmapTileProvider mProvider;
	TileOverlay mOverlay;
	
	public RetrieveAllUserLocation(HeatMapActivity activity, GoogleMap map) {
		this.activity = activity;
		this.map = map;
	}
	
	@Override
	protected Object doInBackground(Object... params) {
		return retrieveAllUserLocation();
	}

	@Override
	protected void onPreExecute() {
		locationList = new ArrayList<UserLocation>();
		latLngList = new ArrayList<LatLng>();
	}
	
	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);
		
		for(int i = 0; i < locationList.size(); i++) {
			location = new LatLng(locationList.get(i).getLat(), locationList.get(i).getLng());
			latLngList.add(location);
		}
		
	    // Create a heat map tile provider, passing it the latlngs
	    mProvider = new HeatmapTileProvider.Builder().data(latLngList).build();
	    // Add a tile overlay to the map, using the heat map tile provider.
	    mOverlay = map.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
	}
	
	private void parseJSONResponse(String responseBody) {
		JSONArray dataArray;
		JSONObject jsonObj;
		UserLocation userLocation;
		
		try {
			jsonObj = new JSONObject(responseBody);
			dataArray = jsonObj.getJSONArray("locationList");
			
			for(int i = 0; i < dataArray.length(); i++) {
				JSONObject data = new JSONObject(dataArray.getString(i));
				userLocation = new UserLocation();
				userLocation.setLocationID(data.getInt("locationID"));
				JSONObject userObj = data.getJSONObject("user");
				userLocation.setUser(new User(userObj.getString("nric")));
				userLocation.setDateTime(sqlDateTimeFormatter.parse(data.getString("dateTime")));
				userLocation.setLat(data.getDouble("lat"));
				userLocation.setLng(data.getDouble("lng"));
				locationList.add(userLocation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String retrieveAllUserLocation() {
		String responseBody = "";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(API_URL + "RetrieveAllUserLocationServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(postParameters));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseBody = httpClient.execute(httpPost, responseHandler);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseBody;
	}
}
