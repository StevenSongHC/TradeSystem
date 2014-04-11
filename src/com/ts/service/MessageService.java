package com.ts.service;

import java.util.List;

import com.ts.entity.Message;

public interface MessageService {
	
	public boolean sendMessage(Message message);
	
	// send to the admin
	public boolean sendBecomePublisherApply(int uid);
	
	public boolean replyBecomePublisherApply(int uid, boolean isAgree);
	
	public boolean haveSentUnreadBecomePublisherApply(int uid);		// which means you have sent become-publisher apply
	
	public int getNotificationAmount(int uid);
	
	public boolean minusNotification(int uid, int noticeType);
	
	public List<Message> fetchUnreadMessageList(int uid);
	
	public List<Message> fetchReadMessageList(int uid);
	
	public boolean readMessage(int mid, int uid, int noticeType);
	
}
