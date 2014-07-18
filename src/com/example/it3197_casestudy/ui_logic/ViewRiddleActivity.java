package com.example.it3197_casestudy.ui_logic;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.model.Riddle;

public class ViewRiddleActivity extends FragmentActivity {
	TextView tv_riddleTitle, tv_riddleUser, tv_riddleContent;
	
	Bundle data;
	Riddle riddle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_view_riddle);
		
		data = getIntent().getExtras();
		riddle = data.getParcelable("riddle");
			
		tv_riddleTitle = (TextView) findViewById(R.id.tv_riddle_title);
		tv_riddleUser = (TextView) findViewById(R.id.tv_riddle_user);
		tv_riddleContent = (TextView) findViewById(R.id.tv_riddle_content);
		
		tv_riddleTitle.setText(riddle.getRiddleTitle());
		tv_riddleUser.setText(riddle.getUser().getName());
		tv_riddleContent.setText(riddle.getRiddleContent());
	}
}