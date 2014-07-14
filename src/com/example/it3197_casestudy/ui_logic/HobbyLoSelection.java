package com.example.it3197_casestudy.ui_logic;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.id;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HobbyLoSelection extends Activity implements LocationListener, OnMarkerClickListener, OnMarkerDragListener{

	GoogleMap map;
	
	double lat;
	double lon;
	String Address;
	String City;
	String Lat;
	 String Lng;
	private static LatLng fromPosition = null;
    private static LatLng toPosition = null;

    Location location; 
    private boolean gps_enabled=false;
	private boolean network_enabled=false;
	LocationManager lm;
	
	EditText et;
	TextView storingLoc;
	
	String locToBeStored = "";

    MarkerOptions mp = new MarkerOptions();
    
    Button done, cancel;
	
    double finalizedLat;
    double finalizedLon;
    
    
    @SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hobby_lo_selection);
		
		getActionBar().setTitle("Location Seletor");
		
		
			storingLoc = (TextView)findViewById(R.id.storingLoc);
		
		
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();		
		
				  
			  map.setOnMarkerDragListener(this);
			  Lat = getIntent().getStringExtra("lat");
			  Lng = getIntent().getStringExtra("Lng");
			  Toast.makeText(getApplicationContext(), Lat, Toast.LENGTH_LONG).show();
			  Toast.makeText(getApplicationContext(), Lng, Toast.LENGTH_LONG).show();
			  convertToAddress(Lat,Lng);
			
			  
			  
			  
			  
			  Button btn = (Button) findViewById(R.id.btn);
			  btn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					et = (EditText) findViewById(R.id.addressET);					
						enteredLoc();
				}
			  });
			  
			  
			  done = (Button) findViewById(R.id.done);
			  done.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent output = new Intent();
					   output.putExtra("selectedAdd", locToBeStored);
					   output.putExtra("selectedLat", String.valueOf(finalizedLat));
					   output.putExtra("selectedLon", String.valueOf(finalizedLon));
					   setResult(RESULT_OK, output);
					 finish();
				}
				  
			  });
			  
			  cancel = (Button) findViewById(R.id.cancel);
			  cancel.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
				  
			  });
	}

    public void convertToAddress(String Lat, String Lng){
    	
		  
    	Toast.makeText(getApplicationContext(), Lat, Toast.LENGTH_LONG).show();
    	Toast.makeText(getApplicationContext(), Lng, Toast.LENGTH_LONG).show();
    	 try
	       {
	   	   //Getting address based on coordinates.
	       Geocoder geocoder;  
	       List<Address> addresses;
	       geocoder = new Geocoder(this, Locale.getDefault());
	       addresses = geocoder.getFromLocation(Double.parseDouble(Lat), Double.parseDouble(Lng), 1);
	 
	        Address = addresses.get(0).getAddressLine(0);
	        City = addresses.get(0).getAddressLine(1);
	       }
	       catch (Exception e)
	       {
	           e.printStackTrace();
	       }

	      if (Address != null && !Address.isEmpty()) {
	    	  mp.position(new LatLng(Double.parseDouble(Lat), Double.parseDouble(Lng)));
	    	 // mp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
		   	   try
		   	      {
		   	  	   //Getting address based on coordinates.
		   	      Geocoder geocoder;  
		   	      List<Address> addresses;
		   	      geocoder = new Geocoder(this, Locale.getDefault());
		   	      addresses = geocoder.getFromLocation(Double.parseDouble(Lat), Double.parseDouble(Lng), 1);
		   	
		   	       Address = addresses.get(0).getAddressLine(0);
		   	       City = addresses.get(0).getAddressLine(1);
		   	      }
		   	      catch (Exception e)
		   	      {
		   	          e.printStackTrace();
		   	      }
		   	   
		   	   mp.title(Address + ", " + City);
		   	   
		   	   
		   	   locToBeStored=Address + "\n" + City;
		   	   updateLoc(locToBeStored);
		   	   
		   	   finalizedLat=Double.parseDouble(Lat);
		   	   finalizedLon=Double.parseDouble(Lng);
		   	   mp.draggable(true);
		   	   map.addMarker(mp).showInfoWindow();
	
		   	   map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(Lat), Double.parseDouble(Lng)), 16));
	      }
    }
    
	@Override
	public void onMarkerDrag(Marker mp) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onMarkerDragEnd(Marker mp) {
		toPosition = mp.getPosition();
	    
		 //   lm.removeUpdates(this);
		    
		   // Toast.makeText(getApplicationContext(),"Marker " + mp.getTitle() + " dragged from " + fromPosition+ " to " + toPosition, Toast.LENGTH_LONG).show();  
		    try
		      {
		  	   //Getting address based on coordinates.
		      Geocoder geocoder;  
		      List<Address> addresses;
		      geocoder = new Geocoder(this, Locale.getDefault());
		      addresses = geocoder.getFromLocation(toPosition.latitude, toPosition.longitude,1);
		
		       Address = addresses.get(0).getAddressLine(0);
		       City = addresses.get(0).getAddressLine(1);
		    	   mp.setTitle(addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getAddressLine(1));
			       mp.showInfoWindow();
		       locToBeStored=Address + "\n" + City;
		   	   updateLoc(locToBeStored);
		   	   finalizedLat = toPosition.latitude;
		   	   finalizedLon=toPosition.longitude;
		
		      }
		      catch (Exception e)
		      {
		          e.printStackTrace();
		      }
		
	}


	@Override
	public void onMarkerDragStart(Marker mp) {
		 fromPosition = mp.getPosition();	
	}


	@Override
	public boolean onMarkerClick(Marker mp) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
	   public void enteredLoc(){
		   
		   String enteredAddress = et.getText().toString();
		   Geocoder gc = new Geocoder(this);

		   if(gc.isPresent()){
		     List<Address> list = null;
			try {
				list = gc.getFromLocationName(enteredAddress, 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(list.size()>0){
				   Address address = list.get(0);
				   double lat = address.getLatitude();
				   double lng = address.getLongitude();
				   map.clear();
				
				 try
		   	      {
		   	  	   //Getting address based on coordinates.
		   	      Geocoder geocoder;  
		   	      List<Address> addresses;
		   	      geocoder = new Geocoder(this, Locale.getDefault());
		   	      addresses = geocoder.getFromLocation(lat, lng, 1);
		   	
		   	       Address = addresses.get(0).getAddressLine(0);
		   	       City = addresses.get(0).getAddressLine(1);
		   	      }
		   	      catch (Exception e)
		   	      {
		   	          e.printStackTrace();
		   	      }
		   	    
				 mp.position(new LatLng(lat, lng));
				    mp.title(Address + ", " + City);
				    mp.draggable(true);
				   	map.addMarker(mp).showInfoWindow();
					map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(), address.getLongitude()), 16));
				
				
				locToBeStored = Address + "\n" + City ;
				updateLoc(locToBeStored);
				finalizedLat=lat;
			    finalizedLon=lng;
			   }
			     else{
			    	 Toast.makeText(getApplicationContext(), "No location matches " + enteredAddress + "." , Toast.LENGTH_LONG).show();
			     }
		   }
	   }
	   
	   
	   public void updateLoc(String loc){
		   storingLoc.setText(loc);
		   
	   }
	   

}
