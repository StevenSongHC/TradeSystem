package com.ts.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ts.dao.UserDAO;
import com.ts.entity.User;

public class UserDAOImpl implements UserDAO {
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/* 
	 * 注册表单填完后将其注册入数据库,涉及到的表有
	 * user
	 * authority
	 * notification
	 */
	public int addUser(User user) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		// check
		Query query = null;
		query = session.createQuery("from User user where user.name='" + user.getName() + "'");
		if (query.uniqueResult() != null) {
			System.out.println("-2: username confict");
			return -2;
		}
		else {
			query = session.createQuery("from User user where user.account='" + user.getAccount() + "'");
			if (query.uniqueResult() != null) {
				System.out.println("-1: account confict");
				return -1;
			}
		}
		try {
			// insert into user
			session.save(user);
			System.out.println("1: table -> user saved");
			
			// insert into authority
			session.createSQLQuery("INSERT INTO authority(uid) VALUES(" + user.getId() + ")").executeUpdate();
			session.flush();
			System.out.println("1: table -> authority saved");
			
			// insert into notification
			session.createSQLQuery("INSERT INTO notification(uid) VALUES(" + user.getId() + ")").executeUpdate();
			session.flush();
			System.out.println("1: table -> notification saved");
			
			tx.commit();
		} catch(RuntimeException e) {
			if (tx != null) {
				System.out.println("0: user register FAILED, rollback!");
				tx.rollback();
				e.printStackTrace();
			}
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}
	
	public User getUserById(int id) {
		return (User) sessionFactory.openSession().get(User.class, id);
	}
	
	public User getUserByAccount(String account) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from User user where user.account='" + account + "'");
		session.getTransaction().commit();
		return (User) query.uniqueResult();
	}
	
}
