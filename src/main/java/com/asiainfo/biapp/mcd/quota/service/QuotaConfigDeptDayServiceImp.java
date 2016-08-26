package com.asiainfo.biapp.mcd.quota.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.quota.dao.QuotaConfigDeptDayDao;
import com.asiainfo.biapp.mcd.quota.dao.QuotaConfigDeptMothDao;
import com.asiainfo.biapp.mcd.quota.dao.QuotaDayDeptUsedDao;
import com.asiainfo.biapp.mcd.quota.dao.UserDeptLinkDao;
import com.asiainfo.biapp.mcd.quota.model.QuotaConfigDeptDay;
import com.asiainfo.biapp.mcd.quota.util.QuotaUtils;

@Service("quotaConfigDeptDayService")
public class QuotaConfigDeptDayServiceImp implements QuotaConfigDeptDayService {
	@Resource(name = "quotaConfigDeptMothDao")
	private QuotaConfigDeptMothDao quotaConfigDeptMothDao;
	@Resource(name = "quotaConfigDeptDayDao")
	private QuotaConfigDeptDayDao quotaConfigDeptDayDao;
	@Resource(name = "userDeptLinkDao")
	private UserDeptLinkDao userDeptLinkDao;
	@Resource(name = "quotaDayDeptUsedDao")
	private QuotaDayDeptUsedDao quotaDayDeptUsedDao;

	public void setQuotaDayDeptUsedDao(QuotaDayDeptUsedDao quotaDayDeptUsedDao) {
		this.quotaDayDeptUsedDao = quotaDayDeptUsedDao;
	}
	public void setUserDeptLinkDao(UserDeptLinkDao userDeptLinkDao) {
		this.userDeptLinkDao = userDeptLinkDao;
	}
	public void setQuotaConfigDeptMothDao(
			QuotaConfigDeptMothDao quotaConfigDeptMothDao) {
		this.quotaConfigDeptMothDao = quotaConfigDeptMothDao;
	}
	public void setQuotaConfigDeptDayDao(
			QuotaConfigDeptDayDao quotaConfigDeptDayDao) {
		this.quotaConfigDeptDayDao = quotaConfigDeptDayDao;
	}

	@Override
	public List<QuotaConfigDeptDay> getMonthDaysQuota(String cityID,
			String deptId, String month) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = quotaConfigDeptDayDao
				.getMonthDaysQuotaInMem(cityID, deptId, month);
		List<QuotaConfigDeptDay> days = new ArrayList<QuotaConfigDeptDay>();
		if (list != null && list.size() > 0) {

			for (Map<String, Object> map : list) {
				QuotaConfigDeptDay tempObj = new QuotaConfigDeptDay();
				try {
					QuotaUtils.map2Bean(map, tempObj);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				days.add(tempObj);
			}

		}

		return days;
	}

	@Override
	public int getTotal4Days(String cityId, String deptId, String month) {
		return quotaConfigDeptDayDao.getTotal4DaysInMem(cityId, deptId, month);
	}

	@Override
	public Boolean batchUpdateDaysQuota(String cityid, String deptId,
			String month, List<QuotaConfigDeptDay> list) {
		// TODO Auto-generated method stub
		// 不可更新日配额的条件：1.月限额的剩余大于或等于配置的（从明天到月底）限额；2.当天之前的日限额不能调整《包括当天》（这个在前端控制）
		int detptMon = quotaConfigDeptMothDao.getQuotaByKeysInMem(cityid, deptId,month);
		int deptUsedUtilY = quotaDayDeptUsedDao.getMonQutoaTotalUntilYesterdayInMem(cityid, deptId);
		int currentDayConf = quotaConfigDeptDayDao.getDayConfNumByKeys(cityid, deptId,QuotaUtils.getDayDate("yyyyMMdd"));
		int limit = detptMon - (deptUsedUtilY + currentDayConf);
		int tom2monend = getTom2Monthend(list);

		if (tom2monend > limit) {
			return false;
		}
        List<QuotaConfigDeptDay> list2 = this.setMonthValue(list, month);
		quotaConfigDeptDayDao.batchSaveOrUpdateInMem(list2);
		return true;
	}
	
	private List<QuotaConfigDeptDay> setMonthValue(List<QuotaConfigDeptDay> list,String month){
		List<QuotaConfigDeptDay> a = new ArrayList<QuotaConfigDeptDay>();
		for(QuotaConfigDeptDay q : list){
			q.setDataDateM(month);
			a.add(q);
		}
		return a;
	}

	@Override
	public int getRemain(String cityID, String deptId, String month) {
		int remain = 0;
		int detptMonLimit = quotaConfigDeptMothDao.getByKeysInMem(cityID, deptId,
				month).getMonthQuotaNum();
		int daysTotal = 0;
		List<QuotaConfigDeptDay> list = this.getMonthDaysQuota(cityID, deptId,
				month);
		for (QuotaConfigDeptDay day : list) {
			daysTotal += day.getDayQuotaNum();
		}
		remain = detptMonLimit - daysTotal;
		return remain;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getDaysQuota4Mon(String cityID, String deptId, String month) {
		List<QuotaConfigDeptDay> listMonth = this.getMonthDaysQuota(cityID,
				deptId, month);// 只查询一次DB（不能在循环中访问DB）
		List daysQuotaAll = new ArrayList();
		int days = QuotaUtils.getMonDays(month);

		if (listMonth != null && listMonth.size() > 0) {

			for (int i = 1; i <= days; i++) {
				String tempDate;
				String tempDay;
				if (i < 10) {
					tempDay = "0" + i;
				} else {
					tempDay = i + "";
				}
				tempDate = month + tempDay;

				Boolean hasConfiged = false;

				for (int m = 0; m < listMonth.size(); m++) {
					QuotaConfigDeptDay dayConf = listMonth.get(m);
					if (dayConf.getDataDate().equals(tempDate)) {
						daysQuotaAll.add(dayConf.getDayQuotaNum());
						hasConfiged = true;
						break;
					}
				}
				if (hasConfiged == false) {
					daysQuotaAll.add(0);
				}

			}

		} else {
			/*
			 * for (int i = 1; i <= days; i++) { daysQuotaAll.add(0); }
			 */

		}

		return daysQuotaAll;
	}

	@Override
	public String getDeptId(String useId) {
		return userDeptLinkDao.getUserDeptInMem(useId);
	}

	/**
	 * 从明天到月末的限额和
	 * 
	 * @param list
	 * @return
	 */
	private int getTom2Monthend(List<QuotaConfigDeptDay> list) {
		int total = 0;
		int today = QuotaUtils.getCurrentDayOfMon();
		if (today == list.size()) {// 今天是最后一天
			return 0;
		} else {
			for (int day = today; day < list.size(); day++) {
				total += list.get(day).getDayQuotaNum();
			}
		}
		return total;

	}
}
