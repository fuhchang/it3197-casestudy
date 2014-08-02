package com.example.it3197_casestudy.listview;

import java.io.File;
import java.util.ArrayList;
import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.model.Hobby;
import com.google.android.gms.common.images.ImageManager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ImageReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HobbyListView extends ArrayAdapter<Hobby> {
	private final Activity context;
	private ArrayList<Hobby> resultArray = new ArrayList<Hobby>();

	public HobbyListView(Context context, ArrayList<Hobby> hobbyList) {
		super(context, R.layout.activity_hobby_list_view, hobbyList);
		this.context = (Activity) context;
		this.resultArray = hobbyList;
	}

	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.activity_hobby_list_view,
				null, true);
		rowView.setBackgroundColor(Color.WHITE);
		
		TextView gTitle = (TextView) rowView.findViewById(R.id.gTitle);
		TextView gCate = (TextView) rowView.findViewById(R.id.gType);
		TextView gDesc = (TextView) rowView.findViewById(R.id.gDesc);
		gTitle.setTextSize(35);
		gCate.setTextSize(20);
		gDesc.setTextSize(15);
		View hr = rowView.findViewById(R.id.hr);
		hr.setBackgroundColor(Color.GRAY);
		View hr2 = rowView.findViewById(R.id.hr2);
		hr2.setBackgroundColor(Color.GRAY);
		gTitle.setText(resultArray.get(position).getGroupName());
		gCate.setText(resultArray.get(position).getCategory());
		gDesc.setText(resultArray.get(position).getDescription());
		return rowView;
	}

}
