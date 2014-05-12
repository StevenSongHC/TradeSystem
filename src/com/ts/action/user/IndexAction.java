package com.ts.action.user;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.service.GoodService;

public class IndexAction extends ActionSupport implements RequestAware {

	/**
	 * index page contains good-list(new)
	 */
	private static final long serialVersionUID = -5408899195231372773L;

	private Map<String, Object> requestMap;
	private GoodService gService;

	@Override
	public String execute() throws Exception {
		requestMap.put("pageNavi", gService.getPublisherGoodPageList(0, 10, 1, 1, 1, 1, 0, -1));
		return SUCCESS;
	}

	public void setRequest(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}
	public GoodService getgService() {
		return gService;
	}
	public void setgService(GoodService gService) {
		this.gService = gService;
	}

}
