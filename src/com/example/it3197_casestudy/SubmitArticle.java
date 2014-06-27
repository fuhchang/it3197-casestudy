package com.example.it3197_casestudy;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SubmitArticle extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_article);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit_article, menu);
		return true;
	}

}
