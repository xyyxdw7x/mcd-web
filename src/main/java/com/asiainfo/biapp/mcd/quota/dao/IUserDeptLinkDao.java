package com.asiainfo.biapp.mcd.quota.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface IUserDeptLinkDao {

	public String getUserDeptInMem(String useId) throws DataAccessException;

	public String getUserDeptNameInMem(String deptId) throws DataAccessException;

	List<Map<String, Object>> getCityDepts(String cityId) throws DataAccessException;

}
