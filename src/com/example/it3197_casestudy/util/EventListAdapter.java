package com.example.it3197_casestudy.util;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.model.Event;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * This is the event list adapter to bind it with the event list at the view all events page
 * @author Lee Zhuo Xun
 *
 */
public class EventListAdapter extends ArrayAdapter<Event> implements Settings{
	private final Activity context;
	private final Event[] eventList;
 
	public EventListAdapter(Activity context, Event[] eventList) {
		super(context, R.layout.list_events, eventList);
		this.context = context;
		this.eventList = eventList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_events, null, true);
		rowView.setId(eventList[position].getEventID());
		TextView tvEventName = (TextView) rowView.findViewById(R.id.tv_event_name);
		TextView tvDateTime = (TextView) rowView.findViewById(R.id.tv_event_date_time);
		tvEventName.setText(eventList[position].getEventName().toString());
		tvDateTime.setText(dateTimeFormatter.format(eventList[position].getEventDateTimeFrom()));
		return rowView;
	}
}
