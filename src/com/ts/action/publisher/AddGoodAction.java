package com.ts.action.publisher;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Good;
import com.ts.entity.Publisher;
import com.ts.service.GoodService;
import com.ts.service.PublisherService;

public class AddGoodAction extends ActionSupport {

	/**
	 * 保存新商品，上传新图片或点击保存按钮即触发，放回新商品编辑页面
	 */
	private static final long serialVersionUID = 6979702910252841864L;
	
	private PublisherService pService;
	private GoodService gService;
	private String title;
	private String pic;
	private double price;
	private String desc;
	private int gid;
	
	public String execute() throws Exception {
		Publisher publisher = pService.getCurrentPublisher();
		// not a publisher, get restricted or no activated
		if (publisher == null || publisher.getIsRestricted() || !publisher.getIsActivate())
			return "restricted";
		// init
		if (pic == null)
			return INPUT;
		else {
			Good good = new Good();
			good.setPublisherId(publisher.getId());
			good.setTitle(title);
			good.setPic(pic);
			good.setPrice(price);
			good.setDesc(desc);
			if (gService.addGood(good)) {
				gid = good.getId();
				return SUCCESS;
			}
		}
		
		return ERROR;
	}
	public PublisherService getpService() {
		return pService;
	}
	public void setpService(PublisherService pService) {
		this.pService = pService;
	}
	public GoodService getgService() {
		return gService;
	}
	public void setgService(GoodService gService) {
		this.gService = gService;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}

}
