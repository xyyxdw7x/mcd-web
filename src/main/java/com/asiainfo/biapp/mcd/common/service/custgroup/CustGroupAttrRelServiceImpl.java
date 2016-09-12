package com.asiainfo.biapp.mcd.common.service.custgroup;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.dao.custgroup.CustGroupAttrRelDao;
import com.asiainfo.biapp.mcd.custgroup.vo.McdCustgroupAttrList;
import com.asiainfo.biapp.mcd.tactics.vo.RuleTimeTermLable;
@Service("custGroupAttrRelService")
public class CustGroupAttrRelServiceImpl implements CustGroupAttrRelService{
	@Resource(name="custGroupAttrRelDao")
	private CustGroupAttrRelDao custGroupAttrRelDao;

	@Override
	public List<McdCustgroupAttrList> initTermLable(String custGroupId) {
		List<McdCustgroupAttrList> mtlGroupAttrRelList = null;
		try {
			mtlGroupAttrRelList = this.custGroupAttrRelDao.initTermLable(custGroupId);
		} catch (Exception e) {
		}
		return mtlGroupAttrRelList;
	}
	
	public List<RuleTimeTermLable> getFunctionNameById(String functionId){
		List<RuleTimeTermLable> list = null;
		try {
			list = this.custGroupAttrRelDao.getFunctionNameById(functionId);
		} catch (Exception e) {
		}
		return list;
	}
}
