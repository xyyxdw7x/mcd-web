package com.asiainfo.biapp.mcd.avoid.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asiainfo.biframe.utils.string.StringUtil;

public class TempUtil {
	/**
	 * 判断字符串是不是由数字组�?
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (StringUtil.isEmpty(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
