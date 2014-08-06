package com.example.it3197_casestudy.model;

public class MashUpData {
	private String name;
	private String addressStreetName;
	private String addressBlkHouseNumber;
	private String addressBuildingName;
	private String addressFloorNumber;
	private String addressUnitNumber;
	private String addressPostalCode;
	
	private String description;
	private String hyperlink;
	//X & Y
	//Y
	private double lat;
	//X
	private double lng;
	private String iconName;
	private String photoUrl;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddressStreetName() {
		return addressStreetName;
	}
	
	public void setAddressStreetName(String addressStreetName) {
		this.addressStreetName = addressStreetName;
	}
	
	public String getAddressBlkHouseNumber() {
		return addressBlkHouseNumber;
	}
	
	public void setAddressBlkHouseNumber(String addressBlkHouseNumber) {
		this.addressBlkHouseNumber = addressBlkHouseNumber;
	}
	
	public String getAddressBuildingName() {
		return addressBuildingName;
	}
	
	public void setAddressBuildingName(String addressBuildingName) {
		this.addressBuildingName = addressBuildingName;
	}
	
	public String getAddressFloorNumber() {
		return addressFloorNumber;
	}
	
	public void setAddressFloorNumber(String addressFloorNumber) {
		this.addressFloorNumber = addressFloorNumber;
	}
	
	public String getAddressUnitNumber() {
		return addressUnitNumber;
	}
	
	public void setAddressUnitNumber(String addressUnitNumber) {
		this.addressUnitNumber = addressUnitNumber;
	}
	
	public String getAddressPostalCode() {
		return addressPostalCode;
	}
	
	public void setAddressPostalCode(String addressPostalCode) {
		this.addressPostalCode = addressPostalCode;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getHyperlink() {
		return hyperlink;
	}
	
	public void setHyperlink(String hyperlink) {
		this.hyperlink = hyperlink;
	}
	
	public double getLat() {
		return lat;
	}
	
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	public double getLng() {
		return lng;
	}
	
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public String getIconName() {
		return iconName;
	}
	
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
}
