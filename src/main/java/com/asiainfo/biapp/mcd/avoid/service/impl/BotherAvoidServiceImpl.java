package com.asiainfo.biapp.mcd.avoid.service.impl;

import com.asiainfo.biapp.mcd.avoid.dao.IMcdMtlBotherAvoidDao;
import com.asiainfo.biapp.mcd.avoid.service.IMcdMtlBotherAvoidService;
import com.asiainfo.biapp.mcd.avoid.vo.MtlBotherAvoid;
import com.asiainfo.biapp.mcd.common.util.Pager;
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
	public List searchBotherAvoidUser(Pager pager, MtlBotherAvoid mtlBotherAvoid) {
		
		List data = null;
		try {
			data = mcdMtlBotherAvoidDao.searchBotherAvoidUser(pager, mtlBotherAvoid);

		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	@Override
	public void addBotherAvoidUser(List<MtlBotherAvoid> list) {
		
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
	public int chkIsExist(MtlBotherAvoid mtl) {
		
		int isExist = 0;
		try {
			
			isExist = mcdMtlBotherAvoidDao.findBotherAvoidUserInMem(mtl);
	
		} catch(Exception e) {
			e.printStackTrace();
		}
		return isExist;
	}
	
	@Override
	public void mdfBotherAvoidUser(List<MtlBotherAvoid> list) {
		
		try {
			
			mcdMtlBotherAvoidDao.mdfBotherAvoidUserInMem(list);
	
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void delBotherAvoidUser(MtlBotherAvoid mtl) {
		
		try {
			
			mcdMtlBotherAvoidDao.delBotherAvoidUserInMem(mtl);
	
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void batchDelBotherAvoidUser(List<MtlBotherAvoid> list) {
		
		try {
			
			mcdMtlBotherAvoidDao.batchDelBotherAvoidUserInMem(list);
	
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
