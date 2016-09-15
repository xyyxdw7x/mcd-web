package com.asiainfo.biapp.mcd.common.custgroup.service;

import java.util.List;

import com.asiainfo.biapp.mcd.custgroup.vo.McdCustgroupAttrList;
import com.asiainfo.biapp.mcd.tactics.vo.RuleTimeTermLable;

/**
 * 
 * Title: 
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-7-21 上午11:37:16
 * @version 1.0
 */

public interface ICustGroupAttrRelService {
	/**
	 * 根据客户群id获取群发用于变量
	 * @param custGroupId
	 * @return
	 */
	public List<McdCustgroupAttrList> initTermLable(String custGroupId);
	public List<RuleTimeTermLable> getFunctionNameById(String functionId);
	
}
