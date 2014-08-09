package com.example.it3197_casestudy.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.model.RequestHobby;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RequestListAdapter extends ArrayAdapter<RequestHobby> {
	private Activity context;
	private ArrayList<RequestHobby> requestList;
	
	public RequestListAdapter(Activity context, ArrayList<RequestHobby> requestList) {
		super(context, R.layout.activity_request_list_adapter, requestList);
		this.context = context;
		this.requestList = requestList;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.activity_request_list_adapter, null, true);
		rowView.setBackgroundColor(Color.WHITE);
		TextView grpname = (TextView) rowView.findViewById(R.id.grpName);
		TextView date1 = (TextView) rowView.findViewById(R.id.date1);
		TextView date2 = (TextView) rowView.findViewById(R.id.date2);
		TextView status = (TextView) rowView.findViewById(R.id.status);
		ImageView iconImg = (ImageView) rowView.findViewById(R.id.image);
		if(requestList.get(position).getRequestStatus().equals("accept")){
		iconImg.setImageResource(R.drawable.com_facebook_button_check_on);
		}else{
			iconImg.setImageResource(R.drawable.com_facebook_button_check_off);
		}
		
		grpname.setText(requestList.get(position).getGroupname());
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String startDate = df.format(requestList.get(position).getRequestDateStart());
		String endDate = df.format(requestList.get(position).getRequestDateEnd());
		date1.setText(startDate);
		date2.setText(endDate);
		status.setText(requestList.get(position).getRequestStatus());
		
		return rowView;
	}

	

}
