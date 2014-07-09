package com.example.it3197_casestudy.ui_logic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetEvent;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 * 
 * @author Lee Zhuo Xun
 */
public class ViewEventsDetailsFragment extends Fragment {
	private int eventID;
	
	private TextView tvEventID;
	public TextView tvEventName;
	
	public ViewEventsDetailsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_view_events_details, container, false);
		
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		savedInstanceState = getActivity().getIntent().getExtras();
		eventID = savedInstanceState.getInt("eventID");
		System.out.println("Event ID: " + eventID);
		// TODO Auto-generated method stub
		GetEvent getEvent = new GetEvent(ViewEventsDetailsFragment.this,eventID);
		getEvent.execute();
		tvEventID = (TextView) getActivity().findViewById(R.id.tv_event_id);
		tvEventID.setText("Event No: #" + eventID);
		
		tvEventName = (TextView) getActivity().findViewById(R.id.tv_event_name);
		
		super.onActivityCreated(savedInstanceState);
	}
}
