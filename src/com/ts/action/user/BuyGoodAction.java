package com.ts.action.user;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Good;
import com.ts.entity.Message;
import com.ts.entity.Order;
import com.ts.entity.Publisher;
import com.ts.entity.User;
import com.ts.service.GoodService;
import com.ts.service.MessageService;
import com.ts.service.OrderService;
import com.ts.service.PublisherService;
import com.ts.service.UserService;

public class BuyGoodAction extends ActionSupport {

	/**
	 * give order
	 */
	private static final long serialVersionUID = 8628562802737700578L;
	
	private UserService uService;
	private PublisherService pService;
	private GoodService gService;
	private OrderService oService;
	private MessageService mService;
	
	private int gid;
	private String json;

	@Override
	public String execute() throws Exception {
		User user = uService.getCurrentUser();
		Good good = gService.getGoodById(gid);
		if (user == null)
			json = "{'status' : 0}";		// need to login
		else if (good == null)
			json = "{'status' : -2}";		// wrong item
		else if (!uService.checkAuth(user.getId(), "auth_give_order"))
			json = "{'status' : -3}";		// got no auth
		else {
			// save order
			Publisher publisher = pService.getPublisherByPid(good.getPublisherId());
			Order order = new Order();
			order.setBuyerId(user.getId());
			order.setGoodId(gid);
			order.setSellerId(publisher.getUid());
			// send message
			Message message = new Message();
			message.setSenderUid(user.getId());
			message.setReceiverUid(publisher.getUid());
			message.setNoticeType(1);
			message.setWord(user.getName() + " 已经购买了你的产品  " + good.getTitle());
			// and add one buy count to the good
			int buyerCount = good.getBuyerCount();
			good.setBuyerCount(buyerCount + 1);
			
			if (oService.newOrder(order) && mService.sendMessage(message) && gService.updateGood(good))
				json = "{'status' : 1}";
			else
				json = "{'status' : -1}";	// hit error
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
	public GoodService getgService() {
		return gService;
	}
	public void setgService(GoodService gService) {
		this.gService = gService;
	}
	public OrderService getoService() {
		return oService;
	}
	public void setoService(OrderService oService) {
		this.oService = oService;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public MessageService getmService() {
		return mService;
	}
	public void setmService(MessageService mService) {
		this.mService = mService;
	}

}
