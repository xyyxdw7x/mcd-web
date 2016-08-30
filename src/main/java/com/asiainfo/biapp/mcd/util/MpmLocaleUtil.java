package com.asiainfo.biapp.mcd.util;

import java.util.Arrays;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;

import com.asiainfo.biframe.utils.config.Configure;


/**
 * 读取国际化资源文件
 *
 * @author wanglei
 *
 */
public class MpmLocaleUtil {
	private static Logger log = LogManager.getLogger();
	public static final Locale LOCALE = new Locale(Configure.getInstance().getProperty("LOCALE_LANGUAGE_DEFAULT"), Configure.getInstance().getProperty("LOCALE_COUNTRY_DEFAULT"), "");
	private static MessageSource messageSource;

	public static void setMessageSource(MessageSource messageSource) {
		MpmLocaleUtil.messageSource = messageSource;
	}

	/**
	 * 替换资源条目；
	 *
	 * @param key
	 * @return
	 */
	public static String getMessage(String key) {
		String value = "";
		try {
			value = messageSource.getMessage(key, null, LOCALE);
		} catch (Exception e) {
			log.error("MpmLocaleUtil.getMessage(" + key + ")  error! ", e);
		}
		return value;
	}

	/**
	 * 替换资源条目，含占位符；
	 *
	 * @param key
	 * @param params
	 *            占位符变量
	 * @return
	 */
	public static String getMessage(String key, String[] params) {
		String value = "";
		try{
		value = messageSource.getMessage(key, params, LOCALE);
		}catch (Exception e) {
			log.error("MpmLocaleUtil.getMessage(" + key + ","+params!=null?Arrays.toString(params):"null"+")  error! ", e);
		}
		return value;
	}
	
	public static String escape4Html(String str) {
		if (StringUtils.isEmpty(str)) {
			if (str.indexOf("\"") != -1) {
				str = str.replaceAll("\"", "&quot;");
			}
			if (str.indexOf("\'") != -1) {
				str = str.replaceAll("\'", "&apos;");
			}
		}
		return str;
	}
	
}
