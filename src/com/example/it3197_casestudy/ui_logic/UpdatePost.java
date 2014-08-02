package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import java.util.List;
import java.util.Locale;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.validation.Form;
import com.example.it3197_casestudy.validation.Validate;
import com.example.it3197_casestudy.validation.validator.NotEmptyValidator;
import com.example.it3197_casestudy.validation_controller.UpdatePostValidationController;

import com.google.android.gms.maps.GoogleMap;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class UpdatePost extends Activity {
	GoogleMap map;
	int postID;
	int groupID;
	String userNric;
	Location location;
	String locToBeStored = "";
	double lat;
	double Lng;
	String Address;
	String City;
	String adminNric, grpName;
	
	private boolean gps_enabled = false;
	private boolean network_enabled = false;
	EditText postTitleET, contentET;
	TextView LocAddress, newAddress;
	private static final int selectedLoc = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_post);
		userNric= getIntent().getExtras().getString("userNric");
		postID = getIntent().getExtras().getInt("postID");
		groupID = getIntent().getExtras().getInt("grpID");
		grpName = getIntent().getExtras().getString("grpName");
		adminNric = getIntent().getExtras().getString("adminNric");
		postTitleET = (EditText) findViewById(R.id.postNameET);
		contentET = (EditText) findViewById(R.id.etPContent);
		postTitleET.setText(getIntent().getExtras().getString("postTitle"));
		contentET.setText(getIntent().getExtras().getString("content"));
		LocAddress = (TextView) findViewById(R.id.LocAddress);
		newAddress = (TextView) findViewById(R.id.newAddress);
		newAddress.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getBaseContext(), HobbyLoSelection.class);
				intent.putExtra("lat", Double.toString(lat));
				intent.putExtra("Lng", Double.toString(Lng));
				startActivityForResult(intent, selectedLoc);
			}
			
		});
		getMyCurrentLocation();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_post, menu);
		return true;
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode == RESULT_OK){
			switch(requestCode){
			case selectedLoc:
				String selectedAdd = data.getStringExtra("selectedAdd");
				 String selectedLat = data.getStringExtra("selectedLat");
				 String selectedLon = data.getStringExtra("selectedLon");
				 lat = Double.parseDouble(selectedLat);
				 Lng = Double.parseDouble(selectedLon);
				 LocAddress.setText(selectedAdd);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
			case R.id.action_update_post:
				Form mForm = new Form();
				Validate validTitle = new Validate(postTitleET);
				Validate validContent = new Validate(contentET);
				validTitle.addValidator(new NotEmptyValidator(UpdatePost.this));
				mForm.addValidates(validTitle);
				validContent.addValidator(new NotEmptyValidator(UpdatePost.this));
				mForm.addValidates(validContent);
				ArrayList<Validate> validList = new ArrayList<Validate>();
				validList.add(validTitle);
				validList.add(validContent);
				Intent intent = new Intent();
				
				UpdatePostValidationController validCon = new UpdatePostValidationController(this, userNric, postID, groupID, lat, Lng, postTitleET.getText().toString() ,contentET.getText().toString(), grpName, adminNric);
				validCon.validateForm(intent, mForm, validList);
			break;
		}
		return super.onOptionsItemSelected(item);
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

		try {
			// Getting address based on coordinates.
			Geocoder geocoder;
			List<Address> addresses;
			geocoder = new Geocoder(this, Locale.getDefault());
			addresses = geocoder.getFromLocation(lat, Lng, 1);

			Address = addresses.get(0).getAddressLine(0);
			City = addresses.get(0).getAddressLine(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (Address != null && !Address.isEmpty()) {
			// mp.position(new LatLng(location.getLatitude(),
			// location.getLongitude()));
			// mp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
			try {
				// Getting address based on coordinates.
				Geocoder geocoder;
				List<Address> addresses;
				geocoder = new Geocoder(this, Locale.getDefault());
				addresses = geocoder.getFromLocation(lat, Lng, 1);

				Address = addresses.get(0).getAddressLine(0);
				City = addresses.get(0).getAddressLine(1);
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			// mp.title(Address + ", " + City);

			locToBeStored = Address + "\n" + City;
			

			LocAddress.setText("Estimated location: \n" + locToBeStored);

			
		} else {
			AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
			builder1.setTitle("Service Unavailable");
			builder1.setMessage("Unable to get your location, check if your GPS and Network are turned on.");
			builder1.setCancelable(true);
			builder1.setNegativeButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			AlertDialog alert11 = builder1.create();
			alert11.show();

			LocAddress.setText("opps please check you gps.");
		}
	}
}
