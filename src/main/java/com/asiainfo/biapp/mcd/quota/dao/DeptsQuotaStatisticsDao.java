package com.asiainfo.biapp.mcd.quota.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface DeptsQuotaStatisticsDao {
    /**
     * 科室月限额和科室月使用额链接查询：查询限额和使用额。  展示科室月限额列表时使用
     * @param cityId
     * @param month
     * @return
     * @throws DataAccessException
     */
	public List<Map<String, Object>> getStatisticsInMem(String cityId, String month)
			throws DataAccessException;
	
	
	//以下三个方法在群发管理页面中使用（主要用于展示页面上半部分的百分比）
	public List<Map<String, Object>> getCurrentMonthInMem(String cityId)
			throws DataAccessException;
	public List<Map<String, Object>> getCurrentDayInMem(String cityId)
			throws DataAccessException;
	public Map<String, Object> getCityStatisInMem(String cityId)
			throws DataAccessException;
	
	
	//以下三个方法在定时任务中使用

    /**
     * 根据地市月限额配置查询地市月使用额，如果没有配置使用额，则使用额为0。（以地市月限额表为左表---city）
     * @param month
     * @return
     * @throws DataAccessException
     */
	public List<Map<String, Object>> getConfigedCityUsedInMem(String month)
			throws DataAccessException;

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

}
