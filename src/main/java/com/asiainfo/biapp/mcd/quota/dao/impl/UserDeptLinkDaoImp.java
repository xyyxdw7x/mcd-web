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
	
	private static String TAB_USE_DEPT_MAP="MTL_USER_DEPT_MAP";
	private static String TAB_DEPT="MTL_USER_DEPT";
	
	@Override
	public String getUserDeptInMem(String useId)throws DataAccessException{
		String deptName;
		String sql = "select DEPT_ID from "+TAB_USE_DEPT_MAP +" where user_id=?";
		log.info("com.asiainfo.biapp.mcd.dao.quota.UserDeptLinkDaoImp & getUserDept："+sql);
		String[] parm={useId};
		try {
			deptName=this.getJdbcTemplate().queryForObject(sql, parm,String.class).toString();
		} catch (DataAccessException e) {
			log.error("根据用户id查询用户科室名称出错！！！");
			throw e;
		} 
		return deptName;
		
	}
	@Override
	public String getUserDeptNameInMem(String deptId)throws DataAccessException{
		String deptName;
		String sql = "select DEPT_NAME from "+TAB_DEPT+" where dept_id=?";
		String[] parm={deptId}; 
		try {
			deptName=this.getJdbcTemplate().queryForObject(sql, parm,String.class).toString();
		} catch (DataAccessException e) {
			log.error("根据用户科室id查询科室名称出错！！！");
			throw e;
		} 
		return deptName;
	}
	
	@Override
	public List<Map<String, Object>>  getCityDepts(String cityId)throws DataAccessException{
		List<Map<String, Object>> list;
		String sql = "select DEPT_ID,DEPT_NAME,CITY_ID from "+TAB_DEPT+" where CITY_ID=?";
		try {
			list = this.getJdbcTemplate().queryForList(sql, new Object[]{cityId});
		} catch (DataAccessException e) {
			log.error("根据用户科室id查询科室名称出错！！！",e);
			throw e;
		} 
		return list;
	}
	
	

}
