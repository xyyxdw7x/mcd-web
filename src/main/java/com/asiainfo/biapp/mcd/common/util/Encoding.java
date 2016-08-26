package com.asiainfo.biapp.mcd.common.util;

import java.io.UnsupportedEncodingException;

public class Encoding {
	public static String ecode = "UTF-8";// 编码方式

	/*
	 * 编码方法
	 */
	public static String encode(String str) {
		try {
			str = java.net.URLEncoder.encode(str, ecode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/*
	 * 解码方法
	 */
	public static String decode(String str) {
		try {
			str = java.net.URLDecoder.decode(str, ecode);
			str = new String(str.getBytes("ISO-8859-1"), ecode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 
	 * decode:url解码
	 * 
	 * @param str
	 * @param encoding
	 * @return
	 * @return String
	 */
	public static String decodeURL(String str, String encoding) {
		try {
			str = java.net.URLDecoder.decode(str, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/*
	 * 测试
	 */
	public static void main(String args[]) {
		// System.out.println(Encoding.encode("测试"));
		System.out
				.println(Encoding
						.decodeURL(
								"http://www.taobao.com/webww/?ver=1&&touid=cntaobao%E5%BC%A0%E6%B5%B7%E7%91%9E_200&siteid=cntaobao&status=1&portalId=&gid=&itemsId=",
								ecode));
	}
}
