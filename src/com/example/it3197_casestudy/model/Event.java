package com.example.it3197_casestudy.model;

import java.util.Date;

import android.content.SharedPreferences;
import android.text.format.Time;

public class Event{
	private int eventID;
	private String eventAdminNRIC;
	private String eventName;
	private String eventCategory;
	private String eventDescription;
	private boolean isEventTypeSmall;
	private Date eventDate;
	private Time eventTime;
	private String eventLocation;
	private int noOfParticipantsAllowed;
	private int noOfVolunteersAllowed;
	private int active;
	
	public Event(){
		
	}
	
	public Event(int eventID, String eventAdminNRIC, String eventName,String eventCategory, String eventDescription,boolean isEventTypeSmall, Date eventDate, Time eventTime,String eventLocation, int noOfParticipantsAllowed,int noOfVolunteersAllowed, int active) {
		this.eventID = eventID;
		this.eventAdminNRIC = eventAdminNRIC;
		this.eventName = eventName;
		this.eventCategory = eventCategory;
		this.eventDescription = eventDescription;
		this.isEventTypeSmall = isEventTypeSmall;
		this.eventDate = eventDate;
		this.eventTime = eventTime;
		this.eventLocation = eventLocation;
		this.noOfParticipantsAllowed = noOfParticipantsAllowed;
		this.noOfVolunteersAllowed = noOfVolunteersAllowed;
		this.active = active;
	}
	
	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	public String getEventAdminNRIC() {
		return eventAdminNRIC;
	}
	public void setEventAdminNRIC(String eventAdminNRIC) {
		this.eventAdminNRIC = eventAdminNRIC;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventCategory() {
		return eventCategory;
	}
	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public boolean getIsEventTypeSmall() {
		return isEventTypeSmall;
	}
	public void setIsEventTypeSmall(boolean isEventTypeSmall) {
		this.isEventTypeSmall = isEventTypeSmall;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public Time getEventTime() {
		return eventTime;
	}
	public void setEventTime(Time eventTime) {
		this.eventTime = eventTime;
	}
	public String getEventLocation() {
		return eventLocation;
	}
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}
	public int getNoOfParticipantsAllowed() {
		return noOfParticipantsAllowed;
	}
	public void setNoOfParticipantsAllowed(int noOfParticipantsAllowed) {
		this.noOfParticipantsAllowed = noOfParticipantsAllowed;
	}
	public int getNoOfVolunteersAllowed() {
		return noOfVolunteersAllowed;
	}
	public void setNoOfVolunteersAllowed(int noOfVolunteersAllowed) {
		this.noOfVolunteersAllowed = noOfVolunteersAllowed;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
}
