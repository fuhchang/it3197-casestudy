package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.validation.Form;
import com.example.it3197_casestudy.validation.Validate;
import com.example.it3197_casestudy.validation.validator.NotEmptyValidator;
import com.example.it3197_casestudy.validation_controller.CreateGrpStep1ValidationController;
import com.example.it3197_casestudy.validation_controller.CreateGrpStep2ValidationController;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateGroupActivityStep2 extends Activity {
	Button btnNext;
	private EditText grpDesc;
	String title, category;
	private String nric;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_group_activity_step2); 
		grpDesc = (EditText) findViewById(R.id.etDesc);
		title = getIntent().getStringExtra("eventName");
		category = getIntent().getStringExtra("category");
		grpDesc = (EditText) findViewById(R.id.etDesc);
		nric = getIntent().getExtras().getString("nric");
	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_group_activity_step2, menu);
		return true;
	}

	/**
	 * @return the nric
	 */
	public String getNric() {
		return nric;
	}

	/**
	 * @param nric the nric to set
	 */
	public void setNric(String nric) {
		this.nric = nric;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.action_next:
			Form mForm = new Form();
			Validate validDesc = new Validate(grpDesc);
			validDesc.addValidator(new NotEmptyValidator(CreateGroupActivityStep2.this));
			
			mForm.addValidates(validDesc);
			
			ArrayList<Validate> validList2 = new ArrayList<Validate>();
			validList2.add(validDesc);
			Intent intent = new Intent();
			
			
			CreateGrpStep2ValidationController validationController = new CreateGrpStep2ValidationController(CreateGroupActivityStep2.this,title,category,grpDesc.getText().toString());
			 validationController.validateForm(intent, mForm, validList2);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
}
