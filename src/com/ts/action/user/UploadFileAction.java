package com.ts.action.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.User;
import com.ts.service.UserService;

public class UploadFileAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7650583492784562476L;
	
	private UserService uService;

	private String type;
	private File file;
	private String fileType;
	private String json;

	public String execute() throws Exception {
		User user = uService.getCurrentUser();
		if (user != null) {
			// storage name in database
			String saveName = null;
			// file storage path
			String savePath = null;
			
			if (type.equals("userPortrait")) {
				saveName = "images/portrait/u_" + user.getId() + "_" + Calendar.getInstance().getTimeInMillis() + fileType;
				savePath = ServletActionContext.getServletContext().getRealPath("") + "/" + saveName;
				
				if (file != null) {
					FileInputStream fis = new FileInputStream(file);
					FileOutputStream fos = new FileOutputStream(savePath);
					byte[] b = new byte[1024];
					int len = 0;
					while ((len = fis.read(b)) > 0)
						fos.write(b, 0, len);
					
					fos.flush();
					fos.close();
					fis.close();
				}
			}
			json = "{'msg' : '上传成功', 'picPath' : '" + saveName + "'}";
		}
		else
			json = "{'msg' : '上传失败，请重新登陆再做尝试'}";
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return "map";
	}
	public UserService getuService() {
		return uService;
	}
	public void setuService(UserService uService) {
		this.uService = uService;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}

}
