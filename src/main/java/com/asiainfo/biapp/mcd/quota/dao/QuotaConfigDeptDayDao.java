package com.asiainfo.biapp.mcd.quota.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.asiainfo.biapp.mcd.quota.model.QuotaConfigDeptDay;

public interface QuotaConfigDeptDayDao {
	/**
	 * 修改科室日配置额
	 * 
	 * @param depDayQuota
	 * @param newQuota
	 * @throws DataAccessException
	 */
	public void updateDepDayQuotaInMem(QuotaConfigDeptDay depDayQuota, int newQuota)
			throws DataAccessException;
	
	public void updateDepDayQuotaInMem(String cityId,String deptId,String date, int newQuota)
			throws DataAccessException;

	/**
	 * 根据主键查询科室日配置额（主键为DATA_DATE、 CITY_ID、DEPT_ID）
	 * 
	 * @param depDayQuota
	 * @return
	 * @throws DataAccessException
	 */
	public QuotaConfigDeptDay getByKeysInMem(QuotaConfigDeptDay depDayQuota)
			throws DataAccessException;

	/**
	 * 根据主键获得科室日配置额
	 * 
	 * @param cityID
	 * @param deptId
	 * @param date
	 *            格式为： yyyy-mm-dd
	 * @return
	 * @throws DataAccessException
	 */
	public QuotaConfigDeptDay getByKeysInMem(String cityID, String deptId,String data) throws DataAccessException;
	
	public int getDayConfNumByKeys(String cityID, String deptId,String data) throws DataAccessException;

	/**
	 * 查询指定科室的指定月的每日配置额
	 * 
	 * @param cityID
	 * @param deptId
	 * @param month
	 *            格式为：yyyy-mm
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> getMonthDaysQuotaInMem(String cityID,String deptId, String month) throws DataAccessException;

	public int getTotal4DaysInMem(String cityId, String deptId, String month);
	
	public int getDayQuota(String cityID,String deptId, String date) throws DataAccessException;
	//保存日配置列表时使用
	public void batchSaveOrUpdateInMem(List<QuotaConfigDeptDay> list);
    //定时任务-日任务时使用
	public void batchUpdateDayConfNumInMem(List<QuotaConfigDeptDay> list);

	public List<Map<String, Object>> getDayConfInMem(String cityId, String deptId,String fromDate, String toDate);

	public void batchSaveInMem(List<QuotaConfigDeptDay> list);

	public List<Map<String, Object>> queryConfigDeptInMem(String month);

	

	

}
