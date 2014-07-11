package com.example.it3197_casestudy.ui_logic;

import java.util.Arrays;

import com.example.it3197_casestudy.R;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import android.os.Build;
import android.os.Bundle;
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

	public LoginActivity() {

	}

	/**
	 * Login activity is created
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(LoginActivity.this, callback);
		uiHelper.onCreate(savedInstanceState);
		loginActionBar = getActionBar();
		// Hide the action bar
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
				Intent intent = new Intent(LoginActivity.this,
						MainLinkPage.class);
				startActivity(intent);
				this.finish();
				break;
			case R.id.btn_login_facebook:
				Session.openActiveSession(LoginActivity.this, true, callback);
				break;
			default:
				LoginActivity.this.finish();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			Log.i("Login via Facebook status: ", "Logged in...");
		} else if (state.isClosed()) {
			Log.i("Login via Facebook status: ", "Logged out...");
		}
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			loginViaFB(session);
		}
	};

	@Override
	public void onResume() {
		super.onResume();
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			loginViaFB(session);
		}

		uiHelper.onResume();
	}
	
	private void loginViaFB(Session session){
		onSessionStateChange(session, session.getState(), null);
		final ProgressDialog dialog = ProgressDialog.show(this, "Logging in...", "Please Wait. ");
		Request.newMeRequest(session, new Request.GraphUserCallback() {
			// callback after Graph API response with user object
			@Override
			public void onCompleted(GraphUser user, Response response) {
				if (user != null) {
					TextView userNameOrEmailTextField = (TextView) findViewById(R.id.tv_user_name);
					userNameOrEmailTextField.setText(user.getName());
					Intent intent = new Intent(LoginActivity.this,MainLinkPage.class);
					dialog.dismiss();
					startActivity(intent);
					LoginActivity.this.finish();
				}
			}
		}).executeAsync();
	}
	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
}
