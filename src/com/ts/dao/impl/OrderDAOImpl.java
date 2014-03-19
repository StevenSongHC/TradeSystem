package com.ts.dao.impl;

import org.hibernate.SessionFactory;

import com.ts.dao.OrderDAO;

public class OrderDAOImpl implements OrderDAO {
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
}
