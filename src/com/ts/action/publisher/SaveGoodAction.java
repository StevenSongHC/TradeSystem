package com.ts.action.publisher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Good;
import com.ts.entity.Publisher;
import com.ts.service.GoodService;
import com.ts.service.PublisherService;
import com.ts.util.CookieUtil;

public class SaveGoodAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {

	/**
	 * 动态保存商品页面
	 */
	private static final long serialVersionUID = 886000961360942776L;
	
	private PublisherService pService;
	private GoodService gService;
	private String title;
	private String pic;
	private double price;
	private String desc;
	private String json;
	private HttpServletResponse response;
	private HttpServletRequest request;

	public String execute() throws Exception {
		Publisher publisher = pService.getCurrentPublisher();
		Good good = new Good();
		good.setPublisherId(publisher.getId());
		good.setTitle(title);
		good.setPic(pic);
		good.setPrice(price);
		good.setDesc(desc);
		if (publisher != null) {
			// if it is a new good
			if (!CookieUtil.haveSuchCookie(request, com.ts.util.CookieUtil.GOOD_COOKIE)) {
				if (gService.addGood(good)) {
					response.addCookie(CookieUtil.generateGoodCookie(good));
					json = "{'flag' : true}";
				}
				else
					json = "{'flag' : false}";
			}
			// if it is an existing good (validate by good_cookie)
			else {
				// get the gid from cookie
				good.setId(Integer.parseInt(CookieUtil.readCookieValue(request, com.ts.util.CookieUtil.GOOD_COOKIE).split(",")[0]));
				if (gService.updateGood(good))
					json = "{'flag' : true}";
				else
					json = "{'flag' : false}";
			}
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
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

}
