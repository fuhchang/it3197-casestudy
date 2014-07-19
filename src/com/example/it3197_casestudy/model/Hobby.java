package com.example.it3197_casestudy.model;

public class Hobby {
	private int groupID;
	private String groupName;
	private String category;
	private double Lat;
	private double Lng;
	private String description;
	private int active;
	private String adminNric;
	private byte[] GrpImg;

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}
	public byte[] getGrpImg() {
		return GrpImg;
	}

	public void setGrpImg(byte[] grpImg) {
		GrpImg = grpImg;
	}

	public double getLat() {
		return Lat;
	}

	public void setLat(double lat) {
		Lat = lat;
	}

	public double getLng() {
		return Lng;
	}

	public void setLng(double lng) {
		Lng = lng;
	}

	/**
	 * @return the adminNric
	 */
	public String getAdminNric() {
		return adminNric;
	}

	/**
	 * @param adminNric the adminNric to set
	 */
	public void setAdminNric(String adminNric) {
		this.adminNric = adminNric;
	}
	
}
