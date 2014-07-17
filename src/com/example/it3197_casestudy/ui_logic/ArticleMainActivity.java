package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.controller.GetApprovedLatestArticles;
import com.example.it3197_casestudy.model.Article;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ArticleMainActivity extends Activity {

	SingleArticleActivity saa;
	ListView list;
	ArrayList<Article> resultArray = new ArrayList<Article>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_main);
		
		getActionBar().setTitle("Latest News");
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
		
		
	//	TextView tv = (TextView) findViewById(R.id.tv);
		
		
	//	String txt = "<u>Latest News</u>";
		//imageUriTv.setText(Html.fromHtml(img));
		
	//	tv.setText(Html.fromHtml(txt));
		
		list = (ListView) findViewById(R.id.articleListView);
		//list.setBackgroundColor(Color.WHITE);
		GetApprovedLatestArticles gala = new GetApprovedLatestArticles(this, list);
		gala.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.article_main, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		Intent intent = new Intent(ArticleMainActivity.this, MainLinkPage.class);
		startActivity(intent);
		ArticleMainActivity.this.finish();
		
		super.onBackPressed();
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		
		int id = item.getItemId();
		
		
		if(id==R.id.backToMainLink){
			Intent intent = new Intent(ArticleMainActivity.this, MainLinkPage.class);
			startActivity(intent);
			ArticleMainActivity.this.finish();
		}
		
		if(id==R.id.submitArt){
			
			Intent intent = new Intent(ArticleMainActivity.this, SubmitArticle.class);
			startActivity(intent);
			//ArticleMainActivity.this.finish();
			
		}
		
		
		if(id==R.id.refresh){
			Intent intent = new Intent(ArticleMainActivity.this, ArticleMainActivity.class);
			startActivity(intent);
			ArticleMainActivity.this.finish();
		}
		
		
		return super.onMenuItemSelected(featureId, item);
	}

}
