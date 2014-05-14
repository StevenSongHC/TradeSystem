package com.ts.action.user;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Publisher;
import com.ts.entity.User;
import com.ts.service.PublisherService;
import com.ts.service.UserService;
import com.ts.util.CookieUtil;
import com.ts.util.MD5Util;

public class LoginAction extends ActionSupport implements ServletResponseAware, SessionAware {

	/**
	 * Ajax Login
	 * @param 		account & password
	 * @return 		msg & status
	 */
	private static final long serialVersionUID = 7019959282825482237L;
	public static final String USER_SESSION = "USER_SESSION";
	public static final String PUBLISHER_SESSION = "PUBLISHER_SESSION";
	
	private String account;
	private String password;
	private int status;
	private String msg;
	private String json;
	private HttpServletResponse response;
    private Map<String, Object> session;
	private UserService uService;
	public PublisherService pService;
	
	public String execute() throws Exception {
		try {
			User user = uService.getUserByAccount(account);
			if (user == null || user.getIsDelete()) {									// Invalid account
				status = -2;
				msg = "无此用户,请检查,或注册学号";
			}
			else if (!MD5Util.authenticateCode(user.getPassword(), password)) {	// Wrong password
				status = -1;
				msg = account;
			}
			else {												// Login succeed
				System.out.println("加密密码: " + MD5Util.encryptCode(user.getPassword()));
				// add user cookie
				response.addCookie(CookieUtil.generateUserCookie(user));
				// add use session
				session.put(USER_SESSION, user);
				status = 1;
				
				if (user.getIsRestricted())
					System.out.println("have restriction");
				if (user.getIsPublisher()) {
					Publisher publisher = pService.getPublisherByUid(user.getId());
					// add publisher cookie
					response.addCookie(CookieUtil.generatePublisherCookie(publisher));
					// add publisher session
					session.put(PUBLISHER_SESSION, publisher);
				}
			}
		} catch(RuntimeException e) {							// Unknown error
			System.out.println("login error hit!");
			e.printStackTrace();
			status = 0;
			msg = "登陆出错,请重试,或待会再来";
		}
		
		// return
		json = "{status:" + status + ",msg:'" + msg + "'}";
		
		return "map";
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
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
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	public PublisherService getpService() {
		return pService;
	}
	public void setpService(PublisherService pService) {
		this.pService = pService;
	}
	
}
