package com.ts.action.publisher;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Publisher;
import com.ts.entity.User;
import com.ts.service.GoodService;
import com.ts.service.PublisherService;
import com.ts.service.UserService;

public class HomePageAction extends ActionSupport implements RequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7503377477094300818L;
	
	public UserService uService;
	private PublisherService pService;
	private GoodService gService;
	private int pid;
	private int currentPage;
	private int pageSize;
	private Map<String, Object> requestMap;

	public String execute() throws Exception {
		Publisher publisher = pService.getPublisherByPid(pid);
		User user = uService.getCurrentUser();
		// NULL, such publisher doesn't existed
		if (publisher == null)
			return "null";
		
		requestMap.put("publisher", publisher);
		
		// default return the first page of goods data
		if (currentPage == 0)
			currentPage = 1;
		// pageSize's default value is 5
		if (pageSize == 0)
			pageSize = 5;
		
		// anonymous visitor
		if (user == null) {
			requestMap.put("pageNavi", gService.getGoodPageList(pid, pageSize, currentPage, 1, 1, 1, 0, -1));
			return "guest";
		}
		// owner
		else if (user.getId() == publisher.getUid()) {
			requestMap.put("pageNavi", gService.getGoodPageList(pid, pageSize, currentPage, 2, 2, 2, 0, -1));
			return "host";
		}
		// visitor
		else {
			requestMap.put("pageNavi", gService.getGoodPageList(pid, pageSize, currentPage, 1, 1, 1, 0, -1));
			return "guest";
		}
	}

	public UserService getuService() {
		return uService;
	}
	public void setuService(UserService uService) {
		this.uService = uService;
	}
	public PublisherService getpService() {
		return pService;
	}
	public void setpService(PublisherService pService) {
		this.pService = pService;
	}
	public GoodService getgService() {
		return gService;
	}
	public void setgService(GoodService gService) {
		this.gService = gService;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public void setRequest(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

}
