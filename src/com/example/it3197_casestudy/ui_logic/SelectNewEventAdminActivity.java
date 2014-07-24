package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

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
import com.example.it3197_casestudy.model.User;

public class SelectNewEventAdminActivity extends Activity{
	private int eventID;
	private String nricList[];
	private String userNRIC;
	ListView lvEventAdmin;
	private String newEventAdminNRIC;
	private ArrayList<User> userArrList = new ArrayList<User>();
	
	public ListView getLvEventAdmin() {
		return lvEventAdmin;
	}

	public void setLvEventAdmin(ListView lvEventAdmin) {
		this.lvEventAdmin = lvEventAdmin;
	}
	
	public ArrayList<User> getUserArrList() {
		return userArrList;
	}

	public void setUserArrList(ArrayList<User> userArrList) {
		this.userArrList = userArrList;
	}

	public String getNewEventAdminNRIC() {
		return newEventAdminNRIC;
	}

	public void setNewEventAdminNRIC(String newEventAdminNRIC) {
		this.newEventAdminNRIC = newEventAdminNRIC;
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_new_event_admin);
        savedInstanceState = getIntent().getExtras();
        if(savedInstanceState != null){
        	eventID = savedInstanceState.getInt("eventID");
        	nricList = savedInstanceState.getStringArray("nricList");
        	userNRIC = savedInstanceState.getString("userNRIC");
        }

		GetEventParticipantsInfo getEventParticipantsInfo = new GetEventParticipantsInfo(SelectNewEventAdminActivity.this, eventID, nricList, userNRIC);
		getEventParticipantsInfo.execute();
		
        lvEventAdmin = (ListView) findViewById(R.id.lv_event_admin);
        lvEventAdmin.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
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
				returnIntent.putExtra("newEventAdminNRIC",getNewEventAdminNRIC());
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
