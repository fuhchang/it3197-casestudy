package com.example.it3197_casestudy.ui_logic;

import com.example.it3197_casestudy.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ArticleSelectedActivity extends Fragment {

	
	TextView titleTv, authorTv, articleDateTv, contentTv, addTv;
	
	String title, author,date, content, address;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.article_selected_activity, container, false);		
		Bundle bundle = getArguments();
		title = bundle.getString("title");
		author = bundle.getString("author");
		date = bundle.getString("articleDate");
		content = bundle.getString("content");
		address = bundle.getString("address");
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		getActivity().getActionBar().setTitle("");
		
		titleTv =(TextView)getActivity().findViewById(R.id.title);
		authorTv = (TextView)getActivity().findViewById(R.id.author);
		articleDateTv = (TextView)getActivity().findViewById(R.id.date);
		contentTv = (TextView)getActivity().findViewById(R.id.content);
		
		titleTv.setText(title);
		authorTv.setText("Author: " + author);
		articleDateTv.setText(date);
		contentTv.setText(content);
	}

}
