package com.asiainfo.biapp.mcd.home.echartbean;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.beanutils.BeanUtils;

//import com.asiainfo.biframe.utils.export.excelhelper.CellBean;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	private static ObjectMapper mapper = new ObjectMapper();

	static {
		// 设置日期（Date、Timestamp）的格式
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		// 序列化设置,序列化成json字符串的时候不包含null值,针对Map类
//		mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES,
//				false);
//		// 序列化设置,序列化成json字符串的时候不包含null值,针对POJO类
//		mapper.setSerializationConfig(mapper.getSerializationConfig()
//				.withSerializationInclusion(Inclusion.NON_NULL));
////	 允许反序列化不带双引号的key
//	 mapper.configure(DeserializationFeature.ALLOW_UNQUOTED_FIELD_NAMES, true);
////	 序列化的结果不带双引号
//	 mapper.configure(org.codehaus.jackson.JsonGenerator.Feature.QUOTE_FIELD_NAMES, true);
		
		
		// 序列化设置,序列化成json字符串的时候不包含null值
		mapper.setSerializationInclusion(Include.NON_NULL);  
		// 反序列化设置,忽略JSON字符串中存在而Java对象实际没有的属性，否则json字符串中的属性如果bean里面没有的话会报错
		 mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static JSONObject Object2Json(Object o) {
		return JSONObject.fromObject(o);
	}

	public static JSONArray Object2Json(List o) {
		return JSONArray.fromObject(o);
	}

	public static JSONObject Object2Json(Object obj,
			PropertyFilter propertyFilter) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(propertyFilter);
		return JSONObject.fromObject(obj, jsonConfig);
	}

	public static JSONArray Object2Json(List list, PropertyFilter propertyFilter) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(propertyFilter);
		return JSONArray.fromObject(list, jsonConfig);
	}

	public static String Object2JsonString(Object o) {
		return Object2Json(o).toString();
	}

	public static String Object2JsonString(List o) {
		return Object2Json(o).toString();
	}

	/**
	 * 解析json字符串到object类型 add by gaolc@asiainfo-linkage.com
	 * 
	 * @param js
	 *            json串
	 * @param c
	 *            object的class类型
	 * @param classMap
	 *            object类型包含的属性字符串与类型的键值对map 基本类型不需要
	 * @return
	 */
	public static Object JsonString2Bean(String js, Class c, Map classMap) {
		JSONObject jo = JSONObject.fromObject(js);
		return JSONObject.toBean(jo, c, classMap);
	}

	/**
	 * 解析json字符串到List<object类型> add by gaolc@asiainfo-linkage.com
	 * 
	 * @param js
	 *            json串
	 * @param c
	 *            object的class类型
	 * @param classMap
	 *            object类型包含的属性字符串与类型的键值对map 基本类型不需要
	 * @return
	 */
	public static List JsonString2List(String js, Class c, Map classMap) {
		JSONArray array = JSONArray.fromObject(js);
		List l = new ArrayList();
		JSONObject jo = null;
		Object o = null;
		for (int i = 0; i < array.size(); i++) {
			jo = array.getJSONObject(i);
			o = JSONObject.toBean(jo, c, classMap);
			l.add(o);
		}
		return l;
	}

	//TODO excelhelper.CellBean？
//	public static Object JsonString2Bean(String js, Class c) {
//		JSONObject jo = JSONObject.fromObject(js);
//		List cellList = new ArrayList();
//		if ((jo.containsKey("headers"))
//				&& (jo.getJSONArray("headers").size() > 0)) {
//			JSONArray jarr = jo.getJSONArray("headers");
//			for (int i = 0; i < jarr.size(); i++) {
//				JSONObject jobj = (JSONObject) jarr.get(i);
//				Map map1 = (Map) JSONObject.toBean(jobj, Map.class);
//				CellBean cellbean = new CellBean();
//				try {
//					BeanUtils.copyProperties(cellbean, map1);
//					cellList.add(cellbean);
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		Map map = (Map) JSONObject.toBean(jo, Map.class);
//		if (cellList.size() > 0) {
//			map.put("headers", cellList);
//		}
//		Object bean = null;
//		try {
//			bean = c.newInstance();
//			BeanUtils.copyProperties(bean, map);
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}
//		return bean;
//	}

	public static String JsonString2Xml(String s) {
		XMLSerializer xml = new XMLSerializer();
		String xmlString = xml.write(Object2Json(s));
		return xmlString;
	}

	public static String Xml2JsonString(String s) {
		XMLSerializer xml = new XMLSerializer();
		JSON js = xml.read(s);
		return js.toString();
	}
	
	/**
     * 将Object转化成JSON String
     * @param obj
     * @return
     * @author zhuyq3@asiainfo.com
     * 2014年11月28日12:51:20
     */
    public static String toJSONString(Object obj){
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("JsonUtil.toJSONString发生错误", e);
        }
    }

}
