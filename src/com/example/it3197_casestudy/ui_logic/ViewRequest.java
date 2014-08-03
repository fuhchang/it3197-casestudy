package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetRequestView;
import com.example.it3197_casestudy.model.Hobby;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.widget.ListView;

public class ViewRequest extends Activity {
	private ListView requestList;
	private ArrayList<Hobby> hobbyList;
	private int hobbyID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_request);
		requestList = (ListView) findViewById(R.id.requestList);
		requestList.setBackgroundColor(Color.LTGRAY);
		hobbyID = getIntent().getExtras().getInt("hobbyID");
		GetRequestView getRequest = new GetRequestView(this,requestList, hobbyID);
		getRequest.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_request, menu);
		return true;
	}

}
