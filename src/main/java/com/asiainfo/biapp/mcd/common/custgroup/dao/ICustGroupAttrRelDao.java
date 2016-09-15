package com.asiainfo.biapp.mcd.common.custgroup.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.custgroup.vo.McdCustgroupAttrList;
import com.asiainfo.biapp.mcd.tactics.vo.RuleTimeTermLable;

public interface ICustGroupAttrRelDao {
	/**
	 * 根据客户群id获取群发用于变量
	 * @param custGroupId
	 * @return
	 */
	public List<McdCustgroupAttrList> initTermLable(String custGroupId);
	
	public List<RuleTimeTermLable> getFunctionNameById(String functionId);

}
