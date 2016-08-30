package com.asiainfo.biapp.mcd.bull.dao;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;

@Repository(value="userDeptDao")
public class UserDeptDaoImp  extends JdbcDaoBase implements UserDeptDao{
	private static final Logger log = LogManager.getLogger();
	
	private static final String TABLE= "MTL_USER_DEPT";
	
	private  JdbcTemplate sqlFireJdbcTemplate;

	public void setSqlFireJdbcTemplate(JdbcTemplate sqlFireJdbcTemplate) {
		this.sqlFireJdbcTemplate = sqlFireJdbcTemplate;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getCityDeptes(String cityId)throws DataAccessException{
		String sql="select * from "+TABLE+" where CITY_ID='"+cityId+"'";
		List<Map<String,Object>> list=null;
		try {
			list=this.sqlFireJdbcTemplate.queryForList(sql);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("查询部门出错！！！");
			return null;
		}
		return list;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getUserDeptWithId(String useId)throws DataAccessException{
		List<Map<String,Object>> list=null;
		String sql = "select MAP.DEPT_ID,DEPT.DEPT_NAME from MTL_USER_DEPT_MAP MAP " +
				"LEFT OUTER JOIN MTL_USER_DEPT DEPT on MAP.DEPT_ID=DEPT.DEPT_ID  " +
				"where user_id=?";
		
		String[] parm={useId};
		try {
			list=this.sqlFireJdbcTemplate.queryForList(sql,parm);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("根据用户id查询用户科室名称出错！！！");
			return null;
		} 
		return list;
		
	}
	

}
