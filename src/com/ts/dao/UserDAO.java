package com.ts.dao;

import com.ts.entity.User;

public interface UserDAO {
	
	public int addUser(User user);
	
	public User getUserById(int id);
	
	public User getUserByAccount(String account);
	
	public boolean updateUser(User user);
	
}
