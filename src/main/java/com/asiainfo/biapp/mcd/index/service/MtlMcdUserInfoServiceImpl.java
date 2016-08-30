package com.asiainfo.biapp.mcd.index.service;

import com.asiainfo.biapp.mcd.index.dao.MtlMcdUserInfoDao;
import com.asiainfo.biapp.mcd.model.index.MtlMcdUserInfo;

public class MtlMcdUserInfoServiceImpl  implements MtlMcdUserInfoService{
	private MtlMcdUserInfoDao mtlMcdUserInfoDao;
	
	public void setMtlMcdUserInfoDao(MtlMcdUserInfoDao mtlMcdUserInfoDao) {
		this.mtlMcdUserInfoDao = mtlMcdUserInfoDao;
	}

	@Override
	public MtlMcdUserInfo getMtlMcdUserInfo(String userId){
		return mtlMcdUserInfoDao.getMtlMcdUserInfo(userId);
		
	}
	

}
