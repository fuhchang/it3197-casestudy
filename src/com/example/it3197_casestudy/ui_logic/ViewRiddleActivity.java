package com.example.it3197_casestudy.ui_logic;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.RetrieveAllRiddleAnswer;
import com.example.it3197_casestudy.model.Riddle;
import com.example.it3197_casestudy.model.RiddleAnswer;

public class ViewRiddleActivity extends FragmentActivity {
	TextView tv_riddleTitle, tv_riddleUser, tv_riddleContent;
	Button btn_riddleAns1, btn_riddleAns2, btn_riddleAns3, btn_riddleAns4;
	RiddleAnswer[] riddleAnsList = new RiddleAnswer[4];
	
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
		tv_riddleUser.setText("- " + riddle.getUser().getName());
		tv_riddleContent.setText(riddle.getRiddleContent());
		
		btn_riddleAns1 = (Button) findViewById(R.id.btn_riddle_ans_1);
		btn_riddleAns2 = (Button) findViewById(R.id.btn_riddle_ans_2);
		btn_riddleAns3 = (Button) findViewById(R.id.btn_riddle_ans_3);
		btn_riddleAns4 = (Button) findViewById(R.id.btn_riddle_ans_4);
		
		RetrieveAllRiddleAnswer retrieveAllRiddleAnswer = new RetrieveAllRiddleAnswer(this, riddle, btn_riddleAns1, btn_riddleAns2, btn_riddleAns3, btn_riddleAns4);
		retrieveAllRiddleAnswer.execute();
	}
}