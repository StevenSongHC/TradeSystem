package com.ts.action.admin;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Publisher;
import com.ts.service.MessageService;
import com.ts.service.PublisherService;

public class UpgradePublisherAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1264291694977032955L;
	
	private boolean upgrade;
	private int uid;
	private PublisherService pService;
	private MessageService mService;

	public String execute() throws Exception {
		/*
		 * tables: user publisher message notification
		 * steps:
		 * 1. update user's isPublisher
		 * 2. 
		 */
		Publisher admin = pService.getCurrentPublisher();
		if (admin == null || !pService.isAdmin())
			return "deny";
		
		if (upgrade) {
			// register
			if (pService.registerPublisher(uid)) {
				// update message and notifications
				if (!mService.replyBecomePublisherApply(uid, true))
					return "fail";
			}
			else
				return "fail";
		}
		else {
			// delete
			if (pService.cancelPublisher(uid)) {
				// update message and notification
				if (!mService.replyBecomePublisherApply(uid, false))
					return "fail";
			}
			else
				return "fail";
		}
		return SUCCESS;
	}

	public boolean getUpgrade() {
		return upgrade;
	}
	public void setUpgrade(boolean upgrade) {
		this.upgrade = upgrade;
	}
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
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
