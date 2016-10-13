package com.asiainfo.biapp.mcd.home.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.home.dao.ICepKeywordsDao;

@Repository("CepKeywordsDao")
public class CepKeywordsDaoImpl extends JdbcDaoBase implements ICepKeywordsDao {

	public static Logger log = LogManager.getLogger();

	@Override
	public List<Map<String, Object>> queryData4Charts(String sql, Object[] params) {
		return this.getJdbcTemplate().queryForList(sql, params);
	}

}
