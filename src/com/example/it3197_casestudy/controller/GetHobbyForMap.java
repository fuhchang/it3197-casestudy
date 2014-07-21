package com.example.it3197_casestudy.controller;

import java.util.ArrayList;


import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.it3197_casestudy.geofencing.SimpleGeofenceStore;
import com.example.it3197_casestudy.model.Hobby;
import com.example.it3197_casestudy.ui_logic.SearchHobbyByMap;
import com.example.it3197_casestudy.ui_logic.ViewHobbiesMain;
import com.example.it3197_casestudy.util.Settings;

public class GetHobbyForMap extends AsyncTask<Object, Object, Object> implements Settings  {
	private ProgressDialog dialog;
	private ViewHobbiesMain activity;
	private ArrayList<Hobby> hobbyList;
	private SimpleGeofenceStore mPrefs;
	public GetHobbyForMap(ViewHobbiesMain activity){
		this.activity = activity;
	}
	
	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return getAllHobby();
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		parseJSONResponse((String) result);
		
		Intent intent = new Intent(activity, SearchHobbyByMap.class);
		/*
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList("hobbyList", hobbyList);
		*/
		intent.putExtra("hobbyList", hobbyList);
		activity.startActivity(intent);
		dialog.dismiss();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		hobbyList = new ArrayList<Hobby>();
		dialog = ProgressDialog.show(activity,"Retrieving Hobby", "Please wait...", true);
	}
	
	public String getAllHobby() {
		String responseBody = "";
		// Instantiate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "GetHobbyLocServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		// Instantiate a POST HTTP method
		try {
			httppost.setEntity(new UrlEncodedFormEntity(postParameters));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseBody = httpclient.execute(httppost, responseHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(responseBody);
		return responseBody;

	}
	
	public void parseJSONResponse(String responseBody) {
		JSONArray data_array;
		JSONObject json;
		Hobby hobby;
		try {
			json = new JSONObject(responseBody);
			data_array = json.getJSONArray("hobbyList");
			for (int i = 0; i < data_array.length(); i++) {
				JSONObject dataJob = new JSONObject(data_array.getString(i));
				hobby = new Hobby();
				hobby.setGroupID(dataJob.getInt("grpID"));
				hobby.setGroupName(dataJob.getString("grpName"));
				hobby.setCategory(dataJob.getString("category"));
				hobby.setDescription(dataJob.getString("grpDesc"));
				hobby.setAdminNric(dataJob.getString("adminNric"));
				hobby.setLat(dataJob.getDouble("Lat"));
				hobby.setLng(dataJob.getDouble("Lng"));
				hobbyList.add(hobby);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorOnExecuting();
		}
		
	}

	private void errorOnExecuting() {
		this.cancel(true);
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			public void run() {
				dialog.dismiss();
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setTitle("Error in retrieving hobby ");
				builder.setMessage("Unable to retrieve hobby. Please try again.");
				builder.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
							}
						});
				builder.create().show();
			}
		});
	}	
	
	
}
