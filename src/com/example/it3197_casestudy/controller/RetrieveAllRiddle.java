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
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.it3197_casestudy.model.Riddle;
import com.example.it3197_casestudy.model.User;
import com.example.it3197_casestudy.ui_logic.RiddleActivity;
import com.example.it3197_casestudy.util.RiddleListAdapter;
import com.example.it3197_casestudy.util.Settings;

public class RetrieveAllRiddle extends AsyncTask<Object, Object, Object> implements Settings{
	private RiddleActivity activity;
	private ArrayList<Riddle> riddleList;
	private ProgressDialog dialog;
	
	RiddleListAdapter riddleAdapter;
	ListView riddleListView;
	
	public RetrieveAllRiddle(RiddleActivity activity, ListView riddleListView) {
		this.activity = activity;
		this.riddleListView = riddleListView;
	}
	
	@Override
	protected Object doInBackground(Object... arg0) {
		return retrieveAllRiddle();
	}

	@Override
	protected void onPreExecute() {
		riddleList = new ArrayList<Riddle>();
		dialog = ProgressDialog.show(activity, null, "Retrieving...", true);
	}

	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);
		dialog.dismiss();
		riddleAdapter = new RiddleListAdapter(activity, riddleList);
		riddleListView.setAdapter(riddleAdapter);
		riddleListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				//Intent intent = new Intent(activity, ViewRiddleActivity.class);
				//activity.startActivity(intent);
			}
			
		});
	}
	
	public void parseJSONResponse(String responseBody) {
		JSONArray dataArray;
		JSONObject jsonObj;
		Riddle riddle;
		try {
			jsonObj = new JSONObject(responseBody);
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
	
	private void errorOnExecuting() {
		this.cancel(true);
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				dialog.dismiss();
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setTitle("Error").setMessage("Unable to retrieve riddle. Please try again.");
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				builder.create().show();
			}
		});
	}
}