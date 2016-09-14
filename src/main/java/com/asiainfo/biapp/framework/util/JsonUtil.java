package com.asiainfo.biapp.framework.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {

	/**
	 * object转化为json字符串
	 * @param obj
	 * @return
	 */
	public static String objectJSONString(Object obj){
		String jsonStr=null;
		ObjectMapper objectMapper=(ObjectMapper) SpringContextsUtil.getBean("objectMapper");
		try {
			jsonStr=objectMapper.writeValueAsString(obj);
		} catch ( IOException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	/**
	 * 输出json串到页面
	 * @param response
	 * @param json
	 */
	public static void outJson(HttpServletResponse response, Object json){
		response.setContentType("text/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().print(json == null ? "{}" : json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
