package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;
import java.util.List;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.model.DrawerItem;
import com.example.it3197_casestudy.util.CustomDrawerAdapter;
import com.example.it3197_casestudy.util.viewHobbiesAdapter;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ViewHobbiesMain extends FragmentActivity implements
		ActionBar.TabListener {

	ViewPager ViewPager;
	viewHobbiesAdapter viewHobbies;

	private String[] mNavigationDrawerItemTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private DrawerItem[] drawerItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_events);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// Show the Up button in the action bar.
		actionBar.setDisplayHomeAsUpEnabled(true);

		viewHobbies = new viewHobbiesAdapter(getSupportFragmentManager());

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
		mNavigationDrawerItemTitles = getResources().getStringArray(
				R.array.navgigation_drawer_items_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		drawerItem = new DrawerItem[3];
		drawerItem[0] = new DrawerItem(R.drawable.ic_launcher);
		drawerItem[1] = new DrawerItem(R.drawable.article);
		drawerItem[2] = new DrawerItem(R.drawable.events);

		CustomDrawerAdapter adapter = new CustomDrawerAdapter(this,
				R.layout.list_hobbie, drawerItem);

		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}

			private void SelectItem(int position) {
				Fragment fragment = null;
				switch (position) {
				case 0:
					fragment = new CreateFragment();
					break;
				case 1 :
					fragment = new ReadFragment();
					break;
				case 2 :
					fragment = new HelpFragment();
					break;
				default:
					break;
				}
				
				if(fragment != null){
					android.app.FragmentManager fm = getFragmentManager();
					
				}
			}
		});
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
		case android.R.id.home:

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
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

}
