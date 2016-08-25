package com.asiainfo.biapp.framework.jdbc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;

/**
 * 
 * implements  MethodBeforeAdvice, AfterReturningAdvice
 * 根据JdbcTemplate执行的方法名动态的切换数据源
 * @author hjn
 *
 */
public class ResetDataSourceAdvice {

	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * 内存数据库名称标识符号
	 */
	private String memDataBaseFlag;

	/**
	 * 设置当前数据源为主库
	 */
	public void resetMasterDataSource(JoinPoint joinPoint) {
		String methodName=joinPoint.getSignature().getName();
		if(methodName.indexOf(getMemDataBaseFlag())>=3){
			logger.debug("setMasterDataSource");
			CustomerContextHolder.setCustomerType(RoutingDataSource.MASTER_MEM);
		}else{
			logger.debug("setMasterMemDataSource");
			CustomerContextHolder.setCustomerType(RoutingDataSource.MASTER);
		}
	}
	
	/**
	 * 设置当前数据源为从库
	 */
	public void resetSlaveDataSource(JoinPoint joinPoint) {
		String methodName=joinPoint.getSignature().getName();
		if(methodName.indexOf(getMemDataBaseFlag())>=3){
			logger.debug("resetSlaveMemDataSource");
			CustomerContextHolder.setCustomerType(RoutingDataSource.SLAVE_MEM);
		}else{
			logger.debug("resetSlaveDataSource");
			CustomerContextHolder.setCustomerType(RoutingDataSource.SLAVE);
		}
	}
	
	
	public String getMemDataBaseFlag() {
		return memDataBaseFlag;
	}

	public void setMemDataBaseFlag(String memDataBaseFlag) {
		this.memDataBaseFlag = memDataBaseFlag;
	}
}
