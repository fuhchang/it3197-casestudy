package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.validation.Form;
import com.example.it3197_casestudy.validation.Validate;
import com.example.it3197_casestudy.validation.validator.NotEmptyValidator;
import com.example.it3197_casestudy.validation_controller.CreateGrpStep1ValidationController;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateGroupActivityStep1 extends Activity {
	

	Button btnNext;
	private EditText gTitle;
	private Spinner cate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_group_activity_step1);
		btnNext = (Button) findViewById(R.id.btnNext);
		gTitle = (EditText) findViewById(R.id.gTitle);
		setCate((Spinner) findViewById(R.id.sType));
		btnNext.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Form mForm = new Form();
				Validate validTitle = new Validate(gTitle);
				validTitle.addValidator(new NotEmptyValidator(CreateGroupActivityStep1.this));
				
				mForm.addValidates(validTitle);
				
				ArrayList<Validate> validList = new ArrayList<Validate>();
				validList.add(validTitle);
				Intent intent = new Intent();
				CreateGrpStep1ValidationController validationController = new CreateGrpStep1ValidationController(CreateGroupActivityStep1.this);
				 validationController.validateForm(intent, mForm, validList);
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_group_activity_step1, menu);
		return true;
	}

	/**
	 * @return the cate
	 */
	public Spinner getCate() {
		return cate;
	}

	/**
	 * @param cate the cate to set
	 */
	public void setCate(Spinner cate) {
		this.cate = cate;
	}

	public EditText getgTitle() {
		return gTitle;
	}

	public void setgTitle(EditText gTitle) {
		this.gTitle = gTitle;
	}
	
}
