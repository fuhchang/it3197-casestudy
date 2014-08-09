package com.example.it3197_casestudy.util;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.it3197_casestudy.controller.GetEventParticipants;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.model.EventParticipants;
import com.example.it3197_casestudy.ui_logic.ViewEventsDetailsFragment;
import com.example.it3197_casestudy.ui_logic.ViewEventsGalleryFragment;
import com.example.it3197_casestudy.ui_logic.ViewEventsLocationFragment;
import com.example.it3197_casestudy.ui_logic.ViewEventsTimelineFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class ViewEventsAdapter extends FragmentPagerAdapter implements Settings{
	private Event event = new Event();
	private boolean joined;
	private double lat,lng;
	
	public ViewEventsAdapter(FragmentManager fm, Event event, boolean joined, double lat, double lng) {
		super(fm);
		this.event = event;
		this.joined = joined;
		this.lat = lat;
		this.lng = lng;
	}
	
	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		//Add parameter into a bundle to transfer data to the fragment
		Bundle args = new Bundle();
		// getItem is called to instantiate the fragment for the given page.
		// Return a DummySectionFragment (defined as a static inner class
		// below) with the page number as its lone argument.
		System.out.println(position);
		switch (position) {
		case 0:
			fragment = new ViewEventsDetailsFragment();
			args.putInt("eventID", event.getEventID());
			args.putString("eventAdminNRIC", event.getEventAdminNRIC());
			args.putString("eventName", event.getEventName());
			args.putString("eventCategory", event.getEventCategory());
			args.putString("eventDescription", event.getEventDescription());
			args.putString("eventDateTimeFrom", sqlDateTimeFormatter.format(event.getEventDateTimeFrom()));
			args.putString("eventDateTimeTo", sqlDateTimeFormatter.format(event.getEventDateTimeTo()));
			args.putString("occurence", event.getOccurence());
			args.putInt("noOfParticipants", event.getNoOfParticipantsAllowed());
			args.putInt("active", event.getActive());
			args.putString("eventFBPostID", event.getEventFBPostID());
			args.putBoolean("joined", joined);
			args.putDouble("lat", lat);
			args.putDouble("lng", lng);
			fragment.setArguments(args);

			break;
		case 1:
			fragment = new ViewEventsTimelineFragment();
			args.putString("eventFBPostID", event.getEventFBPostID());
			fragment.setArguments(args);
			break;
		default:
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// Show 2 total pages.
		return 2;
	}
}