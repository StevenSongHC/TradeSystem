package com.ts.dao;

import java.util.List;

import com.ts.entity.Publisher;

public interface PublisherDAO {
	
	public boolean addPublisher(Publisher publisher);	// temp

	public boolean registerPublisher(int uid);			// officially
	
	public boolean removePublisher(int uid);
	
	public boolean isNameExist(String name);
	
	public Publisher getAdmin();

	public Publisher getPublisherByUid(int uid);
	
	public Publisher getPublisherByPid(int pid);

	public Publisher getPublisherByContact(String contact);
	
	public boolean updatePublisher(Publisher publisher);
	
	public boolean publishGood(int pid);
	
	public int getAllRow(String condition);
	
	public List<Publisher> queryPage(final int offset, final int pageSize, final String condition);
	
}
