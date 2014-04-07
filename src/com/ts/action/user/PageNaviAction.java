package com.ts.action.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.bean.PageBean;
import com.ts.entity.Publisher;
import com.ts.entity.User;
import com.ts.service.GoodService;
import com.ts.service.PublisherService;
import com.ts.service.UserService;
import com.ts.util.CookieUtil;

import net.sf.json.JSONObject;

public class PageNaviAction extends ActionSupport implements ServletResponseAware {

	/**
	 * 用户进行page页面操作用
	 * 更新list和页面导航栏
	 */
	private static final long serialVersionUID = -602713848577782660L;
	
	public UserService uService;
	private PublisherService pService;
	private GoodService gService;
	private int pid;
	private String naviType;
	private int currentPage;
	private int pageSize;
	private int dateOrder;
	private String json;
	private HttpServletResponse response;
	
	public String execute() throws Exception {
		if (currentPage ==0)
			currentPage = 1;
		System.out.println("page size " +pageSize);
		User user = uService.getCurrentUser();
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (naviType.equals("good")) {
			// updateOrCreate the cookie
			response.addCookie(CookieUtil.generatePageConfigCookie(pageSize, dateOrder));
			
			Publisher publisher = pService.getPublisherByPid(pid);
			PageBean pb = new PageBean();
			
			// owner
			if (publisher != null && user.getId() == publisher.getUid()) {
				pb = gService.getGoodPageList(pid, pageSize, currentPage, 2, 2, 2, 0, dateOrder);
			}
			// visitor
			else {
				pb = gService.getGoodPageList(pid, pageSize, currentPage, 1, 1, 1, 0, dateOrder);
			}
			
			map.put("pageSizeValue", pageSize);
			map.put("dateOrderValue", dateOrder);
			map.put("content", pb.getResult());
			map.put("pageNavi", pb.getNaviBar());
		}
		
		json = JSONObject.fromObject(map).toString();
		System.out.println("json: " + json);
		
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
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getNaviType() {
		return naviType;
	}
	public void setNaviType(String naviType) {
		this.naviType = naviType;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getDateOrder() {
		return dateOrder;
	}
	public void setDateOrder(int dateOrder) {
		this.dateOrder = dateOrder;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
}
