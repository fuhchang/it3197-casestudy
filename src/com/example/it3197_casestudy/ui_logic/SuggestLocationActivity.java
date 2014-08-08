package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.model.MashUpData;
import com.example.it3197_casestudy.model.MyItem;
import com.example.it3197_casestudy.one_map_controller.GetMashUpData;
import com.example.it3197_casestudy.util.MyClusterRenderer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.MarkerManager.Collection;
import com.google.maps.android.clustering.algo.GridBasedAlgorithm;
import com.google.maps.android.clustering.algo.PreCachingAlgorithmDecorator;
import com.google.maps.android.clustering.algo.StaticCluster;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import android.os.Bundle;
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
	
	ClusterManager<MyItem> mClusterManager;
    ArrayList<MyItem> myItemArrList = new ArrayList<MyItem>();
	
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
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setBuildingsEnabled(true);
		map.setIndoorEnabled(true);

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
	
	public void setThings(ClusterManager<MyItem> mClusterManager){
		this.mClusterManager = mClusterManager;
        mClusterManager.setRenderer(new MyClusterRenderer(SuggestLocationActivity.this,map,mClusterManager));
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
}
