package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetRequestList;
import com.example.it3197_casestudy.model.Hobby;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */
public class ViewEventsGalleryFragment extends Fragment {

	public ViewEventsGalleryFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_view_events_gallery, container, false);
		return rootView;
	}
}
