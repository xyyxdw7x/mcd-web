package com.asiainfo.biapp.mcd.custgroup.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.custgroup.model.MtlGroupAttrRel;

public interface CustGroupAttrRelDao {
	/**
	 * 根据客户群id获取群发用于变量
	 * @param custGroupId
	 * @return
	 */
	public List<MtlGroupAttrRel> initTermLable(String custGroupId);

}
