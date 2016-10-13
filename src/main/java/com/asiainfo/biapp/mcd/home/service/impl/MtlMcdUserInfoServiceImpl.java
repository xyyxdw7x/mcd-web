package com.asiainfo.biapp.mcd.home.service.impl;

import com.asiainfo.biapp.mcd.home.dao.IMtlMcdUserInfoDao;
import com.asiainfo.biapp.mcd.home.service.IMtlMcdUserInfoService;
import com.asiainfo.biapp.mcd.home.vo.MtlMcdUserInfo;

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
