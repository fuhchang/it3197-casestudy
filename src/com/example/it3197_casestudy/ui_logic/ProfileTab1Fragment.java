package com.example.it3197_casestudy.ui_logic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.util.GPSTracker;

public class ProfileTab1Fragment extends Fragment {
	Context context;
	TextView tv_lat, tv_long, tv_country, tv_address;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		context = getActivity();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_profile_tab1, container, false);
		
        // check if GPS enabled
        GPSTracker gpsTracker = new GPSTracker(getActivity());

        if (gpsTracker.canGetLocation())
        {
        	tv_long = (TextView) rootView.findViewById(R.id.tv_long);
        	tv_long.setText("Longtitude: " + String.valueOf(gpsTracker.getLongitude()));
        	
        	tv_lat = (TextView) rootView.findViewById(R.id.tv_lat);
        	tv_lat.setText("Latitude: " + String.valueOf(gpsTracker.getLatitude()));
        	
        	tv_country = (TextView) rootView.findViewById(R.id.tv_country);
        	tv_country.setText(String.valueOf(gpsTracker.getCountryName(context)));
        	
        	tv_address = (TextView) rootView.findViewById(R.id.tv_address);
        	tv_address.setText(String.valueOf(gpsTracker.getAddressLine(context)));
        }
        else
        {
            // Can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }
		
		return rootView;
	}
}

/*
	LocationManager locationManager;
	LocationListener locationListener;
	Location location;
	String provider;
	
	

		
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (!enabled) {
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
	        alertDialog.setTitle("Unable to retrieve location").setMessage("Check if your GPS or Network is enabled.");
	        
	        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() 
	        {   
	            @Override
	            public void onClick(DialogInterface dialog, int which) 
	            {
	                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	                startActivity(intent);
	            }
	        });
	        alertDialog.show();
		}
		provider = locationManager.getBestProvider(new Criteria(), false);
		location = locationManager.getLastKnownLocation(provider);
	    if (location != null) {
	        onLocationChanged(location);
	    }
	    locationListener = new LocationListener(){
	    	@Override
	    	public void onLocationChanged(Location location) {
	    	    Toast.makeText(MainLinkPage.this, "Latitude: " + String.valueOf(location.getLatitude()), Toast.LENGTH_SHORT).show();
	    	    Toast.makeText(MainLinkPage.this, "Longitude: " + String.valueOf(location.getLongitude()), Toast.LENGTH_SHORT).show();
	    	}

	    	@Override
	    	public void onProviderEnabled(String provider) {
	    	    Toast.makeText(MainLinkPage.this, "Enabled new provider " + provider, Toast.LENGTH_SHORT).show();
	    	}

	    	@Override
	    	public void onProviderDisabled(String provider) {
	    	    Toast.makeText(MainLinkPage.this, "Disabled provider " + provider, Toast.LENGTH_SHORT).show();
	    	}

	    	@Override
	    	public void onStatusChanged(String provider, int status, Bundle extras) {
	    	}
	    };
	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1000, locationListener);
*/