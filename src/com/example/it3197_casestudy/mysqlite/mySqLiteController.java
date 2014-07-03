package com.example.it3197_casestudy.mysqlite;

import com.example.it3197_casestudy.model.Hobby;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class mySqLiteController {
	private static final String database_name = "community_outreach";
	private static final String database_article = "article";
	private static final String database_comments = "comments";
	private static final String database_Event = "event";
	private static final String database_particpants = "event_Particpant";
	private static final String database_groupmember = "hobbies_group_members";
	private static final String database_post = "post";
	private static final String database_user = "user";
	private static final String database_hobby = "hobbies_group";
	private static final String database_riddle = "riddle";
	private static final String database_riddle_answered = "riddle_answered";

	private static final int database_version = 1;
	private DBHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private static class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, database_name, null, database_version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE "
					+ database_user
					+ "(nric TEXT PRIMARY KEY, name TEXT, type TEXT, password TEXT, contactNo TEXT, address TEXT, email TEXT, active INTEGER, points INTEGER)");
			db.execSQL("CREATE TABLE "
					+ database_article
					+ "(articleID INTEGER PRIMARY KEY, title TEXT, content TEXT, dateTime DATETIME, category TEXT, location TEXT, userNRIC TEXT, active INTEGER, approved INTEGER)");
			db.execSQL("CREATE TABLE "
					+ database_comments
					+ "(postID INTEGER PRIMARY KEY, userNRIC TEXT, content TEXT, dateTime DATETIME, location TEXT)");
			db.execSQL("CREATE TABLE "
					+ database_Event
					+ "(eventID INTEGER PRIMARY KEY, eventAdminNRIC TEXT, eventName TEXT, eventCategory TEXT, eventCategory TEXT, eventDescription TEXT, eventType TEXT, eventDateTimeFROM DATETIME, eventDateTimeTo DATETIME, occurence TEXT, eventLocation TEXT, noOfParticipantsAllowed INTEGER, active INTEGER)");
			db.execSQL("CREATE TABLE "
					+ database_particpants
					+ "(eventID INTEGER PRIMARY KEY, userNRIC TEXT, dateTImeJoined DATETIME , checkIn INTEGER)");
			db.execSQL("CREATE TABLE "
					+ database_groupmember
					+ "(groupID INTEGER PRIMARY KEY, userNRIC TEXT, dateTImeJoined DATETIME , active INTEGER)");
			db.execSQL("CREATE TABLE "
					+ database_post
					+ "(postID INTEGER PRIMARY KEY, datetime DATETIME, content TEXT , location TEXT, groupID INTEGER)");
			db.execSQL("CREATE TABLE "
					+ database_hobby
					+ "(groupID INTEGER PRIMARY KEY AUTOINCREMENT, groupName TEXT, category TEXT, location TEXT, description TEXT, groupImg BLOB, active INTEGER)");
			db.execSQL("CREATE TABLE "
					+ database_riddle
					+ "(riddleID INTEGER PRIMARY KEY AUTOINCREMENT, riddleTitle TEXT, riddleContent TEXT, riddleAnsID INTEGER, riddlePoints INTEGER)");
			db.execSQL("CREATE TABLE "
					+ database_riddle_answered
					+ "(riddleAnsID INTEGER PRIMARY KEY AUTOINCREMENT, riddleID INTEGER, nric TEXT, riddleAns TEXT, riddleStatus TEXT)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub

		}

	}

	public mySqLiteController(Context context) {
		ourContext = context;
	}

	public mySqLiteController open() throws SQLException {
		ourHelper = new DBHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourHelper.close();
	}
	public String getHobbyTable(){
		return database_hobby;
	}
	public SQLiteDatabase getDB(){
		return ourDatabase;
	}
	public String getUserTable(){
		return database_user;
	}
	public String getRiddleTable(){
		return database_riddle;
	}
	public String getRiddleAnsTable(){
		return database_riddle_answered;
	}
}