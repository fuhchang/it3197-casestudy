package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.transition.Visibility;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dropbox.chooser.android.DbxChooser;
import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetImageFromDropbox;
import com.example.it3197_casestudy.crouton.Crouton;
import com.example.it3197_casestudy.crouton.Style;
import com.example.it3197_casestudy.model.EventLocationDetail;
import com.example.it3197_casestudy.util.Settings;
import com.example.it3197_casestudy.validation.Form;
import com.example.it3197_casestudy.validation.Validate;
import com.example.it3197_casestudy.validation.validator.NotEmptyValidator;
import com.example.it3197_casestudy.validation_controller.CreateEventStep1ValidationController;
import com.example.it3197_casestudy.validation_controller.UpdateEventStep1ValidationController;

public class UpdateEventStep1Activity extends Activity implements Settings{
	String typeOfEvent,eventID,eventName,eventCategory,eventDescription,eventDateTimeFrom,eventDateTimeTo,occurence,eventLocation,noOfParticipants;
	
	EventLocationDetail eventLocationDetails = new EventLocationDetail();
	
	EditText etEventName,etDescription,etLocation;
	Spinner spinnerCategory,spinnerNoOfParticipants;
	ImageView ivPoster;
	TextView tvLocation, tvLocationAlt, tvPoster;
	CheckBox cBoxRequestHelp;
	
	static final int DBX_CHOOSER_REQUEST = 0;  // You can change this if needed
	Button btnUploadEventPoster,btnSuggestLocation;
	private DbxChooser mChooser;
	private String posterFileName;
	
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

	public CheckBox getcBoxRequestHelp() {
		return cBoxRequestHelp;
	}

	public void setcBoxRequestHelp(CheckBox cBoxRequestHelp) {
		this.cBoxRequestHelp = cBoxRequestHelp;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_event_step_1);
		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			typeOfEvent = bundle.getString("typeOfEvent","Small Event");
			eventID = bundle.getString("eventID","0");
			eventName = bundle.getString("eventName", "");
			eventCategory = bundle.getString("eventCategory", "");
			eventDescription = bundle.getString("eventDescription", "");
			eventDateTimeFrom = bundle.getString("eventDateTimeFrom", "");
			eventDateTimeTo = bundle.getString("eventDateTimeTo", "");
			occurence = bundle.getString("occurence", "");
			noOfParticipants = bundle.getString("noOfParticipants", "0");
			posterFileName = bundle.getString("pictureURL","");
			
			System.out.println(noOfParticipants);
		}
		etEventName = (EditText) findViewById(R.id.et_name);
		etEventName.setText(eventName);
		
		spinnerCategory = (Spinner) findViewById(R.id.spinner_category);
		spinnerCategory.setSelection(((ArrayAdapter<CharSequence>)spinnerCategory.getAdapter()).getPosition(eventCategory));
		
		etDescription = (EditText) findViewById(R.id.et_description);
		etDescription.setText(eventDescription);
		
		etLocation = (EditText) findViewById(R.id.et_location);
		etLocation.setText(eventLocation);
		
		spinnerNoOfParticipants = (Spinner) findViewById(R.id.spinner_no_of_participants);
		if(noOfParticipants.equals("99")){
			spinnerNoOfParticipants.setSelection(0);
		}
		else if(noOfParticipants.equals("499")){
			spinnerNoOfParticipants.setSelection(1);
		}
		else if(noOfParticipants.equals("999")){
			spinnerNoOfParticipants.setSelection(2);
		}
		else if(noOfParticipants.equals("9999")){
			spinnerNoOfParticipants.setSelection(3);
		}
		else if(noOfParticipants.equals("99999")){
			spinnerNoOfParticipants.setSelection(4);
		}
		
		btnUploadEventPoster = (Button) findViewById(R.id.btn_upload_event_poster);
		btnSuggestLocation = (Button) findViewById(R.id.btn_suggest_location);
		ivPoster = (ImageView) findViewById(R.id.iv_event_poster);
		tvPoster = (TextView) findViewById(R.id.tv_event_poster);
		tvLocation = (TextView) findViewById(R.id.tv_location);
		tvLocationAlt = (TextView) findViewById(R.id.tv_location_alt);

		cBoxRequestHelp = (CheckBox) findViewById(R.id.cbox_request_help);
		mChooser = new DbxChooser(DROPBOX_API_KEY);		
		btnUploadEventPoster.setOnClickListener(new OnClickListener(){
	        @Override
	        public void onClick(View v) {
	          mChooser.forResultType(DbxChooser.ResultType.PREVIEW_LINK).launch(UpdateEventStep1Activity.this, DBX_CHOOSER_REQUEST);
	        }
	    });
		if((posterFileName != "") && (!posterFileName.equals(""))){
			ivPoster.setVisibility(View.VISIBLE);
			tvPoster.setVisibility(View.VISIBLE);
            GetImageFromDropbox getImageFromDropbox = new GetImageFromDropbox(UpdateEventStep1Activity.this,ivPoster, posterFileName);
            getImageFromDropbox.execute();
		}
		else{
			ivPoster.setVisibility(View.GONE);
			tvPoster.setVisibility(View.GONE);
		}
		
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
	            posterFileName = result.getLink().toString() + "?dl=1";
	            String validatingFileName = fileName.substring(fileName.lastIndexOf("."),fileName.length());
	            Log.d("main", "Link to selected file extension: " + validatingFileName);
	            Log.d("main", "Link to selected file: " + result.getLink());
	            GetImageFromDropbox getImageFromDropbox = new GetImageFromDropbox(UpdateEventStep1Activity.this,ivPoster, posterFileName);
	            getImageFromDropbox.execute();
	            tvPoster.setVisibility(View.VISIBLE);
	            // Handle the result
	        } else {
	            // Failed or was cancelled by the user.
	        }
	    } 
	    else if(requestCode == 200){
	        if (resultCode == RESULT_OK) {
	        	String name = data.getStringExtra("eventLocationName");
	        	String address = data.getStringExtra("eventLocationAddress");
	        	String hyperlink = data.getStringExtra("eventLocationHyperLink");
	        	double lat = data.getDoubleExtra("eventLocationLat", 0.00);
	        	double lng = data.getDoubleExtra("eventLocationLng", 0.00);
				eventLocationDetails = new EventLocationDetail(0,0,name,address,hyperlink,lat,lng);
				etLocation.setText(address);
	        }
	    }else {
	        super.onActivityResult(requestCode, resultCode, data);
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_event_step_1_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent = null;
		int id = item.getItemId();
		switch(id){
		case R.id.next:
			Form mForm = new Form();
			
			Validate eventNameField = new Validate(etEventName);
			eventNameField.addValidator(new NotEmptyValidator(this));
			Validate eventDescriptionField = new Validate(etDescription);
			eventDescriptionField.addValidator(new NotEmptyValidator(this));
			Validate eventLocationField = new Validate(etLocation);
			eventLocationField.addValidator(new NotEmptyValidator(this));
			
			mForm.addValidates(eventNameField);
			mForm.addValidates(eventDescriptionField);
			mForm.addValidates(eventLocationField);
			
			ArrayList<Validate> validatorsArrList = new ArrayList<Validate>();
			validatorsArrList.add(eventNameField);
			validatorsArrList.add(eventDescriptionField);
			validatorsArrList.add(eventLocationField);
			
			UpdateEventStep1ValidationController validationController = new UpdateEventStep1ValidationController(UpdateEventStep1Activity.this,eventLocationDetails);
			validationController.validateForm(eventID, intent, mForm, validatorsArrList, eventDateTimeFrom, eventDateTimeTo, occurence, posterFileName);
			break;

		case R.id.cancel:
			onBackPressed();
			break;
		default:
			UpdateEventStep1Activity.this.finish();
			break;	
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClick(View view){
		//Intent intent = null;
		try {
			switch (view.getId()) {
			case R.id.btn_suggest_location:{
				Intent i = new Intent(UpdateEventStep1Activity.this,SuggestLocationActivity.class);
				i.putExtra("category", spinnerCategory.getSelectedItem().toString());
				startActivityForResult(i,200);
				eventLocationDetails = new EventLocationDetail(0,0,"Test","Test","Test",1.0,2.0);
				break;
			}
			default:
				UpdateEventStep1Activity.this.finish();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override 
	public void onBackPressed(){  
		Intent intent = new Intent(UpdateEventStep1Activity.this, ViewAllEventsActivity.class);
		startActivity(intent);
		UpdateEventStep1Activity.this.finish();
	}
}
