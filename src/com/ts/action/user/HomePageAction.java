package com.ts.action.user;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.User;
import com.ts.service.UserService;

public class HomePageAction extends ActionSupport implements RequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9140180521319178203L;
	
	private String account;
	private UserService uService;
	private Map<String, Object> requestMap;
	
	public String execute() throws Exception {
		User user = null;
		// without account parameter
		if (null == account)
			user = uService.getCurrentUser();
		// with it
		else
			user = uService.getUserByAccount(account);
		
		// user not existed
		if (user == null)
			return "null";
		// go to
		else {
			user.setPassword("123456");
			requestMap.put("user", user);
		}
		
		return SUCCESS;
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
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
