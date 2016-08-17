package com.asiainfo.biapp.framework.web.view;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.util.UriUtils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 统一的下载数据视图 处理
 * @author hanjn
 *
 */
public abstract class AbstractDownloadView extends AbstractView {

	/** The content type for response */
	public String getContentType() {
		return "application/octet-stream";
	}
	
	/** The down file name for an Excel response */
	private String fileName ;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/** The down file suffix such as .pdf*/
	private String extension;
	public String getExtension() {
		return extension;
	}
	
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * 
	 * @return 获得
	 * @param HttpServletRequest request
	 * @throws  
	 */
	public String getContentDispositionValue(HttpServletRequest request) throws UnsupportedEncodingException{
		String contentDispositionValue=null;
		
		UserAgent userAgent=UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		Browser browserGroup=userAgent.getBrowser().getGroup();
		String fileName=getFileName()+getExtension();
		//java.net.URLEncode 会将空格转化为+而在浏览器中需要转化为 、%20才显示空格 
		String fileNameEncode=UriUtils.encodePath(fileName, "UTF8");
		fileName=new String(fileName.getBytes("UTF-8"),"ISO8859-1");

		if(browserGroup==Browser.IE){
			if(userAgent.getBrowser()==Browser.IE8){
				return "attachment; filename=\""+fileNameEncode+"\"";
			}
		}else if(browserGroup==Browser.CHROME){
		}else if(browserGroup==Browser.SAFARI){
		}else if(browserGroup==Browser.FIREFOX){
		}
		contentDispositionValue=String.format("attachment; filename=\"%s\"; filename*=UTF-8''%s",fileName,fileNameEncode);
		return contentDispositionValue;
	}
	
	@Override
	protected void prepareResponse(HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(getContentType());
		//todo 设置缓存时间和过期时间 可以在xml配置文件中设置
		
		//设置下载文件的名称  支持中文名称
		try {
			response.setHeader("Content-Disposition", getContentDispositionValue(request));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		super.prepareResponse(request, response);
	}
	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	}
}