package com.example.it3197_casestudy.mysqlite;



import java.util.ArrayList;

import com.example.it3197_casestudy.model.Hobby;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class hobbySQL {
	Context context;
	public hobbySQL(Context context){
		this.context = context;
	}
	
	mySqLiteController conn = new  mySqLiteController(context);
	
	public void insertGrp(Hobby hobby){
		conn.open();
		ContentValues cv = new ContentValues();
		cv.put("groupID", hobby.getGroupID());
		cv.put("category", hobby.getCategory());
		cv.put("Lat", hobby.getLat());
		cv.put("Lng", hobby.getLng());
		cv.put("description", hobby.getDescription());
		cv.put("active", hobby.getActive());
		cv.put("grpImg", hobby.getGrpImg());
		conn.getDB().insert(conn.getHobbyTable(), null, cv);
		conn.close();
	}
	
	public ArrayList<Hobby> getAllHobby(){
		ArrayList<Hobby> hobbyList = new ArrayList<Hobby>();
		conn.open();
		Cursor cursor = conn.getDB().rawQuery("select * from" + conn.getHobbyTable(), null);
		conn.close();
		do{
			Hobby hobby = new Hobby();
			hobby.setGroupID(cursor.getInt(cursor.getColumnIndex("groupID")));
			hobby.setGroupName(cursor.getString(cursor.getColumnIndex("groupName")));
			hobby.setCategory(cursor.getString(cursor.getColumnIndex("category")));
			hobby.setDescription(cursor.getString(cursor.getColumnIndex("description")));
			//hobby.setGrpImg(cursor.getBlob(cursor.getColumnIndex("groupImg")));
			hobbyList.add(hobby);
		}while(cursor.moveToNext());
		return hobbyList;
		
	}
}

