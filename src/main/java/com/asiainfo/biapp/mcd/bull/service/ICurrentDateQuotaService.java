package com.asiainfo.biapp.mcd.bull.service;

import com.asiainfo.biapp.mcd.quota.vo.CityMonthQuota;

public interface ICurrentDateQuotaService {
	public CityMonthQuota getCityStatis(String cityId);
}
