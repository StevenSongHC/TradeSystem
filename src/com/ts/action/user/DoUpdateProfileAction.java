package com.ts.action.user;

import java.io.File;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.User;
import com.ts.service.UserService;

public class DoUpdateProfileAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7906530203527956954L;
	
	private UserService uService;
	
	private String summary;
	private String password;
	private String portrait;
	private String json;

	public String execute() throws Exception {
		User user = uService.getCurrentUser();
		
		if (user != null) {
			user.setSummary(summary);
			user.setPassword(password);
			
			// delete the original portrait
			if (!portrait.equals(user.getPortrait())) {
				if (!user.getPortrait().equals("images/portrait/default.png")) {
					if (!new File(ServletActionContext.getServletContext().getRealPath("")+"/"+user.getPortrait()).delete()) {
						json = "{'msg' : '图片上传失败'}";
						return "map";
					}
				}
			}
			// update the new portrait
			user.setPortrait(portrait);
		}
		if (uService.updateUser(user)) {
			json = "{'msg' : '保存成功'}";
		}
		return "map";
	}

	public UserService getuService() {
		return uService;
	}
	public void setuService(UserService uService) {
		this.uService = uService;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	
}
