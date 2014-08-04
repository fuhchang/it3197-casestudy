package com.example.it3197_casestudy.ui_logic;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.example.it3197_casestudy.R;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginSelectionActivity extends FragmentActivity {
	private ActionBar loginActionBar;
	private UiLifecycleHelper uiHelper;
	private LoginButton authButton;

	private static final List<String> PERMISSIONS = Arrays.asList("user_friends","user_about_me","read_friendlists");
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	private boolean pendingPublishReauthorization = false;
	/**
	 * Login activity is created
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(LoginSelectionActivity.this, callback);
		uiHelper.onCreate(savedInstanceState);
		loginActionBar = getActionBar();
		// Hide the action bar
		loginActionBar.hide();
		setContentView(R.layout.activity_login_selection);
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
		authButton = (LoginButton) findViewById(R.id.btn_login_facebook);
		authButton.setReadPermissions(PERMISSIONS);
		//		authButton.setReadPermissions(PERMISSIONS);
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
				Intent intent = new Intent(LoginSelectionActivity.this,LoginActivity.class);
				startActivity(intent);
				this.finish();
				break;
			case R.id.btn_login_facebook:
				Session.openActiveSession(LoginSelectionActivity.this, true, callback);
				//Session.openActiveSession(LoginSelectionActivity.this, true, callback);
				break;
			default:
				LoginSelectionActivity.this.finish();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			loginViaFB(session);
			Log.i("Login via Facebook status: ", "Logged in...");
		} else if (state.isClosed()) {
			Log.i("Login via Facebook status: ", "Logged out...");
		}
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, session.getState(), null);
		}
	};

	@Override
	public void onResume() {
		super.onResume();
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() && !session.isClosed())) {
			Session.openActiveSession(LoginSelectionActivity.this, true, callback);
		}
		uiHelper.onResume();
	}
	
	private void loginViaFB(final Session session){
		final ProgressDialog dialog = ProgressDialog.show(this, "Logging in...", "Please Wait. ");
		Request.newMeRequest(session, new Request.GraphUserCallback() {
			//callback after Graph API response with user object
			@Override
			public void onCompleted(GraphUser user, Response response) {
				if (user != null) {
					Intent intent = new Intent(LoginSelectionActivity.this,MainLinkPage.class);
					dialog.dismiss();
					intent.putExtra("userName", user.getName());
					startActivity(intent);
					LoginSelectionActivity.this.finish();
				}
				else{
					new Handler(Looper.getMainLooper()).post(new Runnable() {
				        public void run() {
				            dialog.dismiss();
				            AlertDialog.Builder builder = new AlertDialog.Builder(LoginSelectionActivity.this);
				            builder.setTitle("Error in logging in with Facebook ");
				            builder.setMessage("Unable to logging in with Facebook. Please try again.");
				            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									Session.getActiveSession().close();
								}
							});
				            builder.create().show();
				        }
					});
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
	
	private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
	    for (String string : subset) {
	        if (!superset.contains(string)) {
	            return false;
	        }
	    }
	    return true;
	}
}
