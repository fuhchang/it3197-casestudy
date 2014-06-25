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

import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.util.EventListAdapter;
import com.example.it3197_casestudy.util.Settings;

public class GetAllEvents extends AsyncTask<Object, Object, Object> implements Settings{
	private ArrayList<Event> eventList;
	private String[] eventNameList;
	private String[] eventDateTimeList;
	private ListFragment listFragment;
	
	public GetAllEvents(ListFragment listFragment){
		this.listFragment = listFragment;
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
			String dateTime = dateTimeFormatter.format(eventList.get(i).getEventDateTime());
			eventDateTimeList[i] = dateTime;
		}
		EventListAdapter adapter = new EventListAdapter(listFragment.getActivity(),eventNameList, eventDateTimeList);
		listFragment.setListAdapter(adapter);
	}

	public String retrieveEvents() {
		String responseBody = "";
		// Instantiate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "get_all_events.php");
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
		return responseBody;
	}

	public void parseJSONResponse(String responseBody) {
		JSONArray data_array;
		JSONObject json, job;
		Event event;
		try {
			json = new JSONObject(responseBody);
			data_array = json.getJSONArray("event");
			for (int i = 0; i < data_array.length(); i++) {
				JSONObject dataJob = new JSONObject(data_array.getString(i));
				String dateInString = dataJob.getString("eventDateTime").toString();
				Date date = dateTimeFormatter.parse(dateInString);
				
				event = new Event();
				event.setEventName(dataJob.getString("eventName"));
				event.setEventDateTime(date);
				eventList.add(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}