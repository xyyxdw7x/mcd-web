package com.asiainfo.biapp.mcd.bull.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.bull.vo.CityQuotaStatic;
import com.asiainfo.biapp.mcd.bull.vo.CurrentDateQuota;
import com.asiainfo.biapp.mcd.quota.dao.DeptsQuotaStatisticsDao;
import com.asiainfo.biapp.mcd.util.QuotaUtils;

@Service("currentDateQuotaService")
public class CurrentDateQuotaServiceImp implements CurrentDateQuotaService {
	@Autowired
	private DeptsQuotaStatisticsDao deptsQuotaStatisticsDao;
	
	@Override
	public List<CurrentDateQuota> getCurrentStatis(String cityId) {
		List<CurrentDateQuota> list = new ArrayList<CurrentDateQuota>();
		List<Map<String, Object>> month = deptsQuotaStatisticsDao.getCurrentMonthInMem(cityId);

		if (month != null && month.size() > 0 ) {
			for (int i = 0; i < month.size(); i++) {
				CurrentDateQuota temp = new CurrentDateQuota();
				try {
					QuotaUtils.map2Bean(month.get(i), temp);
				} catch (Exception e) {
					e.printStackTrace();
				}
				temp.setMonthUsedPercent();
				list.add(temp);
			}
		}

		return list;
	}
	@Override
	public CityQuotaStatic getCityStatis(String cityId){
		CityQuotaStatic cityStatic = new CityQuotaStatic();
		Map<String, Object>  map = deptsQuotaStatisticsDao.getCityStatisInMem(cityId);
		if(map!=null && map.size()>0){
			cityStatic.setCityId(map.get("CITY_ID").toString());
			
			if(map.get("MONTH_QUOTA_NUM")!=null){
				cityStatic.setMonthQuotaNum(Integer.parseInt(map.get("MONTH_QUOTA_NUM").toString()));
			}else{
				cityStatic.setMonthQuotaNum(0);
			}
			if(map.get("USED_NUM")!=null){
				cityStatic.setMonthUsedNum(Integer.parseInt(map.get("USED_NUM").toString()));
			}else{
				cityStatic.setMonthUsedNum(0);
			}
			
			
		}else{
			cityStatic.setCityId(cityId);
			cityStatic.setMonthQuotaNum(0);
			cityStatic.setMonthUsedNum(0);
		}
		cityStatic.setCityUsedPercent();
		
		return cityStatic;
		
	}
}
