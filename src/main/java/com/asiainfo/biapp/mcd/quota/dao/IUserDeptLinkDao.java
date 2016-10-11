package com.asiainfo.biapp.mcd.quota.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface IUserDeptLinkDao {

	/**
	 * 获取某个地市的所有科室
	 * @param cityId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> getCityDepts(String cityId) throws DataAccessException;
	
	/**
	 * 获取某个地市的所有科室
	 * @param cityId
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> getAllDepts() throws DataAccessException;

}
