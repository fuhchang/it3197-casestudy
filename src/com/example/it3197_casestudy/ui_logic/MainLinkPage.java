package com.example.it3197_casestudy.ui_logic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.util.GridImageList;

public class MainLinkPage extends Activity {
	GridView gv;
	String[] title = { "EVENTS", "HOBBIES", "ARTICLE", "RIDDLES", "PROFILE", "SETTING" };

	Integer[] imageID = { R.drawable.events, R.drawable.hobbies, R.drawable.article, R.drawable.riddles, R.drawable.profile, R.drawable.setting };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_link_page);
		
		GridImageList adapter = new GridImageList(MainLinkPage.this, title, imageID);
		GridView gv = (GridView) findViewById(R.id.gridview);
		gv.setAdapter(adapter);
		gv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				if(position == 0){
					Intent event = new Intent(MainLinkPage.this,ViewEventsActivity.class);
					startActivity(event);
				}else if(position == 1){
					Intent hobbies = new Intent(MainLinkPage.this, ViewHobbiesMain.class);
					startActivity(hobbies);
				}else if(position == 2){
					Toast.makeText(getApplicationContext(), "ARTICLE", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(MainLinkPage.this, SubmitArticle.class);
					startActivity(intent);
				}else if (position == 3){
					Toast.makeText(getApplicationContext(), "RIDDLES", Toast.LENGTH_LONG).show();
				}else if (position == 4){
					Intent profile = new Intent(MainLinkPage.this, ProfileActivity.class);
					startActivity(profile);
				}else if(position == 5){
					Toast.makeText(getApplicationContext(), "SETTING", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}
