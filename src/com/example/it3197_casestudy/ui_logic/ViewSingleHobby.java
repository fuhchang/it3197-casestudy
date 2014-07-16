package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.dropbox.chooser.android.R.color;
import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.controller.getPostController;
import com.example.it3197_casestudy.listview.HobbyListView;
import com.example.it3197_casestudy.model.Hobby;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewSingleHobby extends Activity {
	TextView grpTitle;
	ListView itemList;
	HobbyListView hobbyList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_single_hobby);
		String grpName = getIntent().getExtras().getString("grpName");
		int id = getIntent().getExtras().getInt("grpID");
		grpTitle = (TextView) findViewById(R.id.grpTile);
		grpTitle.setTextSize(40);
		grpTitle.setText(grpName);
		itemList = (ListView) findViewById(R.id.grpList);
		itemList.setBackgroundColor(Color.GRAY);
		getPostController getPostList = new getPostController(this, id,itemList);
		getPostList.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_single_hobby, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.action_new:
			String grpID = getIntent().getExtras().get("grpID").toString();
			
			Intent newPost = new Intent(this, CreateHobbyPost.class);
			newPost.putExtra("grpID", grpID);
			startActivity(newPost);
			break;
		case R.id.action_update_group:
			Intent updateIntent = new Intent(this, UpdateGroupActivity.class);
			startActivity(updateIntent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
