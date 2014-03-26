package com.ts.dao;

import java.util.List;

import com.ts.entity.Good;

public interface GoodDAO {

	public boolean addGood(Good good);
	
	public boolean updateGood(Good good);
	
	public Good getGoodById(int gid);
	
	public int getAllRow(String condition);
	
	public List<Good> queryPage(final int offset, final int pageSize, final String condition);
	
}
