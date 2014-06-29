package com.example.it3197_casestudy.ui_logic;

import java.util.Calendar;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateEventStep2Activity extends Activity {
	Button btnDateFrom, btnDateTo, btnTimeFrom, btnTimeTo;
	Spinner spinnerRepeats;

	private int year;
	private int month;
	private int day;
	
	private int hour;
	private int minute;

	static final int DATE_DIALOG_ID = 999;
	static final int DATE_DIALOG_ID_1 = 1000;

	static final int TIME_DIALOG_ID = 99;
	static final int TIME_DIALOG_ID_1 = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event_step_2);

		btnDateFrom = (Button) findViewById(R.id.btn_date_from);
		btnDateTo = (Button) findViewById(R.id.btn_date_to);
		btnTimeFrom = (Button) findViewById(R.id.btn_time_from);
		btnTimeTo = (Button) findViewById(R.id.btn_time_to);
		
		Calendar calendar = Calendar.getInstance();
		day = calendar.get(Calendar.DAY_OF_MONTH);
		month = calendar.get(Calendar.MONTH);
		year = calendar.get(Calendar.YEAR);
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		
		btnDateFrom.setText(new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year).append(" "));
		btnDateTo.setText(new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year).append(" "));
		btnTimeFrom.setText(new StringBuilder().append(pad(hour)).append(":").append(pad(minute)));
		btnTimeTo.setText(new StringBuilder().append(pad(hour)).append(":").append(pad(minute)));
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}

	public void onClick(View view) {
		Intent intent;
		try {
			switch (view.getId()) {
			case R.id.btn_create_event:
				Toast.makeText(getApplicationContext(),
						"Event created successfully.", Toast.LENGTH_SHORT)
						.show();
				intent = new Intent(CreateEventStep2Activity.this,
						ViewAllEventsActivity.class);
				startActivity(intent);
				this.finish();
				break;
			case R.id.btn_previous:
				onBackPressed();
				break;
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
		Intent intent = new Intent(CreateEventStep2Activity.this,
				CreateEventStep1Activity.class);
		startActivity(intent);
		this.finish();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerFromListener, year,
					month, day);

		case DATE_DIALOG_ID_1:
			return new DatePickerDialog(this, datePickerToListener, year,
					month, day);
		
		case TIME_DIALOG_ID:
			// set time picker as current time
			return new TimePickerDialog(this, 
                                        timePickerFromListener, hour, minute,false);
		case TIME_DIALOG_ID_1:
			// set time picker as current time
			return new TimePickerDialog(this, 
                                        timePickerToListener, hour, minute,false);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerFromListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// set selected date into textview
			btnDateFrom.setText(new StringBuilder().append(day).append("/")
					.append(month + 1).append("/").append(year).append(" "));
		}
	};
	private DatePickerDialog.OnDateSetListener datePickerToListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// set selected date into textview
			btnDateTo.setText(new StringBuilder().append(day).append("/")
					.append(month + 1).append("/").append(year).append(" "));
		}
	};
	 
	private TimePickerDialog.OnTimeSetListener timePickerFromListener = 
            new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;
 
			// set current time into textview
			btnTimeFrom.setText(new StringBuilder().append(pad(hour)).append(":").append(pad(minute)));
		}
	};
	
	private TimePickerDialog.OnTimeSetListener timePickerToListener = 
            new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;
 
			// set current time into textview
			btnTimeTo.setText(new StringBuilder().append(pad(hour)).append(":").append(pad(minute)));
		}
	};
 
	private static String pad(int c) {
		if (c >= 10)
		   return String.valueOf(c);
		else
		   return "0" + String.valueOf(c);
	}
}
