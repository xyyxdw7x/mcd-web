package com.asiainfo.biapp.mcd.quota.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface QuotaConfigCityMothDao {

    /**
     * 查询科室指定月的配额
     * @param city
     * @param Month
     * @return
     */
	int queryCityMonthQuotaInMem(String city)throws DataAccessException;

	List<Map<String, Object>> queryAllInMem();

}
