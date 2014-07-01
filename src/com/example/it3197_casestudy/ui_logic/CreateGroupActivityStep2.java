package com.example.it3197_casestudy.ui_logic;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CreateGroupActivityStep2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_group_activity_step2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_group_activity_step2, menu);
		return true;
	}

}
