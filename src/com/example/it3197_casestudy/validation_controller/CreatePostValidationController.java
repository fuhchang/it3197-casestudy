package com.example.it3197_casestudy.validation_controller;

import java.util.ArrayList;

import android.content.Intent;
import android.widget.Toast;

import com.example.it3197_casestudy.controller.CreateHobbyPostController;
import com.example.it3197_casestudy.crouton.Crouton;
import com.example.it3197_casestudy.crouton.Style;
import com.example.it3197_casestudy.model.HobbyPost;
import com.example.it3197_casestudy.ui_logic.CreateHobbyPost;
import com.example.it3197_casestudy.util.Settings;
import com.example.it3197_casestudy.validation.Form;
import com.example.it3197_casestudy.validation.Validate;

public class CreatePostValidationController implements Settings {

	private CreateHobbyPost activity;
	private HobbyPost post;

	public CreatePostValidationController(CreateHobbyPost activity) {
		this.activity = activity;
	}

	public void validateForm(Intent intent, Form mForm,
			ArrayList<Validate> validatorsArrList) {
		// Launch Validation
		if (mForm.validate()) {
			post = new HobbyPost();
			String grpID = intent.getStringExtra("grpID");
			post.setGrpID(Integer.parseInt(grpID));
			post.setContent(activity.getEtContent().getText().toString());
			post.setLat(activity.getLat());
			post.setLng(activity.getLng());
			CreateHobbyPostController con = new CreateHobbyPostController(
					activity, post);
			con.execute();
		} else {
			if (!validatorsArrList.get(0).isValid()) {
				Crouton crouton = Crouton.makeText(activity,
						"Please enter a Group name for hobby.", Style.ALERT);
				crouton.show();
			}
		}
	}

}
