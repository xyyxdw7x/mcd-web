package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.asiainfo.biapp.mcd.tactics.dao.IUserScope;
import com.asiainfo.biapp.mcd.tactics.service.IMpmUserPrivilegeService;
import com.asiainfo.biapp.mcd.tactics.vo.IMpmUserCityDao;
import com.asiainfo.biframe.common.SysCodes;
import com.asiainfo.biframe.exception.ServiceException;
import com.asiainfo.biframe.privilege.ICity;
import com.asiainfo.biframe.privilege.IMenuItem;
import com.asiainfo.biframe.privilege.IUser;
import com.asiainfo.biframe.privilege.IUserCompany;
import com.asiainfo.biframe.privilege.IUserPrivilegeService;
import com.asiainfo.biframe.privilege.IUserRight;
import com.asiainfo.biframe.privilege.menu.SysMenuMaintain;
import com.asiainfo.biframe.privilege.menu.bean.SysMenuItemBean;
import com.asiainfo.biframe.privilege.model.SysMenuItem;
import com.asiainfo.biframe.utils.database.jdbc.ConnectionEx;
import com.asiainfo.biframe.utils.database.jdbc.Sqlca;
import com.asiainfo.biframe.utils.string.StringUtil;
import com.mysql.jdbc.StringUtils;

public class MpmUserPrivilegeServiceImpl implements IMpmUserPrivilegeService {
	private static Logger log = LogManager.getLogger();

	private IUserPrivilegeService userPrivilegeService;
	private IMpmUserCityDao userCityDao;

	public IMpmUserCityDao getUserCityDao() {
		return userCityDao;
	}

	public void setUserCityDao(IMpmUserCityDao userCityDao) {
		this.userCityDao = userCityDao;
	}

	public IUserPrivilegeService getUserPrivilegeService() {
		return userPrivilegeService;
	}

	public void setUserPrivilegeService(IUserPrivilegeService userPrivilegeService) {
		this.userPrivilegeService = userPrivilegeService;
	}

	public List getAllUser() throws ServiceException {
		return userPrivilegeService.getAllUsers();
	}

	public List getUsersByCompany(String companyId) throws ServiceException {
		if (companyId == null || "".equals(companyId) || !MpmUtil.isNumeric(companyId)) {
			return new ArrayList();
		}

		return userPrivilegeService.getUsersOfDepartment(Integer.parseInt(companyId));
	}

	public List<IUserCompany> getAllUserCompany() throws ServiceException {
		return userPrivilegeService.getAllUserCompany();
	}

	public List<IUserRight> getRight(String userId, int roleType, int resourceType) throws ServiceException {
		return userPrivilegeService.getRight(userId, roleType, resourceType);
	}

	public IUser getUser(String userId) throws ServiceException {
		return userPrivilegeService.getUser(userId);
	}

	public List<IUser> getAllGroupUsersByUserId(String userId) throws ServiceException {
		List<IUser> allUsers = new ArrayList<IUser>();
		IUser user = userPrivilegeService.getUser(userId);
		if (user != null) {
			String groupId = user.getGroupId();
			allUsers = userPrivilegeService.getUsersByGroupId(groupId);
		}

		log.debug("users in the group includes [" + userId + "] is :" + allUsers.size());

		return allUsers;
	}

	public IUserCompany getUserCompanyById(String companyId) {

		return userPrivilegeService.getUserCompanyById(companyId);
	}

	public String getUserDmCity(String userId, String dmType) throws ServiceException {
		return userPrivilegeService.getUserDmCity(userId, dmType);
	}

	public List<ICity> getAllCity() throws ServiceException {
		return userPrivilegeService.getAllCity();
	}

	public List<ICity> getSubCity(String cityId) throws ServiceException {
		return userPrivilegeService.getSubCitysById(cityId);
	}

	public List<ICity> getAllCityByUser(String userId) throws ServiceException {
		List<ICity> list = new ArrayList();
		try {
			list = userPrivilegeService.getCityByUser(userId);
		} catch (Exception e) {
			log.error(e);
		}
		return list;
	}

	public List<ICity> getAllRecomendCityByUser(String userId) throws ServiceException {
		// 查询LKG_STAFF_SCOPE表中用户访问地市访问权限记录
		IUserScope userScope = getUserCityDao().getUserScope(userId);
		// 构造查询对象
		Map<String, String> queryMap = new HashMap<String, String>();
		if (userScope != null) {
			String prov = userScope.getProvScope();
			String city = userScope.getCityScope();
			String country = userScope.getCountryScope();
			// 完全根据现场数据规则定义的省市管理员判断条件
			if ("290".equals(prov) && "290".equals(city) && "0000".equals(country)) {
				queryMap.put("prov", prov);
			} else if ("290".equals(prov)) {
				queryMap.put("city", city);
			}
		}
		// 根据该记录是省管理员还是市管理员分别取权限
		return getUserCityDao().getRecommendCitysByUserId(queryMap);
	}

	public ICity getCityById(String cityId) throws ServiceException {
		return userPrivilegeService.getCityByCityID(cityId);
	}

	public List<IMenuItem> getAllMenuItem(String userId) throws ServiceException {
		return userPrivilegeService.getAllMenuItem(userId);
	}

	/**
	 * 得到直接子菜单；
	 * @param userId
	 * @param menuId
	 * @return
	 * @throws ServiceException
	 */
	public List<IMenuItem> getSubDirectlyMenuItem(String userId, String menuId) throws ServiceException {
		if (MpmUtil.isNumeric(menuId)) {
			return userPrivilegeService.getSubMenuItemById(Integer.parseInt(menuId), userId, true);
		}
		log.debug("menuId is invalid.");

		return new ArrayList<IMenuItem>();
		//		return getSubMenuItemById(userId,menuId,false);
	}

	public List<IMenuItem> getSubAllMenuItem(String userId, String menuId) throws ServiceException {
		/*if(MpmUtil.isNumeric(menuId)) {
			return userPrivilegeService.getSubMenuItemById(Integer.parseInt(menuId), userId, true);
		}
		log.debug("menuId is invalid.");

		return new ArrayList<IMenuItem>();*/
		return getSubMenuItemById(userId, menuId, true);
	}

	public String getUserDmAll(String userId) throws ServiceException {
		return userPrivilegeService.getUserDmCity(userId, SysCodes.DM_ALL);
	}

	public String getUserDmCity(String userId) throws ServiceException {
		return userPrivilegeService.getUserDmCity(userId, SysCodes.DM_CITY);
	}

	public String getUserDmCounty(String userId) throws ServiceException {
		return userPrivilegeService.getUserDmCity(userId, SysCodes.DM_COUNTY);
	}

	public String getUserDmDept(String userId) throws ServiceException {
		return userPrivilegeService.getUserDmCity(userId, SysCodes.DM_DEPT);
	}

	/**
	 * 获取直接子菜单
	 * @param userId
	 * @param menuId
	 * @param flag 是否递归
	 * @return
	 * @throws ServiceException
	 */
	private List<IMenuItem> getSubMenuItemById(String userId, String menuId, boolean flag) throws ServiceException {
		if (MpmUtil.isNumeric(menuId)) {
			Sqlca sqlca = null;
			try {
				sqlca = new Sqlca(new ConnectionEx());
				List subMenuIdList = SysMenuMaintain.getSubMenuItem(sqlca, menuId, flag); // 获得顶级菜单IdList

				if (StringUtil.isNotEmpty(userId)) {
					boolean isOnlyFolder = subMenuIdList.isEmpty();
					if (subMenuIdList.isEmpty()) {
						subMenuIdList.add(menuId);
					}
					List<IUserRight> resourceList = userPrivilegeService.getRight(userId, 1, Integer.parseInt("50"));
					List idList = PrivilegeListUtil.convertToNewList(resourceList, "ResourceId"); // 获取列表中菜单ID字段的List
					subMenuIdList.retainAll(idList); // 交集  (集对应的有权限的FolderIds)
					List list = PrivilegeListUtil.getMenu(subMenuIdList, resourceList, flag, isOnlyFolder);
					return foreachMenuItemList(list);
				}

			} catch (Exception e) {
				throw new ServiceException(e.getMessage());
			} finally {
				if (sqlca != null) {
					sqlca.closeAll();
				}
			}
		}
		log.debug("menuId is invalid.");
		return new ArrayList<IMenuItem>();
	}

	private List<IMenuItem> foreachMenuItemList(List list) {
		List result = new ArrayList();
		//		ComparatorPrivilege comparator = new ComparatorPrivilege();
		//		Collections.sort(list, comparator);
		Collections.sort(list, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				SysMenuItemBean item0 = (SysMenuItemBean) arg0;
				SysMenuItemBean item1 = (SysMenuItemBean) arg1;
				return new Integer(item0.getMENUITEMID()).compareTo(new Integer(item1.getMENUITEMID()));
			}
		});
		Iterator it = list.iterator();
		while (it.hasNext()) {
			SysMenuItemBean smi = (SysMenuItemBean) it.next();
			SysMenuItem menuItem = new SysMenuItem();
			menuItem.setMenuItemId(Integer.valueOf(smi.getMENUITEMID()));
			menuItem.setMenuItemTitle(smi.getMENUITEMTITLE());
			menuItem.setParentId(Integer.valueOf(smi.getPARENTID()));
			menuItem.setMenuType(Integer.valueOf(smi.getMENUTYPE()));
			menuItem.setUrl(smi.getURL());
			menuItem.setAccessToken(Integer.valueOf(smi.getACCESSTOKEN()));
			menuItem.setApplicationId(smi.getApplicationId());
			menuItem.setUrlPort(smi.getURLPORT());
			menuItem.setUrlTarget(smi.getURLTARGET());
			menuItem.setResourceType(Integer.valueOf(smi.getRESTYPE()));
			menuItem.setResId(smi.getRESID());
			menuItem.setSortNum(Integer.valueOf(smi.getSORTNUM()));
			menuItem.setFolderOrNot(StringUtil.isEmpty(menuItem.getUrl()));
			menuItem.setOperationType("-1");
			result.add(menuItem);
		}
		return result;
	}

	public boolean isAdminUser(String userid) {
		return userPrivilegeService.isAdminUser(userid);
	}

	public List<ICity> getAllCounty() throws ServiceException {
		// TODO Auto-generated method stub
		return userCityDao.getAllCounty();
	}

	public IUserScope getUserScope(String userId) throws ServiceException {
		// TODO Auto-generated method stub
		return (IUserScope) getUserCityDao().getUserScope(userId);

	}

	/**
	 * 判断该用户是否地市管理员
	 * @param userid
	 * @return
	 */
	public boolean isCityAdminUser(String userid) {
		IUser user = userPrivilegeService.getUser(userid);
		return this.isCityAdminGroup(user.getGroupId());
	}

	/**
	 * 判断传入的组ID是否地市管理组
	 * @param groupId
	 * @return
	 */
	public boolean isCityAdminGroup(String groupId) {
		String cityGroupId = MpmConfigure.getInstance().getProperty("CITY_ADMIN_GROUP_ID");
		if (StringUtil.isNotEmpty(cityGroupId) && StringUtil.isNotEmpty(groupId) && cityGroupId.equals(groupId)) {
			return true;
		}
		return false;
	}

	@Override
	public String getCityNamesByIds(String cityIds) {
		StringBuffer cityNames = new StringBuffer();
		if (StringUtil.isNotEmpty(cityIds)) {
			List<String> cIds = StringUtils.split(cityIds, ",", true);
			List<ICity> citys = this.getAllCity();
			for (ICity city : citys) {
				if (cIds.contains(city.getCityId())) {
					if (StringUtil.isNotEmpty(city.getCityName())) {
						cityNames.append(city.getCityName()).append(",");
					}
				}
			}
			if (cityNames.length() > 0) {
				cityNames.deleteCharAt(cityNames.length() - 1);
			}
		}
		return cityNames.toString();
	}

	public ICity getUserActualCity(String cityId) {
		ICity actualCity = this.getCityById(cityId);
		if (actualCity != null
				&& ((StringUtil.isNotEmpty(actualCity.getDmCountyId()) && !"-1".equals(actualCity.getDmCountyId())) || (StringUtil
						.isNotEmpty(actualCity.getDmDeptId()) && !"-1".equals(actualCity.getDmDeptId())))) {
			actualCity = getUserActualCity(actualCity.getParentId());
		}
		return actualCity;
	}
}
