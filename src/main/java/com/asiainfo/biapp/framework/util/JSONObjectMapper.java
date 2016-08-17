package com.asiainfo.biapp.framework.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * json的统一处理
 * @author hanjn
 *
 */
public class JSONObjectMapper extends ObjectMapper {

	public JSONObjectMapper() {
		super();
		//key和value 可以是单引号
		this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		//key可以不加引号
		this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
	}
	/**
	 * 将一个json格式的字符串转化为Map<String,Object>
	 * @param content
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public Map<String,Object> readValueToMap(String content) throws JsonParseException, JsonMappingException, IOException{
		Map<String,Object> result=readValue(content,new TypeReference<HashMap<String,Object>>(){});
		return result;
	}
}
