package com.asiainfo.biapp.mcd.privilege.service.dao.impl;

import java.sql.Types;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.framework.jdbc.VoPropertyRowMapper;
import com.asiainfo.biapp.framework.privilege.vo.Menu;
import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.mcd.privilege.service.dao.IUserPrivilegeDao;

/**
 * 用户权限接口DAO
 * @author hjn
 *
 */
@Repository("userPrivilegeDao")
public class UserPrivilegeDaoImpl extends JdbcDaoBase implements IUserPrivilegeDao {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public User queryUser(String userId, String userPwd) throws Exception {
		logger.info("userId="+userId+" userPwd="+userPwd);
		String sql="select t.USERID,t.CITYID,t.USERNAME,t.PWD,t.STATUS,t.MOBILEPHONE,t.DEPARTMENTID from USER_USER t where t.USERID=? and t.PWD=? and status>=0 ";
		int[] argTypes=new int[]{Types.VARCHAR,Types.VARCHAR};
		List ss=this.getJdbcTemplate().queryForList(sql, new Object[]{userId,userPwd}, argTypes);
		List<User> users=this.getJdbcTemplate().query(sql, new Object[]{userId,userPwd}, argTypes, new VoPropertyRowMapper<User>(User.class));
		User user=null;
		if(users.size()==0){
			return user;
		}else{
			user=users.get(0);
		}
		return user;
	}

	@Override
	public List<Menu> getUserMenuInfos(String userId) throws Exception {
		logger.info("userId="+userId);
		String sql="select t.MENUITEMID,t.MENUITEMTITLE,t.PARENTID,t.SORTNUM,t.URL from SYS_MENU_ITEM t where parentid!=-10 order by t.parentid ,sortnum asc ";
		List<Menu> menus=this.getJdbcTemplate().query(sql, new VoPropertyRowMapper<Menu>(Menu.class));
		return menus;
	}

}
