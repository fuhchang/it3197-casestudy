package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.controller.GetRequestList;
import com.example.it3197_casestudy.model.Hobby;
import com.example.it3197_casestudy.model.RequestHobby;
import com.example.it3197_casestudy.util.RequestListAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.widget.ListView;

public class RequestForHobbyHelp extends Activity {
	private ListView requestList;
	ArrayList<Hobby> hobbyList;
	private int eventID =1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_for_hobby_help);
		requestList = (ListView) findViewById(R.id.requestList);
		requestList.setBackgroundColor(Color.LTGRAY);
		GetRequestList getRequestList = new GetRequestList(this,requestList, eventID);
		getRequestList.execute();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.request_for_hobby_help, menu);
		return true;
	}

}
