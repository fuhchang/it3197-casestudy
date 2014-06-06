package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.util.EventListAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */
public class HomeFragment extends ListFragment implements OnItemClickListener  {
	String[] myFrriends = new String[] {  
		       "Tution on English",
		      };
	
	String[] myFrriends1 = new String[] {  
		       "10/8/2014 4:00PM",
		      };
	
	public HomeFragment() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		  //ArrayAdapter<String> adapter= new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, myFrriends);
		  EventListAdapter adapter = new EventListAdapter(HomeFragment.this.getActivity(),myFrriends,myFrriends1);
		  setListAdapter(adapter);
		  
		  return super.onCreateView(inflater, container, savedInstanceState);	
	}
		   // this code for item click of the list 
		    
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		  super.onActivityCreated(savedInstanceState);
		  getListView().setOnItemClickListener(this);
	}
		  
	@Override
	public void onItemClick(AdapterView adapter, View view, int position, long id) {
		//Toast.makeText(getActivity().getBaseContext(), "Item clicked: " + myFrriends[position], Toast.LENGTH_LONG).show();
		Intent intent = new Intent(getActivity(), ViewEventsActivity.class);
		getActivity().startActivity(intent);
	}
}