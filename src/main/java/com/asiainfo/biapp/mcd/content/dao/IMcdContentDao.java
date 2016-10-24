package com.asiainfo.biapp.mcd.content.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.plan.vo.McdDimPlanOnlineStatus;

public interface IMcdContentDao {
	/* 获取内容状态 */
	List<McdDimPlanOnlineStatus> initDimContentStatus() throws Exception;

	/* 更新操作 */
	public Boolean updateContent(String sql, List<Object> params);

}
