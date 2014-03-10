package com.ts.action.admin;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.service.UserService;

public class GetAllDataAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2045850679244368692L;
	
	private UserService uService;

	@Override
	public String execute() throws Exception {
		System.out.println("Good");
		return SUCCESS;
	}

	public UserService getuService() {
		return uService;
	}
	public void setuService(UserService uService) {
		this.uService = uService;
	}

}
