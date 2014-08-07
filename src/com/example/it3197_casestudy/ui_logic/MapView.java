package com.example.it3197_casestudy.ui_logic;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MapView extends Activity {
	GoogleMap map;
	double lat;
	double lng;
	String Address;
	String City;
	TextView addressTV;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_view);
		addressTV = (TextView) findViewById(R.id.LocAddress);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.gMap)).getMap();
		lat = getIntent().getExtras().getDouble("lat");
		lng =  getIntent().getExtras().getDouble("lng");
		getMyLocationAddress(lat, lng);
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		MarkerOptions mp = new MarkerOptions();
		mp.position(new LatLng(lat, lng));
		mp.title(Address + ", " + City);
		 map.addMarker(mp).showInfoWindow();
		 map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 16));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map_view, menu);
		return true;
	}
	
	
	public void getMyLocationAddress(double lat, double lng){
		
		 try {
			 Geocoder geocoder;  
	 	      List<Address> addresses;
	 	      geocoder = new Geocoder(this, Locale.getDefault());
			addresses = geocoder.getFromLocation(lat, lng, 1);
			Address = addresses.get(0).getAddressLine(0);
			City = addresses.get(0).getAddressLine(1);
			addressTV.setText(Address + " " + City);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}

}
