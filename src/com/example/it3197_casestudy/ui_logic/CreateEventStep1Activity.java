package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.transition.Visibility;
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
	String typeOfEvent;
	
	EditText etEventName,etDescription,etLocation;
	Spinner spinnerCategory,spinnerNoOfParticipants;
	ImageView ivPoster;
	TextView tvLocation, tvLocationAlt, tvPoster;
	
	static final int DBX_CHOOSER_REQUEST = 0;  // You can change this if needed
	Button btnUploadEventPoster,btnSuggestLocation;
	private DbxChooser mChooser;
	
	public EditText getEtEventName() {
		return etEventName;
	}

	public void setEtEventName(EditText etEventName) {
		this.etEventName = etEventName;
	}

	public EditText getEtDescription() {
		return etDescription;
	}

	public void setEtDescription(EditText etDescription) {
		this.etDescription = etDescription;
	}

	public EditText getEtLocation() {
		return etLocation;
	}

	public void setEtLocation(EditText etLocation) {
		this.etLocation = etLocation;
	}

	public Spinner getSpinnerCategory() {
		return spinnerCategory;
	}

	public void setSpinnerCategory(Spinner spinnerCategory) {
		this.spinnerCategory = spinnerCategory;
	}

	public Spinner getSpinnerNoOfParticipants() {
		return spinnerNoOfParticipants;
	}

	public void setSpinnerNoOfParticipants(Spinner spinnerNoOfParticipants) {
		this.spinnerNoOfParticipants = spinnerNoOfParticipants;
	}

	public ImageView getIvPoster() {
		return ivPoster;
	}

	public void setIvPoster(ImageView ivPoster) {
		this.ivPoster = ivPoster;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event_step_1);
		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			typeOfEvent = bundle.getString("typeOfEvent","Small Event");
		}
		etEventName = (EditText) findViewById(R.id.et_name);
		spinnerCategory = (Spinner) findViewById(R.id.spinner_category);
		etDescription = (EditText) findViewById(R.id.et_description);
		etLocation = (EditText) findViewById(R.id.et_location);
		spinnerNoOfParticipants = (Spinner) findViewById(R.id.spinner_no_of_participants);
		btnUploadEventPoster = (Button) findViewById(R.id.btn_upload_event_poster);
		btnSuggestLocation = (Button) findViewById(R.id.btn_suggest_location);
		ivPoster = (ImageView) findViewById(R.id.iv_event_poster);
		tvPoster = (TextView) findViewById(R.id.tv_event_poster);
		tvLocation = (TextView) findViewById(R.id.tv_location);
		tvLocationAlt = (TextView) findViewById(R.id.tv_location_alt);
		
		mChooser = new DbxChooser(DROPBOX_API_KEY);		
		btnUploadEventPoster.setOnClickListener(new OnClickListener(){
	        @Override
	        public void onClick(View v) {
	          mChooser.forResultType(DbxChooser.ResultType.FILE_CONTENT).launch(CreateEventStep1Activity.this, DBX_CHOOSER_REQUEST);
	        }
	    });
		
		tvPoster.setVisibility(View.GONE);
		if(typeOfEvent.equals("Big Event")){
			tvLocation.setVisibility(View.VISIBLE);
			tvLocationAlt.setVisibility(View.VISIBLE);
			etLocation.setVisibility(View.GONE);
			btnSuggestLocation.setVisibility(View.GONE);
		}
		else{
			tvLocation.setVisibility(View.VISIBLE);
			tvLocationAlt.setVisibility(View.GONE);
			etLocation.setVisibility(View.VISIBLE);
			btnSuggestLocation.setVisibility(View.VISIBLE);
		}
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
	            tvPoster.setVisibility(View.VISIBLE);
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
			case R.id.btn_suggest_location:{
				Intent i = new Intent(CreateEventStep1Activity.this,SuggestLocationActivity.class);
				startActivity(i);
			}
			case R.id.btn_next:
				Form mForm = new Form();
				
				Validate eventNameField = new Validate(etEventName);
				eventNameField.addValidator(new NotEmptyValidator(this));
				Validate eventDescriptionField = new Validate(etDescription);
				eventDescriptionField.addValidator(new NotEmptyValidator(this));
				Validate eventLocationField = new Validate(etLocation);
				eventLocationField.addValidator(new NotEmptyValidator(this));
				
				mForm.addValidates(eventNameField);
				mForm.addValidates(eventDescriptionField);
				
				ArrayList<Validate> validatorsArrList = new ArrayList<Validate>();
				validatorsArrList.add(eventNameField);
				validatorsArrList.add(eventDescriptionField);
				if(!typeOfEvent.equals("Big Event")){
					mForm.addValidates(eventLocationField);
					validatorsArrList.add(eventLocationField);
				}
				
				CreateEventStep1ValidationController validationController = new CreateEventStep1ValidationController(CreateEventStep1Activity.this,typeOfEvent);
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
