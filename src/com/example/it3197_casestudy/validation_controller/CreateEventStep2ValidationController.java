package com.example.it3197_casestudy.validation_controller;

import java.util.ArrayList;

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
	private CreateEventStep1Activity activity;
	private String typeOfEvent;
	
	public CreateEventStep2ValidationController(CreateEventStep1Activity activity, String typeOfEvent){
		this.activity = activity;
		this.typeOfEvent = typeOfEvent;
	}
	
	public void validateForm(Intent intent){
		if(true){
			
		}
		else{
			Toast.makeText(activity.getApplicationContext(),"Event created successfully.", Toast.LENGTH_SHORT).show();
			intent = new Intent(activity, ViewAllEventsActivity.class);
			activity.startActivity(intent);
			activity.finish();
		}
	}
}
