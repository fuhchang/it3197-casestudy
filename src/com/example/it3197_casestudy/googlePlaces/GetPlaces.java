package com.example.it3197_casestudy.googlePlaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.androidmapsextensions.GoogleMap;
import com.androidmapsextensions.MarkerOptions;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.ui_logic.SuggestLocationActivity;
import com.example.it3197_casestudy.ui_logic.ViewAllEventsActivity;
import com.example.it3197_casestudy.util.EventExpandedListAdapter;
import com.example.it3197_casestudy.util.RecommendedLocationListAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

public class GetPlaces extends AsyncTask<Void,Void,Object> {
	private ProgressDialog dialog;	
	private SuggestLocationActivity activity;
	private String recommendedPlaces;
	private GoogleMap map;
	private Location loc;
	private ArrayList<Place> recommendedPlacesArrList ;

	public GetPlaces(SuggestLocationActivity activity, String recommendedPlaces, GoogleMap map, Location loc) {
	   this.activity = activity;
	   this.recommendedPlaces = recommendedPlaces;
	   this.map = map;
	   this.loc = loc;
	}

	@Override
	protected void onPostExecute(Object result) {
	   super.onPostExecute(result);
	   if (dialog.isShowing()) {
		   dialog.dismiss();
	   }
	   if(recommendedPlacesArrList.size() > 0){
		   for (int i = 0; i < recommendedPlacesArrList.size(); i++) {
			   	ArrayList<String> listDataHeader = new ArrayList<String>();
				HashMap<String, List<Place>> listDataChild = new HashMap<String, List<Place>>();
		 
			    listDataHeader.add("Nearby Places within 1km");
			    listDataChild.put(listDataHeader.get(0), recommendedPlacesArrList);
		        System.out.println(recommendedPlacesArrList.get(i).getName());
			    RecommendedLocationListAdapter adapter = new RecommendedLocationListAdapter(activity,activity,listDataHeader, listDataChild, activity.getLvRecommendedLocations());
			    activity.getLvRecommendedLocations().setAdapter(adapter);
			    activity.getLvRecommendedLocations().expandGroup(0);
			    activity.getLvRecommendedLocations().setOnGroupClickListener(new OnGroupClickListener() {
					@Override
					public boolean onGroupClick(ExpandableListView arg0,View arg1, int arg2, long arg3) {
						return true;
					}
			    });
		   }
	   }
	}

	@Override
	protected void onPreExecute() {
	   super.onPreExecute();
	   dialog = new ProgressDialog(activity);
	   dialog.setCancelable(false);
	   dialog.setMessage("Loading..");
	   dialog.isIndeterminate();
	   dialog.show();
	}

	@Override
	protected Object doInBackground(Void... arg0) {
	   PlacesService service = new PlacesService();
	   if(!recommendedPlaces.equals(""))
		   recommendedPlacesArrList = service.findPlaces(loc.getLatitude(), loc.getLongitude(), recommendedPlaces); 
	   
	   return null;
	}
}
