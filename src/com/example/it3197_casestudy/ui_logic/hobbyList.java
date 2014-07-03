package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.example.it3197_casestudy.model.Hobby;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class hobbyList extends ArrayAdapter<Hobby> {
	private final Activity context;
	private ArrayList<Hobby> hobbyList = new ArrayList<Hobby>();
	private Integer[] imgID;

	public hobbyList(Context context, ArrayList<Hobby> hobbyList) {
		super(context, R.layout.activity_list_item, hobbyList);
		// TODO Auto-generated constructor stub
		this.context = (Activity) context;
		this.hobbyList = hobbyList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return super.getView(position, convertView, parent);
	}

}
