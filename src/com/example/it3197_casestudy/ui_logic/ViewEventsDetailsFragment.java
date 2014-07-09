package com.example.it3197_casestudy.ui_logic;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetEvent;
import com.example.it3197_casestudy.model.Event;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 * 
 * @author Lee Zhuo Xun
 */
public class ViewEventsDetailsFragment extends Fragment {
	private int eventID;
	
	private TextView tvEventID, tvEventName, tvEventCategory, tvEventDescription, tvEventDateTimeFrom,
					 tvEventDateTimeTo, tvEventOccur, tvEventNoOfParticipants;
	private ImageView ivEventPoster;
	private Button btnCheckIn;
	
	public TextView getTvEventID() {
		return tvEventID;
	}

	public void setTvEventID(TextView tvEventID) {
		this.tvEventID = tvEventID;
	}

	public TextView getTvEventName() {
		return tvEventName;
	}

	public void setTvEventName(TextView tvEventName) {
		this.tvEventName = tvEventName;
	}

	public TextView getTvEventCategory() {
		return tvEventCategory;
	}

	public void setTvEventCategory(TextView tvEventCategory) {
		this.tvEventCategory = tvEventCategory;
	}

	public TextView getTvEventDescription() {
		return tvEventDescription;
	}

	public void setTvEventDescription(TextView tvEventDescription) {
		this.tvEventDescription = tvEventDescription;
	}

	public TextView getTvEventDateTimeFrom() {
		return tvEventDateTimeFrom;
	}

	public void setTvEventDateTimeFrom(TextView tvEventDateTimeFrom) {
		this.tvEventDateTimeFrom = tvEventDateTimeFrom;
	}

	public TextView getTvEventDateTimeTo() {
		return tvEventDateTimeTo;
	}

	public void setTvEventDateTimeTo(TextView tvEventDateTimeTo) {
		this.tvEventDateTimeTo = tvEventDateTimeTo;
	}

	public TextView getTvEventOccur() {
		return tvEventOccur;
	}

	public void setTvEventOccur(TextView tvEventOccur) {
		this.tvEventOccur = tvEventOccur;
	}

	public TextView getTvEventNoOfParticipants() {
		return tvEventNoOfParticipants;
	}

	public void setTvEventNoOfParticipants(TextView tvEventNoOfParticipants) {
		this.tvEventNoOfParticipants = tvEventNoOfParticipants;
	}

	public ImageView getIvEventPoster() {
		return ivEventPoster;
	}

	public void setIvEventPoster(ImageView ivEventPoster) {
		this.ivEventPoster = ivEventPoster;
	}

	public ViewEventsDetailsFragment(){}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_view_events_details, container, false);
		eventID = getArguments().getInt("eventID");
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		tvEventID = (TextView) getActivity().findViewById(R.id.tv_event_id);
		tvEventName = (TextView) getActivity().findViewById(R.id.tv_event_name);
		ivEventPoster = (ImageView) getActivity().findViewById(R.id.iv_event_poster);
		tvEventCategory = (TextView) getActivity().findViewById(R.id.tv_event_category);
		tvEventDescription = (TextView) getActivity().findViewById(R.id.tv_event_description);
		tvEventDateTimeFrom = (TextView) getActivity().findViewById(R.id.tv_event_date_time_from);
		tvEventDateTimeTo = (TextView) getActivity().findViewById(R.id.tv_event_date_time_to);
		tvEventOccur = (TextView) getActivity().findViewById(R.id.tv_event_occur);
		tvEventNoOfParticipants = (TextView) getActivity().findViewById(R.id.tv_event_no_of_participants);
		btnCheckIn = (Button) getActivity().findViewById(R.id.btn_check_in);
		
		ivEventPoster.setVisibility(View.GONE);
		
		GetEvent getEvent = new GetEvent(ViewEventsDetailsFragment.this, eventID);
		getEvent.execute();
		
		super.onActivityCreated(savedInstanceState);
	}
}
