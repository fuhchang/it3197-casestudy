package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import java.util.List;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.SubmitArticle;
import com.example.it3197_casestudy.model.DrawerItem;
import com.example.it3197_casestudy.model.RowItem;
import com.example.it3197_casestudy.util.CustomDrawerAdapter;
import com.example.it3197_casestudy.util.GridImageList;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

public class MainLinkPage extends Activity {
	GridView gv;
	String[] title = { "EVENTS", "HOBBIES", "ARTICLE", "RIDDLES", "PROFILE",
			"SETTING" };

	Integer[] imageID = { R.drawable.events, R.drawable.hobbies,
			R.drawable.article, R.drawable.riddles, R.drawable.profile,
			R.drawable.setting };

    List<DrawerItem> dataList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_link_page);
		GridImageList adapter = new GridImageList(MainLinkPage.this, title,
				imageID);
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
					Intent intent = new Intent(getApplicationContext(), SubmitArticle.class);
					startActivity(intent);
				}else if (position == 3){
					Toast.makeText(getApplicationContext(), "RIDDLES", Toast.LENGTH_LONG).show();
				}else if (position == 4){
					Toast.makeText(getApplicationContext(), "PROFILE", Toast.LENGTH_LONG).show();
				}else if(position == 5){
					Toast.makeText(getApplicationContext(), "SETTING", Toast.LENGTH_LONG).show();
				}
			}
			
		});
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_link_page, menu);
		return true;
	}

}
