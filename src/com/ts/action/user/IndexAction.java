package com.ts.action.user;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Good;
import com.ts.service.GoodService;

public class IndexAction extends ActionSupport implements RequestAware {

	/**
	 * index page contains good-list(new)
	 */
	private static final long serialVersionUID = -5408899195231372773L;

	private Map<String, Object> requestMap;
	private GoodService gService;

	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		String hotGoodConditions[][] = {{"isComplete", "isAgree", "isAvailable", "isDelete"}, 
				   {"1", "1", "1", "0"}};
		String latestGoodConditions[][] = {{"isComplete", "isAgree", "isAvailable", "isDelete"}, 
										   {"1", "1", "1", "0"}};
		String hotGoodSort[] = {"buyerCount", "0"};
		String latestGoodSort[] = {"addTime", "0"};
		List<Good> hotGoodList = (List<Good>) gService.getGoodPageList(8, 1, hotGoodConditions, hotGoodSort).getResult();			// 热门商品，买的人多就热门
		List<Good> latestGoodList = (List<Good>) gService.getGoodPageList(8, 1, latestGoodConditions, latestGoodSort).getResult();		// 最新商品，依上线时间

		requestMap.put("hotGood", hotGoodList);
		requestMap.put("latestGood", latestGoodList);
		return SUCCESS;
	}

	public void setRequest(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}
	public GoodService getgService() {
		return gService;
	}
	public void setgService(GoodService gService) {
		this.gService = gService;
	}

}
