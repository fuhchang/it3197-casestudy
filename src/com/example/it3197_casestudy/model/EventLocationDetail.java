package com.example.it3197_casestudy.model;

public class EventLocationDetail {
	private int eventLocationID;
	private int eventID;
	private String eventLocationName;
	private String eventLocationAddress;
	private String eventLocationHyperLink;
	private double eventLocationLat;
	private double eventLocationLng;

	public EventLocationDetail() {
		// TODO Auto-generated constructor stub
	}
	
	public EventLocationDetail(int eventLocationID, int eventID, String eventLocationName, String eventLocationAddress, String eventLocationHyperLink, double eventLocationLat, double eventLocationLng) {
		this.eventLocationID = eventLocationID;
		this.eventID = eventID;
		this.eventLocationName = eventLocationName;
		this.eventLocationAddress = eventLocationAddress;
		this.eventLocationHyperLink = eventLocationHyperLink;
		this.eventLocationLat = eventLocationLat;
		this.eventLocationLng = eventLocationLng;
	}

	public int getEventLocationID() {
		return eventLocationID;
	}


	public void setEventLocationID(int eventLocationID) {
		this.eventLocationID = eventLocationID;
	}


	public int getEventID() {
		return eventID;
	}


	public void setEventID(int eventID) {
		this.eventID = eventID;
	}


	public String getEventLocationName() {
		return eventLocationName;
	}


	public void setEventLocationName(String eventLocationName) {
		this.eventLocationName = eventLocationName;
	}


	public String getEventLocationAddress() {
		return eventLocationAddress;
	}


	public void setEventLocationAddress(String eventLocationAddress) {
		this.eventLocationAddress = eventLocationAddress;
	}


	public String getEventLocationHyperLink() {
		return eventLocationHyperLink;
	}


	public void setEventLocationHyperLink(String eventLocationHyperLink) {
		this.eventLocationHyperLink = eventLocationHyperLink;
	}


	public double getEventLocationLat() {
		return eventLocationLat;
	}


	public void setEventLocationLat(double eventLocationLat) {
		this.eventLocationLat = eventLocationLat;
	}


	public double getEventLocationLng() {
		return eventLocationLng;
	}


	public void setEventLocationLng(double eventLocationLng) {
		this.eventLocationLng = eventLocationLng;
	}
}
