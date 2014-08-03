package com.example.it3197_casestudy.util;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.model.Hobby;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class AvailableHobbyAdapter extends ArrayAdapter<Hobby> {
	private Activity context;
	private ArrayList<Hobby> hobbyList;
	public AvailableHobbyAdapter(Activity context, ArrayList<Hobby> hobbyList) {
		super(context, R.layout.activity_available_hobby_adapter, hobbyList);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.hobbyList =hobbyList;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.activity_available_hobby_adapter, null, true);
		
		
		
		return rowView;
	}

	
	

}
