package com.example.it3197_casestudy.validation_controller;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.example.it3197_casestudy.crouton.Crouton;
import com.example.it3197_casestudy.crouton.Style;
import com.example.it3197_casestudy.ui_logic.CreateEventStep1Activity;
import com.example.it3197_casestudy.ui_logic.CreateEventStep2Activity;
import com.example.it3197_casestudy.ui_logic.ViewAllEventsActivity;
import com.example.it3197_casestudy.util.Settings;
import com.example.it3197_casestudy.validation.Form;
import com.example.it3197_casestudy.validation.Validate;
import com.example.it3197_casestudy.validation.validator.NotEmptyValidator;

public class CreateEventStep2ValidationController implements Settings{
	private CreateEventStep2Activity activity;
	private String typeOfEvent;
	
	public CreateEventStep2ValidationController(CreateEventStep2Activity activity, String typeOfEvent){
		this.activity = activity;
		this.typeOfEvent = typeOfEvent;
	}
	
	public void validateForm(Intent intent, Calendar calendarFrom, Calendar calendarTo){
		Calendar calendarCurrentDate = Calendar.getInstance();
		System.out.println(calendarFrom.before(calendarCurrentDate));
		if(calendarFrom.before(calendarCurrentDate) || (calendarTo.before(calendarCurrentDate))){
			Crouton crouton = Crouton.makeText(activity,"Please choose another date after today.",Style.ALERT);
			crouton.show();
			return;
		}
		if(typeOfEvent.equals("Big Event")){
			if(calendarFrom.after(calendarTo)){
				Crouton crouton = Crouton.makeText(activity,"Please choose another date after the starting date.",Style.ALERT);
				crouton.show();
			}
			else{
				Toast.makeText(activity.getApplicationContext(),"Event created successfully.", Toast.LENGTH_SHORT).show();
				intent = new Intent(activity, ViewAllEventsActivity.class);
				activity.startActivity(intent);
				activity.finish();
			}
		}
		else{
			Toast.makeText(activity.getApplicationContext(),"Event created successfully.", Toast.LENGTH_SHORT).show();
			intent = new Intent(activity, ViewAllEventsActivity.class);
			activity.startActivity(intent);
			activity.finish();
		}
	}
}
