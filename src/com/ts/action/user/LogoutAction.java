package com.ts.action.user;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

public class LogoutAction extends ActionSupport implements ServletRequestAware,ServletResponseAware {

	/**
	 * Logout
	 */
	private static final long serialVersionUID = 2971364830312645534L;
	private HttpSession session;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String redirectUrl;
	
	public String execute() throws Exception {
		// remove sessions
		session = request.getSession(false); 
		try {
			if(session != null) {
				// remove all sessions
				session.removeAttribute(com.ts.action.user.LoginAction.USER_SESSION);
				session.removeAttribute(com.ts.action.user.LoginAction.PUBLISHER_SESSION);
				System.out.println("remove sessions succeed");
			}
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				// remove all cookies
				for (Cookie cookie : cookies) {
					if (com.ts.util.CookieUtil.USER_COOKIE.equals(cookie.getName()) || 
						com.ts.util.CookieUtil.PUBLISHER_COOKIE.equals(cookie.getName())) {
						cookie.setValue("");
						cookie.setMaxAge(0);
						cookie.setPath("/");
						response.addCookie(cookie);
					}
				}
				System.out.println("remove cookies succeed");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		redirectUrl = "{redirectUrl:'" + redirectUrl + "'}";
		System.out.println(redirectUrl);
		return "map";
	}
	
	public void setSession(HttpSession session) {
		this.session = session;
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	
}
