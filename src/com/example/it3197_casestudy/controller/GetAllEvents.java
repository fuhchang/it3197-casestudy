package com.example.it3197_casestudy.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.geofencing.GeofenceRequester;
import com.example.it3197_casestudy.geofencing.SimpleGeofence;
import com.example.it3197_casestudy.geofencing.SimpleGeofenceStore;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.model.EventLocationDetail;
import com.example.it3197_casestudy.model.EventParticipants;
import com.example.it3197_casestudy.mysqlite.EventLocationDetailSQLController;
import com.example.it3197_casestudy.mysqlite.EventParticipantsSQLController;
import com.example.it3197_casestudy.mysqlite.EventSQLController;
import com.example.it3197_casestudy.ui_logic.MainLinkPage;
import com.example.it3197_casestudy.ui_logic.ViewAllEventsActivity;
import com.example.it3197_casestudy.ui_logic.ViewEventsActivity;
import com.example.it3197_casestudy.util.EventListAdapter;
import com.example.it3197_casestudy.util.MySharedPreferences;
import com.example.it3197_casestudy.util.PullToRefreshListView;
import com.example.it3197_casestudy.util.Settings;
import com.google.android.gms.location.Geofence;

public class GetAllEvents extends AsyncTask<Object, Object, Object> implements Settings{
	private ArrayList<Event> eventArrList;
	private ArrayList<EventLocationDetail> eventLocationArrList;
	private ArrayList<EventParticipants> eventParticipantsArrList;
	private Event[] eventList;
	private ViewAllEventsActivity activity;
	private ListView lvViewAllEvents;
	private SimpleGeofenceStore mPrefs;
	private GeofenceRequester mGeofenceRequester;
	List<Geofence> mCurrentGeofences;
	private String nric;
	private ProgressDialog dialog;
	private MySharedPreferences p;
	private boolean checkParticipantsInfo = false;
	
	public GetAllEvents(ViewAllEventsActivity activity,ListView lvViewAllEvents){
		this.activity = activity;
		this.lvViewAllEvents = lvViewAllEvents;
	}
	
	@Override
	protected void onPreExecute() {
		eventArrList = new ArrayList<Event>(); 
		eventLocationArrList = new ArrayList<EventLocationDetail>();
		eventParticipantsArrList = new ArrayList<EventParticipants>();
		mPrefs = new SimpleGeofenceStore(activity);
		mGeofenceRequester = new GeofenceRequester(activity);
		mCurrentGeofences = new ArrayList<Geofence>();
		p = new MySharedPreferences(activity);
		nric = p.getPreferences("nric", "");
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
		EventLocationDetailSQLController locationDetailsController = new EventLocationDetailSQLController(activity);
		EventParticipantsSQLController participantsController = new EventParticipantsSQLController(activity);
		
		controller.deleteAllEvents();
		for(int i=0;i<eventArrList.size();i++){
			eventList[i] = eventArrList.get(i);
			controller.insertEvent(eventArrList.get(i));
		}
		
		locationDetailsController.deleteAllEventLocationDetails();
		for(int i=0;i<eventLocationArrList.size();i++){	
			locationDetailsController.insertEventLocationDetail(eventLocationArrList.get(i));
		}
		
		if(checkParticipantsInfo){
			participantsController.deleteAllEventParticipants();
			for(int i=0;i<eventParticipantsArrList.size();i++){	
				participantsController.insertEventParticipant(eventParticipantsArrList.get(i));
			}
		}
		
		for(int i=0;i<eventLocationArrList.size();i++){
			for(int j=0;j<eventArrList.size();j++){
				if(eventLocationArrList.get(i).getEventID() == eventArrList.get(j).getEventID()){
					SimpleGeofence UiGeofence = new SimpleGeofence("Event No " + String.valueOf(eventLocationArrList.get(i).getEventLocationID()) + ": " + eventArrList.get(j).getEventName(), eventLocationArrList.get(i).getEventLocationLat(), eventLocationArrList.get(i).getEventLocationLng(), 1000,Geofence.NEVER_EXPIRE, Geofence.GEOFENCE_TRANSITION_ENTER);
					mPrefs.setGeofence(String.valueOf(eventLocationArrList.get(i).getEventLocationID()), UiGeofence);
					mCurrentGeofences.add(UiGeofence.toGeofence());
				}
			}
		}
		try {
	        // Try to add geofences
			mGeofenceRequester.addGeofences(mCurrentGeofences," events within 1km","There is an event ",0);
		} catch (UnsupportedOperationException e) {
	            // Notify user that previous request hasn't finished.
			Toast.makeText(activity, R.string.add_geofences_already_requested_error, Toast.LENGTH_LONG).show();
		}
		EventListAdapter adapter = new EventListAdapter(activity,eventList);
		lvViewAllEvents.setAdapter(adapter);
		lvViewAllEvents.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
		        Intent intent = new Intent(activity,ViewEventsActivity.class);
		        intent.putExtra("eventID", view.getId());
		        Event event = new Event();
		        EventLocationDetail eventLocationDetails = new EventLocationDetail();
		        boolean joined = false;
		        for(int i=0;i<eventArrList.size();i++){
		        	if(eventArrList.get(i).getEventID() == view.getId()){
		        		event = eventArrList.get(i);
				        for(int j=0;j<eventParticipantsArrList.size();j++){
				        	if(nric.equals(eventParticipantsArrList.get(j).getUserNRIC())){
				        		joined = true;
				        	}
				        }
		        	}
		        }
		        for(int i=0;i<eventLocationArrList.size();i++){
		        	if(event.getEventID() == eventLocationArrList.get(i).getEventID()){
		        		eventLocationDetails = eventLocationArrList.get(i);
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
				intent.putExtra("joined", joined);
				intent.putExtra("lat", eventLocationDetails.getEventLocationLat());
				intent.putExtra("lng", eventLocationDetails.getEventLocationLng());
		        activity.startActivity(intent);
		        activity.finish();
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
		postParameters.add(new BasicNameValuePair("userNRIC",nric));
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
		JSONArray data_array,data_array2,data_array3;
		JSONObject json;
		Event event;
		EventLocationDetail eventLocation;
		EventParticipants eventParticipants;
		try {
			json = new JSONObject(responseBody);
			System.out.println(responseBody);
			data_array = json.getJSONArray("eventInfo");
			data_array2 = json.getJSONArray("eventLocationInfo");

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
			for(int i=0;i<data_array2.length();i++){
				JSONObject dataJob = new JSONObject(data_array2.getString(i));
				eventLocation = new EventLocationDetail();
				eventLocation.setEventLocationID(dataJob.getInt("eventLocationID"));
				eventLocation.setEventID(dataJob.getInt("eventID"));
				eventLocation.setEventLocationName(dataJob.getString("eventLocationName"));
				eventLocation.setEventLocationAddress(dataJob.getString("eventLocationAddress"));
				eventLocation.setEventLocationHyperLink(dataJob.getString("eventLocationHyperLink"));
				eventLocation.setEventLocationLat(dataJob.getDouble("eventLocationLat"));
				eventLocation.setEventLocationLng(dataJob.getDouble("eventLocationLng"));
				eventLocationArrList.add(eventLocation);
			}

			if(json.has("eventParticipantsInfo")){
				checkParticipantsInfo = true;
				data_array3 = json.getJSONArray("eventParticipantsInfo");
				for(int i=0;i<data_array3.length();i++){
					JSONObject dataJob = new JSONObject(data_array3.getString(i));
					System.out.println("Job 3: " + dataJob);
					eventParticipants = new EventParticipants();
					eventParticipants.setEventID(dataJob.getInt("eventID"));
					eventParticipants.setUserNRIC(dataJob.getString("userNRIC"));
					eventParticipants.setDateTimeJoined(sqlDateTimeFormatter.parse(dataJob.getString("dateTimeJoined")));
					eventParticipants.setCheckIn(dataJob.getInt("checkIn"));
					eventParticipantsArrList.add(eventParticipants);
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
	    		activity.getSwipeLayout().setRefreshing(false);
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