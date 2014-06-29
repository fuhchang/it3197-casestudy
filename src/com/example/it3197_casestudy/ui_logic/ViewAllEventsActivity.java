package com.example.it3197_casestudy.ui_logic;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.id;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.util.EventListAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ViewAllEventsActivity extends Activity {
	ListView lvViewAllEvents;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_all_events);
		
	    String[] values = { "Android", "iPhone", "WindowsMobile",
	            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
	            "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
	            "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
	            "Android", "iPhone", "WindowsMobile" };
	    String[] values1 = new String[] { "Android", "iPhone", "WindowsMobile",
	            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
	            "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
	            "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
	            "Android", "iPhone", "WindowsMobile" };

		lvViewAllEvents = (ListView) findViewById(R.id.lv_view_all_events);
		
		final EventListAdapter adapter = new EventListAdapter(this,values, values1);
		lvViewAllEvents.setAdapter(adapter);
		lvViewAllEvents.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int position,long arg3) {
				// TODO Auto-generated method stub
		        final String item = (String) parent.getItemAtPosition(position);
		        Log.i("Item: ",item);
		        
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_all_events, menu);
		return true;
	}

}
