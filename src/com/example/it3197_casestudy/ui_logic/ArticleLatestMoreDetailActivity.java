package com.example.it3197_casestudy.ui_logic;

import java.util.List;
import java.util.Locale;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ArticleLatestMoreDetailActivity extends Activity {

	GoogleMap map;
	
	double lat;
	double lon;
	String Address;
	String City;
	
	Location location; 
    private boolean gps_enabled=false;
	private boolean network_enabled=false;
	LocationManager lm;
	
	MarkerOptions mp = new MarkerOptions();
	MarkerOptions artLoc = new MarkerOptions();
	
	TextView addTv;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_latest_more_detail);
		
		getActionBar().setTitle("");
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();		
		//map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3667, 103.8), 10));
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		//map.setMapType(GoogleMap.MAP_TYPE_NONE);
		//map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		//map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		//map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);		  
		  
		//map.setTrafficEnabled(true);
		  
		//map.setMyLocationEnabled(true);	  
		  getMyCurrentLocation();
		  
		  addTv = (TextView) findViewById(R.id.add);
		  
		  Bundle extras = this.getIntent().getExtras();
		  String address = extras.getString("articleLoc");
		  addTv.setText(address);
		  //convertToAddress();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.article_latest_more_detail, menu);
		return true;
	}
	
	
	
	/** Check the type of GPS Provider available at that instance and  collect the location informations**/
		void getMyCurrentLocation() {    
		   
		   
	   	  // map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 16));
		   
	       LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	       try{
	       		gps_enabled=locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	       }catch(Exception ex){
	       	
	       }
	       try{
	       	network_enabled=locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	       }catch(Exception ex){
	       	
	       }

	       if(gps_enabled){
	           location=locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

	       }          

	       
	       if(network_enabled && location==null)    {
	           location=locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

	       }

		   if (location != null) {          
		   	//accLoc=location.getAccuracy();
		       lat = location.getLatitude();
			   lon= location.getLongitude();
		   } 
	   
	      try
	       {
	   	   //Getting address based on coordinates.
	       Geocoder geocoder;  
	       List<Address> addresses;
	       geocoder = new Geocoder(this, Locale.getDefault());
	       addresses = geocoder.getFromLocation(lat, lon, 1);
	 
	        Address = addresses.get(0).getAddressLine(0);
	        City = addresses.get(0).getAddressLine(1);
	       }
	       catch (Exception e)
	       {
	           e.printStackTrace();
	       }

	      if (Address != null && !Address.isEmpty()) {
		   	   try
		   	      {
		   	  	   //Getting address based on coordinates.
		   	      Geocoder geocoder;  
		   	      List<Address> addresses;
		   	      geocoder = new Geocoder(this, Locale.getDefault());
		   	      addresses = geocoder.getFromLocation(lat, lon, 1);
		   	
		   	       Address = addresses.get(0).getAddressLine(0);
		   	       City = addresses.get(0).getAddressLine(1);
		   	      }
		   	      catch (Exception e)
		   	      {
		   	          e.printStackTrace();
		   	      }
		   	   //mp.position(new LatLng(location.getLatitude(), location.getLongitude()));
		   	   //mp.title("You are here");		   	 
		   	   //map.addMarker(mp).showInfoWindow();	
		   	   //map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3667, 103.8), 10));
	      }
	      else{ 
	         AlertDialog.Builder builder1 = new AlertDialog.Builder(ArticleLatestMoreDetailActivity.this);
	         builder1.setTitle("Service Unavailable");
	   		 builder1.setMessage("Unable to get your location, check if your GPS and Network are turned on.");
	   		 builder1.setCancelable(true);
	         builder1.setNegativeButton("OK",new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   dialog.cancel();
	               }
	           });
	           AlertDialog alert11 = builder1.create();
	           alert11.show();
	           
	           lat=1.3667;
	           lon=103.8;      
	         //  mp.position(new LatLng(1.3667, 103.8));
		     //  mp.draggable(true);
		   	  // map.addMarker(mp).showInfoWindow();
		   	   
		   	 //map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3667, 103.8), 10));
	      }
	      
	      
	      
	       Bundle extras = this.getIntent().getExtras();
		   double dbLat = extras.getDouble("dbLat");
		   double dbLon = extras.getDouble("dbLon");
		   artLoc.position(new LatLng(dbLat, dbLon));
		   artLoc.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		   artLoc.title("Place of occurrence");
		   map.addMarker(artLoc).showInfoWindow();
		   map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dbLat, dbLon), 18));
	      
	      
	      
	   }

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		
		int id = item.getItemId();
		
		if(id==R.id.close){
		//	Intent intent = new Intent(ArticleLatestMoreDetailActivity.this, ArticleMainActivity.class);
		//	startActivity(intent);
			ArticleLatestMoreDetailActivity.this.finish();
		}
		
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//Intent intent = new Intent(ArticleLatestMoreDetailActivity.this, ArticleMainActivity.class);
		//startActivity(intent);
		ArticleLatestMoreDetailActivity.this.finish();
		super.onBackPressed();
	}

}
