package com.example.it3197_casestudy;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;

/**
 * This is the UI logic activity class for the Login Page
 * @author Lee Zhuo Xun
 */
public class LoginActivity extends Activity {
	/**
	 * Login activity is created
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	/**
	 * To create option menu and setup the action bar for the login page
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		ActionBar actionBar = getActionBar();
		//Remove the icon
		actionBar.setDisplayShowHomeEnabled(false);
		//Remove the title
		/*
		actionBar.setDisplayShowTitleEnabled(false);
		*/
		return true;
	}

}
