package com.example.it3197_casestudy.ui_logic;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.array;
import com.example.it3197_casestudy.R.id;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class SubmitArticle extends Activity {

	Spinner spCat;
	ArrayAdapter<CharSequence> adapter;
	//TextView categorySelected;
	String[] categoryArray;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_article);
		
		getActionBar().setTitle("");
		
		/***Category Selection***/
		spCat = (Spinner) findViewById(R.id.spCat);
		//categorySelected =  (TextView) findViewById(R.id.categorySelected);
		Resources myRes = this.getResources();
		categoryArray = myRes.getStringArray(R.array.article_category_choice);
		
		adapter = ArrayAdapter.createFromResource(this, R.array.article_category_choice, android.R.layout.simple_spinner_dropdown_item);
		spCat.setAdapter(adapter);
		
		spCat.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				/**GET SELECTED VALUE**/
				//categorySelected.setText(arg0.getItemAtPosition(arg2).toString());
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub				
			}			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit_article, menu);
		return true;
	}

}
