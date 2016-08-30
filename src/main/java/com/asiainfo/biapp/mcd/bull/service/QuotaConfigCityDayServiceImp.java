package com.asiainfo.biapp.mcd.bull.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.bull.dao.QuotaConfigCityDayDao;
import com.asiainfo.biapp.mcd.model.quota.CityQuotaStatisDay;
import com.asiainfo.biapp.mcd.util.QuotaUtils;

@Service("quotaConfigCityDayService")
public class QuotaConfigCityDayServiceImp implements QuotaConfigCityDayService {
	
	@Autowired
	private QuotaConfigCityDayDao quotaConfigCityDayDao;

	@Override
	public CityQuotaStatisDay getCityQuotaStatisDay(String cityId) {
		 
		CityQuotaStatisDay cityQuotaStatisDay = new CityQuotaStatisDay();
		String dataDate = QuotaUtils.getDayDate("yyyyMMdd");
		Map<String, Object> map = this.quotaConfigCityDayDao.getCityStatisDay(cityId, dataDate);
		if (map != null) {
			try {
				QuotaUtils.map2Bean(map, cityQuotaStatisDay);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		cityQuotaStatisDay.setCityUsedPercentDay();//设置百分比
		return cityQuotaStatisDay;

	}

	@Override
	public int queryCityDayQuota(String cityId, String dataDate) {
		try {
			return this.quotaConfigCityDayDao.queryCityDayQuota(cityId, dataDate);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Map<String, Object>> queryCityDayQuotas(String cityId,
			String dataDate) {
		try {
			return this.quotaConfigCityDayDao.queryCityDayQuotas(cityId, dataDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Map<String, Object>>();
	}

	@Override
	public int saveDayQuotas(String cityid, String date, String quota,String quotaM) throws Exception {
		return this.quotaConfigCityDayDao.saveDayQuotas(cityid, date, quota,quotaM);
	}
	
	/**
	 * 获得今天之前的所有日配额之和
	 * @param cityid
	 * @param date
	 * @param quota
	 * @param quotaM
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getNowBeforeSum(String cityid, String date, String quota,String quotaM) throws Exception{
		return this.quotaConfigCityDayDao.getNowBeforeSum(cityid, date, quota,quotaM);
	}
	
	/**
	 * 查询当前日期后的所有日期
	 * @param cityid
	 * @param date
	 * @param quota
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String,Object>> getNowAfterDataList(String cityid, String date, String quota) throws Exception{
		return this.quotaConfigCityDayDao.getNowAfterDataList(cityid, date, quota);
	}
	
	/**
	 * 当前日期后面的数据平分
	 * @param cityid
	 * @param date
	 * @param quota
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateNowAfterData(String cityid, String date, String quota,String endQuota,List<Map<String,Object>> list) throws Exception{
		return this.quotaConfigCityDayDao.updateNowAfterData(cityid, date, quota,endQuota,list);
	}
    /**
     * 获取本月地址配置的配额之和
     * @param cityId
     * @param month
     * @return
     */
    @Override
    public int getCityMonthQuotaSum(String cityId, String month) {
        return this.quotaConfigCityDayDao.getCityMonthQuotaSum(cityId, month);

    }
    /**
     * 查询本日之前每天剩余配额之和
     * @param cityId
     * @param month
     * @param nowDate
     * @return
     */
    @Override
    public int getSurplusQuotaSum(String cityId, String month, String nowDate) {
        return this.quotaConfigCityDayDao.getSurplusQuotaSum(cityId, month,nowDate);

    }
}
