package com.ts.action.user;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.User;
import com.ts.service.UserService;
import com.ts.util.CookieUtil;

public class RegisterAction extends ActionSupport implements ServletResponseAware, SessionAware {

	/**
	 * Register
	 */
	private static final long serialVersionUID = 5524818628325619432L;
	
	private String name;
	private String account;
	private String password;
	private HttpServletResponse response;
    private Map<String, Object> session;
	private UserService uService;

	public String execute() throws Exception {
		User user = new User();
		user.setName(name);
		user.setAccount(account);
		user.setPassword(password);
		int code = uService.addUser(user);
		System.out.println("save code: " + code);
		switch (code) {
			case -2: {	// NAME CONFLICT
				this.addActionError("改昵称已被使用");
				//this.addFieldError(name, "改昵称已被使用");
				break;
			}
			case -1: {	// ACCOUNT CONFLICT
				this.addActionError("改学号已被注册");
				//this.addFieldError(account, "改学号已被注册");
				break;
			}
			case 0: {	// INSERT DATA FAILED
				this.addActionError("Opps,注册失败,请重新尝试,或过段时间再来!");
				break;
			}
			case 1: {	// SUCCESS
				// add user cookie
				response.addCookie(CookieUtil.generateUserCookie(user));
				// add use session
				session.put(com.ts.action.user.LoginAction.USER_SESSION, user);
				return SUCCESS;
			}
			default: {	// UNKNOWN ERROR
				this.addActionError("抱歉,未知错误,请重新尝试,或过段时间再来!");
				System.out.println("unknown error hit!");
			}
		}
		return INPUT;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	public UserService getuService() {
		return uService;
	}
	public void setuService(UserService uService) {
		this.uService = uService;
	}

}
