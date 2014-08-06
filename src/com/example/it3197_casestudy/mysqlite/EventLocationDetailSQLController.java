package com.example.it3197_casestudy.mysqlite;

import java.util.ArrayList;

import com.example.it3197_casestudy.model.EventLocationDetail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class EventLocationDetailSQLController {
		Context context;
		mySqLiteController conn;
		public EventLocationDetailSQLController(Context context){
			this.context = context;
			conn = new mySqLiteController(context);
		}
		
		public void insertEventLocationDetail(EventLocationDetail eventLocationDetails){
			conn.open();
			ContentValues cv = new ContentValues();
			cv.put("eventLocationID", eventLocationDetails.getEventLocationID());
			cv.put("eventID", eventLocationDetails.getEventID());
			cv.put("eventLocationName", eventLocationDetails.getEventLocationName());
			cv.put("eventLocationAddress", eventLocationDetails.getEventLocationAddress());
			cv.put("eventLocationHyperLink", eventLocationDetails.getEventLocationHyperLink());
			cv.put("eventLocationLat", eventLocationDetails.getEventLocationLat());
			cv.put("eventLocationLng", eventLocationDetails.getEventLocationLng());
			
			conn.getDB().insert(conn.getEventLocationDetailTable(), null, cv);
			conn.close();
		}
		
		public ArrayList<EventLocationDetail> getAllEventLocationDetails(){
			ArrayList<EventLocationDetail> eventLocationDetailsList = new ArrayList<EventLocationDetail>();
			conn.open();
			Cursor cursor = conn.getDB().query(conn.getEventLocationDetailTable(), null, null, null, null, null, null, null);
			if(cursor.moveToFirst()){
				do{
					EventLocationDetail eventLocationDetails = new EventLocationDetail();
					eventLocationDetails.setEventLocationID(cursor.getInt(cursor.getColumnIndex("eventLocationID")));
					eventLocationDetails.setEventID(cursor.getInt(cursor.getColumnIndex("eventID")));
					eventLocationDetails.setEventLocationName(cursor.getString(cursor.getColumnIndex("eventLocationName")));
					eventLocationDetails.setEventLocationAddress(cursor.getString(cursor.getColumnIndex("eventLocationAddress")));
					eventLocationDetails.setEventLocationHyperLink(cursor.getString(cursor.getColumnIndex("eventLocationHyperLink")));
					eventLocationDetails.setEventLocationLat(cursor.getDouble(cursor.getColumnIndex("eventLocationLat")));
					eventLocationDetails.setEventLocationLng(cursor.getDouble(cursor.getColumnIndex("eventLocationLng")));
					eventLocationDetailsList.add(eventLocationDetails);
				}while(cursor.moveToNext());
			}
			conn.close();
			return eventLocationDetailsList;
		}
		
		public EventLocationDetail getEventLocationDetail(int eventID){
			EventLocationDetail eventLocationDetails = new EventLocationDetail();
			conn.open();
			Cursor cursor = conn.getDB().query(conn.getEventLocationDetailTable(), null, "eventID = ?", new String[]{ String.valueOf(eventID) }, null, null, null, null);
			if(cursor.moveToFirst()){
				eventLocationDetails.setEventLocationID(cursor.getInt(cursor.getColumnIndex("eventLocationID")));
				eventLocationDetails.setEventID(cursor.getInt(cursor.getColumnIndex("eventID")));
				eventLocationDetails.setEventLocationName(cursor.getString(cursor.getColumnIndex("eventLocationName")));
				eventLocationDetails.setEventLocationAddress(cursor.getString(cursor.getColumnIndex("eventLocationAddress")));
				eventLocationDetails.setEventLocationHyperLink(cursor.getString(cursor.getColumnIndex("eventLocationHyperLink")));
				eventLocationDetails.setEventLocationLat(cursor.getDouble(cursor.getColumnIndex("eventLocationLat")));
				eventLocationDetails.setEventLocationLng(cursor.getDouble(cursor.getColumnIndex("eventLocationLng")));
			}
			else{
				eventLocationDetails.setEventID(0);
			}
			conn.close();
			return eventLocationDetails;
		}
}
