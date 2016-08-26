package com.asiainfo.biapp.mcd.custgroup.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.custgroup.dao.CustGroupInfoDao;
import com.asiainfo.biapp.mcd.custgroup.service.CustGroupInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmUserPrivilegeService;
import com.asiainfo.biapp.mcd.tactics.vo.MtlGroupInfo;
import com.asiainfo.biframe.privilege.IUser;
import com.asiainfo.biframe.utils.string.StringUtil;
@Service("custGroupInfoService")
public class CustGroupInfoServiceImpl implements CustGroupInfoService{
	@Resource(name="custGroupInfoDao")
	CustGroupInfoDao custGroupInfoDao;
	@Resource(name="mpmUserPrivilegeService")
	IMpmUserPrivilegeService mpmUserPrivilegeService;
	public void setCustGroupInfoDao(CustGroupInfoDao custGroupInfoDao) {
		this.custGroupInfoDao = custGroupInfoDao;
	}
	@Override
	public int getMoreMyCustomCount(String currentUserId,String keyWords) {
		int num = 0;
		try {
			num = custGroupInfoDao.getMoreMyCustomCount(currentUserId,keyWords);
		} catch (Exception e) {
		}
		return num;
	}
	@Override
	public List<MtlGroupInfo> getMoreMyCustom(String currentUserId,String keyWords,Pager pager) {
		List<MtlGroupInfo> custGroupList = null;
		try {
			custGroupList = custGroupInfoDao.getMoreMyCustom(currentUserId,keyWords,pager);
			if(CollectionUtils.isNotEmpty(custGroupList)){
				for(int i = 0 ; i < custGroupList.size(); i++) {
					MtlGroupInfo mtlGroupInfo = custGroupList.get(i);
					String userName = "";
					if((StringUtil.isEmpty(mtlGroupInfo.getCreateUserName()) || mtlGroupInfo.getCreateUserName() == "null") && StringUtil.isNotEmpty(mtlGroupInfo.getCreateUserId())) {
						IUser user = mpmUserPrivilegeService.getUser(mtlGroupInfo.getCreateUserId());
						if(user != null) {
							userName = user.getUsername();
						}
						mtlGroupInfo.setCreateUserName(userName);
					}
				}
			}
		} catch (Exception e) {
		}
		return custGroupList;
	}
	
	@Override
	public List<MtlGroupInfo> getMyCustGroup(String currentUserId) {
		List<MtlGroupInfo> custGroupList = null;
		try {
			custGroupList = custGroupInfoDao.getMyCustGroup(currentUserId);
		} catch (Exception e) {
		}
		return custGroupList;
	}

}
