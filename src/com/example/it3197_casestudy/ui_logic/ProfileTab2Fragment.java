package com.example.it3197_casestudy.ui_logic;

import com.example.it3197_casestudy.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileTab2Fragment extends Fragment {
	public ProfileTab2Fragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_profile_tab2, container, false);
		TextView txtText = (TextView) rootView.findViewById(R.id.tv_tab2);
		txtText.setText("Tab 2");
		return rootView;
	}

}
