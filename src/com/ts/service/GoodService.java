package com.ts.service;

import com.ts.bean.PageBean;
import com.ts.entity.Good;

public interface GoodService {

	public boolean addGood(Good good);
	
	public boolean updateGood(Good good);
	
	public Good getGoodById(int gid);
	
	/**
	 * ( 0 = false, 1 = true, 2 = ignore )
	 * visitor: *, *, 1, 1, 1, 0,*;
	 * owner: *, *, 2, 2, 2, 0,*;
	 * admin: *, *, 2, 2, 2, 2,*;
	 * @param pageSize
	 * @param currentPage
	 * @param isComplete
	 * @param isAgreed
	 * @param isAvailable
	 * @param isDeleted
	 * @return List<Good>
	 */
	public PageBean getGoodPageList(final int pid, final int pageSize, final int currentPage, 
									final int isComplete, final int isAgreed, final int isAvailable, final int isDeleted, final int timeOrder);
	
}
