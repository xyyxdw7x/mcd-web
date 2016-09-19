package com.asiainfo.biapp.mcd.tactics.service;

import java.util.List;

import com.asiainfo.biapp.mcd.common.custgroup.vo.McdCustgroupDef;

public interface MtlCampsegCustgroupService {
	
	/**
	 * 
	 * @param campsegId  根据campsegId查询客户群与策略的关系
	 * @return
	 */
	public abstract List<McdCustgroupDef> getChoiceCustom(String campsegId);

	/**
	 * 根据策略id获得客户群，策略和客户群是一对多的关系，一个策略只能获得一个客户群
	 * @param campsegId
	 * @return
	 */
	McdCustgroupDef getCustGroupByCamp(String campsegId);

}
