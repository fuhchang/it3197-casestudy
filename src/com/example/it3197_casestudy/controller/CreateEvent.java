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
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.ui_logic.CreateEventStep2Activity;
import com.example.it3197_casestudy.ui_logic.ViewAllEventsActivity;
import com.example.it3197_casestudy.util.EventListAdapter;
import com.example.it3197_casestudy.util.Settings;

public class CreateEvent extends AsyncTask<Object, Object, Object> implements Settings{
	private CreateEventStep2Activity activity;
	private Event event;
	public CreateEvent(CreateEventStep2Activity activity, Event event){
		this.activity = activity;
		this.event = event;
	}
	
	@Override
	protected void onPreExecute() {
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
		HttpPost httppost = new HttpPost(API_URL + "createEvent");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("eventName", event.getEventName()));
		postParameters.add(new BasicNameValuePair("eventCategory", event.getEventCategory()));
		postParameters.add(new BasicNameValuePair("eventDescription", event.getEventDescription()));
		postParameters.add(new BasicNameValuePair("eventType", event.getEventType()));
		postParameters.add(new BasicNameValuePair("eventDateTimeFrom", sqlDateTimeFormatter.format(event.getEventDateTimeFrom())));
		postParameters.add(new BasicNameValuePair("eventDateTimeTo", sqlDateTimeFormatter.format(event.getEventDateTimeTo())));
		postParameters.add(new BasicNameValuePair("occurence", event.getOccurence()));
		postParameters.add(new BasicNameValuePair("eventLocation", event.getEventLocation()));
		postParameters.add(new BasicNameValuePair("noOfParticipants", String.valueOf(event.getNoOfParticipantsAllowed())));
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
		JSONObject json;
		try {
			json = new JSONObject(responseBody);
			boolean success = json.getBoolean("success");
			if(success){
				Toast.makeText(activity.getApplicationContext(),"Event created successfully.", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(activity,ViewAllEventsActivity.class);
				activity.startActivity(intent);
				activity.finish();
			}
			else{
				Toast.makeText(activity, json.getString("message"), Toast.LENGTH_LONG);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
