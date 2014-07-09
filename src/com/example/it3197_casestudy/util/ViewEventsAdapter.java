package com.example.it3197_casestudy.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.ui_logic.ViewEventsDetailsFragment;
import com.example.it3197_casestudy.ui_logic.ViewEventsGalleryFragment;
import com.example.it3197_casestudy.ui_logic.ViewEventsLocationFragment;
import com.example.it3197_casestudy.ui_logic.ViewEventsTimelineFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class ViewEventsAdapter extends FragmentPagerAdapter {
	private int eventID;
	public ViewEventsAdapter(FragmentManager fm, int eventID) {
		super(fm);
		this.eventID = eventID;
	}
	
	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		// getItem is called to instantiate the fragment for the given page.
		// Return a DummySectionFragment (defined as a static inner class
		// below) with the page number as its lone argument.
		System.out.println(position);
		switch (position) {
		case 0:
			fragment = new ViewEventsDetailsFragment();
			//Add parameter into a bundle to transfer data to the fragment
			Bundle args = new Bundle();
			args.putInt("eventID", eventID);
			fragment.setArguments(args);
			break;
		case 1:
			fragment = new ViewEventsTimelineFragment();
			break;
		case 2:
			fragment = new ViewEventsGalleryFragment();
			break;
		case 3:
			fragment = new ViewEventsLocationFragment();
			break;
		default:
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// Show 4 total pages.
		return 4;
	}
}