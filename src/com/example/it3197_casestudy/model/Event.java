package com.example.it3197_casestudy.model;

import java.util.Date;

public class Event{
	private int eventID;
	private String eventAdminNRIC;
	private String eventName;
	private String eventCategory;
	private String eventDescription;
	private Date eventDateTimeFrom;
	private Date eventDateTimeTo;
	private String occurence;
	private int noOfParticipantsAllowed;
	private int active;
	private String eventFBPostID;
	
	/**
	 * Event's default constructor
	 */
	public Event(){}

	/**
	 * This constructor is to retrieve events
	 * @param eventID
	 * @param eventAdminNRIC
	 * @param eventName
	 * @param eventCategory
	 * @param eventDescription
	 * @param eventDateTimeFrom
	 * @param eventDateTimeTo
	 * @param occurence
	 * @param noOfParticipantsAllowed
	 * @param active
	 */
	public Event(int eventID, String eventAdminNRIC, String eventName,String eventCategory, String eventDescription,Date eventDateTimeFrom, Date eventDateTimeTo, String occurence, int noOfParticipantsAllowed, int active) {
		// TODO Auto-generated constructor stub
		this.eventID = eventID;
		this.eventAdminNRIC = eventAdminNRIC;
		this.eventName = eventName;
		this.eventCategory = eventCategory;
		this.eventDescription = eventDescription;
		this.eventDateTimeFrom = eventDateTimeFrom;
		this.eventDateTimeTo = eventDateTimeTo;
		this.occurence = occurence;
		this.noOfParticipantsAllowed = noOfParticipantsAllowed;
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

	public Date getEventDateTimeFrom() {
		return eventDateTimeFrom;
	}

	public void setEventDateTimeFrom(Date eventDateTimeFrom) {
		this.eventDateTimeFrom = eventDateTimeFrom;
	}

	public Date getEventDateTimeTo() {
		return eventDateTimeTo;
	}

	public void setEventDateTimeTo(Date eventDateTimeTo) {
		this.eventDateTimeTo = eventDateTimeTo;
	}

	public String getOccurence() {
		return occurence;
	}

	public void setOccurence(String occurence) {
		this.occurence = occurence;
	}

	public int getNoOfParticipantsAllowed() {
		return noOfParticipantsAllowed;
	}

	public void setNoOfParticipantsAllowed(int noOfParticipantsAllowed) {
		this.noOfParticipantsAllowed = noOfParticipantsAllowed;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getEventFBPostID() {
		return eventFBPostID;
	}

	public void setEventFBPostID(String eventFBPostID) {
		this.eventFBPostID = eventFBPostID;
	}
}
