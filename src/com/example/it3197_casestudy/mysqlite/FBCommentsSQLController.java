package com.example.it3197_casestudy.mysqlite;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.model.FBComments;
import com.example.it3197_casestudy.util.Settings;

public class FBCommentsSQLController implements Settings{
	Context context;
	mySqLiteController conn;
	public FBCommentsSQLController(Context context){
		this.context = context;
		conn = new mySqLiteController(context);
	}
	
	public void insertComments(FBComments fbComments){
		conn.open();
		ContentValues cv = new ContentValues();
		cv.put("fbPostID", fbComments.getFBPostID());
		cv.put("name", fbComments.getName());
		cv.put("comment", fbComments.getComment());
		cv.put("time", sqliteDateTimeFormatter.format(fbComments.getDateTimeMade()));
		
		conn.getDB().insert(conn.getFBCommentsTable(), null, cv);
		conn.close();
	}
	
	public ArrayList<FBComments> getFBComments(String eventFBPostID){
	ArrayList<FBComments> fbCommentsList = new ArrayList<FBComments>(); 
		conn.open();
		Cursor cursor = conn.getDB().query(conn.getFBCommentsTable(), null, "fbPostID = ?", new String[]{eventFBPostID}, null, null, "time", null);
		if(cursor.moveToFirst()){
			do{
				FBComments fbComments = new FBComments();
				fbComments.setFBPostID(cursor.getString(cursor.getColumnIndex("fbPostID")));
				fbComments.setName(cursor.getString(cursor.getColumnIndex("name")));
				fbComments.setComment(cursor.getString(cursor.getColumnIndex("comment")));
				try {
					Date dateTimeFrom = sqliteDateTimeFormatter.parse(cursor.getString(cursor.getColumnIndex("time")));
					
					String stringFrom = dateTimeFormatter.format(dateTimeFrom);
					
					fbComments.setDateTimeMade(dateTimeFormatter.parse(stringFrom));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fbCommentsList.add(fbComments);
			}while(cursor.moveToNext());
		}
		conn.close();
		return fbCommentsList;
	}
	
	public ArrayList<FBComments> getAllFBComments(){		
		ArrayList<FBComments> fbCommentsList = new ArrayList<FBComments>();
		conn.open();
		Cursor cursor = conn.getDB().query(conn.getFBCommentsTable(), null, null, null, null, null, "time", null);
		if(cursor.moveToFirst()){
			do{
				FBComments fbComments = new FBComments();
				fbComments.setFBPostID(cursor.getString(cursor.getColumnIndex("fbPostID")));
				fbComments.setName(cursor.getString(cursor.getColumnIndex("name")));
				fbComments.setComment(cursor.getString(cursor.getColumnIndex("comment")));
				try {
					Date dateTimeFrom = sqliteDateTimeFormatter.parse(cursor.getString(cursor.getColumnIndex("time")));
					
					String stringFrom = dateTimeFormatter.format(dateTimeFrom);
					
					fbComments.setDateTimeMade(dateTimeFormatter.parse(stringFrom));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fbCommentsList.add(fbComments);
			}while(cursor.moveToNext());
		}
		conn.close();
		return fbCommentsList;
	}
	
	public void deleteAllFBComments(){
		conn.open();
		conn.getDB().delete(conn.getFBCommentsTable(), null, null);
		conn.close();
	}
}
