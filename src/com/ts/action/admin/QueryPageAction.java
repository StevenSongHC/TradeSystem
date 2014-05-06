package com.ts.action.admin;

import java.text.SimpleDateFormat;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

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
	private String filterArray;
	private String sortArray;
	
	private String json;

	public String execute() throws Exception {
		PageBean pb = new PageBean();
		if (currentPage == 0)
			currentPage = 1;
		System.out.println(naviType+"_"+pageSize+"_"+currentPage+"_"+filterArray+"_"+sortArray);
		final String USER_COLUMN[] = 		{"isSuspend",
									  		 "isDelete",
									  		 "isRestricted",
									  		 "isPublisher"};
		final String PUBLISHER_COLUMN[] = 	{"isActivate",
										   	 "isRestricted"};
		final String GOOD_COLUMN[] = 		{"isComplete",
				 					  		 "isAgree",
				 					  		 "isAvailable",
				 					  		 "isDelete"};
		
		
		int[] userColumnArgs = new int[4];
		int[] publisherColumnArgs = new int[2];
		int[] goodColumnArgs = new int[4];
		String userColumnConditions[][] = new String[2][4];
		String publisherColumnConditions[][] = new String[2][2];
		String goodColumnConditions[][] = new String[2][4];
		
		// make Date friendly to json format
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessor() {
			private SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
				return value == null? "" : sd.format(value);
			}
			public Object processArrayValue(Object value, JsonConfig jsonConfig) {
				return null;
			}
		});
		
		if (naviType.equals("user")) {
			for (int i =0; i<userColumnArgs.length; i++) {
				userColumnArgs[i] = Integer.parseInt(filterArray.split(",")[i]);
			}
			
			for (int i=0; i<userColumnConditions[0].length; i++) {
				userColumnConditions[0][i] = USER_COLUMN[i];
				userColumnConditions[1][i] = String.valueOf(userColumnArgs[i]);
			}
			
			pb = uService.getUserPageList(pageSize, currentPage, userColumnConditions, sortArray.split(","));
		}
		
		else if(naviType.equals("publisher")) {
			for (int i =0; i<publisherColumnArgs.length; i++) {
				publisherColumnArgs[i] = Integer.parseInt(filterArray.split(",")[i]);
			}
			
			for (int i=0; i<publisherColumnConditions[0].length; i++) {
				publisherColumnConditions[0][i] = PUBLISHER_COLUMN[i];
				publisherColumnConditions[1][i] = String.valueOf(publisherColumnArgs[i]);
			}
			
			pb = pService.getPublisherPageList(pageSize, currentPage, publisherColumnConditions, sortArray.split(","));
		}
		
		else if (naviType.equals("good")) {
			for (int i =0; i<goodColumnArgs.length; i++) {
				goodColumnArgs[i] = Integer.parseInt(filterArray.split(",")[i]);
			}
			
			for (int i=0; i<goodColumnConditions[0].length; i++) {
				goodColumnConditions[0][i] = GOOD_COLUMN[i];
				goodColumnConditions[1][i] = String.valueOf(goodColumnArgs[i]);
			}
			
			pb = gService.getGoodPageList(pageSize, currentPage, goodColumnConditions, sortArray.split(","));
		}
		
		pb.setDataType(naviType);
		
		json = JSONObject.fromObject(pb, jsonConfig).toString();
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
