package com.example.it3197_casestudy.validation_controller;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.example.it3197_casestudy.crouton.Crouton;
import com.example.it3197_casestudy.crouton.Style;
import com.example.it3197_casestudy.googlePlaces.Place;
import com.example.it3197_casestudy.model.EventLocationDetail;
import com.example.it3197_casestudy.ui_logic.CreateEventStep1Activity;
import com.example.it3197_casestudy.ui_logic.CreateEventStep2Activity;
import com.example.it3197_casestudy.util.Settings;
import com.example.it3197_casestudy.validation.Form;
import com.example.it3197_casestudy.validation.Validate;
import com.example.it3197_casestudy.validation.validator.NotEmptyValidator;

public class CreateEventStep1ValidationController implements Settings{
	private CreateEventStep1Activity activity;
	private EventLocationDetail eventLocationDetails;
	private ArrayList<Place> notRecommendedPlacesArrList;
	
	public CreateEventStep1ValidationController(CreateEventStep1Activity activity, EventLocationDetail eventLocationDetails){
		this.activity = activity;
		this.eventLocationDetails = eventLocationDetails;
	}
	
	public void validateForm(Intent intent,Form mForm,ArrayList<Validate> validatorsArrList, String posterFileName, ArrayList<Place> notRecommendedPlacesArrList){
		// Launch Validation
		if(mForm.validate()){
			for(int i=0;i<notRecommendedPlacesArrList.size();i++){
				if(activity.getEtLocation().getText().toString().equals(notRecommendedPlacesArrList.get(i).getVicinity())){
					Toast.makeText(activity, "Location is not suitable for the event. Please select another location.", Toast.LENGTH_LONG).show();
					return;
				}
			}
			intent = new Intent(activity, CreateEventStep2Activity.class);
			intent.putExtra("posterFileName", posterFileName);
			intent.putExtra("eventName", activity.getEtEventName().getText().toString());
			intent.putExtra("eventCategory", activity.getSpinnerCategory().getSelectedItem().toString());
			intent.putExtra("eventDescription", activity.getEtDescription().getText().toString());
			intent.putExtra("eventLocation", activity.getEtLocation().getText().toString());
			intent.putExtra("noOfParticipants", activity.getSpinnerNoOfParticipants().getSelectedItem().toString());
			intent.putExtra("locationName", eventLocationDetails.getEventLocationName());
			intent.putExtra("locationAddress", eventLocationDetails.getEventLocationAddress());
			intent.putExtra("locationHyperLink", eventLocationDetails.getEventLocationHyperLink());
			intent.putExtra("lat", eventLocationDetails.getEventLocationLat());
			intent.putExtra("lng", eventLocationDetails.getEventLocationLng());
			intent.putExtra("requestHelp", activity.getcBoxRequestHelp().isChecked());
			activity.startActivity(intent);
			activity.finish();
		}
		else{
			if(!validatorsArrList.get(0).isValid()){
				Crouton crouton = Crouton.makeText(activity,"Please enter a event name.",Style.ALERT);
				crouton.show();
				return;
			}
			if(!validatorsArrList.get(1).isValid()){
				Crouton crouton = Crouton.makeText(activity,"Please enter a event description.",Style.ALERT);
				crouton.show();
				return;
			}
			if(!validatorsArrList.get(2).isValid()){
				Crouton crouton = Crouton.makeText(activity,"Please enter a event location.",Style.ALERT);
				crouton.show();
				return;
			}
		}
	}
}
