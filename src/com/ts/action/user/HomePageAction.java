package com.ts.action.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Good;
import com.ts.entity.Order;
import com.ts.entity.Publisher;
import com.ts.entity.User;
import com.ts.service.GoodService;
import com.ts.service.OrderService;
import com.ts.service.PublisherService;
import com.ts.service.UserService;

public class HomePageAction extends ActionSupport implements RequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9140180521319178203L;
	
	private String account;
	private UserService uService;
	private PublisherService pService;
	private OrderService oService;
	private GoodService gService;
	private Map<String, Object> requestMap;
	
	public String execute() throws Exception {
		User user = null;
		// without account parameter
		if (null == account)
			user = uService.getCurrentUser();
		// with it
		else
			user = uService.getUserByAccount(account);
		
		// user not existed
		if (user == null)
			return "null";
		// go to
		else {
			user.setPassword("123456");
			requestMap.put("user", user);
			
			// put user's order list
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<Order> orderList = new ArrayList<Order>();
			orderList = oService.fetchUserAllOrderList(user.getId());
			if (orderList != null) {
				for (Order order: orderList) {
					Publisher publisher = pService.getPublisherByUid(order.getSellerId());
					Good good = gService.getGoodById(order.getGoodId());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("orderId", order.getId());
					map.put("goodId", good.getId());
					map.put("goodTitle", good.getTitle());
					map.put("goodPrice", good.getPrice());
					map.put("goodPic", good.getPic());
					map.put("publisherPid", publisher.getId());
					map.put("publisherName", publisher.getName());
					map.put("time", order.getAddTime().toString());
					list.add(map);
				}
				requestMap.put("orderList", list);
			}
			else
				requestMap.put("orderList", null);
		}
		
		return SUCCESS;
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
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
	public OrderService getoService() {
		return oService;
	}
	public void setoService(OrderService oService) {
		this.oService = oService;
	}
	public void setRequest(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

}
