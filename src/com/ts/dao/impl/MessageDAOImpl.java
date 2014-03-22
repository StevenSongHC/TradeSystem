package com.ts.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.ts.dao.MessageDAO;
import com.ts.entity.Message;

public class MessageDAOImpl implements MessageDAO {
	private final String[] COLUMN = {"new_message",
							  		 "order_give",
							  		 "order_cancel",
							  		 "good_suspended",
							  		 "good_deleted",
							  		 "new_publisher_apply"};
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public int getNotificationAmount(int uid) {
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery("SELECT new_message+order_give+order_cancel+" +
											 "good_suspended+good_deleted+" +
											 "new_publisher_apply AS amount " + 
											 "FROM notification WHERE uid=" + uid)
										.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Object result = query.uniqueResult();
		if (result != null) {
			Map<?, ?> map = (Map<?, ?>) result;
			return Integer.parseInt(map.get("amount").toString());
		}
		return 0;
	}
	
	public String[] getNotification(int uid) {
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery("SELECT new_message AS '0',order_give AS '1',order_cancel AS '2'," + 
											 "good_suspended AS '3',good_deleted AS '4',new_publisher_apply AS '5' " + 
											 "FROM notification WHERE uid=" + uid + 
											 " AND (new_message>0 OR order_give>0 OR order_cancel>0 OR " +
											 "good_suspended>0 OR good_deleted>0 OR new_publisher_apply>0)")
										.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		// got notification
		Object result = query.uniqueResult();
		if (result != null) {
			Map<?, ?> map = (Map<?, ?>) result;
			System.out.println("apply"+map.get("7").toString());
		}
		
		return null;
	}
	
	public boolean minusNotification(int uid, int noticeType) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			// update notification
			session.createSQLQuery("UPDATE notification SET " + COLUMN[noticeType] + "=" + 
									COLUMN[noticeType] +"-1 WHERE uid=" + uid).executeUpdate();
			session.flush();
			
			tx.commit();
		} catch(RuntimeException e) {
			if (tx != null) {
				System.out.println("Failed to update notification " + COLUMN[noticeType] + "->" + uid);
				tx.rollback();
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public boolean plusNotification(int uid, int noticeType) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			// update notification
			session.createSQLQuery("UPDATE notification SET " + COLUMN[noticeType] + "=" + 
									COLUMN[noticeType] +"+1 WHERE uid=" + uid).executeUpdate();
			session.flush();
			
			tx.commit();
		} catch(RuntimeException e) {
			if (tx != null) {
				System.out.println("Failed to update notification " + COLUMN[noticeType] + "->" + uid);
				tx.rollback();
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> getUnreadMessageList(int uid) {
		Session session = sessionFactory.openSession();
		Query query = null;
		query = session.createQuery("from Message msg where msg.receiverUid=" + uid + " and msg.isRead=false order by msg.id desc");
		return (List<Message>) query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> getReadMessageList(int uid) {
		Session session = sessionFactory.openSession();
		Query query = null;
		query = session.createQuery("from Message msg where msg.receiverUid=" + uid + " and msg.isRead=true order by msg.id desc");
		return (List<Message>) query.list();
	}
	
	public boolean sendMessage(Message msg) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			// save message
			session.save(msg);
			// update notification
			if (msg.getNoticeType() <= 8) {
				session.createSQLQuery("UPDATE notification SET " + COLUMN[msg.getNoticeType()] + "=" + 
										COLUMN[msg.getNoticeType()] +"+1 WHERE uid=" + msg.getReceiverUid()).executeUpdate();
			}
			session.flush();
			
			tx.commit();
		} catch(RuntimeException e) {
			if (tx != null) {
				System.out.println("Failed to send the message(" + msg.getNoticeType() + ")");
				tx.rollback();
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public boolean haveSentUnreadMessage(int uid, int noticeType) {
		Session session = sessionFactory.openSession();
		Query query = null;
		query = session.createSQLQuery("SELECT * FROM message WHERE sender_uid=" + uid + "&&notice_type=" + noticeType + "&&isRead=0");
		if (query.uniqueResult() != null)
			return true;
		else
			return false;
	}
	
	public boolean setMessageRead(int mid,int receiverUid, int senderUid, int noticeType) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			if (mid != 0)
				session.createSQLQuery("UPDATE message set isRead=1 WHERE mid=" + mid).executeUpdate();
			else {
				if (receiverUid != 0)
					session.createSQLQuery("UPDATE message set isRead=1 WHERE receiver_uid=" + receiverUid + " AND notice_type=" + noticeType).executeUpdate();
				else
					session.createSQLQuery("UPDATE message set isRead=1 WHERE sender_uid=" + senderUid + " AND notice_type=" + noticeType).executeUpdate();
			}
			session.flush();
			
			tx.commit();
		} catch(RuntimeException e) {
			if (tx != null) {
				System.out.println("Failed to change message's status");
				tx.rollback();
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
}
