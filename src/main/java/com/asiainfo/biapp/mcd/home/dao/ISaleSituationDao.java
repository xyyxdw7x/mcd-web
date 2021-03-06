package com.asiainfo.biapp.mcd.home.dao;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.home.vo.CampChannel;

public interface ISaleSituationDao {

	long getTotalSale(String cityId) throws Exception;

	long getTotalSuccess(String cityId);

	long getTotalSaleMonth(String cityId);

	long getTotalSucessMonth(String cityId);

	long getTotalSaleDay(String cityId);

	long getTotalSuccessDay(String cityId);

	List<CampChannel> getCampChannel(String campsegId);

	Pager getRecommendCamp(int pageNum, String city_id);

	Pager getMySale(String userId, int pageNum,int pageSize);
	
	List<Map<String,Object>> getCaliber();

}
