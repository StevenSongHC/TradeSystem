package com.ts.dao.impl;

import java.util.List;

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
		// prevent SQL injection
		Query query = session.createQuery("from User user where user.account=?").setString(0, account);
		session.getTransaction().commit();
		return (User) query.uniqueResult();
	}
	
	public User getUserByName(String name) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from User user where user.name=?").setString(0, name);
		session.getTransaction().commit();
		return (User) query.uniqueResult();
	}
	
	public boolean updateUser(User user) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			session.update(user);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null) {
				System.out.println("0: fail to update user info!");
				tx.rollback();
				e.printStackTrace();
			}
			return false;
		} finally {
			session.close();
		}
		return true;
	}
	
	public int getAllRow(String condition) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("select count(*) from User u" + condition);
		return Integer.parseInt(query.list().get(0).toString());
	}
	
	@SuppressWarnings("unchecked")
	public List<User> queryPage(final int offset, final int pageSize, final String condition) {
		Query query = sessionFactory.openSession().createQuery("from User u" + condition);
		query.setFirstResult(offset);
		query.setMaxResults(pageSize);
		return query.list();
	}
	
	public boolean checkAuth(int uid, String auth) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createSQLQuery("SELECT " + auth + " FROM authority WHERE uid=" + uid);
		return (Integer.parseInt(query.uniqueResult().toString()) == 1);
	}
	
	public boolean modifyAuth(int uid, String auth, int val) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.createSQLQuery("UPDATE authority SET " + auth + "=" + val + " WHERE uid=" + uid).executeUpdate();
			session.flush();
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null) {
				System.out.println("change " + uid + "'s authority failed");
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
