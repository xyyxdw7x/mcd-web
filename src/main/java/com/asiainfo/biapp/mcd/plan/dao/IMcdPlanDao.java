package com.asiainfo.biapp.mcd.plan.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.plan.vo.McdDimPlanOnlineStatus;

public interface IMcdPlanDao {
	/* 获取产品状态 */
	List<McdDimPlanOnlineStatus> initDimPlanStatus() throws Exception;

	/* 更新操作 */
	public Boolean updatePlan(String sql, List<Object> params);

}
