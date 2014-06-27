package com.example.it3197_casestudy.util;

import com.example.it3197_casestudy.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridImageList extends ArrayAdapter<String>{
	private final Activity context;
	private final String[] iconTitle;
	private final Integer[] imageId;
	
public GridImageList(Context context, String[] title, Integer[] imageId) {
		super(context, R.layout.single_icon, title);
		// TODO Auto-generated constructor stub
		this.context =  (Activity) context;
		this.iconTitle =  title;
		this.imageId = imageId;
	}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
	// TODO Auto-generated method stub
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView = inflater.inflate(R.layout.single_icon, null, true);
	ImageView imageview = (ImageView) rowView.findViewById(R.id.btnImg);
	TextView textview = (TextView) rowView.findViewById(R.id.iconTitle);
	
	textview.setText(iconTitle[position]);
	imageview.setImageResource(imageId[position]);
	
	
	return rowView;
}

	
}
