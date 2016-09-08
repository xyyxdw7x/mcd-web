package com.asiainfo.biapp.mcd.tactics.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.common.vo.custgroup.MtlGroupInfo;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampsegCustgroup;

public interface MtlCampsegCustgroupDao {
	public abstract void save(MtlCampsegCustgroup transientInstance);
	/**
	 * 根据campsegId删除业务标签或者基础标签 add by lixq10
	 * @param campsegId
	 * @return
	 */
	public boolean deleteLableByCampsegId(String campsegId);
	/**
	 * 删除活动下所有客户群
	 * @param campsegId
	 */
	public abstract void deleteByCampsegId(String campsegId);
	/**
	 * 
	 * @param campsegId  根据campsegId查询客户群与策略的关系
	 * @return
	 */
	public abstract List<MtlGroupInfo> getChoiceCustom(String campsegId);
	
	/**
	 * 
	 * @param campsegId   策略id
	 * @return
	 */
	public List getCustInfoByCampsegId(String campsegId);
}