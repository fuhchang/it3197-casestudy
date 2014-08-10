package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.UpdateRiddle;
import com.example.it3197_casestudy.model.Riddle;
import com.example.it3197_casestudy.model.RiddleAnswer;
import com.example.it3197_casestudy.model.User;

public class UpdateRiddleActivity extends FragmentActivity {
	RadioButton rbtn_riddleAns1, rbtn_riddleAns2, rbtn_riddleAns3, rbtn_riddleAns4;
	EditText et_riddleTitle, et_riddleContent, et_riddleAns1, et_riddleAns2, et_riddleAns3, et_riddleAns4;
	Button btn_updateRiddle;
	
	int checked;
	RadioButton[] ansBtn = {rbtn_riddleAns1, rbtn_riddleAns2, rbtn_riddleAns3, rbtn_riddleAns4};
	Bundle data;
	User user;
	Riddle riddle;
	ArrayList<RiddleAnswer> riddleAnswerList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_riddle);

		data = getIntent().getExtras();
		user = data.getParcelable("user");
		riddle = data.getParcelable("riddle");
		riddleAnswerList = data.getParcelableArrayList("riddleAnswerList");
		
		et_riddleTitle = (EditText) findViewById(R.id.et_riddle_title);
		et_riddleContent = (EditText) findViewById(R.id.et_riddle_content);
		rbtn_riddleAns1 = (RadioButton) findViewById(R.id.rbtn_riddle_answer_1);
		rbtn_riddleAns2 = (RadioButton) findViewById(R.id.rbtn_riddle_answer_2);
		rbtn_riddleAns3 = (RadioButton) findViewById(R.id.rbtn_riddle_answer_3);
		rbtn_riddleAns4 = (RadioButton) findViewById(R.id.rbtn_riddle_answer_4);
		et_riddleAns1 = (EditText) findViewById(R.id.et_riddle_answer_1);
		et_riddleAns2 = (EditText) findViewById(R.id.et_riddle_answer_2);
		et_riddleAns3 = (EditText) findViewById(R.id.et_riddle_answer_3);
		et_riddleAns4 = (EditText) findViewById(R.id.et_riddle_answer_4);
		btn_updateRiddle = (Button) findViewById(R.id.btn_update_riddle);
		
		et_riddleTitle.setText(riddle.getRiddleTitle());
		et_riddleContent.setText(riddle.getRiddleContent());
		
		for(int i = 0; i < riddleAnswerList.size(); i++) {
			if(riddleAnswerList.get(i).getRiddleAnswerStatus().equals("CORRECT")) {
				switch(i) {
					case 0 :
						checked = 0;
						rbtn_riddleAns1.setChecked(true);
						break;
					case 1 :
						checked = 1;
						rbtn_riddleAns2.setChecked(true);
						break;
					case 2 :
						checked = 2;
						rbtn_riddleAns3.setChecked(true);
						break;
					case 3 :
						checked = 3;
						rbtn_riddleAns4.setChecked(true);
						break;
				}
			}
		}
		
		et_riddleAns1.setText(riddleAnswerList.get(0).getRiddleAnswer());
		et_riddleAns2.setText(riddleAnswerList.get(1).getRiddleAnswer());
		et_riddleAns3.setText(riddleAnswerList.get(2).getRiddleAnswer());
		et_riddleAns4.setText(riddleAnswerList.get(3).getRiddleAnswer());
		
		rbtn_riddleAns1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				checked = 0;
				rbtn_riddleAns2.setChecked(false);
				rbtn_riddleAns3.setChecked(false);
				rbtn_riddleAns4.setChecked(false);
			}
		});
		
		rbtn_riddleAns2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				checked = 1;
				rbtn_riddleAns1.setChecked(false);
				rbtn_riddleAns3.setChecked(false);
				rbtn_riddleAns4.setChecked(false);
			}
		});
		
		rbtn_riddleAns3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				checked = 2;
				rbtn_riddleAns1.setChecked(false);
				rbtn_riddleAns2.setChecked(false);
				rbtn_riddleAns4.setChecked(false);
			}
		});
		
		rbtn_riddleAns4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				checked = 3;
				rbtn_riddleAns1.setChecked(false);
				rbtn_riddleAns2.setChecked(false);
				rbtn_riddleAns3.setChecked(false);
			}
		});
		
		btn_updateRiddle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				Riddle setRiddle = new Riddle();
				setRiddle.setRiddleID(riddle.getRiddleID());
				setRiddle.setUser(riddle.getUser());
				setRiddle.setRiddleTitle(et_riddleTitle.getText().toString());
				setRiddle.setRiddleContent(et_riddleContent.getText().toString());
				setRiddle.setRiddleStatus("UPDATED");
				setRiddle.setRiddlePoint(riddle.getRiddlePoint());
				
				RiddleAnswer[] riddleChoices = new RiddleAnswer[4];
				for(int i = 0; i < riddleChoices.length; i++) {
					RiddleAnswer answer = new RiddleAnswer();
					answer.setRiddleAnswerID(riddleAnswerList.get(i).getRiddleAnswerID());
					answer.setRiddle(setRiddle);
					switch(i) {
						case 0 :
							answer.setRiddleAnswer(et_riddleAns1.getText().toString());
							break;
						case 1 :
							answer.setRiddleAnswer(et_riddleAns2.getText().toString());
							break;
						case 2 :
							answer.setRiddleAnswer(et_riddleAns3.getText().toString());
							break;
						case 3 :
							answer.setRiddleAnswer(et_riddleAns4.getText().toString());
							break;
					}
					if(i == checked)
						answer.setRiddleAnswerStatus("CORRECT");
					else
						answer.setRiddleAnswerStatus("WRONG");
					
					riddleChoices[i] = answer;
				}
				
				UpdateRiddle updateRiddle = new UpdateRiddle(UpdateRiddleActivity.this, setRiddle, riddleChoices, user);
				updateRiddle.execute();
			}
		});
	}
}
