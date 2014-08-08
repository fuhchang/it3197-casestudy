package com.example.it3197_casestudy.util;

import java.util.HashMap;
import java.util.List;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.mysqlite.EventSQLController;
import com.example.it3197_casestudy.mysqlite.SavedEventSQLController;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class EventExpandedListAdapter extends BaseExpandableListAdapter implements Settings{
	 
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Event>> _listDataChild;
 
    public EventExpandedListAdapter(Context context, List<String> listDataHeader,HashMap<String, List<Event>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }
 
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {

		final SavedEventSQLController savedEventController = new SavedEventSQLController(_context);
		final EventSQLController eventController = new EventSQLController(_context);
        final Event event = (Event) getChild(groupPosition, childPosition);
 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_events, null);
        }
 
        TextView tvEventName = (TextView) convertView.findViewById(R.id.tv_event_name);
        TextView tvDateTime = (TextView) convertView.findViewById(R.id.tv_event_date_time);
		final ImageButton btnFavourite = (ImageButton) convertView.findViewById(R.id.btn_favourite);

		convertView.setId(event.getEventID());

		final int checkEventID = convertView.getId();
		Event checkSavedEvent =  savedEventController.getSavedEvent(checkEventID);
		if(checkSavedEvent.getEventID() != 0){
			btnFavourite.setImageResource(android.R.drawable.btn_star_big_on);
		}
		else{
			btnFavourite.setImageResource(android.R.drawable.btn_star_big_off);
		}
		
		btnFavourite.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN ) {
					int eventID = checkEventID;
					Event savedEvent =  savedEventController.getSavedEvent(eventID);
					if(savedEvent.getEventID() != 0){
						savedEventController.deleteEvent(eventID);
						btnFavourite.setImageResource(android.R.drawable.btn_star_big_off);
						Toast.makeText(_context, "Event unsaved", Toast.LENGTH_LONG).show();
					}
					else{
						Event newSavedEvent = eventController.getEvent(eventID);
						savedEventController.insertEvent(newSavedEvent);
						btnFavourite.setImageResource(android.R.drawable.btn_star_big_on);
						Toast.makeText(_context, "Event saved", Toast.LENGTH_LONG).show();
					}
					return true;
				}
				return false;
			}
		});
		tvEventName.setText(event.getEventName().toString());
		tvDateTime.setText(dateTimeFormatter.format(event.getEventDateTimeFrom()));
        //txtListChild.setText(childText);
        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_events_header, null);
        }
 
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.tv_header);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
 
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}