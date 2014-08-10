package com.example.it3197_casestudy.ui_logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.model.User;
import com.example.it3197_casestudy.util.LocationService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class ProfileActivity extends FragmentActivity {
	TextView tv_username, tv_points, tv_address;
	GoogleMap map;
	LatLng location;
	
	Bundle data;
	User user;
	Intent intent;
	Location polledLocation;
	ArrayList<LatLng> locationList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		data = getIntent().getExtras();
		user = data.getParcelable("user");
		
		intent = new Intent(ProfileActivity.this, LocationService.class);
		
		tv_username = (TextView) findViewById(R.id.tv_username);
		tv_points = (TextView) findViewById(R.id.tv_points);
		tv_address = (TextView) findViewById(R.id.tv_address);
		
		// Commended out for presentation purpose //
		//registerReceiver(broadcastReceiver, new IntentFilter(LocationService.BROADCAST_ACTION));
		Toast.makeText(getApplicationContext(), "Retrieving current location...", Toast.LENGTH_LONG).show();
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		tv_username.setText(user.getName());
		tv_points.setText(Integer.toString(user.getPoints()));
		
		/************* (Start) For presentation purpose ********************/
		location = new LatLng(1.379348000000000000, 103.849876000000000000);
		polledLocation = new Location("dummyprovider");
		polledLocation.setLatitude(location.latitude);
		polledLocation.setLongitude(location.longitude);
		// Get display current location
		getAddress(polledLocation);
		// Get current location coordinates (lat & lng)
		location = new LatLng(polledLocation.getLatitude(), polledLocation.getLongitude());
		// Get all the locations
		locationList = new ArrayList<LatLng>();
		
		map.clear();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));
		
		locationList.add(new LatLng(1.381905000000000000, 103.844818000000030000));
		locationList.add(new LatLng(1.380646900000000000, 103.846514099999920000));
		locationList.add(new LatLng(1.3796572, 103.84821290000002));
		locationList.add(new LatLng(1.379348000000000000, 103.849876000000000000));
		
		PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
		Marker marker;
		marker = map.addMarker(new MarkerOptions().position(locationList.get(0)).title("Start").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
		marker.showInfoWindow();
		options.add(locationList.get(0));
		for(int i = 1; i < locationList.size(); i++) {
			if(i != locationList.size() - 1) {
				map.addMarker(new MarkerOptions().position(locationList.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
			}
			else {
				Marker mark = map.addMarker(new MarkerOptions().position(locationList.get(i)).title("You are here"));
				mark.showInfoWindow();
			}
			options.add(locationList.get(i));
			map.addPolyline(options);
		}
		/************* (End) For presentation purpose **********************/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.profile, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_heatmap:
			if(location == null) {
				AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
				builder.setTitle("Unable to get current location").setMessage("Please try again.");
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				builder.create().show();
			}
			else {
				Intent heatMapIntent = new Intent(ProfileActivity.this, HeatMapActivity.class);
				heatMapIntent.putExtra("lat", location.latitude);
				heatMapIntent.putExtra("lng", location.longitude);
				startActivity(heatMapIntent);
			}
			break;
	}
		return super.onOptionsItemSelected(item);
	}

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			data = intent.getExtras();
			user = data.getParcelable("user");
			tv_points.setText(Integer.toString(user.getPoints()));
			ProfileActivity.this.getLocationService(intent);
		}
	};
	
	public void getLocationService(Intent intent) {
		final double lat = intent.getDoubleExtra("Latitude", 0.00);
		final double lng = intent.getDoubleExtra("Longitude", 0.00);
		final String provider = intent.getStringExtra("Provider");
		polledLocation = new Location(provider);
		polledLocation.setLatitude(lat);
		polledLocation.setLongitude(lng);
		
		// Get display current location
		getAddress(polledLocation);
		// Get current location coordinates (lat & lng)
		location = new LatLng(polledLocation.getLatitude(), polledLocation.getLongitude());
		// Get all the locations
		locationList = intent.getParcelableArrayListExtra("LocationList");
		
		map.clear();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));
		
		PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
		Marker marker;
		marker = map.addMarker(new MarkerOptions().position(locationList.get(0)).title("Start").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
		marker.showInfoWindow();
		options.add(locationList.get(0));
		for(int i = 1; i < locationList.size(); i++) {
			if(i != locationList.size() - 1) {
				map.addMarker(new MarkerOptions().position(locationList.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
			}
			else {
				Marker mark = map.addMarker(new MarkerOptions().position(locationList.get(i)).title("You are here"));
				mark.showInfoWindow();
			}
			options.add(locationList.get(i));
			map.addPolyline(options);
		}
	}
	
	public void getAddress(Location location) {
		String addressString = "";
		
		Geocoder geocode = new Geocoder(this, Locale.getDefault());
		try {
			List<Address> addresses = geocode.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
			Address address = addresses.get(0);			
			addressString = address.getAddressLine(0).toString();
			tv_address.setText(addressString);
		}
		catch (IOException e) {
		}
	}
	
	// Commended out for presentation purpose //
	/*
	@Override
	protected void onDestroy() {
		if(intent!=null){
			unregisterReceiver(broadcastReceiver);
		}
		super.onDestroy();
	}
	*/
}