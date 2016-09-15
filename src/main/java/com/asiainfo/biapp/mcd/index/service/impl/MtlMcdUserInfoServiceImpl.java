package com.asiainfo.biapp.mcd.index.service.impl;

import com.asiainfo.biapp.mcd.index.dao.IMtlMcdUserInfoDao;
import com.asiainfo.biapp.mcd.index.service.IMtlMcdUserInfoService;
import com.asiainfo.biapp.mcd.index.vo.MtlMcdUserInfo;

public class MtlMcdUserInfoServiceImpl  implements IMtlMcdUserInfoService{
	private IMtlMcdUserInfoDao mtlMcdUserInfoDao;
	
	public void setMtlMcdUserInfoDao(IMtlMcdUserInfoDao mtlMcdUserInfoDao) {
		this.mtlMcdUserInfoDao = mtlMcdUserInfoDao;
	}

	@Override
	public MtlMcdUserInfo getMtlMcdUserInfo(String userId){
		return mtlMcdUserInfoDao.getMtlMcdUserInfo(userId);
		
	}
	

}
