package com.asiainfo.biapp.mcd.quota.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.quota.dao.IUserDeptLinkDao;

@Repository(value="userDeptLinkDao")
public class UserDeptLinkDaoImp extends JdbcDaoBase implements IUserDeptLinkDao{
	private static final Logger log = LogManager.getLogger();
	/**
	 * 获取指定地市的所有科室
	 */
	@Override
	public List<Map<String, Object>>  getCityDepts(String cityId)throws DataAccessException{
		List<Map<String, Object>> list;
		String sql = "select DEPT_ID,DEPT_NAME,CITY_ID from MTL_USER_DEPT where CITY_ID=?";
		try {
			list = this.getJdbcTemplate().queryForList(sql, new Object[]{cityId});
		} catch (DataAccessException e) {
			log.error("根据用户科室id查询科室名称出错！！！",e);
			throw e;
		} 
		return list;
	}

	/**
	 * 获取所有地市的所有科室
	 */
	@Override
	public List<Map<String, Object>> getAllDepts() throws DataAccessException {
		List<Map<String, Object>> list;
		String sql = "select DEPT_ID,DEPT_NAME,CITY_ID from MTL_USER_DEPT ";
		try {
			list = this.getJdbcTemplate().queryForList(sql);
		} catch (DataAccessException e) {
			log.error("根据用户科室id查询科室名称出错！！！",e);
			throw e;
		} 
		return list;
	}
	
	

}
