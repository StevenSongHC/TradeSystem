package com.ts.action.publisher;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Good;
import com.ts.entity.Publisher;
import com.ts.entity.User;
import com.ts.service.GoodService;
import com.ts.service.PublisherService;
import com.ts.service.UserService;

public class EditGoodAction extends ActionSupport implements RequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1204889751360201710L;
	private UserService uService;
	private PublisherService pService;
	private GoodService gService;
	private int gid;
	private Map<String, Object> requestMap;
	private String msg;

	public String execute() throws Exception {
		Publisher publisher = pService.getCurrentPublisher();
		User user = uService.getCurrentUser();
		Good good = gService.getGoodById(gid);
		
		// good does not existed
		if (good == null) {
			msg = "该商品不存在";
			return "redirect";
		}
		
		// inspect the good directly if ur admin
		if (publisher != null && publisher.getIsAdmin() == 2779) {
			System.out.println("admin");
			requestMap.put("good", good);
			return "view";
		}
		
		// ain't the host
		if (user == null || publisher == null || (good.getPublisherId() != publisher.getId())) {
			System.out.println("ain't the host");
			// delete status
			if (good.getIsDelete()) {
				msg = "该商品不存在";
				return "redirect";
			}
			// incomplete status
			if (!good.getIsComplete()) {
				msg = "该商品还未可用";
				return "redirect";
			}
			// didn't passed
			if (!good.getIsAgree()) {
				msg = "该商品未通过审核";
				return "redirect";
			}
			// unavailable status
			if (!good.getIsAvailable()) {
				msg = "该商品暂不可用";
				return "redirect";
			}
			// inspect the good
			requestMap.put("good", good);
			requestMap.put("canBuy", "true");
			return "view";
		}
		
		// host
		if (user != null || publisher != null || (good.getPublisherId() == publisher.getId())) {
			requestMap.put("good", good);
			// good completed or got no authority to edit good
			if (good.getIsComplete() || !publisher.getIsActivate()) {
				if (good.getIsComplete())
					msg = "已完成的商品";
				else
					msg = "发布者账号权限未激活";
				return "view";
			}
			if (good.getIsDelete())
				msg = "该商品为删除标记";
			else if (!good.getIsAvailable())
				msg = "该商品现未可用";
			
		}
		
		return SUCCESS;
		
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
	public void setRequest(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
