package com.asiainfo.biapp.mcd.avoid.dao;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.avoid.vo.McdBotherAvoid;
import com.asiainfo.biapp.mcd.custgroup.vo.McdBotherContactConfig;;

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
	
	public List<Map<String,Object>> searchBotherAvoidUser(String sql ,List<Object> param);

	public void addBotherAvoidUserInMem(List<McdBotherAvoid> list) throws Exception;
	
	public int findBotherAvoidUserInMem(McdBotherAvoid mtl);
		
	public void updateBotherAvoidUserInMem(List<McdBotherAvoid> list) ;
	
	public void updateDelBotherAvoidUserInMem(McdBotherAvoid mtl) ;
	
	public void updatebBatchDelBotherAvoidUserInMem(List<McdBotherAvoid> list) ;
	
	/**
	 * 条件查询
	 * @param campsegTypeId
	 * @param channelId
	 * @return
	 */
	public McdBotherContactConfig getBotherContactConfig(String campsegTypeId,String channelId,int campsegCityType);
}
