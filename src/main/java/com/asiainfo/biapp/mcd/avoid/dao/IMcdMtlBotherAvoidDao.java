package com.asiainfo.biapp.mcd.avoid.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.avoid.model.MtlBotherAvoid;
import com.asiainfo.biapp.mcd.common.util.Pager;;

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

	public void addBotherAvoidUser(List<MtlBotherAvoid> list) throws Exception;
	
	public int chkIsExist(MtlBotherAvoid mtl);
		
	public void mdfBotherAvoidUser(List<MtlBotherAvoid> list) ;
	
	public void delBotherAvoidUser(MtlBotherAvoid mtl) ;
	
	public void batchDelBotherAvoidUser(List<MtlBotherAvoid> list) ;
}
