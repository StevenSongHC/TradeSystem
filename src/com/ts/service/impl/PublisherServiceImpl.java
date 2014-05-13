package com.ts.service.impl;

import org.apache.struts2.ServletActionContext;

import com.ts.bean.PageBean;
import com.ts.dao.PublisherDAO;
import com.ts.entity.Publisher;
import com.ts.service.PublisherService;
import com.ts.util.PageUtil;

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
	
	public Publisher getPublisherByContact(String contact) {
		return publisherDao.getPublisherByContact(contact);
	}
	
	public int getAdminUid() {
		return publisherDao.getAdmin().getUid();
	}
	
	public boolean isAdmin() {
		return getCurrentPublisher().getUid() == getAdminUid();
	}
	
	public boolean updatePublisher(Publisher publisher) {
		return publisherDao.updatePublisher(publisher);
	}
	
	public boolean publishGood(int pid) {
		return publisherDao.publishGood(pid);
	}
	
	public PageBean getPublisherPageList(final int pageSize, final int currentPage, final String conditions[][], final String sort[]) {
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
				condition.append(" p." + conditions[0][index] + "=" + conditions[1][index]);
				isMulti = true;
			}
			index++;
		}
		// exclude admin
		if (!flag)
			condition.append(" where");
		if (isMulti)
			condition.append(" and");
		condition.append(" p.id>1");
		
		PageBean pb = new PageBean();
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setAllRow(publisherDao.getAllRow(condition.toString()));
		
		// iter data sort solution
		condition.append(" order by p." + sort[0] + " " + SORT[Integer.parseInt(sort[1])]);
		
		pb.setTotalPage(pb.countTotalPage(pageSize, pb.getAllRow()));
		pb.setResult(publisherDao.queryPage(pb.countOffset(pageSize, currentPage), pageSize, condition.toString()));
		for (Publisher publisher : publisherDao.queryPage(pb.countOffset(pageSize, currentPage), pageSize, condition.toString())) {
			System.out.println("#"+publisher.getId()+" - "+publisher.getName());
		}
		pb.setNaviBar(PageUtil.drawNavi(pb.getCurrentPage(), pb.getTotalPage()));
		return pb;
	}
	
}
