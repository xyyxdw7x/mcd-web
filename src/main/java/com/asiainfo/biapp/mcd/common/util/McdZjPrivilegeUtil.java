package com.asiainfo.biapp.mcd.common.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.asiainfo.biapp.mcd.tactics.vo.LkgStaff;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * Title: Description:浙江IMCD从黄金眼系统中获取权限信息时工具类 Copyright: (C) Copyright 1993-2014
 * AsiaInfo Holdings, Inc Company: 亚信科技（中国）有限公司
 * 
 * @author lixq10 2015-10-14 下午5:30:05
 * @version 1.0
 */

public class McdZjPrivilegeUtil {

	public static String createParam(String methodName,String userId, String menuId,String isAllMenu,String flag) {
		Map map = new HashMap();
		map.put("invok", methodName);
		Map userInfoMap = new HashMap();
		userInfoMap.put("user_id", userId);
		userInfoMap.put("city_id", "0");
		userInfoMap.put("4a_organiz", "");
		map.put("userinfo", userInfoMap);
		Map appinfoMap = new HashMap();
		appinfoMap.put("app_id", "");
		appinfoMap.put("serial_no", "");
		map.put("appinfo", appinfoMap);
		Map paraminfoMap = new HashMap();
		if (flag.equals("0")) { // 用户参数
			paraminfoMap.put("user_id", userId);
		} else if (flag.equals("1")) { // 角色参数
			paraminfoMap.put("user_id", userId);
			paraminfoMap.put("is_main", ""); //// 主角色标识 1-主角色 0-其他角色 默认为空，查询全部
		} else if (flag.equals("2")) { // 菜单参数
			paraminfoMap.put("user_id", userId);
			paraminfoMap.put("is_all_menu", isAllMenu);
			paraminfoMap.put("parent_menu_id", menuId);
		}

		map.put("paraminfo", paraminfoMap);
		net.sf.json.JSONObject paramStr = net.sf.json.JSONObject.fromObject(map);
		return paramStr.toString();
	}
	/**
	 * 解析从黄金眼返回的用户xmljson串
	 * @param resultJson
	 * @return
	 * @throws JSONException
	 */
	public static LkgStaff parseUserXmlToList(String resultJson) throws JSONException{
		if(StringUtils.isNotEmpty(resultJson)){
			JSONObject jsonObj = new JSONObject(resultJson);
			String resulttype = String.valueOf(jsonObj.get("resulttype")) ;
			String responsemessage = String.valueOf(jsonObj.get("responsemessage")) ;
			String responsecode = String.valueOf(jsonObj.get("responsecode")) ;  //0:请求失败；1：请求成功
			if("1".equals(responsecode)){
				String resultlist = String.valueOf(jsonObj.get("resultlist")) ;
//				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				LkgStaff user = new LkgStaff();
				JSONObject obj = new JSONObject(resultlist);
				String userId = String.valueOf(obj.get("user_id")) ;
				String cityId = String.valueOf(obj.get("city_id")) ;
				String deptId = String.valueOf(obj.get("dept_id")) ;
				String userName = String.valueOf(obj.get("user_name")) ;
				String pwd = String.valueOf(obj.get("pwd")) ;
				String status = String.valueOf(obj.get("status")) ;
				String beginDate = String.valueOf(obj.get("begin_date")) ;
				String endDate = String.valueOf(obj.get("end_date")) ;
				String createTime = String.valueOf(obj.get("create_time")) ;
				String birthday = String.valueOf(obj.get("birthday")) ;
				String mobilePhone = String.valueOf(obj.get("mobile_phone")) ;
				String email = String.valueOf(obj.get("email")) ;
				String notes = String.valueOf(obj.get("notes")) ;
				String dutyId = String.valueOf(obj.get("duty_id")) ;
				String countryId = String.valueOf(obj.get("county_id")) ;
				String areaId = String.valueOf(obj.get("area_id")) ;
				String sex = String.valueOf(obj.get("sex")) ;
				String age = String.valueOf(obj.get("age")) ;
				String nation = String.valueOf(obj.get("nation")) ;
				String dataLevel = String.valueOf(obj.get("sensitive_data_level")) ;
				String address = String.valueOf(obj.get("address")) ;
				String postalcode = String.valueOf(obj.get("postalcode")) ;
				String roleId = String.valueOf(obj.get("role_id")) ;
				String deptName = String.valueOf(obj.get("notes")) ;
				
				user.setStaffId(userId);
				user.setStaffName(userName);
				user.setStaffPwd(pwd);
				user.setSex(sex);
				user.setAge(age);
				user.setNation(nation);
				user.setCountryId(countryId);
				user.setCityId(cityId);
				if("0".equals(cityId)){
					user.setCityId("999");
				}
				user.setAreaId(areaId);
				user.setDataLevel(dataLevel);
				user.setDepId(deptId+"&&"+deptName);
				user.setPhone(mobilePhone);
				user.setEmail(email);
				user.setCommAddress(address);
				user.setPostCode(postalcode);
				user.setRoleId(roleId);
				return user;
			}
		}
		return null;
	}

}
