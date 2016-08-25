package com.asiainfo.biapp.mcd.avoid.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.avoid.dao.IMpmCommonJdbcDao;

/*
 * Created on 2:57:54 PM
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */
@Repository(value="commonJdbcDao")
public class MpmCommonJdbcDaoImpl extends JdbcDaoBase implements IMpmCommonJdbcDao {
	private static Logger log = LogManager.getLogger();

	public MpmCommonJdbcDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	//获得营销渠道类型
	@Override
	public List getCampChannelTypeMap() throws Exception {

		List list = new ArrayList();
		String sql = "select CHANNELTYPE_ID, CHANNELTYPE_NAME from DIM_MTL_CHANNELTYPE  ORDER BY DISPLAY_ORDER ASC";
		list = this.getJdbcTemplate().queryForList(sql);
		return list;
	}
}
