package com.example.it3197_casestudy.ui_logic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.it3197_casestudy.R;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */
public class ViewEventsDetailsFragment extends Fragment {
	public ViewEventsDetailsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_view_events_details, container, false);
		return rootView;
	}
}
