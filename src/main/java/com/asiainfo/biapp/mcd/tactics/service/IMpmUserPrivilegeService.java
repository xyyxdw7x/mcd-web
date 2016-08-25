package com.asiainfo.biapp.mcd.tactics.service;

import com.asiainfo.biframe.exception.ServiceException;
import com.asiainfo.biframe.privilege.IUser;

/**
 * mpm权限接口；
 * @author chengxc
 *
 */
public interface IMpmUserPrivilegeService {
	/**
	 * 取得用户信息(获取当前Session中存储的IUser实例对象)
	 *
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public abstract IUser getUser(String userId) throws ServiceException;
	
}
