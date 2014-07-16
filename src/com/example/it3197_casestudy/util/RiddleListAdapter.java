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
		
		TextView tv_riddle_title = (TextView) rowView.findViewById(R.id.tv_riddle_title);
		TextView tv_riddle_content = (TextView) rowView.findViewById(R.id.tv_riddle_content);
		
		tv_riddle_title.setText(riddleList.get(position).getRiddleTitle());
		tv_riddle_content.setText(riddleList.get(position).getRiddleContent());
		
		return rowView;
	}
}
