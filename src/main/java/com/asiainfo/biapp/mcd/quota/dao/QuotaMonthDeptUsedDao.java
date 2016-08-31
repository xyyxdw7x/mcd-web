package com.asiainfo.biapp.mcd.quota.dao;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.quota.vo.QuotaMonthDeptUsed;

public interface QuotaMonthDeptUsedDao {
	/**
	 * 修改科室月使用额为新的值
	 * @param depDayQuota
	 * @param newQuota
	 * @return
	 */
	public QuotaMonthDeptUsed updateDayQuotaUsed(QuotaMonthDeptUsed depDayQuota,int newQuota);
	


	
	/**
	 * 根据主键查询科室月使用额使
	 * @param depDayQuota
	 * @return
	 */
	public int getByKeys(QuotaMonthDeptUsed depDayQuota);
	
	/**
	 * 查询指定月份各科室的使用额
	 * @param depDayQuota
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String, Object>> getDeptMonthUsedInMem(String cityId,String month,String ordyBy) throws Exception;
    /**
     * 查询指定月份各科室的使用额，默认按dept_Id排序
     * @param cityId
     * @param month
     * @return
     * @throws Exception
     */
	public List<Map<String, Object>> getDeptMonthUsedInMem(String cityId, String month) throws Exception;




	public void saveBatchSaveInMem(List<QuotaMonthDeptUsed> list);
	
	

}
