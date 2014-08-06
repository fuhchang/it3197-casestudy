package com.example.it3197_casestudy.ui_logic;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.CreatehobbyGroup;
import com.example.it3197_casestudy.controller.ImageUploader;
import com.example.it3197_casestudy.model.Hobby;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestBatch;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateGroupActivityStep4 extends Activity implements LocationListener, OnMarkerClickListener, OnMarkerDragListener{
	GoogleMap map;
	
	double lat;
	double Lng;
	String Address;
	String City;
	String locToBeStored = "";
	double finallLat;
	double finalLng;
	String title, gDesc, type;
	private LatLng newPosition = null;
	Location location;
	private boolean gps_enabled=false;
	private boolean network_enabled=false;
	MarkerOptions marker = new MarkerOptions();
	TextView addressTV;
	EditText findAddress;
	Button findLoc, createGrp;
	byte[] byteImg;
	String imgPath;
	private UiLifecycleHelper uiHelper;
	private ProgressDialog dialog;
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	private boolean pendingPublishReauthorization = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_group_activity_step4);
		uiHelper = new UiLifecycleHelper(this, null);
	    uiHelper.onCreate(savedInstanceState);
	    imgPath = getIntent().getStringExtra("imgPath");
		title = getIntent().getStringExtra("hobbyName");
		type = getIntent().getStringExtra("category");
		gDesc = getIntent().getStringExtra("hobbyDesc");
		byteImg = getIntent().getByteArrayExtra("byteImg");
		
		
		addressTV = (TextView) findViewById(R.id.LocAddress);
		findAddress = (EditText) findViewById(R.id.addressET);
		findLoc = (Button) findViewById(R.id.btnFind);
		
		MarkerOptions mp = new MarkerOptions();
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.gMap)).getMap();
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		
		map.setOnMarkerDragListener(this);
		
		getMyCurrentLocation();
		
		findLoc.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				findLoc();
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_group_activity_step4, menu);
		return true;
	}
	
	public void onClick(View view){
		
	}
	@Override
	public void onMarkerDrag(Marker arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMarkerDragEnd(Marker mp) {
		// TODO Auto-generated method stub
		newPosition = mp.getPosition();
		try{
		Geocoder geocoder;  
	      List<Address> addresses;
	      geocoder = new Geocoder(this, Locale.getDefault());
	      addresses = geocoder.getFromLocation(newPosition.latitude, newPosition.longitude,1);
	      Address = addresses.get(0).getAddressLine(0);
	       City = addresses.get(0).getAddressLine(1);
	       mp.setTitle(addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getAddressLine(1));
	       mp.showInfoWindow();
	       
	       locToBeStored = Address + "\n" + City ;  
	   	   addressTV.setText("Estimated location: \n" + locToBeStored);
	       lat = newPosition.latitude;
	       Lng = newPosition.longitude;
		}catch(Exception e){
			 e.printStackTrace();
		}
		
	}

	@Override
	public void onMarkerDragStart(Marker arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
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
	
	private void getMyCurrentLocation() {    

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
			   Lng= location.getLongitude();
		   } 
	   
	      try
	       {
	   	   //Getting address based on coordinates.
	       Geocoder geocoder;  
	       List<Address> addresses;
	       geocoder = new Geocoder(this, Locale.getDefault());
	       addresses = geocoder.getFromLocation(lat, Lng, 1);
	 
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
		   	  	   
		   	      Geocoder geocoder;  
		   	      List<Address> addresses;
		   	      geocoder = new Geocoder(this, Locale.getDefault());
		   	      addresses = geocoder.getFromLocation(lat, Lng, 1);
		   	
		   	       Address = addresses.get(0).getAddressLine(0);
		   	       City = addresses.get(0).getAddressLine(1);
		   	       
		   	    marker.position(new LatLng(lat, Lng));
				 marker.title(Address + ", " + City);
				 marker.draggable(true);
				 map.addMarker(marker).showInfoWindow();
				 map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, Lng), 16));
		   	      }
		   	      catch (Exception e)
		   	      {
		   	          e.printStackTrace();
		   	      }
		   	   locToBeStored=Address + "\n" + City;
		   	   addressTV.setText("Estimated location: \n" + locToBeStored);
		   	   

	      }
	      else{ 
	         AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
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
	           
	           
	           addressTV.setText("Opps no location found!! >.<");
	           
	      }
	      
	      
	   }  

	private void findLoc(){
  	  String find = findAddress.getText().toString();
  	  Geocoder gc = new Geocoder(this);
  	  
  	  if(gc.isPresent()){
  		  List<Address> list = null;
  		  try {
			list = gc.getFromLocationName(find, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		  
  		  if(list.size() > 0){
  			Address address = list.get(0);
  			 double lat = address.getLatitude();
			 double lng = address.getLongitude();
			 map.clear();
			 
			 try {
				 Geocoder geocoder;
				 
				 List<Address> addresses;
				 geocoder = new Geocoder(this, Locale.getDefault());
				 addresses = geocoder.getFromLocation(lat, lng, 1);
				 Address = addresses.get(0).getAddressLine(0);
		   	      City = addresses.get(0).getAddressLine(1);
				 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			 marker.position(new LatLng(lat, lng));
			 marker.title(Address + ", " + City);
			 marker.draggable(true);
			 map.addMarker(marker).showInfoWindow();
			 map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(), address.getLongitude()), 16));
			 addressTV.setText( Address + "\n" + City);
			 this.lat=lat;
			 this.Lng=lng;
  		  }else{
  			 Toast.makeText(getApplicationContext(), "No location matches ", Toast.LENGTH_LONG).show();
  		  }
  	  }
  	  
  	  
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.createGrp:
		Hobby hobby = new Hobby();
		hobby.setGroupName(title);
		hobby.setCategory(type);
		hobby.setDescription(gDesc);
		hobby.setLat(lat);
		hobby.setLng(Lng);
		hobby.setGrpImg(imgPath);
		hobby.setAdminNric(getIntent().getExtras().getString("nric"));
		dialog = ProgressDialog.show(CreateGroupActivityStep4.this,
				"Creating Hobby", "Please wait...", true);
		if(!Session.getActiveSession().isClosed()){
			publishHobby(dialog, hobby);
			}else{
				Toast.makeText(getApplicationContext(), "Unable to share on facebook", Toast.LENGTH_LONG).show();
				dialog.dismiss();
			}
		
		}
		
		return super.onOptionsItemSelected(item);
	}
	/*
	public static byte[] fileToByteArray(String path) throws IOException {
	    File imagefile = new File(path);
	    byte[] data = new byte[(int) imagefile.length()];
	    FileInputStream fis = new FileInputStream(imagefile);
	    fis.read(data);
	    fis.close();
	    return data;
	}
	
	// And to convert the image URI to the direct file system path of the image file
	public String getRealPathFromURI(Uri contentUri) {

	        // can post image
	        String [] proj={MediaStore.Images.Media.DATA};
	        Cursor cursor = managedQuery( contentUri,
	                        proj, // Which columns to return
	                        null,       // WHERE clause; which rows to return (all rows)
	                        null,       // WHERE clause selection arguments (none)
	                        null); // Order-by clause (ascending by name)
	        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        cursor.moveToFirst();

	        return cursor.getString(column_index);
	}
	*/
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		 uiHelper.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		 uiHelper.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 uiHelper.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		 uiHelper.onSaveInstanceState(outState);
	}
	
	private void publishHobby(final ProgressDialog dialog, final Hobby hobby){
		final Hobby hobbyItem = hobby;
		Session session = Session.getActiveSession();
		if(session != null){
			
			 List<String> permissions = session.getPermissions();
			 
		        if (!isSubsetOf(PERMISSIONS, permissions)) {
		            pendingPublishReauthorization = true;
		            Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(this, PERMISSIONS);
		            session.requestNewPublishPermissions(newPermissionsRequest);
		            return;
		        }else{
		        	dialog.dismiss();
					
		        }
		        
		        RequestBatch requestBatch = new RequestBatch();
		        Bundle params = new Bundle();
		        params.putString("fb:app_id", "1448892845363751");
		        params.putString("og:type", "community_outreach:hobby_group");
		        params.putString("og:url", "localhost:8080/CommunityOutreach/");
		        params.putString("og:title", title);
		        params.putString("og:image", imgPath);
		        params.putString("og:description", gDesc);
		        if((imgPath.length() >0 ) && imgPath != null){
		        	params.putString("object","{\"title\":\""+title+"\",\"type\":\"community_outreach:hobby_group\",\"image\":\""+imgPath+"\",\"description\":\""+gDesc+"\"}");	
		        }else{
		        	params.putString("object","{\"title\":\""+title+"\",\"type\":\"community_outreach:hobby_group\",\"description\":\""+gDesc+"\"}");
		        }
		       
		        Request.Callback callback= new Request.Callback() {

					@Override
					public void onCompleted(Response response) {
						// TODO Auto-generated method stub
						System.out.println(response.toString());
						try{
							if(response != null){
								
				                JSONObject graphResponse = response.getGraphObject().getInnerJSONObject();
				               
			                	if(graphResponse.getString("id") != null){
			                		hobbyItem.setHobbyFBPostID(graphResponse.getString("id"));
			                	}
			                	else{
			                		hobbyItem.setHobbyFBPostID("0");
			                	}
			                	CreatehobbyGroup createHobby = new CreatehobbyGroup(CreateGroupActivityStep4.this, hobby, dialog, graphResponse.getString("id") );
			            		createHobby.execute();
							}
						} catch (Exception e) {
		                    Log.i("Tag","JSON error "+ e.getMessage());
		                    e.printStackTrace();
			                }
					}
		        	
		        };
		        Request request = new Request(session, "me/objects/community_outreach:hobby_group", params, HttpMethod.POST, callback);
		        request.setBatchEntryName("object");
		        requestBatch.add(request);
		        requestBatch.executeAsync();
		        
		}
		
	}
	private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
	    for (String string : subset) {
	        if (!superset.contains(string)) {
	            return false;
	        }
	    }
	    return true;
	}
}
