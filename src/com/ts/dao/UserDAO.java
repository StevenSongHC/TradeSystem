package com.ts.dao;

import java.util.List;

import com.ts.entity.User;

public interface UserDAO {
	
	public int addUser(User user);
	
	public User getUserById(int id);
	
	public User getUserByAccount(String account);
	
	public boolean updateUser(User user);
	
	public int getAllRow(String condition);
	
	public List<User> queryPage(final int offset, final int pageSize, final String condition);
	
	public boolean checkAuth(int uid, String auth);
	
	public boolean modifyAuth(int uid, String auth, int val);
	
}
