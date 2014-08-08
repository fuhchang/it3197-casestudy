package com.example.it3197_casestudy.model;

import java.util.Date;

public class FBComments {
	private String fbPostID;
	private String name;
	private String comment;
	private Date dateTimeMade;
	
	public FBComments(){
		
	}
	
	public FBComments(String fbPostID, String name, String comment,
			Date dateTimeMade) {
		// TODO Auto-generated constructor stub
		this.fbPostID = fbPostID;
		this.name = name;
		this.comment = comment;
		this.dateTimeMade = dateTimeMade;
	}
	public String getFBPostID() {
		return fbPostID;
	}
	public void setFBPostID(String fbPostID) {
		this.fbPostID = fbPostID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getDateTimeMade() {
		return dateTimeMade;
	}
	public void setDateTimeMade(Date dateTimeMade) {
		this.dateTimeMade = dateTimeMade;
	}
}
