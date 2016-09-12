package com.asiainfo.biapp.mcd.bull.service;

import java.util.List;

import com.asiainfo.biapp.mcd.bull.vo.CityQuotaStatic;
import com.asiainfo.biapp.mcd.bull.vo.CurrentDateQuota;

public interface CurrentDateQuotaService {

	public List<CurrentDateQuota> getCurrentStatis(String cityId);

	public CityQuotaStatic getCityStatis(String cityId);

}
