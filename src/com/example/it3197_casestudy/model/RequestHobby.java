package com.example.it3197_casestudy.model;

import java.util.Date;

public class RequestHobby {
	
	private int requestID;
	private int eventID;
	private int hobbyID;
	private String requestStatus;
	private Date requestDateStart;
	private Date requestDateEnd;
	private String groupname;
	public int getRequestID() {
		return requestID;
	}
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}
	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	public int getHobbyID() {
		return hobbyID;
	}
	public void setHobbyID(int hobbyID) {
		this.hobbyID = hobbyID;
	}
	public String getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
	public Date getRequestDateStart() {
		return requestDateStart;
	}
	public void setRequestDateStart(Date requestDateStart) {
		this.requestDateStart = requestDateStart;
	}
	public Date getRequestDateEnd() {
		return requestDateEnd;
	}
	public void setRequestDateEnd(Date requestDateEnd) {
		this.requestDateEnd = requestDateEnd;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	
	
}
