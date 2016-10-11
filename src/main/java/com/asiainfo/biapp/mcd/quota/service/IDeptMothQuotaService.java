package com.asiainfo.biapp.mcd.quota.service;

import java.util.List;

import com.asiainfo.biapp.mcd.quota.vo.DeptMonthQuota;

public interface IDeptMothQuotaService {
	public List<DeptMonthQuota> getCityDeptsMonthQuota(String cityId,String dataDate) throws Exception;
	public int getCityMonthQuota(String cityid);
	public int getTotal4CityDeptMonth(String cityid, String Month);
	public boolean saveDefault(List<DeptMonthQuota> list, String cityId);
	public String saveOrUpdate(List<DeptMonthQuota> list, String city, String month);

}
