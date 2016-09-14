package com.asiainfo.biapp.mcd.quota.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.quota.dao.DeptsQuotaStatisticsDao;
import com.asiainfo.biapp.mcd.quota.dao.QuotaConfigCityMothDao;
import com.asiainfo.biapp.mcd.quota.dao.QuotaConfigDeptDayDao;
import com.asiainfo.biapp.mcd.quota.dao.QuotaConfigDeptMonthDefaultDao;
import com.asiainfo.biapp.mcd.quota.dao.QuotaConfigDeptMothDao;
import com.asiainfo.biapp.mcd.quota.dao.QuotaDayDeptUsedDao;
import com.asiainfo.biapp.mcd.quota.dao.impl.QuotaDayDeptUsedDaoImp;
import com.asiainfo.biapp.mcd.common.util.CommonUtil;
import com.asiainfo.biapp.mcd.quota.util.QuotaUtils;
import com.asiainfo.biapp.mcd.quota.vo.DeptMonQuotaDefault;
import com.asiainfo.biapp.mcd.quota.vo.DeptsQuotaStatistics;
import com.asiainfo.biapp.mcd.quota.vo.QuotaConfigDeptMoth;
import com.asiainfo.biapp.mcd.quota.vo.QuotaMonthDeptUsed;

@Service("quotaConfigDeptMothService")
public class QuotaConfigDeptMothServiceImp implements QuotaConfigDeptMothService {
	private static final Logger log = LogManager.getLogger();

	@Resource(name = "quotaConfigDeptMothDao")
	private QuotaConfigDeptMothDao quotaConfigDeptMothDao;
	@Resource(name = "quotaConfigCityMothDao")	
	private QuotaConfigCityMothDao quotaConfigCityMothDao;
	@Resource(name = "quotaDayDeptUsedDao")
	private QuotaDayDeptUsedDao quotaDayDeptUsedDao;
	@Resource(name = "quotaConfigDeptDayDao")
	private QuotaConfigDeptDayDao quotaConfigDeptDayDao;
	@Resource(name = "quotaConfigDeptMonthDefaultDao")
	private QuotaConfigDeptMonthDefaultDao quotaConfigDeptMonthDefaultDao;
	@Resource(name = "deptsQuotaStatisticsDao")
	private DeptsQuotaStatisticsDao deptsQuotaStatisticsDao;
	
	public void setDeptsQuotaStatisticsDao(DeptsQuotaStatisticsDao deptsQuotaStatisticsDao) {
		this.deptsQuotaStatisticsDao = deptsQuotaStatisticsDao;
	}
	public void setQuotaConfigDeptMonthDefaultDao(QuotaConfigDeptMonthDefaultDao quotaConfigDeptMonthDefaultDao) {
		this.quotaConfigDeptMonthDefaultDao = quotaConfigDeptMonthDefaultDao;
	}
	public void setQuotaDayDeptUsedDao(QuotaDayDeptUsedDao quotaDayDeptUsedDao) {
		this.quotaDayDeptUsedDao = quotaDayDeptUsedDao;
	}
	public void setQuotaConfigDeptDayDao(QuotaConfigDeptDayDao quotaConfigDeptDayDao) {
		this.quotaConfigDeptDayDao = quotaConfigDeptDayDao;
	}
	public void setQuotaDayDeptUsedDao(QuotaDayDeptUsedDaoImp quotaDayDeptUsedDao) {
		this.quotaDayDeptUsedDao = quotaDayDeptUsedDao;
	}
	public void setQuotaConfigCityMothDao(QuotaConfigCityMothDao quotaConfigCityMothDao) {
		this.quotaConfigCityMothDao = quotaConfigCityMothDao;
	}
	public void setQuotaConfigDeptMothDao(QuotaConfigDeptMothDao quotaConfigDeptMothDao) {
		this.quotaConfigDeptMothDao = quotaConfigDeptMothDao;
	}
	
	

	@Override
	public List<QuotaConfigDeptMoth> queryDeptsConf(String cityId,String dataDate) throws Exception {
		List<Map<String, Object>> mapData = quotaConfigDeptMothDao.getDeptsByDateInMem(cityId, dataDate);
		List<QuotaConfigDeptMoth> list = this.getDeptsMonQuotaList(mapData);
		return list;
	}

	
	@Override
	public List<DeptsQuotaStatistics> getDeptsQuotaStatistics(String cityId,String dataDate) throws Exception {

		List<DeptsQuotaStatistics> statisList = null;

		List<Map<String, Object>> deptMonthList = quotaConfigDeptMothDao.getDeptsByDateInMem(cityId, dataDate);

		if (deptMonthList != null && deptMonthList.size() > 0) {// 有科室的月配额已经配置
			List<Map<String, Object>> statisMap = deptsQuotaStatisticsDao.getStatisticsInMem(cityId, dataDate);
			statisList = this.getStatistics(statisMap);
			this.setRemain4StatisList(statisList);// 设置剩余值

		} else {// 所有科室月配额都没有配置时--- 取模板显示配置显示
				statisList = new ArrayList<DeptsQuotaStatistics>();
				List<Map<String, Object>> templates = quotaConfigDeptMonthDefaultDao.getTempletConfigsInMem(cityId);
				for (int i = 0; i < templates.size(); i++) {
					DeptsQuotaStatistics newStatis = new DeptsQuotaStatistics();
					newStatis.setCityId(templates.get(i).get("CITY_ID").toString());
					newStatis.setDeptName(templates.get(i).get("DEPT_NAME").toString());
					newStatis.setDeptId(templates.get(i).get("DEPT_ID").toString());
					
					//外层if逻辑 added by zhuyq3 2015-11-10 15:39:37
					if (Integer.parseInt(QuotaUtils.getDayMonth("yyyyMM")) > CommonUtil.pickDigits(dataDate)) {
						//历史未配置的默认为0
						newStatis.setMonthQuotaNum(0);
					} else {
						//非历史数据配置科室默认值
						if (templates.get(i).get("MONTH_QUOTA_NUM") != null) {
							newStatis.setMonthQuotaNum(Integer.parseInt(templates.get(i).get("MONTH_QUOTA_NUM").toString()));
						} else {
							newStatis.setMonthQuotaNum(0);
						}
					}
					
					newStatis.setDataDate(dataDate);
					// newStatis.setRecordFlag("update");
					newStatis.setUsedNum(0);
					statisList.add(newStatis);
				}
		}
		return statisList;
	}

	private void setRemain4StatisList(List<DeptsQuotaStatistics> list) {
		for (int i = 0; i < list.size(); i++) {
			DeptsQuotaStatistics temp = list.get(i);
			long configNum = temp.getMonthQuotaNum();
			long usedNum = temp.getUsedNum();
			temp.setRemainNum(configNum - usedNum);
		}
	}

	@Override
	public int getCityMonthQuota(String cityid) {
		return this.quotaConfigCityMothDao.queryCityMonthQuotaInMem(cityid);
	}

	@Override
	public int getTotal4CityDeptMonth(String cityid, String Month) {
		return quotaConfigDeptMothDao.getTotal4CityDeptMonthInMem(cityid, Month);
	}

	@Override
	public boolean saveDefault(List<DeptMonQuotaDefault> list, String cityId) {
		int cityMonthQuota = quotaConfigCityMothDao.queryCityMonthQuotaInMem(cityId);// 地市月配额
		int deptsMonthTotal=0;  //各科室的月配额之和
		for(DeptMonQuotaDefault temp:list){
			int tempQuota = temp.getMonthQuotaNum();
			deptsMonthTotal+=tempQuota;
		}
		if(cityMonthQuota>=deptsMonthTotal){
			quotaConfigDeptMonthDefaultDao.saveBatchSaveInMem(list, cityId);
			return true;
		}
		return false;
	}

	@Override
	public String saveOrUpdate(List<DeptsQuotaStatistics> list, String cityid,
			String month) {
		QuotaUtils.updateProp(list, cityid, month);

		if (!isTotalDeptLeCity(list, cityid, month)) {// 各科室月限额之和大于地市月限额
			return "0";
		}

		quotaConfigDeptMothDao.saveBatchSaveOrUpdateInMem(list);
		return "1";

	}

	/**
	 * 判断各科室月限额之和是否小于或等于地市月限额
	 * 
	 * @param list
	 * @param city
	 * @param month
	 * @return
	 */
	private Boolean isTotalDeptLeCity(List<DeptsQuotaStatistics> list,String city, String month) {
		Boolean renFlag = false;
		int cityMonthQuota = quotaConfigCityMothDao.queryCityMonthQuotaInMem(city);// 地市月配额
		long deptsMonthQuotaTotal = 0; // 地市各科室月配总额
		for (int i = 0; i < list.size(); i++) {
			long temp = list.get(i).getMonthQuotaNum();
			deptsMonthQuotaTotal += temp;
		}
		if (deptsMonthQuotaTotal <= cityMonthQuota) {// 各科室月限额和不能大于地市月限额
			renFlag = true;
		} else {
			log.error("各科室月配额之和大于地市月配额");
		}
		return renFlag;
	}

	/**
	 * 获得不符合最小限额的科室的id(多个科室中间用，分隔)：每个科室的月限额应该>=截止昨天的月使用额+今天的月配额
	 * 
	 * @param list
	 * @return
	 */
	private String getNotSuitMinQuotaDeptes(List<DeptsQuotaStatistics> list) {
		StringBuffer sb = new StringBuffer("");

		for (DeptsQuotaStatistics temp : list) {// 此处循环访问DB（还好数据不大）
			int deptUsedUtilY = this.getMonQutoaTotalUntilYesterday(temp.getCityId(),temp.getDeptId());
			int currentDayConf = quotaConfigDeptDayDao.getDayConfNumByKeys(temp.getCityId(), temp.getDeptId(),QuotaUtils.getDayDate("yyyyMMdd"));
			long deptMonConf = temp.getMonthQuotaNum();
			int limit = deptUsedUtilY + currentDayConf;
			if (deptMonConf < limit) {
				log.debug("{" + temp.getDeptId() + ":" + temp.getDeptName()
						+ "}月限额最小值为：" + limit);
				sb.append("{" + temp.getDeptId() + ":" + temp.getDeptName()
						+ "}最低限额为：" + limit + ",");
			}
		}

		String str = sb.toString();
		if (!"".equals(str)) {// 去掉结尾的逗号
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	/**
	 * 获得科室截止到昨天的使用额总量
	 * 
	 * @param cityId
	 * @param deptId
	 * @return
	 */
	private int getMonQutoaTotalUntilYesterday(String cityId, String deptId) {
		int totalUsed=0;
		String fromDate,toDate;
		String month = QuotaUtils.getDayMonth("yyyyMM");//YYYYmm格式的日期
		fromDate=month+"01";
		int day = QuotaUtils.getCurrentDayOfMon();//当前的日期
		if(day==1){//如果当前日期是这个月的第一天
			return 0;
		}else{
			int yesterday=day-1;
			if(yesterday<10){
				toDate="0"+yesterday;
			}else{
				toDate=yesterday+"";
			}
			
		}
		toDate=month+toDate;
		List<Map<String,Object>> used = quotaDayDeptUsedDao.getUsed4DaysInMem(cityId, deptId, fromDate, toDate);
		if(used!=null&&used.size()>0){
			for(int i=0;i<used.size();i++){
				totalUsed+=Integer.parseInt(used.get(i).get("USED_NUM").toString());
			}
		}

		return totalUsed;
	}
	
	private List<QuotaConfigDeptMoth> getDeptsMonQuotaList(
			List<Map<String, Object>> list) {
		List<QuotaConfigDeptMoth> renObj = new ArrayList<QuotaConfigDeptMoth>();

		for (Map<String, Object> map : list) {
			QuotaConfigDeptMoth tempObj = new QuotaConfigDeptMoth();
			try {
				QuotaUtils.map2Bean(map, tempObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			renObj.add(tempObj);
		}
		return renObj;

	}

	@SuppressWarnings("unused")
	private List<QuotaMonthDeptUsed> getDeptsMonQuotaListUsed(
			List<Map<String, Object>> list) {
		List<QuotaMonthDeptUsed> renObj = new ArrayList<QuotaMonthDeptUsed>();

		for (Map<String, Object> map : list) {
			QuotaMonthDeptUsed tempObj = new QuotaMonthDeptUsed();
			try {
				QuotaUtils.map2Bean(map, tempObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			renObj.add(tempObj);
		}
		return renObj;

	}

	private List<DeptsQuotaStatistics> getStatistics(
			List<Map<String, Object>> list) {
		List<DeptsQuotaStatistics> renObj = new ArrayList<DeptsQuotaStatistics>();

		for (Map<String, Object> map : list) {
			DeptsQuotaStatistics tempObj = new DeptsQuotaStatistics();
			try {
				QuotaUtils.map2Bean(map, tempObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			renObj.add(tempObj);
		}
		return renObj;

	}

	@Override
	public int getNum4Dept(String cityId, String deptId, String month) {
		return quotaConfigDeptMothDao.getQuotaByKeysInMem(cityId, deptId, month);
	}

}
