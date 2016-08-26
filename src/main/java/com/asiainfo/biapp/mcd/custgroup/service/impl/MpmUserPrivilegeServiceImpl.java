package com.asiainfo.biapp.mcd.custgroup.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.custgroup.service.IMpmUserPrivilegeService;
import com.asiainfo.biframe.common.SysCodes;
import com.asiainfo.biframe.exception.ServiceException;
import com.asiainfo.biframe.privilege.ICity;
import com.asiainfo.biframe.privilege.IMenuItem;
import com.asiainfo.biframe.privilege.IUser;
import com.asiainfo.biframe.privilege.IUserCompany;
import com.asiainfo.biframe.privilege.IUserPrivilegeService;
import com.asiainfo.biframe.privilege.IUserRight;
import com.asiainfo.biframe.privilege.menu.bean.SysMenuItemBean;
import com.asiainfo.biframe.privilege.model.SysMenuItem;
import com.asiainfo.biframe.utils.database.jdbc.ConnectionEx;
import com.asiainfo.biframe.utils.database.jdbc.Sqlca;
import com.asiainfo.biframe.utils.string.StringUtil;
import com.mysql.jdbc.StringUtils;

@Service("userPrivilegeService")
public class MpmUserPrivilegeServiceImpl implements IMpmUserPrivilegeService {
	private static Logger log = LogManager.getLogger();
	
	private IUserPrivilegeService userPrivilegeService;
	
	public IUser getUser(String userId) throws ServiceException {
		return userPrivilegeService.getUser(userId);
	}
}
