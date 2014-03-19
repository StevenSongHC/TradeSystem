package com.ts.service.impl;

import com.ts.dao.OrderDAO;
import com.ts.service.OrderService;

public class OrderServiceImpl implements OrderService {
	private OrderDAO orderDao;
	public OrderDAO getOrderDao() {
		return orderDao;
	}
	public void setOrderDao(OrderDAO orderDao) {
		this.orderDao = orderDao;
	}
	
}
