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

import com.example.it3197_casestudy.model.Riddle;
import com.example.it3197_casestudy.model.RiddleAnswer;
import com.example.it3197_casestudy.model.RiddleUserAnswered;
import com.example.it3197_casestudy.model.User;
import com.example.it3197_casestudy.ui_logic.ViewRiddleActivity;
import com.example.it3197_casestudy.util.Settings;

public class InsertChoice  extends AsyncTask<Object, Object, Object> implements Settings {
	ViewRiddleActivity activity;
	Riddle riddle;
	ArrayList<RiddleAnswer> riddleAnswerList;
	RiddleAnswer riddleAnswer;
	User user;
	
	ProgressDialog dialog;
	String[] responses;
	
	public InsertChoice(ViewRiddleActivity activity, Riddle riddle, ArrayList<RiddleAnswer> riddleAnswerList, RiddleAnswer riddleAnswer, User user){
		this.activity = activity;
		this.riddle = riddle;
		this.riddleAnswerList = riddleAnswerList;
		this.riddleAnswer = riddleAnswer;
		this.user = user;
	}
	
	@Override
	protected Object doInBackground(Object... arg0) {
		return insertChoiceUpdatePoints();
	}
	
	@Override
	protected void onPreExecute() {
		responses = new String[2];
		
		dialog = ProgressDialog.show(activity, null, "Checking answer...", true);
	}
	
	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String[]) result);
		Intent intent = new Intent(activity, ViewRiddleActivity.class);
		activity.finish();
		intent.putExtra("user", user);
		intent.putExtra("riddle", riddle);
		intent.putParcelableArrayListExtra("riddleAnswerList", riddleAnswerList);
		intent.putExtra("userAnswer", new RiddleUserAnswered(riddle, riddleAnswer, user, "NULL"));
		activity.startActivity(intent);
	}
	
	public String[] insertChoiceUpdatePoints() {
		String insertChoiceResponseBody = insertChoice();
		responses[0] = insertChoiceResponseBody;
		if(riddleAnswer.getRiddleAnswerStatus().equals("CORRECT")) {
			String userResponseBody = updateUserPoints();
			responses[1] = userResponseBody;
		}
		return responses;
	}
	
	private void parseJSONResponse(String[] responseBody){
		JSONObject json;
		try {
			json = new JSONObject(responseBody[0]);
			boolean success = json.getBoolean("success");
			if(success){
				dialog.dismiss();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(responseBody[1] != null) {
			try {
				json = new JSONObject(responseBody[1]);
				boolean success = json.getBoolean("success");
				if(success){
					dialog.dismiss();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String insertChoice() {
		String responseBody = "";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(API_URL + "InsertChoiceServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		postParameters.add(new BasicNameValuePair("riddleID", Integer.toString(riddle.getRiddleID())));
		postParameters.add(new BasicNameValuePair("riddleAnswerID", Integer.toString(riddleAnswer.getRiddleAnswerID())));	
		postParameters.add(new BasicNameValuePair("userNRIC", user.getNric()));
		
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
	
	public String updateUserPoints() {
		String responseBody = "";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(API_URL + "UpdateUserPointsServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		postParameters.add(new BasicNameValuePair("userNRIC", user.getNric()));
		postParameters.add(new BasicNameValuePair("userPoints", Integer.toString(user.getPoints()+riddle.getRiddlePoint())));
		
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
