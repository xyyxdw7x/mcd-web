package com.asiainfo.biapp.mcd.policy.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.plan.vo.McdDimPlanOnlineStatus;

public interface IMcdPolicyDao {
	/* 获取政策状态 */
	List<McdDimPlanOnlineStatus> initDimPolicyStatus() throws Exception;

	/* 更新操作 */
	public Boolean updatePolicy(String sql, List<Object> params);

}
