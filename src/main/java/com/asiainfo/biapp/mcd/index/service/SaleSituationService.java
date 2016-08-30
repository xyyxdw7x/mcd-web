package com.asiainfo.biapp.mcd.index.service;

import com.asiainfo.biapp.mcd.model.index.SaleSituation;
import com.asiainfo.biapp.mcd.util.jdbcPage.Pager;

public interface SaleSituationService {
	SaleSituation querySaleSituation(String cityId) throws Exception;

	Pager getRecommendCamp(int pageNum,String city_id);

	Pager getMySale(String userId, int pageNum,int pageSize);
}
