package com.asiainfo.biapp.framework.log;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.asiainfo.biapp.framework.core.context.IApplicationContextRefreshed;
import com.asiainfo.biapp.framework.util.SpringContextsUtil;


@Component("log4jConfigRefreshed")
public class Log4jConfigRefreshed implements IApplicationContextRefreshed {

	@Override
	public int getOrder() {
		return 0;
	}

	/**
	 * 动态创建log4j的JDBCAppender
	 */
	@Override
	public boolean executeAuto() {
		try {
			SpringJDBCAppender appender = (SpringJDBCAppender) SpringContextsUtil.getBean("springJDBCAppender");
		    appender.setName(UserOperationLogHelper.OPERATION_LOG_APPENDER);
		    appender.activateOptions();
		    //这个appender不能添加到root中 否则全部日志都写到数据库中了
		    //Logger.getRootLogger().addAppender(appender);
		    Logger logger=Logger.getLogger(UserOperationLogHelper.OPERATION_LOG);
		    logger.addAppender(appender);
		    logger.setAdditivity(false);
		    logger.setLevel(Level.INFO);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
