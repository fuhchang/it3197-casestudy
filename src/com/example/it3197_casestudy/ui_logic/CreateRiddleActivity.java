package com.example.it3197_casestudy.ui_logic;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.CreateRiddle;
import com.example.it3197_casestudy.model.Riddle;
import com.example.it3197_casestudy.model.RiddleAnswer;
import com.example.it3197_casestudy.model.User;

public class CreateRiddleActivity extends FragmentActivity {
	RadioButton rbtn_riddleAns1, rbtn_riddleAns2, rbtn_riddleAns3, rbtn_riddleAns4;
	EditText et_riddleTitle, et_riddleContent, et_riddleAns1, et_riddleAns2, et_riddleAns3, et_riddleAns4;
	Button btn_createRiddle;
	String riddleAns1, riddleAns2, riddleAns3, riddleAns4, riddleAnsStatus;
	int checked = 1;
	User user = new User("S9876543A", "Mr Loi", "User", "Password", "99999999", "AMK", "mr_loi@email.com", 1, 100);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_riddle);
		
		et_riddleTitle = (EditText) findViewById(R.id.et_create_riddle_title);
		et_riddleContent = (EditText) findViewById(R.id.et_create_riddle_content);
		rbtn_riddleAns1 = (RadioButton) findViewById(R.id.rbtn_create_riddle_answer_1);
		rbtn_riddleAns2 = (RadioButton) findViewById(R.id.rbtn_create_riddle_answer_2);
		rbtn_riddleAns3 = (RadioButton) findViewById(R.id.rbtn_create_riddle_answer_3);
		rbtn_riddleAns4 = (RadioButton) findViewById(R.id.rbtn_create_riddle_answer_4);
		et_riddleAns1 = (EditText) findViewById(R.id.et_create_riddle_answer_1);
		et_riddleAns2 = (EditText) findViewById(R.id.et_create_riddle_answer_2);
		et_riddleAns3 = (EditText) findViewById(R.id.et_create_riddle_answer_3);
		et_riddleAns4 = (EditText) findViewById(R.id.et_create_riddle_answer_4);
		btn_createRiddle = (Button) findViewById(R.id.btn_create_riddle);
		
		rbtn_riddleAns1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				checked = 1;
				rbtn_riddleAns2.setChecked(false);
				rbtn_riddleAns3.setChecked(false);
				rbtn_riddleAns4.setChecked(false);
			}
		});
		
		rbtn_riddleAns2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				checked = 2;
				rbtn_riddleAns1.setChecked(false);
				rbtn_riddleAns3.setChecked(false);
				rbtn_riddleAns4.setChecked(false);
			}
		});
		
		rbtn_riddleAns3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				checked = 3;
				rbtn_riddleAns1.setChecked(false);
				rbtn_riddleAns2.setChecked(false);
				rbtn_riddleAns4.setChecked(false);
			}
		});
		
		rbtn_riddleAns4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				checked = 4;
				rbtn_riddleAns1.setChecked(false);
				rbtn_riddleAns2.setChecked(false);
				rbtn_riddleAns3.setChecked(false);
			}
		});
		
		btn_createRiddle.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				
				Riddle riddle = new Riddle();
				riddle.setUser(user);
				riddle.setRiddleTitle(et_riddleTitle.getText().toString());
				riddle.setRiddleContent(et_riddleContent.getText().toString());
				riddle.setRiddleStatus("ACTIVE");
				riddle.setRiddlePoint(10);
				
				RiddleAnswer[] riddleChoices = new RiddleAnswer[4];
				for(int i = 1; i < riddleChoices.length+1; i++) {
					RiddleAnswer answer = new RiddleAnswer();
					answer.setRiddle(riddle);
					answer.setUser(user);
					switch(i) {
						case 1 :
							answer.setRiddleAnswer(et_riddleAns1.getText().toString());
							break;
						case 2 :
							answer.setRiddleAnswer(et_riddleAns2.getText().toString());
							break;
						case 3 :
							answer.setRiddleAnswer(et_riddleAns3.getText().toString());
							break;
						case 4 :
							answer.setRiddleAnswer(et_riddleAns4.getText().toString());
							break;
					}
					if(i == checked)
						answer.setRiddleAnswerStatus("CORRECT");
					else
						answer.setRiddleAnswerStatus("WRONG");
					
					riddleChoices[i-1] = answer;
				}
				
				CreateRiddle createRiddle = new CreateRiddle(CreateRiddleActivity.this, riddle, riddleChoices);
				createRiddle.execute();
			}
		});
	}
}
