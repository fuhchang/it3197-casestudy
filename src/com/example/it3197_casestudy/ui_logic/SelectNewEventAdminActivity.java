package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SelectNewEventAdminActivity extends Activity{
	
	ListView lvEventAdmin;
	ArrayList<String> nricArrList = new ArrayList<String>();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_new_event_admin);
        savedInstanceState = getIntent().getExtras();
        if(savedInstanceState != null){
        	String nricList[] = savedInstanceState.getStringArray("nricList");
        	for(int i=0;i<nricList.length;i++){
        		nricArrList.add(nricList[i]);
        	}
        }
        lvEventAdmin = (ListView) findViewById(R.id.lv_event_admin);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,nricArrList);
        
        lvEventAdmin.setAdapter(adapter);
        lvEventAdmin.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		//return super.onCreateOptionsMenu(menu);
		return false;
	}
}
