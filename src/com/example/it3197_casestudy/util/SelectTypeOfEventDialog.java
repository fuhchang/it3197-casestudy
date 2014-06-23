package com.example.it3197_casestudy.util;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.ui_logic.CreateEventStep1Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

@SuppressLint("ValidFragment")
public class SelectTypeOfEventDialog extends DialogFragment{
	Activity activity;
	
	public SelectTypeOfEventDialog(){
		
	}
	
	public SelectTypeOfEventDialog(Activity activity){
		this.activity = activity;
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle("Select type of event");
		builder.setItems(R.array.type_of_events, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String[] typeOfEvents = getResources().getStringArray(R.array.type_of_events);
				String choice = typeOfEvents[which];
				Intent intent = new Intent(activity, CreateEventStep1Activity.class);
    			intent.putExtra("typeOfEvent", choice);
    			startActivity(intent);
    			activity.finish();
			}
		});
		return builder.create();
		
	}
}
