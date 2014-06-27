package com.example.it3197_casestudy.ui_logic;

import com.example.it3197_casestudy.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Hobbies_All extends Fragment {
	public Hobbies_All() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.activity_hobbies__all,
				container, false);
		TextView txtText = (TextView) rootView.findViewById(R.id.AllTitle);
		txtText.setText("hehehehe");
		return rootView;
	}

}
