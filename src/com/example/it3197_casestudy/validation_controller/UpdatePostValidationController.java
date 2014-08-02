package com.example.it3197_casestudy.validation_controller;

import java.util.ArrayList;

import android.content.Intent;

import com.example.it3197_casestudy.controller.updatePost;
import com.example.it3197_casestudy.model.HobbyPost;
import com.example.it3197_casestudy.ui_logic.UpdatePost;
import com.example.it3197_casestudy.util.Settings;
import com.example.it3197_casestudy.validation.Form;
import com.example.it3197_casestudy.validation.Validate;

public class UpdatePostValidationController implements Settings{
	private String userNric;
	private int postID;
	private int GrpID;
	private UpdatePost upPost;
	private double lat;
	private double lng;
	private String postTitle;
	private String content;
	private String grpName;
	private String adminNric;
	public UpdatePostValidationController(UpdatePost up, String userNric, int postID, int GrpID, double lat, double lng,String postTitle, String content, String grpName, String adminNric){
		this.upPost = up;
		this.userNric = userNric;
		this.postID = postID;
		this.GrpID = GrpID;
		this.lat = lat;
		this.lng = lng;
		this.postTitle = postTitle;
		this.content = content;
		this.grpName = grpName;
		this.adminNric = adminNric;
	}
	
	public void validateForm(Intent intent, Form mForm, ArrayList<Validate> validatorsArrList){
		if(mForm.validate()){ 
			HobbyPost hobbyPost = new HobbyPost();
			hobbyPost.setPostID(postID);
			hobbyPost.setGrpID(GrpID);
			hobbyPost.setPosterNric(userNric);
			hobbyPost.setLat(lat);
			hobbyPost.setLng(lng);
			hobbyPost.setPostTitle(postTitle);
			hobbyPost.setContent(content);
			updatePost up = new updatePost(upPost, hobbyPost, grpName, adminNric);
			up.execute();
		}
	}
}
