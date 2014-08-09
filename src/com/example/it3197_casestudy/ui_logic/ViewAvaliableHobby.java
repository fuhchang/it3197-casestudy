package com.example.it3197_casestudy.ui_logic;

import java.util.Date;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.id;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.controller.GetAvaliableHobby;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.widget.ListView;

public class ViewAvaliableHobby extends Activity {
	private ListView listView;
	private int eventID = 1;
	private String date1, date2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_avaliable_hobby);
		listView = (ListView) findViewById(R.id.avaliableHobbyList);
		listView.setBackgroundColor(Color.LTGRAY);
		eventID = getIntent().getExtras().getInt("eventID");
		date1 = getIntent().getExtras().getString("date1");
		date2 = getIntent().getExtras().getString("date2");
		GetAvaliableHobby gethobby = new GetAvaliableHobby(this, listView,  eventID, date1, date2);
		gethobby.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_avaliable_hobby, menu);
		return true;
	}

}
