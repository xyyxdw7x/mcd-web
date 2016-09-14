package com.asiainfo.biapp.mcd.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.time.FastDateFormat;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期转换工具类 ，使用BeanUtils时，Date类型值为空的解决办法
 * @author zhongzhq
 *
 */
public class DateConvert implements Converter {
	private static String dateFormatStr = "yyyy-MM-dd";
	private static FastDateFormat dateTimeFormat = FastDateFormat.getInstance(dateFormatStr);

	private static String dateLongFormatStr = dateFormatStr + " HH:mm:ss";
	private static FastDateFormat dateTimeLongFormat = FastDateFormat.getInstance(dateLongFormatStr);

	public Object convert(Class arg0, Object arg1) {
		if (arg1 == null) {
			return null;
		}
		String className = arg1.getClass().getName();
		//java.sql.Timestamp
		if ("java.sql.Timestamp".equalsIgnoreCase(className)) {
			try {
				DateFormat df = new SimpleDateFormat(dateFormatStr + " HH:mm:ss");
				return df.parse(dateTimeLongFormat.format(arg1));
			} catch (Exception e) {
				try {
					DateFormat df = new SimpleDateFormat(dateFormatStr);
					return df.parse(dateTimeFormat.format(arg1));
				} catch (ParseException ex) {
					e.printStackTrace();
					return null;
				}
			}
		} else {//java.util.Date,java.sql.Date
			String p = arg1 != null ? dateTimeLongFormat.format(arg1) : "";
			if (StringUtils.isEmpty(p)) {
				return null;
			}
			try {
				DateFormat df = new SimpleDateFormat(dateFormatStr + " HH:mm:ss");
				return df.parse(p.trim());
			} catch (Exception e) {
				try {
					DateFormat df = new SimpleDateFormat(dateFormatStr);
					return df.parse(p.trim());
				} catch (ParseException ex) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}

	public static String formatDateTime(Object obj) {
		if (obj != null) {
			return dateTimeFormat.format(obj);
		} else {
			return "";
		}
	}

	public static String formatLongDateTime(Object obj) {
		if (obj != null) {
			return dateTimeLongFormat.format(obj);
		} else {
			return "";
		}
	}

	public static java.sql.Date utilDate2sqlDate(java.util.Date srcDate){
		return srcDate != null ? new java.sql.Date(srcDate.getTime()) : null;
	}

}
