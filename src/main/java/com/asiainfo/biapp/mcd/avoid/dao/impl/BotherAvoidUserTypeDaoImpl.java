package com.asiainfo.biapp.mcd.avoid.dao.impl;

import java.sql.SQLException;
import java.util.List;

import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.avoid.dao.IDimBotherAvoidUserTypeDao;
import com.asiainfo.biapp.mcd.avoid.vo.DimBotherAvoidUserType;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

/*
 * Created on 2016-8-2 15:37:03
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author zhaokai
 * @version 1.0
 */
@Repository(value="botherAvoidUserTypeDao")
public class BotherAvoidUserTypeDaoImpl extends JdbcDaoBase implements IDimBotherAvoidUserTypeDao {

	private static Logger log = LogManager.getLogger();
	
	public BotherAvoidUserTypeDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public List getAllUserType() throws Exception {
		
		String sql = "SELECT * FROM DIM_BOTHER_AVOID_USER_TYPE WHERE IS_SHOW = 1 ORDER BY DISPLAY_ORDER";
		return (List) this.getJdbcTemplate().query(sql, new RowMapperResultSetExtractor(new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				DimBotherAvoidUserType userType = new DimBotherAvoidUserType();
				userType.setId(Integer.parseInt(rs.getString("ID")));
				userType.setName(rs.getString("NAME"));
				return userType;
			}
		}, 0));

	}

}
