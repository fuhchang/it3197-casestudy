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
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.model.EventParticipants;
import com.example.it3197_casestudy.ui_logic.CreateEventStep2Activity;
import com.example.it3197_casestudy.ui_logic.ViewAllEventsActivity;
import com.example.it3197_casestudy.ui_logic.ViewEventsDetailsFragment;
import com.example.it3197_casestudy.util.Settings;

public class JoinEvent extends AsyncTask<Object, Object, Object> implements Settings{
	private ViewEventsDetailsFragment activity;
	private EventParticipants eventParticipants;
	
	private ProgressDialog dialog;
	
	public JoinEvent(ViewEventsDetailsFragment activity, EventParticipants eventParticipants){
		this.activity = activity;
		this.eventParticipants = eventParticipants;
	}
	
	@Override
	protected void onPreExecute() {
		dialog = ProgressDialog.show(activity.getActivity(),
				"Joining event", "Please wait...", true);
	}

	@Override
	protected String doInBackground(Object... arg0) {
		return joinEvent();
	}

	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);
	}

	public String joinEvent() {
		String responseBody = "";
		// Instantiate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "createEventParticipant");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("eventID", String.valueOf(eventParticipants.getEventID())));
		postParameters.add(new BasicNameValuePair("userNRIC", eventParticipants.getUserNRIC()));
		postParameters.add(new BasicNameValuePair("eventDateTimeFrom", sqlDateTimeFormatter.format(eventParticipants.getDateTimeJoined())));
		postParameters.add(new BasicNameValuePair("web", "false"));
		// Instantiate a POST HTTP method
		try {
			httppost.setEntity(new UrlEncodedFormEntity(postParameters));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseBody = httpclient.execute(httppost, responseHandler);
		} catch (Exception e) {
			errorOnExecuting();
		}
		System.out.println(responseBody);
		return responseBody;
	}

	public void parseJSONResponse(String responseBody) {
		JSONObject json;
		try {
			json = new JSONObject(responseBody);
			boolean success = json.getBoolean("success");
			if(success){
				dialog.dismiss();
				Toast.makeText(activity.getActivity().getApplicationContext(),"Joined event successfully.", Toast.LENGTH_SHORT).show();
			}
			else{
				errorOnExecuting();
			}
		} catch (Exception e) {
			errorOnExecuting();
		}
	}
	
	private void errorOnExecuting(){
		this.cancel(true);
		new Handler(Looper.getMainLooper()).post(new Runnable() {
	        public void run() {
	            dialog.dismiss();
	            AlertDialog.Builder builder = new AlertDialog.Builder(activity.getActivity());
	            builder.setTitle("Error in joining event ");
	            builder.setMessage("Unable to join event. Please try again.");
	            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
			            cancel(true);
					}
				});
	            builder.create().show();
	        }
	    });
	}

}
