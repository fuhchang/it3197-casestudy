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

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.it3197_casestudy.model.Hobby;
import com.example.it3197_casestudy.model.User;
import com.example.it3197_casestudy.ui_logic.LoginActivity;
import com.example.it3197_casestudy.ui_logic.MainLinkPage;
import com.example.it3197_casestudy.util.MySharedPreferences;
import com.example.it3197_casestudy.util.Settings;

public class GetUser extends AsyncTask<Object, Object, Object>
implements Settings{
	private ArrayList<User> userList;
	private LoginActivity activity;
	private User user;
	private ProgressDialog dialog;
	
	public GetUser(LoginActivity activity, User user){
		this.activity = activity;
		this.user = user;
	}
	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return getUser();
	}
	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		parseJSONResponse((String)result);
		dialog.dismiss();
		for(int i=0; i<userList.size();i++){
			if(userList.get(i).getNric().equals(user.getNric()) && userList.get(i).getPassword().equals(user.getPassword())){
				MySharedPreferences preferences = new MySharedPreferences(activity);
				preferences.addPreferences("nric", user.getNric());
				preferences.addPreferences("password",user.getPassword());
				
				Intent intent = new Intent(activity, MainLinkPage.class);
				intent.putExtra("nric", user.getNric());
				activity.startActivity(intent);
			
			}
		}
	}
	@Override
	protected void onPreExecute() {
		userList = new ArrayList<User>();
		dialog = ProgressDialog.show(activity,
				"Checking User....", "Please wait...", true);
	}
	
	public String getUser(){
		String responseBody = "";
		// Instantiate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "GetUserServlet");
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
		try {
			System.out.println(responseBody);
			json = new JSONObject(responseBody);
			data_array = json.getJSONArray("userList");
			for (int i = 0; i < data_array.length(); i++) {
				JSONObject dataJob = new JSONObject(data_array.getString(i));
				User user = new User();
				user.setNric(dataJob.getString("nric"));
				user.setPassword(dataJob.getString("password"));
				userList.add(user);
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
				builder.setMessage("Please check our username and password");
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
