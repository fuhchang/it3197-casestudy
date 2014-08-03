package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetAllHobbyGroup;
import com.example.it3197_casestudy.model.Hobby;
import com.example.it3197_casestudy.util.HobbyListView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Hobbies_All extends Fragment {
	private String nric;
	ListView allList;
	HobbyListView hobbyList;
	ArrayList<Hobby> allHobbyList;
		
	public Hobbies_All(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.activity_hobbies__all,
				container, false);
		allList = (ListView) rootView.findViewById(R.id.hobbyAllList);
		allList.setBackgroundColor(Color.LTGRAY);
		GetAllHobbyGroup getAll = new GetAllHobbyGroup(this, allList, nric);
		getAll.execute();
		return rootView;
	}
	
	public String getNric(){
		return nric;
	}
	
	public void setNric(String nric){
		this.nric = nric;
	}
}
