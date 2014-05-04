package com.ts.action.admin;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.service.GoodService;
import com.ts.service.PublisherService;
import com.ts.service.UserService;

public class GetAllDataAction extends ActionSupport implements RequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2045850679244368692L;
	
	private PublisherService pService;
	private UserService uService;
	private GoodService gService;
	
	private Map<String, Object> requestMap;

	@Override
	public String execute() throws Exception {
		// FILTER columns
		final String USER_COLUMN[] = {"isSuspend",
								 "isDelete",
								 "isRestricted",
								 "isPublisher"};
		final String PUBLISHER_COLUMN[] = {"isActivate",
										   "isRestricted"};
		final String GOOD_COLUMN[] = {"isComplete",
				  					  "isAgree",
				  					  "isAvailable",
				  					  "isDelete"};
		
		// values
		// 0 = false, 1 = true, 2 = all(ignore)
		int userColumnArgs[] = {2, 2, 2, 2};
		int publisherColumnArgs[] = {2, 2};
		int goodColumnArgs[] = {2, 2, 2, 2};
		
		// SORT solution
		// default sort by id desc
		String[] userColumnSort = {"id", "0"};				// id | name
		String[] publisherColumnSort = {"id", "0"};	// id | name | joinDate | goodCount
		String[] goodColumnSort = {"id", "0"};			// id | pid  | price    | buyerCount | addTime
		
		// query conditions
		String userColumnConditions[][] = new String[2][4];
		String publisherColumnConditions[][] = new String [2][2];
		String goodColumnConditions[][] = new String [2][2];
		
		// assign
		for (int i=0; i<userColumnConditions[0].length; i++) {
			userColumnConditions[0][i] = USER_COLUMN[i];
			userColumnConditions[1][i] = String.valueOf(userColumnArgs[i]);
		}
		for (int i=0; i<publisherColumnConditions[0].length; i++) {
			publisherColumnConditions[0][i] = PUBLISHER_COLUMN[i];
			publisherColumnConditions[1][i] = String.valueOf(publisherColumnArgs[i]);
		}
		for (int i=0; i<goodColumnConditions[0].length; i++) {
			goodColumnConditions[0][i] = GOOD_COLUMN[i];
			goodColumnConditions[1][i] = String.valueOf(goodColumnArgs[i]);
		}
		
		// PageBeans
		requestMap.put("userList", uService.getUserPageList(5, 1, userColumnConditions, userColumnSort));							// user list
		requestMap.put("publisherList", pService.getPublisherPageList(5, 1, publisherColumnConditions, publisherColumnSort));		// publisher list
		requestMap.put("goodList", gService.getGoodPageList(5, 1, goodColumnConditions, goodColumnSort));							// good list
		
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
	public Map<String, Object> getRequestMap() {
		return requestMap;
	}
	public void setRequestMap(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}
	public void setRequest(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

}
