package com.ts.action.publisher;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Publisher;
import com.ts.entity.User;
import com.ts.service.GoodService;
import com.ts.service.PublisherService;
import com.ts.service.UserService;
import com.ts.util.CookieUtil;

public class HomePageAction extends ActionSupport implements RequestAware,ServletResponseAware,ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7503377477094300818L;
	
	public UserService uService;
	private PublisherService pService;
	private GoodService gService;
	private int pid;
	private Map<String, Object> requestMap;
	private HttpServletResponse response;
	private HttpServletRequest request;

	public String execute() throws Exception {
		Publisher publisher = pService.getPublisherByPid(pid);
		User user = uService.getCurrentUser();
		// NULL, such publisher doesn't existed
		if (publisher == null)
			return NONE;
		
		requestMap.put("publisher", publisher);
		
		// read page-config from cookie
		int pageSize, dateOrder;
		if (CookieUtil.haveSuchCookie(request, com.ts.util.CookieUtil.PAGE_CONFIG_COOKIE)) {
			String pageConfig = CookieUtil.readCookieValue(request, com.ts.util.CookieUtil.PAGE_CONFIG_COOKIE);
			pageSize = Integer.parseInt(pageConfig.split(",")[0]);
			dateOrder = Integer.parseInt(pageConfig.split(",")[1]);
		}
		// if not existed, create one
		else {
			pageSize = 5;		// default 5
			dateOrder = -1;		// default -1
			response.addCookie(CookieUtil.generatePageConfigCookie(pageSize, dateOrder));
		}
		// put the page-config to the front page
		requestMap.put("pageSizeValue", pageSize);
		requestMap.put("dateOrderValue", dateOrder);
		
		// return the first page record
		// anonymous visitor
		if (user == null) {
			requestMap.put("pageNavi", gService.getGoodPageList(pid, pageSize, 1, 1, 1, 1, 0, dateOrder));
			return "guest";
		}
		// owner
		else if (user.getId() == publisher.getUid()) {
			requestMap.put("pageNavi", gService.getGoodPageList(pid, pageSize, 1, 2, 2, 2, 0, dateOrder));
			return "host";
		}
		// visitor
		else {
			requestMap.put("pageNavi", gService.getGoodPageList(pid, pageSize, 1, 1, 1, 1, 0, dateOrder));
			return "guest";
		}
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
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public void setRequest(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

}
