package com.asiainfo.biapp.mcd.quota.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.quota.vo.CityMonthQuota;

public interface IQuotaMothCityUsedDao {

	public void saveBatchSave(List<CityMonthQuota> list);

}
