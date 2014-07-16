package com.example.it3197_casestudy.listview;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.model.Hobby;
import com.example.it3197_casestudy.model.HobbyPost;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PostListView extends ArrayAdapter<HobbyPost>{
	private Activity activity;
	private ArrayList<HobbyPost> resultArray = new ArrayList<HobbyPost>();
	
	public PostListView(Context context, ArrayList<HobbyPost> postList) {
		super(context, R.layout.activity_post_list_view, postList);
		this.activity = (Activity) context;
		this.resultArray  = postList;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = activity.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.activity_post_list_view,
				null, true);
		rowView.setBackgroundColor(Color.WHITE);
		TextView gTitle = (TextView) rowView.findViewById(R.id.postID);
		TextView gCate = (TextView) rowView.findViewById(R.id.postDate);
		TextView gDesc = (TextView) rowView.findViewById(R.id.postContent);
		gTitle.setTextSize(35);
		gCate.setTextSize(20);
		gDesc.setTextSize(15);
		View hr = rowView.findViewById(R.id.hr);
		hr.setBackgroundColor(Color.GRAY);
		View hr2 = rowView.findViewById(R.id.hr2);
		hr2.setBackgroundColor(Color.GRAY);
		gTitle.setText(Integer.toString(resultArray.get(position).getPostID()));
		//gCate.setText(resultArray.get(position).getDatetime().toString());
		gDesc.setText(resultArray.get(position).getContent());
		return rowView;
	}

	
}
