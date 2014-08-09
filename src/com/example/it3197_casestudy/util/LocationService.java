package com.example.it3197_casestudy.util;

import java.util.ArrayList;

import com.example.it3197_casestudy.controller.InsertUserLocation;
import com.example.it3197_casestudy.controller.UpdateUserPoints;
import com.example.it3197_casestudy.model.User;
import com.google.android.gms.maps.model.LatLng;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LocationService extends Service {
	public static final String BROADCAST_ACTION = "LOCATION SERVICE";
	private static final int TWO_MINUTES = 1000 * 60 * 2;
	public LocationManager locationManager;
	public MyLocationListener listener;
	public Location previousLocation;
	
	LatLng location;
	ArrayList<LatLng> locationList;
	
	Bundle data;
	User user;
	Intent intent;
	
	@Override
	public void onCreate() {
	    super.onCreate();
	    intent = new Intent(BROADCAST_ACTION);

		locationList = new ArrayList<LatLng>();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
	    Log.v("START SERVICE", "STARTED");
	    if(intent != null){
	    	data = intent.getExtras();
	    	user = data.getParcelable("user");
	    }
		
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    listener = new MyLocationListener();
	    //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, listener);
	    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, listener);
	}

	@Override
	public void onDestroy() {       
	   // handler.removeCallbacks(sendUpdatesToUI);     
	    super.onDestroy();
	    Log.v("STOP SERVICE", "STOPPED");
	    locationManager.removeUpdates(listener);        
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	/*
	protected boolean isBetterLocation(Location location, Location currentBestLocation) {
	    if (currentBestLocation == null) {
	        // A new location is always better than no location
	        return true;
	    }
	    
	    // Check whether the new location fix is newer or older
	    long timeDelta = location.getTime() - currentBestLocation.getTime();
	    boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
	    boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
	    boolean isNewer = timeDelta > 0;
	    
	    // If it's been more than two minutes since the current location, use the new location because the user has likely moved
	    if (isSignificantlyNewer) {
	        return true;
	    // If the new location is more than two minutes older, it must be worse
	    } else if (isSignificantlyOlder) {
	        return false;
	    }
	    
	    // Check whether the new location fix is more or less accurate
	    int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
	    boolean isLessAccurate = accuracyDelta > 0;
	    boolean isMoreAccurate = accuracyDelta < 0;
	    boolean isSignificantlyLessAccurate = accuracyDelta > 200;
	    
	    // Check if the old and new location are from the same provider
	    boolean isFromSameProvider = isSameProvider(location.getProvider(), currentBestLocation.getProvider());
	
	    // Determine location quality using a combination of timeliness and accuracy
	    if (isMoreAccurate) {
	        return true;
	    } else if (isNewer && !isLessAccurate) {
	        return true;
	    } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
	        return true;
	    }
	    return false;
	}
	*/
	
	/* Checks whether two providers are the same */
	/*private boolean isSameProvider(String provider1, String provider2) {
	    if (provider1 == null) {
	      return provider2 == null;
	    }
	    return provider1.equals(provider2);
	}*/
	
	public static Thread performOnBackgroundThread(final Runnable runnable) {
	    final Thread t = new Thread() {
	        @Override
	        public void run() {
	            try {
	                runnable.run();
	            } finally {
	            	
	            }
	        }
	    };
	    t.start();
	    return t;
	}
	
	public class MyLocationListener implements LocationListener
	{
		public void onLocationChanged(final Location loc)
	    {
	        if(previousLocation == null) {
	        	previousLocation = loc;
	        	
	        	location = new LatLng(loc.getLatitude(), loc.getLongitude());
	        	locationList.add(location);
	        	intent.putParcelableArrayListExtra("LocationList", locationList);
	            
	            InsertUserLocation insertUserLocation = new InsertUserLocation(user, location);
	            insertUserLocation.execute();
	        }
	        
	        if(previousLocation.getLatitude() != loc.getLatitude() && previousLocation.getLongitude() != loc.getLongitude()) {
		        Log.i("Location", "Location changed");
		        
	        	float distance = previousLocation.distanceTo(loc);
	        	System.out.println(distance);
	        	
	        	// distance >= 5
	        	if(distance >= 2) {		        	
		        	System.out.println(previousLocation.getLatitude() + " " + previousLocation.getLongitude());
		        	System.out.println(loc.getLatitude() + " " + loc.getLongitude());
		        	
		        	previousLocation = loc;
		        	
		            location = new LatLng(loc.getLatitude(), loc.getLongitude());
		            locationList.add(location);
		            intent.putParcelableArrayListExtra("LocationList", locationList);
		            
		            InsertUserLocation insertUserLocation = new InsertUserLocation(user, location);
		            insertUserLocation.execute();
		            
		            user.setPoints(user.getPoints() + 5);
		            UpdateUserPoints updateUserPoints = new UpdateUserPoints(user);
		            updateUserPoints.execute();
	        	}
	        }
	        intent.putExtra("user", user);
        	intent.putExtra("Latitude", loc.getLatitude());
        	intent.putExtra("Longitude", loc.getLongitude());
        	intent.putExtra("Provider", loc.getProvider());
            sendBroadcast(intent);
	    }
	    
	    public void onProviderDisabled(String provider)
	    {
	        Toast.makeText(getApplicationContext(), "GPS Disabled", Toast.LENGTH_SHORT).show();
	        /*intent.putExtra("GPSstatus", "disabled");
            sendBroadcast(intent);*/
	    }
	
	    public void onProviderEnabled(String provider)
	    {
	        Toast.makeText(getApplicationContext(), "GPS Enabled", Toast.LENGTH_SHORT).show();
	    }

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
	}
}