package com.example.it3197_casestudy.ui_logic;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class CreateEventStep4Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event_step_4);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_page, menu);
		return false;
	}
	
	public void onClick(View view){
		Intent intent;
		try {
			switch (view.getId()) {
			case R.id.btn_create_event:
				Toast.makeText(getApplicationContext(), "Event created successfully.", Toast.LENGTH_SHORT).show();
				intent = new Intent(CreateEventStep4Activity.this, MainPageActivity.class);
				startActivity(intent);
				this.finish();
				break;
			case R.id.btn_previous:
				intent = new Intent(CreateEventStep4Activity.this, CreateEventStep3cActivity.class);
				startActivity(intent);
				this.finish();
				break;
			default:
				CreateEventStep4Activity.this.finish();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override 
	public void onBackPressed(){  
		Intent intent = new Intent(CreateEventStep4Activity.this, CreateEventStep3cActivity.class);
		startActivity(intent);
		this.finish();
	}


}
