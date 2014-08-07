package com.example.it3197_casestudy.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.controller.DeletePost;
import com.example.it3197_casestudy.model.Hobby;
import com.example.it3197_casestudy.model.HobbyPost;
import com.example.it3197_casestudy.ui_logic.MapView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PostListView extends ArrayAdapter<HobbyPost> {
	private Activity activity;
	private ArrayList<HobbyPost> resultArray = new ArrayList<HobbyPost>();
	private int adminRight;
	private String nric;
	private int selectedPos = 0;
	private String Address, City;
	private SparseBooleanArray mSelectedItemsIds;
	private GoogleMap map;
	private LatLng current_location;
	TextView addressTV ;
	public PostListView(Context context, ArrayList<HobbyPost> postList,
			int adminRight, String nric) {
		super(context, R.layout.activity_post_list_view, postList);
		this.activity = (Activity) context;
		this.resultArray = postList;
		this.adminRight = adminRight;
		this.nric = nric;
		mSelectedItemsIds = new SparseBooleanArray();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = activity.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.activity_post_list_view, null,
				true);
		rowView.setBackgroundColor(Color.WHITE);
		selectedPos = position;
	
		TextView postTitle = (TextView) rowView.findViewById(R.id.postID);
		TextView postDate = (TextView) rowView.findViewById(R.id.postDate);
		TextView postContent = (TextView) rowView.findViewById(R.id.postContent);
		addressTV = (TextView) rowView.findViewById(R.id.Address);
	
		current_location = new LatLng(resultArray.get(position).getLat(), resultArray.get(position).getLng());
		addressTV.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(activity, MapView.class);
				intent.putExtra("lat", resultArray.get(position).getLat());
				intent.putExtra("lng", resultArray.get(position).getLng());
				activity.startActivity(intent);
			}
			
		});
		postTitle.setTextSize(35);
		postDate.setTextSize(20);
		postContent.setTextSize(15);
		getMyLocationAddress(resultArray.get(position).getLat(), resultArray.get(position).getLng());
		View hr = rowView.findViewById(R.id.hr);
		hr.setBackgroundColor(Color.GRAY);
		View hr2 = rowView.findViewById(R.id.hr2);
		hr2.setBackgroundColor(Color.GRAY);
		View hr3 = rowView.findViewById(R.id.hr3);
		hr3.setBackgroundColor(Color.GRAY);
		postTitle.setText(resultArray.get(position).getPostTitle());
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		String date = formatter.format(resultArray.get(position).getDatetime());
		postDate.setText(date);
		postContent.setText(resultArray.get(position).getContent());
		return rowView;
	}
	
	public void toggleSelection(int position) {
		selectView(position, !mSelectedItemsIds.get(position));
	}

	public void removeSelection() {
		mSelectedItemsIds = new SparseBooleanArray();
		notifyDataSetChanged();
	}

	public void selectView(int position, boolean value) {
		if (value)
			mSelectedItemsIds.put(position, value);
		else
			mSelectedItemsIds.delete(position);
		notifyDataSetChanged();
	}

	public int getSelectedCount() {
		return mSelectedItemsIds.size();
	}

	public SparseBooleanArray getSelectedIds() {
		return mSelectedItemsIds;
	}

	public void getMyLocationAddress(double lat, double lng){
		
		 try {
			 Geocoder geocoder;  
	 	      List<Address> addresses;
	 	      geocoder = new Geocoder(activity, Locale.getDefault());
			addresses = geocoder.getFromLocation(lat, lng, 1);
			Address = addresses.get(0).getAddressLine(0);
			City = addresses.get(0).getAddressLine(1);
			addressTV.setText(Address + " " + City);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	

}
