package com.example.it3197_casestudy.mysqlite;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import com.example.it3197_casestudy.model.EventLocationDetail;
import com.example.it3197_casestudy.model.EventParticipants;
import com.example.it3197_casestudy.util.Settings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class EventParticipantsSQLController implements Settings{
	Context context;
	mySqLiteController conn;
	public EventParticipantsSQLController(Context context){
		this.context = context;
		conn = new mySqLiteController(context);
	}
	
	public void insertEventParticipant(EventParticipants eventParticipants){
		conn.open();
		ContentValues cv = new ContentValues();
		cv.put("eventID", eventParticipants.getEventID());
		cv.put("userNRIC", eventParticipants.getUserNRIC());
		cv.put("dateTImeJoined", sqliteDateTimeFormatter.format(eventParticipants.getDateTimeJoined()));
		cv.put("checkIn", eventParticipants.getCheckIn());
		
		conn.getDB().insert(conn.getEventParticipantsTable(), null, cv);
		conn.close();
	}
	

	
	public ArrayList<EventParticipants> getAllEventParticipants(){
		ArrayList<EventParticipants> eventParticipantsArrList = new ArrayList<EventParticipants>();
		conn.open();
		Cursor cursor = conn.getDB().query(conn.getEventParticipantsTable(), null, null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				EventParticipants eventParticipants = new EventParticipants();
				eventParticipants.setEventID(cursor.getInt(cursor.getColumnIndex("eventID")));
				eventParticipants.setUserNRIC(cursor.getString(cursor.getColumnIndex("userNRIC")));
				try {
					Date dateTimeFrom = sqliteDateTimeFormatter.parse(cursor.getString(cursor.getColumnIndex("dateTImeJoined")));
					
					String stringFrom = dateTimeFormatter.format(dateTimeFrom);
					
					eventParticipants.setDateTimeJoined(dateTimeFormatter.parse(stringFrom));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				eventParticipants.setCheckIn(cursor.getInt(cursor.getColumnIndex("checkIn")));
				eventParticipantsArrList.add(eventParticipants);
			}while(cursor.moveToNext());
		}
		conn.close();
		return eventParticipantsArrList;
	}
	
	public EventParticipants getEventParticipant(int eventID){
		EventParticipants eventParticipants = new EventParticipants();
		conn.open();
		Cursor cursor = conn.getDB().query(conn.getEventParticipantsTable(), null, "eventID = ?", new String[]{ String.valueOf(eventID) }, null, null, null, null);
		if(cursor.moveToFirst()){
			eventParticipants.setEventID(cursor.getInt(cursor.getColumnIndex("eventID")));
			eventParticipants.setUserNRIC(cursor.getString(cursor.getColumnIndex("userNRIC")));
			try {
				Date dateTimeFrom = sqliteDateTimeFormatter.parse(cursor.getString(cursor.getColumnIndex("dateTImeJoined")));
				
				String stringFrom = dateTimeFormatter.format(dateTimeFrom);
				
				eventParticipants.setDateTimeJoined(dateTimeFormatter.parse(stringFrom));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			eventParticipants.setCheckIn(cursor.getInt(cursor.getColumnIndex("checkIn")));
		}
		else{
			eventParticipants.setEventID(0);
		}
		conn.close();
		return eventParticipants;
	}
	
	public void deleteAllEventParticipants(){
		conn.open();
		conn.getDB().delete(conn.getEventParticipantsTable(), null, null);
		conn.close();
	}
}
