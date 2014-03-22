package com.ts.action.publisher;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Good;
import com.ts.entity.Publisher;
import com.ts.service.GoodService;
import com.ts.service.PublisherService;
import com.ts.service.UserService;

public class PublishGoodAction extends ActionSupport {

	/**
	 * 提交新商品
	 */
	private static final long serialVersionUID = -669322580267914743L;
	
	private UserService uService;
	private PublisherService pService;
	private GoodService gService;
	private int gid;
	private String title;
	private String pic;
	private double price;
	private String desc;
	
	/*
	 * 先验证是否为新增的商品，若是则先实例化
	 * setIsComplete = true
	 * gService.addGood()
	 * set a message to the admin
	 */
	public String execute() throws Exception {
		Publisher publisher = pService.getCurrentPublisher();
		System.out.println(gid+"_"+title+"_"+pic+"_"+price+"_"+desc);
		if (publisher == null)
			return LOGIN;
		
		Good good = new Good();
		good.setTitle(title);
		good.setPic(pic);
		good.setPrice(price);
		good.setDesc(desc);
		good.setIsComplete(true);
		// not necessary. Why, cuz the table still old
		good.setIsAgree(true);
		
		boolean isSuccess = false;
		
		// direct from addGood page, with default picPath
		if (gid == 0) {
			isSuccess = gService.addGood(good);
		}
		// from editGood page
		else {
			good.setId(gid);
			isSuccess = gService.updateGood(good);
		}
		
		if (isSuccess)
			return SUCCESS;
		
		return ERROR;
	}
	
	public UserService getuService() {
		return uService;
	}
	public void setuService(UserService uService) {
		this.uService = uService;
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
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
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

}
