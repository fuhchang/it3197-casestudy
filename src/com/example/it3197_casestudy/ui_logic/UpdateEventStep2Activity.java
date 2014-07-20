package com.example.it3197_casestudy.ui_logic;

import java.text.ParseException;
import java.util.Calendar;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.util.Settings;
import com.example.it3197_casestudy.validation.Form;
import com.example.it3197_casestudy.validation.validate.ConfirmValidate;
import com.example.it3197_casestudy.validation_controller.CreateEventStep2ValidationController;
import com.example.it3197_casestudy.validation_controller.UpdateEventStep2ValidationController;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class UpdateEventStep2Activity extends Activity implements Settings{
	Event event;
	String typeOfEvent;
	
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_event_step_2);
		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			String eventID = bundle.getString("eventID","0");
			String eventName = bundle.getString("eventName", "");
			String eventCategory = bundle.getString("eventCategory","");
			String eventDescription = bundle.getString("eventDescription", "");
			typeOfEvent = bundle.getString("typeOfEvent","Small Event");
			String eventLocation = bundle.getString("eventLocation", "");
			String noOfParticipants = bundle.getString("noOfParticipants", "");
			String eventDateTimeFrom = bundle.getString("eventDateTimeFrom","");
			String eventDateTimeTo = bundle.getString("eventDateTimeTo","");
			String occurence = bundle.getString("occurence","");
			event = new Event();
			event.setEventID(Integer.parseInt(eventID));
			event.setEventName(eventName);
			event.setEventCategory(eventCategory);
			event.setEventDescription(eventDescription);
			event.setEventType(typeOfEvent);
			event.setEventLocation(eventLocation);
			try {
				event.setEventDateTimeFrom(sqlDateTimeFormatter.parse(eventDateTimeFrom));
				event.setEventDateTimeTo(sqlDateTimeFormatter.parse(eventDateTimeTo));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			event.setOccurence(occurence);
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
		}
		
		btnDateFrom = (Button) findViewById(R.id.btn_date_from);
		btnDateTo = (Button) findViewById(R.id.btn_date_to);
		btnTimeFrom = (Button) findViewById(R.id.btn_time_from);
		btnTimeTo = (Button) findViewById(R.id.btn_time_to);
		spinnerRepeats = (Spinner) findViewById(R.id.spinner_repeats);
		spinnerRepeats.setSelection(((ArrayAdapter<CharSequence>)spinnerRepeats.getAdapter()).getPosition(event.getOccurence()));
		
		tvRepeats = (TextView) findViewById(R.id.tv_repeats);
		
		Calendar calendarFrom = Calendar.getInstance();
		calendarFrom.setTime(event.getEventDateTimeFrom());
		dayFrom = calendarFrom.get(Calendar.DAY_OF_MONTH);
		monthFrom = calendarFrom.get(Calendar.MONTH);
		yearFrom = calendarFrom.get(Calendar.YEAR);
		hourFrom = calendarFrom.get(Calendar.HOUR_OF_DAY);
		minuteFrom = calendarFrom.get(Calendar.MINUTE);
		
		Calendar calendarTo = Calendar.getInstance();
		calendarTo.setTime(event.getEventDateTimeTo());
		dayTo = calendarTo.get(Calendar.DAY_OF_MONTH);
		monthTo = calendarTo.get(Calendar.MONTH);
		yearTo = calendarTo.get(Calendar.YEAR);
		hourTo = calendarTo.get(Calendar.HOUR_OF_DAY);
		minuteTo = calendarTo.get(Calendar.MINUTE);
		
		btnDateFrom.setText(new StringBuilder().append(dayFrom).append("/").append(monthFrom + 1).append("/").append(yearFrom).append(" "));
		btnDateTo.setText(new StringBuilder().append(dayTo).append("/").append(monthTo + 1).append("/").append(yearTo).append(" "));		
		btnTimeFrom.setText(new StringBuilder().append(pad(hourFrom)).append(":").append(pad(minuteFrom)));
		btnTimeTo.setText(new StringBuilder().append(pad(hourTo)).append(":").append(pad(minuteTo)));
		
		if(typeOfEvent.equals("Small Event")){
			spinnerRepeats.setVisibility(View.GONE);
			tvRepeats.setVisibility(View.GONE);
		}
		else{
			spinnerRepeats.setVisibility(View.VISIBLE);
			tvRepeats.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_event_step_2_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent = null;
		int id = item.getItemId();
		switch(id){
		case R.id.update_event:
			/*String fromDate = btnDateFrom.getText().toString() + btnTimeFrom.getText().toString(); 
			String toDate = btnDateTo.getText().toString() + btnTimeTo.getText().toString();*/
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
			
			UpdateEventStep2ValidationController controller = new UpdateEventStep2ValidationController(UpdateEventStep2Activity.this,typeOfEvent);
			controller.validateForm(intent,calendarFrom,calendarTo,event,spinnerRepeats.getSelectedItem().toString());
			break;
		case R.id.previous:
			onBackPressed();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClick(View view) {
		//Intent intent = null;
		Log.i("Type of event: ",typeOfEvent);
		try {
			switch (view.getId()) {
			case R.id.btn_date_from:
				showDialog(DATE_DIALOG_ID);
				break;
			case R.id.btn_date_to:
				if(typeOfEvent.equals("Small Event")){
					showDialog(DATE_DIALOG_ID);
				}
				else{
					showDialog(DATE_DIALOG_ID_1);
				}
				break;
			case R.id.btn_time_from:
				showDialog(TIME_DIALOG_ID);
				break;
			case R.id.btn_time_to:
				showDialog(TIME_DIALOG_ID_1);
				break;
			default:
				UpdateEventStep2Activity.this.finish();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(UpdateEventStep2Activity.this,UpdateEventStep1Activity.class);
		intent.putExtra("typeOfEvent", typeOfEvent);
		intent.putExtra("eventName", event.getEventName());
		intent.putExtra("eventCategory", event.getEventCategory());
		intent.putExtra("eventDescription", event.getEventDescription());
		intent.putExtra("eventDateTimeFrom", sqlDateTimeFormatter.format(event.getEventDateTimeFrom()));
		intent.putExtra("eventDateTimeTo", sqlDateTimeFormatter.format(event.getEventDateTimeTo()));
		intent.putExtra("occurence", event.getOccurence());
		intent.putExtra("eventLocation", event.getEventLocation());
		intent.putExtra("noOfParticipants", String.valueOf(event.getNoOfParticipantsAllowed()));
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
			
			if(typeOfEvent.equals("Small Event")){
				yearTo = selectedYear;
				monthTo = selectedMonth;
				dayTo = selectedDay;

				// set selected date into textview
				btnDateTo.setText(new StringBuilder().append(dayTo).append("/")
						.append(monthTo + 1).append("/").append(yearTo).append(" "));
			}
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
			
			if(typeOfEvent.equals("Small Event")){
				yearFrom = selectedYear;
				monthFrom = selectedMonth;
				dayFrom = selectedDay;

				// set selected date into textview
				btnDateFrom.setText(new StringBuilder().append(dayFrom).append("/")
						.append(monthFrom + 1).append("/").append(yearFrom).append(" "));
			}
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
}
