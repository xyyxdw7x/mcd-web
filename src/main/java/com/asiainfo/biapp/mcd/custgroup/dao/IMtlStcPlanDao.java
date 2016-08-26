package com.asiainfo.biapp.mcd.custgroup.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.avoid.model.DimPlanType;

/*
 * Created on 4:00:43 PM
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */
public interface IMtlStcPlanDao {
	/**
	 * add by lixq10  IMCD_ZJ 新建策略页面初始化政策类别
	 * @return
	 */
	public List<DimPlanType> initDimPlanType() throws Exception;
}
