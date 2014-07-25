package com.example.it3197_casestudy.ui_logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.id;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.controller.MainPageController;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getActionBar().setTitle("Home");
		
		
		TextView latest = (TextView)findViewById(R.id.current);
		TextView latestDate = (TextView)findViewById(R.id.currentDateTime);
		
		Calendar c = Calendar.getInstance();
      	SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMMM yyyy");
      	String strDate = sdf.format(c.getTime());
		latest.setText("Today's Latest");
		latestDate.setText(strDate);
		
		list = (ListView) findViewById(R.id.mainListView);
		//list.setBackgroundColor(Color.WHITE);
		MainPageController mpc = new MainPageController(this, list);
		mpc.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
