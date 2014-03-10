package com.ts.entity;

public class User {
	private int id;
	private String name;
	private String account;
	private String password;
	private String portrait;
	private String summary;
	private boolean isSuspend;
	private boolean isDelete;
	private boolean isRestricted;
	private boolean isPublisher;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public boolean getIsSuspend() {
		return isSuspend;
	}
	public void setIsSuspend(boolean isSuspend) {
		this.isSuspend = isSuspend;
	}
	public boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public boolean getIsRestricted() {
		return isRestricted;
	}
	public void setIsRestricted(boolean isRestricted) {
		this.isRestricted = isRestricted;
	}
	public boolean getIsPublisher() {
		return isPublisher;
	}
	public void setIsPublisher(boolean isPublisher) {
		this.isPublisher = isPublisher;
	}
}
