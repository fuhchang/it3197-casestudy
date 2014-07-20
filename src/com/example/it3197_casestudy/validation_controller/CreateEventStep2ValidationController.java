package com.example.it3197_casestudy.validation_controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.example.it3197_casestudy.controller.CreateEvent;
import com.example.it3197_casestudy.crouton.Crouton;
import com.example.it3197_casestudy.crouton.Style;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.ui_logic.CreateEventStep1Activity;
import com.example.it3197_casestudy.ui_logic.CreateEventStep2Activity;
import com.example.it3197_casestudy.ui_logic.ViewAllEventsActivity;
import com.example.it3197_casestudy.util.Settings;
import com.example.it3197_casestudy.util.TimeIgnoringComparator;
import com.example.it3197_casestudy.validation.Form;
import com.example.it3197_casestudy.validation.Validate;
import com.example.it3197_casestudy.validation.validator.NotEmptyValidator;

public class CreateEventStep2ValidationController implements Settings{
	private CreateEventStep2Activity activity;
	private String typeOfEvent;
	
	public CreateEventStep2ValidationController(CreateEventStep2Activity activity, String typeOfEvent){
		this.activity = activity;
		this.typeOfEvent = typeOfEvent;
	}
	
	public void validateForm(Intent intent, Calendar calendarFrom, Calendar calendarTo, Event event, String occurence){
		Calendar calendarCurrentDate = Calendar.getInstance();
		TimeIgnoringComparator t = new TimeIgnoringComparator();
		int differenceInDays = (int) Math.floor((calendarFrom.getTimeInMillis()-calendarTo.getTimeInMillis())/-86400000);
		int days = -86400000 * differenceInDays;
		double compare = (calendarFrom.getTimeInMillis()-calendarTo.getTimeInMillis()) - days;
		System.out.println(compare);
		event.setEventType(typeOfEvent);
		if((calendarFrom.before(calendarCurrentDate) || (calendarTo.before(calendarCurrentDate) || (t.compare(calendarFrom, calendarCurrentDate) == 0 ) || (t.compare(calendarTo, calendarCurrentDate) == 0 )))){
			Crouton crouton = Crouton.makeText(activity,"Please choose another date after today.",Style.ALERT);
			crouton.show();
			return;
		}
		if(compare > -1800000){
			Crouton crouton = Crouton.makeText(activity,"Please select a ending time 30 min before the starting time.",Style.ALERT);
			crouton.show();
			return;
		}
		if(typeOfEvent.equals("Big Event")){
			if((calendarFrom.after(calendarTo)) || (t.compare(calendarFrom, calendarTo) >= 0) || ((t.compare(calendarFrom, calendarCurrentDate) == 0))){
				Crouton crouton = Crouton.makeText(activity,"Please choose another date after the starting date.",Style.ALERT);
				crouton.show();
				return;
			}
			else{
				event.setEventDateTimeFrom(calendarFrom.getTime());
				event.setEventDateTimeTo(calendarTo.getTime());
				event.setOccurence(occurence);
				System.out.println("Event Name: " + event.getEventName());
				System.out.println("Event Category: " + event.getEventCategory());
				System.out.println("Event Description: " + event.getEventDescription());
				System.out.println("Event Location: " + event.getEventLocation());
				System.out.println("No of participants: " + event.getNoOfParticipantsAllowed());
				CreateEvent createEvent = new CreateEvent(activity,event);
				createEvent.execute();
			}
		}
		else{
			event.setEventDateTimeFrom(calendarFrom.getTime());
			event.setEventDateTimeTo(calendarTo.getTime());
			event.setOccurence(occurence);
			CreateEvent createEvent = new CreateEvent(activity,event);
			createEvent.execute();
		}
	}
}
