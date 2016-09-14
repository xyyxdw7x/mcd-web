package com.asiainfo.biapp.mcd.quota.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.quota.dao.DeptsQuotaStatisticsDao;
import com.asiainfo.biapp.mcd.quota.dao.QuotaConfigCityDayDao;
import com.asiainfo.biapp.mcd.quota.dao.QuotaConfigDeptDayDao;
import com.asiainfo.biapp.mcd.quota.dao.QuotaConfigDeptMothDao;
import com.asiainfo.biapp.mcd.quota.dao.QuotaDayDeptUsedDao;
import com.asiainfo.biapp.mcd.quota.util.QuotaUtils;
import com.asiainfo.biapp.mcd.quota.vo.QuotaConfigCityDay;
import com.asiainfo.biapp.mcd.quota.vo.QuotaConfigDeptDay;
import com.asiainfo.biapp.mcd.quota.vo.QuotaDayDeptUsed;

public class DayQuotaTaskServiceImp implements DayQuotaTaskService {
	private DeptsQuotaStatisticsDao deptsQuotaStatisticsDao;
	private QuotaDayDeptUsedDao quotaDayDeptUsedDao;
	private QuotaConfigDeptDayDao quotaConfigDeptDayDao;
	private QuotaConfigDeptMothDao quotaConfigDeptMothDao;
	
	private QuotaConfigCityDayDao quotaConfigCityDayDao;

	public void setQuotaConfigCityDayDao(QuotaConfigCityDayDao quotaConfigCityDayDao) {
		this.quotaConfigCityDayDao = quotaConfigCityDayDao;
	}

	public void setQuotaConfigDeptMothDao(
			QuotaConfigDeptMothDao quotaConfigDeptMothDao) {
		this.quotaConfigDeptMothDao = quotaConfigDeptMothDao;
	}

	public void setQuotaDayDeptUsedDao(QuotaDayDeptUsedDao quotaDayDeptUsedDao) {
		this.quotaDayDeptUsedDao = quotaDayDeptUsedDao;
	}

	public void setQuotaConfigDeptDayDao(
			QuotaConfigDeptDayDao quotaConfigDeptDayDao) {
		this.quotaConfigDeptDayDao = quotaConfigDeptDayDao;
	}

	public void setDeptsQuotaStatisticsDao(
			DeptsQuotaStatisticsDao deptsQuotaStatisticsDao) {
		this.deptsQuotaStatisticsDao = deptsQuotaStatisticsDao;
	}
	@Override
	public void execTaskDayQuota(){
		//没有日使用额的地市时为该地市插入日使用额
		List<QuotaConfigCityDay> noConfUsedList=new ArrayList<QuotaConfigCityDay>();
		String dataDate=QuotaUtils.getDayDate("yyyyMMdd");
		List<Map<String,Object>> list = quotaConfigCityDayDao.getCityUsedListInMem(dataDate);
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Map<String,Object> map = list.get(i);
				if(map.get("USED_NUM")==null){
					QuotaConfigCityDay temp = new QuotaConfigCityDay();
					try {
						QuotaUtils.map2Bean(map, temp);
						temp.setUsedNum(0);
						noConfUsedList.add(temp);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		}
		
		quotaConfigCityDayDao.addBatchAddUsedNumInMem(noConfUsedList);
	}

	@Override
	//科室日配额去掉了，该方法不用了
	public void setYesterdayConfNum() {
		List<Map<String, Object>> list, listToday;
		String yesterday = QuotaUtils.getYesterday("yyyyMMdd");
		list = this.deptsQuotaStatisticsDao.getConfigedDayInMem(yesterday);
		String today = QuotaUtils.getDayDate("yyyyMMdd");
		listToday = this.deptsQuotaStatisticsDao.getConfigedDayInMem(today);

		if (list != null && list.size() > 0) {
			// 1、修改所有科室昨天的日限额为日使用额
			List<QuotaConfigDeptDay> dayConf = new ArrayList<QuotaConfigDeptDay>();

			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				// QuotaDayDeptUsed usedTemp = new QuotaDayDeptUsed();
				QuotaConfigDeptDay confTemp = new QuotaConfigDeptDay();
				try {
					// QuotaUtils.map2Bean(map, usedTemp);
					QuotaUtils.map2Bean(map, confTemp);
				} catch (Exception e) {
					e.printStackTrace();
				}
				int tempUsed = 0;

				if (map.get("USED_NUM") != null) {
					tempUsed = Integer.parseInt(map.get("USED_NUM").toString());
				}
				confTemp.setDayQuotaNum(tempUsed);

				if (map.get("DATA_DATE") == null) {
					confTemp.setDataDate(yesterday);
				}
				dayConf.add(confTemp);
			}
			quotaConfigDeptDayDao.batchUpdateDayConfNumInMem(dayConf);

			// 2、为每个科室的日使用额插入今天数据
			List<QuotaDayDeptUsed> dayUsed = new ArrayList<QuotaDayDeptUsed>();
			for (int i = 0; i < listToday.size(); i++) {
				Map<String, Object> map = listToday.get(i);
				QuotaDayDeptUsed usedTemp = new QuotaDayDeptUsed();
				try {
					QuotaUtils.map2Bean(map, usedTemp);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if (map.get("DATA_DATE") == null) {
					usedTemp.setDataDate(today);
				}
				if (map.get("USED_NUM") == null) {
					dayUsed.add(usedTemp);
				}

			}
			quotaDayDeptUsedDao.saveBatchSaveInMem(dayUsed);

			// 3、当昨天有结余时，将昨天结余配置分配到后一天，并且要保证后一天的配额不大于平均额的3倍
			int currentDayOfMonth = QuotaUtils.getCurrentDayOfMon();
			String fromDate = QuotaUtils.getDayDate("yyyyMMdd");
			String month = QuotaUtils.getDayMonth("yyyyMM");
			int totalDayOfMonth = QuotaUtils.getMonDays(month);
			String toDate = month + totalDayOfMonth;

			List<QuotaConfigDeptDay> dayConf4add = new ArrayList<QuotaConfigDeptDay>();
			List<QuotaConfigDeptDay> dayConf4update = new ArrayList<QuotaConfigDeptDay>();
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				int yesConf = 0, yesUsed = 0;
				if (map.get("DAY_QUOTA_NUM") != null) {
					yesConf = Integer.parseInt(map.get("DAY_QUOTA_NUM").toString());
				}
				if (map.get("USED_NUM") != null) {
					yesUsed = Integer.parseInt(map.get("USED_NUM").toString());
				}
				int remain = yesConf - yesUsed;

				List<Map<String, Object>> listRemain = quotaConfigDeptDayDao.getDayConfInMem(map.get("CITY_ID").toString(),map.get("DEPT_ID").toString(), fromDate, toDate);// 循环中访问DB。。。
				listRemain = this.escape(listRemain, map.get("CITY_ID").toString(), map.get("DEPT_ID").toString(), fromDate,toDate);

				int mothQuota = quotaConfigDeptMothDao.getQuotaByKeysInMem(map.get("CITY_ID").toString(), map.get("DEPT_ID").toString(), month);
				int average3 = (mothQuota / totalDayOfMonth) * 3;
				for (int j = 0; j <= totalDayOfMonth - currentDayOfMonth; i++) {
					int tempQuota = 0;
					Boolean hasConfed = false;

					if (listRemain.get(j).get("DAY_QUOTA_NUM") != null) {
						tempQuota = Integer.parseInt(listRemain.get(j).get("DAY_QUOTA_NUM").toString());
						hasConfed = true;
					}
					String currentDate = month+ ((currentDayOfMonth + j) >= 10 ? currentDayOfMonth:("0" + currentDayOfMonth));
					if (tempQuota + remain <= average3) {
						QuotaConfigDeptDay tempday = new QuotaConfigDeptDay();
						tempday.setCityId(map.get("CITY_ID").toString());
						tempday.setDeptId(map.get("DEPT_ID").toString());
						tempday.setDayQuotaNum(tempQuota + remain);
						tempday.setDataDate(currentDate);
						tempday.setDataDateM(month);
						if (hasConfed) {
							dayConf4update.add(tempday);
						} else {
							dayConf4add.add(tempday);
						}
						break;

					} else {
						QuotaConfigDeptDay tempday = new QuotaConfigDeptDay();
						tempday.setCityId(map.get("CITY_ID").toString());
						tempday.setDeptId(map.get("DEPT_ID").toString());
						tempday.setDayQuotaNum(average3);
						remain = remain - (average3 - tempQuota);
						tempday.setDataDate(currentDate);
						tempday.setDataDateM(month);
						if (hasConfed) {
							dayConf4update.add(tempday);
						} else {
							dayConf4add.add(tempday);
						}

					}

				}
			}
			quotaConfigDeptDayDao.batchUpdateDayConfNumInMem(dayConf4update);
			quotaConfigDeptDayDao.saveBatchSaveInMem(dayConf4add);
		}

	}

	// 合并DB中查询的日配置（如果DB中没有配置，则主动添加）
	private List<Map<String, Object>> escape(List<Map<String, Object>> list,
			String cityId, String deptid, String from, String to) {
		List<Map<String, Object>> renList = new ArrayList<Map<String, Object>>();
		int fromDate = Integer.parseInt(from);
		int toDate = Integer.parseInt(to);
		for (int i = fromDate; i <= toDate; i++) {
			Boolean hasConf = false;
			String tempDate = i + "";
			if (list != null && list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					Map<String, Object> map = list.get(j);

					if (map.get("CITY_ID").toString().equals(cityId)
							&& map.get("DEPT_ID").toString().equals(deptid)
							&& map.get("DATA_DATE").toString().equals(tempDate)) {// 该科室该日期的科室配置在DB中已经配置
						renList.add(map);
						hasConf = true;
						break;
					}
				}
			}
			if (!hasConf) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("CITY_ID", cityId);
				map.put("DEPT_ID", deptid);
				map.put("DATA_DATE", tempDate);
				map.put("DAY_QUOTA_NUM", null);
				renList.add(map);
			}

		}
		return renList;
	}
}
