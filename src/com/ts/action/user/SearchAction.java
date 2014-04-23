package com.ts.action.user;

import java.util.ArrayList;
import java.util.List;



import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Good;
import com.ts.service.GoodService;

public class SearchAction extends ActionSupport implements RequestAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1904488773294951139L;
	
	private GoodService gService;
	private String keyword;
	private Map<String, Object> requestMap;
	
	public String execute() throws Exception {
		List<Good> list = new ArrayList<Good>();
		list = gService.searchGoodByTitle(keyword, 0);
		requestMap.put("keyword", keyword);
		requestMap.put("resultList", list);
		return SUCCESS;
	}
	
	public GoodService getgService() {
		return gService;
	}
	public void setgService(GoodService gService) {
		this.gService = gService;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public void setRequest(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}
}
