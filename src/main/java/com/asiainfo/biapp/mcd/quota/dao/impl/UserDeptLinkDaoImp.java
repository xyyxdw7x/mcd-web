package com.asiainfo.biapp.mcd.quota.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.quota.dao.UserDeptLinkDao;

@Repository(value="userDeptLinkDao")
public class UserDeptLinkDaoImp extends JdbcDaoBase implements UserDeptLinkDao{
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			log.error("根据用户科室id查询科室名称出错！！！");
			throw e;
		} 
		return deptName;
	}
	
	

}
