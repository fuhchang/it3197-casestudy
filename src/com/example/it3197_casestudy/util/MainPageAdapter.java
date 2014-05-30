package com.example.it3197_casestudy.util;

import java.util.Locale;

import com.example.it3197_casestudy.ui_logic.DonationFragment;
import com.example.it3197_casestudy.ui_logic.HomeFragment;
import com.example.it3197_casestudy.ui_logic.ProfileFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class MainPageAdapter extends FragmentPagerAdapter {

	public MainPageAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		// getItem is called to instantiate the fragment for the given page.
		// Return a DummySectionFragment (defined as a static inner class
		// below) with the page number as its lone argument.
		System.out.println(position);
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			//Add parameter into a bundle to transfer data to the fragment
			/*Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);*/
			break;
		case 1:
			fragment = new DonationFragment();
			break;
		case 2:
			fragment = new ProfileFragment();
			break;
		default:
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// Show 3 total pages.
		return 3;
	}
}