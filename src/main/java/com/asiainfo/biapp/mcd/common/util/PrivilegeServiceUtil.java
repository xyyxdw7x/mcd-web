package com.asiainfo.biapp.mcd.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.asiainfo.biapp.mcd.common.constants.ServiceConstants;
import com.asiainfo.biframe.exception.NotLoginException;
import com.asiainfo.biframe.privilege.ICity;
import com.asiainfo.biframe.privilege.IMenuItem;
import com.asiainfo.biframe.privilege.IUser;
import com.asiainfo.biframe.privilege.IUserCompany;
import com.asiainfo.biframe.privilege.IUserPrivilegeService;
import com.asiainfo.biframe.privilege.IUserSession;
import com.asiainfo.biframe.utils.config.Configure;
import com.asiainfo.biframe.utils.i18n.LocaleUtil;
import com.asiainfo.biframe.utils.spring.SystemServiceLocator;
import com.asiainfo.biframe.utils.string.StringUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * Title: PrivilegeServiceUtil.java  <br>
 * Description: 公共方法：通过权限组件取用户有关信息<br>
 * Copyright: (C) Copyright 1993-2020 AsiaInfo Holdings, Inc<br>
 * Company: 亚信联创科技（中国）有限公司<br>
 * 
 * @author chengjia 2013-5-16 上午10:01:55
 * @version 1.0
 */
public class PrivilegeServiceUtil {

	private static Logger log = Logger.getLogger(PrivilegeServiceUtil.class);
	
	/**
	 * 获取一级地市或者省会
	 * @version ZJ
	 * @return
	 * @throws Exception
	 */
	public static String getCityId(String userId)throws Exception{
		IUserPrivilegeService userPrivilegeService = PrivilegeServiceUtil.getUserPrivilegeService();
		IUser user = userPrivilegeService.getUser(userId);
		String cityIdOne = user.getCityid();
		String root = Configure.getInstance().getProperty("CENTER_CITYID");
		String noCity = Configure.getInstance().getProperty("NO_CITY");
		return getOneLevCityId(cityIdOne, root, noCity);
	}
	
	/**
	 * 获取一级地市或者省会
	 * @version ZJ
	 * @param cityId
	 * @param root
	 * @param noCity 
	 * @return
	 */
	private static String getOneLevCityId(String cityId,String root, String noCity){
		try {
			IUserPrivilegeService userPrivilegeService = PrivilegeServiceUtil.getUserPrivilegeService();
			if (cityId.equals(root) || cityId==root || cityId == noCity || cityId.equals(noCity)) {
				return userPrivilegeService.getCityByCityID(cityId).getDmCityId();
			} else {
				ICity city = userPrivilegeService.getCityByCityID(cityId);
				String parentId = city.getParentId();
				if (parentId == null || parentId.equals(root) || parentId==root || parentId==noCity || parentId.equals(noCity)) {
					return city.getDmCityId();
				} else {
					return getOneLevCityId(city.getParentId(),root,noCity);
				}
			}
		} catch (Exception e) {
			log.error("获取地市ID失败", e);
		}
		return null;
	}
	
	/**
	 * 获取权限接口
	 * @return
	 * @throws Exception
	 */
	public static IUserPrivilegeService getUserPrivilegeService() throws Exception {
		return (IUserPrivilegeService) SystemServiceLocator.getInstance().getService("userPrivilegeService");
	}
	
	/**
	 * 查询用户有权限的地市，区县的与维表对应的ID,返回最大权限的地市ID
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static List<String> getUserCityIds(String userId) throws Exception {
		List<ICity> cityList = getUserCitys(userId);
		List<String> cityIdList = new ArrayList<String>();
		Set<String> allCitys = new HashSet<String>();
		for (ICity city : cityList) {
			allCitys.add(city.getCityId());
		}
		String noCity = Configure.getInstance().getProperty("NO_CITY");
		String centerCity = Configure.getInstance().getProperty("CENTER_CITYID");
		for (ICity city : cityList) {
			if (centerCity.equals(city.getDmCityId()) || centerCity.equals(city.getCityId())) {
				cityIdList.clear();
				cityIdList.add(String.valueOf(ServiceConstants.CITY_ID_ROOT_VAL));
				return cityIdList;
			}
			if (allCitys.contains(city.getParentId())) { //如果已经包含上级地市，就不再添加子地市
				continue;
			}
			if (noCity.equals(city.getDmDeptId())) {
				if (noCity.equals(city.getDmCountyId())) {
					cityIdList.add(city.getDmCityId());
				} else {
					cityIdList.add(city.getDmCountyId());
				}
			} else {
				cityIdList.add(city.getDmDeptId());
			}
		}
		return cityIdList;
	}
	
	/**
	 * 查询用户拥有权限的地市集合
	 *  @param userId 不能为空
	 *@return List<ICity> 
	 * fuyu
	 */
	public static List<ICity> getUserCitys(String userId) throws Exception {
		IUserPrivilegeService userPrivilegeService = getUserPrivilegeService();
		return userPrivilegeService.getCityByUser(userId);
	}
	
	public static ICity getCityByCityId(String cityId) throws Exception {
		IUserPrivilegeService userPrivilegeService = getUserPrivilegeService();
		return userPrivilegeService.getCityByCityID(cityId);
	}
}
