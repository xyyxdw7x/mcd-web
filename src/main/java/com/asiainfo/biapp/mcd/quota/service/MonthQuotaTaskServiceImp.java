package com.asiainfo.biapp.mcd.quota.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.quota.dao.DeptsQuotaStatisticsDao;
import com.asiainfo.biapp.mcd.quota.dao.QuotaConfigCityDayDao;
import com.asiainfo.biapp.mcd.quota.dao.QuotaConfigCityMothDao;
import com.asiainfo.biapp.mcd.quota.dao.QuotaConfigDeptMothDao;
import com.asiainfo.biapp.mcd.quota.dao.QuotaMonthDeptUsedDao;
import com.asiainfo.biapp.mcd.quota.dao.QuotaMothCityUsedDao;
import com.asiainfo.biapp.mcd.quota.util.QuotaUtils;
import com.asiainfo.biapp.mcd.quota.vo.QuotaConfigCityDay;
import com.asiainfo.biapp.mcd.quota.vo.QuotaConfigCityMoth;
import com.asiainfo.biapp.mcd.quota.vo.QuotaConfigDeptMoth;
import com.asiainfo.biapp.mcd.quota.vo.QuotaMonthCityUsed;
import com.asiainfo.biapp.mcd.quota.vo.QuotaMonthDeptUsed;

public class MonthQuotaTaskServiceImp implements MonthQuotaTaskService {

	private DeptsQuotaStatisticsDao deptsQuotaStatisticsDao;
	private QuotaMothCityUsedDao quotaMothCityUsedDao;
	private QuotaConfigCityMothDao quotaConfigCityMothDao;
	private QuotaConfigDeptMothDao quotaConfigDeptMothDao;
	private QuotaMonthDeptUsedDao quotaMonthDeptUsedDao;
	private QuotaConfigCityDayDao quotaConfigCityDayDao;
	
	public void setQuotaConfigCityDayDao(QuotaConfigCityDayDao quotaConfigCityDayDao) {
		this.quotaConfigCityDayDao = quotaConfigCityDayDao;
	}

	public void setQuotaConfigCityMothDao(QuotaConfigCityMothDao quotaConfigCityMothDao) {
		this.quotaConfigCityMothDao = quotaConfigCityMothDao;
	}
	
	public void setQuotaConfigDeptMothDao(QuotaConfigDeptMothDao quotaConfigDeptMothDao) {
		this.quotaConfigDeptMothDao = quotaConfigDeptMothDao;
	}

	public void setQuotaMonthDeptUsedDao(QuotaMonthDeptUsedDao quotaMonthDeptUsedDao) {
		this.quotaMonthDeptUsedDao = quotaMonthDeptUsedDao;
	}

	public void setDeptsQuotaStatisticsDao(DeptsQuotaStatisticsDao deptsQuotaStatisticsDao) {
		this.deptsQuotaStatisticsDao = deptsQuotaStatisticsDao;
	}

	public void setQuotaMothCityUsedDao(QuotaMothCityUsedDao quotaMothCityUsedDao) {
		this.quotaMothCityUsedDao = quotaMothCityUsedDao;
	}
	@Override
	public void execTaskMonthQuota(){
		this.setCityUsed4CurrentMonth();
		this.averageCityMonthQuota();
		this.setDeptUsedAndConf4CurrentMonth();
	}

	// 没有月使用额的地市添加月使用额（0）
	@Override
	public void setCityUsed4CurrentMonth() {

		List<QuotaMonthCityUsed> useds = new ArrayList<QuotaMonthCityUsed>();
		String month = QuotaUtils.getDayMonth("yyyyMM");
		List<Map<String, Object>> list = deptsQuotaStatisticsDao.getConfigedCityUsedInMem(month);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				if(map.get("USED_NUM")==null){
					QuotaMonthCityUsed temp = new QuotaMonthCityUsed();
					try {
						QuotaUtils.map2Bean(map, temp);
						temp.setUsedNum(0);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if(map.get("DATA_DATE")==null){
						temp.setDataDate(month);
					}
					
					useds.add(temp);
					
				}
				
			}
			quotaMothCityUsedDao.saveBatchSave(useds);
		}
	}
	//地市月配额平均分配到地市日配额中
	public void averageCityMonthQuota(){
		
		List<Map<String,Object>>  cityMonthQuotaList = this.quotaConfigCityMothDao.queryAllInMem();
		
		if(cityMonthQuotaList!=null && cityMonthQuotaList.size()>0){
			List<QuotaConfigCityMoth>  cityMonth = new ArrayList<QuotaConfigCityMoth>();
			
			for(int i=0;i<cityMonthQuotaList.size();i++){
				Map<String,Object> map = cityMonthQuotaList.get(i);
				QuotaConfigCityMoth temp = new QuotaConfigCityMoth();
				try {
					QuotaUtils.map2Bean(map, temp);
					cityMonth.add(temp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			List<QuotaConfigCityDay> cityDays = null;
			cityDays = this.cityMonth2DayForToday(cityMonth);
			//插入前先删除本月的所有地市日配额，以防之前已有地市日配额时报错
			quotaConfigCityDayDao.delCityDayQuota4Month(QuotaUtils.getDayMonth("yyyyMM"));
			quotaConfigCityDayDao.addBatchAddCitysDayQuotaInMem(cityDays);
		}
	}
	
	private List<QuotaConfigCityDay> cityMonth2DayForToday(List<QuotaConfigCityMoth> citysMonthQuota){
		
		String currentMonth = QuotaUtils.getDayMonth("yyyyMM");
		int monthDayNum = QuotaUtils.getMonDays(currentMonth) ;
		List<QuotaConfigCityDay> days = new ArrayList<QuotaConfigCityDay>();
		for(int i=0;i<citysMonthQuota.size();i++){
			QuotaConfigCityMoth tempMonth = citysMonthQuota.get(i);
			int tempCityMonthQuota = tempMonth.getMonthQuotaNum();
			int arverageNum = tempCityMonthQuota/monthDayNum;
			int lastDayNum = arverageNum;
			if(tempCityMonthQuota%monthDayNum!=0){
				lastDayNum = arverageNum+(tempCityMonthQuota%monthDayNum);
			}
			for(int j=0;j<monthDayNum;j++){
				int tempDay = j+1;
				QuotaConfigCityDay temp = new QuotaConfigCityDay();
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

	// 科室月限额：已配置时按照已配置的值；没有配置时取模板值； 科室使用额：为所有科室都添加一条记录（usedNum为0）
	@Override
	public void setDeptUsedAndConf4CurrentMonth() {
		String month = QuotaUtils.getDayMonth("yyyyMM");
		List<QuotaMonthDeptUsed> useds = new ArrayList<QuotaMonthDeptUsed>();
		List<QuotaConfigDeptMoth> confs = new ArrayList<QuotaConfigDeptMoth>();
		
		//List<QuotaConfigDeptMoth> noConfDay = new ArrayList<QuotaConfigDeptMoth>();

		//查询当月所有科室的月配额（月配额、月使用额、默认配额）
		List<Map<String, Object>> list = deptsQuotaStatisticsDao.getDeptQuotaStaticInMem(month);
		//查询当月配置了日配额的所有科室
		/*List<Map<String, String>> confDaysDept = (List<Map<String, String>>)quotaConfigDeptDayDao.queryConfigDept(month);
		List<String> confDaysDeptList = this.maplist2Strlist(confDaysDept);*/
		
		if (list != null && list.size() > 0) {

			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				
				QuotaConfigDeptMoth tempConf = new QuotaConfigDeptMoth();
				QuotaMonthDeptUsed tempUsed = new QuotaMonthDeptUsed();

				try {
					QuotaUtils.map2Bean(map, tempConf);
					QuotaUtils.map2Bean(map, tempUsed);

					tempConf.setDataDate(month);
					tempUsed.setDataDate(month);
					
					if (map.get("MONTH_QUOTA_NUM") == null) {// 如果科室没月限额。（放入待插入列表confs）
						if(map.get("DEFNUM")!=null){//科室的模板有月限额
							int defNum = Integer.parseInt(map.get("DEFNUM").toString());
							tempConf.setMonthQuotaNum(defNum);
						}else{
							tempConf.setMonthQuotaNum(0);
						}
						confs.add(tempConf);
					}
					
					if(map.get("USED_NUM") == null){//如果科室没有月使用额，则向使用额列表中插入一天记录，使用量为0
						tempUsed.setUsedNum(0);
						useds.add(tempUsed);
					}
					
					

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				/*String tempDept= tempConf.getDeptId();*/
				
				/*if(!confDaysDeptList.contains(tempDept)){
					noConfDay.add(tempConf);
				}*/
			}

			quotaConfigDeptMothDao.saveBatchSaveInMem(confs);
			quotaMonthDeptUsedDao.saveBatchSaveInMem(useds);
			/*//将月限额平均到日限额中
			List<QuotaConfigDeptDay> days = this.monthlist2Daylist(noConfDay, month);
			quotaConfigDeptDayDao.batchSave(days);*/
		}
	}
	
	/*private List<String> maplist2Strlist(List<Map<String,String>> list){
		List<String> listStr = new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			Map<String,String> temp = list.get(i);
			String value = temp.get("DEPT_ID");
			listStr.add(value);
		}
		return listStr;
	}
	//根据科室月限额列表得到日限额列表
	private List<QuotaConfigDeptDay> monthlist2Daylist(List<QuotaConfigDeptMoth> list,String month){
		List<QuotaConfigDeptDay> listDay = new ArrayList<QuotaConfigDeptDay>();
		int monthDayNum = QuotaUtils.getMonDays(month);
		for(int i=0;i<list.size();i++){
			QuotaConfigDeptMoth temp = list.get(i);
			for(int j=1;j<=monthDayNum;j++){
				QuotaConfigDeptDay day = new QuotaConfigDeptDay();
				day.setCityId(temp.getCityId());
				day.setDataDateM(month);
				int dayNum = temp.getMonthQuotaNum()/monthDayNum;
				day.setDayQuotaNum(dayNum);
				day.setDeptId(temp.getDeptId());
				String tmpDataDate;
				if(j<10){
					tmpDataDate = month+"0"+j;
				}else{
					tmpDataDate = month+j;
				}
				day.setDataDate(tmpDataDate);
				listDay.add(day);
			}
		}
		return listDay;
		
	}*/
	

}
