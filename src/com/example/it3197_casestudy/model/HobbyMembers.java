package com.example.it3197_casestudy.model;

import java.util.Date;

public class HobbyMembers {

	private int groupMemberID;
	private int groupID;
	private String userNric;
	private Date dateTimeJoined;
	private int active;
	private String MemberRole;
	public int getGroupMemberID() {
		return groupMemberID;
	}
	public void setGroupMemberID(int groupMemberID) {
		this.groupMemberID = groupMemberID;
	}
	public int getGroupID() {
		return groupID;
	}
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	public String getUserNric() {
		return userNric;
	}
	public void setUserNric(String userNric) {
		this.userNric = userNric;
	}
	public Date getDateTimeJoined() {
		return dateTimeJoined;
	}
	public void setDateTimeJoined(Date dateTimeJoined) {
		this.dateTimeJoined = dateTimeJoined;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getMemberRole() {
		return MemberRole;
	}
	public void setMemberRole(String memberRole) {
		MemberRole = memberRole;
	}
	
	
}
