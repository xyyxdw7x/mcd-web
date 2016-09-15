package com.asiainfo.biapp.mcd.quota.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.quota.vo.QuotaMonthCityUsed;

public interface IQuotaMothCityUsedDao {

	public void saveBatchSave(List<QuotaMonthCityUsed> list);

}
