package com.asiainfo.biapp.mcd.bull.service;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.model.quota.CityQuotaStatisDay;

public interface QuotaConfigCityDayService {

	CityQuotaStatisDay getCityQuotaStatisDay(String cityId);
	
	int queryCityDayQuota(String cityId, String dataDate);
	
	List<Map<String, Object>> queryCityDayQuotas(String cityId, String dataDate);

	int saveDayQuotas(String cityid, String date, String quota,String quotaM) throws Exception;
	
	/**
	 * 获得今天之前的所有日配额之和
	 * @param cityid
	 * @param date
	 * @param quota
	 * @param quotaM
	 * @return
	 * @throws Exception
	 */
	int getNowBeforeSum(String cityid, String date, String quota,String quotaM) throws Exception;
	
	/**
	 * 查询当前日期后的所有日期
	 * @param cityid
	 * @param date
	 * @param quota
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> getNowAfterDataList(String cityid, String date, String quota) throws Exception;
	
	/**
	 * 当前日期后面的数据平分
	 * @param cityid
	 * @param date
	 * @param quota
	 * @return
	 * @throws Exception
	 */
	int updateNowAfterData(String cityid, String date, String quota,String endQuota,List<Map<String,Object>> list) throws Exception;
    /**
     * 获取本月地址配置的配额之和
     * @param cityId
     * @param month
     * @return
     */
    int getCityMonthQuotaSum(String cityId, String month);
    /**
     * 查询本日之前每天剩余配额之和
     * @param cityId
     * @param month
     * @param nowDate
     * @return
     */
    int getSurplusQuotaSum(String cityId, String month, String nowDate);

}
