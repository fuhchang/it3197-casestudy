package com.example.it3197_casestudy.ui_logic;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OfficerMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_officer_main);
		
		
		getActionBar().setTitle("Welcome");
		
		
		Button b1 = (Button)findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(OfficerMainActivity.this, FeedbackArticleActivity.class);
				startActivity(intent);
			}
			
		});
		
	/*	Button b2 = (Button)findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(OfficerMainActivity.this, FeedbackArticleActivity.class);
				startActivity(intent);
			}
			
		});*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.officer_main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		
		int id = item.getItemId();
		
		
		if(id==R.id.artLog){
			Intent i = new Intent(OfficerMainActivity.this,LoginActivity.class);
			startActivity(i);
			OfficerMainActivity.this.finish();
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent i = new Intent(OfficerMainActivity.this,LoginActivity.class);
		startActivity(i);
		OfficerMainActivity.this.finish();
		
		super.onBackPressed();
	}

}
