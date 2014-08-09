package com.example.it3197_casestudy.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.it3197_casestudy.ui_logic.Hobbies_All;
import com.example.it3197_casestudy.ui_logic.Hobbies_Joined;



public class viewHobbiesAdapter extends FragmentPagerAdapter {
	String nric;
	public viewHobbiesAdapter(FragmentManager fm, String nric) {
		super(fm);
		this.nric = nric;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		Fragment fragment = null;
		switch(arg0){
		case 0 : 
			fragment = new Hobbies_Joined();
			((Hobbies_Joined) fragment).setNric(nric);
			break;
		case 1 :
			fragment = new Hobbies_All();
			((Hobbies_All) fragment).setNric(nric);
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
