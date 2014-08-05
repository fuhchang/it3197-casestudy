package com.example.it3197_casestudy.util;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.mysqlite.EventSQLController;
import com.example.it3197_casestudy.mysqlite.SavedEventSQLController;
import com.example.it3197_casestudy.ui_logic.ViewAllEventsActivity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
		final SavedEventSQLController savedEventController = new SavedEventSQLController(context);
		final EventSQLController eventController = new EventSQLController(context);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View rowView = inflater.inflate(R.layout.list_events, null, true);
		TextView tvEventName = (TextView) rowView.findViewById(R.id.tv_event_name);
		TextView tvDateTime = (TextView) rowView.findViewById(R.id.tv_event_date_time);
		final ImageButton btnFavourite = (ImageButton) rowView.findViewById(R.id.btn_favourite);

		rowView.setId(eventList[position].getEventID());

		int checkEventID = rowView.getId();
		Event checkSavedEvent =  savedEventController.getSavedEvent(checkEventID);
		if(checkSavedEvent.getEventID() != 0){
			btnFavourite.setImageResource(android.R.drawable.btn_star_big_on);
		}
		else{
			btnFavourite.setImageResource(android.R.drawable.btn_star_big_off);
		}
		
		btnFavourite.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				int eventID = rowView.getId();
				Event savedEvent =  savedEventController.getSavedEvent(eventID);
				System.out.println(eventID);
				System.out.println(savedEvent.getEventID());
				if(savedEvent.getEventID() != 0){
					savedEventController.deleteEvent(eventID);
					btnFavourite.setImageResource(android.R.drawable.btn_star_big_off);
					Toast.makeText(context, "Event unsaved", Toast.LENGTH_LONG).show();
				}
				else{
					Event newSavedEvent = eventController.getEvent(eventID);
					savedEventController.insertEvent(newSavedEvent);
					btnFavourite.setImageResource(android.R.drawable.btn_star_big_on);
					Toast.makeText(context, "Event saved", Toast.LENGTH_LONG).show();
				}
			}
		});
		tvEventName.setText(eventList[position].getEventName().toString());
		tvDateTime.setText(dateTimeFormatter.format(eventList[position].getEventDateTimeFrom()));
		return rowView;
	}
}
