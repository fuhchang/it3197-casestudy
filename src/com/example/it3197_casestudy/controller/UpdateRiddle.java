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

import com.example.it3197_casestudy.model.Riddle;
import com.example.it3197_casestudy.model.RiddleAnswer;
import com.example.it3197_casestudy.ui_logic.RiddleActivity;
import com.example.it3197_casestudy.ui_logic.UpdateRiddleActivity;
import com.example.it3197_casestudy.util.Settings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class UpdateRiddle extends AsyncTask<Object, Object, Object> implements Settings	{
	UpdateRiddleActivity activity;
	Riddle riddle;
	RiddleAnswer[] riddleChoices;
	ArrayList<RiddleAnswer> riddleAnswerList;
	ProgressDialog dialog;
	
	public UpdateRiddle(UpdateRiddleActivity activity, Riddle riddle, RiddleAnswer[] riddleChoices){
		this.activity = activity;
		this.riddle = riddle;
		this.riddleChoices = riddleChoices;
	}

	@Override
	protected Object doInBackground(Object... arg0) {
		return updateRiddle();
	}
	
	@Override
	protected void onPreExecute() {
		riddleAnswerList = new ArrayList<RiddleAnswer>();
		
		dialog = ProgressDialog.show(activity, null, "Updating...", true);
	}
	
	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);
		Toast.makeText(activity, "Update successful", Toast.LENGTH_SHORT).show();
		dialog.dismiss();
		Intent intent = new Intent(activity, RiddleActivity.class);
		intent.putExtra("riddle", riddle);
		for(int i = 0; i < riddleChoices.length; i++) {
			riddleAnswerList.add(riddleChoices[i]);
		}
		intent.putParcelableArrayListExtra("riddleAnswerList", riddleAnswerList);
		activity.startActivity(intent);
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
	
	public String updateRiddle() {
		String responseBody = "";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(API_URL + "UpdateRiddleServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		postParameters.add(new BasicNameValuePair("riddleID", Integer.toString(riddle.getRiddleID())));
		postParameters.add(new BasicNameValuePair("userNRIC", riddle.getUser().getNric()));
		postParameters.add(new BasicNameValuePair("riddleTitle", riddle.getRiddleTitle()));
		postParameters.add(new BasicNameValuePair("riddleContent", riddle.getRiddleContent()));
		postParameters.add(new BasicNameValuePair("riddleStatus", riddle.getRiddleStatus()));
		postParameters.add(new BasicNameValuePair("riddlePoint", Integer.toString(riddle.getRiddlePoint())));
		
		for(int i = 0; i < riddleChoices.length; i++) {
			postParameters.add(new BasicNameValuePair("riddleAnswerID"+i, Integer.toString(riddleChoices[i].getRiddleAnswerID())));
			postParameters.add(new BasicNameValuePair("riddleAnswer"+i, riddleChoices[i].getRiddleAnswer()));
			postParameters.add(new BasicNameValuePair("riddleAnswerStatus"+i, riddleChoices[i].getRiddleAnswerStatus()));
		}
		
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
