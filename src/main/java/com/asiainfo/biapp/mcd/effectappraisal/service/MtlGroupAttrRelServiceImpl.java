package com.asiainfo.biapp.mcd.effectappraisal.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.effectappraisal.dao.IMtlGroupAttrRelDao;
import com.asiainfo.biapp.mcd.model.DimMtlAdivInfo;
import com.asiainfo.biapp.mcd.model.MtlGroupAttrRel;
import com.asiainfo.biapp.mcd.model.RuleTimeTermLable;

/**
 * 
 * Title: 
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-7-21 上午11:39:55
 * @version 1.0
 */
@Service("mtlGroupAttrRelService")
public class MtlGroupAttrRelServiceImpl implements IMtlGroupAttrRelService {
	private final static Logger log = LogManager.getLogger();
	@Autowired
	private IMtlGroupAttrRelDao mtlGroupAttrRelDao;
	@Override
	public List<MtlGroupAttrRel> initTermLable(String custGroupId) {
		List<MtlGroupAttrRel> mtlGroupAttrRelList = null;
		try {
			mtlGroupAttrRelList = this.mtlGroupAttrRelDao.initTermLable(custGroupId);
		} catch (Exception e) {
			log.error("",e);
		}
		return mtlGroupAttrRelList;
	}
	
	@Override
	public List<DimMtlAdivInfo> initDimMtlAdivInfo(String channelId,String planId) {
		List<DimMtlAdivInfo> resultList = null;
		try {
			resultList = this.mtlGroupAttrRelDao.initDimMtlAdivInfo(channelId,planId);
			/*if(CollectionUtils.isNotEmpty(resultListTemp)){   //去重复运营位
				Set set = new HashSet();
				Set<DimMtlAdivInfo> tt = new HashSet<DimMtlAdivInfo>();
				for(int i = 0;i<resultListTemp.size();i++){
					DimMtlAdivInfo obj = resultListTemp.get(i);
					set.add(obj.getAdivId());
				}
			Iterator i = set.iterator();
			String adivids = "";
			while (i.hasNext()) {
				adivids += "'"+i.next()+"',";
			}
			resultList = this.mtlGroupAttrRelDao.getAdivInfo(adivids.substring(0,adivids.length()-1));	
			}*/
		} catch (Exception e) {
			log.error("",e);
		}
		return resultList;
	}
	
	public IMtlGroupAttrRelDao getMtlGroupAttrRelDao() {
		return mtlGroupAttrRelDao;
	}
	public void setMtlGroupAttrRelDao(IMtlGroupAttrRelDao mtlGroupAttrRelDao) {
		this.mtlGroupAttrRelDao = mtlGroupAttrRelDao;
	}

	@Override
	public List<RuleTimeTermLable> initRuleTimeTermLable() {
		List<RuleTimeTermLable> list = null;
		try {
			list = this.mtlGroupAttrRelDao.initRuleTimeTermLable();
		} catch (Exception e) {
			log.error(e);
		}
		return list;
	}
	
	public List<RuleTimeTermLable> getFunctionNameById(String functionId){
		List<RuleTimeTermLable> list = null;
		try {
			list = this.mtlGroupAttrRelDao.getFunctionNameById(functionId);
		} catch (Exception e) {
			log.error(e);
		}
		return list;
	}
	
	@Override
	public List<RuleTimeTermLable> initRuleTimeTermSonLable(String sceneId){
		List<RuleTimeTermLable> list = null;
		try {
			list = this.mtlGroupAttrRelDao.initRuleTimeTermSonLable(sceneId);
		} catch (Exception e) {
			log.error(e);
		}
		return list;
	}

	@Override
	public List initAdivInfoByChannelId(String cityId) {
		List list = null;
		try {
			list = this.mtlGroupAttrRelDao.initAdivInfoByChannelId(cityId);
		} catch (Exception e) {
			log.error("", e);
		}
		return list;
	}
}
