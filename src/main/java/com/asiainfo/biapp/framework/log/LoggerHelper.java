package com.asiainfo.biapp.framework.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 记录慢方法 
 * @author hjn
 *
 */
public class LoggerHelper {

	private static LoggerHelper slh;
	
	private final Logger slowLogger=LoggerFactory.getLogger("slowLog");
	private final Logger errorLogger=LoggerFactory.getLogger("errorLog");
	
	
	public static LoggerHelper getInstance(){
		if(slh==null){
			slh=new LoggerHelper();
		}
		return slh;
	}
	/**
	 * 特定的日志logger输出到特定的目的地
	 * @param msg
	 */
	public  void logSlow(String msg){
		slowLogger.info(msg);
	}
	
	/**
	 * 特定的日志logger输出到特定的目的地
	 * @param msg
	 */
	public  void logError(String msg){
		errorLogger.error(msg);
	}
	
}
