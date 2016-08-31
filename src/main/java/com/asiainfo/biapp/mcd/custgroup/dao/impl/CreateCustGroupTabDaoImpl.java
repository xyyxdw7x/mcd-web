package com.asiainfo.biapp.mcd.custgroup.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.custgroup.dao.CreateCustGroupTabDao;
/**
 * 
 * Title: 
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author zhaokai 2016-8-25 下午2:11:09
 * @version 1.0
 */
@Repository("createCustGroupTab")
public class CreateCustGroupTabDaoImpl extends JdbcDaoBase implements CreateCustGroupTabDao {
	
	private static Logger log = LogManager.getLogger();
	
	@Override
	public void addCreateCustGroupTabInMem(String sql) {
		try {   
			log.debug("sql: {}", sql);   
			this.getJdbcTemplate().execute(sql);  
		} catch (Exception e) {
			e.printStackTrace(); 
		}   
	}
	
}
