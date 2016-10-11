package com.asiainfo.biapp.mcd.quota.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.quota.dao.IDeptMonthQuotaDao;
import com.asiainfo.biapp.mcd.quota.dao.ICityDayQuotaDao;
import com.asiainfo.biapp.mcd.quota.dao.ICityMonthQuotaDao;
import com.asiainfo.biapp.mcd.quota.dao.IUserDeptLinkDao;
import com.asiainfo.biapp.mcd.quota.service.IDeptMothQuotaService;
import com.asiainfo.biapp.mcd.quota.util.QuotaUtils;
import com.asiainfo.biapp.mcd.quota.vo.CityDayQuota;
import com.asiainfo.biapp.mcd.quota.vo.CityMonthQuota;
import com.asiainfo.biapp.mcd.quota.vo.DeptMonthQuota;

@Service("quotaConfigDeptMothService")
public class DeptMothQuotaServiceImp implements IDeptMothQuotaService {
	private static final Logger log = LogManager.getLogger();

	@Resource(name = "quotaConfigDeptMothDao")
	private IDeptMonthQuotaDao deptMonthQuotaDao;//科室月配额
	@Resource(name = "quotaConfigCityMothDao")	
	private ICityMonthQuotaDao quotaConfigCityMothDao;//地市月配额
	@Resource(name = "userDeptLinkDao")
	private IUserDeptLinkDao userDeptLinkDao;//人员科室
	@Resource(name = "quotaConfigCityDayDao")
	private ICityDayQuotaDao quotaConfigCityDayDao;//地市日配额

	/**
	 * 查询某个地市的所有科室的月配额---配额管理（左侧界面）
	 */
	@Override
	public List<DeptMonthQuota> getCityDeptsMonthQuota(String cityId,String dataDate) throws Exception {

		List<DeptMonthQuota> statisList = null;

		List<Map<String, Object>> cityDepts = userDeptLinkDao.getCityDepts(cityId);//获得地市的所有科室
		List<Map<String, Object>> cityDeptMonQuota = deptMonthQuotaDao.getStatisticsInMem(cityId, dataDate);
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
		if(CollectionUtils.isEmpty(target)){
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
					break;
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
		return deptMonthQuotaDao.getTotal4CityDeptMonthInMem(cityid, Month);
	}

	@Override
	public boolean saveDefault(List<DeptMonthQuota> list, String cityId) {
		int cityMonthQuota = quotaConfigCityMothDao.queryCityMonthQuotaInMem(cityId);// 地市月配额
		int deptsMonthTotal=0;  //各科室的月配额之和
		for(DeptMonthQuota temp:list){
			long tempQuota = temp.getMonthQuotaNum();
			deptsMonthTotal+=tempQuota;
		}
		if(cityMonthQuota>=deptsMonthTotal){
			deptMonthQuotaDao.saveBatchSaveDefaultInMem(list, cityId);
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
		deptMonthQuotaDao.saveBatchSaveOrUpdateConfigInMem(list);
		return "1";

	}

	//判断各科室月限额之和是否小于或等于地市月限额
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


	//将List<Map<String, Object>>转换成List<DeptMonthQuota>
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


	/**
	 * 地市月配额平均分配到地市日配额中
	 */
	@Override
	public void averageCityMonthQuota(){
			
			List<Map<String,Object>>  cityMonthQuotaList = this.quotaConfigCityMothDao.queryAllInMem();
			
			if(cityMonthQuotaList!=null && cityMonthQuotaList.size()>0){
				List<CityMonthQuota>  cityMonth = new ArrayList<CityMonthQuota>();
				
				for(int i=0;i<cityMonthQuotaList.size();i++){
					Map<String,Object> map = cityMonthQuotaList.get(i);
					CityMonthQuota temp = new CityMonthQuota();
					try {
						QuotaUtils.map2Bean(map, temp);
						cityMonth.add(temp);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				List<CityDayQuota> cityDays = null;
				cityDays = this.cityMonth2DayForToday(cityMonth);
				//插入前先删除本月的所有地市日配额，以防之前已有地市日配额时报错
				quotaConfigCityDayDao.delCityDayQuota4Month(QuotaUtils.getDayMonth("yyyyMM"));
				quotaConfigCityDayDao.addBatchAddCitysDayQuotaInMem(cityDays);
			}
		}
		
	private List<CityDayQuota> cityMonth2DayForToday(List<CityMonthQuota> citysMonthQuota){
			
			String currentMonth = QuotaUtils.getDayMonth("yyyyMM");
			int monthDayNum = QuotaUtils.getMonDays(currentMonth) ;
			List<CityDayQuota> days = new ArrayList<CityDayQuota>();
			for(int i=0;i<citysMonthQuota.size();i++){
				CityMonthQuota tempMonth = citysMonthQuota.get(i);
				int tempCityMonthQuota = tempMonth.getMonthQuotaNum();
				int arverageNum = tempCityMonthQuota/monthDayNum;
				int lastDayNum = arverageNum;
				if(tempCityMonthQuota%monthDayNum!=0){
					lastDayNum = arverageNum+(tempCityMonthQuota%monthDayNum);
				}
				for(int j=0;j<monthDayNum;j++){
					int tempDay = j+1;
					CityDayQuota temp = new CityDayQuota();
					temp.setCityId(tempMonth.getCityId());
					temp.setDataDateM(currentMonth);
					
					if(tempDay<10){
						temp.setDataDate(currentMonth+"0"+tempDay);
					}else{
						temp.setDataDate(currentMonth+tempDay);
					}
					
					if(j!=monthDayNum-1){
						temp.setDayQuotaNum(arverageNum);
					}else{
						temp.setDayQuotaNum(lastDayNum);
					}
					days.add(temp);
				}
			}
			
			return days;
		}

	
	/**
	 * 科室月限额：已配置时按照已配置的值；没有配置时取模板值；
	 */
	@Override
	public void setDeptsCurrentMonthQuota() {
		String month = QuotaUtils.getDayMonth("yyyyMM");
		List<Map<String, Object>> allDepts = userDeptLinkDao.getAllDepts();//所有科室(所有地市的)	
		if(CollectionUtils.isEmpty(allDepts)){
			return;
		}
		List<Map<String, Object>> allDeptMonthQuota = deptMonthQuotaDao.getAllDeptMonthQuotaInMem(month);	
		this.combineList(allDepts, allDeptMonthQuota);
		List<DeptMonthQuota> allDeptsQuota = this.getStatistics(allDepts);//---
		
		List<Map<String, Object>> allDeptMonthQuotaDef = deptMonthQuotaDao.queryAllDeptDefQuotaInMem();	
		
		if(!CollectionUtils.isEmpty(allDeptMonthQuotaDef)){
			List<DeptMonthQuota> allDeptsDefQuota = this.getStatistics(allDeptMonthQuotaDef);//--
			List<DeptMonthQuota> addList = new ArrayList<DeptMonthQuota>();
			for(int i=0;i<allDeptsQuota.size();i++){
				DeptMonthQuota tmpConf = allDeptsQuota.get(i);
				long tmpConfNum = tmpConf.getMonthQuotaNum();
				for(int j=0;j<allDeptsDefQuota.size();j++){
					DeptMonthQuota tmpDef = allDeptsDefQuota.get(i);
					if(tmpConfNum==0){//科室没有配置月配额
						if(tmpConf.getDeptId().equals(tmpDef.getDeptId())){
							tmpConf.setMonthQuotaNum(tmpDef.getDefaultMonthQuotaNum());
							addList.add(tmpConf);
							break;
						}
					}
				}
			}
			deptMonthQuotaDao.saveBatchSaveDeptMonConfInMem(addList);
		}
	}
	
	
}
