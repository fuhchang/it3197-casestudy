package com.example.it3197_casestudy.controller;

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

import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.ui_logic.CreateEventStep2Activity;
import com.example.it3197_casestudy.ui_logic.MainLinkPage;
import com.example.it3197_casestudy.ui_logic.UpdateEventStep2Activity;
import com.example.it3197_casestudy.ui_logic.ViewAllEventsActivity;
import com.example.it3197_casestudy.util.EventListAdapter;
import com.example.it3197_casestudy.util.Settings;

public class UpdateEvent extends AsyncTask<Object, Object, Object> implements Settings{
	private UpdateEventStep2Activity activity;
	private Event event;
	
	private ProgressDialog dialog;
	
	public UpdateEvent(UpdateEventStep2Activity activity, Event event){
		this.activity = activity;
		this.event = event;
	}
	
	@Override
	protected void onPreExecute() {
		dialog = ProgressDialog.show(activity,
				"Updating event", "Please wait...", true);
	}

	@Override
	protected String doInBackground(Object... arg0) {
		return createEvent();
	}

	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);
	}

	public String createEvent() {
		String responseBody = "";
		// Instantiate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "editEvent");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("eventID", String.valueOf(event.getEventID())));
		postParameters.add(new BasicNameValuePair("eventName", event.getEventName()));
		postParameters.add(new BasicNameValuePair("eventCategory", event.getEventCategory()));
		postParameters.add(new BasicNameValuePair("eventDescription", event.getEventDescription()));
		postParameters.add(new BasicNameValuePair("eventType", event.getEventType()));
		postParameters.add(new BasicNameValuePair("eventDateTimeFrom", sqlDateTimeFormatter.format(event.getEventDateTimeFrom())));
		postParameters.add(new BasicNameValuePair("eventDateTimeTo", sqlDateTimeFormatter.format(event.getEventDateTimeTo())));
		postParameters.add(new BasicNameValuePair("occurence", event.getOccurence()));
		postParameters.add(new BasicNameValuePair("eventLocation", event.getEventLocation()));
		postParameters.add(new BasicNameValuePair("noOfParticipants", String.valueOf(event.getNoOfParticipantsAllowed())));
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
				Toast.makeText(activity.getApplicationContext(),"Event created successfully.", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(activity,ViewAllEventsActivity.class);
				activity.startActivity(intent);
				activity.finish();
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
	            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	            builder.setTitle("Error in updating event ");
	            builder.setMessage("Unable to update event. Please try again.");
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