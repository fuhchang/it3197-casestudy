package com.example.it3197_casestudy.ui_logic;

import java.text.ParseException;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetEvent;
import com.example.it3197_casestudy.controller.GetEventParticipants;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.util.Settings;
import com.example.it3197_casestudy.util.ViewEventsAdapter;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class ViewEventsActivity extends FragmentActivity implements ActionBar.TabListener,Settings {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	ViewEventsAdapter mViewEventsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	private Event event = new Event();
	private boolean joined;
	private double lat;
	private double lng;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_events);
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// Show the Up button in the action bar.
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		savedInstanceState = getIntent().getExtras();
		if(savedInstanceState != null){
			event.setEventID(savedInstanceState.getInt("eventID"));
			event.setEventAdminNRIC(savedInstanceState.getString("eventAdminNRIC"));
			event.setEventName(savedInstanceState.getString("eventName"));
			event.setEventCategory(savedInstanceState.getString("eventCategory"));
			event.setEventDescription(savedInstanceState.getString("eventDescription"));
			try {
				event.setEventDateTimeFrom(sqlDateTimeFormatter.parse(savedInstanceState.getString("eventDateTimeFrom")));
				event.setEventDateTimeTo(sqlDateTimeFormatter.parse(savedInstanceState.getString("eventDateTimeTo")));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			event.setOccurence(savedInstanceState.getString("occurence"));
			event.setNoOfParticipantsAllowed(savedInstanceState.getInt("noOfParticipants"));
			event.setActive(savedInstanceState.getInt("active"));
			event.setEventFBPostID(savedInstanceState.getString("eventFBPostID"));
			joined = savedInstanceState.getBoolean("joined");

			lat = savedInstanceState.getDouble("lat");
			lng = savedInstanceState.getDouble("lng");
		}
		mViewEventsPagerAdapter = new ViewEventsAdapter(getSupportFragmentManager(),event,joined,lat,lng);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mViewEventsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});
		actionBar.addTab(actionBar.newTab().setText("Details").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("Timeline").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("Gallery").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("Location").setTabListener(this));
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}
	
	 @Override
	 public void onBackPressed(){
		 Intent intent = new Intent(ViewEventsActivity.this, ViewAllEventsActivity.class);
		 startActivity(intent);
		 this.finish(); 
	 }

}
