package com.ts.action.publisher;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Publisher;
import com.ts.service.PublisherService;

public class AddGoodAction extends ActionSupport {

	/**
	 * link in the add-good page
	 */
	private static final long serialVersionUID = 6979702910252841864L;
	
	private PublisherService pService;
	
	public String execute() throws Exception {
		Publisher publisher = pService.getCurrentPublisher();
		// not a publisher, get restricted or no activated
		if (publisher == null || publisher.getIsRestricted() || !publisher.getIsActivate())
			return "restricted";
		
		return SUCCESS;
	}
	public PublisherService getpService() {
		return pService;
	}
	public void setpService(PublisherService pService) {
		this.pService = pService;
	}

}
