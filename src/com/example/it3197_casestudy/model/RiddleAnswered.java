package com.example.it3197_casestudy.model;

public class RiddleAnswered {
	private int riddleAnsID;
	private Riddle riddleID;
	private User nric;
	private String riddleAns;
	private String riddleStatus;
	
	public RiddleAnswered(){};
	
	public RiddleAnswered(int riddleAnsID, Riddle riddleID, User nric, String riddleAns, String riddleStatus){
		this.riddleAnsID = riddleAnsID;
		this.riddleID = riddleID;
		this.nric = nric;
		this.riddleAns = riddleAns;
		this.riddleStatus = riddleStatus;
	}

	public int getRiddleAnsID() {
		return riddleAnsID;
	}
	public void setRiddleAnsID(int riddleAnsID) {
		this.riddleAnsID = riddleAnsID;
	}
	public Riddle getRiddleID() {
		return riddleID;
	}
	public void setRiddleID(Riddle riddleID) {
		this.riddleID = riddleID;
	}
	public User getNric() {
		return nric;
	}
	public void setNric(User nric) {
		this.nric = nric;
	}
	public String getRiddleAns() {
		return riddleAns;
	}
	public void setRiddleAns(String riddleAns) {
		this.riddleAns = riddleAns;
	}
	public String getRiddleStatus() {
		return riddleStatus;
	}
	public void setRiddleStatus(String riddleStatus) {
		this.riddleStatus = riddleStatus;
	};
}
