package com.ts.service.impl;

import java.util.List;

import com.ts.dao.MessageDAO;
import com.ts.dao.PublisherDAO;
import com.ts.entity.Message;
import com.ts.service.MessageService;

public class MessageServiceImpl implements MessageService {
	private MessageDAO messageDao;
	private PublisherDAO publisherDao;
	public MessageDAO getMessageDao() {
		return messageDao;
	}
	public void setMessageDao(MessageDAO messageDao) {
		this.messageDao = messageDao;
	}
	public PublisherDAO getPublisherDao() {
		return publisherDao;
	}
	public void setPublisherDao(PublisherDAO publisherDao) {
		this.publisherDao = publisherDao;
	}
	
	/**
	 * noticeType:
	 * 0 = u2u message
	 * 1 = get a new order notice
	 * 2 = order canceled notice
	 * 3 = good passed notice
	 * 4 = good denied notice
	 * 5 = good suspended notice
	 * 6 = good deleted notice
	 * 7 = become publisher apply sent notice
	 * 8 = someone else try to change admin
	 */
	
	public boolean sendBecomePublisherApply(int uid) {
		Message msg = new Message();
		msg.setSenderUid(uid);
		msg.setReceiverUid(publisherDao.getAdmin().getUid());
		msg.setWord("try to be a publisher");
		msg.setNoticeType(7);
		return messageDao.sendMessage(msg);
	}
	
	public boolean replyBecomePublisherApply(int uid, boolean isAgree) {
		// set message read
		if (messageDao.setMessageRead(0, 0, uid, 7)) {
			// update admin's notification
			int adminUid = publisherDao.getAdmin().getUid();
			if (messageDao.minusNotification(adminUid, 7)) {
				// send sender a message about the result of become-publisher apply
				Message msg = new Message();
				if (isAgree) {
					msg.setSenderUid(adminUid);
					msg.setReceiverUid(uid);
					msg.setWord("恭喜你!管理员已经通过了您的成为发布者申请");
					msg.setNoticeType(0);
				}
				else {
					msg.setSenderUid(adminUid);
					msg.setReceiverUid(uid);
					msg.setWord("很遗憾,管理员不同意你成为发布者，再接再厉!");
					msg.setNoticeType(0);
				}
				
				if (messageDao.sendMessage(msg))
					return true;
				else
					return false;
			}
			else
				return false;
		}
		else
			return false;
	}
	
	public boolean haveSentUnreadBecomePublisherApply(int uid) {
		return messageDao.haveSentUnreadMessage(uid, 7);
	}
	
	public int getNotificationAmount(int uid) {
		return messageDao.getNotificationAmount(uid);
	}
	
	public boolean minusNotification(int uid, int noticeType) {
		return messageDao.minusNotification(uid, noticeType);
	}
	
	public List<Message> fetchUnreadMessageList(int uid) {
		return messageDao.getUnreadMessageList(uid);
	}
	
	public List<Message> fetchReadMessageList(int uid) {
		return messageDao.getReadMessageList(uid);
	}
	
	public boolean readMessage(int mid, int uid, int noticeType) {
		if (messageDao.setMessageRead(mid, 0, 0, 0)) {
			if (messageDao.minusNotification(uid, noticeType))
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
}
