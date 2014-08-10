package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.DeleteRiddle;
import com.example.it3197_casestudy.controller.InsertChoice;
import com.example.it3197_casestudy.model.Riddle;
import com.example.it3197_casestudy.model.RiddleAnswer;
import com.example.it3197_casestudy.model.RiddleUserAnswered;
import com.example.it3197_casestudy.model.User;

public class ViewRiddleActivity extends FragmentActivity {
	TextView tv_riddleTitle, tv_riddleUser, tv_riddleContent;
	TextView tv_easy, tv_medium, tv_hard;
	ImageView iv_easy, iv_medium, iv_hard;
	RelativeLayout rl_easy, rl_medium, rl_hard;
	Button btn_rate, btn_riddleAns1, btn_riddleAns2, btn_riddleAns3, btn_riddleAns4;

	Bundle data;
	User user;
	Riddle riddle;
	ArrayList<RiddleAnswer> riddleAnswerList;
	RiddleUserAnswered userAnswer;
	
	String rating;
	GradientDrawable gd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_view_riddle);
		
		data = getIntent().getExtras();
		user = data.getParcelable("user");
		riddle = data.getParcelable("riddle");
		riddleAnswerList = data.getParcelableArrayList("riddleAnswerList");
		if(data.getParcelable("userAnswer") != null) {
			userAnswer = data.getParcelable("userAnswer");
		}
		
		tv_riddleTitle = (TextView) findViewById(R.id.tv_riddle_title);
		tv_riddleUser = (TextView) findViewById(R.id.tv_riddle_user);
		tv_riddleContent = (TextView) findViewById(R.id.tv_riddle_content);
		
		tv_riddleTitle.setText(riddle.getRiddleTitle());
		tv_riddleUser.setText("- " + riddle.getUser().getName());
		tv_riddleContent.setText(riddle.getRiddleContent());
		
		btn_rate = (Button) findViewById(R.id.btn_rate);
		btn_riddleAns1 = (Button) findViewById(R.id.btn_riddle_ans_1);
		btn_riddleAns2 = (Button) findViewById(R.id.btn_riddle_ans_2);
		btn_riddleAns3 = (Button) findViewById(R.id.btn_riddle_ans_3);
		btn_riddleAns4 = (Button) findViewById(R.id.btn_riddle_ans_4);
		
		btn_riddleAns1.setText(riddleAnswerList.get(0).getRiddleAnswer());
		btn_riddleAns2.setText(riddleAnswerList.get(1).getRiddleAnswer());
		btn_riddleAns3.setText(riddleAnswerList.get(2).getRiddleAnswer());
		btn_riddleAns4.setText(riddleAnswerList.get(3).getRiddleAnswer());
		
		btn_riddleAns1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				RiddleAnswer riddleAnswer = riddleAnswerList.get(0);
				InsertChoice insertChoice = new InsertChoice(ViewRiddleActivity.this, riddle, riddleAnswerList, riddleAnswer, user);
				insertChoice.execute();
			}
		});
		
		btn_riddleAns2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				RiddleAnswer riddleAnswer = riddleAnswerList.get(1);
				InsertChoice insertChoice = new InsertChoice(ViewRiddleActivity.this, riddle, riddleAnswerList, riddleAnswer, user);
				insertChoice.execute();
			}
		});
		
		btn_riddleAns3.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				RiddleAnswer riddleAnswer = riddleAnswerList.get(2);
				InsertChoice insertChoice = new InsertChoice(ViewRiddleActivity.this, riddle, riddleAnswerList, riddleAnswer, user);
				insertChoice.execute();
			}
		});
		
		btn_riddleAns4.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				RiddleAnswer riddleAnswer = riddleAnswerList.get(3);
				InsertChoice insertChoice = new InsertChoice(ViewRiddleActivity.this, riddle, riddleAnswerList, riddleAnswer, user);
				insertChoice.execute();
			}
		});
		
		if(user.getNric().equals(riddle.getUser().getNric()) || userAnswer != null) {
			btn_riddleAns1.setClickable(false);
			btn_riddleAns2.setClickable(false);
			btn_riddleAns3.setClickable(false);
			btn_riddleAns4.setClickable(false);
			
			if(!user.getNric().equals(riddle.getUser().getNric()) && userAnswer.getAnsweredRate().equals("NULL")) {
				btn_rate.setVisibility(1);
				btn_rate.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
					    AlertDialog.Builder builder = new AlertDialog.Builder(ViewRiddleActivity.this);
					    LayoutInflater inflater = ViewRiddleActivity.this.getLayoutInflater();
						View view =  inflater.inflate(R.layout.dialog_rate_riddle, null);
						builder.setView(view);

						iv_easy = (ImageView) view.findViewById(R.id.iv_easy);
						iv_medium = (ImageView) view.findViewById(R.id.iv_medium);
						iv_hard = (ImageView) view.findViewById(R.id.iv_hard);
						
						tv_easy = (TextView) view.findViewById(R.id.tv_easy);
						tv_medium = (TextView) view.findViewById(R.id.tv_medium);
						tv_hard = (TextView) view.findViewById(R.id.tv_hard);
						
						rl_easy = (RelativeLayout) view.findViewById(R.id.rl_easy);
						rl_medium = (RelativeLayout) view.findViewById(R.id.rl_medium);
						rl_hard = (RelativeLayout) view.findViewById(R.id.rl_hard);
						
						rl_easy.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								gd = new GradientDrawable();
								gd.setColor(Color.GRAY);
								rl_easy.setBackground(gd);
								tv_easy.setTextColor(Color.WHITE);
								rl_medium.setBackground(null);
								tv_medium.setTextColor(Color.BLACK);
								rl_hard.setBackground(null);
								tv_hard.setTextColor(Color.BLACK);
								
								rating = "EASY";
							}
						});
						rl_medium.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								gd = new GradientDrawable();
								gd.setColor(Color.GRAY);
								rl_easy.setBackground(null);
								tv_easy.setTextColor(Color.BLACK);
								rl_medium.setBackground(gd);
								tv_medium.setTextColor(Color.WHITE);
								rl_hard.setBackground(null);
								tv_hard.setTextColor(Color.BLACK);
								
								rating = "MEDIUM";
							}
						});
						rl_hard.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								gd = new GradientDrawable();
								gd.setColor(Color.GRAY);
								rl_easy.setBackground(null);
								tv_easy.setTextColor(Color.BLACK);
								rl_medium.setBackground(null);
								tv_medium.setTextColor(Color.BLACK);
								rl_hard.setBackground(gd);
								tv_hard.setTextColor(Color.WHITE);
								
								rating = "HARD";
							}
						});
						
						builder.setTitle("Earn 1 point for rating riddle");
						builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								if(rating.equals("NULL")) {
								    AlertDialog.Builder builder = new AlertDialog.Builder(ViewRiddleActivity.this);
								    builder.setMessage("No selection detected.");
								    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {										
										@Override
										public void onClick(DialogInterface dialog, int which) {
										}
									});
								    builder.create().show();
								}
								else {
									InsertChoice insertChoice = new InsertChoice(ViewRiddleActivity.this, riddle, riddleAnswerList, userAnswer.getRiddleAnswer(), user, rating);
									insertChoice.execute();
								}
							}
						});
						builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								rating = "NULL";
							}
						});      
					    builder.create().show();
					}
				});
			}
			
			int index = 0;
			for(int i = 0; i < riddleAnswerList.size(); i++) {
				if(riddleAnswerList.get(i).getRiddleAnswerStatus().equals("CORRECT")) {
					index = i;
				}
				else {
					if(userAnswer != null) {
						if(riddleAnswerList.get(i).getRiddleAnswerID() == userAnswer.getRiddleAnswer().getRiddleAnswerID()) {
							switch(i) {
								case 0 :
									btn_riddleAns1.setCompoundDrawablesWithIntrinsicBounds((R.drawable.ic_action_cancel), 0, 0, 0);
									btn_riddleAns1.setCompoundDrawablePadding(-100);
									break;
								case 1 :
									btn_riddleAns2.setCompoundDrawablesWithIntrinsicBounds((R.drawable.ic_action_cancel), 0, 0, 0);
									btn_riddleAns2.setCompoundDrawablePadding(-100);
									break;
								case 2 :
									btn_riddleAns3.setCompoundDrawablesWithIntrinsicBounds((R.drawable.ic_action_cancel), 0, 0, 0);
									btn_riddleAns3.setCompoundDrawablePadding(-100);
									break;
								case 3 :
									btn_riddleAns4.setCompoundDrawablesWithIntrinsicBounds((R.drawable.ic_action_cancel), 0, 0, 0);
									btn_riddleAns4.setCompoundDrawablePadding(-100);
									break;
							}
						}
					}
				}
			}
			
			if(userAnswer != null) {
				if(riddleAnswerList.get(index).getRiddleAnswerID() == userAnswer.getRiddleAnswer().getRiddleAnswerID()) {
					switch(index) {
						case 0 :
							btn_riddleAns1.setCompoundDrawablesWithIntrinsicBounds((R.drawable.ic_action_accept), 0, 0, 0);
							btn_riddleAns1.setCompoundDrawablePadding(-100);
							break;
						case 1 :
							btn_riddleAns2.setCompoundDrawablesWithIntrinsicBounds((R.drawable.ic_action_accept), 0, 0, 0);
							btn_riddleAns2.setCompoundDrawablePadding(-100);
							break;
						case 2 :
							btn_riddleAns3.setCompoundDrawablesWithIntrinsicBounds((R.drawable.ic_action_accept), 0, 0, 0);
							btn_riddleAns3.setCompoundDrawablePadding(-100);
							break;
						case 3 :
							btn_riddleAns4.setCompoundDrawablesWithIntrinsicBounds((R.drawable.ic_action_accept), 0, 0, 0);
							btn_riddleAns4.setCompoundDrawablePadding(-100);
							break;
					}
				}
			}
			
			int red = Color.parseColor("#e52000");
			int green = Color.parseColor("#55e114");
			switch(index) {
			case 0 :
				gd = new GradientDrawable();
				gd.setStroke(8, Color.GRAY);
				gd.setColor(green);
				btn_riddleAns1.setBackground(gd);
				gd = new GradientDrawable();
				gd.setStroke(8, Color.GRAY);
				gd.setColor(red);
				btn_riddleAns2.setBackground(gd);
				btn_riddleAns3.setBackground(gd);
				btn_riddleAns4.setBackground(gd);
				break;
			case 1 :
				gd = new GradientDrawable();
				gd.setStroke(8, Color.GRAY);
				gd.setColor(green);
				btn_riddleAns2.setBackground(gd);
				gd = new GradientDrawable();
				gd.setStroke(8, Color.GRAY);
				gd.setColor(red);
				btn_riddleAns1.setBackground(gd);
				btn_riddleAns3.setBackground(gd);
				btn_riddleAns4.setBackground(gd);
				break;
			case 2 :
				gd = new GradientDrawable();
				gd.setStroke(8, Color.GRAY);
				gd.setColor(green);
				btn_riddleAns3.setBackground(gd);
				gd = new GradientDrawable();
				gd.setStroke(8, Color.GRAY);
				gd.setColor(red);
				btn_riddleAns1.setBackground(gd);
				btn_riddleAns2.setBackground(gd);
				btn_riddleAns4.setBackground(gd);
				break;
			case 3 :
				gd = new GradientDrawable();
				gd.setStroke(8, Color.GRAY);
				gd.setColor(green);
				btn_riddleAns4.setBackground(gd);
				gd = new GradientDrawable();
				gd.setStroke(8, Color.GRAY);
				gd.setColor(red);
				btn_riddleAns1.setBackground(gd);
				btn_riddleAns2.setBackground(gd);
				btn_riddleAns3.setBackground(gd);
				break;
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(user.getNric().equals(riddle.getUser().getNric())) {
			getMenuInflater().inflate(R.menu.action_update_delete_riddle, menu);
		}
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