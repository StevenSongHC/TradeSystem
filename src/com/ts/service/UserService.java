package com.ts.service;

import com.ts.bean.PageBean;
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
	
	/*public PageBean getUserPageList(final int pageSize, final int currentPage,
									final int isSuspend, final int isDelete, final int isRestricted, final int isPublisher);*/
	public PageBean getUserPageList(final int pageSize, final int currentPage, final String conditions[][], final String sort[]);

}
