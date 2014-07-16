package com.example.it3197_casestudy.model;

public class Riddle {
	private int riddleID;
	private User user;
	private String riddleTitle;
	private String riddleContent;
	private String riddleStatus;
	private int riddlePoint;
	
	public Riddle(){}
	
	public Riddle(int riddleID, User user, String riddleTitle, String riddleContent, String riddleStatus, int riddlePoint){
		this.riddleID = riddleID;
		this.user = user;
		this.riddleTitle = riddleTitle;
		this.riddleContent = riddleContent;
		this.riddleStatus = riddleStatus;
		this.riddlePoint = riddlePoint;
	}
	
	//For testing purposes
	public Riddle(int riddleID, String riddleTitle, String riddleContent){
		this.riddleID = riddleID;
		this.riddleTitle = riddleTitle;
		this.riddleContent = riddleContent;
	}
	
	public int getRiddleID() {
		return riddleID;
	}
	public void setRiddleID(int riddleID) {
		this.riddleID = riddleID;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getRiddleTitle() {
		return riddleTitle;
	}
	public void setRiddleTitle(String riddleTitle) {
		this.riddleTitle = riddleTitle;
	}
	public String getRiddleContent() {
		return riddleContent;
	}
	public void setRiddleContent(String riddleContent) {
		this.riddleContent = riddleContent;
	}
	public String getRiddleStatus() {
		return riddleStatus;
	}
	public void setRiddleStatus(String riddleStatus) {
		this.riddleStatus = riddleStatus;
	}
	public int getRiddlePoint() {
		return riddlePoint;
	}
	public void setRiddlePoint(int riddlePoint) {
		this.riddlePoint = riddlePoint;
	}
}