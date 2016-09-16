package com.asiainfo.biapp.mcd.privilege.dao;

import java.util.List;

import com.asiainfo.biapp.framework.privilege.vo.Menu;
import com.asiainfo.biapp.framework.privilege.vo.User;

/**
 * 用户权限接口DAO
 * @author hjn
 *
 */
public interface IUserPrivilegeDao {

	/**
	 * 根据用户ID查询用户信息 查询不到返回null
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public User queryUserById(String userId) throws Exception;
	
	/**
	 * 根据用户ID和密码查询用户 查询不到返回null
	 * @param userId
	 * @param userPwd
	 * @return
	 * @throws Exception
	 */
	public User queryUser(String userId, String userPwd) throws Exception;
	

	/**
	 * 查询一个用户的所有菜单信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Menu> getUserMenuInfos(String userId) throws Exception;

}
