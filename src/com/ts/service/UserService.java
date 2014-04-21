package com.ts.service;

import com.ts.entity.User;

public interface UserService {
	
	public int addUser(User user);
	
	/**
	 * @param id
	 * @return
	 */
	public User getUserById(int id);
	
	/**
	 * @param account
	 * @return
	 */
	public User getUserByAccount(String account);
	
	/**
	 * @param
	 * @return CODE
	 * [-2: Invalid account]
	 * [-1: Wrong password]
	 * [0: Unknown error]
	 * [1: Login succeed ]
	 */
	public int login(String account, String password);
	
	public User getCurrentUser();
	
	public boolean updateUser(User user);

}
