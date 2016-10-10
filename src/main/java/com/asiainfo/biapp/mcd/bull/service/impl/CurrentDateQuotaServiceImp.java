package com.asiainfo.biapp.mcd.bull.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.bull.service.ICurrentDateQuotaService;
import com.asiainfo.biapp.mcd.quota.dao.IDeptsQuotaStatisticsDao;
import com.asiainfo.biapp.mcd.quota.vo.CityMonthQuota;

@Service("currentDateQuotaService")
public class CurrentDateQuotaServiceImp implements ICurrentDateQuotaService {
	@Autowired
	private IDeptsQuotaStatisticsDao deptsQuotaStatisticsDao;
	
	@Override
	public CityMonthQuota getCityStatis(String cityId){
		CityMonthQuota cityStatic = new CityMonthQuota();
		Map<String, Object>  map = deptsQuotaStatisticsDao.getCityStatisInMem(cityId);
		if(map!=null && map.size()>0){
			cityStatic.setCityId(map.get("CITY_ID").toString());
			if(map.get("MONTH_QUOTA_NUM")!=null){
				cityStatic.setMonthQuotaNum(Integer.parseInt(map.get("MONTH_QUOTA_NUM").toString()));
			}else{
				cityStatic.setMonthQuotaNum(0);
			}
			if(map.get("USED_NUM")!=null){
				cityStatic.setUsedNum(Integer.parseInt(map.get("USED_NUM").toString()));
			}else{
				cityStatic.setUsedNum(0);
			}
			
			
		}else{
			cityStatic.setCityId(cityId);
			cityStatic.setMonthQuotaNum(0);
			cityStatic.setUsedNum(0);
		}
		cityStatic.setCityUsedPercent();
		
		return cityStatic;
		
	}
}
