package com.example.it3197_casestudy.ui_logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.RetrieveAllUserLocation;
import com.example.it3197_casestudy.util.LocationService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HeatMapActivity extends FragmentActivity {
	GoogleMap map;
	
	Bundle data;
	LatLng currentLocation;
	ArrayList<LatLng> locationList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_heatmap);
		
	    Calendar cal = Calendar.getInstance();
	    ActionBar ab = getActionBar();
	    ab.setSubtitle(new SimpleDateFormat("MMM", Locale.ENGLISH).format(cal.getTime())); 
		
		data = getIntent().getExtras();
		currentLocation = new LatLng(data.getDouble("lat"), data.getDouble("lng"));
		// Commended out for presentation purpose //
		//registerReceiver(broadcastReceiver, new IntentFilter(LocationService.BROADCAST_ACTION));
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
		map.addMarker(new MarkerOptions().position(currentLocation).title("You are here")).showInfoWindow();

		/************* (Start) For presentation purpose ********************/
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, map.getCameraPosition().zoom));
		/************* (End) For presentation purpose **********************/
		
		RetrieveAllUserLocation retrieveAllUserLocation = new RetrieveAllUserLocation(this, map);
		retrieveAllUserLocation.execute();
	}

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			HeatMapActivity.this.getLocationService(intent);
		}
	};
	
	public void getLocationService(Intent intent) {
		final double lat = intent.getDoubleExtra("Latitude", 0.00);
		final double lng = intent.getDoubleExtra("Longitude", 0.00);
		currentLocation = new LatLng(lat, lng);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, map.getCameraPosition().zoom));
	}
	
	// Commended out for presentation purpose //
	/*
	@Override
	protected void onDestroy() {
		unregisterReceiver(broadcastReceiver);
		super.onDestroy();
	}
	*/
}
