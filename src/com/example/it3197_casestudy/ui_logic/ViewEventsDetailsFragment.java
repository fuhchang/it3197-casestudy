package com.example.it3197_casestudy.ui_logic;

import java.util.Calendar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetEvent;
import com.example.it3197_casestudy.controller.JoinEvent;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.model.EventParticipants;
import com.example.it3197_casestudy.util.Settings;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 * 
 * @author Lee Zhuo Xun
 */
public class ViewEventsDetailsFragment extends Fragment implements Settings{
	private int eventID;
	private String typeOfEvent,location;
	private TextView tvEventID, tvEventName, tvEventCategory, tvEventDescription, tvEventDateTimeFrom,
					 tvEventDateTimeTo, tvEventOccur, tvEventNoOfParticipants;
	private ImageView ivEventPoster;
	private Button btnCheckIn;
	private Event event;
	private String nric,password;
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
	
	public String getTypeOfEvent() {
		return typeOfEvent;
	}

	public void setTypeOfEvent(String typeOfEvent) {
		this.typeOfEvent = typeOfEvent;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public ViewEventsDetailsFragment(){}
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.view_events_details_fragment_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.update:
			Intent i = new Intent(ViewEventsDetailsFragment.this.getActivity(), UpdateEventStep1Activity.class);
			i.putExtra("typeOfEvent", typeOfEvent);
			i.putExtra("eventID", String.valueOf(event.getEventID()));
			i.putExtra("eventName", event.getEventName());
			i.putExtra("eventCategory", event.getEventCategory());
			i.putExtra("eventDescription", event.getEventDescription());
			i.putExtra("eventDateTimeFrom", sqlDateTimeFormatter.format(event.getEventDateTimeFrom()));
			i.putExtra("eventDateTimeTo", sqlDateTimeFormatter.format(event.getEventDateTimeTo()));
			i.putExtra("occurence", event.getOccurence());
			i.putExtra("eventLocation", location);
			i.putExtra("noOfParticipants", String.valueOf(event.getNoOfParticipantsAllowed()));
			startActivity(i);
			ViewEventsDetailsFragment.this.getActivity().finish();
			break;
		case R.id.join : 
			Calendar todayDate = Calendar.getInstance();
			EventParticipants eventParticipants = new EventParticipants(eventID,nric,todayDate.getTime(),0);
			JoinEvent joinEvent = new JoinEvent(ViewEventsDetailsFragment.this,eventParticipants);
			joinEvent.execute();
			break;
		case R.id.unjoin:
			
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_view_events_details, container, false);
		eventID = getArguments().getInt("eventID");
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
		nric = preferences.getString("nric","");
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
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
		
		//ivEventPoster.setVisibility(View.GONE);
		
		GetEvent getEvent = new GetEvent(ViewEventsDetailsFragment.this, eventID);
		getEvent.execute();
	}
}

