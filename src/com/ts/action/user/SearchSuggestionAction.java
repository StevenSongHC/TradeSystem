package com.ts.action.user;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Good;
import com.ts.service.GoodService;

public class SearchSuggestionAction extends ActionSupport {

	/**
	 * 搜索建议功能
	 */
	private static final long serialVersionUID = 9102271668816094959L;
	
	private GoodService gService;
	private String keyword;
	private String json;
	
	public String execute() throws Exception {
		List<Good> list = new ArrayList<Good>();
		List<String> result = new ArrayList<String>();
		list = gService.searchGoodByTitle(keyword, 5);
		for (Good good : list) {
			result.add(good.getTitle());
		}
		json = JSONArray.fromObject(result).toString();
		return "map";
	}
	
	public GoodService getgService() {
		return gService;
	}
	public void setgService(GoodService gService) {
		this.gService = gService;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}

}
