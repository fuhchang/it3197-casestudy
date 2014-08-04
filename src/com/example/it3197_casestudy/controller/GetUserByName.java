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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.it3197_casestudy.model.User;
import com.example.it3197_casestudy.ui_logic.FeedbackArticleActivity;
import com.example.it3197_casestudy.ui_logic.LoginSelectionActivity;
import com.example.it3197_casestudy.ui_logic.MainLinkPage;
import com.example.it3197_casestudy.util.MySharedPreferences;
import com.example.it3197_casestudy.util.Settings;

public class GetUserByName extends AsyncTask<Object, Object, Object> implements Settings{
	private LoginSelectionActivity activity;
	private String username;
	private ProgressDialog dialog;
	private User checkUser, user;
	public GetUserByName(LoginSelectionActivity activity, String username){
		this.activity = activity;
		this.username = username;
	}
	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return getUser();
	}

	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);
	}

	@Override
	protected void onPreExecute() {
		user = new User();
		dialog = ProgressDialog.show(activity,
				"Checking User....", "Please wait...", true);
	}
	
	public String getUser(){
		String responseBody = "";
		// Instantiate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "GetUserByNameServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		postParameters.add(new BasicNameValuePair("username", username));

		
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
		try {
			System.out.println(responseBody);
			json = new JSONObject(responseBody);
			data_array = json.getJSONArray("user");
			//JSONObject dataObj = json.getJSONObject("user");
			JSONObject dataObj = new JSONObject(data_array.getString(0));
			checkUser = new User();
			checkUser.setNric(dataObj.getString("nric"));
			checkUser.setName(dataObj.getString("name"));
			checkUser.setType(dataObj.getString("type"));
			checkUser.setPassword(dataObj.getString("password"));
			checkUser.setContactNo(dataObj.getString("contactNo"));
			checkUser.setAddress(dataObj.getString("address"));
			checkUser.setEmail(dataObj.getString("email"));
			checkUser.setActive(dataObj.getInt("active"));
			checkUser.setPoints(dataObj.getInt("points"));
			System.out.println(checkUser.getNric());
			/*data_array = json.getJSONArray("userList");
			for (int i = 0; i < data_array.length(); i++) {
				JSONObject dataJob = new JSONObject(data_array.getString(i));
				User user = new User();
				user.setNric(dataJob.getString("nric"));
				user.setPassword(dataJob.getString("password"));
				user.setType(dataJob.getString("type"));
				user.setName(dataJob.getString("name"));
				userList.add(user);
			}*/
			if(checkUser.getName().equals(username)){
				MySharedPreferences preferences = new MySharedPreferences(activity);
				preferences.addPreferences("nric", checkUser.getNric());
				preferences.addPreferences("username", checkUser.getName());
				preferences.addPreferences("password",user.getPassword());
				preferences.addPreferences("type",checkUser.getType());
				preferences.addPreferences("contactNo",checkUser.getContactNo());
				preferences.addPreferences("address", checkUser.getAddress());
				preferences.addPreferences("email", checkUser.getEmail());
				preferences.addPreferences("active", checkUser.getActive());
				preferences.addPreferences("points", checkUser.getPoints());
				
				if(checkUser.getType().equals("User")){
					Intent intent = new Intent(activity, MainLinkPage.class);
					intent.putExtra("nric", checkUser.getNric());
					intent.putExtra("user", checkUser);
					System.out.println("Line 72: " + checkUser.getPoints());
					activity.startActivity(intent);
					activity.finish();
				}
				if(checkUser.getType().equals("Officer")){
					Intent art = new Intent(activity, FeedbackArticleActivity.class);
					activity.startActivity(art);
					activity.finish();
				}
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
				builder.setTitle("Error in retrieving user ");
				builder.setMessage("Please check your username and password");
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
