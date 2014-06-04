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
import android.widget.EditText;
import android.widget.Spinner;

public class CreateEventStep1Activity extends Activity {
	EditText etEventName,etDescription;
	Spinner spinnerCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event_step_1);
		etEventName = (EditText) findViewById(R.id.et_name);
		spinnerCategory = (Spinner) findViewById(R.id.spinner_category);
		etDescription = (EditText) findViewById(R.id.et_description);
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
				intent = new Intent(CreateEventStep1Activity.this, CreateEventStep2Activity.class);
				startActivity(intent);
				this.finish();
				break;
			case R.id.btn_cancel:
				onBackPressed();
				break;
			default:
				CreateEventStep1Activity.this.finish();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override 
	public void onBackPressed(){  
		Intent intent = new Intent(CreateEventStep1Activity.this, MainPageActivity.class);
		startActivity(intent);
		this.finish();
	}

}
