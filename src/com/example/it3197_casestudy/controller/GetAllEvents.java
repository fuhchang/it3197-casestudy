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
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.widget.ListView;

import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.util.EventListAdapter;
import com.example.it3197_casestudy.util.Settings;

public class GetAllEvents extends AsyncTask<Object, Object, Object> implements Settings{
	private ArrayList<Event> eventList;
	private String[] eventNameList;
	private String[] eventDateTimeList;
	private Activity activity;
	private ListView lvViewAllEvents;
	
	public GetAllEvents(Activity activity,ListView lvViewAllEvents){
		this.activity = activity;
		this.lvViewAllEvents = lvViewAllEvents;
	}
	
	@Override
	protected void onPreExecute() {
		eventList = new ArrayList<Event>();
	}

	@Override
	protected String doInBackground(Object... arg0) {
		return retrieveEvents();
	}

	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);
		eventNameList = new String[eventList.size()];
		eventDateTimeList = new String[eventList.size()];
		for(int i=0;i<eventList.size();i++){
			eventNameList[i] = eventList.get(i).getEventName();
			String dateTime = dateTimeFormatter.format(eventList.get(i).getEventDateTimeFrom());
			eventDateTimeList[i] = dateTime;
		}
		EventListAdapter adapter = new EventListAdapter(activity,eventNameList, eventDateTimeList);
		lvViewAllEvents.setAdapter(adapter);
	}

	public String retrieveEvents() {
		String responseBody = "";
		// Instantiate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "retrieveAllEvents");
		httppost.setHeader("Content-type", "application/json");
		httppost.setHeader("Accept", "application/json");
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
		JSONObject json, job;
		Event event;
		try {
			json = new JSONObject(responseBody);
			data_array = json.getJSONArray("eventInfo");
			for (int i = 0; i < data_array.length(); i++) {
				JSONObject dataJob = new JSONObject(data_array.getString(i));
				String dateFromInString = dataJob.getString("eventDateTimeFrom").toString();
				Date date = sqlDateTimeFormatter.parse(dateFromInString);
				int active = dataJob.getInt("active");
				event = new Event();
				event.setEventName(dataJob.getString("eventName"));
				event.setEventDateTimeFrom(date);
				if(active == 1){
					eventList.add(event);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}