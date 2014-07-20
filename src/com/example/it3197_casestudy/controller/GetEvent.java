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
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.ui_logic.MainLinkPage;
import com.example.it3197_casestudy.ui_logic.ViewAllEventsActivity;
import com.example.it3197_casestudy.ui_logic.ViewEventsActivity;
import com.example.it3197_casestudy.ui_logic.ViewEventsDetailsFragment;
import com.example.it3197_casestudy.util.EventListAdapter;
import com.example.it3197_casestudy.util.Settings;
import com.example.it3197_casestudy.util.ViewEventsAdapter;

public class GetEvent extends AsyncTask<Object, Object, Object> implements Settings{
	private ViewEventsDetailsFragment activity;
	private Event event = new Event();
	private ProgressDialog dialog;
	
	public GetEvent(ViewEventsDetailsFragment activity, int eventID){
		this.activity = activity;
		event.setEventID(eventID);
	}
	
	@Override
	protected void onPreExecute() { 
		dialog = ProgressDialog.show(activity.getActivity(),
				"Retrieving event", "Please wait...", true);
	}

	@Override
	protected String doInBackground(Object... arg0) {
		return retrieveEvents();
	}

	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);
		try{
			activity.getTvEventID().setText("Event No: #" + event.getEventID());
			activity.getTvEventName().setText(event.getEventName());
			activity.getTvEventCategory().setText("Category: \n" + event.getEventCategory());
			activity.getTvEventDescription().setText("Description: \n" + event.getEventDescription());
			activity.getTvEventDateTimeFrom().setText("From: \n" + dateTimeFormatter.format(event.getEventDateTimeFrom()));
			activity.getTvEventDateTimeTo().setText("To: \n" + dateTimeFormatter.format(event.getEventDateTimeTo()));
			activity.getTvEventOccur().setText("Occurs: \n" + event.getOccurence());
			activity.getTvEventNoOfParticipants().setText("No of participants allowed: \n" + event.getNoOfParticipantsAllowed());
			activity.setTypeOfEvent(event.getEventType());
			activity.setLocation(event.getEventLocation());
			activity.setEvent(event);
		}
		catch(Exception e){
			errorOnExecuting();
			e.printStackTrace();
		}
		
		dialog.dismiss();
	}

	public String retrieveEvents() {
		String responseBody = "";
		// Instantiate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "retrieveEvent");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("eventID",String.valueOf(event.getEventID())));
		// Instantiate a POST HTTP method
		try {
			httppost.setEntity(new UrlEncodedFormEntity(postParameters));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseBody = httpclient.execute(httppost, responseHandler);
		} catch (Exception e) {
			errorOnExecuting();
			e.printStackTrace();
		}
		System.out.println(responseBody);
		return responseBody;
	}

	public void parseJSONResponse(String responseBody) {
		JSONObject json,json_object;
		try {
			json = new JSONObject(responseBody);
			System.out.println(responseBody);
			json_object = json.getJSONObject("eventInfo");
			event.setEventAdminNRIC(json_object.getString("eventAdminNRIC"));
			event.setEventName(json_object.getString("eventName"));
			event.setEventCategory(json_object.getString("eventCategory"));
			event.setEventDescription(json_object.getString("eventDescription"));
			event.setEventType(json_object.getString("eventType"));
			event.setEventDateTimeFrom(sqlDateTimeFormatter.parse(json_object.getString("eventDateTimeFrom")));
			event.setEventDateTimeTo(sqlDateTimeFormatter.parse(json_object.getString("eventDateTimeTo")));
			event.setOccurence(json_object.getString("occurence"));
			event.setEventLocation(json_object.getString("eventLocation"));
			event.setNoOfParticipantsAllowed(json_object.getInt("noOfParticipantsAllowed"));
			event.setActive(json_object.getInt("active"));
		} catch (Exception e) {
			errorOnExecuting();
			e.printStackTrace();
		}
	}
	
	private void errorOnExecuting(){
		this.cancel(true);
		new Handler(Looper.getMainLooper()).post(new Runnable() {
	        public void run() {
	            dialog.dismiss();
	            AlertDialog.Builder builder = new AlertDialog.Builder(activity.getActivity());
	            builder.setTitle("Error in retrieving event ");
	            builder.setMessage("Unable to retrieve event. Please try again.");
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