package com.ts.dao;

import java.util.List;

import com.ts.entity.Message;

public interface MessageDAO {
	
	public int getNotificationAmount(int uid);
	
	public String[] getNotification(int uid);
	
	public boolean minusNotification(int uid, int noticeType);
	
	public boolean plusNotification(int uid, int noticeType);
	
	public List<Message> getUnreadMessageList(int uid);
	
	public List<Message> getReadMessageList(int uid);
	
	// To Admin
	public boolean sendMessage(Message msg);
	
	// messages' status
	public boolean haveSentUnreadMessage(int uid, int noticeType);	// as a sender
	
	/**
	 * 
	 * @param mid
	 * @param receiverUid
	 * @param senderUid
	 * @param noticeType
	 * @return isSuccess
	 */
	public boolean setMessageRead(int mid, int receiverUid, int senderUid, int noticeType);
	
}
