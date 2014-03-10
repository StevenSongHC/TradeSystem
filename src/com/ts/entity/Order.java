package com.ts.entity;

import java.sql.Timestamp;

public class Order {
	private int id;
	private int goodId;
	private int sellerId;
	private int buyerId;
	private Timestamp addTime;
	private Timestamp finishTime;
	private boolean isDone;
	private boolean isCancel;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGoodId() {
		return goodId;
	}
	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public int getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	public Timestamp getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
	}
	public boolean getIsDone() {
		return isDone;
	}
	public void setIsDone(boolean isDone) {
		this.isDone = isDone;
	}
	public boolean getIsCancel() {
		return isCancel;
	}
	public void setIsCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}
}
