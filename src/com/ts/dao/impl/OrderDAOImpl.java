package com.ts.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ts.dao.OrderDAO;
import com.ts.entity.Order;

public class OrderDAOImpl implements OrderDAO {
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public boolean addOrder(Order order) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			session.save(order);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null) {
				System.out.println("0: fail to give an order!");
				tx.rollback();
				e.printStackTrace();
			}
			return false;
		} finally {
			session.close();
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<Order> fetchUserAllOrderList(int uid) {
		Session session = sessionFactory.openSession();
		Query query = null;
		query = session.createQuery("from Order order where order.buyerId=" + uid + " order by order.addTime desc");
		return (List<Order>) query.list();
	}
	
}
