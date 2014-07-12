package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.listview.HobbyListView;
import com.example.it3197_casestudy.model.Hobby;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Hobbies_All extends Fragment {
	
	ListView allList;
	HobbyListView hobbyList;
	public Hobbies_All() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.activity_hobbies__all,
				container, false);
		ArrayList<Hobby> allHobbyList = new ArrayList<Hobby>();
		for(int i=0; i<3; i++){
			Hobby h = new Hobby();
			h.setGroupName(i+ " abc" + i *15);
			h.setCategory("type" +i);
			h.setDescription("asdasjhdkjbasdhbsdhfbasbdfhsbadf");
			allHobbyList.add(h);
		}
		allList = (ListView) rootView.findViewById(R.id.hobbyAllList);
		allList.setBackgroundColor(Color.GRAY);
		hobbyList = new HobbyListView(getActivity(), allHobbyList);
		allList.setAdapter(hobbyList);
		allList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Log.d("position", Integer.toString(position));
				Intent intent = new Intent(getActivity(), ViewSingleHobby.class);
				startActivity(intent);
			}
			
		});
		return rootView;
	}

}
