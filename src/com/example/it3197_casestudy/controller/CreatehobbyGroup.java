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

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.it3197_casestudy.model.Hobby;
import com.example.it3197_casestudy.ui_logic.CreateGroupActivityStep4;
import com.example.it3197_casestudy.ui_logic.ViewHobbiesMain;
import com.example.it3197_casestudy.util.Settings;

public class CreatehobbyGroup extends AsyncTask<Object, Object, Object>
		implements Settings {
	private CreateGroupActivityStep4 activity;
	private Hobby hobby;
	private ProgressDialog dialog;
	private String imgID;
	public CreatehobbyGroup(CreateGroupActivityStep4 activity, Hobby hobby, ProgressDialog dialog, String imgID) {
		this.activity = activity;
		this.hobby = hobby;
		this.dialog = dialog;
		this.imgID =imgID;
	}

	@Override
	protected void onPreExecute() {
		
	}

	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return createHobby();
	}
	
	
	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		parseJSONResponse((String)result);
		Intent intent = new Intent(activity, ViewHobbiesMain.class);
		intent.putExtra("nric", hobby.getAdminNric());
		activity.startActivity(intent);
		activity.finish();
	}

	public String createHobby(){
		String responseBody= "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "CreateHobbyServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		postParameters.add(new BasicNameValuePair("nric", hobby.getAdminNric()));
		postParameters.add(new BasicNameValuePair("gtitle", hobby.getGroupName()));
		postParameters.add(new BasicNameValuePair("gType", hobby.getCategory()));
		postParameters.add(new BasicNameValuePair("gDesc", hobby.getDescription()));
		postParameters.add(new BasicNameValuePair("gLat", Double.toString(hobby.getLat())));
		postParameters.add(new BasicNameValuePair("gLng", Double.toString(hobby.getLng())));
		postParameters.add(new BasicNameValuePair("gImg", hobby.getGrpImg()));
		postParameters.add(new BasicNameValuePair("imgID", imgID));
		try {
			httppost.setEntity(new UrlEncodedFormEntity(postParameters));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseBody = httpclient.execute(httppost, responseHandler);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseBody;
		
	}
	
	private void parseJSONResponse(String responseBody){
		JSONObject json;
		System.out.println(responseBody);
		try {
			json = new JSONObject(responseBody);
			boolean success = json.getBoolean("success");
			if(success){
				dialog.dismiss();
				Toast.makeText(activity, "Hobby Group Created", Toast.LENGTH_LONG).show();
				activity.finish();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
