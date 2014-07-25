package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.id;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.model.Article;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class SingleArticleActivity extends ArrayAdapter<Article>{
		private final Activity context;
		private ArrayList<Article> resultArray = new ArrayList<Article>();
		private Integer[] imageId;
		
		public SingleArticleActivity(Context context, ArrayList<Article> articleList) {
			super(context, R.layout.activity_single_article,articleList);
			// TODO Auto-generated constructor stub
			this.context = (Activity) context;
			this.resultArray = articleList;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
					//setup the infalter
					LayoutInflater inflater = context.getLayoutInflater();
					View rowView = inflater.inflate(R.layout.activity_single_article, null, true);
					
					//link to widgets
					ImageView iv = (ImageView)rowView.findViewById(R.id.image);
					iv.setImageResource(R.drawable.article_main4);
					
					
					TextView txtTitle = (TextView) rowView.findViewById(R.id.title);
					TextView txtAuthor = (TextView) rowView.findViewById(R.id.author);
					TextView txtDate = (TextView) rowView.findViewById(R.id.date);
					TextView txtLoc = (TextView) rowView.findViewById(R.id.location);
					TextView txtdist = (TextView) rowView.findViewById(R.id.distances);
					
					txtTitle.setText(resultArray.get(position).getTitle());
					txtAuthor.setText("Posted By: " + resultArray.get(position).getArticleUser());
					txtDate.setText(resultArray.get(position).getArticleDate());
					txtLoc.setText(resultArray.get(position).getLocation());
					txtdist.setText("Distance: " + resultArray.get(position).getDist()+"km");
					
					return rowView;
		}


	
}
