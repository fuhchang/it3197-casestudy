package com.example.it3197_casestudy.ui_logic;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.it3197_casestudy.controller.CreateArticle;
import com.example.it3197_casestudy.model.Article;
import com.example.it3197_casestudy.ui_logic.ArticleLocSelection;
import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.array;
import com.example.it3197_casestudy.R.id;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Address;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class SubmitArticle extends Activity {



	GoogleMap map;
	Spinner spCat;
	ArrayAdapter<CharSequence> adapter;
	//TextView categorySelected;
	String[] categoryArray;
	
	
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
	
	EditText articleTitle, articleContent;
	TextView storingLoc, currentTime;
	
	String locToBeStored = "";

	Button chooseLoc;
	
	
	String categorySelected;
	
	final int CHOOSE_LOC=1;
	
    MarkerOptions mp = new MarkerOptions();
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_article);
		
		getActionBar().setTitle("Submit Article");
		
		/***Category Selection***/
		spCat = (Spinner) findViewById(R.id.spCat);
		//categorySelected =  (TextView) findViewById(R.id.categorySelected);
		Resources myRes = this.getResources();
		categoryArray = myRes.getStringArray(R.array.article_category_choice);
		
		adapter = ArrayAdapter.createFromResource(this, R.array.article_category_choice, android.R.layout.simple_spinner_dropdown_item);
		spCat.setAdapter(adapter);
		
		spCat.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				/**GET SELECTED VALUE**/
				//categorySelected.setText(arg0.getItemAtPosition(arg2).toString());
				categorySelected = arg0.getItemAtPosition(arg2).toString();
			//	Toast.makeText(getApplicationContext(), categorySelected, Toast.LENGTH_LONG).show();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub				
			}			
		});
		
		 /**Get current time*/		
		currentTime = (TextView)findViewById(R.id.currentTime);
		Calendar c = Calendar.getInstance();
      	SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMMM yyyy - hh:mm a");
      	String strDate = sdf.format(c.getTime());
      	currentTime.setText(strDate);
		
		
		
		storingLoc = (TextView)findViewById(R.id.storingLoc);
		articleTitle = (EditText) findViewById(R.id.articleTitle);
		articleContent =(EditText) findViewById(R.id.articleContent);
		/*
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();		
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3667, 103.8), 10));
			  map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			//  map.setMapType(GoogleMap.MAP_TYPE_NONE);
			//    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			//  map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			//  map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);		  
			  map.setOnMarkerDragListener(this);
			  
			  map.setTrafficEnabled(true);
			  
			  //map.setMyLocationEnabled(true);	  
			  
			  
			  
			  Button btn = (Button) findViewById(R.id.btn);
			  btn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					et = (EditText) findViewById(R.id.addressET);
					
						enteredLoc();
					
				}
			  });*/
			  
			  /****For constant updating of location****/
			//		 lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			//		   lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
			  
			  /*****Get location when entering app*****/
			  getMyCurrentLocation();
			  
		//	  chooseLoc = (Button) findViewById(R.id.chooseLoc);
		//	  chooseLoc.setOnClickListener(new OnClickListener(){

		//		@Override
		//		public void onClick(View arg0) {
		//			// TODO Auto-generated method stub
					
		//			Intent myIntent = new Intent(SubmitArticle.this, ArticleLocSelection.class);
		//			myIntent.putExtra("mainSubmitLat", String.valueOf(lat));
		//			myIntent.putExtra("mainSubmitLon", String.valueOf(lon));
		//			startActivityForResult(myIntent, CHOOSE_LOC);
					
		//		}
				  
		//	  });
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit_article, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		int id = item.getItemId();
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MMMM/yyyy HH:mm a");
		Calendar cal = Calendar.getInstance();
		String now = dateFormat.format(cal.getTime());
		
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM/yyyy HH:mm a");

            Date currentTime = null;
			try {
				currentTime = simpleDateFormat.parse(now);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		if(id==R.id.submit){
			
			if((articleTitle.getText().toString().equals(""))||(articleContent.getText().toString().equals(""))){
				Toast.makeText(getApplicationContext(), "Please provide a title and description", Toast.LENGTH_LONG).show();
			}
			else{
				Article article = new Article();
				article.setArticleID(0);
				article.setTitle(articleTitle.getText().toString());
				article.setContent(articleContent.getText().toString());
				article.setDateTime(currentTime);
				article.setCategory(categorySelected);
				article.setLocation(locToBeStored);
				article.setUserNRIC("S9512233X");
				article.setActive(1);
				article.setApproved("Pending");
				article.setDbLat(lat);
				article.setDbLon(lon);
				
				//(0, "Hi", "Hi", currentTime,categorySelected, "Hi", "S9512233X", 1, "Hi", 1.3, 54.6);
				CreateArticle c = new CreateArticle(SubmitArticle.this,article);
				c.execute();
			}
		}
		if(id==R.id.backToMain){
			//Intent intent = new Intent(SubmitArticle.this, ArticleMainActivity.class);
			//startActivity(intent);
			SubmitArticle.this.finish();
		}
		
		return super.onOptionsItemSelected(item);
	}

/*
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
		       mp.setSnippet("(Co-ordinates: " + toPosition.latitude + ", " + toPosition.longitude + ").");
		       mp.showInfoWindow();
		       
		       
		       
		       locToBeStored=Address + "\n" + City;
		   	   updateLoc(locToBeStored);
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
	
	*/
	
	/** Check the type of GPS Provider available at that instance and  collect the location informations**/
	   void getMyCurrentLocation() {    

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
	    //	  mp.position(new LatLng(location.getLatitude(), location.getLongitude()));
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
		   	   
	//	   	   mp.title(Address + ", " + City);
		   	   
		   	   
		   	   locToBeStored=Address + ", " + City;
		   	  // updateLoc(locToBeStored);
		   	 //  Toast.makeText(getApplicationContext(), locToBeStored, Toast.LENGTH_SHORT).show();
		   	   
		   	  // storingLoc.setText(Address + "\n" + City + "\n(Coordinates: " + lat + ", " + lon + ")");
		   	storingLoc.setText(Address + ",\n" + City);
		   	   
	//	   	   mp.snippet("(Co-ordinates: " + lat +", " + lon + ").");
	//	   	   mp.draggable(true);
	//	   	   map.addMarker(mp).showInfoWindow();
	
	//	   	   map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
	      }
	      else{ 
	         AlertDialog.Builder builder1 = new AlertDialog.Builder(SubmitArticle.this);
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
	           locToBeStored = "601 Island Club Rd, Singapore"; 
	         //  Toast.makeText(getApplicationContext(), locToBeStored, Toast.LENGTH_SHORT).show();
	          // storingLoc.setText("601 Island Club Rd \nSingapore \n(Coordinates: " + lat + ", " + lon +")");
	           storingLoc.setText("601 Island Club Rd ,\nSingapore");
	       /*    mp.position(new LatLng(1.3667, 103.8));
		     //  mp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
		       mp.title("Singapore, Singapore");
		       
		       locToBeStored = "Singapore, Singapore";
		       updateLoc(locToBeStored);
		       
		       
		   	   mp.snippet("(Co-ordinates: " + 1.3667 +", " + 103.8 + ").");
		       mp.draggable(true);
		   	   map.addMarker(mp).showInfoWindow();*/
	      }
	   }  
	   /*
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
				    mp.snippet("(Co-ordinates: " + address.getLatitude() +", " + address.getLongitude() + ").");
				    mp.draggable(true);
				   	map.addMarker(mp).showInfoWindow();
					map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(), address.getLongitude()), 16));
				
				
				locToBeStored = Address + "\n" + City;
				updateLoc(locToBeStored);
			   }
			     else{
			    	 Toast.makeText(getApplicationContext(), "No location matches " + enteredAddress + "." , Toast.LENGTH_LONG).show();
			     }
		   }
	   }
	   */
	   
	   public void updateLoc(String loc){
		   storingLoc.setText(loc);
		   
	   }


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode==CHOOSE_LOC){
			if(resultCode==RESULT_OK){
				//String selectedAddress = data.getStringExtra();
				
				String selectedAdd = data.getStringExtra("selectedAdd");
				 String selectedLat = data.getStringExtra("selectedLat");
				 String selectedLon = data.getStringExtra("selectedLon");
				 String locToMain = data.getStringExtra("locToMain");
				 locToBeStored = locToMain;
				// Toast.makeText(getApplicationContext(), locToBeStored, Toast.LENGTH_SHORT).show();
				 
				 storingLoc.setText(selectedAdd);
				 lat=Double.parseDouble(selectedLat);
				 lon = Double.parseDouble(selectedLon);
				 
				// Log.d("TESTTTTTTT", String.valueOf(lat));
			}
			
		}
	}
	
	
	public void changeLoc(View v){
		Intent myIntent = new Intent(SubmitArticle.this, ArticleLocSelection.class);
		myIntent.putExtra("mainSubmitLat", String.valueOf(lat));
		myIntent.putExtra("mainSubmitLon", String.valueOf(lon));
		startActivityForResult(myIntent, CHOOSE_LOC);
	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		//Intent intent = new Intent(SubmitArticle.this, ArticleMainActivity.class);
		//startActivity(intent);
		SubmitArticle.this.finish();
		super.onBackPressed();
	}

}
