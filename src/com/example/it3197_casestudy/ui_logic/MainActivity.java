package com.example.it3197_casestudy.ui_logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.id;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.controller.MainPageController;
import com.example.it3197_casestudy.model.User;
import com.example.it3197_casestudy.util.LocationService;
import com.facebook.Session;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	ListView list;
	
	Bundle data;
	User user;
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getActionBar().setTitle("Home");
		
		data = getIntent().getExtras();
		user = data.getParcelable("user");		
		startService();
		
		TextView latest = (TextView)findViewById(R.id.current);
		TextView latestDate = (TextView)findViewById(R.id.currentDateTime);
		
		Calendar c = Calendar.getInstance();
      	SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMMM yyyy");
      	String strDate = sdf.format(c.getTime());
		latest.setText("Today's Latest");
		latestDate.setText(strDate);
		
		list = (ListView) findViewById(R.id.mainListView);
		//list.setBackgroundColor(Color.WHITE);
		MainPageController mpc = new MainPageController(this, list, user);
		mpc.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		int id = item.getItemId();
		
		if(id==R.id.logout){
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		      alertDialogBuilder.setMessage("Logout?");
		      alertDialogBuilder.setNegativeButton("Yes", 
		      new DialogInterface.OnClickListener() {
				
		         @Override
		         public void onClick(DialogInterface arg0, int arg1) {
		        	Intent i;
		        	if(Session.getActiveSession() != null){
			        	i = new Intent(MainActivity.this,LoginSelectionActivity.class);
			        	Session.getActiveSession().closeAndClearTokenInformation();
		        	}
		        	else{
		        		i = new Intent(MainActivity.this,LoginActivity.class);
		        	}
	        		startActivity(i);
	        		MainActivity.this.finish();
		         }
		      });
		      alertDialogBuilder.setPositiveButton("No", 
		      new DialogInterface.OnClickListener() {
					
		         @Override
		         public void onClick(DialogInterface dialog, int which) {
		            dialog.cancel();
				 }
		      });
			    
		      AlertDialog alertDialog = alertDialogBuilder.create();
		      alertDialog.show();
		}
		if(id==R.id.refresh){
			MainPageController mpc = new MainPageController(this, list, user);
			mpc.execute();
		}
		
		if(id==R.id.article_main){
			Intent intent = new Intent(MainActivity.this, ArticleMainActivity.class);
			startActivity(intent);
		}
		if(id==R.id.events_main){
			Intent intent = new Intent(MainActivity.this, ViewAllEventsActivity.class);
			startActivity(intent);
		}
		
		if(id==R.id.profile){
			Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
			intent.putExtra("user", user);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	      alertDialogBuilder.setMessage("Logout?");
	      alertDialogBuilder.setNegativeButton("Yes", 
	      new DialogInterface.OnClickListener() {
			
	         @Override
	         public void onClick(DialogInterface arg0, int arg1) {
	        	Intent i;
	        	if(Session.getActiveSession() != null){
		        	i = new Intent(MainActivity.this,LoginSelectionActivity.class);
		        	Session.getActiveSession().closeAndClearTokenInformation();
	        	}
	        	else{
	        		i = new Intent(MainActivity.this,LoginActivity.class);
	        	}
      		startActivity(i);
      		MainActivity.this.finish();
	         }
	      });
	      alertDialogBuilder.setPositiveButton("No", 
	      new DialogInterface.OnClickListener() {
				
	         @Override
	         public void onClick(DialogInterface dialog, int which) {
	            dialog.cancel();
			 }
	      });
		    
	      AlertDialog alertDialog = alertDialogBuilder.create();
	      alertDialog.show();		
	}
	
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			/*if(intent.getStringExtra("GPSstatus").equals("disabled")) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("GPS disabled").setMessage("Enable GPS setting to earn points while travelling");
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);  
						startActivity(gpsOptionsIntent);
					}
				});
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				builder.create().show();
			}*/
		}
	};
	
	private void startService() {
		intent = new Intent(this, LocationService.class);
		intent.putExtra("user", user);
		startService(intent);
		
		registerReceiver(broadcastReceiver, new IntentFilter(LocationService.BROADCAST_ACTION));
	}

	@Override
	protected void onDestroy() {
		if(intent!=null){
			unregisterReceiver(broadcastReceiver);
			stopService(intent);
		}
		super.onDestroy();
	}
}
