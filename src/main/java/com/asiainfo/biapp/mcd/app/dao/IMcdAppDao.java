package com.asiainfo.biapp.mcd.app.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.plan.vo.McdDimPlanOnlineStatus;

public interface IMcdAppDao {
	/* 获取内容状态 */
	List<McdDimPlanOnlineStatus> initDimAppStatus() throws Exception;

	/* 更新操作 */
	public Boolean updateApp(String sql, List<Object> params);

}
