package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.controller.GetApprovedLatestArticles;
import com.example.it3197_casestudy.controller.GetPendingFeedbackArticles;
import com.example.it3197_casestudy.model.Article;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class FeedbackArticleActivity extends Activity {

	SingleArticleActivity saa;
	ListView list;
	ArrayList<Article> resultArray = new ArrayList<Article>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback_article);
		
		
		getActionBar().setTitle("Pending Feedbacks");

		list = (ListView) findViewById(R.id.articleListView);
		//list.setBackgroundColor(Color.WHITE);
		GetPendingFeedbackArticles gpfa = new GetPendingFeedbackArticles(this, list);
		gpfa.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feedback_article, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		
		int id = item.getItemId();
		
		if(id==R.id.refresh){
			Intent intent = new Intent(FeedbackArticleActivity.this, FeedbackArticleActivity.class);
			startActivity(intent);
			FeedbackArticleActivity.this.finish();
		}
		
		return super.onMenuItemSelected(featureId, item);
	}

}
