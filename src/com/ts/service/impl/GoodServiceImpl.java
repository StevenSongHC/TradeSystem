package com.ts.service.impl;

import java.util.List;

import com.ts.bean.PageBean;
import com.ts.dao.GoodDAO;
import com.ts.entity.Good;
import com.ts.service.GoodService;
import com.ts.util.PageUtil;

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
	
	public Good getGoodById(int gid) {
		return goodDao.getGoodById(gid);
	}
	
	public PageBean getPublisherGoodPageList(int pid, int pageSize, int currentPage, 
									int isComplete, int isAgreed, int isAvailable, int isDeleted, int dateOrder) {
		
		// query condition(s)
		StringBuilder condition = new StringBuilder();
		boolean isMulti = false;
		if (pid != 0 || isComplete != 2 || isAgreed != 2 || isAvailable != 2 || isDeleted != 2) {
			condition.append(" where");
			if (pid != 0) {
				condition.append(" g.publisherId=" + pid);
				isMulti = true;
			}
			if (isComplete != 2) {
				if (isMulti)
					condition.append(" and");
				condition.append(" g.isComplete=" + isComplete);
				isMulti = true;
			}
			if (isAgreed != 2) {
				if (isMulti)
					condition.append(" and");
				condition.append(" g.isAgree=" + isAgreed);
				isMulti = true;
			}
			if (isAvailable != 2) {
				if (isMulti)
					condition.append(" and");
				condition.append(" g.isAvailable=" + isAvailable);
				isMulti = true;
			}
			if (isDeleted != 2) {
				if (isMulti)
					condition.append(" and");
				condition.append(" g.isDelete=" + isDeleted);
			}
		}
		
		PageBean pb = new PageBean();
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setAllRow(goodDao.getAllRow(condition.toString()));

		// order by descending add-time
		if (dateOrder == -1)
			condition.append(" order by g.addTime desc");
		// order by ascending add-time, DEFAULT
		else if (dateOrder == 1)
			condition.append(" order by g.addTime asc");
		
		pb.setTotalPage(pb.countTotalPage(pageSize, pb.getAllRow()));
		pb.setResult(goodDao.queryPage(pb.countOffset(pageSize, currentPage), pageSize, condition.toString()));
		pb.setNaviBar(PageUtil.drawNavi(pb.getCurrentPage(), pb.getTotalPage()));
		return pb;
	}
	
	public List<Good> searchGoodByTitle(String title, int maxResult) {
		return goodDao.search(title, "title", maxResult);
	}
	
	public PageBean getGoodPageList(final int pageSize, final int currentPage, final String conditions[][], final String sort[]) {
		boolean flag = false;
		boolean isMulti = false;
		final String SORT[] = {"desc", "asc"};		// 0 = descending, 1 = ascending
		StringBuilder condition = new StringBuilder();
		
		// iter every conditions' value
		int index = 0;
		while (index < conditions[1].length) {
			if (!conditions[1][index].equals("2")) {
				// add the syntax of " where"
				if (!flag) {
					condition.append(" where");
					flag = true;
				}
				// add the multi-condition query syntax
				if (isMulti)
					condition.append(" and");
				condition.append(" g." + conditions[0][index] + "=" + conditions[1][index]);
				isMulti = true;
			}
			index++;
		}
		
		PageBean pb = new PageBean();
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		pb.setAllRow(goodDao.getAllRow(condition.toString()));
		
		// iter data sort solution
		condition.append(" order by g." + sort[0] + " " + SORT[Integer.parseInt(sort[1])]);
		
		pb.setTotalPage(pb.countTotalPage(pageSize, pb.getAllRow()));
		pb.setResult(goodDao.queryPage(pb.countOffset(pageSize, currentPage), pageSize, condition.toString()));
		for (Good good : goodDao.queryPage(pb.countOffset(pageSize, currentPage), pageSize, condition.toString())) {
			System.out.println("#"+good.getId()+" - "+good.getTitle());
		}
		pb.setNaviBar(PageUtil.drawNavi(pb.getCurrentPage(), pb.getTotalPage()));
		return pb;
	}
	
}
