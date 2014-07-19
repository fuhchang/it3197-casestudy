package com.example.it3197_casestudy.model;

import java.util.Date;

public class HobbyPost {
	private int postID;
	private Date datetime;
	private String content;
	private Double Lat;
	private Double Lng;
	private int grpID;
	private String posterNric;
	private String postTitle;
	public int getPostID() {
		return postID;
	}

	public void setPostID(int postID) {
		this.postID = postID;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Double getLat() {
		return Lat;
	}

	public void setLat(Double lat) {
		Lat = lat;
	}

	public Double getLng() {
		return Lng;
	}

	public void setLng(Double lng) {
		Lng = lng;
	}


	public int getGrpID() {
		return grpID;
	}


	public void setGrpID(int grpID) {
		this.grpID = grpID;
	}

	/**
	 * @return the posterNric
	 */
	public String getPosterNric() {
		return posterNric;
	}

	/**
	 * @param posterNric the posterNric to set
	 */
	public void setPosterNric(String posterNric) {
		this.posterNric = posterNric;
	}

	/**
	 * @return the postTitle
	 */
	public String getPostTitle() {
		return postTitle;
	}

	/**
	 * @param postTitle the postTitle to set
	 */
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

}
