package com.ts.action.admin;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Good;
import com.ts.entity.Message;
import com.ts.entity.Publisher;
import com.ts.entity.User;
import com.ts.service.GoodService;
import com.ts.service.MessageService;
import com.ts.service.PublisherService;
import com.ts.service.UserService;

public class ToggleStatusAction extends ActionSupport {

	/**
	 * 用于管理界面的字段状态改变
	 */
	private static final long serialVersionUID = 7274206184904736967L;
	
	private UserService uService;
	private PublisherService pService;
	private GoodService gService;
	private MessageService mService;
	
	private String statusCoor;		// coordinate of the column to be changed, contains entity type, id, column and status
	private String json;
	
	public String execute() throws Exception {
		
		Publisher admin = pService.getCurrentPublisher();
		if (admin != null && pService.isAdmin()) {
			int val = 1;	// related authority value
			// decode the statusCoor
			String type = statusCoor.split(",")[0];
			int id = Integer.parseInt(statusCoor.split(",")[1]);
			int column = Integer.parseInt(statusCoor.split(",")[2]);
			boolean status = (statusCoor.split(",")[3].equals("1"));
			// USER TABLE
			if (type.equals("user")) {
				User mice = uService.getUserById(id);
				switch (column) {
					case 0:
						if (uService.checkAuth(admin.getUid(), "auth_suspend_any_user"))
							mice.setIsSuspend(status);
						break;
					case 1:
						if (uService.checkAuth(admin.getUid(), "auth_delete_any_user"))
							mice.setIsDelete(status);
						break;
					case 2:
						if (uService.checkAuth(admin.getUid(), "auth_edit_any_user"))
							mice.setIsRestricted(status);
						break;
					default:
						System.out.println("toggle status failed");		// log it
				}
				if (status || mice.getIsSuspend() || mice.getIsDelete() || mice.getIsRestricted())	// set auth_give_order
					val = 0;
				// act
				if (uService.updateUser(mice) && 
						uService.modifyAuth(mice.getId(), "auth_give_order", val))		// any disable returns giving order restriction
					json = "{isSucceed:true}";
				else
					json = "{isSucceed:false}";
			}
			// PUBLISHER TABLE
			else if(type.equals("publisher")) {
				Publisher tommy = pService.getPublisherByPid(id);
				switch (column) {
				case 0:
					if (uService.checkAuth(admin.getUid(), "auth_agree_publisher"))
						tommy.setIsActivate(status);
					break;
				case 1:		// restricted(status=1) = all authorities of being a publisher vanished
					if (uService.checkAuth(admin.getUid(), "auth_edit_any_user"))
						tommy.setIsRestricted(status);
					uService.modifyAuth(tommy.getUid(), "auth_edit_good", (status? 0 : 1));
					uService.modifyAuth(tommy.getUid(), "auth_suspend_good", (status? 0 : 1));
					uService.modifyAuth(tommy.getUid(), "auth_delete_good", (status? 0 : 1));
					break;
				default:
					System.out.println("toggle status failed");		// log it
			}
				// column = 0 and disable -> bad || column = 1 and enable -> bad...
				System.out.println("column " + column + "    status " + status);
				if (((column == 0 && status) || (column == 1 && !status)) && tommy.getIsActivate() && !tommy.getIsRestricted())
					val = 1;
				else
					val = 0;
				if ((column == 0 ||
						(!uService.checkAuth(tommy.getUid(), "auth_edit_good") == status &&
						!uService.checkAuth(tommy.getUid(), "auth_suspend_good") == status &&
						!uService.checkAuth(tommy.getUid(), "auth_delete_good") == status)) &&
						pService.updatePublisher(tommy) && 
						uService.modifyAuth(tommy.getUid(), "auth_publish_good", val))
					json = "{isSucceed:true}";
				else
					json = "{isSucceed:false}";
			}
			// GOOD TABLE
			else if (type.equals("good")) {
				Good potter = gService.getGoodById(id);
				switch (column) {
				case 0:
					if (uService.checkAuth(admin.getUid(), "auth_edit_good"))
						potter.setIsComplete(status);
					break;
				case 1:
					if (uService.checkAuth(admin.getUid(), "auth_edit_good"))
						potter.setIsAgree(status);
					break;
				case 2:
					if (uService.checkAuth(admin.getUid(), "auth_suspend_good"))
						potter.setIsAvailable(status);
					break;
				case 3:
					if (uService.checkAuth(admin.getUid(), "auth_delete_good"))
						potter.setIsDelete(status);
				default:
					System.out.println("toggle status failed");		// log it
			}
				
			// notify publisher for their good's change of status
			// good -> pid -> Publisher -> uid -> User -> id
			Message msg = new Message();
			String action = "";
			if (column == 2) {
				action = status? "恢复上线" : "下线";
				msg.setSenderUid(admin.getUid());
				msg.setReceiverUid(uService.getUserById((pService.getPublisherByPid(potter.getPublisherId()).getUid())).getId());
				msg.setWord("管理员" + action + "了您的商品 " + potter.getTitle() + ",商品号为 " + potter.getId());
				msg.setNoticeType(3);
			}
			else if (column == 3) {
				action = status? "删除" : "撤销删除";
				msg.setSenderUid(admin.getUid());
				msg.setReceiverUid(uService.getUserById((pService.getPublisherByPid(potter.getPublisherId()).getUid())).getId());
				msg.setWord("管理员" + action + "了您的商品 " + potter.getTitle() + ",商品号为 " + potter.getId());
				msg.setNoticeType(4);
			}
				
			if (gService.updateGood(potter) && mService.sendMessage(msg)) {
				json = "{isSucceed:true}";
				
			}
			else
				json = "{isSucceed:false}";
			}
		}
		else
			System.out.println("not the admin");
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
	public GoodService getgService() {
		return gService;
	}
	public void setgService(GoodService gService) {
		this.gService = gService;
	}
	public MessageService getmService() {
		return mService;
	}
	public void setmService(MessageService mService) {
		this.mService = mService;
	}
	public String getStatusCoor() {
		return statusCoor;
	}
	public void setStatusCoor(String statusCoor) {
		this.statusCoor = statusCoor;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}

}
