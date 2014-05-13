package com.ts.action.user;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Message;
import com.ts.entity.Publisher;
import com.ts.entity.User;
import com.ts.service.MessageService;
import com.ts.service.PublisherService;
import com.ts.service.UserService;

public class SendMessageAction extends ActionSupport {

	/**
	 * 发送自定义信息
	 */
	private static final long serialVersionUID = 313465946601328065L;
	
	private UserService uService;
	private PublisherService pService;
	private MessageService mService;
	
	private String receiverType;
	private String receiverCode;	// User -> .name , Publisher -> contact
	private String content;
	private String json;
	
	public String execute() throws Exception {
		User sender = uService.getCurrentUser();
		if (sender == null)
			json = "{msg:'未检测用户登陆'}";
		else if (content.equals(""))
			json = "{msg:'信息内容不能为空'}";
		else {
			Message msg = new Message();
			if (receiverType.equals("user")) {
				User receiver = uService.getUserByName(receiverCode);
				if (receiver == null)
					json = "{msg:'该用户名称不存在'}";
				else {
					msg.setSenderUid(sender.getId());
					msg.setReceiverUid(receiver.getId());
					msg.setNoticeType(0);
					msg.setWord(content);
					if (mService.sendMessage(msg))
						json = "{flag:true}";
					else
						json = "{msg:'发信失败!'}";
				}
			}
			else if (receiverType.equals("publisher")) {
				Publisher receiver = pService.getPublisherByContact(receiverCode);
				if (receiver == null)
					json = "{msg:'错误的联系方式'}";
				else {
					msg.setSenderUid(sender.getId());
					msg.setReceiverUid(receiver.getUid());
					msg.setNoticeType(0);
					msg.setWord(content);
					if (mService.sendMessage(msg))
						json = "{flag:true}";
					else
						json = "{msg:'发信失败!'}";
				}
			}
			else if (receiverType.equals("admin")) {
				msg.setSenderUid(sender.getId());
				msg.setReceiverUid(pService.getAdminUid());
				msg.setNoticeType(0);
				msg.setWord(content);
				if (mService.sendMessage(msg))
					json = "{flag:true}";
				else
					json = "{msg:'发信失败!'}";
			}
		}
		return "map";
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
	public String getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	public String getReceiverCode() {
		return receiverCode;
	}
	public void setReceiverCode(String receiverCode) {
		this.receiverCode = receiverCode;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}

}
