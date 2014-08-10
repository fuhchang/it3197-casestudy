package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetAllEvents;
import com.example.it3197_casestudy.geofencing.GeofenceRequester;
import com.example.it3197_casestudy.geofencing.SimpleGeofence;
import com.example.it3197_casestudy.geofencing.SimpleGeofenceStore;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.model.EventLocationDetail;
import com.example.it3197_casestudy.model.EventParticipants;
import com.example.it3197_casestudy.mysqlite.EventLocationDetailSQLController;
import com.example.it3197_casestudy.mysqlite.EventParticipantsSQLController;
import com.example.it3197_casestudy.mysqlite.EventSQLController;
import com.example.it3197_casestudy.mysqlite.SavedEventSQLController;
import com.example.it3197_casestudy.util.CheckNetworkConnection;
import com.example.it3197_casestudy.util.EventExpandedListAdapter;
import com.example.it3197_casestudy.util.MySharedPreferences;
import com.example.it3197_casestudy.util.Settings;
import com.google.android.gms.location.Geofence;

public class ViewAllEventsActivity extends Activity implements Settings{
	ExpandableListView lvViewAllEvents;
	SwipeRefreshLayout swipeLayout;
	ProgressDialog dialog;
	MenuItem menuItemCreate;
	
	private SimpleGeofenceStore mPrefs;
	private GeofenceRequester mGeofenceRequester;
	List<Geofence> mCurrentGeofences;
	
	public SwipeRefreshLayout getSwipeLayout() {
		return swipeLayout;
	}

	public void setSwipeLayout(SwipeRefreshLayout swipeLayout) {
		this.swipeLayout = swipeLayout;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_all_events);

		mPrefs = new SimpleGeofenceStore(this);
		mGeofenceRequester = new GeofenceRequester(this);
		mCurrentGeofences = new ArrayList<Geofence>();
		
		lvViewAllEvents = (ExpandableListView) findViewById(R.id.lv_view_all_events);
		
		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright,android.R.color.holo_green_light,android.R.color.holo_orange_light,android.R.color.holo_red_light);
		swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						// TODO Auto-generated method stub
						swipeLayout.setRefreshing(true);
						(new Handler()).postDelayed(new Runnable() {
							@Override
							public void run() {
								getAllEvents();
							}
						},1000);
					}
				});

		lvViewAllEvents.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int i) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if (firstVisibleItem == 0)
					swipeLayout.setEnabled(true);
				else
					swipeLayout.setEnabled(false);
			}
		});

		getAllEvents();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_all_events_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menuItemCreate = menu.findItem(R.id.create_event);
		if(!CheckNetworkConnection.haveNetworkConnection(ViewAllEventsActivity.this)){
			menuItemCreate.setVisible(false);
		}
		else{
			menuItemCreate.setVisible(true);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.create_event:
			if(!CheckNetworkConnection.haveNetworkConnection(ViewAllEventsActivity.this)){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("You are in offline mode");
				builder.setMessage("Please check your internet connection and try again.");
				builder.setPositiveButton("OK", null);
				builder.create().show();
			}
			else{
				Intent intent = new Intent(ViewAllEventsActivity.this, CreateEventStep1Activity.class);
				startActivity(intent);
				finish();
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void getAllEvents(){
		if(CheckNetworkConnection.haveNetworkConnection(ViewAllEventsActivity.this)){
			GetAllEvents getAllEvents = new GetAllEvents(ViewAllEventsActivity.this, lvViewAllEvents);
			getAllEvents.execute();	
		}
		else{
			dialog = ProgressDialog.show(ViewAllEventsActivity.this,"Retrieving events", "Please wait...", true);
			getAllEventOffline();
		}
	}
	
	public void getAllEventOffline(){
		EventSQLController controller = new EventSQLController(ViewAllEventsActivity.this);
		EventLocationDetailSQLController locationDetailsController = new EventLocationDetailSQLController(ViewAllEventsActivity.this);
		EventParticipantsSQLController participantsController = new EventParticipantsSQLController(ViewAllEventsActivity.this);
		SavedEventSQLController savedEventController = new SavedEventSQLController(ViewAllEventsActivity.this);
		
		final ArrayList<Event> eventArrList = controller.getAllEvent();
		final ArrayList<EventLocationDetail> eventLocationArrList = locationDetailsController.getAllEventLocationDetails() ;
		final ArrayList<EventParticipants> eventParticipantsArrList = participantsController.getAllEventParticipants();
        ArrayList<Event> savedEventArrList = savedEventController.getAllSavedEvent();
        
		Event[] eventList = new Event[eventArrList.size()];
		for(int i=0;i<eventArrList.size();i++){
			eventList[i] = eventArrList.get(i);
		}
		
		Collections.sort(eventArrList, new Comparator<Event>() {
		
		    public int compare(Event ord1, Event ord2) {
		        Date d1 = null;
		        Date d2 = null;
		        try {
		            d1 = ord1.getEventDateTimeFrom();
		            d2 = ord2.getEventDateTimeFrom();
		        } catch (Exception e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }


		        return (d1.getTime() < d2.getTime() ? -1 : 1);     //descending
		    //  return (d1.getTime() > d2.getTime() ? 1 : -1);     //ascending
		    }
		}); 
		
		if((eventArrList.size() > 0) && (eventLocationArrList.size() > 0)){
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
				Toast.makeText(this, R.string.add_geofences_already_requested_error, Toast.LENGTH_LONG).show();
			}
			
	        for(int i=0;i<savedEventArrList.size();i++){
	        	boolean checkMatch = false;
	        	for(int j=0;j<eventArrList.size();j++){
	        		if(savedEventArrList.get(i).getEventID() == eventArrList.get(j).getEventID()){
	        			checkMatch = true;
	        		}
	        	}
	        	if(!checkMatch){
	        		savedEventController.deleteEvent(savedEventArrList.get(i).getEventID());
	        		savedEventArrList.remove(i);
	        	}
	        }
			
			populateList(savedEventArrList, eventArrList,eventLocationArrList,eventParticipantsArrList);
		}
		dialog.dismiss();
		swipeLayout.setRefreshing(false);
	}
	
	// @Override
	/*
	 * public void onBackPressed(){ Intent intent = new
	 * Intent(ViewAllEventsActivity.this, MainLinkPage.class);
	 * MySharedPreferences preferences = new MySharedPreferences(this); String
	 * userName = preferences.getPreferences("username", "");
	 * intent.putExtra("username", userName); startActivity(intent);
	 * this.finish(); }
	 */

	public void populateList(ArrayList<Event> savedEventArrList, final ArrayList<Event> eventArrList, final ArrayList<EventLocationDetail> eventLocationArrList, final ArrayList<EventParticipants> eventParticipantsArrList){
		MySharedPreferences p = new MySharedPreferences(ViewAllEventsActivity.this);
		final String nric = p.getPreferences("nric", "");
		
		ArrayList<String> listDataHeader = new ArrayList<String>();
		HashMap<String, List<Event>> listDataChild = new HashMap<String, List<Event>>();
 
        if(savedEventArrList.size() <= 0){
	        listDataHeader.add("Upcoming Events");
            listDataChild.put(listDataHeader.get(0), eventArrList);
        }
        else if(eventArrList.size() <= 0){
            listDataHeader.add("Saved Events");
            listDataChild.put(listDataHeader.get(0), savedEventArrList); // Header, Child data
        }
        else{
            listDataHeader.add("Saved Events");
            listDataHeader.add("Upcoming Events");
     
            listDataChild.put(listDataHeader.get(0), savedEventArrList); // Header, Child data
            listDataChild.put(listDataHeader.get(1), eventArrList);
        }
        
		EventExpandedListAdapter adapter = new EventExpandedListAdapter(ViewAllEventsActivity.this,ViewAllEventsActivity.this,listDataHeader, listDataChild, lvViewAllEvents);
		lvViewAllEvents.setAdapter(adapter);

		if(listDataHeader.size() == 2){
			lvViewAllEvents.expandGroup(1);
		}
		else{
			if(listDataHeader.get(0).equals("Upcoming Events")){
				lvViewAllEvents.expandGroup(0);	
			}
		}
		lvViewAllEvents.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
		        Intent intent = new Intent(ViewAllEventsActivity.this,ViewEventsActivity.class);
		        intent.putExtra("eventID", view.getId());
		        Event event = new Event();
		        EventLocationDetail eventLocationDetails = new EventLocationDetail();
		        boolean joined = false;
		        for(int i=0;i<eventArrList.size();i++){
		        	if(eventArrList.get(i).getEventID() == view.getId()){
		        		event = eventArrList.get(i);
				        for(int j=0;j<eventParticipantsArrList.size();j++){
				        	if(nric.equals(eventParticipantsArrList.get(j).getUserNRIC())){
				        		if(eventParticipantsArrList.get(j).getEventID() == view.getId()){
				        			joined = true;
				        		}
				        	}
				        	System.out.println("User NRIC: " + nric);
				        	System.out.println("Current NRIC: " + eventParticipantsArrList.get(j).getUserNRIC());
				        	System.out.println("Joined: " + joined);
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

				System.out.println("From: "+ event.getEventDateTimeFrom());
				System.out.println("To: "+ event.getEventDateTimeTo());
				intent.putExtra("occurence", event.getOccurence());
				intent.putExtra("noOfParticipants", event.getNoOfParticipantsAllowed());
				intent.putExtra("active", event.getActive());
				intent.putExtra("eventFBPostID", event.getEventFBPostID());
				intent.putExtra("joined", joined);
				intent.putExtra("lat", eventLocationDetails.getEventLocationLat());
				intent.putExtra("lng", eventLocationDetails.getEventLocationLng());
				ViewAllEventsActivity.this.startActivity(intent);
				ViewAllEventsActivity.this.finish();
				return false;
			}
		});
	}
}
