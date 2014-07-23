package com.example.it3197_casestudy.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.it3197_casestudy.listview.HobbyListView;
import com.example.it3197_casestudy.listview.PostListView;
import com.example.it3197_casestudy.model.HobbyPost;
import com.example.it3197_casestudy.ui_logic.ViewHobbiesMain;
import com.example.it3197_casestudy.ui_logic.ViewSingleHobby;
import com.example.it3197_casestudy.util.Settings;

public class getPostController extends AsyncTask<Object, Object, Object>
		implements Settings {
	ViewSingleHobby activity;
	int id;
	ListView itemList;
	ArrayList<HobbyPost> getList;
	PostListView postListView;
	private ProgressDialog dialog;
	private int adminRight;
	private String userNric;
	public getPostController(ViewSingleHobby activity, int id, ListView itemList, int adminRight, String nric){
		this.activity = activity;
		this.id = id;
		this.itemList = itemList;
		this.adminRight = adminRight;
		this.userNric = nric;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		getList = new ArrayList<HobbyPost>();
		dialog = ProgressDialog.show(activity, "Retrieving Post","Retrieving Post Please wait....", true);
				
	}

	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return retrievePost(id);
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		parseJSONResponse((String)result);
		dialog.dismiss();
		postListView = new PostListView(activity,getList, adminRight, userNric);
		itemList.setAdapter(postListView);
	}
	
	public String retrievePost(int id){
		String responseBody= "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "GetPostListServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		postParameters.add(new BasicNameValuePair("id",Integer.toString(id)));
		
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
		JSONArray data_array;
		HobbyPost hobbyPost;
		System.out.println(responseBody);
		try {
			json = new JSONObject(responseBody);
			boolean success = json.getBoolean("success");
			if(success){
				data_array = json.getJSONArray("postList");
				for(int i=0; i< data_array.length(); i++){
					JSONObject dataJob = new JSONObject(data_array.getString(i));
					hobbyPost = new HobbyPost();
					hobbyPost.setGrpID(dataJob.getInt("grpID"));
					hobbyPost.setPostID(dataJob.getInt("postID"));
					Date date = new SimpleDateFormat("MMM dd, yyyy").parse(dataJob.getString("datetime"));
					hobbyPost.setDatetime(date);
					hobbyPost.setContent(dataJob.getString("content"));
					hobbyPost.setLat(dataJob.getDouble("Lat"));
					hobbyPost.setLng(dataJob.getDouble("Lng"));
					hobbyPost.setPosterNric(dataJob.getString("nric"));
					hobbyPost.setPostTitle(dataJob.getString("postTitle"));
					getList.add(hobbyPost);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			errorOnExecuting();
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	private void errorOnExecuting() {
		this.cancel(true);
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			public void run() {
				dialog.dismiss();
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setTitle("Error in retrieving post ");
				builder.setMessage("Unable to retrieve post. Please try again.");
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
