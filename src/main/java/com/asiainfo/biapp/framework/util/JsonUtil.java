package com.asiainfo.biapp.framework.util;

import java.io.IOException;

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
}
