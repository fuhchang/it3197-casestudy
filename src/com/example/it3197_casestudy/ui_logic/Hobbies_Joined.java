package com.example.it3197_casestudy.ui_logic;

import com.example.it3197_casestudy.R;

import android.os.Bundle;

import android.app.ListFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class Hobbies_Joined extends Fragment {

	public Hobbies_Joined() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.activity_hobbies__joined,
				container, false);
		TextView txtText = (TextView) rootView.findViewById(R.id.JoinedTitle);
		txtText.setText("muhahaha");
		return rootView;
	}

}
