package com.ts.action.user;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.User;
import com.ts.service.UserService;

public class UpdateProfileAction extends ActionSupport implements RequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1768045715778884522L;

	private UserService uService;
	
	private Map<String, Object> requestMap;
	
	public String execute() throws Exception {
		User user = uService.getCurrentUser();
		if (user == null || user.getIsSuspend())
			return LOGIN;
		requestMap.put("user", user);
		return INPUT;
	}

	public UserService getuService() {
		return uService;
	}
	public void setuService(UserService uService) {
		this.uService = uService;
	}
	public void setRequest(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

}
