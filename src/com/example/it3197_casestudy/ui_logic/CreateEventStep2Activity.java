package com.example.it3197_casestudy.ui_logic;

import java.util.Calendar;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.model.EventLocationDetail;
import com.example.it3197_casestudy.util.Settings;
import com.example.it3197_casestudy.validation.Form;
import com.example.it3197_casestudy.validation.validate.ConfirmValidate;
import com.example.it3197_casestudy.validation_controller.CreateEventStep2ValidationController;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateEventStep2Activity extends Activity implements Settings{
	Event event;
	EventLocationDetail eventLocationDetails = new EventLocationDetail();
	
	Button btnDateFrom, btnDateTo, btnTimeFrom, btnTimeTo;
	Spinner spinnerRepeats;
	TextView tvRepeats;
	
	private int yearFrom;
	private int monthFrom;
	private int dayFrom;
	private int yearTo;
	private int monthTo;
	private int dayTo;
	
	private int hourFrom;
	private int minuteFrom;
	private int hourTo;
	private int minuteTo;

	static final int DATE_DIALOG_ID = 999;
	static final int DATE_DIALOG_ID_1 = 1000;

	static final int TIME_DIALOG_ID = 99;
	static final int TIME_DIALOG_ID_1 = 100;
	private UiLifecycleHelper uiHelper;
	private String posterFileName;
	private boolean requestHelp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event_step_2);
		uiHelper = new UiLifecycleHelper(this, null);
	    uiHelper.onCreate(savedInstanceState);
	    
		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			posterFileName = bundle.getString("posterFileName", "");
			String eventName = bundle.getString("eventName", "");
			String eventCategory = bundle.getString("eventCategory","");
			String eventDescription = bundle.getString("eventDescription", "");
			String noOfParticipants = bundle.getString("noOfParticipants", "");
			event = new Event();
			event.setEventName(eventName);
			event.setEventCategory(eventCategory);
			event.setEventDescription(eventDescription);
			if(noOfParticipants.equals("0 - 99")){
				event.setNoOfParticipantsAllowed(99);
			}
			else if(noOfParticipants.equals("100 - 499")){
				event.setNoOfParticipantsAllowed(499);
			}
			else if(noOfParticipants.equals("500 - 999")){
				event.setNoOfParticipantsAllowed(999);
			}
			else if(noOfParticipants.equals("1000 - 9999")){
				event.setNoOfParticipantsAllowed(9999);
			}
			else if(noOfParticipants.equals("10000 - 99999")){
				event.setNoOfParticipantsAllowed(99999);
			}
			
			String locationName = bundle.getString("locationName", "");
			String locationAddress = bundle.getString("locationAddress", "");
			String locationHyperLink = bundle.getString("locationHyperLink", "");
			double lat = bundle.getDouble("lat", 0.0000);
			double lng = bundle.getDouble("lng", 0.0000);
			
			requestHelp = bundle.getBoolean("requestHelp", false);
			
			eventLocationDetails = new EventLocationDetail(0,0,locationName,locationAddress,locationHyperLink,lat,lng);
		}
		
		btnDateFrom = (Button) findViewById(R.id.btn_date_from);
		btnDateTo = (Button) findViewById(R.id.btn_date_to);
		btnTimeFrom = (Button) findViewById(R.id.btn_time_from);
		btnTimeTo = (Button) findViewById(R.id.btn_time_to);
		spinnerRepeats = (Spinner) findViewById(R.id.spinner_repeats);
		tvRepeats = (TextView) findViewById(R.id.tv_repeats);
		
		Calendar calendarFrom = Calendar.getInstance();
		dayFrom = calendarFrom.get(Calendar.DAY_OF_MONTH);
		monthFrom = calendarFrom.get(Calendar.MONTH);
		yearFrom = calendarFrom.get(Calendar.YEAR);
		hourFrom = calendarFrom.get(Calendar.HOUR_OF_DAY);
		minuteFrom = calendarFrom.get(Calendar.MINUTE);
		
		Calendar calendarTo = Calendar.getInstance();
		dayTo = calendarTo.get(Calendar.DAY_OF_MONTH);
		monthTo = calendarTo.get(Calendar.MONTH);
		yearTo = calendarTo.get(Calendar.YEAR);
		hourTo = calendarTo.get(Calendar.HOUR_OF_DAY);
		minuteTo = calendarTo.get(Calendar.MINUTE) + 1;
		
		btnDateFrom.setText(new StringBuilder().append(dayFrom).append("/").append(monthFrom + 1).append("/").append(yearFrom).append(" "));
		btnDateTo.setText(new StringBuilder().append(dayTo).append("/").append(monthTo + 1).append("/").append(yearTo).append(" "));		
		btnTimeFrom.setText(new StringBuilder().append(pad(hourFrom)).append(":").append(pad(minuteFrom)));
		btnTimeTo.setText(new StringBuilder().append(pad(hourTo)).append(":").append(pad(minuteTo)));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_event_step_2_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent = null;
		int id = item.getItemId();
		switch(id){
		case R.id.create_event:
			Calendar calendarFrom = Calendar.getInstance();
			calendarFrom.set(Calendar.DAY_OF_MONTH,dayFrom);
			calendarFrom.set(Calendar.MONTH,monthFrom);
			calendarFrom.set(Calendar.YEAR,yearFrom);
			calendarFrom.set(Calendar.HOUR_OF_DAY,hourFrom);
			calendarFrom.set(Calendar.MINUTE,minuteFrom);
			
			Calendar calendarTo = Calendar.getInstance();
			calendarTo.set(Calendar.DAY_OF_MONTH,dayTo);
			calendarTo.set(Calendar.MONTH,monthTo);
			calendarTo.set(Calendar.YEAR,yearTo);
			calendarTo.set(Calendar.HOUR_OF_DAY,hourTo);
			calendarTo.set(Calendar.MINUTE,minuteTo);
			
			System.out.println("From Date: " + sqlDateTimeFormatter.format(calendarFrom.getTime()));
			System.out.println("To Date: " + sqlDateTimeFormatter.format(calendarTo.getTime()));
			
			CreateEventStep2ValidationController controller = new CreateEventStep2ValidationController(CreateEventStep2Activity.this,eventLocationDetails,posterFileName);
			controller.validateForm(intent,calendarFrom,calendarTo,event,spinnerRepeats.getSelectedItem().toString(),requestHelp);
			break;	
		case R.id.magic_button:
			btnDateFrom.setText(new StringBuilder().append(11).append("/").append(9 + 1).append("/").append(2014).append(" "));
			btnDateTo.setText(new StringBuilder().append(13).append("/").append(9 + 1).append("/").append(2014).append(" "));		
			btnTimeFrom.setText(new StringBuilder().append(pad(10)).append(":").append(pad(30)));
			btnTimeTo.setText(new StringBuilder().append(pad(4)).append(":").append(pad(30)));
			break;
		case R.id.previous:
			onBackPressed();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClick(View view) {
		//Intent intent = null;
		try {
			switch (view.getId()) {
			case R.id.btn_date_from:
				showDialog(DATE_DIALOG_ID);
				break;
			case R.id.btn_date_to:
				showDialog(DATE_DIALOG_ID_1);
				break;
			case R.id.btn_time_from:
				showDialog(TIME_DIALOG_ID);
				break;
			case R.id.btn_time_to:
				showDialog(TIME_DIALOG_ID_1);
				break;
			default:
				CreateEventStep2Activity.this.finish();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(CreateEventStep2Activity.this,CreateEventStep1Activity.class);
		startActivity(intent);
		this.finish();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerFromListener, yearFrom,
					monthFrom, dayFrom);

		case DATE_DIALOG_ID_1:
			return new DatePickerDialog(this, datePickerToListener, yearTo,
					monthTo, dayTo);
		
		case TIME_DIALOG_ID:
			// set time picker as current time
			return new TimePickerDialog(this, 
                                        timePickerFromListener, hourFrom, minuteFrom,false);
		case TIME_DIALOG_ID_1:
			// set time picker as current time
			return new TimePickerDialog(this, 
                                        timePickerToListener, hourTo, minuteTo,false);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerFromListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			yearFrom = selectedYear;
			monthFrom = selectedMonth;
			dayFrom = selectedDay;

			// set selected date into textview
			btnDateFrom.setText(new StringBuilder().append(dayFrom).append("/")
					.append(monthFrom + 1).append("/").append(yearFrom).append(" "));
		}
	};
	private DatePickerDialog.OnDateSetListener datePickerToListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			yearTo = selectedYear;
			monthTo = selectedMonth;
			dayTo = selectedDay;

			// set selected date into textview
			btnDateTo.setText(new StringBuilder().append(dayTo).append("/")
					.append(monthTo + 1).append("/").append(yearTo).append(" "));
		}
	};
	 
	private TimePickerDialog.OnTimeSetListener timePickerFromListener = 
            new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hourFrom = selectedHour;
			minuteFrom = selectedMinute;
 
			// set current time into textview
			btnTimeFrom.setText(new StringBuilder().append(pad(hourFrom)).append(":").append(pad(minuteFrom)));
		}
	};
	
	private TimePickerDialog.OnTimeSetListener timePickerToListener = 
            new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hourTo = selectedHour;
			minuteTo = selectedMinute;
 
			// set current time into textview
			btnTimeTo.setText(new StringBuilder().append(pad(hourTo)).append(":").append(pad(minuteTo)));
		}
	};
 
	private static String pad(int c) {
		if (c >= 10)
		   return String.valueOf(c);
		else
		   return "0" + String.valueOf(c);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
	        @Override
	        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	            Log.e("Activity", String.format("Error: %s", error.toString()));
	        }

	        @Override
	        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	            Log.i("Activity", "Success!");
	        }
	    });
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}
}
