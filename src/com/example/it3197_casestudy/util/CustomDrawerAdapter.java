package com.example.it3197_casestudy.util;

import java.util.List;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.model.DrawerItem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {
	Context context;
	int layoutResID;
	DrawerItem data[] = null;
	public CustomDrawerAdapter(Context context, int resource,
			DrawerItem[] listItems) {
		super(context, resource, listItems);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data = listItems;
		this.layoutResID = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View view = convertView;
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
		view = view.inflate(context,layoutResID, parent);
		ImageView imgView = (ImageView) view.findViewById(R.id.hobbieImg);
		TextView txtTitle = (TextView) view.findViewById(R.id.hobbieTitle);
		
		
		
		DrawerItem folder = data[position];
		imgView.setImageResource(folder.getImgResID());
		txtTitle.setText(folder.getItemName());
		
		return view;

		
	}

	
}
