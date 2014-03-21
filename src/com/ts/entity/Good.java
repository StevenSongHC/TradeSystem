package com.ts.entity;

import java.sql.Timestamp;

public class Good {
	private int id;
	private int publisherId;
	private String title;
	private String pic;
	private double price;
	private String desc;
	private int buyerCount;
	private Timestamp addTime;
	private boolean isComplete;
	private boolean isAgree;
	private boolean isAvailable;
	private boolean isDelete;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getBuyerCount() {
		return buyerCount;
	}
	public void setBuyerCount(int buyerCount) {
		this.buyerCount = buyerCount;
	}
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	public boolean getIsComplete() {
		return isComplete;
	}
	public void setIsComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}
	public boolean getIsAgree() {
		return isAgree;
	}
	public void setIsAgree(boolean isAgree) {
		this.isAgree = isAgree;
	}
	public boolean getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	public boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
}
