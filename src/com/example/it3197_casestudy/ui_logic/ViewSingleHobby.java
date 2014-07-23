package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.dropbox.chooser.android.R.color;
import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.controller.JoinHobbyGrp;
import com.example.it3197_casestudy.controller.getPostController;
import com.example.it3197_casestudy.listview.HobbyListView;
import com.example.it3197_casestudy.model.Hobby;
import com.example.it3197_casestudy.model.HobbyMembers;
import com.example.it3197_casestudy.util.MySharedPreferences;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewSingleHobby extends Activity {
	TextView grpTitle;
	ListView itemList;
	HobbyListView hobbyList;
	String userNric;
	String adminNric;
	int adminRight = 0;
	String memberCheck;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_single_hobby);

		
		String grpName = getIntent().getExtras().getString("grpName");
		int id = getIntent().getExtras().getInt("grpID");
		memberCheck = getIntent().getExtras().getString("member");
		userNric = getIntent().getExtras().getString("userNric");
		adminNric = getIntent().getExtras().getString("adminNric");
		grpTitle = (TextView) findViewById(R.id.grpTile);
		grpTitle.setTextSize(40);
		grpTitle.setText(grpName);
		itemList = (ListView) findViewById(R.id.grpList);
		itemList.setBackgroundColor(Color.GRAY);
		if(userNric.equals(adminNric)){
			adminRight = 1;
		}
		getPostController getPostList = new getPostController(this, id,itemList, adminRight, userNric);
		getPostList.execute();
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		if(adminRight == 0){
			menu.removeItem(R.id.action_update_group);
			if(memberCheck.equals("none")){
				menu.removeItem(R.id.action_new);
			}else{
				menu.removeItem(R.id.action_join_group);
			}
		}else{
			
		}
		
		return super.onPrepareOptionsMenu(menu);
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
			newPost.putExtra("userNric", userNric);
			newPost.putExtra("adminRight", adminRight);
			CreateHobbyPost createPost = new CreateHobbyPost(ViewSingleHobby.this,itemList);
			startActivity(newPost);
			break;
		case R.id.action_update_group:
			if(userNric.equals(adminNric)){
			
			Intent updateIntent = new Intent(this, EditHobbyGrp.class);
			updateIntent.putExtra("grpID", getIntent().getExtras().getInt("grpID"));
			updateIntent.putExtra("grpName", getIntent().getExtras().getString("grpName"));
			updateIntent.putExtra("grpType", getIntent().getExtras().getString("grpType"));
			updateIntent.putExtra("grpContent", getIntent().getExtras().getString("grpContent"));
			updateIntent.putExtra("Lat", getIntent().getExtras().getDouble("Lat"));
			updateIntent.putExtra("Lng", getIntent().getExtras().getDouble("Lng"));
			updateIntent.putExtra("posterNric", userNric);
			startActivity(updateIntent);
			break;
		
			}else{
				Toast.makeText(getApplicationContext(), "Sorry you are not the admin.", Toast.LENGTH_LONG).show();
				break;
			}
		case R.id.action_join_group:
			HobbyMembers members = new HobbyMembers();
			members.setGroupID(getIntent().getExtras().getInt("grpID"));
			members.setUserNric(userNric);
			JoinHobbyGrp joinGrp = new JoinHobbyGrp(this, members);
			joinGrp.execute();
			this.finish();
			break;
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
