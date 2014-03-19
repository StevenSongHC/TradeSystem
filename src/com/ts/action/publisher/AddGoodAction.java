package com.ts.action.publisher;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.service.PublisherService;
import com.ts.service.UserService;

public class AddGoodAction extends ActionSupport {

	/**
	 * 提交新商品
	 */
	private static final long serialVersionUID = 6979702910252841864L;
	
	private UserService uService;
	private PublisherService pService;
	
	public String execute() throws Exception {
		
		return SUCCESS;
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

}
