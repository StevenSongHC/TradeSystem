package com.ts.dao;

import com.ts.entity.Publisher;

public interface PublisherDAO {
	
	public boolean addPublisher(Publisher publisher);	// temp

	public boolean registerPublisher(int uid);			// officially
	
	public boolean removePublisher(int uid);
	
	public boolean isNameExist(String name);
	
	public Publisher getAdmin();

	public Publisher getPublisherByUid(int uid);
	
	public Publisher getPublisherByPid(int pid);
	
}
