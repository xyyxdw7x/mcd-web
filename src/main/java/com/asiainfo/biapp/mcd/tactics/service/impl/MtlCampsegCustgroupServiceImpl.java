package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.vo.custgroup.McdCustgroupDef;
import com.asiainfo.biapp.mcd.tactics.dao.MtlCampsegCustgroupDao;
import com.asiainfo.biapp.mcd.tactics.service.MtlCampsegCustgroupService;
@Service("mtlCampsegCustgroupService")
public class MtlCampsegCustgroupServiceImpl implements MtlCampsegCustgroupService{

	
	private MtlCampsegCustgroupDao mtlCampsegCustgroupDao;
	@Override
	public List<McdCustgroupDef> getChoiceCustom(String campsegId) {
		return mtlCampsegCustgroupDao.getChoiceCustom(campsegId);
	}
}
