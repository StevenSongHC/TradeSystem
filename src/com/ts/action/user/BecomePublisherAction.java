package com.ts.action.user;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Publisher;
import com.ts.entity.User;
import com.ts.service.MessageService;
import com.ts.service.PublisherService;
import com.ts.service.UserService;

public class BecomePublisherAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1122155732039405605L;
	
	private String act;
	private String name;
	private String summary;
	private String contact;
	private UserService uService;
	private PublisherService pService;
	private MessageService mService;
	
	public String execute() throws Exception {
		User user = uService.getCurrentUser();
		if (user == null)
			return LOGIN;
		
		// check it now
		if (user.getIsRestricted())
			return "restricted";
		if (mService.haveSentUnreadBecomePublisherApply(user.getId())) {
			System.out.println(user.getName() + " already sent a become-publisher apply");
			return "deny";
		}
		
		else if (act.equals("try")) {
			System.out.println("A user try to become a publisher");
			return INPUT;
		}
		else if (act.equals("go")) {
			if (pService.validateName(name)) {	// name is good
				// add into temp publisher list
				Publisher publisher = new Publisher();
				publisher.setUid(user.getId());
				publisher.setName(name);
				publisher.setSummary(summary);
				publisher.setContact(contact);
				pService.addPublisher(publisher);
				// then let admin knows
				mService.sendBecomePublisherApply(user.getId());
				System.out.println("A new publisher-apply sent");	// log
			}
			else {
				System.out.println("该名称已存在,请另起名");
				this.addActionMessage("该名称已存在,请另起名");		// return message
				return INPUT;
			}
		}
		return SUCCESS;
	}

	public String getAct() {
		return act;
	}
	public void setAct(String act) {
		this.act = act;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
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
	public MessageService getmService() {
		return mService;
	}
	public void setmService(MessageService mService) {
		this.mService = mService;
	}

}
