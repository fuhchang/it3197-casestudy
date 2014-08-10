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
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.it3197_casestudy.model.Riddle;
import com.example.it3197_casestudy.model.RiddleAnswer;
import com.example.it3197_casestudy.model.RiddleUserAnswered;
import com.example.it3197_casestudy.model.User;
import com.example.it3197_casestudy.ui_logic.RiddleActivity;
import com.example.it3197_casestudy.ui_logic.ViewRiddleActivity;
import com.example.it3197_casestudy.util.RiddleListAdapter;
import com.example.it3197_casestudy.util.Settings;

public class RetrieveAllRiddleWithAnswers extends AsyncTask<Object, Object, Object> implements Settings {
	RiddleActivity activity;
	ArrayList<Riddle> riddleList;
	ArrayList<RiddleAnswer> riddleAnsList;
	ArrayList<RiddleAnswer> riddleAnswerList;
	ArrayList<RiddleUserAnswered> answeredList;
	
	Riddle riddle;
	RiddleUserAnswered userAnswer;
	User user;
	
	RiddleListAdapter riddleAdapter;
	ListView riddleListView;
	TextView tv_points;

	ProgressDialog dialog;
	String[] responses;
	
	public RetrieveAllRiddleWithAnswers(RiddleActivity activity, ListView riddleListView, TextView tv_points, User user) {
		this.activity = activity;
		this.riddleListView = riddleListView;
		this.tv_points = tv_points;
		this.user = user;
	}
	
	@Override
	protected Object doInBackground(Object... arg0) {
		return retrieveAllRiddleWithAnswers();
	}

	@Override
	protected void onPreExecute() {
		responses = new String[4];
		riddleList = new ArrayList<Riddle>();
		riddleAnsList = new ArrayList<RiddleAnswer>();
		riddleAnswerList = new ArrayList<RiddleAnswer>();
		answeredList = new ArrayList<RiddleUserAnswered>();
		
		dialog = ProgressDialog.show(activity, null, "Retrieving...", true);
	}

	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String[]) result);
		tv_points.setText("Points: " + user.getPoints());
		if(answeredList != null)
			riddleAdapter = new RiddleListAdapter(activity, riddleList, answeredList, user);
		else
			riddleAdapter = new RiddleListAdapter(activity, riddleList, user);
		riddleListView.setAdapter(riddleAdapter);
		riddleListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent(activity, ViewRiddleActivity.class);
				intent.putExtra("user", user);
				intent.putExtra("riddle", riddleList.get(position));
				for(int i = 0; i < riddleAnsList.size(); i++) {
					if(riddleList.get(position).getRiddleID() == riddleAnsList.get(i).getRiddle().getRiddleID()) {
						riddleAnswerList.add(riddleAnsList.get(i));
					}
				}
				for(int i = 0; i < answeredList.size(); i++) {
					if(riddleList.get(position).getRiddleID() == answeredList.get(i).getRiddle().getRiddleID()) {
						userAnswer = answeredList.get(i);
					}
				}
				intent.putParcelableArrayListExtra("riddleAnswerList", riddleAnswerList);
				intent.putExtra("userAnswer", userAnswer);
				activity.startActivity(intent);
			}
		});
		dialog.dismiss();
	}
	
	public String[] retrieveAllRiddleWithAnswers() {
		String riddleResponseBody = retrieveAllRiddle();
		String answersResponseBody = retrieveAllRiddleAnswer();
		String userAnsResponseBody = retrieveAllUserAnswer();
		String getUserResponseBody = getUser();
		responses[0] = riddleResponseBody;
		responses[1] = answersResponseBody;
		responses[2] = userAnsResponseBody;
		responses[3] = getUserResponseBody;
		
		return responses;
	}
	
	public void parseJSONResponse(String[] responseBody) {
		JSONArray dataArray;
		JSONObject jsonObj;
		Riddle riddle;
		RiddleAnswer riddleAns;
		RiddleUserAnswered userAns;
		
		try {
			jsonObj = new JSONObject(responseBody[0]);
			dataArray = jsonObj.getJSONArray("riddleList");
			
			for(int i = 0; i < dataArray.length(); i++) {
				JSONObject data = new JSONObject(dataArray.getString(i));
				riddle = new Riddle();
				riddle.setRiddleID(data.getInt("riddleID"));
				JSONObject userObj = data.getJSONObject("user");
				riddle.setUser(new User(userObj.getString("nric"), userObj.getString("name"), userObj.getString("type"), userObj.getString("password"), userObj.getString("contactNo"), userObj.getString("address"), userObj.getString("email"), userObj.getInt("active"), userObj.getInt("points")));
				riddle.setRiddleTitle(data.getString("riddleTitle"));
				riddle.setRiddleContent(data.getString("riddleContent"));
				riddle.setRiddleStatus(data.getString("riddleStatus"));
				riddle.setRiddlePoint(data.getInt("riddlePoint"));
				riddleList.add(riddle);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorOnExecuting();
		}
		
		try {
			jsonObj = new JSONObject(responseBody[1]);
			dataArray = jsonObj.getJSONArray("riddleAnsList");
			
			for(int i = 0; i < dataArray.length(); i++) {
				JSONObject data = new JSONObject(dataArray.getString(i));
				riddleAns = new RiddleAnswer();
				riddleAns.setRiddleAnswerID(data.getInt("riddleAnswerID"));
				JSONObject riddleObj = data.getJSONObject("riddle");
				riddleAns.setRiddle(new Riddle(riddleObj.getInt("riddleID")));
				riddleAns.setRiddleAnswer(data.getString("riddleAnswer"));
				riddleAns.setRiddleAnswerStatus(data.getString("riddleAnswerStatus"));
				riddleAnsList.add(riddleAns);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			errorOnExecuting();
		}
		
		try {
			jsonObj = new JSONObject(responseBody[2]);
			dataArray = jsonObj.getJSONArray("answeredList");
			
			for(int i = 0; i < dataArray.length(); i++) {
				JSONObject data = new JSONObject(dataArray.getString(i));
				userAns = new RiddleUserAnswered();
				userAns.setRiddleUserAnsweredID(data.getInt("riddleUserAnsweredID"));
				JSONObject riddleObj = data.getJSONObject("riddle");
				userAns.setRiddle(new Riddle(riddleObj.getInt("riddleID")));
				JSONObject riddleAnsObj = data.getJSONObject("riddleAnswer");
				userAns.setRiddleAnswer(new RiddleAnswer(riddleAnsObj.getInt("riddleAnswerID")));
				JSONObject userObj = data.getJSONObject("user");
				userAns.setUser(new User(userObj.getString("nric")));
				userAns.setAnsweredRate(data.getString("answeredRate"));
				answeredList.add(userAns);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			errorOnExecuting();
		}
		
		try {
			jsonObj = new JSONObject(responseBody[3]);
			dataArray = jsonObj.getJSONArray("user");
			JSONObject dataObj = new JSONObject(dataArray.getString(0));
			user.setNric(dataObj.getString("nric"));
			user.setName(dataObj.getString("name"));
			user.setType(dataObj.getString("type"));
			user.setPassword(dataObj.getString("password"));
			user.setContactNo(dataObj.getString("contactNo"));
			user.setAddress(dataObj.getString("address"));
			user.setEmail(dataObj.getString("email"));
			user.setActive(dataObj.getInt("active"));
			user.setPoints(dataObj.getInt("points"));		
		} catch (Exception e) {
			e.printStackTrace();
			errorOnExecuting();
		}
	}
	
	public String retrieveAllRiddle() {
		String responseBody = "";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(API_URL + "RetrieveAllRiddleServlet");
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
	
	public String retrieveAllRiddleAnswer() {
		String responseBody = "";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(API_URL + "RetrieveAllRiddleAnswerServlet");
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
	
	public String retrieveAllUserAnswer() {
		String responseBody = "";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(API_URL + "RetrieveAllUserAnswerServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		postParameters.add(new BasicNameValuePair("userNRIC", user.getNric()));
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(postParameters));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseBody = httpClient.execute(httpPost, responseHandler);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseBody;
	}
	
	public String getUser() {
		String responseBody = "";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(API_URL + "GetUserByNameServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		postParameters.add(new BasicNameValuePair("username", user.getName()));
		
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
	
	private void errorOnExecuting() {
		this.cancel(true);
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				dialog.dismiss();
			}
		});
	}
}
