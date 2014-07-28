package com.example.it3197_casestudy.ui_logic;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetAllEvents;
import com.example.it3197_casestudy.util.MySharedPreferences;
import com.example.it3197_casestudy.util.PullToRefreshListView;
import com.example.it3197_casestudy.util.PullToRefreshListView.OnRefreshListener;

import android.os.Bundle;
import android.os.Handler;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
public class ViewAllEventsActivity extends ListActivity {
	PullToRefreshListView lvViewAllEvents;
	//SwipeRefreshLayout swipeLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_all_events);

		/*lvViewAllEvents = (ListView) findViewById(R.id.lv_view_all_events);
		
		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener((OnRefreshListener) this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright, 
                android.R.color.holo_green_light, 
                android.R.color.holo_orange_light, 
                android.R.color.holo_red_light);*/
		lvViewAllEvents = (PullToRefreshListView) getListView();
		((PullToRefreshListView) lvViewAllEvents).setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
        		GetAllEvents getAllEvents = new GetAllEvents(ViewAllEventsActivity.this,lvViewAllEvents);
        		getAllEvents.execute();
            }
        });
		
		GetAllEvents getAllEvents = new GetAllEvents(ViewAllEventsActivity.this,lvViewAllEvents);
		getAllEvents.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_all_events_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.create_event:
				Intent intent = new Intent(ViewAllEventsActivity.this, CreateEventStep1Activity.class);
    			startActivity(intent);
    			finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//@Override 
	/*public void onBackPressed(){  
		Intent intent = new Intent(ViewAllEventsActivity.this, MainLinkPage.class);
		MySharedPreferences preferences = new MySharedPreferences(this);
		String userName = preferences.getPreferences("username", "");
		intent.putExtra("username", userName);
		startActivity(intent);
		this.finish();
	}*/

}
