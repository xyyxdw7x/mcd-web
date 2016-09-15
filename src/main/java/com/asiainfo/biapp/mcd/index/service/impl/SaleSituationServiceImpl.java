package com.asiainfo.biapp.mcd.index.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.index.dao.ISaleSituationDao;
import com.asiainfo.biapp.mcd.index.service.ISaleSituationService;
import com.asiainfo.biapp.mcd.index.vo.CampChannel;
import com.asiainfo.biapp.mcd.index.vo.RecommendCamp;
import com.asiainfo.biapp.mcd.index.vo.SaleSituation;

@Service("saleSituationService")
public class SaleSituationServiceImpl implements ISaleSituationService{
	
	@Autowired
	private ISaleSituationDao saleSituationDao;

	@Override
	public SaleSituation querySaleSituation(String cityId) throws Exception{

		SaleSituation ss = new SaleSituation();
		
        ss.setTotalNum(saleSituationDao.getTotalSale(cityId));
		ss.setTotalSuccessNum(saleSituationDao.getTotalSuccess(cityId));
		
		ss.setSaleNumMon(saleSituationDao.getTotalSaleMonth(cityId));
		ss.setSaleSuccessNumMon(saleSituationDao.getTotalSucessMonth(cityId));
		
		ss.setSaleNumDay(saleSituationDao.getTotalSaleDay(cityId));
		ss.setSaleSuccessNumDay(saleSituationDao.getTotalSuccessDay(cityId));
		return ss;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Pager getRecommendCamp(int pageNum,String city_id){
		Pager page =  saleSituationDao.getRecommendCamp(pageNum,city_id);
		List<RecommendCamp> list = page.getResult();
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				String campId = list.get(i).getCampsegId();
				List<CampChannel> tempList = saleSituationDao.getCampChannel(campId);
				list.get(i).setChannels(tempList);
			}
		}
		return page;
	}
	@Override
	public Pager getMySale(String userId,int pageNum,int pageSize){
		return saleSituationDao.getMySale(userId, pageNum,pageSize);
	}

}
