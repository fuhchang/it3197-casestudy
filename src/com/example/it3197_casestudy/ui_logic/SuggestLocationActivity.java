package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.model.MashUpData;
import com.example.it3197_casestudy.model.MyItem;
import com.example.it3197_casestudy.one_map_controller.GetMashUpData;
import com.example.it3197_casestudy.util.MyClusterRenderer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class SuggestLocationActivity extends Activity {
	GoogleMap map;
	TextView tvEventLocationName,tvEventLocationAddress,tvEventLocationHyperlink,tvEventLocationLat,tvEventLocationLng;
	TableLayout tableLayoutEventLocationInformation;
	
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggest_location);
		// Get a handle to the Map Fragment
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		tableLayoutEventLocationInformation = (TableLayout) findViewById(R.id.table_layout_event_location_information);
		
		tvEventLocationName = (TextView) findViewById(R.id.tv_event_location_name);
		tvEventLocationAddress = (TextView) findViewById(R.id.tv_event_location_address);
		tvEventLocationHyperlink = (TextView) findViewById(R.id.tv_event_location_hyperlink);
		tvEventLocationLat = (TextView) findViewById(R.id.tv_event_location_lat);
		tvEventLocationLng = (TextView) findViewById(R.id.tv_event_location_lng);
		
		LatLng singapore = new LatLng(1.3450, 103.8250);

		map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 10));

		// Show the Up button in the action bar.
		setupActionBar();
		
		tableLayoutEventLocationInformation.setVisibility(View.GONE);
		GetMashUpData getMashUpData = new GetMashUpData(
				SuggestLocationActivity.this, "HOTELS");
		getMashUpData.execute();
	}
	
	public void setThings(ClusterManager<MyItem> mClusterManager){

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
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onClick(View v){
		int itemId = v.getId();
		switch(itemId){
		case R.id.btn_select_location:
			break;
		}
	}
}
