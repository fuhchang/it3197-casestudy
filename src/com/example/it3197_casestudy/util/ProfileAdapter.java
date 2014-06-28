package com.example.it3197_casestudy.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.it3197_casestudy.ui_logic.ProfileTab1Fragment;
import com.example.it3197_casestudy.ui_logic.ProfileTab2Fragment;

public class ProfileAdapter extends FragmentPagerAdapter {

	public ProfileAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		Fragment fragment = null;
		switch(arg0){
		case 0 : 
			fragment = new ProfileTab1Fragment();
			break;
		case 1 :
			fragment = new ProfileTab2Fragment();
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return 2;
	}
}
