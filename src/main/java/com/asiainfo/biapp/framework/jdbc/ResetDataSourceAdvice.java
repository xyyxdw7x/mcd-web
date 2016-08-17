package com.asiainfo.biapp.framework.jdbc;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * implements  MethodBeforeAdvice, AfterReturningAdvice
 * 根据JdbcTemplate执行的方法名动态的切换数据源
 * @author hjn
 *
 */
public class ResetDataSourceAdvice {

	protected final Log logger = LogFactory.getLog(getClass());
	
	public void before(Method method, Object[] args, Object className) throws Throwable {
		String methodName=method.getName();
		logger.info("before className="+className+" method="+methodName);
		//根据方法名进行切换数据源  query execute call update batchUpdate方法
		if(methodName.startsWith("update")||methodName.startsWith("batchUpdate")||
				methodName.startsWith("call")){
			resetMasterDataSource();
		}else if(methodName.startsWith("query")||methodName.startsWith("execute")){
			resetSlaveDataSource();
		}
	}

	/**
	 * 设置当前数据源为主库
	 */
	public void resetMasterDataSource() {
		logger.debug("setMasterDataSource");
		CustomerContextHolder.setCustomerType(RoutingDataSource.MASTER);
	}

	/**
	 * 设置当前数据源为从库
	 */
	public void resetSlaveDataSource() {
		logger.debug("setSlaveDataSource");
		CustomerContextHolder.setCustomerType(RoutingDataSource.SLAVE);
	}
}
