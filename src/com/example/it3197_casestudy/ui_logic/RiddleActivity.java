package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
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
	ArrayList<Riddle> riddleList = new ArrayList<Riddle>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_riddle);
		
		riddleList.add(new Riddle(1, "Riddle 1", "First riddle"));
		riddleList.add(new Riddle(2, "Riddle 2", "Second riddle"));
		
		lv_riddle = (ListView) findViewById(R.id.lv_riddle);
		RiddleListAdapter adapter = new RiddleListAdapter(RiddleActivity.this, riddleList);
		lv_riddle.setAdapter(adapter);
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
