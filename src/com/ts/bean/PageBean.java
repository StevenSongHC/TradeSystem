package com.ts.bean;

import java.util.List;

/**
 * page navigation bean
 */
public class PageBean {
	
	private List<?> result;				// 某一页的数据列表
	
	private int allRow;					// 总记录数
	private int pageSize;				// 每页记录数
	private int totalPage;				// 总页数
	private int currentPage;			// 当前第几页
	
	private String naviBar;				// 页面导航栏
	private String dataType;			// 数据类型
	
	public List<?> getResult() {
		return result;
	}
	public void setResult(List<?> result) {
		this.result = result;
	}
	public int getAllRow() {
		return allRow;
	}
	public void setAllRow(int allRow) {
		this.allRow = allRow;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public String getNaviBar() {
		return naviBar;
	}
	public void setNaviBar(String naviBar) {
		this.naviBar = naviBar;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	/**
	 * RT
	 * @param pageSize
	 * @param currentPage
	 * @return total page
	 */
	public int countTotalPage(final int pageSize, final int allRow) {
		final int totalPage = allRow % pageSize == 0 ? allRow / pageSize : allRow / pageSize + 1;
		return totalPage;
	}
	
	/**
	 * get current page's first data
	 * @param pageSize
	 * @param currentPage
	 * @return offset
	 */
	public int countOffset(final int pageSize, final int currentPage) {
		final int offset = pageSize * (currentPage - 1);
		return offset;
	}
	
	/**
	 * RT. default 1
	 * @param page
	 * @return current page
	 */
	/*public static int countCurrentPage(final int page) {
		final int currentPage = (page == 0 ? 1 : page);
		return currentPage;
	}*/
	
}
