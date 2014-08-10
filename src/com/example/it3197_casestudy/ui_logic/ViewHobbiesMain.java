package com.example.it3197_casestudy.ui_logic;


import com.example.it3197_casestudy.R;

import com.example.it3197_casestudy.ar.activity.Demo;
import com.example.it3197_casestudy.controller.GetHobbyForMap;
import com.example.it3197_casestudy.util.viewHobbiesAdapter;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class ViewHobbiesMain extends FragmentActivity implements
		ActionBar.TabListener {

	ViewPager ViewPager;
	viewHobbiesAdapter viewHobbies;
	Button btnNewGrp;
	String nric;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_events);
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		nric = sp.getString("nric","");
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// Show the Up button in the action bar.
		//actionBar.setDisplayHomeAsUpEnabled(true);

		viewHobbies = new viewHobbiesAdapter(getSupportFragmentManager(), nric);

		// Set up the ViewPager with the sections adapter.
		ViewPager = (ViewPager) findViewById(R.id.pager);
		ViewPager.setAdapter(viewHobbies);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		ViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});
		actionBar.addTab(actionBar.newTab().setText("Joined")
				.setTabListener(this));
		actionBar
				.addTab(actionBar.newTab().setText("All").setTabListener(this));
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_hobbies_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_searchByGeoFencing:
			GetHobbyForMap getHobby = new GetHobbyForMap(this);
			getHobby.execute();
			break;
		case R.id.new_group:
			Intent intentNewGrp = new Intent(this, CreateGroupActivityStep1.class);
			intentNewGrp.putExtra("nric", nric);
			startActivity(intentNewGrp);
			break;
		default:
			
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		ViewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction) {
	}
	
}
