package com.ts.service.impl;

import com.ts.dao.GoodDAO;
import com.ts.entity.Good;
import com.ts.service.GoodService;

public class GoodServiceImpl implements GoodService {
	private GoodDAO goodDao;
	public GoodDAO getGoodDao() {
		return goodDao;
	}
	public void setGoodDao(GoodDAO goodDao) {
		this.goodDao = goodDao;
	}
	
	public boolean addGood(Good good) {
		return goodDao.addGood(good);
	}
	
	public boolean updateGood(Good good) {
		return goodDao.updateGood(good);
	}
	
}
