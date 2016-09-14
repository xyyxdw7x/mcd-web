package com.asiainfo.biapp.mcd.avoid.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.avoid.dao.IDimBotherAvoidUserTypeDao;
import com.asiainfo.biapp.mcd.avoid.vo.DimBotherAvoidUserType;

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

	public BotherAvoidUserTypeDaoImpl() {
		super();
	}
	
	@Override
	public List<DimBotherAvoidUserType> getAllUserType() throws Exception {
		
		String sql = "SELECT * FROM DIM_BOTHER_AVOID_USER_TYPE WHERE IS_SHOW = 1 ORDER BY DISPLAY_ORDER";

		return (List<DimBotherAvoidUserType>) this.getJdbcTemplate().query(sql, 
				new BeanPropertyRowMapper<DimBotherAvoidUserType>  (DimBotherAvoidUserType.class));

	}

}
