package com.example.it3197_casestudy.ui_logic;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.CreateRiddle;
import com.example.it3197_casestudy.model.Riddle;
import com.example.it3197_casestudy.model.RiddleAnswer;
import com.example.it3197_casestudy.model.User;

public class CreateRiddleActivity extends FragmentActivity {
	EditText et_create_riddle_title, et_create_riddle_content, et_create_riddle_answer;
	Button btn_create_riddle;
	String riddleTitle, riddleContent, riddleAns;
	//User(String nric, String name, String type, String password,String contactNo, String address, String email, int active, int points)
	User user = new User("S98765432", "Mr Loi", "User", "Password", "99999999", "AMK", "mr_loi@email.com", 1, 100);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_riddle);
		
		et_create_riddle_title = (EditText) findViewById(R.id.et_create_riddle_title);
		et_create_riddle_content = (EditText) findViewById(R.id.et_create_riddle_content);
		et_create_riddle_answer = (EditText) findViewById(R.id.et_create_riddle_answer);

		riddleTitle = et_create_riddle_title.toString();
		riddleContent = et_create_riddle_content.toString();
		riddleAns = et_create_riddle_answer.toString();
		
		btn_create_riddle = (Button) findViewById(R.id.btn_create_riddle);
		btn_create_riddle.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				Riddle riddle = new Riddle();
				riddle.setUser(user);
				riddle.setRiddleTitle(riddleTitle);
				riddle.setRiddleContent(riddleContent);
				riddle.setRiddleStatus("ACTIVE");
				riddle.setRiddlePoint(10);
				
				RiddleAnswer answer = new RiddleAnswer();
				answer.setRiddle(riddle);
				answer.setUser(user);
				answer.setRiddleAnswer(riddleAns);
				answer.setRiddleAnswerStatus("ANSWER");
				
				CreateRiddle createRiddle = new CreateRiddle(CreateRiddleActivity.this, riddle, answer);
				createRiddle.execute();
				System.out.println("executed");
			}
		});
	}
}
