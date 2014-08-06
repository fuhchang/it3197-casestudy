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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.example.it3197_casestudy.model.User;
import com.example.it3197_casestudy.ui_logic.FeedbackArticleActivity;
import com.example.it3197_casestudy.ui_logic.MainLinkPage;
import com.example.it3197_casestudy.util.MySharedPreferences;
import com.example.it3197_casestudy.util.Settings;

public class CheckMemberHobby  extends AsyncTask<Object, Object, Object> implements Settings{
	private Activity activity;
	private ProgressDialog dialog;
	private String userName;
	private int grpID;
	private String membership;
	private int checkID;
	public CheckMemberHobby(Activity activity, String userName, int grpID){
		this.activity = activity;
		this.userName= userName;
		this.grpID = grpID;
	}
	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return CheckMemberShip();
	}

	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String)result);
	}

	@Override
	protected void onPreExecute() {
		dialog = ProgressDialog.show(activity,
				"Checking User....", "Please wait...", true);
	}
	
	public String CheckMemberShip(){
		String responseBody = "";
		// Instantiate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "CheckMemberServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		// Instantiate a POST HTTP method
		postParameters.add(new BasicNameValuePair("userName", userName));
		postParameters.add(new BasicNameValuePair("grpID", Integer.toString(grpID)));
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
			data_array = json.getJSONArray("memberCheck");
			//JSONObject dataObj = json.getJSONObject("user");
			JSONObject dataObj = new JSONObject(data_array.getString(0));
			setMembership(dataObj.getString("MemberRoles"));
			setCheckID(dataObj.getInt("groupID"));
			
			
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
				builder.setMessage("Unable to retrieve Hobby that you joined. Please try again.");
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
	/**
	 * @return the membership
	 */
	public String getMembership() {
		return membership;
	}
	/**
	 * @param membership the membership to set
	 */
	public void setMembership(String membership) {
		this.membership = membership;
	}
	/**
	 * @return the checkID
	 */
	public int getCheckID() {
		return checkID;
	}
	/**
	 * @param checkID the checkID to set
	 */
	public void setCheckID(int checkID) {
		this.checkID = checkID;
	}	
}
