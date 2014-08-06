package com.example.it3197_casestudy.util;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.model.Riddle;
import com.example.it3197_casestudy.model.RiddleUserAnswered;
import com.example.it3197_casestudy.model.User;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RiddleListAdapter extends ArrayAdapter<Riddle>{
	TextView tv_riddleTitle, tv_riddleContent, tv_riddleUser;
	private Activity context;
	private ArrayList<Riddle> riddleList;
	private ArrayList<RiddleUserAnswered> answeredList;
	private User user;
	
	public RiddleListAdapter(Activity context, ArrayList<Riddle> riddleList, ArrayList<RiddleUserAnswered> answeredList, User user){
		super(context, R.layout.riddle_row, riddleList);
		this.context = context;
		this.riddleList = riddleList;
		this.answeredList = answeredList;
		this.user = user;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.riddle_row, null, true);

		GradientDrawable gd;
		if(riddleList.get(position).getUser().getNric().equals(user.getNric())) {
			gd = new GradientDrawable();
			gd.setColor(Color.WHITE);
			gd.setStroke(10, Color.BLACK);
			rowView.setBackground(gd);
		}
		else if(riddleList.get(position).getRiddlePoint() == 5) {
			gd = new GradientDrawable();
			gd.setColor(Color.WHITE);
			gd.setStroke(10, Color.GREEN);
			rowView.setBackground(gd);
		}
		else if(riddleList.get(position).getRiddlePoint() == 10) {
			gd = new GradientDrawable();
			gd.setColor(Color.WHITE);
			gd.setStroke(10, Color.YELLOW);
			rowView.setBackground(gd);
		}
		else if(riddleList.get(position).getRiddlePoint() == 20) {
			gd = new GradientDrawable();
			gd.setColor(Color.WHITE);
			gd.setStroke(10, Color.RED);
			rowView.setBackground(gd);
		}
		for(int i = 0; i < answeredList.size(); i++) {
			if(riddleList.get(position).getRiddleID() == answeredList.get(i).getRiddle().getRiddleID()) {
				gd = new GradientDrawable();
				gd.setColor(Color.WHITE);
				gd.setStroke(10, Color.GRAY);
				rowView.setBackground(gd);
			}
		}
		
		tv_riddleTitle = (TextView) rowView.findViewById(R.id.tv_riddle_title);
		tv_riddleContent = (TextView) rowView.findViewById(R.id.tv_riddle_content);
		tv_riddleUser = (TextView) rowView.findViewById(R.id.tv_riddle_user);
		
		if(riddleList.get(position).getRiddleTitle().length() > 45) {
			tv_riddleTitle.setText(riddleList.get(position).getRiddleTitle().substring(0, 44) + " ...");
		}
		else {
			tv_riddleTitle.setText(riddleList.get(position).getRiddleTitle());
		}
		if(riddleList.get(position).getRiddleContent().length() > 45) {
			tv_riddleContent.setText(riddleList.get(position).getRiddleContent().substring(0, 44) + " ...");
		}
		else {
			tv_riddleContent.setText(riddleList.get(position).getRiddleContent());
		}
			tv_riddleUser.setText("- " + riddleList.get(position).getUser().getName());
		
		return rowView;
	}
}
