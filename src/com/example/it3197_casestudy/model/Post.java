package com.example.it3197_casestudy.model;

import java.util.Date;

public class Post {

	private int postID;
	private Date dateTime;
	private String content;
	private String location;
	private int groupID;
	public int getPostID() {
		return postID;
	}
	public void setPostID(int postID) {
		this.postID = postID;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getGroupID() {
		return groupID;
	}
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	
	
	
}
