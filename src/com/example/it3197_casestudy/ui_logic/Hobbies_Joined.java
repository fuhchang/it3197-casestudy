package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.listview.HobbyListView;
import com.example.it3197_casestudy.model.Hobby;

import android.os.Bundle;

import android.app.ListFragment;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class Hobbies_Joined extends Fragment {
	ListView joinedList;
	HobbyListView hobbyList;
	public Hobbies_Joined() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.activity_hobbies__joined,
				container, false);
		ArrayList<Hobby> allHobbyList = new ArrayList<Hobby>();
		for(int i=0; i<3; i++){
			Hobby h = new Hobby();
			h.setGroupName(i+ " abc" + i *15);
			h.setCategory("type" +i);
			h.setDescription("asdasjhdkjbasdhbsdhfbasbdfhsbadf");
			allHobbyList.add(h);
		}
		joinedList = (ListView) rootView.findViewById(R.id.hobbyJoinedList);
		joinedList.setBackgroundColor(Color.GRAY);
		hobbyList = new HobbyListView(getActivity(), allHobbyList);
		joinedList.setAdapter(hobbyList);
		return rootView;
	}

}
