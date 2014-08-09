package com.example.it3197_casestudy.googlePlaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.androidmapsextensions.GoogleMap;
import com.androidmapsextensions.MarkerOptions;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.ui_logic.CreateEventStep1Activity;
import com.example.it3197_casestudy.ui_logic.SuggestLocationActivity;
import com.example.it3197_casestudy.ui_logic.ViewAllEventsActivity;
import com.example.it3197_casestudy.util.EventExpandedListAdapter;
import com.example.it3197_casestudy.util.RecommendedLocationListAdapter;
import com.example.it3197_casestudy.validation.Form;
import com.example.it3197_casestudy.validation.Validate;
import com.example.it3197_casestudy.validation_controller.CreateEventStep1ValidationController;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class CheckPlaces extends AsyncTask<Void,Void,Object> {
	private ProgressDialog dialog;	
	private CreateEventStep1Activity activity;
	private String notRecommendedPlaces;
	private Location loc;
	private ArrayList<Place> notRecommendedPlacesArrList;
	Intent intent;
	Form mForm;
	ArrayList<Validate> validatorsArrList; 
	String posterFileName;
	CreateEventStep1ValidationController validationController;
	
	public CheckPlaces(CreateEventStep1Activity activity, String notRecommendedPlaces, Location loc, Intent intent,Form mForm, ArrayList<Validate> validatorsArrList, String posterFileName, CreateEventStep1ValidationController validationController) {
	   this.activity = activity;
	   this.notRecommendedPlaces = notRecommendedPlaces;
	   this.loc = loc;
	   
	   this.intent = intent;
	   this.mForm = mForm;
	   this.validatorsArrList = validatorsArrList;
	   this.posterFileName = posterFileName;
	   this.validationController = validationController;
	}

	@Override
	protected void onPostExecute(Object result) {
	   super.onPostExecute(result);
	   if (dialog.isShowing()) {
		   dialog.dismiss();
	   }
	   if(notRecommendedPlacesArrList.size() > 0){
		   activity.setNotRecommendedPlacesArrList(notRecommendedPlacesArrList);

		   validationController.validateForm(intent, mForm, validatorsArrList,posterFileName,notRecommendedPlacesArrList);
	   }
	   else{
			Toast.makeText(activity, "Invalid address. Please put a valid address", Toast.LENGTH_LONG).show();
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
	   if(!notRecommendedPlaces.equals("")){
		   notRecommendedPlacesArrList = service.findPlaces(loc.getLatitude(), loc.getLongitude(), notRecommendedPlaces);
	   }
	   
	   return null;
	}
}
