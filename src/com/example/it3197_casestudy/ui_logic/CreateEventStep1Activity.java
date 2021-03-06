package com.example.it3197_casestudy.ui_logic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.transition.Visibility;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.chooser.android.DbxChooser;
import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetImageFromDropbox;
import com.example.it3197_casestudy.crouton.Crouton;
import com.example.it3197_casestudy.crouton.Style;
import com.example.it3197_casestudy.googlePlaces.CheckPlaces;
import com.example.it3197_casestudy.googlePlaces.GetPlaces;
import com.example.it3197_casestudy.googlePlaces.Place;
import com.example.it3197_casestudy.model.EventLocationDetail;
import com.example.it3197_casestudy.util.Settings;
import com.example.it3197_casestudy.validation.Form;
import com.example.it3197_casestudy.validation.Validate;
import com.example.it3197_casestudy.validation.validator.NotEmptyValidator;
import com.example.it3197_casestudy.validation_controller.CreateEventStep1ValidationController;

public class CreateEventStep1Activity extends Activity implements Settings{
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
	
	private ArrayList<Place> notRecommendedPlacesArrList ;
	
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

	public ArrayList<Place> getNotRecommendedPlacesArrList() {
		return notRecommendedPlacesArrList;
	}

	public void setNotRecommendedPlacesArrList(
			ArrayList<Place> notRecommendedPlacesArrList) {
		this.notRecommendedPlacesArrList = notRecommendedPlacesArrList;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event_step_1);
		
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
		cBoxRequestHelp = (CheckBox) findViewById(R.id.cbox_request_help);
		
		mChooser = new DbxChooser(DROPBOX_API_KEY);		
		btnUploadEventPoster.setOnClickListener(new OnClickListener(){
	        @Override
	        public void onClick(View v) {
	          mChooser.forResultType(DbxChooser.ResultType.PREVIEW_LINK).launch(CreateEventStep1Activity.this, DBX_CHOOSER_REQUEST);
	        }
	    });
		
		ivPoster.setVisibility(View.GONE);
		tvPoster.setVisibility(View.GONE);
		tvLocationAlt.setVisibility(View.GONE);
		
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
	            GetImageFromDropbox getImageFromDropbox = new GetImageFromDropbox(CreateEventStep1Activity.this,ivPoster, posterFileName);
	            getImageFromDropbox.execute();
	            tvPoster.setVisibility(View.VISIBLE);
	            // Handle the result
	        } else {
	            // Failed or was cancelled by the user.
	        }
	    } 
	    else if(requestCode == 100){
	        if (resultCode == RESULT_OK) {
	        	String name = data.getStringExtra("eventLocationName");
	        	String address = data.getStringExtra("eventLocationAddress");
	        	String hyperlink = data.getStringExtra("eventLocationHyperLink");
	        	double lat = data.getDoubleExtra("eventLocationLat", 0.00);
	        	double lng = data.getDoubleExtra("eventLocationLng", 0.00);
				eventLocationDetails = new EventLocationDetail(0,0,name,address,hyperlink,lat,lng);
				etLocation.setText(address);
	        }
	    }
	    else {
	        super.onActivityResult(requestCode, resultCode, data);
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_event_step_1_menu, menu);
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
			
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			String provider = locationManager.getBestProvider(new Criteria(), false);
			Location location = new Location(provider);
			Geocoder geoCoder = new Geocoder(CreateEventStep1Activity.this);
			List<Address> addressList = null;
			try {
				String trimmed = etLocation.getText().toString().trim();
				int words = trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;
				System.out.println(words);
				if(words > 2){
					addressList = geoCoder.getFromLocationName(etLocation.getText().toString(), 1);
				}
				else{
					addressList = new ArrayList<Address>();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(eventLocationDetails.getEventLocationLat() != 0.00){
				location.setLatitude(eventLocationDetails.getEventLocationLat());
			}
			else{
				for(int i=0;i<addressList.size();i++){
					location.setLatitude(addressList.get(i).getLatitude());
				}	
			}
			if(eventLocationDetails.getEventLocationLng() != 0.00){
				location.setLongitude(eventLocationDetails.getEventLocationLng());
			}
			else{
				for(int i=0;i<addressList.size();i++){
					location.setLongitude(addressList.get(i).getLongitude());
				}	
			}
			
			if((location.getLatitude() == 0) && (location.getLongitude() == 0)){
				Toast.makeText(this, "Invalid address. Please put a valid address", Toast.LENGTH_LONG).show();
			}
			else{
				String notRecommended = "bank|bar|casino|church|cemetery|courthouse|embassy|funeral_home|gas_station|liquor_store|night_club|spa";
	
				CreateEventStep1ValidationController validationController = new CreateEventStep1ValidationController(CreateEventStep1Activity.this,eventLocationDetails);
				CheckPlaces checkPlaces = new CheckPlaces(CreateEventStep1Activity.this,notRecommended,location,intent,mForm,validatorsArrList,posterFileName,validationController);
				checkPlaces.execute();
			}
			break;
		case R.id.magic_button:
			String eventName = "Caregiving Welfare Association Fund Raising Event";
			String eventDescription = "We are welcoming participants for CWA Fund Raising Event, scheduled on 28 Aug 2014 (Saturday), from 8.30am to 4.30pm. All donations collected will go towards the well-being of our clients. ";
			
			boolean requestHelp = true;
			
			etEventName.setText(eventName);
			etDescription.setText(eventDescription);
			spinnerCategory.setSelection(2);
			spinnerNoOfParticipants.setSelection(4);
			cBoxRequestHelp.setChecked(requestHelp);
			
			break;
		case R.id.cancel:
			onBackPressed();
			break;
		default:
			CreateEventStep1Activity.this.finish();
			break;	
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClick(View view){
		//Intent intent = null;
		try {
			switch (view.getId()) {
			case R.id.btn_suggest_location:{
				Intent i = new Intent(CreateEventStep1Activity.this,SuggestLocationActivity.class);
				i.putExtra("category", spinnerCategory.getSelectedItem().toString());
				startActivityForResult(i,100);
				break;
			}
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
