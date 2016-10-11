package com.asiainfo.biapp.mcd.quota.service;

import java.util.List;

import com.asiainfo.biapp.mcd.quota.vo.DeptMonthQuota;

public interface IDeptMothQuotaService {
	/**
	 * 查询某个地市的所有科室的月配额---配额管理（左侧界面）
	 */
	public List<DeptMonthQuota> getCityDeptsMonthQuota(String cityId,String dataDate) throws Exception;
	/**
	 * 批量保存科室月配额--配额管理左侧界面的保存
	 * @param list
	 * @param city
	 * @param month
	 * @return
	 */
	public String saveOrUpdate(List<DeptMonthQuota> list, String city, String month);
	
	public int getCityMonthQuota(String cityid);
	public int getTotal4CityDeptMonth(String cityid, String Month);
	public boolean saveDefault(List<DeptMonthQuota> list, String cityId);
	
	
	/**
	 * 地市月配额平均分配到地市日配额中
	 */
	public void averageCityMonthQuota();
	/**
	 * 科室月限额：已配置时按照已配置的值；没有配置时取模板值；
	 */
	public void setDeptsCurrentMonthQuota();

}
