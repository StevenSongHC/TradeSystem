package com.ts.action.publisher;

import java.io.File;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Good;
import com.ts.entity.Publisher;
import com.ts.service.GoodService;
import com.ts.service.PublisherService;

public class SaveGoodAction extends ActionSupport {

	/**
	 * 动态保存商品页面
	 */
	private static final long serialVersionUID = 886000961360942776L;
	
	private PublisherService pService;
	private GoodService gService;
	private int gid;
	private String title;
	private String pic;
	private double price;
	private String desc;
	private String json;

	public String execute() throws Exception {
		Publisher publisher = pService.getCurrentPublisher();
		if (publisher != null) {
			Good good = gService.getGoodById(gid);
			good.setTitle(title);
			good.setPrice(price);
			good.setDesc(desc);
			// new pic updated
			// delete the old one
			if (!pic.equals(good.getPic())) {
				if (new File(ServletActionContext.getServletContext().getRealPath("")+"/"+good.getPic()).delete())
					good.setPic(pic);
				else {
					json = "{'error' : '图片上传失败'}";
					return "map";
				}
			}
			if (gService.updateGood(good))
				json = "{'msg' : '保存成功'}";
		}
		return "map";
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
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}

}
