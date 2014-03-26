package com.ts.dao.impl;

import java.util.List;

import org.hibernate.Query;
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
	
	public Good getGoodById(int gid) {
		return (Good) sessionFactory.openSession().get(Good.class, gid);
	}
	
	public int getAllRow(String condition) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("select count(*) from Good g" + condition);
		return Integer.parseInt(query.list().get(0).toString());
	}
	
	@SuppressWarnings("unchecked")
	public List<Good> queryPage(int offset, int pageSize, String condition) {
		Query query = sessionFactory.openSession().createQuery("from Good g" + condition);
		query.setFirstResult(offset);
		query.setMaxResults(pageSize);
		return query.list();
	}
	
}
