package com.example.it3197_casestudy.model;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class UserLocation implements Parcelable {
	private int locationID;
	private User user;
	private Date dateTime;
	private double lat;
	private double lng;
	
	// Constructor
	public UserLocation() {}
	
	public UserLocation(int locationID, User user, Date dateTime, double lat, double lng) {
		this.locationID = locationID;
		this.user = user;
		this.dateTime = dateTime;
		this.lat = lat;
		this.lng = lng;
	}
	
	// Getter and Setter
	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
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
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(locationID);
		dest.writeParcelable(this.user,  flags);
		dest.writeLong(dateTime.getTime());
		dest.writeDouble(lat);
		dest.writeDouble(lng);
	}
	
	private UserLocation(Parcel in) {
		locationID = in.readInt();
		user = in.readParcelable(User.class.getClassLoader());
		dateTime = new Date(in.readLong());
		lat = in.readDouble();
		lng = in.readDouble();
	}
	
	public static final Parcelable.Creator<UserLocation> CREATOR = new Parcelable.Creator<UserLocation>() {
		@Override
		public UserLocation createFromParcel(Parcel source) {
			return new UserLocation(source);
		}

		@Override
		public UserLocation[] newArray(int size) {
			return new UserLocation[size];
		}
	};
}
