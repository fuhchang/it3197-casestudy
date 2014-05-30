package com.example.it3197_casestudy.util;

import com.example.it3197_casestudy.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventListAdapter extends ArrayAdapter<String>{
	private final Activity context;
	private final String[] eventNameList;
 
	public EventListAdapter(Activity context, String[] eventNameList) {
		super(context, R.layout.list_events, eventNameList);
		this.context = context;
		this.eventNameList = eventNameList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.list_events, null, true);
		TextView textView = (TextView) rowView.findViewById(R.id.tv_event_name);
		TextView textView2 = (TextView) rowView.findViewById(R.id.tv_event_date_time);
		textView.setText(eventNameList[position]);
		textView2.setText(eventNameList[position]);
 
		// Change icon based on name
		String s = eventNameList[position];
 
		System.out.println(s);

 
		return rowView;
	}
}
