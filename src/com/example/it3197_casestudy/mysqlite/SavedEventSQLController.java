package com.example.it3197_casestudy.mysqlite;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.util.Settings;

public class SavedEventSQLController implements Settings{
	Context context;
	mySqLiteController conn;
	public SavedEventSQLController(Context context){
		this.context = context;
		conn = new mySqLiteController(context);
	}
	
	public void insertEvent(Event event){
		conn.open();
		ContentValues cv = new ContentValues();
		cv.put("eventID", event.getEventID());
		cv.put("eventAdminNRIC", event.getEventAdminNRIC());
		cv.put("eventName", event.getEventName());
		cv.put("eventCategory", event.getEventCategory());
		cv.put("eventDescription", event.getEventDescription());
		cv.put("eventDateTimeFrom", sqliteDateTimeFormatter.format(event.getEventDateTimeFrom()));
		cv.put("eventDateTimeTo", sqliteDateTimeFormatter.format(event.getEventDateTimeTo()));
		cv.put("occurence", event.getOccurence());
		cv.put("noOfParticipantsAllowed", event.getNoOfParticipantsAllowed());
		cv.put("active", event.getActive());
		cv.put("eventFBPostID", event.getEventFBPostID());
		
		conn.getDB().insert(conn.getSavedEventTable(), null, cv);
		conn.close();
	}
	
	public ArrayList<Event> getAllSavedEvent(){
		ArrayList<Event> eventList = new ArrayList<Event>();
		conn.open();
		Cursor cursor = conn.getDB().query(conn.getSavedEventTable(), null, null, null, null, null, "eventDateTimeFrom", null);
		if(cursor.moveToFirst()){
			do{
				Event event = new Event();
				event.setEventID(cursor.getInt(cursor.getColumnIndex("eventID")));
				event.setEventAdminNRIC(cursor.getString(cursor.getColumnIndex("eventAdminNRIC")));
				event.setEventName(cursor.getString(cursor.getColumnIndex("eventName")));
				event.setEventCategory(cursor.getString(cursor.getColumnIndex("eventCategory")));
				event.setEventDescription(cursor.getString(cursor.getColumnIndex("eventDescription")));
				try {
					Date dateTimeFrom = sqliteDateTimeFormatter.parse(cursor.getString(cursor.getColumnIndex("eventDateTimeFrom")));
					Date dateTimeTo = sqliteDateTimeFormatter.parse(cursor.getString(cursor.getColumnIndex("eventDateTimeFrom")));
					
					String stringFrom = dateTimeFormatter.format(dateTimeFrom);
					String stringTo = dateTimeFormatter.format(dateTimeTo);
					
					event.setEventDateTimeFrom(dateTimeFormatter.parse(stringFrom));
					event.setEventDateTimeTo(dateTimeFormatter.parse(stringTo));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				event.setOccurence(cursor.getString(cursor.getColumnIndex("occurence")));
				event.setNoOfParticipantsAllowed(cursor.getInt(cursor.getColumnIndex("noOfParticipantsAllowed")));
				event.setActive(cursor.getInt(cursor.getColumnIndex("active")));
				event.setEventFBPostID(cursor.getString(cursor.getColumnIndex("eventFBPostID")));
				eventList.add(event);
			}while(cursor.moveToNext());
		}
		conn.close();
		return eventList;
	}
	
	public Event getSavedEvent(int eventID){
		Event event = new Event();
		conn.open();
		Cursor cursor = conn.getDB().query(conn.getSavedEventTable(), null, "eventID = ?", new String[]{ String.valueOf(eventID) }, null, null, null, null);
	
		if(cursor.moveToFirst()){
			event.setEventID(cursor.getInt(cursor.getColumnIndex("eventID")));
			System.out.print(event.getEventID());
			event.setEventAdminNRIC(cursor.getString(cursor.getColumnIndex("eventAdminNRIC")));
			event.setEventName(cursor.getString(cursor.getColumnIndex("eventName")));
			event.setEventCategory(cursor.getString(cursor.getColumnIndex("eventCategory")));
			event.setEventDescription(cursor.getString(cursor.getColumnIndex("eventDescription")));
			try {
				Date dateTimeFrom = sqliteDateTimeFormatter.parse(cursor.getString(cursor.getColumnIndex("eventDateTimeFrom")));
				Date dateTimeTo = sqliteDateTimeFormatter.parse(cursor.getString(cursor.getColumnIndex("eventDateTimeFrom")));
				
				String stringFrom = dateTimeFormatter.format(dateTimeFrom);
				String stringTo = dateTimeFormatter.format(dateTimeTo);
				
				event.setEventDateTimeFrom(dateTimeFormatter.parse(stringFrom));
				event.setEventDateTimeTo(dateTimeFormatter.parse(stringTo));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			event.setOccurence(cursor.getString(cursor.getColumnIndex("occurence")));
			event.setNoOfParticipantsAllowed(cursor.getInt(cursor.getColumnIndex("noOfParticipantsAllowed")));
			event.setActive(cursor.getInt(cursor.getColumnIndex("active")));
			event.setEventFBPostID(cursor.getString(cursor.getColumnIndex("eventFBPostID")));
		}
		else{
			event.setEventID(0);
		}
		conn.close();
		return event;
	}
	
	public void deleteEvent(int eventID){
		conn.open();
		conn.getDB().delete(conn.getSavedEventTable(), "eventID = " + eventID, null);
		conn.close();
	}
}
