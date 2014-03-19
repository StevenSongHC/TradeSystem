package com.ts.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ts.dao.PublisherDAO;
import com.ts.entity.Publisher;

public class PublisherDAOImpl implements PublisherDAO {
	public static final int ADMIN_CODE = 2779;
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public boolean addPublisher(Publisher publisher) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			// insert into publisher
			session.save(publisher);
			System.out.println("1: table -> publisher saved");
			
			tx.commit();
		} catch(RuntimeException e) {
			if (tx != null) {
				System.out.println("0: publisher upgrade FAILED, rollback!");
				tx.rollback();
				e.printStackTrace();
			}
			return false;
		} finally {
			session.close();
		}
		return true;
	}
	
	public boolean registerPublisher(int uid) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			// change user's isPublisher status
			session.createSQLQuery("UPDATE user SET u_isPublisher=1 WHERE uid=" + uid).executeUpdate();
			session.flush();
			System.out.println("#1: table -> user modified");
			// change publisher's isActivate status
			session.createSQLQuery("UPDATE publisher SET p_isActivate=1 WHERE uid=" + uid).executeUpdate();
			session.flush();
			// add publisher's joinDate
			session.createSQLQuery("UPDATE publisher SET p_join_date=(select curdate()) WHERE uid=" + uid).executeUpdate();
			session.flush();
			System.out.println("#2: table -> publisher modified");
			// give publisher authority
			session.createSQLQuery("UPDATE authority SET auth_publish_good=1,auth_edit_good=1,auth_suspend_good=1,auth_delete_good=1 WHERE uid=" + uid).executeUpdate();
			session.flush();
			System.out.println("#3: table -> authority modified");
			
			tx.commit();
		} catch(RuntimeException e) {
			if (tx != null) {
				System.out.println("0: publisher upgrade FAILED, rollback!");
				tx.rollback();
				e.printStackTrace();
			}
			return false;
		} finally {
			session.close();
		}
		return true;
	}
	
	public boolean removePublisher(int uid) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			// delete user from publisher table
			session.createQuery("DELETE Publisher p WHERE p.uid=" + uid).executeUpdate();
			System.out.println("1: delete publisher, done");
			session.flush();
			
			tx.commit();
		} catch(RuntimeException e) {
			if (tx != null) {
				System.out.println("0: delete publisher FAILED, rollback!");
				tx.rollback();
				e.printStackTrace();
			}
			return false;
		} finally {
			session.close();
		}
		return true;
	}
	
	public boolean isNameExist(String name) {
		Session session = sessionFactory.openSession();
		Query query = null;
		query = session.createQuery("from Publisher p where p.name='" + name + "'");
		if (query.uniqueResult() != null)
			return true;
		else
			return false;
	}
	
	public Publisher getAdmin() {
		Session session = sessionFactory.openSession();
		Query query = null;
		query = session.createQuery("from Publisher p where p.isAdmin=" + ADMIN_CODE);
		return (Publisher) query.uniqueResult();
	}
	
	public Publisher getPublisherByUid(int uid) {
		Session session = sessionFactory.openSession();
		Query query = null;
		query = session.createQuery("from Publisher p where p.uid=" + uid);
		return (Publisher) query.uniqueResult();
	}
	
	public Publisher getPublisherByPid(int pid) {
		Session session = sessionFactory.openSession();
		Query query = null;
		query = session.createQuery("from Publisher p where p.id=" + pid);
		return (Publisher) query.uniqueResult();
	}

}
