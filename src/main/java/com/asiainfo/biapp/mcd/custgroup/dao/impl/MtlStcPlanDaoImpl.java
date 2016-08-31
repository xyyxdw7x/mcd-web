package com.asiainfo.biapp.mcd.custgroup.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.common.vo.plan.DimPlanType;
import com.asiainfo.biapp.mcd.custgroup.dao.IMtlStcPlanDao;

/**
 * Created on 4:02:56 PM
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */
public class MtlStcPlanDaoImpl extends JdbcDaoBase implements IMtlStcPlanDao {
	private static Logger log = LogManager.getLogger();

	public MtlStcPlanDaoImpl() {
		super();
	}
	@Override
	public List<DimPlanType> initDimPlanType() throws Exception {
		String sql = "SELECT * FROM DIM_PLAN_TYPE D ORDER BY D.SORT_NUM";
		return this.getJdbcTemplate().queryForList(sql,DimPlanType.class);
	}
}
