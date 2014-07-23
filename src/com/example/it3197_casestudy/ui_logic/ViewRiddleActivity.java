package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.DeleteRiddle;
import com.example.it3197_casestudy.model.Riddle;
import com.example.it3197_casestudy.model.RiddleAnswer;

public class ViewRiddleActivity extends FragmentActivity {
	TextView tv_riddleTitle, tv_riddleUser, tv_riddleContent;
	Button btn_riddleAns1, btn_riddleAns2, btn_riddleAns3, btn_riddleAns4;

	Bundle data;
	Riddle riddle;
	ArrayList<RiddleAnswer> riddleAnswerList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_view_riddle);
		
		data = getIntent().getExtras();
		riddle = data.getParcelable("riddle");
		riddleAnswerList = data.getParcelableArrayList("riddleAnswerList");
			
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
		
		btn_riddleAns1.setText(riddleAnswerList.get(0).getRiddleAnswer());
		btn_riddleAns2.setText(riddleAnswerList.get(1).getRiddleAnswer());
		btn_riddleAns3.setText(riddleAnswerList.get(2).getRiddleAnswer());
		btn_riddleAns4.setText(riddleAnswerList.get(3).getRiddleAnswer());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.action_update_delete_riddle, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_update_riddle :
			Intent updateRiddleIntent = new Intent(ViewRiddleActivity.this, UpdateRiddleActivity.class);
			updateRiddleIntent.putExtra("riddle", riddle);
			updateRiddleIntent.putParcelableArrayListExtra("riddleAnswerList", riddleAnswerList);
			startActivity(updateRiddleIntent);
			break;
		case R.id.action_delete_riddle :
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Delete").setMessage("Are you sure you want to delete?");
			builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					DeleteRiddle deleteRiddle = new DeleteRiddle(ViewRiddleActivity.this, riddle);
					deleteRiddle.execute();
				}
			});
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
				}
			});
			builder.create().show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}