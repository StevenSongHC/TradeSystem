package com.ts.action.user;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.User;
import com.ts.service.MessageService;
import com.ts.service.UserService;

public class MuteMessageAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7645397767887225931L;
	
	private int mid;
	private int noticeType;
	private String json;
	private UserService uService;
	private MessageService mService;
	
	public String execute() throws Exception {
		User user = uService.getCurrentUser();
		if (user == null || !mService.readMessage(mid, user.getId(), noticeType))
			json = "{flag:false}";
		else
			json = "{flag:true}";
		return "map";
	}

	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public int getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(int noticeType) {
		this.noticeType = noticeType;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public UserService getuService() {
		return uService;
	}
	public void setuService(UserService uService) {
		this.uService = uService;
	}
	public MessageService getmService() {
		return mService;
	}
	public void setmService(MessageService mService) {
		this.mService = mService;
	}

}
