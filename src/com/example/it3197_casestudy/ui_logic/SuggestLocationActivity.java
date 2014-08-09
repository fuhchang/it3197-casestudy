package com.example.it3197_casestudy.ui_logic;

import java.text.DecimalFormat;
import java.util.*;

import com.androidmapsextensions.ClusteringSettings;
import com.androidmapsextensions.DefaultClusterOptionsProvider;
import com.androidmapsextensions.GoogleMap;
import com.androidmapsextensions.MapFragment;
import com.androidmapsextensions.Marker;
import com.androidmapsextensions.SupportMapFragment;
import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.model.MashUpData;
import com.example.it3197_casestudy.model.MyItem;
import com.example.it3197_casestudy.one_map_controller.GetMashUpData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import android.location.Location;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;

public class SuggestLocationActivity extends Activity {
	GoogleMap map;
	TextView tvEventLocationName,tvEventLocationAddress,tvEventLocationHyperlink,tvEventLocationLat,tvEventLocationLng;
	TableLayout tableLayoutEventLocationInformation;
	Button btnSelectLocation;

	ArrayList<Integer> selectedIndex = new ArrayList<Integer>();
	private String artsThemeList[] = {"Monuments","Parks","Tourism"};
	private String artsThemeOneMapList[] = {"MONUMENTS","NATIONALPARKS","TOURISM"};
	private String educationThemeList[] = {"Community Clubs","Heritage Sites","Libraries"};
	private String educationThemeOneMapList[] = {"COMMUNITYCLUBS","HERITAGESITES","LIBRARIES"};
	private String familyThemeList[] = {"Elder Care Services","Family Services ","Voluntary Welfare Organizations"};
	private String familyThemeOneMapList[] = {"Eldercare","Family","VoluntaryWelfareOrgs"};
	private String healthThemeList[] = {"Exercise Facilities","Retail Pharmacy Locations","Relax@SG"};
	private String healthThemeOneMapList[] = {"EXERCISEFACILITIES","REGISTERED_PHARMACY","RelaxSG"};
	private String category;

	String[] finalList = new String[3];
	String[] finalOneMapList = new String [3];
	
    ArrayList<MyItem> myItemArrList = new ArrayList<MyItem>();

    private MapFragment mapFragment;
    
	public GoogleMap getMap() {
		return map;
	}

	public void setMap(GoogleMap map) {
		this.map = map;
	}

	public TextView getTvEventLocationName() {
		return tvEventLocationName;
	}

	public void setTvEventLocationName(TextView tvEventLocationName) {
		this.tvEventLocationName = tvEventLocationName;
	}

	public TextView getTvEventLocationAddress() {
		return tvEventLocationAddress;
	}

	public void setTvEventLocationAddress(TextView tvEventLocationAddress) {
		this.tvEventLocationAddress = tvEventLocationAddress;
	}

	public TextView getTvEventLocationHyperlink() {
		return tvEventLocationHyperlink;
	}

	public void setTvEventLocationHyperlink(TextView tvEventLocationHyperlink) {
		this.tvEventLocationHyperlink = tvEventLocationHyperlink;
	}

	public TextView getTvEventLocationLat() {
		return tvEventLocationLat;
	}

	public void setTvEventLocationLat(TextView tvEventLocationLat) {
		this.tvEventLocationLat = tvEventLocationLat;
	}

	public TextView getTvEventLocationLng() {
		return tvEventLocationLng;
	}

	public void setTvEventLocationLng(TextView tvEventLocationLng) {
		this.tvEventLocationLng = tvEventLocationLng;
	}

	public TableLayout getTableLayoutEventLocationInformation() {
		return tableLayoutEventLocationInformation;
	}

	public void setTableLayoutEventLocationInformation(
			TableLayout tableLayoutEventLocationInformation) {
		this.tableLayoutEventLocationInformation = tableLayoutEventLocationInformation;
	}

	public ArrayList<MyItem> getMyItemArrList() {
		return myItemArrList;
	}

	public void setMyItemArrList(ArrayList<MyItem> myItemArrList) {
		this.myItemArrList = myItemArrList;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggest_location);
		// Get a handle to the Map Fragment
		
		savedInstanceState = getIntent().getExtras();
		if(savedInstanceState != null){
			category = savedInstanceState.getString("category");
			System.out.println("Category:"+category);
			if(category.equals("Arts")){
				passInArr(artsThemeList,artsThemeOneMapList);
			}
			else if(category.equals("Education")){
				passInArr(educationThemeList,educationThemeOneMapList);
			}
			else if(category.equals("Family")){
				passInArr(familyThemeList,familyThemeOneMapList);
			}
			else if(category.equals("Health")){
				passInArr(healthThemeList,healthThemeOneMapList);
			}
		}

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		map = mapFragment.getExtendedMap();
		map.setBuildingsEnabled(true);
		map.setIndoorEnabled(true);

        ClusteringSettings settings = new ClusteringSettings();

        settings.clusterOptionsProvider(new DefaultClusterOptionsProvider(getResources())).addMarkersDynamically(true);

        map.setClustering(settings);
        
		tableLayoutEventLocationInformation = (TableLayout) findViewById(R.id.table_layout_event_location_information);
		
		tvEventLocationName = (TextView) findViewById(R.id.tv_event_location_name);
		tvEventLocationAddress = (TextView) findViewById(R.id.tv_event_location_address);
		tvEventLocationHyperlink = (TextView) findViewById(R.id.tv_event_location_hyperlink);
		tvEventLocationLat = (TextView) findViewById(R.id.tv_event_location_lat);
		tvEventLocationLng = (TextView) findViewById(R.id.tv_event_location_lng);
		
		btnSelectLocation = (Button) findViewById(R.id.btn_select_location);
		
		btnSelectLocation.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String name = tvEventLocationName.getText().toString();
				String address = tvEventLocationAddress.getText().toString();
				String hyperlink = tvEventLocationHyperlink.getText().toString();
				double lat = Double.parseDouble(tvEventLocationLat.getText().toString());
				double lng = Double.parseDouble(tvEventLocationLng.getText().toString());
				Intent output = new Intent();
				output.putExtra("eventLocationName", name);
				output.putExtra("eventLocationAddress", address);
				output.putExtra("eventLocationHyperLink", hyperlink);
				output.putExtra("eventLocationLat", lat);
				output.putExtra("eventLocationLng", lng);
				setResult(RESULT_OK,output);
				finish();
			}
			
		});
		
		LatLng singapore = new LatLng(1.3450, 103.8250);

		map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 10));  

		// Show the Up button in the action bar.
		setupActionBar();
		tableLayoutEventLocationInformation.setVisibility(View.GONE);
		
	}
	
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.suggest_location_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.select_theme:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Select Theme");
		
			builder.setItems(finalList, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub{
					String theme = finalOneMapList[which];
					selectedIndex.add(which);
					GetMashUpData getMashUpData = new GetMashUpData(SuggestLocationActivity.this, theme);
					getMashUpData.execute();
				}
			});
			builder.create().show();
			break;
		case R.id.suggest_location_for_event:
			//myItemArrList.get(0).get

			if(myItemArrList.size() > 0){
				double currentLat = map.getMyLocation().getLatitude();
				double currentLng = map.getMyLocation().getLongitude();
				

				double currentDist = 0.00;
				int closest = -1;
				for(int i = 0;i<myItemArrList.size();i++){
					double testLat = myItemArrList.get(i).getPosition().latitude;
					double testLng = myItemArrList.get(i).getPosition().latitude;
	
					double distanceInMeters = distanceFrom(currentLat, currentLng, testLat, testLng);
					if((closest == -1) || (currentDist > distanceInMeters)){
						currentDist = distanceInMeters;
						closest = i;
					}
				}
				
				List<Marker> markersArrList = map.getMarkers();
		        
		        markersArrList.get(closest).showInfoWindow();
		        map.moveCamera(CameraUpdateFactory.newLatLngZoom(markersArrList.get(closest).getPosition(), 15));
			}
			else{
				Toast.makeText(this,"Please select a theme.", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.deselect_all_themes:
			if(myItemArrList.size() > 0){
				for(int i=0;i<myItemArrList.size();i++){
					myItemArrList.remove(i);
					
				}
				for(int i=0;i<selectedIndex.size();i++){
					selectedIndex.remove(i);
				}
				map.clear();
			}
			else{
				Toast.makeText(this,"Please select a theme.", Toast.LENGTH_LONG).show();
			}
			break;

		case R.id.refresh_map:
			if(myItemArrList.size() > 0){
				map.clear();
				for(int i=0;i<selectedIndex.size();i++){
					String theme = finalOneMapList[selectedIndex.get(i)];
					GetMashUpData getMashUpData = new GetMashUpData(SuggestLocationActivity.this, theme);
					getMashUpData.execute();
				}
			}
			else{
				Toast.makeText(this,"Please select a theme.", Toast.LENGTH_LONG).show();
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void passInArr(String[] themesArr,String[] themesOneMapArr){
		for(int i=0;i<finalList.length;i++){
			finalList[i] = themesArr[i];
			System.out.println(themesArr[i]);
		}
		for(int i=0;i<finalOneMapList.length;i++){
			finalOneMapList[i] = themesOneMapArr[i];
		}
	}
	
	@SuppressLint("UseValueOf")
	public double distanceFrom(double lat1, double lng1, double lat2, double lng2) {
	    double earthRadius = 3958.75;
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;
	    int meterConversion = 1609;
	    return new Double(dist * meterConversion).floatValue();    // this will return distance
	}
}
