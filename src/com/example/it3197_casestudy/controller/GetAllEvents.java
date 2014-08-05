package com.example.it3197_casestudy.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
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
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.mysqlite.EventSQLController;
import com.example.it3197_casestudy.ui_logic.MainLinkPage;
import com.example.it3197_casestudy.ui_logic.ViewAllEventsActivity;
import com.example.it3197_casestudy.ui_logic.ViewEventsActivity;
import com.example.it3197_casestudy.util.EventListAdapter;
import com.example.it3197_casestudy.util.PullToRefreshListView;
import com.example.it3197_casestudy.util.Settings;

public class GetAllEvents extends AsyncTask<Object, Object, Object> implements Settings{
	private ArrayList<Event> eventArrList;
	private Event[] eventList;
	private ViewAllEventsActivity activity;
	private ListView lvViewAllEvents;

	private ProgressDialog dialog;
	
	public GetAllEvents(ViewAllEventsActivity activity,ListView lvViewAllEvents){
		this.activity = activity;
		this.lvViewAllEvents = lvViewAllEvents;
	}
	
	@Override
	protected void onPreExecute() {
		eventArrList = new ArrayList<Event>(); 
		dialog = ProgressDialog.show(activity,
				"Retrieving events", "Please wait...", true);
	}

	@Override
	protected String doInBackground(Object... arg0) {
		return retrieveEvents();
	}

	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);
		eventList = new Event[eventArrList.size()];

		EventSQLController controller = new EventSQLController(activity);
		int internalDBSize = controller.getAllEvent().size();
		if(internalDBSize != eventArrList.size()){
			for(int i=0;i<eventArrList.size();i++){
				eventList[i] = eventArrList.get(i);
				controller.insertEvent(eventArrList.get(i));
			}
		}
		else{
			for(int i=0;i<eventArrList.size();i++){
				eventList[i] = eventArrList.get(i);
			}
		}
		EventListAdapter adapter = new EventListAdapter(activity,eventList);
		lvViewAllEvents.setAdapter(adapter);
		lvViewAllEvents.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
		        Intent intent = new Intent(activity,ViewEventsActivity.class);
		        intent.putExtra("eventID", view.getId());
		        Event event = new Event();
		        for(int i=0;i<eventArrList.size();i++){
		        	if(eventArrList.get(i).getEventID() == view.getId()){
		        		event = eventArrList.get(i);
		        	}
		        }
				intent.putExtra("eventAdminNRIC", event.getEventAdminNRIC());
				intent.putExtra("eventName", event.getEventName());
				intent.putExtra("eventCategory", event.getEventCategory());
				intent.putExtra("eventDescription", event.getEventDescription());
				intent.putExtra("eventDateTimeFrom", sqlDateTimeFormatter.format(event.getEventDateTimeFrom()));
				intent.putExtra("eventDateTimeTo", sqlDateTimeFormatter.format(event.getEventDateTimeTo()));
				intent.putExtra("occurence", event.getOccurence());
				intent.putExtra("noOfParticipants", event.getNoOfParticipantsAllowed());
				intent.putExtra("active", event.getActive());
				intent.putExtra("eventFBPostID", event.getEventFBPostID());
		        activity.startActivity(intent);
			}
		});
		dialog.dismiss();
		activity.getSwipeLayout().setRefreshing(false);
	}

	public String retrieveEvents() {
		String responseBody = "";
		// Instantiate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "retrieveAllEvents");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("web","false"));
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
		JSONArray data_array;
		JSONObject json;
		Event event;
		try {
			json = new JSONObject(responseBody);
			System.out.println(responseBody);
			data_array = json.getJSONArray("eventInfo");
			for (int i = 0; i < data_array.length(); i++) {
				JSONObject dataJob = new JSONObject(data_array.getString(i));
				int active = dataJob.getInt("active");
				event = new Event();
				event.setEventID(dataJob.getInt("eventID"));
				event.setEventAdminNRIC(dataJob.getString("eventAdminNRIC"));
				event.setEventName(dataJob.getString("eventName"));
				event.setEventCategory(dataJob.getString("eventCategory"));
				event.setEventDescription(dataJob.getString("eventDescription"));
				event.setEventDateTimeFrom(sqlDateTimeFormatter.parse(dataJob.getString("eventDateTimeFrom")));
				event.setEventDateTimeTo(sqlDateTimeFormatter.parse(dataJob.getString("eventDateTimeTo")));
				event.setOccurence(dataJob.getString("occurence"));
				event.setNoOfParticipantsAllowed(dataJob.getInt("noOfParticipantsAllowed"));
				event.setActive(dataJob.getInt("active"));
				event.setEventFBPostID(dataJob.getString("eventFBPostID"));
				System.out.println(event.getEventDateTimeFrom());
				if(active == 1){
					eventArrList.add(event);
				}
			}
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
	            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	            builder.setTitle("Error in retrieving events ");
	            builder.setMessage("Unable to retrieve events. Please try again.");
	            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});
	            builder.create().show();
	        }
	    });
	}
}