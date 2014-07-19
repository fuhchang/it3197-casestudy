package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetAllJoinedHobbyGroup;
import com.example.it3197_casestudy.listview.HobbyListView;
import com.example.it3197_casestudy.model.Hobby;

import android.os.Bundle;


import android.graphics.Color;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class Hobbies_Joined extends Fragment {
	private String nric;
	ListView joinedList;
	HobbyListView hobbyList;
	ArrayList<Hobby> allHobbyList;
	public Hobbies_Joined() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.activity_hobbies__joined,
				container, false);
		
		
		joinedList = (ListView) rootView.findViewById(R.id.hobbyJoinedList);
		joinedList.setBackgroundColor(Color.GRAY);
		GetAllJoinedHobbyGroup getJoined = new GetAllJoinedHobbyGroup(this, joinedList, nric);
		getJoined.execute();
		return rootView;
	}

	public String getNric() {
		return nric;
	}

	public void setNric(String nric) {
		this.nric = nric;
	}
	
}
