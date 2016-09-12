package com.asiainfo.biapp.mcd.tactics.service;

import java.util.List;

import com.asiainfo.biapp.mcd.common.vo.custgroup.McdCustgroupDef;

public interface MtlCampsegCustgroupService {
	
	/**
	 * 
	 * @param campsegId  根据campsegId查询客户群与策略的关系
	 * @return
	 */
	public abstract List<McdCustgroupDef> getChoiceCustom(String campsegId);

}
