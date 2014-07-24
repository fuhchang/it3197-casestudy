package com.example.it3197_casestudy.ui_logic;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.model.User;

public class ProfileActivity extends FragmentActivity {
	TextView tv_username, tv_points;
	
	Bundle data;
	User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		data = getIntent().getExtras();
		user = data.getParcelable("user");
		
		tv_username = (TextView) findViewById(R.id.tv_username);
		tv_points = (TextView) findViewById(R.id.tv_points);
		
		tv_username.setText(user.getName());
		//tv_points.setText(user.getPoints());
	}
}