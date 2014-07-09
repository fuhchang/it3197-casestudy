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
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.ui_logic.ViewAllEventsActivity;
import com.example.it3197_casestudy.ui_logic.ViewEventsDetailsFragment;
import com.example.it3197_casestudy.util.EventListAdapter;
import com.example.it3197_casestudy.util.Settings;

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
		activity.tvEventName.setText(event.getEventName());
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
			if(json.getBoolean("success")){
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
			}
			else{
				Toast.makeText(activity.getActivity(), "Unable to retrieve events.", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}