package com.example.it3197_casestudy.mysqlite;



import com.example.it3197_casestudy.model.Hobby;

import android.content.ContentValues;
import android.content.Context;

public class hobbySQL {
	Context context;
	public hobbySQL(Context context){
		this.context = context;
	}
	
	mySqLiteController conn = new mySqLiteController(context);
	
	public void insertGrp(Hobby hobby){
		conn.open();
		ContentValues cv = new ContentValues();
		cv.put("groupID", hobby.getGroupID());
		cv.put("category", hobby.getCategory());
		cv.put("location", hobby.getLocation());
		cv.put("description", hobby.getDescription());
		cv.put("active", hobby.getActive());
		conn.getDB().insert(conn.getHobbyTable(), null, cv);
		conn.close();
	}
}

