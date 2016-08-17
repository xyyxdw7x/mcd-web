package com.asiainfo.biapp.framework.util;

import java.util.Properties;


/**
 * 获得conf中的配置信息
 * @author hjn
 *
 */
public class ConfProperties {

	private static Properties props=null;

	private static ConfProperties confProperties;
	
	public ConfProperties() {
		if(props==null){
			props = (Properties) SpringContextsUtil.getBean("confProperties");
		}
	}
	
	public static ConfProperties getInstance(){
		if(confProperties==null){
			confProperties=new ConfProperties();
		}
		return confProperties;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getValue(String key){
		return props.getProperty(key);
	}
	
	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getValue(String key,String defaultValue){
		return props.getProperty(key, defaultValue);
	}
}
