package com.example.it3197_casestudy.model;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class HobbyPost implements Parcelable {
	private int postID;
	private Date datetime;
	private String content;
	private Double Lat;
	private Double Lng;
	private int grpID;
	private String posterNric;
	private String postTitle;
	
	public HobbyPost(int postID, Date datetime, String content, double Lat, double Lng, String nric, String postTitle ){
		this.postID = postID;
		this.content = content;
		this.Lat = Lat;
		this.Lng = Lng;
		this.posterNric = nric;
	}
	public HobbyPost(){
		
	}
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
	
	public static final Parcelable.Creator<HobbyPost> CREATOR = new Parcelable.Creator<HobbyPost>(){

		@Override
		public HobbyPost createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new HobbyPost(source);
		}

		@Override
		public HobbyPost[] newArray(int size) {
			// TODO Auto-generated method stub
			return new HobbyPost[size];
		}
		
	};
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(postID);
		dest.writeInt(grpID);
		dest.writeString(postTitle);
		dest.writeString(posterNric);
		dest.writeString(content);
		dest.writeDouble(Lat);
		dest.writeDouble(Lng);
	}
	private HobbyPost(Parcel source) {
		postID = source.readInt();
		grpID = source.readInt();
		postTitle = source.readString();
		Lat = source.readDouble();
		Lng = source.readDouble();
		posterNric = source.readString();
		content = source.readString();
	}
}
