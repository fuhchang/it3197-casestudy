package com.example.it3197_casestudy.model;

import java.util.Date;

public class Combined {
	
	
	private String title;
	private Date dateTime;
	private String desc;

	private String articleTitle;
	private String articleContent;
	private Date articleDateTime;
	
	
	private String eventTitle;
	private Date eventFromTo;
	
	private String hobbyName;
	private String hobbyDescription;
	
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public String getArticleContent() {
		return articleContent;
	}
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
	public Date getArticleDateTime() {
		return articleDateTime;
	}
	public void setArticleDateTime(Date articleDateTime) {
		this.articleDateTime = articleDateTime;
	}
	public String getEventTitle() {
		return eventTitle;
	}
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}
	public Date getEventFromTo() {
		return eventFromTo;
	}
	public void setEventFromTo(Date eventFromTo) {
		this.eventFromTo = eventFromTo;
	}
	public String getHobbyName() {
		return hobbyName;
	}
	public void setHobbyName(String hobbyName) {
		this.hobbyName = hobbyName;
	}
	public String getHobbyDescription() {
		return hobbyDescription;
	}
	public void setHobbyDescription(String hobbyDescription) {
		this.hobbyDescription = hobbyDescription;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}	
}
