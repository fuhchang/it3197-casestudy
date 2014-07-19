package com.example.it3197_casestudy;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.widget.Toast;

public class FeedbackArticleStatsActivity extends Activity {

	GoogleMap map;

	MarkerOptions mp = new MarkerOptions();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback_article_stats);
		
		
		
		
		 Bundle extras = this.getIntent().getExtras();
		 ArrayList<Double> feedbackLatitude = (ArrayList<Double>) getIntent().getSerializableExtra("feedbackLatitude");
		 ArrayList<Double> feedbackLongitude = (ArrayList<Double>) getIntent().getSerializableExtra("feedbackLongitude");
		 ArrayList<Double> locationLatitude = (ArrayList<Double>) getIntent().getSerializableExtra("locationLatitude");
		 ArrayList<Double> locationLongitude = (ArrayList<Double>) getIntent().getSerializableExtra("locationLongitude");
		 int noOfFeedbacks = extras.getInt("noOfFeedbacks");
		 int noOfLocationReq = extras.getInt("noOfLocationReq");
		 int selectedDistance = extras.getInt("distanceSelected");
		 double currentLatitude = extras.getDouble("currentLatitude");
		 double currentLongitude = extras.getDouble("currentLongitude");
		 ArrayList<String> feedbackTitle = (ArrayList<String>)getIntent().getSerializableExtra("feedbackTitle");
		 ArrayList<String> locTitle = (ArrayList<String>)getIntent().getSerializableExtra("locTitle");
		 
		// Toast.makeText(getApplicationContext(), String.valueOf(locationLatitude.size()), Toast.LENGTH_SHORT).show();
		// Toast.makeText(getApplicationContext(), String.valueOf(feedbackLatitude.size()), Toast.LENGTH_SHORT).show();
		 
		 getActionBar().setTitle("Article Locations");
		
		 map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();		
			//map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3667, 103.8), 10));
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			//map.setMapType(GoogleMap.MAP_TYPE_NONE);
			//map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			//map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			//map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);		  
		 
			mp.position(new LatLng(currentLatitude, currentLongitude));
			mp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			mp.title("You are here");
			map.addMarker(mp).showInfoWindow();
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 12));

			map.addCircle(new CircleOptions()
	        .center(new LatLng(currentLatitude, currentLongitude))
	        .radius(selectedDistance*1000)
	        .strokeColor(Color.BLUE)
	        .fillColor(0x220000FF));
			
			
			for(int i=0; i<noOfFeedbacks;i++){
				map.addMarker(new MarkerOptions()
		        	.position(new LatLng(feedbackLatitude.get(i), feedbackLongitude.get(i)))
					.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
					.title(feedbackTitle.get(i)));
					
			}
			
			for(int i=0; i<noOfLocationReq;i++){
				map.addMarker(new MarkerOptions()
		        	.position(new LatLng(locationLatitude.get(i), locationLongitude.get(i)))
					.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
					.title(locTitle.get(i)));
			}
		
	}
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feedback_article_stats, menu);
		return true;
	}*/

}
