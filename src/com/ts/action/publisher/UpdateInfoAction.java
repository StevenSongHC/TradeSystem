package com.ts.action.publisher;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Publisher;
import com.ts.service.PublisherService;

public class UpdateInfoAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4480702332488771289L;
	
	private PublisherService pService;
	
	private int pid;
	private String contact;
	private String summary;
	private String json;

	public String execute() throws Exception {
		Publisher publisher = pService.getCurrentPublisher();
		System.out.println(pid+"_"+contact+"_"+summary);
		json = "{status:false}";
		if (publisher != null) {
			if (publisher.getId() == pid) {
				publisher.setContact(contact);
				publisher.setSummary(summary);
				if (pService.updatePublisher(publisher))
					json = "{status:true}";
			}
		}
		return "map";
	}

	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public PublisherService getpService() {
		return pService;
	}
	public void setpService(PublisherService pService) {
		this.pService = pService;
	}

}
