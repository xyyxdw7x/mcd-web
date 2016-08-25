package com.asiainfo.biapp.mcd.privilege.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.framework.privilege.service.IUserPrivilege;
import com.asiainfo.biapp.framework.privilege.vo.Menu;
import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.framework.util.MD5Util;
import com.asiainfo.biapp.mcd.privilege.service.dao.IUserPrivilegeDao;

@Service("defaultUserPrivilege")
public class DefaultUserPrivilege implements IUserPrivilege {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource(name="userPrivilegeDao")
	private IUserPrivilegeDao userPrivilegeDao;
	 

	@Override
	public User validationUserPwd(String userId, String userPwd) throws Exception {
		logger.info("userId="+userId+" userPwd="+userPwd);
		String md5Pwd=MD5Util.encode(userPwd);
		User user=userPrivilegeDao.queryUser(userId, md5Pwd);
		return user;
	}

	@Override
	public List<Menu> getUserMenuInfos(String userId) throws Exception {
		logger.info("userId="+userId);
		List<Menu> menuList=userPrivilegeDao.getUserMenuInfos(userId);
		return menuList;
	}

	@Override
	public boolean isAdmin(String userId) throws Exception {
		return true;
	}
	
	public IUserPrivilegeDao getUserPrivilegeDao() {
		return userPrivilegeDao;
	}

	public void setUserPrivilegeDao(IUserPrivilegeDao userPrivilegeDao) {
		this.userPrivilegeDao = userPrivilegeDao;
	}
}
