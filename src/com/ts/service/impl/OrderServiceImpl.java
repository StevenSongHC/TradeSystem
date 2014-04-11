package com.ts.service.impl;

import java.util.List;

import com.ts.dao.OrderDAO;
import com.ts.entity.Order;
import com.ts.service.OrderService;

public class OrderServiceImpl implements OrderService {
	private OrderDAO orderDao;
	public OrderDAO getOrderDao() {
		return orderDao;
	}
	public void setOrderDao(OrderDAO orderDao) {
		this.orderDao = orderDao;
	}
	
	public boolean newOrder(Order order) {
		return orderDao.addOrder(order);
	}
	
	public List<Order> fetchUserAllOrderList(int uid) {
		return orderDao.fetchUserAllOrderList(uid);
	}
	
}
