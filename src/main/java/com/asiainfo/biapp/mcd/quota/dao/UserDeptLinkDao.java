package com.asiainfo.biapp.mcd.quota.dao;

import org.springframework.dao.DataAccessException;

public interface UserDeptLinkDao {

	public String getUserDeptInMem(String useId) throws DataAccessException;

	public String getUserDeptNameInMem(String deptId) throws DataAccessException;

}
