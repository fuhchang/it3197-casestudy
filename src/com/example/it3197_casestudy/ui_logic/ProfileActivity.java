package com.example.it3197_casestudy.ui_logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
		intent.putExtra("user", user);
		registerReceiver(broadcastReceiver, new IntentFilter(LocationService.BROADCAST_ACTION));
		
		tv_username = (TextView) findViewById(R.id.tv_username);
		tv_points = (TextView) findViewById(R.id.tv_points);
		tv_address = (TextView) findViewById(R.id.tv_address);
		
		Toast.makeText(getApplicationContext(), "Retrieving current location...", Toast.LENGTH_LONG).show();
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		tv_username.setText(user.getName());
		tv_points.setText(Integer.toString(user.getPoints()));
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
		// Get all the coordinates
		locationList = intent.getParcelableArrayListExtra("LocationList");
		
		map.clear();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
		for(int i = 0; i < locationList.size(); i++) {
			map.addMarker(new MarkerOptions().position(locationList.get(i)));
		}
		
		// Temp
		/*map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
		LatLng yck = new LatLng(1.381905000000000000, 103.844818000000030000);
		LatLng cbgc = new LatLng(1.380646900000000000, 103.846514099999920000);
		LatLng lvw = new LatLng(1.3796572, 103.84821290000002);
		LatLng nyp = new LatLng(1.379348000000000000, 103.849876000000000000);
		map.addMarker(new MarkerOptions().position(yck).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
		map.addMarker(new MarkerOptions().position(cbgc).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		map.addMarker(new MarkerOptions().position(lvw).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		map.addMarker(new MarkerOptions().position(nyp));*/
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
}