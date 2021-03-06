package com.asiainfo.biapp.mcd.bull.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.bull.dao.IUserDeptDao;

@Repository(value="userDeptDao")
public class UserDeptDaoImp  extends JdbcDaoBase implements IUserDeptDao{
	private static final Logger log = LogManager.getLogger();
	
	private static final String TABLE= "MTL_USER_DEPT";
	
	
	@Override
	public List<Map<String,Object>> getCityDeptes(String cityId)throws DataAccessException{
		String sql="select * from "+TABLE+" where CITY_ID='"+cityId+"'";
		List<Map<String,Object>> list=null;
		try {
			list=this.getJdbcTemplate().queryForList(sql);
		} catch (DataAccessException e) {
			log.error("查询部门出错！！！");
			return null;
		}
		return list;
		
	}
	
	@Override
	public List<Map<String,Object>> getUserDeptWithId(String useId)throws DataAccessException{
		List<Map<String,Object>> list=null;
		String sql = "select MAP.DEPT_ID,DEPT.DEPT_NAME from MTL_USER_DEPT_MAP MAP " +
				"LEFT OUTER JOIN MTL_USER_DEPT DEPT on MAP.DEPT_ID=DEPT.DEPT_ID  " +
				"where user_id=?";

		try {
			list=this.getJdbcTemplate().queryForList(sql,useId);
		} catch (DataAccessException e) {
			log.error("根据用户id查询用户科室名称出错！！！");
			return null;
		} 
		return list;
		
	}
	

}
