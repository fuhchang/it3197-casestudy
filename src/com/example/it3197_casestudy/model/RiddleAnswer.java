package com.example.it3197_casestudy.model;

public class RiddleAnswer {
	private int riddleAnswerID;
	private Riddle riddleObj;
	private User user;
	private String riddleAnswer;
	private String riddleAnswerStatus;
	
	public RiddleAnswer(){}
	
	public RiddleAnswer(int riddleAnswerID, Riddle riddleObj, User user, String riddleAnswer, String riddleAnswerStatus){
		this.riddleAnswerID = riddleAnswerID;
		this.riddleObj = riddleObj;
		this.user = user;
		this.riddleAnswer = riddleAnswer;
		this.riddleAnswerStatus = riddleAnswerStatus;
	}
	
	public int getRiddleAnswerID() {
		return riddleAnswerID;
	}
	public void setRiddleAnswerID(int riddleAnswerID) {
		this.riddleAnswerID = riddleAnswerID;
	}
	public Riddle getriddleObj() {
		return riddleObj;
	}
	public void setriddleObj(Riddle riddleObj) {
		this.riddleObj = riddleObj;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getRiddleAnswer() {
		return riddleAnswer;
	}
	public void setRiddleAnswer(String riddleAnswer) {
		this.riddleAnswer = riddleAnswer;
	}
	public String getRiddleAnswerStatus() {
		return riddleAnswerStatus;
	}
	public void setRiddleAnswerStatus(String riddleAnswerStatus) {
		this.riddleAnswerStatus = riddleAnswerStatus;
	}
	
	
}
