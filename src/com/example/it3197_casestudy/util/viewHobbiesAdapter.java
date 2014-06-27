package com.example.it3197_casestudy.util;

import com.example.it3197_casestudy.ui_logic.Hobbies_All;
import com.example.it3197_casestudy.ui_logic.Hobbies_Joined;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class viewHobbiesAdapter extends FragmentPagerAdapter {

	public viewHobbiesAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		Fragment fragment = null;
		switch(arg0){
		case 0 : 
			fragment = new Hobbies_Joined();
			break;
		case 1 :
			fragment = new Hobbies_All();
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
