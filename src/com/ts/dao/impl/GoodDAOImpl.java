package com.ts.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ts.dao.GoodDAO;
import com.ts.entity.Good;

public class GoodDAOImpl implements GoodDAO {
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public boolean addGood(Good good) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			session.save(good);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null) {
				System.out.println("0: fail to add a good!");
				tx.rollback();
				e.printStackTrace();
			}
			return false;
		} finally {
			session.close();
		}
		return true;
	}
	
	public boolean updateGood(Good good) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			session.update(good);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null) {
				System.out.println("0: fail to update the good!");
				tx.rollback();
				e.printStackTrace();
			}
			return false;
		} finally {
			session.close();
		}
		return true;
	}
	
}
