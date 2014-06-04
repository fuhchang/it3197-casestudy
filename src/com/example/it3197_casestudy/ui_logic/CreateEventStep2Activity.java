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

public class CreateEventStep2Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event_step_2);
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
			case R.id.btn_next:
				intent = new Intent(CreateEventStep2Activity.this, CreateEventStep3aActivity.class);
				startActivity(intent);
				this.finish();
				break;
			case R.id.btn_previous:
				intent = new Intent(CreateEventStep2Activity.this, CreateEventStep1Activity.class);
				startActivity(intent);
				this.finish();
				break;
			default:
				CreateEventStep2Activity.this.finish();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override 
	public void onBackPressed(){  
		Intent intent = new Intent(CreateEventStep2Activity.this, CreateEventStep1Activity.class);
		startActivity(intent);
		this.finish();
	}

}
