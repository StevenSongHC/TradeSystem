package com.ts.action.admin;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.service.GoodService;
import com.ts.service.PublisherService;
import com.ts.service.UserService;

public class QueryPageAction extends ActionSupport {

	/**
	 * 管理员用户信息统计
	 */
	private static final long serialVersionUID = -3382652753803312082L;

	private UserService uService;
	private PublisherService pService;
	private GoodService gService;
	
	private String naviType;
	private int pageSize;
	private int currentPage;
	private String filterArray;
	private String sortArray;
	private String json;

	public String execute() throws Exception {
		if (currentPage == 0)
			currentPage = 1;
		System.out.println(naviType+"_"+pageSize+"_"+currentPage+"_"+filterArray+"_"+sortArray);
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
		
		
		int[] userColumnArgs = new int[4];
		
		
		for (int i =0; i<userColumnArgs.length; i++) {
			userColumnArgs[i] = Integer.parseInt(filterArray.split(",")[i]);
		}
		
		String userColumnConditions[][] = new String[2][4];
		
		
		for (int i=0; i<userColumnConditions[0].length; i++) {
			userColumnConditions[0][i] = USER_COLUMN[i];
			userColumnConditions[1][i] = String.valueOf(userColumnArgs[i]);
		}
		
		
		uService.getUserPageList(pageSize, currentPage, userColumnConditions, sortArray.split(","));
		
		
		return "map";
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
	public String getNaviType() {
		return naviType;
	}
	public void setNaviType(String naviType) {
		this.naviType = naviType;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public String getFilterArray() {
		return filterArray;
	}
	public void setFilterArray(String filterArray) {
		this.filterArray = filterArray;
	}
	public String getSortArray() {
		return sortArray;
	}
	public void setSortArray(String sortArray) {
		this.sortArray = sortArray;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}

}
