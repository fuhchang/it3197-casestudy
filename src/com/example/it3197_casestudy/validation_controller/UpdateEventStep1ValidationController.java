package com.example.it3197_casestudy.validation_controller;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;

import com.example.it3197_casestudy.crouton.Crouton;
import com.example.it3197_casestudy.crouton.Style;
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
	private String typeOfEvent;
	
	public UpdateEventStep1ValidationController(UpdateEventStep1Activity activity, String typeOfEvent){
		this.activity = activity;
		this.typeOfEvent = typeOfEvent;
	}
	
	public void validateForm(String eventID, Intent intent,Form mForm,ArrayList<Validate> validatorsArrList, String eventDateTimeFrom, String eventDateTimeTo, String occurence){
		// Launch Validation
		if(mForm.validate()){
			intent = new Intent(activity, UpdateEventStep2Activity.class);
			intent.putExtra("eventID", eventID);
			intent.putExtra("eventName", activity.getEtEventName().getText().toString());
			intent.putExtra("eventCategory", activity.getSpinnerCategory().getSelectedItem().toString());
			intent.putExtra("eventDescription", activity.getEtDescription().getText().toString());
			intent.putExtra("typeOfEvent", typeOfEvent);
			intent.putExtra("eventLocation", activity.getEtLocation().getText().toString());
			intent.putExtra("noOfParticipants", activity.getSpinnerNoOfParticipants().getSelectedItem().toString());
			intent.putExtra("eventDateTimeFrom", eventDateTimeFrom);
			intent.putExtra("eventDateTimeTo", eventDateTimeTo);
			intent.putExtra("occurence", occurence);
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
			if(typeOfEvent.equals("Big Event")){
			}
			else{
				if(!validatorsArrList.get(2).isValid()){
					Crouton crouton = Crouton.makeText(activity,"Please enter a event location.",Style.ALERT);
					crouton.show();
					return;
				}
			}
		}
	}
}