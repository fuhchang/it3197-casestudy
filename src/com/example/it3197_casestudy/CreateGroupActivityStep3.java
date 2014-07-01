package com.example.it3197_casestudy;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CreateGroupActivityStep3 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_group_activity_step3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_group_activity_step3, menu);
		return true;
	}

}
