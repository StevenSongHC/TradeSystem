package com.ts.dao;

import java.util.List;

import com.ts.entity.Order;

public interface OrderDAO {

	public boolean addOrder(Order order);
	
	public List<Order> fetchUserAllOrderList(int uid);
	
}
