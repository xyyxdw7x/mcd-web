package com.asiainfo.biapp.mcd.quota.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.asiainfo.biapp.mcd.quota.vo.DeptMonthQuota;

public interface IDeptMonthQuotaDao {

	int getTotal4CityDeptMonthInMem(String cityId, String dataDate)throws DataAccessException;

	public void saveBatchSaveDeptMonConfInMem(List<DeptMonthQuota> list);

	//--------------------------------------------------------------------------------------------------------------
	 /**
     * 配额管理界面：展示科室月配额（科室月配额和科室月使用额连接查询）
     * @param cityId
     * @param month
     * @return
     * @throws DataAccessException
     */
	public List<Map<String, Object>> getStatisticsInMem(String cityId, String month)throws DataAccessException;
	
	/**
	 * 查询地市月配额：主要用于群发管理展示页面上半部分的百分比
	 * @param cityId
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> getCityStatisInMem(String cityId)throws DataAccessException;
	
	 /**
	  * 批量保存或者更新科室月配额：配额管理界面
	  * @param list
	  */
	public void saveBatchSaveOrUpdateConfigInMem(List<DeptMonthQuota> list);
	
	/**
	 * 批量保存科室月使用额
	 * @param list
	 */
	public void saveBatchSaveUsedInMem(List<DeptMonthQuota> list);
	
	//以下三个方法在定时任务中使用-----------------------------------------------------------------------------------
    /**
     * 根据地市月限额配置查询地市月使用额，如果没有配置使用额，则使用额为0。（以地市月限额表为左表---city）
     * @param month
     * @return
     * @throws DataAccessException
     */
	public List<Map<String, Object>> getConfigedCityUsedInMem(String month)throws DataAccessException;

    /**
     * 查询所有科室（地市科室表）的月配额和月使用额情况
     * @param month
     * @return
     */
	public List<Map<String, Object>> getDeptQuotaStaticInMem(String month);

    /**
     * 根据科室月限额配置查找科室对应的日使用额。（以科室月限额表为左表--deptId）
     * @param date
     * @return
     */
	public List<Map<String, Object>> getConfigedDayInMem(String date);
	
	/**
	 * 批量保存科室月配额模板
	 * @param list
	 * @param cityId
	 * @throws DataAccessException
	 */
	public void saveBatchSaveDefaultInMem(List<DeptMonthQuota> list, String cityId)throws DataAccessException;
}
