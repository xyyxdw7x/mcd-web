package com.asiainfo.biapp.mcd.tactics.service;

import java.util.List;

import com.asiainfo.biframe.exception.ServiceException;
import com.asiainfo.biframe.privilege.ICity;
import com.asiainfo.biframe.privilege.IMenuItem;
import com.asiainfo.biframe.privilege.IUser;
import com.asiainfo.biframe.privilege.IUserCompany;
import com.asiainfo.biframe.privilege.IUserRight;

/**
 * mpm权限接口；
 * @author gaowj3
 *
 */
public interface IMpmUserPrivilegeService {
    /**
     * 获取用户实际归属地市
     * @param cityId
     * @return
     */
    public ICity getUserActualCity(String cityId);

}
