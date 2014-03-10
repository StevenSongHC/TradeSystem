package com.ts.entity;

import java.sql.Date;

@SuppressWarnings("serial")
public class Publisher implements java.io.Serializable {
	private int id;
	private int uid;
	private String name;
	private String summary;
	private String contact;
	private Date joinDate;
	private int goodCount;
	private boolean isActivate;
	private boolean isRestricted;
	private int isAdmin;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	public int getGoodCount() {
		return goodCount;
	}
	public void setGoodCount(int goodCount) {
		this.goodCount = goodCount;
	}
	public boolean getIsActivate() {
		return isActivate;
	}
	public void setIsActivate(boolean isActivate) {
		this.isActivate = isActivate;
	}
	public boolean getIsRestricted() {
		return isRestricted;
	}
	public void setIsRestricted(boolean isRestricted) {
		this.isRestricted = isRestricted;
	}
	public int getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
}
