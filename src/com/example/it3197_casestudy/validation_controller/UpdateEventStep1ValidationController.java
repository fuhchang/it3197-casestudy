package com.example.it3197_casestudy.validation_controller;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;

import com.example.it3197_casestudy.crouton.Crouton;
import com.example.it3197_casestudy.crouton.Style;
import com.example.it3197_casestudy.model.EventLocationDetail;
import com.example.it3197_casestudy.ui_logic.CreateEventStep1Activity;
import com.example.it3197_casestudy.ui_logic.CreateEventStep2Activity;
import com.example.it3197_casestudy.ui_logic.UpdateEventStep1Activity;
import com.example.it3197_casestudy.ui_logic.UpdateEventStep2Activity;
import com.example.it3197_casestudy.util.Settings;
import com.example.it3197_casestudy.validation.Form;
import com.example.it3197_casestudy.validation.Validate;
import com.example.it3197_casestudy.validation.validator.NotEmptyValidator;

public class UpdateEventStep1ValidationController implements Settings{
	private UpdateEventStep1Activity activity;
	private EventLocationDetail eventLocationDetails;
	
	public UpdateEventStep1ValidationController(UpdateEventStep1Activity activity, EventLocationDetail eventLocationDetails){
		this.activity = activity;
		this.eventLocationDetails = eventLocationDetails;
	}
	
	public void validateForm(String eventID, Intent intent,Form mForm,ArrayList<Validate> validatorsArrList, String eventDateTimeFrom, String eventDateTimeTo, String occurence){
		// Launch Validation
		if(mForm.validate()){
			intent = new Intent(activity, UpdateEventStep2Activity.class);
			intent.putExtra("eventID", eventID);
			intent.putExtra("eventName", activity.getEtEventName().getText().toString());
			intent.putExtra("eventCategory", activity.getSpinnerCategory().getSelectedItem().toString());
			intent.putExtra("eventDescription", activity.getEtDescription().getText().toString());
			intent.putExtra("noOfParticipants", activity.getSpinnerNoOfParticipants().getSelectedItem().toString());
			intent.putExtra("eventDateTimeFrom", eventDateTimeFrom);
			intent.putExtra("eventDateTimeTo", eventDateTimeTo);
			intent.putExtra("occurence", occurence);
			intent.putExtra("locationName", eventLocationDetails.getEventLocationName());
			intent.putExtra("locationAddress", eventLocationDetails.getEventLocationAddress());
			intent.putExtra("locationHyperLink", eventLocationDetails.getEventLocationHyperLink());
			intent.putExtra("lat", eventLocationDetails.getEventLocationLat());
			intent.putExtra("lng", eventLocationDetails.getEventLocationLng());
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
