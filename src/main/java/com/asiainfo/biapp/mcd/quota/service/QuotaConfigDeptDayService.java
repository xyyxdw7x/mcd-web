package com.asiainfo.biapp.mcd.quota.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.asiainfo.biapp.mcd.quota.model.QuotaConfigDeptDay;

public interface QuotaConfigDeptDayService {

	public List<QuotaConfigDeptDay> getMonthDaysQuota(String cityID,
			String deptId, String month) throws DataAccessException;

	public int getTotal4Days(String cityId, String deptId, String month);
    /**
     * 批量更新日配额
     * @param cityid
     * @param deptId
     * @param month
     * @param list  一个月的所有天的日配额
     * @return
     */
	public Boolean batchUpdateDaysQuota(String cityid, String deptId, String month,
			List<QuotaConfigDeptDay> list);

	public int getRemain(String cityID, String deptId, String month);

	@SuppressWarnings("rawtypes")
	public List getDaysQuota4Mon(String cityID, String deptId, String month);

	public String getDeptId(String useId);
	
	

}
