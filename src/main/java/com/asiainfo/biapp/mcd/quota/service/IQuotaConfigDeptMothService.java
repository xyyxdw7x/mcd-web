package com.asiainfo.biapp.mcd.quota.service;

import java.util.List;

import com.asiainfo.biapp.mcd.quota.vo.DeptMonQuotaDefault;
import com.asiainfo.biapp.mcd.quota.vo.DeptsMonthQuotaStatistics;

public interface IQuotaConfigDeptMothService {

//	public List<QuotaConfigDeptMoth> queryDeptsConf(String cityId,String dataDate) throws Exception;

	public List<DeptsMonthQuotaStatistics> getCityDeptsMonthQuota(String cityId,String dataDate) throws Exception;

//	public List<DeptsQuotaStatistics> getQuotas4Depts(String cityId, String date) throws Exception;
	
	/*public String batchUpdate(List<QuotaConfigDeptMoth> list, String city,String Month) throws Exception;*/

	public int getCityMonthQuota(String cityid);
	public int getNum4Dept(String cityId,String deptId,String month);

	public int getTotal4CityDeptMonth(String cityid, String Month);

	public boolean saveDefault(List<DeptMonQuotaDefault> list, String cityId);
	
	//public void saveOrUpdate(List<QuotaConfigDeptMoth> addList,List<QuotaConfigDeptMoth> updateList);


	public String saveOrUpdate(List<DeptsMonthQuotaStatistics> list, String city, String month);

}
