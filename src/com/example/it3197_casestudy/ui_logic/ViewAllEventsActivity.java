package com.example.it3197_casestudy.ui_logic;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetAllEvents;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.mysqlite.EventSQLController;
import com.example.it3197_casestudy.util.EventListAdapter;
import com.example.it3197_casestudy.util.MySharedPreferences;
import com.example.it3197_casestudy.util.PullToRefreshListView;
import com.example.it3197_casestudy.util.PullToRefreshListView.OnRefreshListener;
import com.example.it3197_casestudy.util.Settings;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ViewAllEventsActivity extends Activity implements Settings{
	ListView lvViewAllEvents;
	SwipeRefreshLayout swipeLayout;
	ProgressDialog dialog;
	MenuItem menuItemCreate;

	public SwipeRefreshLayout getSwipeLayout() {
		return swipeLayout;
	}

	public void setSwipeLayout(SwipeRefreshLayout swipeLayout) {
		this.swipeLayout = swipeLayout;
	}

	public MenuItem getMenuItemCreate() {
		return menuItemCreate;
	}

	public void setMenuItemCreate(MenuItem menuItemCreate) {
		this.menuItemCreate = menuItemCreate;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_all_events);

		lvViewAllEvents = (ListView) findViewById(R.id.lv_view_all_events);

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
		executeGeofencing();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_all_events_menu, menu);
		if(!haveNetworkConnection()){
			Toast.makeText(this, "Offline Mode", Toast.LENGTH_LONG).show();
			menuItemCreate = menu.findItem(R.id.create_event);
			menuItemCreate.setVisible(false);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.create_event:
			Intent intent = new Intent(ViewAllEventsActivity.this,
					CreateEventStep1Activity.class);
			startActivity(intent);
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void getAllEvents(){
		if(haveNetworkConnection()){
			GetAllEvents getAllEvents = new GetAllEvents(ViewAllEventsActivity.this, lvViewAllEvents);
			getAllEvents.execute();	
		}
		else{
			dialog = ProgressDialog.show(ViewAllEventsActivity.this,"Retrieving events", "Please wait...", true);
			getAllEventOffline();
		}
	}
	
	public void executeGeofencing(){
		
	}
	
	public void getAllEventOffline(){
		EventSQLController controller = new EventSQLController(ViewAllEventsActivity.this);
		final ArrayList<Event> eventArrList = controller.getAllEvent();
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


		        return (d1.getTime() > d2.getTime() ? -1 : 1);     //descending
		    //  return (d1.getTime() > d2.getTime() ? 1 : -1);     //ascending
		    }
		}); 
		
		if(eventArrList.size() > 0){
			EventListAdapter adapter = new EventListAdapter(ViewAllEventsActivity.this,eventList);
			lvViewAllEvents.setAdapter(adapter);
			lvViewAllEvents.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
			        Intent intent = new Intent(ViewAllEventsActivity.this,ViewEventsActivity.class);
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
					ViewAllEventsActivity.this.startActivity(intent);
				}
			});
		}
		dialog.dismiss();
		swipeLayout.setRefreshing(false);
	}
	

	/**
	 * This method is to check if there is any existing connection
	 * @return boolean
	 */
	private boolean haveNetworkConnection() {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
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

}
