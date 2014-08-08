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
import com.google.android.gms.maps.model.LatLng;
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
		
		tv_username = (TextView) findViewById(R.id.tv_username);
		tv_points = (TextView) findViewById(R.id.tv_points);
		tv_address = (TextView) findViewById(R.id.tv_address);

		//if(!intent.getStringExtra("GPSstatus").equals("disabled")) {
			registerReceiver(broadcastReceiver, new IntentFilter(LocationService.BROADCAST_ACTION));
			Toast.makeText(getApplicationContext(), "Retrieving current location...", Toast.LENGTH_LONG).show();
			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		//}
		
		tv_username.setText(user.getName());
		tv_points.setText(Integer.toString(user.getPoints()));
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
			location =  new LatLng(1.4327564, 103.83992660000001); 
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
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
		for(int i = 0; i < locationList.size(); i++) {
			map.addMarker(new MarkerOptions().position(locationList.get(i)));
			
			/*if(locationList.size() > 1 && i > 0) {
				String url = makeURL(locationList.get(i-1).latitude, locationList.get(i-1).longitude, locationList.get(i).latitude, locationList.get(i).longitude);
				JSONParser jParser = new JSONParser();
				String json = jParser.getJSONFromUrl(url);
				DrawPath drawPath = new DrawPath(intent, ProfileActivity.this, map, json);
				drawPath.execute();
			}*/
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