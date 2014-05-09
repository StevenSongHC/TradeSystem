package com.ts.action.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Message;
import com.ts.service.MessageService;
import com.ts.service.UserService;

public class GetMessageListAction extends ActionSupport implements RequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1009691279911189301L;
	
	private String json;
	private UserService uService;
	private MessageService mService;
	private Map<String, Object> requestMap;
	
	public String execute() throws Exception {
		if (uService.getCurrentUser() == null)
			return "deny";
		
		// fetch user's messages with unread status
		List<Map<String, Object>> unreadList = new ArrayList<Map<String, Object>>();
		
		for (Message msg : mService.fetchUnreadMessageList(uService.getCurrentUser().getId())) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("word", msg.getWord());
			item.put("senderName", uService.getUserById(msg.getSenderUid()).getName());
			item.put("senderUid", msg.getSenderUid());
			item.put("time", msg.getTime().toString());
			item.put("noticeType", msg.getNoticeType());
			item.put("mid", msg.getId());
			
			unreadList.add(item);
		}
		
		// read
		List<Map<String, Object>> readList = new ArrayList<Map<String, Object>>();
		mService.fetchReadMessageList(uService.getCurrentUser().getId());
		for (Message msg : mService.fetchReadMessageList(uService.getCurrentUser().getId())) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("word", msg.getWord());
			item.put("senderName", uService.getUserById(msg.getSenderUid()).getName());
			item.put("senderUid", msg.getReceiverUid());
			item.put("receiverUid", msg.getReceiverUid());
			item.put("time", msg.getTime().toString());
			item.put("noticeType", msg.getNoticeType());
			
			readList.add(item);
		}
		
		// return
		requestMap.put("unreadList", unreadList);
		requestMap.put("readList", readList);
		
		return SUCCESS;
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
	public void setRequest(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}
	
}
