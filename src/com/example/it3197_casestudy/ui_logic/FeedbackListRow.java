package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.model.Article;

public class FeedbackListRow extends ArrayAdapter<Article>{
	
	private final Activity context;
	private ArrayList<Article> resultArray = new ArrayList<Article>();
	
	public FeedbackListRow(Context context, ArrayList<Article> articleList) {
		super(context, R.layout.feedback_list_row,articleList);
		// TODO Auto-generated constructor stub
		this.context = (Activity) context;
		this.resultArray = articleList;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
				//setup the infalter
				LayoutInflater inflater = context.getLayoutInflater();
				View rowView = inflater.inflate(R.layout.feedback_list_row, null, true);
				
				
				ImageView iv = (ImageView)rowView.findViewById(R.id.image);
				iv.setImageResource(R.drawable.article_main5);
				
				//link to widgets
				TextView txtTitle = (TextView) rowView.findViewById(R.id.title);
				TextView txtAuthor = (TextView) rowView.findViewById(R.id.author);
				TextView txtDate = (TextView) rowView.findViewById(R.id.date);
				TextView txtLoc = (TextView) rowView.findViewById(R.id.location);
				TextView txtCat = (TextView) rowView.findViewById(R.id.cat);
				TextView txtdist = (TextView) rowView.findViewById(R.id.distances);
				
				txtTitle.setText(resultArray.get(position).getTitle());
				txtAuthor.setText("Posted By: " + resultArray.get(position).getArticleUser());
				txtDate.setText(resultArray.get(position).getArticleDate());
				txtCat.setText("Category: " + resultArray.get(position).getCategory());
				txtLoc.setText(resultArray.get(position).getLocation());
				txtdist.setText("Distance: " + resultArray.get(position).getDist()+"km");
				
				return rowView;
	}

}
