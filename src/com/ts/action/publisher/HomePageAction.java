package com.ts.action.publisher;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Publisher;
import com.ts.entity.User;
import com.ts.service.PublisherService;
import com.ts.service.UserService;

public class HomePageAction extends ActionSupport implements RequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7503377477094300818L;
	
	public UserService uService;
	private PublisherService pService;
	private int pid;
	private Map<String, Object> requestMap;

	public String execute() throws Exception {
		Publisher publisher = pService.getPublisherByPid(pid);
		User user = uService.getCurrentUser();
		// NULL, such publisher doesn't existed
		if (publisher == null)
			return "null";
		
		requestMap.put("publisher", publisher);
		
		if (user == null)
			return "guest";
		else if (user.getId() == publisher.getUid())
			return "host";
		else
			return "guest";
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
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public void setRequest(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

}
