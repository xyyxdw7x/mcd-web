package com.asiainfo.biapp.mcd.index.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.model.index.RecommendCamp;
import com.asiainfo.biapp.mcd.util.jdbcPage.Pager;

public interface SaleSituationDao {

	long getTotalSale(String cityId) throws Exception;

	long getTotalSuccess(String cityId);

	long getTotalSaleMonth(String cityId);

	long getTotalSucessMonth(String cityId);

	long getTotalSaleDay(String cityId);

	long getTotalSuccessDay(String cityId);

	List getCampChannel(String campsegId);

	Pager getRecommendCamp(int pageNum, String city_id);

	Pager getMySale(String userId, int pageNum,int pageSize);

}
