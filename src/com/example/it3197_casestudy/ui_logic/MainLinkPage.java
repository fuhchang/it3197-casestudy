package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;


import java.util.List;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.model.RowItem;
import com.example.it3197_casestudy.model.User;
import com.example.it3197_casestudy.util.GridImageList;
import com.example.it3197_casestudy.util.LocationService;
import com.example.it3197_casestudy.ui_logic.SubmitArticle;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainLinkPage extends Activity {
	GridView gv;
	ViewFlipper page;
	 Animation animFlipInForeward;
	 Animation animFlipOutForeward;
	 Animation animFlipInBackward;
	 Animation animFlipOutBackward;
	String[] title = { "EVENTS", "HOBBIES", "ARTICLE", "RIDDLES", "PROFILE",
			"SETTING" };

	Integer[] imageID = { R.drawable.events, R.drawable.hobbies,
			R.drawable.article, R.drawable.riddles, R.drawable.profile,
			R.drawable.setting };
	
	Bundle data;
	User user;
	Intent intent;
	Location polledLocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_link_page);
		
		getActionBar().setTitle("Home");
		
		data = getIntent().getExtras();
		user = data.getParcelable("user");
		
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		String username = sp.getString("username","");
		
		TextView userDetail = (TextView)findViewById(R.id.userDetail);
		userDetail.setText("Welcome, " + username);
		
		//page = (ViewFlipper) findViewById(R.id.flipper);
		//animFlipInForeward = AnimationUtils.loadAnimation(this, R.anim.f)
		
		//startService();
		GridImageList adapter = new GridImageList(MainLinkPage.this, title,
				imageID);
		GridView gv = (GridView) findViewById(R.id.gridview);
		gv.setAdapter(adapter);
		gv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = null;
				String nric = getIntent().getExtras().getString("nric");
				if(position == 0){
					intent = new Intent(MainLinkPage.this,ViewAllEventsActivity.class);
					intent.putExtra("nric", nric);
				}else if(position == 1){
					intent = new Intent(MainLinkPage.this, ViewHobbiesMain.class);
					intent.putExtra("nric", nric);
				}else if(position == 2){
					intent = new Intent(MainLinkPage.this, SubmitArticle.class);
					intent.putExtra("nric", nric);
				}else if (position == 3){
					intent = new Intent(MainLinkPage.this, RiddleActivity.class);
					intent.putExtra("user", user);
				}else if (position == 4){
					intent = new Intent(MainLinkPage.this, ProfileActivity.class);
					intent.putExtra("user", user);
				}else if(position == 5){
					Toast.makeText(getApplicationContext(), "SETTING", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getApplicationContext(), "Please choose 1 of options.", Toast.LENGTH_LONG).show();
				}
				startActivity(intent);
			}
		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_link_page, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		if(intent!=null){
			unregisterReceiver(broadcastReceiver);
			stopService(intent);
		}
		super.onDestroy();
	}
	
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			MainLinkPage.this.getLocationService(intent);
		}
		
	};
	public void getLocationService(Intent intent) {
		final double lat = intent.getDoubleExtra("Latitude", 0.00);
		final double lng = intent.getDoubleExtra("Longitude", 0.00);
		final String provider = intent.getStringExtra("Provider");
		polledLocation = new Location(provider);
		polledLocation.setLatitude(lat);
		polledLocation.setLongitude(lng);
		Toast.makeText(this, "Lat: "+ polledLocation.getLatitude() + "\nLong: " +polledLocation.getLongitude(), Toast.LENGTH_LONG).show();
	}
	
	private void startService() {
		intent = new Intent(this, LocationService.class);
		startService(intent);
		registerReceiver(broadcastReceiver, new IntentFilter(LocationService.BROADCAST_ACTION));
	}
}
