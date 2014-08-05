package com.example.it3197_casestudy.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RiddleUserAnswered implements Parcelable {
	private int riddleUserAnsweredID;
	private Riddle riddle;
	private RiddleAnswer riddleAnswer;
	private User user;
	private String answeredRate;
	
	//Constructor
	public RiddleUserAnswered(){}
	
	public RiddleUserAnswered(int riddleUserAnsweredID, Riddle riddle, RiddleAnswer riddleAnswer, User user, String answeredRate){
		this.riddleUserAnsweredID = riddleUserAnsweredID;
		this.riddle = riddle;
		this.riddleAnswer = riddleAnswer;
		this.user = user;
		this.answeredRate = answeredRate;
	}

	//Getter and Setter
	public int getRiddleUserAnsweredID() {
		return riddleUserAnsweredID;
	}
	public void setRiddleUserAnsweredID(int riddleUserAnsweredID) {
		this.riddleUserAnsweredID = riddleUserAnsweredID;
	}
	public Riddle getRiddle() {
		return riddle;
	}
	public void setRiddle(Riddle riddle) {
		this.riddle = riddle;
	}
	public RiddleAnswer getRiddleAnswer() {
		return riddleAnswer;
	}
	public void setRiddleAnswer(RiddleAnswer riddleAnswer) {
		this.riddleAnswer = riddleAnswer;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getAnsweredRate() {
		return answeredRate;
	}
	public void setAnsweredRate(String answeredRate) {
		this.answeredRate = answeredRate;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(riddleUserAnsweredID);
		dest.writeParcelable(this.riddle, flags);
		dest.writeParcelable(this.riddleAnswer, flags);
		dest.writeParcelable(this.user, flags);
		dest.writeString(answeredRate);
	}
	
	private RiddleUserAnswered(Parcel in) {
		riddleUserAnsweredID = in.readInt();
		riddle = in.readParcelable(Riddle.class.getClassLoader());
		riddleAnswer = in.readParcelable(RiddleAnswer.class.getClassLoader());
		user = in.readParcelable(User.class.getClassLoader());
		answeredRate = in.readString();
	}
	
	public static final Parcelable.Creator<RiddleUserAnswered> CREATOR = new Parcelable.Creator<RiddleUserAnswered>() {
		@Override
		public RiddleUserAnswered createFromParcel(Parcel source) {
			return new RiddleUserAnswered(source);
		}

		@Override
		public RiddleUserAnswered[] newArray(int size) {
			return new RiddleUserAnswered[size];
		}
	};
}
