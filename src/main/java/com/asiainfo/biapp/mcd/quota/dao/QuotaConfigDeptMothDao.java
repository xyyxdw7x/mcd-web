package com.asiainfo.biapp.mcd.quota.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.asiainfo.biapp.mcd.quota.vo.DeptsQuotaStatistics;
import com.asiainfo.biapp.mcd.quota.vo.QuotaConfigDeptMoth;

public interface QuotaConfigDeptMothDao {

	/**
	 * 修改科室月配置额
	 * 
	 * @param depDayQuota
	 * @return
	 */
	public void updateDepMonthQuotaInMem(QuotaConfigDeptMoth depMonthQuota,
			int newQuota) throws DataAccessException;

	/**
	 * 根据主键查询科室月配置额（主键为DATA_DATE、 CITY_ID、DEPT_ID）
	 * 
	 * @param depDayQuota
	 * @return
	 */
	public QuotaConfigDeptMoth getByKeysInMem(QuotaConfigDeptMoth depDayQuota)
			throws DataAccessException;

	/**
	 * 根据主键获得科室月配置额
	 * 
	 * @param cityID
	 * @param deptId
	 * @param DataDate
	 * @return
	 */
	public QuotaConfigDeptMoth getByKeysInMem(String cityID, String deptId,
			String DataDate) throws DataAccessException;


	/**
	 * 批量修改科室月配额
	 * 
	 * @param list
	 */
	void updateBatchUpdateInMem(List<QuotaConfigDeptMoth> list) throws DataAccessException;

	int getTotal4CityDeptMonthInMem(String cityId, String dataDate)throws DataAccessException;

	public List<Map<String, Object>> getDeptsByDateInMem(String cityId, String dataDate)throws DataAccessException;
	
	public void saveBatchSaveOrUpdateInMem(List<DeptsQuotaStatistics> list);
	
	public int getQuotaByKeysInMem(String cityID, String deptId,String DataDate) throws DataAccessException;

	public void saveBatchSaveInMem(List<QuotaConfigDeptMoth> list);

	public List<Map<String, Object>> getQuotas4DeptsInMem(String cityId, String date) throws Exception;
	
}
