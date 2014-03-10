package com.ts.action.user;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.User;
import com.ts.service.MessageService;
import com.ts.service.UserService;

public class GetNotificationAmountAction extends ActionSupport {

	/**
	 * fetch notifications in every 10 seconds
	 */
	private static final long serialVersionUID = 5005035146118989749L;
	
	private String json;
	private UserService uService;
	private MessageService mService;
	
	public String execute() throws Exception {
		User user = uService.getCurrentUser();
		// 可以考虑加上未登录则删除cookie的方法
		if (user != null)
			json = "{amount:" + mService.getNotificationAmount(user.getId()) + "}";
		return "map";
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
