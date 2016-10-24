package com.asiainfo.biapp.mcd.tacticsApprove.service;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.common.util.Pager;

/**
 * 
 * Title: 
 * Description: 策略审批
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author zhaokai 2016-10-21 上午10:00:00
 * @version 1.0
 */
public interface TacticsApproveService {

	/**
	 * 获取待审批的策略信息
	 * @return
	 */
	List<Map<String,Object>> getTacticsApproveInfo(Pager pager);
	
}
