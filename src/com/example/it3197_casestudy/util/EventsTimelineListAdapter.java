package com.example.it3197_casestudy.util;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.mysqlite.EventSQLController;
import com.example.it3197_casestudy.mysqlite.SavedEventSQLController;
import com.example.it3197_casestudy.ui_logic.ViewEventsTimelineFragment;

public class EventsTimelineListAdapter  extends ArrayAdapter<String[]> implements Settings{
	private TextView eventTimelineName, eventTimelineTime,eventTimelineComments;
	private final Activity context;
	private final String[][] eventTimelineList;
	
	public EventsTimelineListAdapter(Activity context, String[][] eventTimelineList) {
		super(context, R.layout.list_events, eventTimelineList);
		this.context = context;
		this.eventTimelineList = eventTimelineList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View rowView = inflater.inflate(R.layout.list_event_timeline, null, true);
		//rowView.setId(eventList[position].getEventID());

		eventTimelineName = (TextView) rowView.findViewById(R.id.tv_event_timeline_name);
		eventTimelineName.setText(eventTimelineList[position][0]);
		eventTimelineTime = (TextView) rowView.findViewById(R.id.tv_event_timeline_time);
		Date timeCommented = null;
		try{
			if(CheckNetworkConnection.haveNetworkConnection(context)){
				timeCommented = fbDateTimeFormatter.parse(eventTimelineList[position][1]);
			}
			else{
				timeCommented = dateTimeFormatter.parse(eventTimelineList[position][1]);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		eventTimelineTime.setText(dateTimeFormatter.format(timeCommented));
		eventTimelineComments = (TextView) rowView.findViewById(R.id.tv_event_timeline_comments);
		eventTimelineComments.setText(eventTimelineList[position][2]);
		return rowView;
	}
}
