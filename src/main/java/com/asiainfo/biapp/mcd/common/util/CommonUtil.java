package com.asiainfo.biapp.mcd.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONArray;

import com.asiainfo.biframe.utils.string.StringUtil;

public class CommonUtil {
	
	private static String lastTime = "";// 记录最新的时间戳
	
	/**
	 * 根据时间戳生成Id，默认17位
	 * 
	 * @return YYYYMMDDHHMMSSXXX
	 */
	public synchronized static String generateId() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateStr = sdf.format(new Date());
		// 唯一性保证
		if (StringUtil.isNotEmpty(lastTime)
				&& Long.valueOf(lastTime) >= Long.valueOf(dateStr)) {
			dateStr = generateId();
		} else {
			lastTime = dateStr;
		}
		return dateStr;
	}
	
	public static String getRandom(int max) throws Exception {
		return 1 + (int) (Math.random() * max) + "";
	}
	
	public static void main(String[] args) {
		try {
			for (int i = 0; i < 13; i++) {
				System.out.println(getRandom(1000000));
			}
			
//			ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public static List<String> string2List(String str, String sep) throws Exception {
//		String[] arr = str.split(sep);
//		List<String> list = new ArrayList<String>();
//		for (String string : arr) {
//			list.add(string);
//		}
//		return list;
//	}
	
	public static List<String> serializeToList(int heap) throws Exception {
		List<String> list = null;
		if (heap > 0) {
			list = new ArrayList<String>();
			for (int i = 1; i <= heap; i++) {
				list.add(numberAssist(i));
//				list.add(i + "");
			}
			return list;
		}
		return new ArrayList<String>();
	}
	
	public static List jsonArray2List(String jsonArrayStr,Class clazz){
		JSONArray jsonarray = JSONArray.fromObject(jsonArrayStr); 
		List list = (List)JSONArray.toCollection(jsonarray, clazz);
		return list; 
	}
	
	public static Integer pickDigits(String source) throws Exception {
		StringBuffer sb = new StringBuffer();
		for (int index = 0; index < source.length(); index++) {
			char ch = source.charAt(index);
			if (Character.isDigit(ch)) {
				sb.append(ch);
			}
		}
		return Integer.parseInt(sb.toString());
	}

	private static String numberAssist(int i) {
		return i > 9 ? i + "" : "0" + i;
	}
	
	public static String optString(Map<String, Object> map, String key) {
		Object value = map.get(key);
		return StringUtil.isNotEmpty(value) ? String.valueOf(value) : "";
	}
	
}
