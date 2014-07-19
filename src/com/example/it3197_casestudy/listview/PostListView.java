package com.example.it3197_casestudy.listview;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.controller.DeletePost;
import com.example.it3197_casestudy.model.Hobby;
import com.example.it3197_casestudy.model.HobbyPost;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PostListView extends ArrayAdapter<HobbyPost> {
	private Activity activity;
	private ArrayList<HobbyPost> resultArray = new ArrayList<HobbyPost>();
	private int adminRight;
	Button edit, delete;
	private String nric;
	private int selectedPos = 0;

	public PostListView(Context context, ArrayList<HobbyPost> postList,
			int adminRight, String nric) {
		super(context, R.layout.activity_post_list_view, postList);
		this.activity = (Activity) context;
		this.resultArray = postList;
		this.adminRight = adminRight;
		this.nric = nric;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = activity.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.activity_post_list_view, null,
				true);
		rowView.setBackgroundColor(Color.WHITE);
		edit = (Button) rowView.findViewById(R.id.Edit);
		delete = (Button) rowView.findViewById(R.id.Delete);
		edit.setBackgroundColor(Color.LTGRAY);
		delete.setBackgroundColor(Color.LTGRAY);
		selectedPos = position;
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HobbyPost post = new HobbyPost();
				post.setPostID(resultArray.get(selectedPos).getPostID());
				DeletePost delPost = new DeletePost(PostListView.this, post);
				delPost.execute();
			}
		});
		delete.setVisibility(View.INVISIBLE);
		edit.setVisibility(View.INVISIBLE);
		if (adminRight == 1) {
			delete.setVisibility(View.VISIBLE);
		}
		if (nric.equals(resultArray.get(position).getPosterNric())) {
			delete.setVisibility(View.VISIBLE);
			edit.setVisibility(View.VISIBLE);
		}
		TextView postTitle = (TextView) rowView.findViewById(R.id.postID);
		TextView postDate = (TextView) rowView.findViewById(R.id.postDate);
		TextView postContent = (TextView) rowView
				.findViewById(R.id.postContent);
		postTitle.setTextSize(35);
		postDate.setTextSize(20);
		postContent.setTextSize(15);

		View hr = rowView.findViewById(R.id.hr);
		hr.setBackgroundColor(Color.GRAY);
		View hr2 = rowView.findViewById(R.id.hr2);
		hr2.setBackgroundColor(Color.GRAY);
		View hr3 = rowView.findViewById(R.id.hr3);
		hr3.setBackgroundColor(Color.GRAY);
		postTitle.setText(resultArray.get(position).getPostTitle());
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		String date = formatter.format(resultArray.get(position).getDatetime());
		postDate.setText(date);
		postContent.setText(resultArray.get(position).getContent());
		return rowView;
	}

}
