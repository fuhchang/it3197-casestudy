package com.example.it3197_casestudy.model;

import java.util.Date;

public class EventParticipants {
	private int eventID;
	private String userNRIC;
	private Date dateTimeJoined;
	private int checkIn;

	/**
	 * Event Participant's default constructor
	 */
	public EventParticipants(){}

	/**
	 * This constructor is to retrieve event participants
	 * @param eventID
	 * @param userNRIC
	 * @param dateTimeJoined
	 * @param checkIn
	 */
	public EventParticipants(int eventID, String userNRIC, Date dateTimeJoined,int checkIn) {
		super();
		this.eventID = eventID;
		this.userNRIC = userNRIC;
		this.dateTimeJoined = dateTimeJoined;
		this.checkIn = checkIn;
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public String getUserNRIC() {
		return userNRIC;
	}

	public void setUserNRIC(String userNRIC) {
		this.userNRIC = userNRIC;
	}

	public Date getDateTimeJoined() {
		return dateTimeJoined;
	}

	public void setDateTimeJoined(Date dateTimeJoined) {
		this.dateTimeJoined = dateTimeJoined;
	}

	public int getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(int checkIn) {
		this.checkIn = checkIn;
	}
}
