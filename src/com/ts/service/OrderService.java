package com.ts.service;

import java.util.List;

import com.ts.entity.Order;

public interface OrderService {

	public boolean newOrder(Order order);
	
	public List<Order> fetchUserAllOrderList(int uid);
}
