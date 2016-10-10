package com.asiainfo.biapp.mcd.quota.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.quota.dao.IDeptsQuotaStatisticsDao;
import com.asiainfo.biapp.mcd.quota.dao.IQuotaConfigCityMothDao;
import com.asiainfo.biapp.mcd.quota.dao.IQuotaConfigDeptMonthDefaultDao;
import com.asiainfo.biapp.mcd.quota.dao.IQuotaConfigDeptMothDao;
import com.asiainfo.biapp.mcd.quota.dao.IUserDeptLinkDao;
import com.asiainfo.biapp.mcd.quota.service.IQuotaConfigDeptMothService;
import com.asiainfo.biapp.mcd.quota.util.QuotaUtils;
import com.asiainfo.biapp.mcd.quota.vo.DeptMonQuotaDefault;
import com.asiainfo.biapp.mcd.quota.vo.DeptMonthQuota;

@Service("quotaConfigDeptMothService")
public class QuotaConfigDeptMothServiceImp implements IQuotaConfigDeptMothService {
	private static final Logger log = LogManager.getLogger();

	@Resource(name = "quotaConfigDeptMothDao")
	private IQuotaConfigDeptMothDao quotaConfigDeptMothDao;
	@Resource(name = "quotaConfigCityMothDao")	
	private IQuotaConfigCityMothDao quotaConfigCityMothDao;
	
	@Resource(name = "quotaConfigDeptMonthDefaultDao")
	private IQuotaConfigDeptMonthDefaultDao quotaConfigDeptMonthDefaultDao;
	@Resource(name = "deptsQuotaStatisticsDao")
	private IDeptsQuotaStatisticsDao deptsQuotaStatisticsDao;
	@Resource(name = "userDeptLinkDao")
	private IUserDeptLinkDao userDeptLinkDao;

	@Override
	public List<DeptMonthQuota> getCityDeptsMonthQuota(String cityId,String dataDate) throws Exception {

		List<DeptMonthQuota> statisList = null;

		List<Map<String, Object>> cityDepts = userDeptLinkDao.getCityDepts(cityId);//获得地市的所有科室
		List<Map<String, Object>> cityDeptMonQuota = deptsQuotaStatisticsDao.getStatisticsInMem(cityId, dataDate);
		this.combineList(cityDepts, cityDeptMonQuota);
		statisList = this.getStatistics(cityDepts);
		this.setRemain4StatisList(statisList);// 设置剩余值

/*		if (cityDepts != null && cityDepts.size() > 0) {// 有科室的月配额已经配置
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
		}*/
		return statisList;
	}
	/**
	 * 和并对象
	 * @param src
	 * @param target
	 * @return
	 */
	private List<Map<String, Object>> combineList(List<Map<String, Object>> src,List<Map<String, Object>> target){
		if(target==null || target.size()<0){
			return src;
		}
		for(int i =0; i<src.size();i++){
			Map<String, Object> tmp =  src.get(i);
			String srcDeptId = tmp.get("DEPT_ID").toString();
			for(int j =0; j<target.size();j++){
				Map<String, Object> tmp2 = target.get(j);
				String targetDeptId =  tmp2.get("DEPT_ID").toString();
				if(srcDeptId .equals(targetDeptId) ){
					tmp.put("MONTH_QUOTA_NUM",  tmp2.get("MONTH_QUOTA_NUM"));
					tmp.put("USED_NUM", tmp2.get("USED_NUM"));
					tmp.put("DATA_DATE", tmp2.get("DATA_DATE"));
				}
			}
		}
		
		return src;
	}

	private void setRemain4StatisList(List<DeptMonthQuota> list) {
		for (int i = 0; i < list.size(); i++) {
			DeptMonthQuota temp = list.get(i);
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
	public String saveOrUpdate(List<DeptMonthQuota> list, String cityid,String month) {
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
	private Boolean isTotalDeptLeCity(List<DeptMonthQuota> list,String city, String month) {
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
	 * 将List<Map<String, Object>>转换成List<DeptsQuotaStatistics>
	 * @param list
	 * @return
	 */
	private List<DeptMonthQuota> getStatistics(List<Map<String, Object>> list) {
		List<DeptMonthQuota> renObj = new ArrayList<DeptMonthQuota>();

		for (Map<String, Object> map : list) {
			DeptMonthQuota tempObj = new DeptMonthQuota();
			try {
				QuotaUtils.map2Bean(map, tempObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			tempObj.setMonthUsedPercent();
			renObj.add(tempObj);
		}
		return renObj;

	}

	@Override
	public int getNum4Dept(String cityId, String deptId, String month) {
		return quotaConfigDeptMothDao.getQuotaByKeysInMem(cityId, deptId, month);
	}

}
