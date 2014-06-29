package com.example.it3197_casestudy.util;

import com.example.it3197_casestudy.R;

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
public class EventListAdapter extends ArrayAdapter<String>{
	private final Activity context;
	private final String[] eventNameList;
	private final String[] eventDateList;
 
	public EventListAdapter(Activity context, String[] eventNameList, String[] eventDateList) {
		super(context, R.layout.list_events, eventNameList);
		this.context = context;
		this.eventNameList = eventNameList;
		this.eventDateList = eventDateList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_events, null, true);
		TextView tvEventName = (TextView) rowView.findViewById(R.id.tv_event_name);
		TextView tvDateTime = (TextView) rowView.findViewById(R.id.tv_event_date_time);
		tvEventName.setText(eventNameList[position]);
		tvDateTime.setText(eventDateList[position]);
 
		// Change icon based on name
		String s = eventNameList[position];
 
		System.out.println(s);

 
		return rowView;
	}
}
