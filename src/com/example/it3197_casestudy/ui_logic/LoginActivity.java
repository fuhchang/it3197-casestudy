package com.example.it3197_casestudy.ui_logic;

import com.example.it3197_casestudy.R;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

/**
 * This is the UI logic activity class for the Login Page
 * @author Lee Zhuo Xun
 */
public class LoginActivity extends Activity {
	private ActionBar loginActionBar;
	
	public LoginActivity(){
		
	}
	
	/**
	 * Login activity is created
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loginActionBar = getActionBar();
		//Hide the action bar
		loginActionBar.hide();
		setContentView(R.layout.activity_login);
	}

	/**
	 * To create option menu and setup the action bar for the login page
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//Remove the icon
		loginActionBar.setDisplayShowHomeEnabled(false);
		//Remove the title
		/*
		actionBar.setDisplayShowTitleEnabled(false);
		*/
		return true;
	}
	
	public void onClick(View view) {
		try {
			switch (view.getId()) {
			case R.id.btn_login:
				Intent intent = new Intent(LoginActivity.this, MainLinkPage.class);
				startActivity(intent);
				this.finish();
				break;
			default:
				LoginActivity.this.finish();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
