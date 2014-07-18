package com.example.it3197_casestudy.ui_logic;

import java.util.Arrays;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetUser;
import com.example.it3197_casestudy.model.User;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is the UI logic activity class for the Login Page
 * 
 * @author Lee Zhuo Xun
 */
public class LoginActivity extends FragmentActivity {
	private ActionBar loginActionBar;
	private UiLifecycleHelper uiHelper;
	private EditText etUserName, etPassword; 
	
	
	public LoginActivity() {

	}

	/**
	 * Login activity is created
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loginActionBar = getActionBar();
		// Hide the action bar
		loginActionBar.hide();
		setContentView(R.layout.activity_login);
		etUserName = (EditText) findViewById(R.id.et_user_name);
		etPassword = (EditText) findViewById(R.id.et_password);
		
	}

	/**
	 * To create option menu and setup the action bar for the login page
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// Remove the icon
		loginActionBar.setDisplayShowHomeEnabled(false);
		// Remove the title
		/*
		 * actionBar.setDisplayShowTitleEnabled(false);
		 */
		return true;
	}

	public void onClick(View view) {
		try {
			switch (view.getId()) {
			case R.id.btn_login:
				String userNric = etUserName.getText().toString();
				String password = etPassword.getText().toString();
				User user = new User();
				user.setNric(userNric);
				user.setPassword(password);
				GetUser checkUser = new GetUser(this, user);
				checkUser.execute();
				break;
			case R.id.btn_login_facebook:
				//Session.openActiveSession(LoginActivity.this, true, callback);
				break;
			default:
				LoginActivity.this.finish();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent(LoginActivity.this, LoginSelectionActivity.class);
		startActivity(i);
		this.finish();
	}
	
	
}
