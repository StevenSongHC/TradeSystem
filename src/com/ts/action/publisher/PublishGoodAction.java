package com.ts.action.publisher;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.service.GoodService;
import com.ts.service.PublisherService;
import com.ts.service.UserService;

public class PublishGoodAction extends ActionSupport {

	/**
	 * 提交新商品
	 */
	private static final long serialVersionUID = -669322580267914743L;
	
	private UserService uService;
	private PublisherService pService;
	private GoodService gService;
	
	public String execute() throws Exception {
		System.out.println("publish good goes here");
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
	public GoodService getgService() {
		return gService;
	}
	public void setgService(GoodService gService) {
		this.gService = gService;
	}

}
