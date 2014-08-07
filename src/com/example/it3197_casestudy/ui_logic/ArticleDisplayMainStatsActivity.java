package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;
import java.util.List;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.geofencing.GeofenceRequester;
import com.example.it3197_casestudy.geofencing.SimpleGeofence;
import com.example.it3197_casestudy.geofencing.SimpleGeofenceStore;
import com.google.android.gms.location.Geofence;
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
	
	private SimpleGeofenceStore mPrefs;
	private GeofenceRequester mGeofenceRequester;
	List<Geofence> mCurrentGeofences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_display_main_stats);
		
		mPrefs = new SimpleGeofenceStore(this);
		mGeofenceRequester = new GeofenceRequester(this);
		mCurrentGeofences = new ArrayList<Geofence>();
		
		
		 Bundle extras = this.getIntent().getExtras();
		 ArrayList<Double> artLatitude = (ArrayList<Double>) getIntent().getSerializableExtra("artLatitude");
		 ArrayList<Double> artLongitude = (ArrayList<Double>) getIntent().getSerializableExtra("artLongitude");
		 int noOfArticles = extras.getInt("noOfArticles");
		 int selectedDistance = extras.getInt("distanceSelected");
		 double currentLatitude = extras.getDouble("currentLatitude");
		 double currentLongitude = extras.getDouble("currentLongitude");
		 ArrayList<String> artTitle = (ArrayList<String>)getIntent().getSerializableExtra("artTitle");
		 
		 
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
			
		for(int i=0; i<noOfArticles;i++){
			map.addMarker(new MarkerOptions()
	        	.position(new LatLng(artLatitude.get(i), artLongitude.get(i)))
	        	.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
	        	.title(artTitle.get(i)));
			
			SimpleGeofence UiGeofence = new SimpleGeofence(artTitle.get(i),artLatitude.get(i), artLongitude.get(i), 1000,Geofence.NEVER_EXPIRE, Geofence.GEOFENCE_TRANSITION_ENTER);
			mPrefs.setGeofence(artTitle.get(i), UiGeofence);
			
			mCurrentGeofences.add(UiGeofence.toGeofence());
		}
		
		 try {
	            // Try to add geofences
	            mGeofenceRequester.addGeofences(mCurrentGeofences," Article(s) Found ","Article(s) found near you.",2);
	            
	        } catch (UnsupportedOperationException e) {
	            // Notify user that previous request hasn't finished.
	            Toast.makeText(this, R.string.add_geofences_already_requested_error,
	                        Toast.LENGTH_LONG).show();
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
