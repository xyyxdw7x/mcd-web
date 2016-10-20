package com.asiainfo.biapp.mcd.home.service;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.home.vo.SaleSituation;

public interface ISaleSituationService {
	SaleSituation querySaleSituation(String cityId) throws Exception;

	Pager getRecommendCamp(int pageNum,String city_id);

	Pager getMySale(String userId, int pageNum,int pageSize);
	
	List<Map<String,Object>> getCaliber();
}
