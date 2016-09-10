package com.asiainfo.biapp.framework.util;

import org.springframework.beans.factory.annotation.Value;

/**
 * 系统配置项
 * @author hjn
 *
 */
public class AppConfigUtil {

	
	@Value("${app_provinces}")
	public static String APP_PROVINCES="zj";
}
