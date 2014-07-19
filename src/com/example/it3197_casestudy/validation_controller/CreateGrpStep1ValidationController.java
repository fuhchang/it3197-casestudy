package com.example.it3197_casestudy.validation_controller;

import java.util.ArrayList;

import android.content.Intent;
import android.widget.Toast;

import com.example.it3197_casestudy.crouton.Crouton;
import com.example.it3197_casestudy.crouton.Style;
import com.example.it3197_casestudy.ui_logic.CreateGroupActivityStep1;
import com.example.it3197_casestudy.ui_logic.CreateGroupActivityStep2;
import com.example.it3197_casestudy.util.Settings;
import com.example.it3197_casestudy.validation.Form;
import com.example.it3197_casestudy.validation.Validate;

public class CreateGrpStep1ValidationController implements Settings {
	private CreateGroupActivityStep1 activity;

	public CreateGrpStep1ValidationController(CreateGroupActivityStep1 activity) {
		this.activity = activity;
	}
	
	
	public void validateForm(Intent intent,Form mForm,ArrayList<Validate> validatorsArrList){
		// Launch Validation
		if(mForm.validate()){
			intent = new Intent(activity, CreateGroupActivityStep2.class);
			intent.putExtra("eventName", activity.getgTitle().getText().toString());
			intent.putExtra("category", activity.getCate().getSelectedItem().toString());
			intent.putExtra("nric", activity.getNric());
			activity.startActivity(intent);
			activity.finish();
		}
		else{
			if(!validatorsArrList.get(0).isValid()){
				Crouton crouton = Crouton.makeText(activity,"Please enter a Group name for hobby.",Style.ALERT);
				crouton.show();
			}
		}
	}
	
}
