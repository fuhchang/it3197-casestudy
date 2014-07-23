package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.RetrieveAllRiddleWithAnswers;
import com.example.it3197_casestudy.model.Riddle;
import com.example.it3197_casestudy.model.User;
import com.example.it3197_casestudy.util.RiddleListAdapter;

public class RiddleActivity extends FragmentActivity {
	ListView lv_riddle;
	RiddleListAdapter riddleAdapter;
	ArrayList<Riddle> riddleList;
	
	Bundle data;
	User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_riddle);
		
		data = getIntent().getExtras();
		user = data.getParcelable("user");
		
		lv_riddle = (ListView) findViewById(R.id.lv_riddle);
		RetrieveAllRiddleWithAnswers retrieveAllRiddleWithAnswers = new RetrieveAllRiddleWithAnswers(this, lv_riddle);
		retrieveAllRiddleWithAnswers.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.create_riddle, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onRestart() {
		RetrieveAllRiddleWithAnswers retrieveAllRiddleWithAnswers = new RetrieveAllRiddleWithAnswers(this, lv_riddle);
		retrieveAllRiddleWithAnswers.execute();
		super.onRestart();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.action_create_riddle:
				if(user.getPoints() > 10) {
					Intent createRiddleIntent = new Intent(RiddleActivity.this, CreateRiddleActivity.class);
					createRiddleIntent.putExtra("user", user);
					startActivity(createRiddleIntent);
				}
				else {
					AlertDialog.Builder builder = new AlertDialog.Builder(RiddleActivity.this);
					builder.setTitle("Unable to create").setMessage("Insufficient points to create.\nTravel within the community or join some events to earn points.");
					builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					builder.create().show();
				}
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
