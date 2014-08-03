package com.example.it3197_casestudy.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import android.widget.ListView;
import android.widget.Toast;

import com.example.it3197_casestudy.model.RequestHobby;
import com.example.it3197_casestudy.ui_logic.UpdatePost;
import com.example.it3197_casestudy.util.MySharedPreferences;
import com.example.it3197_casestudy.util.RequestListAdapter;
import com.example.it3197_casestudy.util.Settings;
import com.example.it3197_casestudy.util.SwipeDismissListViewTouchListener;

public class GetRequestView extends AsyncTask<Object, Object, Object> implements Settings{
	private Activity activity;
	private ListView listview;
	private int HobbyID;
	private ArrayList<RequestHobby> requestList;
	private ProgressDialog dialog;
	private RequestListAdapter adapter;
	public GetRequestView(Activity activity, ListView listview, int HobbyID){
		this.activity = activity;
		this.listview = listview;
		this.HobbyID = HobbyID;
	}
	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		return getAllRequest();
	}

	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);
		dialog.dismiss();
		adapter = new RequestListAdapter(activity,requestList);
		listview.setAdapter(adapter);
		SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(listview, new SwipeDismissListViewTouchListener.DismissCallbacks(){

			@Override
			public boolean canDismiss(int position) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public void onDismiss(ListView listView,
					int[] reverseSortedPositions) {
				// TODO Auto-generated method stub
				
				for(int position : reverseSortedPositions) {
					final int tempt = position;
					AlertDialog Builder = new AlertDialog.Builder(activity).create();
					Builder.setTitle("Accept Request");
					Builder.setMessage("Are you sure? :(");
					Builder.setButton(AlertDialog.BUTTON_NEGATIVE, "Yes", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int id) {
					    	  
						AcceptRequest accept = new AcceptRequest(activity, requestList.get(tempt));
						accept.execute();
					    } 
					      
					}); 
					Builder.setButton(AlertDialog.BUTTON_NEUTRAL, "No", new DialogInterface.OnClickListener() {

					      public void onClick(DialogInterface dialog, int id) {
					    	  
					    } 
					     
					});
					Builder.show();
				}
			}
			
		
		});
		
		listview.setOnTouchListener(touchListener);
		listview.setOnScrollListener(touchListener.makeScrollListener());
	}

	@Override
	protected void onPreExecute() {
		requestList = new ArrayList<RequestHobby>();
		dialog = ProgressDialog.show(activity, "Retrieving Request","Please wait....", true);
	}
	
	public String getAllRequest(){
		String responseBody = "";
		// Instantiate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "GetRequestServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		// Instantiate a POST HTTP method
		
		postParameters.add(new BasicNameValuePair("hobbyID", Integer.toString(HobbyID)));
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
		RequestHobby requestHobby;
		try {
			json = new JSONObject(responseBody);
			data_array = json.getJSONArray("requestList");
			for (int i = 0; i < data_array.length(); i++) {
				JSONObject dataJob = new JSONObject(data_array.getString(i));
				requestHobby = new RequestHobby ();
				requestHobby.setGroupname(dataJob.getString("groupname"));
				requestHobby.setEventID(dataJob.getInt("eventID"));
				requestHobby.setHobbyID(dataJob.getInt("hobbyID"));
				Date date1 = new SimpleDateFormat("MMM dd, yyyy").parse(dataJob.getString("requestDateStart"));
				Date date2 = new SimpleDateFormat("MMM dd, yyyy").parse(dataJob.getString("requestDateEnd"));
				requestHobby.setRequestDateStart(date1);
				requestHobby.setRequestDateEnd(date2);
				requestHobby.setRequestStatus(dataJob.getString("requestStatus"));
				requestHobby.setRequestID(dataJob.getInt("requestID"));
				requestList.add(requestHobby);
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
				builder.setTitle("no request found. ");
				builder.setMessage("did you request for any?");
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
