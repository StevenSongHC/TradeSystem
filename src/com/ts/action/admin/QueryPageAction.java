package com.ts.action.admin;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.bean.PageBean;
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
	private String userFilterArray;
	private String userSortArray;
	private String json;

	public String execute() throws Exception {
		PageBean pb = new PageBean();
		if (currentPage == 0)
			currentPage = 1;
		System.out.println(naviType+"_"+pageSize+"_"+currentPage+"_"+userFilterArray+"_"+userSortArray);
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
			userColumnArgs[i] = Integer.parseInt(userFilterArray.split(",")[i]);
		}
		
		String userColumnConditions[][] = new String[2][4];
		
		
		for (int i=0; i<userColumnConditions[0].length; i++) {
			userColumnConditions[0][i] = USER_COLUMN[i];
			userColumnConditions[1][i] = String.valueOf(userColumnArgs[i]);
		}
		
		
		pb = uService.getUserPageList(pageSize, currentPage, userColumnConditions, userSortArray.split(","));
		pb.setDataType("user");
		
		json = JSONObject.fromObject(pb).toString();
		System.out.println(json);
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
	public String getUserFilterArray() {
		return userFilterArray;
	}
	public void setUserFilterArray(String userFilterArray) {
		this.userFilterArray = userFilterArray;
	}
	public String getUserSortArray() {
		return userSortArray;
	}
	public void setUserSortArray(String userSortArray) {
		this.userSortArray = userSortArray;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}

}
