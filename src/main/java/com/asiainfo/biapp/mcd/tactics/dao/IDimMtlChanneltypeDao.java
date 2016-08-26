package com.asiainfo.biapp.mcd.tactics.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.tactics.exception.MpmException;
import com.asiainfo.biapp.mcd.tactics.vo.DimMtlChanneltype;
import com.asiainfo.biapp.mcd.tactics.vo.DimPlanSrvType;

/**
 * Created on Jan 4, 2008 4:56:34 PM
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */
public interface IDimMtlChanneltypeDao {

	public List<DimPlanSrvType> getGradeList() throws MpmException;
	public List<DimMtlChanneltype> getMtlChanneltypeByCondition(String isDoubleSelect) throws MpmException;
	/**
	 * add by lixq10 获取渠道信息表  当渠道和政策有关联时 做标识
	 * @return
	 */
	public List<DimMtlChanneltype> getChannelMsg(String isDoubleSelect);
	
}
