package com.asiainfo.biapp.framework.log;

import org.apache.log4j.Logger;

/**
 * JDBC log任务
 * @author hjn
 *
 */
public class JDBCLogTask implements Runnable {

	/**
	 * 要保存的日志消息 
	 */
	private String message;
	
	/**
	 * 特定的logger
	 */
	private Logger logger;
	
	public JDBCLogTask(String message,Logger logger) {
		this.message=message;
		this.logger=logger;
	}
	
	@Override
	public void run() {
		this.logger.info(message);
	}
}
