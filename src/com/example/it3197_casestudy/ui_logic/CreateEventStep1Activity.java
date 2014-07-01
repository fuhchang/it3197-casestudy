package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dropbox.chooser.android.DbxChooser;
import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.crouton.Crouton;
import com.example.it3197_casestudy.crouton.Style;
import com.example.it3197_casestudy.util.Settings;
import com.example.it3197_casestudy.validation.Form;
import com.example.it3197_casestudy.validation.Validate;
import com.example.it3197_casestudy.validation.validator.NotEmptyValidator;
import com.example.it3197_casestudy.validation_controller.CreateEventStep1ValidationController;

public class CreateEventStep1Activity extends Activity implements Settings{
	EditText etEventName,etDescription,etLocation,etNoOfParticipants;
	Spinner spinnerCategory;
	ImageView ivPoster;
	
	static final int DBX_CHOOSER_REQUEST = 0;  // You can change this if needed
	Button btnUploadEventPoster,btnSuggestLocation;
	private DbxChooser mChooser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event_step_1);
		
		etEventName = (EditText) findViewById(R.id.et_name);
		spinnerCategory = (Spinner) findViewById(R.id.spinner_category);
		etDescription = (EditText) findViewById(R.id.et_description);
		etLocation = (EditText) findViewById(R.id.et_location);
		etNoOfParticipants = (EditText) findViewById(R.id.et_number_of_participants);
		btnUploadEventPoster = (Button) findViewById(R.id.btn_upload_event_poster);
		btnSuggestLocation = (Button) findViewById(R.id.btn_suggest_location);
		ivPoster = (ImageView) findViewById(R.id.iv_event_poster);
		mChooser = new DbxChooser(DROPBOX_API_KEY);		
		btnUploadEventPoster.setOnClickListener(new OnClickListener(){
	        @Override
	        public void onClick(View v) {
	          mChooser.forResultType(DbxChooser.ResultType.FILE_CONTENT).launch(CreateEventStep1Activity.this, DBX_CHOOSER_REQUEST);
	        }
	    });
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == DBX_CHOOSER_REQUEST) {
	        if (resultCode == Activity.RESULT_OK) {
	            DbxChooser.Result result = new DbxChooser.Result(data);
	            Log.d("main", "Link to selected file name: " + result.getName());
	            String fileName = result.getName();
	            String validatingFileName = fileName.substring(fileName.lastIndexOf("."),fileName.length());
	            Log.d("main", "Link to selected file extension: " + validatingFileName);
	            Log.d("main", "Link to selected file: " + result.getLink());
	            ivPoster.setImageURI(result.getLink());
	            // Handle the result
	        } else {
	            // Failed or was cancelled by the user.
	        }
	    } else {
	        super.onActivityResult(requestCode, resultCode, data);
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}
	
	public void onClick(View view){
		Intent intent = null;
		try {
			switch (view.getId()) {
			case R.id.btn_next:
				Form mForm = new Form();
				
				Validate eventNameField = new Validate(etEventName);
				eventNameField.addValidator(new NotEmptyValidator(this));
				mForm.addValidates(eventNameField);
				
				ArrayList<Validate> validatorsArrList = new ArrayList<Validate>();
				validatorsArrList.add(eventNameField);
				
				CreateEventStep1ValidationController validationController = new CreateEventStep1ValidationController(CreateEventStep1Activity.this);
				validationController.validateForm(intent, mForm, validatorsArrList);
				break;
			case R.id.btn_cancel:
				onBackPressed();
				break;
			default:
				CreateEventStep1Activity.this.finish();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override 
	public void onBackPressed(){  
		Intent intent = new Intent(CreateEventStep1Activity.this, ViewAllEventsActivity.class);
		startActivity(intent);
		this.finish();
	}
}
