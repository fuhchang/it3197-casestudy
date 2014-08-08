package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.RetrieveAllUserLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class HeatMapActivity extends FragmentActivity {
	GoogleMap map;
	
	Bundle data;
	LatLng currentLocation;
	ArrayList<LatLng> locationList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_heatmap);
		
		data = getIntent().getExtras();
		currentLocation = new LatLng(data.getDouble("lat"), data.getDouble("lng"));
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
		map.addMarker(new MarkerOptions().position(currentLocation));
		
		RetrieveAllUserLocation retrieveAllUserLocation = new RetrieveAllUserLocation(this, map);
		retrieveAllUserLocation.execute();
	}
}
