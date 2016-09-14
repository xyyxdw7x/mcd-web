package com.asiainfo.biapp.mcd.common.dao.plan;

import java.util.List;

import com.asiainfo.biapp.mcd.common.vo.plan.McdDimPlanType;

public interface McdDimPlanTypeDao {

	/**
	 * 根据父id获得所有的子
	 * @param pid
	 * @return
	 */
	List<McdDimPlanType> getChildrens(String pid);

	List<McdDimPlanType> getAll();

}
