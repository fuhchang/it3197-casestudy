package com.example.it3197_casestudy.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
	private String nric;
	private String name;
	private String type;
	private String password;
	private String contactNo;
	private String address;
	private String email;
	private int active;
	private int points;
	
	/**
	 * User's default constructor
	 */
	public User(){};
	
	/**
	 * This constructor is to retrieve users
	 * @param nric
	 * @param name
	 * @param type
	 * @param password
	 * @param contactNo
	 * @param address
	 * @param email
	 * @param active
	 * @param points
	 */
	public User(String nric, String name, String type, String password,String contactNo, String address, String email, int active, int points) {
		this.nric = nric;
		this.name = name;
		this.type = type;
		this.password = password;
		this.contactNo = contactNo;
		this.address = address;
		this.email = email;
		this.active = active;
		this.points = points;
	}
	public String getNric() {
		return nric;
	}
	public void setNric(String nric) {
		this.nric = nric;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(nric);
		dest.writeString(name);
		dest.writeString(type);
		dest.writeString(password);
		dest.writeString(contactNo);
		dest.writeString(address);
		dest.writeString(email);
		dest.writeInt(active);
		dest.writeInt(points);
	}
	
	private User(Parcel in) {
		nric = in.readString();
		name = in.readString();
		type = in.readString();
		password = in.readString();
		contactNo = in.readString();
		address = in.readString();
		email = in.readString();
		active = in.readInt();
		points = in.readInt();
	}
	
	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		@Override
		public User createFromParcel(Parcel source) {
			return new User(source);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
		
	};
}
