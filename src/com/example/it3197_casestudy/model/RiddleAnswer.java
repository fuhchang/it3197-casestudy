package com.example.it3197_casestudy.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RiddleAnswer implements Parcelable {
	private int riddleAnswerID;
	private Riddle riddle;
	private String riddleAnswer;
	private String riddleAnswerStatus;
	
	// Constructor
	public RiddleAnswer(){}
	
	public RiddleAnswer(int riddleAnswerID, Riddle riddle, String riddleAnswer, String riddleAnswerStatus){
		this.riddleAnswerID = riddleAnswerID;
		this.riddle = riddle;
		this.riddleAnswer = riddleAnswer;
		this.riddleAnswerStatus = riddleAnswerStatus;
	}
	
	public RiddleAnswer(int riddleAnswerID) {
		this.riddleAnswerID = riddleAnswerID;
	}
	
	// Getter and Setter
	public int getRiddleAnswerID() {
		return riddleAnswerID;
	}
	public void setRiddleAnswerID(int riddleAnswerID) {
		this.riddleAnswerID = riddleAnswerID;
	}
	public Riddle getRiddle() {
		return riddle;
	}
	public void setRiddle(Riddle riddle) {
		this.riddle = riddle;
	}
	public String getRiddleAnswer() {
		return riddleAnswer;
	}
	public void setRiddleAnswer(String riddleAnswer) {
		this.riddleAnswer = riddleAnswer;
	}
	public String getRiddleAnswerStatus() {
		return riddleAnswerStatus;
	}
	public void setRiddleAnswerStatus(String riddleAnswerStatus) {
		this.riddleAnswerStatus = riddleAnswerStatus;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(riddleAnswerID);
		dest.writeParcelable(this.riddle, flags);
		dest.writeString(riddleAnswer);
		dest.writeString(riddleAnswerStatus);
	}
	
	private RiddleAnswer(Parcel in) {
		riddleAnswerID = in.readInt();
		riddle = in.readParcelable(Riddle.class.getClassLoader());
		riddleAnswer = in.readString();
		riddleAnswerStatus = in.readString();
	}
	
	public static final Parcelable.Creator<RiddleAnswer> CREATOR = new Parcelable.Creator<RiddleAnswer>() {
		@Override
		public RiddleAnswer createFromParcel(Parcel source) {
			return new RiddleAnswer(source);
		}

		@Override
		public RiddleAnswer[] newArray(int size) {
			return new RiddleAnswer[size];
		}
		
	};
}
