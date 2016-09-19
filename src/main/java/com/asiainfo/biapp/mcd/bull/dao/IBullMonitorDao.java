package com.asiainfo.biapp.mcd.bull.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface IBullMonitorDao {

	public List<Map<String, Object>> getBullMonitorListByDept(String cityId,
			String deptId) throws DataAccessException;

	public List<Map<String, Object>> getBullMonitorList(String cityId)
			throws DataAccessException;

	void batchUpdatePauseComment(String campIds, String pauseComment);

	/**
	 * 更新策略优先级
	 * @param campsegId
	 * @param cityId 
	 */
    public void updateCampSegInfoCampPriId(String campsegId, String cityId);

}
