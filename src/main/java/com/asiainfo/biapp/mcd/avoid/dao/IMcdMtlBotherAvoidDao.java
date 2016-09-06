package com.asiainfo.biapp.mcd.avoid.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.avoid.vo.MtlBotherAvoid;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.custgroup.vo.MtlBotherContactConfig;;

/**
 * 
 * Title: 
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author zhaokai 2016-8-2 上午9:45:25
 * @version 1.0
 */

public interface IMcdMtlBotherAvoidDao {
	
	public List searchBotherAvoidUser(Pager pager, MtlBotherAvoid mtlBotherAvoid);

	public void addBotherAvoidUserInMem(List<MtlBotherAvoid> list) throws Exception;
	
	public int findBotherAvoidUserInMem(MtlBotherAvoid mtl);
		
	public void updateBotherAvoidUserInMem(List<MtlBotherAvoid> list) ;
	
	public void updateDelBotherAvoidUserInMem(MtlBotherAvoid mtl) ;
	
	public void updatebBatchDelBotherAvoidUserInMem(List<MtlBotherAvoid> list) ;
	
	/**
	 * 条件查询
	 * @param campsegTypeId
	 * @param channelId
	 * @return
	 */
	public MtlBotherContactConfig getBotherContactConfig(String campsegTypeId,String channelId,int campsegCityType);
}
