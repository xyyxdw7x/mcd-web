package com.asiainfo.biapp.mcd.common.service.custgroup;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.dao.custgroup.CustGroupAttrRelDao;
import com.asiainfo.biapp.mcd.custgroup.model.MtlGroupAttrRel;
@Service("custGroupAttrRelService")
public class CustGroupAttrRelServiceImpl implements CustGroupAttrRelService{
	@Resource(name="custGroupAttrRelDao")
	private CustGroupAttrRelDao custGroupAttrRelDao;

	@Override
	public List<MtlGroupAttrRel> initTermLable(String custGroupId) {
		List<MtlGroupAttrRel> mtlGroupAttrRelList = null;
		try {
			mtlGroupAttrRelList = this.custGroupAttrRelDao.initTermLable(custGroupId);
		} catch (Exception e) {
		}
		return mtlGroupAttrRelList;
	}
}