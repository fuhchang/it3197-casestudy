package com.example.it3197_casestudy.util;

import java.util.ArrayList;

import com.example.it3197_casestudy.model.Riddle;

import com.example.it3197_casestudy.R;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RiddleListAdapter extends ArrayAdapter<Riddle>{
	TextView tv_riddleTitle, tv_riddleContent, tv_riddleUser;
	private Activity context;
	private ArrayList<Riddle> riddleList;
	
	public RiddleListAdapter(Activity context, ArrayList<Riddle> riddleList){
		super(context, R.layout.riddle_row, riddleList);
		this.context = context;
		this.riddleList = riddleList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.riddle_row, null, true);
		rowView.setBackgroundColor(Color.WHITE);
		
		tv_riddleTitle = (TextView) rowView.findViewById(R.id.tv_riddle_title);
		tv_riddleContent = (TextView) rowView.findViewById(R.id.tv_riddle_content);
		tv_riddleUser = (TextView) rowView.findViewById(R.id.tv_riddle_user);
		
		tv_riddleTitle.setText(riddleList.get(position).getRiddleTitle());
		if(riddleList.get(position).getRiddleContent().length() > 40){
			tv_riddleContent.setText(riddleList.get(position).getRiddleContent().substring(0, 39) + " ..");
		}
		else
			tv_riddleContent.setText(riddleList.get(position).getRiddleContent());
		tv_riddleUser.setText(riddleList.get(position).getUser().getName());
		
		return rowView;
	}
}
