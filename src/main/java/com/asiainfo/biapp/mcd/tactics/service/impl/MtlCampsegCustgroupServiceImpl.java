package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.custgroup.vo.McdCustgroupDef;
import com.asiainfo.biapp.mcd.tactics.dao.ICampsegCustgroupDao;
import com.asiainfo.biapp.mcd.tactics.service.MtlCampsegCustgroupService;
@Service("mtlCampsegCustgroupService")
public class MtlCampsegCustgroupServiceImpl implements MtlCampsegCustgroupService{

	@Resource(name = "mtlCampsegCustgroupDao")
	private ICampsegCustgroupDao mtlCampsegCustgroupDao;
	@Override
	public List<McdCustgroupDef> getChoiceCustom(String campsegId) {
		return mtlCampsegCustgroupDao.getChoiceCustom(campsegId);
	}
	@Override
	public McdCustgroupDef getCustGroupByCamp(String campsegId) {
		McdCustgroupDef rs=null;
		List<McdCustgroupDef> list = mtlCampsegCustgroupDao.getChoiceCustom(campsegId);
		if(list!=null && list.size()>0){
			rs = list.get(0);
		}
		return rs;
	}
}
