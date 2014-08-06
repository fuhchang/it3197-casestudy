package com.example.it3197_casestudy.ui_logic;

import java.text.DecimalFormat;
import java.util.ArrayList;

import java.util.List;
import java.util.Locale;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.controller.CheckMemberHobby;
import com.example.it3197_casestudy.geofencing.GeofenceRemover;
import com.example.it3197_casestudy.geofencing.GeofenceRequester;
import com.example.it3197_casestudy.geofencing.GeofenceUtils.REMOVE_TYPE;
import com.example.it3197_casestudy.geofencing.GeofenceUtils.REQUEST_TYPE;
import com.example.it3197_casestudy.geofencing.SimpleGeofence;
import com.example.it3197_casestudy.geofencing.SimpleGeofenceStore;
import com.example.it3197_casestudy.model.Hobby;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchHobbyByMap extends FragmentActivity implements LocationListener,
		OnMarkerClickListener, OnMarkerDragListener, GooglePlayServicesClient.ConnectionCallbacks
 {
	GoogleMap map;
	double lat;
	double Lng;
	Location location;
	private LocationClient mLocationClient;
	private boolean gps_enabled = false;
	private boolean network_enabled = false;
	ArrayList<Hobby> hobbyList = new ArrayList<Hobby>();
	private LatLng current_location;
	private SimpleGeofenceStore mPrefs;
	private GeofenceRequester mGeofenceRequester;
	private String userName;
	 List<Geofence> mCurrentGeofences;
	
	 BitmapDescriptor iconDance;
	 BitmapDescriptor iconGardening;
	 BitmapDescriptor iconCooking;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_hobby_by_map);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		userName = prefs.getString("nric", "");
		
		MapsInitializer.initialize(getApplicationContext());
		iconDance = BitmapDescriptorFactory.fromResource(R.drawable.dance);
		iconGardening = BitmapDescriptorFactory.fromResource(R.drawable.gardening);
		iconCooking = BitmapDescriptorFactory.fromResource(R.drawable.cooking);
		
		mPrefs = new SimpleGeofenceStore(this);
		mGeofenceRequester = new GeofenceRequester(this);
		mCurrentGeofences = new ArrayList<Geofence>();
		Bundle extras = getIntent().getExtras();
		hobbyList = extras.getParcelableArrayList("hobbyList");
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.gMap)).getMap();
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		getMyCurrentLocation();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(current_location, 16));
		MarkerOptions mp = new MarkerOptions();
		mp.title("Your location");
		map.addMarker(new MarkerOptions().position(current_location).title("Your location")).showInfoWindow();
		for (int i = 0; i < hobbyList.size(); i++) {
			
			MarkerOptions marker = new MarkerOptions();
			LatLng hobbyLoc = new LatLng(hobbyList.get(i).getLat(), hobbyList.get(i).getLng());
			marker.title(hobbyList.get(i).getGroupName());
			marker.snippet(reverseGeoCoding(hobbyList.get(i).getLat(), hobbyList.get(i).getLng()));
			marker.position(hobbyLoc);
			if(hobbyList.get(i).getCategory().equals("Dance")){
				marker.icon(iconDance);
			}else if(hobbyList.get(i).getCategory().equals("Cooking")){
				marker.icon(iconCooking);
			}else if(hobbyList.get(i).getCategory().equals("Gardening")){
				marker.icon(iconGardening);
			}else{
				
			}
			CircleOptions co = new CircleOptions().center(new LatLng(hobbyList.get(i).getLat(), hobbyList.get(i).getLng())).radius(2000).fillColor(0x40ff0000).strokeColor(Color.TRANSPARENT).strokeWidth(2);
			Circle circle = map.addCircle(co);
			SimpleGeofence UiGeofence = new SimpleGeofence(hobbyList.get(i).getGroupName(), hobbyList.get(i).getLat(), hobbyList.get(i).getLng(), 1000,Geofence.NEVER_EXPIRE, Geofence.GEOFENCE_TRANSITION_ENTER);
			mPrefs.setGeofence(hobbyList.get(i).getGroupName(), UiGeofence);
			
			mCurrentGeofences.add(UiGeofence.toGeofence());
			
			map.addMarker(marker).showInfoWindow();
		}

		 try {
	            // Try to add geofences
	            mGeofenceRequester.addGeofences(mCurrentGeofences,"Hi","There is a hobby group nearby");
	            
	        } catch (UnsupportedOperationException e) {
	            // Notify user that previous request hasn't finished.
	            Toast.makeText(this, R.string.add_geofences_already_requested_error,
	                        Toast.LENGTH_LONG).show();
	        }
		map.setOnMarkerDragListener(this);
		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener(){

			@Override
			public void onInfoWindowClick(Marker mp) {
				// TODO Auto-generated method stub
				Hobby hobby = new Hobby();
				Intent intent = new Intent(SearchHobbyByMap.this, ViewSingleHobby.class);
				for(int i=0; i<hobbyList.size(); i++){
					if(hobbyList.get(i).getGroupName().equals(mp.getTitle())){
		
						Toast.makeText(getApplicationContext(), hobbyList.get(i).getAdminNric() +"-"+ userName, Toast.LENGTH_LONG).show();
						if (hobbyList.get(i).getAdminNric().equals(userName)){
							intent.putExtra("adminNric", hobbyList.get(i).getAdminNric());
							intent.putExtra("member", "none");
						}else{
							intent.putExtra("adminNric", "S0000E");
							
							CheckMemberHobby checkmember = new CheckMemberHobby(SearchHobbyByMap.this,userName, hobbyList.get(i).getGroupID( ));
							checkmember.execute();
							
							if(checkmember.getCheckID() == 0){
								intent.putExtra("member", "none");
							}else{
								intent.putExtra("member", "member");
							}
							
						}
						
						/*
						intent.putExtra("grpID", hobbyList.get(i).getGroupID());
						if(hobbyList.get(i).getAdminNric().isEmpty()){
							intent.putExtra("adminNric", "none");
							CheckMemberHobby checkmember = new CheckMemberHobby(SearchHobbyByMap.this,userName, hobbyList.get(i).getGroupID( ));
							checkmember.execute();
							if(checkmember.getCheckID() == 0){
								intent.putExtra("member", "none");
							}else{
								intent.putExtra("member", "member");
							}
						}else if (hobbyList.get(i).getAdminNric().equals(userName)){
						intent.putExtra("adminNric", hobbyList.get(i).getAdminNric());
					}
					*/
					}
				}
				intent.putExtra("grpName", mp.getTitle());
				
				intent.putExtra("userNric", userName);
				startActivity(intent);
			}
			
		});
		map.setOnMapLongClickListener(new OnMapLongClickListener(){

			@Override
			public void onMapLongClick(LatLng point) {
				// reverse geocode point
				reverseGeoCoding(point.latitude, point.longitude);
			}
			
			
		});
		
		


	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_hobby_by_map, menu);
		return true;
	}

	@Override
	public void onMarkerDrag(Marker arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMarkerDragEnd(Marker arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMarkerDragStart(Marker arg0) {
		// TODO Auto-generated method stub

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
	
	

	public String reverseGeoCoding(double Lat, double Lng) {
		String address = "";
		try {
			String location = "";
			String city = "";
			Geocoder geocoder;
			List<Address> addresses;
			geocoder = new Geocoder(this, Locale.getDefault());
			addresses = geocoder.getFromLocation(Lat, Lng, 1);
			location = addresses.get(0).getAddressLine(0);
			city = addresses.get(0).getAddressLine(1);

			address = location + city;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return address;
	}

	private void getMyCurrentLocation() {
		LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		try {
			gps_enabled = locManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {

		}
		try {
			network_enabled = locManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {

		}

		if (gps_enabled) {
			location = locManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		}

		if (network_enabled && location == null) {
			location = locManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		}

		if (location != null) {
			// accLoc=location.getAccuracy();
			lat = location.getLatitude();
			Lng = location.getLongitude();
		}

		current_location = new LatLng(lat, Lng);

	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	
	public static class StoreLocation{
		public LatLng mLatLng;
		public String mId;
		StoreLocation(LatLng latlng, String id){
			mLatLng = latlng;
			mId = id;
		}
	}
	
	
	
}
