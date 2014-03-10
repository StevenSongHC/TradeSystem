package com.ts.util;

import javax.servlet.http.Cookie;
import com.ts.entity.Publisher;
import com.ts.entity.User;

public class CookieUtil {
	
	public static final String USER_COOKIE = "user_cookie";
	public static final String PUBLISHER_COOKIE = "publisher_cookie";
	public static final int COOKIE_MAX_AGE = 60 * 60;
	
	// login()
	public static Cookie generateUserCookie(User user) {
		// cookie contains id, name, password
		Cookie cookie = new Cookie(USER_COOKIE, user.getId()+","+user.getName()+","+user.getPassword());
		cookie.setMaxAge(COOKIE_MAX_AGE);
		cookie.setPath("/");
		System.out.println("cookie: "+cookie.getValue());
		return cookie;
	}
	
	public static Cookie generatePublisherCookie(Publisher publisher) {
		// cookie contains pid, uid, pname
		Cookie cookie = new Cookie(PUBLISHER_COOKIE, publisher.getId()+","+publisher.getUid()+","+publisher.getName());
		cookie.setMaxAge(COOKIE_MAX_AGE);
		cookie.setPath("/");
		System.out.println("cookie: "+cookie.getValue());
		return cookie;
	}
	
}
