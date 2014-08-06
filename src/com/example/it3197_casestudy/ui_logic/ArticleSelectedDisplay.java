package com.example.it3197_casestudy.ui_logic;

import org.json.JSONException;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetImageFromFacebook;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ArticleSelectedDisplay extends Fragment{

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
	
	String address;
	double dbLat;
	double dbLon;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		View rootView = inflater.inflate(R.layout.activity_article_latest_more_detail, container, false);		
		Bundle bundle = getArguments();
		address = bundle.getString("articleLoc");
		dbLat = bundle.getDouble("dbLat");
		dbLon = bundle.getDouble("dbLon");
		
		return rootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		getActivity().getActionBar().setTitle("");
		
		map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map)).getMap();		
		//map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3667, 103.8), 10));
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		//map.setMapType(GoogleMap.MAP_TYPE_NONE);
		//map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		//map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		//map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);	
		
		//map.setTrafficEnabled(true);
		  
		//map.setTrafficEnabled(true);
		  
		//map.setMyLocationEnabled(true);	  
	//	  getMyCurrentLocation();
		  
		  addTv = (TextView) getActivity().findViewById(R.id.add);
		  addTv.setText(address);
		  
		  artLoc.position(new LatLng(dbLat, dbLon));
		  artLoc.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		  artLoc.title("Place of occurrence");
		  map.addMarker(artLoc).showInfoWindow();
		  map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dbLat, dbLon), 18));
		
		  super.onActivityCreated(savedInstanceState);
	}
}
