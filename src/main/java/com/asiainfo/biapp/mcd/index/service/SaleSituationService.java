package com.asiainfo.biapp.mcd.index.service;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.index.vo.SaleSituation;

public interface SaleSituationService {
	SaleSituation querySaleSituation(String cityId) throws Exception;

	Pager getRecommendCamp(int pageNum,String city_id);

	Pager getMySale(String userId, int pageNum,int pageSize);
}
