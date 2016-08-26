package com.asiainfo.biapp.mcd.quota.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.asiainfo.biapp.mcd.quota.model.QuotaDayDeptUsed;

public interface QuotaDayDeptUsedDao {


	public int getByKeys(String cityid, String deptid, String day)throws DataAccessException;

	public List<Map<String, Object>> getUsed4DaysInMem(String cityId, String deptId,
			String fromDate, String toDate) throws DataAccessException;
	/**
	 * 获得科室截止到昨天的使用额总量
	 * 
	 * @param cityId
	 * @param deptId
	 * @return
	 */
	public int getMonQutoaTotalUntilYesterdayInMem(String cityId, String deptId)throws DataAccessException;
	//批量保存，日定时任务中使用
	public void batchSaveInMem(List<QuotaDayDeptUsed> list)throws DataAccessException;
	
}
