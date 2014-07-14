package com.example.it3197_casestudy.ui_logic;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class UpdateGroupActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_group);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_group, menu);
		return true;
	}

}
