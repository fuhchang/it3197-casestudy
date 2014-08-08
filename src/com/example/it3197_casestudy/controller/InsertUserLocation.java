package com.example.it3197_casestudy.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.example.it3197_casestudy.model.User;
import com.example.it3197_casestudy.util.Settings;
import com.google.android.gms.maps.model.LatLng;

public class InsertUserLocation extends AsyncTask<Object, Object, Object> implements Settings	{
	User user;
	LatLng location;
	
	public InsertUserLocation(User user, LatLng location) {
		this.user = user;
		this.location = location;
	}
	
	@Override
	protected Object doInBackground(Object... arg0) {
		return insertUserLocation();
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);
	}
	
	private void parseJSONResponse(String responseBody) {
		JSONObject json;
		try {
			json = new JSONObject(responseBody);
			boolean success = json.getBoolean("success");
			if(success){
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String insertUserLocation() {
		String responseBody = "";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(API_URL + "InsertUserLocationServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		postParameters.add(new BasicNameValuePair("userNRIC", user.getNric()));
		postParameters.add(new BasicNameValuePair("lat", Double.toString(location.latitude)));
		postParameters.add(new BasicNameValuePair("lng", Double.toString(location.longitude)));
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(postParameters));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseBody = httpClient.execute(httpPost, responseHandler);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseBody;
	}	
}
