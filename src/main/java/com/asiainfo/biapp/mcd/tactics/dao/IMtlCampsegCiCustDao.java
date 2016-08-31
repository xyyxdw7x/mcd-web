package com.asiainfo.biapp.mcd.tactics.dao;

import com.asiainfo.biapp.mcd.tactics.vo.MtlCampsegCiCustgroup;

public interface IMtlCampsegCiCustDao {
	public abstract void save(MtlCampsegCiCustgroup transientInstance);
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
}