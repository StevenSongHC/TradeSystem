package com.ts.entity;

import java.sql.Timestamp;

public class Message {
	public int id;
	public int senderUid;
	public int receiverUid;
	public String word;
	private Timestamp time;
	/**
	 * 0 = u2u message
	 * 1 = get restricted notice
	 * 2 = get a new order notice
	 * 3 = order canceled notice
	 * 4 = good passed notice
	 * 5 = good denied notice
	 * 6 = good suspended notice
	 * 7 = good deleted notice
	 * 8 = become publisher apply sent notice
	 * 9 = someone else try to change admin
	 */
	public int noticeType;
	private boolean isRead;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSenderUid() {
		return senderUid;
	}
	public void setSenderUid(int senderUid) {
		this.senderUid = senderUid;
	}
	public int getReceiverUid() {
		return receiverUid;
	}
	public void setReceiverUid(int receiverUid) {
		this.receiverUid = receiverUid;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public int getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(int noticeType) {
		this.noticeType = noticeType;
	}
	public boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(boolean isRead) {
		this.isRead = isRead;
	}
	
}
