package com.example.it3197_casestudy.ui_logic;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetEventParticipants;
import com.example.it3197_casestudy.controller.GetEventParticipantsInfo;

public class SelectNewEventAdminActivity extends Activity{
	private int eventID;
	private String nricList[];
	ListView lvEventAdmin;
	private String newEventAdminNRIC;
	
	public ListView getLvEventAdmin() {
		return lvEventAdmin;
	}

	public void setLvEventAdmin(ListView lvEventAdmin) {
		this.lvEventAdmin = lvEventAdmin;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_new_event_admin);
        savedInstanceState = getIntent().getExtras();
        if(savedInstanceState != null){
        	eventID = savedInstanceState.getInt("eventID");
        	nricList = savedInstanceState.getStringArray("nricList");
        }

		GetEventParticipantsInfo getEventParticipantsInfo = new GetEventParticipantsInfo(SelectNewEventAdminActivity.this, eventID, nricList);
		getEventParticipantsInfo.execute();
		
        lvEventAdmin = (ListView) findViewById(R.id.lv_event_admin);
        lvEventAdmin.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lvEventAdmin.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				newEventAdminNRIC = nricList[position].toString();
			}
        });
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		//return super.onCreateOptionsMenu(menu);
		return false;
	}
	
	public void onClick(View view){
		try {
			switch (view.getId()) {
			case R.id.btn_select_new_admin:{
				Intent returnIntent = new Intent();
				returnIntent.putExtra("newEventAdminNRIC",newEventAdminNRIC);
				setResult(RESULT_OK,returnIntent);
				finish();
			}
			default:
				Intent returnIntent = new Intent();
				setResult(RESULT_CANCELED,returnIntent);
				finish();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
