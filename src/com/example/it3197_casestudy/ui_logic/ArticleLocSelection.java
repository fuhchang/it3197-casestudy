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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ArticleLocSelection extends Activity implements LocationListener, OnMarkerClickListener, OnMarkerDragListener{

	GoogleMap map;
	
	double lat;
	double lon;
	String Address;
	String City;
	private static LatLng fromPosition = null;
    private static LatLng toPosition = null;

    Location location; 
    private boolean gps_enabled=false;
	private boolean network_enabled=false;
	LocationManager lm;
	
	EditText et;
	TextView storingLoc;
	
	String locToBeStored = "";
	String locToMain="";

    MarkerOptions mp = new MarkerOptions();
    
    Button done, cancel;
	
    double finalizedLat;
    double finalizedLon;
    
    
    @SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_loc_selection);
		
		getActionBar().setTitle("");
		
		
			storingLoc = (TextView)findViewById(R.id.storingLoc);
		
		
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();		
		//map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3667, 103.8), 10));
			  map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			//  map.setMapType(GoogleMap.MAP_TYPE_NONE);
			//    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			//  map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			//  map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);		  
			  map.setOnMarkerDragListener(this);
			  
			//  map.setTrafficEnabled(true);
			  
			  //map.setMyLocationEnabled(true);	  
			  
			  convertToAddress();
			  
			  
			  
			  
			  Button btn = (Button) findViewById(R.id.btn);
			  btn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					et = (EditText) findViewById(R.id.addressET);
					
						enteredLoc();
					
				}
			  });
			  
			  
	/*		  done = (Button) findViewById(R.id.done);
			  done.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent output = new Intent();
					   output.putExtra("locToMain", locToMain);
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
				  
			  });*/
			  
			  /****For constant updating of location****/
			//		 lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			//		   lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
			  
			  /*****Get location when entering app*****/
			  //getMyCurrentLocation();
	}

    public void convertToAddress(){
    	
    	Bundle extras = this.getIntent().getExtras();
		  String mainSubmitLat = extras.getString("mainSubmitLat");
		  String mainSubmitLon = extras.getString("mainSubmitLon");
		  
    	
    	 try
	       {
	   	   //Getting address based on coordinates.
	       Geocoder geocoder;  
	       List<Address> addresses;
	       geocoder = new Geocoder(this, Locale.getDefault());
	       addresses = geocoder.getFromLocation(Double.parseDouble(mainSubmitLat), Double.parseDouble(mainSubmitLon), 1);
	 
	        Address = addresses.get(0).getAddressLine(0);
	        City = addresses.get(0).getAddressLine(1);
	       }
	       catch (Exception e)
	       {
	           e.printStackTrace();
	       }

	      if (Address != null && !Address.isEmpty()) {
	    	  mp.position(new LatLng(Double.parseDouble(mainSubmitLat), Double.parseDouble(mainSubmitLon)));
	    	 // mp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
		   	   try
		   	      {
		   	  	   //Getting address based on coordinates.
		   	      Geocoder geocoder;  
		   	      List<Address> addresses;
		   	      geocoder = new Geocoder(this, Locale.getDefault());
		   	      addresses = geocoder.getFromLocation(Double.parseDouble(mainSubmitLat), Double.parseDouble(mainSubmitLon), 1);
		   	
		   	       Address = addresses.get(0).getAddressLine(0);
		   	       City = addresses.get(0).getAddressLine(1);
		   	      }
		   	      catch (Exception e)
		   	      {
		   	          e.printStackTrace();
		   	      }
		   	   
		   	   mp.title(Address + ", " + City);
		   	   
		   		locToMain=Address + ", " + City;
		   	  // locToBeStored=Address + "\n" + City + "\n(Coordinates: " + Double.parseDouble(mainSubmitLat) + ", " + Double.parseDouble(mainSubmitLon) + ")";
		   		locToBeStored=Address + ",\n" + City;
		   	   updateLoc(locToBeStored);
		   	   
		   	   finalizedLat=Double.parseDouble(mainSubmitLat);
		   	   finalizedLon=Double.parseDouble(mainSubmitLon);
		   	   
		   	   mp.snippet("(Co-ordinates: " + mainSubmitLat +", " + mainSubmitLon + ").");
		   	   mp.draggable(true);
		   	   map.addMarker(mp).showInfoWindow();
	
		   	   map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(mainSubmitLat), Double.parseDouble(mainSubmitLon)), 17));
	      }
    }
    
    
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.article_loc_selection, menu);
		return true;
	}
	
	
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		
		int id = item.getItemId();
		
		if(id==R.id.done){
			Intent output = new Intent();
			output.putExtra("locToMain", locToMain);
			output.putExtra("selectedAdd", locToBeStored);
			output.putExtra("selectedLat", String.valueOf(finalizedLat));
			output.putExtra("selectedLon", String.valueOf(finalizedLon));
			setResult(RESULT_OK, output);
			finish();
		}
		
		
		return super.onMenuItemSelected(featureId, item);
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
		       
		       
		       
		   //    if(City.contains("Singapore")){
		    	   
		    	   
		    	   mp.setTitle(addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getAddressLine(1));
			       mp.setSnippet("(Co-ordinates: " + toPosition.latitude + ", " + toPosition.longitude + ").");
			       mp.showInfoWindow();
			       locToMain = Address + ", " + City;
		     //  locToBeStored=Address + "\n" + City + "\n(Coordinates: " + toPosition.latitude + ", " + toPosition.longitude + ")";
			       locToBeStored=Address + ",\n" + City;
				   updateLoc(locToBeStored);
			   	   finalizedLat = toPosition.latitude;
			   	   finalizedLon=toPosition.longitude;
		    /*   }
		       else{
		    	   AlertDialog.Builder builder1 = new AlertDialog.Builder(ArticleLocSelection.this);
			         builder1.setTitle("Location Denied");
			   		 builder1.setMessage("This application only supports Singapore context.");
			   		 builder1.setCancelable(true);
			         builder1.setNegativeButton("OK",new DialogInterface.OnClickListener() {
			               public void onClick(DialogInterface dialog, int id) {
			            	   dialog.cancel();
			               }
			           });
			           AlertDialog alert11 = builder1.create();
			           alert11.show();
			           mp.setPosition(new LatLng(finalizedLat, finalizedLon));
			         
		       }*/
		      }
		      catch (Exception e)
		      {
		          e.printStackTrace();
		      }
		
	}


	@Override
	public void onMarkerDragStart(Marker mp) {
		 fromPosition = mp.getPosition();
		  //  Log.d(getClass().getSimpleName(), "Drag start at: " + fromPosition);
		
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
	
	
	
	/** Check the type of GPS Provider available at that instance and  collect the location informations**/
	/*   void getMyCurrentLocation() {    

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
	    	  mp.position(new LatLng(location.getLatitude(), location.getLongitude()));
	    	 // mp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
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
		   	   
		   	   mp.title(Address + ", " + City);
		   	   
		   	   
		   	   locToBeStored=Address + "\n" + City + "\n(Coordinates: " + lat + ", " + lon + ")";
		   	   updateLoc(locToBeStored);
		   	   
		   	   mp.snippet("(Co-ordinates: " + lat +", " + lon + ").");
		   	   mp.draggable(true);
		   	   map.addMarker(mp).showInfoWindow();
	
		   	   map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
	      }
	      else{ 
	         AlertDialog.Builder builder1 = new AlertDialog.Builder(ArticleLocSelection.this);
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
	           
	           mp.position(new LatLng(1.3667, 103.8));
		     //  mp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
		       mp.title("Singapore, Singapore");
		       
		       locToBeStored = "Singapore \nSingapore \n(Coordinates: 1.3667, 103.8)";
		       updateLoc(locToBeStored);
		       
		       
		   	   mp.snippet("(Co-ordinates: " + 1.3667 +", " + 103.8 + ").");
		       mp.draggable(true);
		   	   map.addMarker(mp).showInfoWindow();
	      }
	   }  */
	   
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
		   	   
				// if(City.contains("Singapore")){
				 
				 mp.position(new LatLng(lat, lng));
				    mp.title(Address + ", " + City);
				    mp.snippet("(Co-ordinates: " + address.getLatitude() +", " + address.getLongitude() + ").");
				    mp.draggable(true);
				   	map.addMarker(mp).showInfoWindow();
					map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(), address.getLongitude()), 17));
				
					locToMain = Address + ", " + City;
				//locToBeStored = Address + "\n" + City + "\n(Coordinates: " + lat + ", " + lng + ")";
					locToBeStored = Address + ",\n" + City;
				updateLoc(locToBeStored);
				finalizedLat=lat;
			    finalizedLon=lng;
				/* }
				 else{
					 AlertDialog.Builder builder1 = new AlertDialog.Builder(ArticleLocSelection.this);
			         builder1.setTitle("Location Denied");
			   		 builder1.setMessage("This application only supports Singapore context.");
			   		 builder1.setCancelable(true);
			         builder1.setNegativeButton("OK",new DialogInterface.OnClickListener() {
			               public void onClick(DialogInterface dialog, int id) {
			            	   dialog.cancel();
			               }
			           });
			           AlertDialog alert11 = builder1.create();
			           alert11.show();
				 }*/
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
