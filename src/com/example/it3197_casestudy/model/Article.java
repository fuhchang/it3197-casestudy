package com.example.it3197_casestudy.model;

import java.util.Date;

public class Article{
	private int articleID;
	private String title;
	private String content;
	private Date dateTime;
	private String category;
	private String location;
	private String userNRIC;
	private int active;
	private String approved;
	private double dbLat;
	private double dbLon;
	private String articleUser;
	private String articleDate;
	
	private String dist;
	
	private double distToSort;
	
	
	/**
	 * Article's default constructor
	 */
	public Article(){}

	/**
	 * This constructor is to retrieve article
	 * @param articleID
	 * @param title
	 * @param content
	 * @param dateTime
	 * @param category
	 * @param location
	 * @param userNRIC
	 * @param active
	 * @param approved
	 */
	public Article(int articleID, String title, String content, Date dateTime,String category, String location, String userNRIC, int active,String approved, double dbLat, double dbLon) {
		super();
		this.articleID = articleID;
		this.title = title;
		this.content = content;
		this.dateTime = dateTime;
		this.category = category;
		this.location = location;
		this.userNRIC = userNRIC;
		this.active = active;
		this.approved = approved;
		this.dbLat=dbLat;
		this.dbLon=dbLon;
	}

	public int getArticleID() {
		return articleID;
	}

	public void setArticleID(int articleID) {
		this.articleID = articleID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUserNRIC() {
		return userNRIC;
	}

	public void setUserNRIC(String userNRIC) {
		this.userNRIC = userNRIC;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public double getDbLat() {
		return dbLat;
	}

	public void setDbLat(double dbLat) {
		this.dbLat = dbLat;
	}

	public double getDbLon() {
		return dbLon;
	}

	public void setDbLon(double dbLon) {
		this.dbLon = dbLon;
	}

	public String getArticleDate() {
		return articleDate;
	}

	public void setArticleDate(String articleDate) {
		this.articleDate = articleDate;
	}

	public String getArticleUser() {
		return articleUser;
	}

	public void setArticleUser(String articleUser) {
		this.articleUser = articleUser;
	}

	public String getDist() {
		return dist;
	}

	public void setDist(String dist) {
		this.dist = dist;
	}

	public double getDistToSort() {
		return distToSort;
	}

	public void setDistToSort(double distToSort) {
		this.distToSort = distToSort;
	}

}
