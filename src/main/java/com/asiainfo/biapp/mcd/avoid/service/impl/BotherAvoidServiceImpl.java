package com.asiainfo.biapp.mcd.avoid.service.impl;

import com.asiainfo.biapp.mcd.avoid.dao.IMcdMtlBotherAvoidDao;
import com.asiainfo.biapp.mcd.avoid.service.IMcdMtlBotherAvoidService;
import com.asiainfo.biapp.mcd.avoid.vo.McdBotherAvoid;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.custgroup.vo.MtlBotherContactConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

/**
 * 
 * Title: 
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author zhaokai 2016-8-2 上午9:21:52
 * @version 1.0
 */
@Service("botherAvoidService")
public class BotherAvoidServiceImpl implements IMcdMtlBotherAvoidService {
	private static Logger log = LogManager.getLogger();
	
	@Resource(name = "botherAvoidDao")
	private IMcdMtlBotherAvoidDao mcdMtlBotherAvoidDao;
	
	@Override
	public List searchBotherAvoidUser(Pager pager, McdBotherAvoid mtlBotherAvoid) {
		
		List data = null;
		try {
			data = mcdMtlBotherAvoidDao.searchBotherAvoidUser(pager, mtlBotherAvoid);

		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	@Override
	public void addBotherAvoidUser(List<McdBotherAvoid> list) {
		
		try {
			
			mcdMtlBotherAvoidDao.addBotherAvoidUserInMem(list);

		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public IMcdMtlBotherAvoidDao getMcdMtlBotherAvoidDao() {
		return mcdMtlBotherAvoidDao;
	}

	public void setMcdMtlBotherAvoidDao(
			IMcdMtlBotherAvoidDao mcdMtlBotherAvoidDao) {
		this.mcdMtlBotherAvoidDao = mcdMtlBotherAvoidDao;
	}

	@Override
	public int chkIsExist(McdBotherAvoid mtl) {
		
		int isExist = 0;
		try {
			
			isExist = mcdMtlBotherAvoidDao.findBotherAvoidUserInMem(mtl);
	
		} catch(Exception e) {
			e.printStackTrace();
		}
		return isExist;
	}
	
	@Override
	public void mdfBotherAvoidUser(List<McdBotherAvoid> list) {
		
		try {
			
			mcdMtlBotherAvoidDao.updateBotherAvoidUserInMem(list);
	
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void delBotherAvoidUser(McdBotherAvoid mtl) {
		
		try {
			
			mcdMtlBotherAvoidDao.updateDelBotherAvoidUserInMem(mtl);
	
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void batchDelBotherAvoidUser(List<McdBotherAvoid> list) {
		
		try {
			mcdMtlBotherAvoidDao.updatebBatchDelBotherAvoidUserInMem(list);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public MtlBotherContactConfig getMtlBotherContactConfig(String campsegTypeId,String channelId,int campsegCityType){
		return this.mcdMtlBotherAvoidDao.getBotherContactConfig(campsegTypeId, channelId,campsegCityType);
	}
}
