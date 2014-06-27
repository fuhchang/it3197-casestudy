package com.example.it3197_casestudy.ui_logic;

import com.example.it3197_casestudy.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CreateFragment extends Fragment {
	public CreateFragment(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_create, container, false);
		return rootView;
	}
	
}
