package com.example.it3197_casestudy.model;

public class Riddle {
	private int riddleID;
	private String riddleTitle;
	private String riddleContent;
	private Riddle_Answered riddleAnsID;
	private int riddlePoints;
	
	public Riddle(){};
	
	public Riddle(int riddleID, String riddleTitle, String riddleContent, Riddle_Answered riddleAnsID, int riddlePoints){
		this.riddleID = riddleID;
		this.riddleTitle = riddleTitle;
		this.riddleContent = riddleContent;
		this.riddleAnsID = riddleAnsID;
		this.riddlePoints = riddlePoints;
	}

	public int getRiddleID() {
		return riddleID;
	}
	public void setRiddleID(int riddleID) {
		this.riddleID = riddleID;
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
	public Riddle_Answered getRiddleAnsID() {
		return riddleAnsID;
	}
	public void setRiddleAnsID(Riddle_Answered riddleAnsID) {
		this.riddleAnsID = riddleAnsID;
	}
	public int getRiddlePoints() {
		return riddlePoints;
	}
	public void setRiddlePoints(int riddlePoints) {
		this.riddlePoints = riddlePoints;
	}
}