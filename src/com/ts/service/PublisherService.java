package com.ts.service;

import com.ts.bean.PageBean;
import com.ts.entity.Publisher;

public interface PublisherService {

	/**
	 * 
	 * @param publisher
	 * @return
	 * [-1: Name conflict]
	 * [0: Unknown error]
	 * [1: Success]
	 */
	public boolean addPublisher(Publisher publisher);
	
	public boolean registerPublisher(int uid);
	
	public boolean cancelPublisher(int uid);
	
	/**
	 * @param name
	 * @return isNotExisted
	 */
	public boolean validateName(String name);
	
	public Publisher getCurrentPublisher();
	
	public Publisher getPublisherByUid(int uid);
	
	public Publisher getPublisherByPid(int pid);
	
	public Publisher getPublisherByContact(String contact);
	
	public int getAdminUid();
	
	public boolean isAdmin();
	
	public boolean updatePublisher(Publisher publisher);
	
	public boolean publishGood(int pid);	// validate auths and account status requires
	
	public PageBean getPublisherPageList(final int pageSize, final int currentPage, final String conditions[][], final String sort[]);

}
