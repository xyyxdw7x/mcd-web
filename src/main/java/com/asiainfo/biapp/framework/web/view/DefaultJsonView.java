package com.asiainfo.biapp.framework.web.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;

public class DefaultJsonView extends MappingJackson2JsonView {

	/**
	 * 处理IE浏览器不支持json 作为附件下载
	 * 
	 */
	protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
		super.prepareResponse(request,response);
		UserAgent userAgent=UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		Browser browserGroup=userAgent.getBrowser().getGroup();
		//IE浏览器不支持application/json标识
		if(browserGroup==Browser.IE){
			response.setContentType("text/plain");
		}
	}
}
