package com.ts.service.impl;

import org.apache.struts2.ServletActionContext;

import com.ts.dao.UserDAO;
import com.ts.entity.User;
import com.ts.service.UserService;
import com.ts.util.CookieUtil;

public class UserServiceImpl implements UserService {
	private UserDAO userDao;
	public UserDAO getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	public int addUser(User user) {
		return userDao.addUser(user);
	}

	public User getUserById(int id) {
		return userDao.getUserById(id);
	}
	
	public User getUserByAccount(String account) {
		return userDao.getUserByAccount(account);
	}
	
	public int login(String account, String password) {
		try {
			User user = userDao.getUserByAccount(account);
			if (user == null)								// Invalid account
				return -2;
			else if (!password.equals(user.getPassword()))	// Wrong password
				return -1;			
			else {											// Login succeed
				CookieUtil.generateUserCookie(user);	// add user cookie	
				return 1;
			}
		} catch(RuntimeException e) {
			System.out.println("login error hit!");
			e.printStackTrace();
			return 0;				// Unknown error
		}
	}
	
	public User getCurrentUser() {
		return (User) ServletActionContext.getRequest().getSession()
				.getAttribute(com.ts.action.user.LoginAction.USER_SESSION);
	}
	
	public boolean updateUser(User user) {
		return userDao.updateUser(user);
	}
	
}
