package com.example.it3197_casestudy.validation_controller;

import java.net.URISyntaxException;
import java.util.ArrayList;

import android.content.Intent;
import android.util.Log;

import com.example.it3197_casestudy.crouton.Crouton;
import com.example.it3197_casestudy.crouton.Style;
import com.example.it3197_casestudy.ui_logic.CreateGroupActivityStep1;
import com.example.it3197_casestudy.ui_logic.CreateGroupActivityStep2;
import com.example.it3197_casestudy.ui_logic.CreateGroupActivityStep3;
import com.example.it3197_casestudy.util.Settings;
import com.example.it3197_casestudy.validation.Form;
import com.example.it3197_casestudy.validation.Validate;

public class CreateGrpStep2ValidationController implements Settings{
	private CreateGroupActivityStep2 activity;

	public CreateGrpStep2ValidationController(CreateGroupActivityStep2 activity) {
		this.activity = activity;
	}
	
	
	public void validateForm(Intent intent,Form mForm,ArrayList<Validate> validatorsArrList){
		// Launch Validation
		if(mForm.validate()){
			intent = new Intent(activity, CreateGroupActivityStep3.class);
			intent.putExtra("eventDesc", activity.getGrpDesc().toString());
			activity.startActivity(intent);
			activity.finish();
		}
		else{
			if(!validatorsArrList.get(0).isValid()){
				Crouton crouton = Crouton.makeText(activity,"Please tell us about the group.",Style.ALERT);
				crouton.show();
			}
		}
	}
}
