package com.asiainfo.biapp.mcd.bull.service;

import java.util.List;

import com.asiainfo.biapp.mcd.model.quota.CityQuotaStatic;
import com.asiainfo.biapp.mcd.model.quota.CurrentDateQuota;

public interface CurrentDateQuotaService {

	public List<CurrentDateQuota> getCurrentStatis(String cityId);

	public CityQuotaStatic getCityStatis(String cityId);

}
