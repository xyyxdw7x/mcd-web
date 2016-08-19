package com.asiainfo.biapp.mcd.tactics.service;

import java.util.List;

import com.asiainfo.biapp.mcd.tactics.dao.IUserScope;
import com.asiainfo.biframe.exception.ServiceException;
import com.asiainfo.biframe.privilege.ICity;
import com.asiainfo.biframe.privilege.IMenuItem;
import com.asiainfo.biframe.privilege.IUser;
import com.asiainfo.biframe.privilege.IUserCompany;
import com.asiainfo.biframe.privilege.IUserRight;

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

	/**
	 * 取得一个部门的所有用户
	 * @param companyId
	 * @return
	 * @throws ServiceException
	 */
	public List getUsersByCompany(String companyId) throws ServiceException;

	/**
	 * 取得所有的部门；
	 * @return
	 * @throws ServiceException
	 */
	public abstract List<IUserCompany> getAllUserCompany() throws ServiceException;

	/**
	 * 由部门id得到部门信息；
	 * @param companyId
	 * @return
	 */
	public abstract IUserCompany getUserCompanyById(String companyId);

	/**
	 * 获取用户所属地市的数据(不同类型的地市信息)
	 * @param userId  SysCodes.DM_ALL省;SysCodes.DM_CITY市;SysCodes.DM_COUNTY区县;SysCodes.DM_DEPT乡镇
	 * @param dmType
	 * @return
	 * @throws ServiceException
	 */
	public abstract String getUserDmCity(String userId, String dmType) throws ServiceException;

	public abstract String getUserDmCity(String userId) throws ServiceException;

	public abstract String getUserDmCounty(String userId) throws ServiceException;

	public abstract String getUserDmDept(String userId) throws ServiceException;

	public abstract String getUserDmAll(String userId) throws ServiceException;

	/**
	 * 取得所有用户信息（查询所有状态为”有效”的用户信息）；
	 *
	 * @return
	 * @throws ServiceException
	 */
	public abstract List getAllUser() throws ServiceException;

	/**
	 * 取得用户特定权限；
	 * @param userId
	 * @param roleType
	 * @param resourceType
	 * @return
	 * @throws ServiceException
	 */
	public abstract List<IUserRight> getRight(String userId, int roleType, int resourceType) throws ServiceException;

	/**
	 * 取得所有地市信息；
	 * @return
	 * @throws ServiceException
	 */
	public abstract List<ICity> getAllCity() throws ServiceException;

	/**
	 * 取得当前地市下所有地市信息；
	 * @return
	 * @throws ServiceException
	 */
	public abstract List<ICity> getSubCity(String cityId) throws ServiceException;

	/**
	 * 取得用户有权限的所有地市列表；
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public abstract List<ICity> getAllCityByUser(String userId) throws ServiceException;

	/**
	 * 取得推荐对象下用户可访问的全部地市列表；
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public abstract List<ICity> getAllRecomendCityByUser(String userId) throws ServiceException;

	/**
	 * 取得地市信息；
	 * @param cityId
	 * @return
	 * @throws ServiceException
	 */
	public abstract ICity getCityById(String cityId) throws ServiceException;

	/**
	 * 得到所有的菜单列表；
	 * @return
	 * @throws ServiceException
	 */
	public abstract List<IMenuItem> getAllMenuItem(String userId) throws ServiceException;

	/**
	 * 得到直接子菜单；
	 * @param userId
	 * @param menuId
	 * @return
	 * @throws ServiceException
	 */
	public List<IMenuItem> getSubDirectlyMenuItem(String userId, String menuId) throws ServiceException;

	/**
	 * 得到所有子菜单；
	 * @param userId
	 * @param menuId
	 * @return
	 * @throws ServiceException
	 */
	public List<IMenuItem> getSubAllMenuItem(String userId, String menuId) throws ServiceException;

	/**
	 * 得到该用户同组的所有用户列表；
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public List<IUser> getAllGroupUsersByUserId(String userId) throws ServiceException;

	public boolean isAdminUser(String userid);

	public List<ICity> getAllCounty() throws ServiceException;
	
	public IUserScope getUserScope(String userId) throws ServiceException;
	
	public String getCityNamesByIds(String cityIds);

	/**
	 * 判断该用户是否地市管理员
	 * @param userid
	 * @return
	 */
	public boolean isCityAdminUser(String userid);

	/**
	 * 判断传入的组ID是否地市管理组
	 * @param groupId
	 * @return
	 */
	public boolean isCityAdminGroup(String groupId);

	/**
	 * 获取用户实际归属地市
	 * @param cityId
	 * @return
	 */
	public ICity getUserActualCity(String cityId);

}
