package com.asiainfo.biapp.mcd.avoid.service;

import java.util.List;

import com.asiainfo.biapp.mcd.avoid.vo.McdBotherAvoid;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.custgroup.vo.McdBotherContactConfig;

/**
 * 
 * Title: 
 * Description: 免打扰用户信息
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author zhaokai 2016-8-2 上午9:16:38
 * @version 1.0
 */
public interface IMcdMtlBotherAvoidService {

	public List searchBotherAvoidUser(Pager pager, McdBotherAvoid mtlBotherAvoid);
	
	public void addBotherAvoidUser(List<McdBotherAvoid> list);
	
	public int chkIsExist(McdBotherAvoid mtl);
		
	public void mdfBotherAvoidUser(List<McdBotherAvoid> list);
	
	public void delBotherAvoidUser(McdBotherAvoid mtl);
	
	public void batchDelBotherAvoidUser(List<McdBotherAvoid> list);
	
	public McdBotherContactConfig getMtlBotherContactConfig(String campsegTypeId,String channelId,int campsegCityType);
	
}
