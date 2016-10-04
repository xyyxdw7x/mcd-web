package com.asiainfo.biapp.framework.privilege.service;

import java.util.List;

import com.asiainfo.biapp.framework.privilege.vo.Menu;
import com.asiainfo.biapp.framework.privilege.vo.User;

/**
 * 用户权限接口
 * @author hjn
 *
 */
public interface IUserPrivilege {

	/**
	 * 根据用户ID查询用户信息 查询不到返回null
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public User queryUserById(String userId) throws Exception;
	/**
	 * 验证一个用户  如果用户id和密码正确返回用户信息 如果不正确返回null
	 * @param userId
	 * @param userPwd
	 * @return
	 * @throws Exception
	 */
	public User validationUserPwd(String userId,String userPwd) throws Exception;

	/**
	 * 获得用户的所有菜单信息
	 * @param userId
	 * @param isMerge 是否按级别合并菜单
	 * @return
	 * @throws Exception
	 */
	public List<Menu> getUserMenuInfos(String userId,boolean isMerge) throws Exception;
	
	/**
	 * 用户是否是超级管理员
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean isAdmin(String userId) throws Exception;
	
}
