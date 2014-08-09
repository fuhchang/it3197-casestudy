package com.example.it3197_casestudy.ui_logic;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONException;

import com.dropbox.chooser.android.R.color;
import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.ar.activity.Demo;
import com.example.it3197_casestudy.controller.CheckMemberHobby;
import com.example.it3197_casestudy.controller.GetHobbyPost;
import com.example.it3197_casestudy.controller.GetImageFromFaceBookForHobby;
import com.example.it3197_casestudy.controller.GetImageFromFacebookArticle;
import com.example.it3197_casestudy.controller.JoinHobbyGrp;
import com.example.it3197_casestudy.controller.getPostController;
import com.example.it3197_casestudy.model.Hobby;
import com.example.it3197_casestudy.model.HobbyMembers;
import com.example.it3197_casestudy.util.HobbyListView;
import com.example.it3197_casestudy.util.MySharedPreferences;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
	String grpImg;
	private String pictureURL;
	private UiLifecycleHelper uiHelper;
	private ImageView imgView;
	private String fbID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_single_hobby);
		uiHelper = new UiLifecycleHelper(ViewSingleHobby.this, null);
		uiHelper.onCreate(savedInstanceState);
		String grpName = getIntent().getExtras().getString("grpName");
		int id = getIntent().getExtras().getInt("grpID");
		fbID = getIntent().getExtras().getString("fbID");
		MySharedPreferences preferences = new MySharedPreferences(this);
		userNric = preferences.getPreferences("nric", "S000000E");
		grpTitle = (TextView) findViewById(R.id.grpTile);
		grpTitle.setTextSize(40);
		grpTitle.setText(grpName);
		itemList = (ListView) findViewById(R.id.grpList);
		itemList.setBackgroundColor(Color.GRAY);
		adminNric = getIntent().getExtras().getString("adminNric");
		if(userNric.equals(adminNric)){
			adminRight = 1;
			
		}else{
			adminRight =0;
		}
		System.out.println(adminRight);
		File imgFile = new  File("grpImg");
		if(imgFile.exists()){

		    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

		    ImageView myImage = (ImageView) findViewById(R.id.grpImg);
		    myImage.setImageBitmap(myBitmap);

		}

		getPostController getPostList = new getPostController(this, id,itemList, adminRight, userNric, adminNric, grpName);
		getPostList.execute();
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		if(userNric.equals("fromRequest")){
			menu.removeItem(R.id.action_new);
			menu.removeItem(R.id.action_join_group);
			menu.removeItem(R.id.action_update_group);
			menu.removeItem(R.id.action_view_request);
		}else{
			System.out.println(adminRight);
		memberCheck = getIntent().getExtras().getString("member");
		if(adminRight == 0){
			menu.removeItem(R.id.action_update_group);
			menu.removeItem(R.id.action_view_request);
			if(memberCheck.equals("member")){
				menu.removeItem(R.id.action_join_group);	
			}else{
				menu.removeItem(R.id.action_new);
			}
		}else{
			menu.removeItem(R.id.action_join_group);	
		}
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
			newPost.putExtra("fbID", fbID);
			//CreateHobbyPost createPost = new CreateHobbyPost(ViewSingleHobby.this,itemList);
			startActivity(newPost);
			this.finish();
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
		case R.id.action_view_request:
			Intent requestIntent = new Intent(this, ViewRequest.class);
			requestIntent.putExtra("hobbyID", getIntent().getExtras().getInt("grpID"));
			startActivity(requestIntent);
			break;
		case R.id.action_AR:
			GetHobbyPost ViewPost = new GetHobbyPost(this, getIntent().getExtras().getInt("grpID"));
			ViewPost.execute();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		 uiHelper.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		 uiHelper.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 uiHelper.onResume();
	}
	
	
	
}
