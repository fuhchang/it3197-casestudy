package com.example.it3197_casestudy.controller;

import com.example.it3197_casestudy.util.Settings;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class CreateHobbyPost extends AsyncTask<Object, Object, Object> implements Settings{
	private ProgressDialog dialog;
	CreateHobbyPost activity;
	/*
	public CreateHobbyPost(CreateHobbyPost activity, HobbyPost hobbypost){
		
	}
	*/
	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
/*
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(activity, "Creating Hobby Group",
				"Creatng....", true);
	}
	*/
	
}
