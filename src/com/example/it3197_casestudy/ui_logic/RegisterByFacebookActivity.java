package com.example.it3197_casestudy.ui_logic;

import com.example.it3197_casestudy.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class RegisterByFacebookActivity extends Activity {
	EditText fbUserNRIC,fbUserContactNo,fbUserAddress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_by_facebook);
		
		fbUserNRIC = (EditText) findViewById(R.id.et_fb_user_nric);
		fbUserContactNo = (EditText) findViewById(R.id.et_fb_contact_no);
		fbUserAddress = (EditText) findViewById(R.id.et_fb_user_address);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_by_facebook, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
