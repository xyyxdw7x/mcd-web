package com.asiainfo.biapp.framework.jdbc;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 多数据源路由类
 * @author hjn
 *
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

	/**
	 * 主库
	 */
	public static final String MASTER="master";
	
	/**
	 * 主库内存库
	 */
	public static final String MASTER_MEM="master_mem";
	
	
	/**
	 * 从库
	 */
	public static final String SLAVE="slave";
	
	/**
	 * 从库内存库
	 */
	public static final String SLAVE_MEM="slave_mem";
	
	
	@Override
	protected Object determineCurrentLookupKey() {
		String value=CustomerContextHolder.getCustomerType();
		return value;
	}

}
