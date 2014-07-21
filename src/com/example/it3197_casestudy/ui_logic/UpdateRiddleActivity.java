package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.model.Riddle;
import com.example.it3197_casestudy.model.RiddleAnswer;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class UpdateRiddleActivity extends FragmentActivity {
	RadioButton rbtn_riddleAns1, rbtn_riddleAns2, rbtn_riddleAns3, rbtn_riddleAns4;
	EditText et_riddleTitle, et_riddleContent, et_riddleAns1, et_riddleAns2, et_riddleAns3, et_riddleAns4;
	Button btn_updateRiddle;
	
	Bundle data;
	Riddle riddle;
	ArrayList<RiddleAnswer> riddleAnsList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_riddle);

		data = getIntent().getExtras();
		riddle = data.getParcelable("riddle");
		riddleAnsList = data.getParcelableArrayList("riddleAnswerList");
		
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
		btn_updateRiddle = (Button) findViewById(R.id.btn_create_riddle);
		
		et_riddleTitle.setText(riddle.getRiddleTitle());
		et_riddleContent.setText(riddle.getRiddleContent());
		
		et_riddleAns1.setText(riddleAnsList.get(0).getRiddleAnswer());
		et_riddleAns2.setText(riddleAnsList.get(1).getRiddleAnswer());
		et_riddleAns3.setText(riddleAnsList.get(2).getRiddleAnswer());
		et_riddleAns4.setText(riddleAnsList.get(3).getRiddleAnswer());
	}
}
