package com.asiainfo.biapp.mcd.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.asiainfo.biapp.mcd.exception.MpmException;
import com.asiainfo.biframe.privilege.IMenuItem;

/*
 * Created on 10:38:41 AM
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */
public interface IMpmUserPolicyService {

	/**
	 * 判断用户是否是部门/分公司领导，如果不是，返回此用户的userId；如果是，返回部门及子部门下所有用户的userId
	 * @param userId
	 * @return 多个userId，格式如（'userId1','userId2',...）
	 * @throws MpmException
	 */
	//	public String getUserDeptPolicyCache(String userId) throws MpmException;
	
	/**
	 * 取得直接子菜单；
	 * @param userId
	 * @param menuId
	 * @return
	 * @throws MpmException
	 */
	public List<IMenuItem> getDirectlySubMenuItems(String userId, String menuId) throws MpmException;
	
	/**
	 * 取得所有子菜单；
	 * @param userId
	 * @param menuId
	 * @return
	 * @throws MpmException
	 */
	public List<IMenuItem> getAllSubMenuItems(String userId, String menuId) throws MpmException;
	
	/**
	 * 取用户所属角色有权限访问的地市ID集合
	 * @param userId
	 * @return 多个cityId，格式如（'cityId1','cityId2',...）
	 * @throws MpmException
	 */
	public String getUserCityPolicyCache(String userId) throws MpmException;

	/**
	 * 判断用户是否是超级管理员
	 * @param userId
	 * @return
	 * @throws MpmException
	 */
	public boolean isUserAdmin(String userId, String groupId) throws MpmException;
	
	
	/**
	 * 获取用户功能菜单
	 * @param menuId
	 * @param userId
	 * @param groupId
	 * @return
	 * @throws MpmException
	 */
	public List getUserMenuPolicyCache(String menuId,String userId,String groupId) throws MpmException ;
	
	/**
	 * 获取功能多级子菜单
	 * @param menuId
	 * @param userId
	 * @param groupId
	 * @return
	 * @throws MpmException
	 */
	public List getUserSubMenuPolicy(HttpServletRequest request,String menuId,String userId,String groupId) throws MpmException;
	
	/**
	 * 取用户有权限访问的地市信息列表
	 * @param userId TODO
	 * @return 带有dmCityId、cityName信息的LabelValueBean List
	 * @throws MpmException
	 */
	public List getUserDmCityList(String userId) throws MpmException;

	/**
	 * 取用户有权限访问的地市信息列表
	 * @param userId
	 * @return 带有cityId、cityName信息的LabelValueBean List
	 * @throws MpmException
	 */
	public List getUserCityList(String userId) throws MpmException;
	
	/**
	 * 取用户有权限访问的地市信息列表
	 * @param userId
	 * @return Icity对象List
	 * @throws MpmException
	 */
	public List getUserCityObj(String userId) throws MpmException;
	
}
