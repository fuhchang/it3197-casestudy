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
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.it3197_casestudy.model.Riddle;
import com.example.it3197_casestudy.ui_logic.ViewRiddleActivity;
import com.example.it3197_casestudy.util.Settings;

public class DeleteRiddle extends AsyncTask<Object, Object, Object> implements Settings	{
	ViewRiddleActivity activity;
	Riddle riddle;
	ProgressDialog dialog;
	
	public DeleteRiddle(ViewRiddleActivity activity, Riddle riddle) {
		this.activity = activity;
		this.riddle = riddle;
	}
	
	@Override
	protected Object doInBackground(Object... arg0) {
		return deleteRiddle();
	}

	@Override
	protected void onPreExecute() {
		dialog = ProgressDialog.show(activity, null, "Deleting...", true);
	}

	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);
		Toast.makeText(activity, "Delete successful", Toast.LENGTH_SHORT).show();
		dialog.dismiss();
		activity.finish();
	}
	
	private void parseJSONResponse(String responseBody){
		JSONObject json;
		try {
			json = new JSONObject(responseBody);
			boolean success = json.getBoolean("success");
			if(success){
				dialog.dismiss();
				activity.finish();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String deleteRiddle() {
		String responseBody = "";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(API_URL + "DeleteRiddleServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		postParameters.add(new BasicNameValuePair("riddleID", Integer.toString(riddle.getRiddleID())));
		
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
