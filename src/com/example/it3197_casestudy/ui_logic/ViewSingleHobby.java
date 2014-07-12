package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.dropbox.chooser.android.R.color;
import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.listview.HobbyListView;
import com.example.it3197_casestudy.model.Hobby;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class ViewSingleHobby extends Activity {
	TextView grpTitle;
	ListView itemList;
	HobbyListView hobbyList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_single_hobby);
		grpTitle = (TextView) findViewById(R.id.grpTile);
		grpTitle.setTextSize(40);
		itemList = (ListView) findViewById(R.id.grpList);
		itemList.setBackgroundColor(Color.GRAY);
		ArrayList<Hobby> allHobbyList = new ArrayList<Hobby>();
		for(int i=0; i<3; i++){
			Hobby h = new Hobby();
			h.setGroupName(i+ " abc" + i *15);
			h.setCategory("type" +i);
			h.setDescription("asdasjhdkjbasdhbsdhfbasbdfhsbadf");
			allHobbyList.add(h);
		}
		hobbyList = new HobbyListView(this, allHobbyList);
		itemList.setAdapter(hobbyList);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_single_hobby, menu);
		return true;
	}

}
