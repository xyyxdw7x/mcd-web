package com.asiainfo.biapp.mcd.avoid.dao;

import java.util.List;

/**
 * Created on 2:55:48 PM
 *
 * <p>Title: </p>
 * <p>Description: 营销管理系统通过JDBC直接访问数据库的功用方法借口�?/p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */
/**
 * @author dengshu
 *
 */
public interface IMpmCommonJdbcDao {

	/**
	 * @return
	 */
	public List getCampChannelTypeMap() throws Exception;

}
