package com.ts.service.impl;

import org.apache.struts2.ServletActionContext;

import com.ts.bean.PageBean;
import com.ts.dao.UserDAO;
import com.ts.entity.User;
import com.ts.service.UserService;
import com.ts.util.CookieUtil;
import com.ts.util.PageUtil;

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
	
	public User getUserByName(String name) {
		return userDao.getUserByName(name);
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
	
	/*public PageBean getUserPageList(final int pageSize, final int currentPage,
			final int isSuspend, final int isDelete, final int isRestricted, final int isPublisher) {
		// condition(s)
		StringBuilder condition = new StringBuilder();
		boolean isMulti = false;
		if (isSuspend != 2 || isDelete != 2 || isRestricted != 2 || isPublisher != 2) {
			condition.append(" where");
			if ()
		}
		
		PageBean pb = new PageBean();
		
		return pb;
	}*/
	public PageBean getUserPageList(final int pageSize, final int currentPage, final String conditions[][], final String sort[]) {
		boolean flag = false;
		boolean isMulti = false;
		final String SORT[] = {"desc", "asc"};		// 0 = descending, 1 = ascending
		StringBuilder condition = new StringBuilder();
		
		// iter every conditions' value
		int index = 0;
		while (index < conditions[1].length) {
			if (!conditions[1][index].equals("2")) {
				// add the syntax of " where"
				if (!flag) {
					condition.append(" where");
					flag = true;
				}
				// add the multi-condition query syntax
				if (isMulti)
					condition.append(" and");
				condition.append(" u." + conditions[0][index] + "=" + conditions[1][index]);
				isMulti = true;
			}
			index++;
		}
		
		// exclude admin
		if (!flag)
			condition.append(" where");
		if (isMulti)
			condition.append(" and");
		condition.append(" u.id>1");
		
		PageBean pb = new PageBean();
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setAllRow(userDao.getAllRow(condition.toString()));
		
		// iter data sort solution
		condition.append(" order by u." + sort[0] + " " + SORT[Integer.parseInt(sort[1])]);
		
		pb.setTotalPage(pb.countTotalPage(pageSize, pb.getAllRow()));
		pb.setResult(userDao.queryPage(pb.countOffset(pageSize, currentPage), pageSize, condition.toString()));
		for (User user : userDao.queryPage(pb.countOffset(pageSize, currentPage), pageSize, condition.toString())) {
			System.out.println("#"+user.getId()+" - "+user.getName());
		}
		pb.setNaviBar(PageUtil.drawNavi(pb.getCurrentPage(), pb.getTotalPage()));
		return pb;
	}
	
	public boolean checkAuth(int uid, String auth) {
		return userDao.checkAuth(uid, auth);
	}
	
	public boolean modifyAuth(int uid, String auth, int val) {
		return userDao.modifyAuth(uid, auth, val);
	}
	
}
