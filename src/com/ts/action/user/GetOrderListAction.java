package com.ts.action.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Good;
import com.ts.entity.Order;
import com.ts.entity.Publisher;
import com.ts.service.GoodService;
import com.ts.service.OrderService;
import com.ts.service.PublisherService;
import com.ts.service.UserService;

public class GetOrderListAction extends ActionSupport {

	/**
	 * fetch the order list
	 */
	private static final long serialVersionUID = 4220704431342639742L;

	private UserService uService;
	private OrderService oService;
	private PublisherService pService;
	private GoodService gService;
	
	private String type;
	private String json;
	
	public String execute() throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Order> orderList = new ArrayList<Order>();
		
		if (type.equals("all"))
				orderList = oService.fetchUserAllOrderList(uService.getCurrentUser().getId());
		
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
		
		json = JSONArray.fromObject(list).toString();
		
		return "map";
	}

	public UserService getuService() {
		return uService;
	}
	public void setuService(UserService uService) {
		this.uService = uService;
	}
	public OrderService getoService() {
		return oService;
	}
	public void setoService(OrderService oService) {
		this.oService = oService;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}

}
