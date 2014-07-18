package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.RetrieveAllRiddle;
import com.example.it3197_casestudy.model.Riddle;
import com.example.it3197_casestudy.util.RiddleListAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class RiddleActivity extends FragmentActivity {
	ListView lv_riddle;
	RiddleListAdapter riddleAdapter;
	ArrayList<Riddle> riddleList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_riddle);
		
		lv_riddle = (ListView) findViewById(R.id.lv_riddle);
		RetrieveAllRiddle retrieveAllRiddle = new RetrieveAllRiddle(this, lv_riddle);
		retrieveAllRiddle.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.create_riddle, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.action_create_riddle:
				Intent createRiddleIntent = new Intent(RiddleActivity.this, CreateRiddleActivity.class);
				startActivity(createRiddleIntent);
		}
		return super.onOptionsItemSelected(item);
	}
}
