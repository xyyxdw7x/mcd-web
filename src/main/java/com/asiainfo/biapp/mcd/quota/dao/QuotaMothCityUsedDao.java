package com.asiainfo.biapp.mcd.quota.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.quota.model.QuotaMonthCityUsed;

public interface QuotaMothCityUsedDao {

	public void batchSave(List<QuotaMonthCityUsed> list);

}
