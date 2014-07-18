package com.example.it3197_casestudy.ui_logic;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.controller.UpdateHobbyGroup;
import com.example.it3197_casestudy.model.Hobby;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.model.Marker;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditHobbyGrp extends Activity {
	EditText postNameEt, etContent;
	TextView addressTV, hintTV;
	GoogleMap map;
	String Address;
	String City;
	Double Lat,Lng;
	private static final int selectLoc = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_hobby_grp);
		postNameEt = (EditText) findViewById(R.id.postNameET);
		postNameEt.setText(getIntent().getExtras().getString("grpName"));
		etContent = (EditText) findViewById(R.id.etContent);
		etContent.setText(getIntent().getExtras().getString("grpContent"));
		addressTV = (TextView) findViewById(R.id.LocAddress); 
		hintTV= (TextView) findViewById(R.id.HintText);
		hintTV.setText("Click on the Address to change it.");
		Lat = getIntent().getExtras().getDouble("Lat");
		Lng = getIntent().getExtras().getDouble("Lng");
		
		addressTV.setText(getAddress(getIntent().getExtras().getDouble("Lat"), getIntent().getExtras().getDouble("Lng")));
		addressTV.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(EditHobbyGrp.this, SelectPostLocation.class);
				intent.putExtra("Lat", Lat);
				intent.putExtra("Lng", Lng);
				startActivityForResult(intent,selectLoc);
			}
			
		});
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_hobby_grp, menu);
		return true;
	}

	public String getAddress(double lat, double lng){
		String location = null;
		try {
			Geocoder geocoder = new Geocoder(this, Locale.getDefault());
			List<Address> addresses;
			addresses = geocoder.getFromLocation(lat, lng, 1);
			Address = addresses.get(0).getAddressLine(0);
			City = addresses.get(0).getAddressLine(1);
			location = Address + " " + City;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return location;
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode == RESULT_OK){
			switch(requestCode){
			case selectLoc:
				Lat = Double.parseDouble(data.getStringExtra("selectedLat"));
				Lng = Double.parseDouble(data.getStringExtra("selectedLon"));
					String selectedAdd = data.getStringExtra("selectedAdd");
					addressTV.setText(selectedAdd);
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.action_update:
			Toast.makeText(getApplicationContext(),	getIntent().getExtras().getString("grpType"), Toast.LENGTH_LONG).show();
			Hobby hobby = new Hobby();
			hobby.setGroupID(getIntent().getExtras().getInt("grpID"));
			hobby.setGroupName(postNameEt.getText().toString());
			hobby.setCategory(getIntent().getExtras().getString("grpType"));
			hobby.setDescription(etContent.getText().toString());
			hobby.setLat(Lat);
			hobby.setLng(Lng);
			UpdateHobbyGroup updateGrp = new UpdateHobbyGroup(this, hobby);
			updateGrp.execute();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
