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
	 * 从库
	 */
	public static final String SLAVE="slave";
	
	@Override
	protected Object determineCurrentLookupKey() {
		String value=CustomerContextHolder.getCustomerType();
		return value;
	}

}
