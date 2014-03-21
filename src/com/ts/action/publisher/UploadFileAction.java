package com.ts.action.publisher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.ts.entity.Publisher;
import com.ts.service.GoodService;
import com.ts.service.PublisherService;

public class UploadFileAction extends ActionSupport {

	/**
	 * upload
	 */
	private static final long serialVersionUID = -1942622812184727878L;
	
	private String type;
	private File file;
	private String fileType;
	private String json;
	private PublisherService pService;
	private GoodService gService;
	
	
	public String execute() throws Exception {
		Publisher publisher = pService.getCurrentPublisher();
		if (publisher != null) {
			// storage name in database
			String saveName = null;
			// file storage path
			String savePath = null;
			// current time
			long time = Calendar.getInstance().getTimeInMillis();
			
			if (type.equals("goodPic")) {
				saveName = "images/good/" + publisher.getId() + "_" + time + fileType;
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

}
