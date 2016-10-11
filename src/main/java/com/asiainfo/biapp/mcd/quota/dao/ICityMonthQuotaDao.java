package com.asiainfo.biapp.mcd.quota.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.asiainfo.biapp.mcd.quota.vo.CityMonthQuota;

public interface ICityMonthQuotaDao {

    /**
     * 查询科室指定月的配额
     * @param city
     * @param Month
     * @return
     */
	int queryCityMonthQuotaInMem(String city)throws DataAccessException;

	List<Map<String, Object>> queryAllInMem();
	
	/**
	 * 批量添加科室月使用额
	 * @param list
	 */
	public void saveBatchSaveCityMonthUsed(List<CityMonthQuota> list);


}
