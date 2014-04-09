package com.ts.action.publisher;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Good;
import com.ts.entity.Publisher;
import com.ts.service.GoodService;
import com.ts.service.PublisherService;

public class EditGoodAction extends ActionSupport implements RequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1204889751360201710L;
	private PublisherService pService;
	private GoodService gService;
	private int gid;
	private Map<String, Object> requestMap;

	public String execute() throws Exception {
		Publisher publisher = pService.getCurrentPublisher();
		Good good = gService.getGoodById(gid);
		
		// good not existed
		if (good == null)
			return NONE;

		requestMap.put("good", good);
		
		// not the host
		// a completed good
		// authority restricted
		if (publisher == null || publisher.getId() != good.getPublisherId() || publisher.getIsRestricted() || good.getIsComplete()) {
			if ((publisher == null || publisher.getId() != good.getPublisherId()) && good.getIsComplete() && good.getIsAgree() && good.getIsAvailable() && !good.getIsDelete())
				requestMap.put("canBuy", "true");
			return "view";
		}
		// editable page
		return SUCCESS;
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

}
