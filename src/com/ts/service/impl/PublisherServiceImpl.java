package com.ts.service.impl;

import org.apache.struts2.ServletActionContext;

import com.ts.dao.PublisherDAO;
import com.ts.entity.Publisher;
import com.ts.service.PublisherService;

public class PublisherServiceImpl implements PublisherService {
	private PublisherDAO publisherDao;
	public PublisherDAO getPublisherDao() {
		return publisherDao;
	}
	public void setPublisherDao(PublisherDAO publisherDao) {
		this.publisherDao = publisherDao;
	}
	
	public boolean addPublisher(Publisher publisher) {
		return publisherDao.addPublisher(publisher);
	}
	
	public boolean registerPublisher(int uid) {
		return publisherDao.registerPublisher(uid);
	}
	
	public boolean cancelPublisher(int uid) {
		return publisherDao.removePublisher(uid);
	}
	
	public boolean validateName(String name) {
		return !publisherDao.isNameExist(name);
	}
	
	public Publisher getCurrentPublisher() {
	return (Publisher) ServletActionContext.getRequest().getSession()
			.getAttribute(com.ts.action.user.LoginAction.PUBLISHER_SESSION);
	}
	
	public Publisher getPublisherByUid(int uid) {
		return publisherDao.getPublisherByUid(uid);
	}
	
	public Publisher getPublisherByPid(int pid) {
		return publisherDao.getPublisherByPid(pid);
	}
	
	public int getAdminUid() {
		return publisherDao.getAdmin().getUid();
	}
	
	public boolean isAdmin() {
		return getCurrentPublisher().getUid() == getAdminUid();
	}
	
}
