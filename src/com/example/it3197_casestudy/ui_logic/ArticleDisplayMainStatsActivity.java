package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
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

public class ArticleDisplayMainStatsActivity extends Activity {

	GoogleMap map;
	
	MarkerOptions mp = new MarkerOptions();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_display_main_stats);
		
		
		
		
		 Bundle extras = this.getIntent().getExtras();
		 ArrayList<Double> artLatitude = (ArrayList<Double>) getIntent().getSerializableExtra("artLatitude");
		 ArrayList<Double> artLongitude = (ArrayList<Double>) getIntent().getSerializableExtra("artLongitude");
		 int noOfArticles = extras.getInt("noOfArticles");
		 int selectedDistance = extras.getInt("distanceSelected");
		 double currentLatitude = extras.getDouble("currentLatitude");
		 double currentLongitude = extras.getDouble("currentLongitude");
		 
		 
		 getActionBar().setTitle("Article Locations");
			
			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();		
			//map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3667, 103.8), 10));
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			//map.setMapType(GoogleMap.MAP_TYPE_NONE);
			//map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			//map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			//map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);		  
		 
			mp.position(new LatLng(currentLatitude, currentLongitude));
			mp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
			mp.title("You are here");
			map.addMarker(mp).showInfoWindow();
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 12));

			map.addCircle(new CircleOptions()
	        .center(new LatLng(currentLatitude, currentLongitude))
	        .radius(selectedDistance*1000)
	        .strokeColor(Color.BLUE)
	        .fillColor(0x220000FF));
			
		for(int i=0; i<noOfArticles;i++){
			map.addMarker(new MarkerOptions()
	        	.position(new LatLng(artLatitude.get(i), artLongitude.get(i))));
		}
		
	}
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.article_display_main_stats, menu);
		return true;
	}
*/
}
