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
import com.example.it3197_casestudy.googlePlaces.GetPlaces;
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
import android.widget.ExpandableListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;

public class SuggestLocationActivity extends Activity {
	GoogleMap map;
	ExpandableListView lvRecommendedLocations;

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

	private List<Marker> markersArrList;
	
	String[] finalList = new String[3];
	String[] finalOneMapList = new String [3];
	String finalCategory = "";
	
    ArrayList<MyItem> myItemArrList = new ArrayList<MyItem>();

    private MapFragment mapFragment;
    
	public GoogleMap getMap() {
		return map;
	}

	public void setMap(GoogleMap map) {
		this.map = map;
	}

	public ArrayList<MyItem> getMyItemArrList() {
		return myItemArrList;
	}

	public void setMyItemArrList(ArrayList<MyItem> myItemArrList) {
		this.myItemArrList = myItemArrList;
	}

	public ExpandableListView getLvRecommendedLocations() {
		return lvRecommendedLocations;
	}

	public void setLvRecommendedLocations(ExpandableListView lvRecommendedLocations) {
		this.lvRecommendedLocations = lvRecommendedLocations;
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
				passInArr(artsThemeList,artsThemeOneMapList,category);
			}
			else if(category.equals("Education")){
				passInArr(educationThemeList,educationThemeOneMapList,category);
			}
			else if(category.equals("Family")){
				passInArr(familyThemeList,familyThemeOneMapList,category);
			}
			else if(category.equals("Health")){
				passInArr(healthThemeList,healthThemeOneMapList,category);
			}
		}

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        lvRecommendedLocations = (ExpandableListView) findViewById(R.id.lv_view_all_recommended_locations);
		map = mapFragment.getExtendedMap();
		map.setBuildingsEnabled(true);
		map.setIndoorEnabled(true);

        ClusteringSettings settings = new ClusteringSettings();

        settings.clusterOptionsProvider(new DefaultClusterOptionsProvider(getResources())).addMarkersDynamically(true);
        map.setClustering(settings);
		
		LatLng singapore = new LatLng(1.3450, 103.8250);

		map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 10));  
		
		// Show the Up button in the action bar.
		setupActionBar();

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
					// TODO Auto-generated method stub
					boolean selectedAlready = false;
					for(int i = 0;i<selectedIndex.size();i++){
						if(selectedIndex.get(i) == which){
							selectedAlready = true;
							break;
						}
					}
					if(!selectedAlready){
						String theme = finalOneMapList[which];
						selectedIndex.add(which);
						GetMashUpData getMashUpData = new GetMashUpData(SuggestLocationActivity.this, theme, finalCategory);
						getMashUpData.execute();
					}
					else{
						Toast.makeText(SuggestLocationActivity.this,"This theme has been selected already.", Toast.LENGTH_LONG).show();
					}
				}
			});
			builder.create().show();
			break;
		case R.id.suggest_location_for_event:
			//myItemArrList.get(0).get

			if(selectedIndex.size() > 0){				
				markersArrList = map.getMarkers();
				int closest = findClosestMarker();
		        
		        map.moveCamera(CameraUpdateFactory.newLatLngZoom(markersArrList.get(closest).getPosition(), 18));
		        markersArrList.get(closest).showInfoWindow();
			}
			else{
				Toast.makeText(this,"Please select a theme.", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.deselect_all_themes:
			if(selectedIndex.size() > 0){
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
			if(selectedIndex.size() > 0){
				map.clear();
				for(int i=0;i<selectedIndex.size();i++){
					String theme = finalOneMapList[selectedIndex.get(i)];
					GetMashUpData getMashUpData = new GetMashUpData(SuggestLocationActivity.this, theme,finalCategory);
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
	
	public void passInArr(String[] themesArr,String[] themesOneMapArr, String category){
		for(int i=0;i<finalList.length;i++){
			finalList[i] = themesArr[i];
			System.out.println(themesArr[i]);
		}
		for(int i=0;i<finalOneMapList.length;i++){
			finalOneMapList[i] = themesOneMapArr[i];
		}
		finalCategory = category;
	}
		
	//Find the closest marker to the current location
	public int findClosestMarker() {

		double lat1 = map.getMyLocation().getLatitude();
		double lon1 = map.getMyLocation().getLongitude();
		
		double pi = Math.PI;
		double R = 6371; //equatorial radius
	    double[] distances = new double[markersArrList.size()];
	    int closest = -1;

	    for(int i=0;i<markersArrList.size(); i++ ) {  
	    	double lat2 = markersArrList.get(i).getPosition().latitude;
	    	double lon2 = markersArrList.get(i).getPosition().longitude;

	    	double chLat = lat2-lat1;
	    	double chLon = lon2-lon1;

	    	double dLat = chLat*(pi/180);
	    	double dLon = chLon*(pi/180);

	    	double rLat1 = lat1*(pi/180);
	    	double rLat2 = lat2*(pi/180);

	    	double a = Math.sin(dLat/2) * Math.sin(dLat/2) + 
	                    Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(rLat1) * Math.cos(rLat2); 
	    	double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
	    	double d = R * c;

	        distances[i] = d;
	        if ( closest == -1 || d < distances[closest] ) {
	            closest = i;
	        }
	    }
	    return closest;
	}
}
