package com.example.it3197_casestudy.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Hobby implements Parcelable {
	private int groupID;
	private String groupName;
	private String category;
	private double Lat;
	private double Lng;
	private String description;
	private int active;
	private String adminNric;
	private byte[] GrpImg;

	public Hobby() {

	}

	public Hobby(int groupID, String groupName, String category, double Lat,
			double Lng, String desc, int active, String adminNric) {
		this.groupID = groupID;
		this.groupName = groupName;
		this.category = category;
		this.Lat = Lat;
		this.Lng = Lng;
		this.description = desc;
		this.active = active;
		this.adminNric = adminNric;
	}

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
	 * @param adminNric
	 *            the adminNric to set
	 */
	public void setAdminNric(String adminNric) {
		this.adminNric = adminNric;
	}

	// Parcelling part

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(this.groupID);
		dest.writeString(this.groupName);
		dest.writeString(this.category);
		dest.writeDouble(this.Lat);
		dest.writeDouble(this.Lng);
		dest.writeString(this.description);
		dest.writeInt(this.active);
		dest.writeString(this.adminNric);
	}

	public Hobby(Parcel in) {
		super();
		readFromParcel(in);
	}

	public static final Parcelable.Creator<Hobby> CREATOR = new Parcelable.Creator<Hobby>() {
		public Hobby createFromParcel(Parcel in) {
			return new Hobby(in);
		}

		public Hobby[] newArray(int size) {
			return new Hobby[size];
		}
	};
	
	public void readFromParcel(Parcel in){
		this.groupID = in.readInt();
		this.groupName = in.readString();
		this.category = in.readString();
		this.Lat = in.readDouble();
		this.Lng = in.readDouble();
		this.description = in.readString();
		this.active = in.readInt();
		this.adminNric = in.readString();
	}
}
