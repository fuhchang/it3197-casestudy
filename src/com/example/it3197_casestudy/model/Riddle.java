package com.example.it3197_casestudy.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Riddle implements Parcelable {
	private int riddleID;
	private User user;
	private String riddleTitle;
	private String riddleContent;
	private String riddleStatus;
	private int riddlePoint;
	
	// Constructor
	public Riddle(){}
	
	public Riddle(int riddleID, User user, String riddleTitle, String riddleContent, String riddleStatus, int riddlePoint){
		this.riddleID = riddleID;
		this.user = user;
		this.riddleTitle = riddleTitle;
		this.riddleContent = riddleContent;
		this.riddleStatus = riddleStatus;
		this.riddlePoint = riddlePoint;
	}
	
	public Riddle(int riddleID){
		this.riddleID = riddleID;
	}
	
	// Getter and Setter
	public int getRiddleID() {
		return riddleID;
	}
	public void setRiddleID(int riddleID) {
		this.riddleID = riddleID;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getRiddleTitle() {
		return riddleTitle;
	}
	public void setRiddleTitle(String riddleTitle) {
		this.riddleTitle = riddleTitle;
	}
	public String getRiddleContent() {
		return riddleContent;
	}
	public void setRiddleContent(String riddleContent) {
		this.riddleContent = riddleContent;
	}
	public String getRiddleStatus() {
		return riddleStatus;
	}
	public void setRiddleStatus(String riddleStatus) {
		this.riddleStatus = riddleStatus;
	}
	public int getRiddlePoint() {
		return riddlePoint;
	}
	public void setRiddlePoint(int riddlePoint) {
		this.riddlePoint = riddlePoint;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(riddleID);
		dest.writeParcelable(this.user, flags);
		dest.writeString(riddleTitle);
		dest.writeString(riddleContent);
		dest.writeString(riddleStatus);
		dest.writeInt(riddlePoint);
	}
	
	private Riddle(Parcel in) {
		riddleID = in.readInt();
		user = in.readParcelable(User.class.getClassLoader());
		riddleTitle = in.readString();
		riddleContent = in.readString();
		riddleStatus = in.readString();
		riddlePoint = in.readInt();
	}
	
	public static final Parcelable.Creator<Riddle> CREATOR = new Parcelable.Creator<Riddle>() {
		@Override
		public Riddle createFromParcel(Parcel source) {
			return new Riddle(source);
		}

		@Override
		public Riddle[] newArray(int size) {
			return new Riddle[size];
		}
	};
}