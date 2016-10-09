package com.asiainfo.biapp.mcd.quota.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.quota.dao.IMtlSysCampConfigDao;
import com.asiainfo.biapp.mcd.quota.vo.McdSysDic;

/**
 * 
 * Title: 
 * Description: 策略基本信息保存action
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-8-19 下午5:23:45
 * @version 1.0
 */
@Repository(value="sysCampConfigDao")
public class MtlSysCampConfigDaoImpl extends JdbcDaoBase implements IMtlSysCampConfigDao {
	private static Logger log = LogManager.getLogger();
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<McdSysDic> getAll() {
		List<McdSysDic> list = new ArrayList<McdSysDic>();
		try {
			List<Map<String,Object>> listTemp = null;
			StringBuffer buffer = new StringBuffer();
			buffer.append(" select * from mcd_sys_dic");
			listTemp = this.jdbcTemplate.queryForList(buffer.toString());
			for (Map<String, Object> map : listTemp) {
				McdSysDic mtlSysCampConfig = new McdSysDic();
				mtlSysCampConfig.setConfigKey(map.get("CONFIG_KEY").toString());
				mtlSysCampConfig.setConfigName(map.get("CONFIG_NAME").toString());
				mtlSysCampConfig.setConfigValue(map.get("CONFIG_VALUE").toString());
				list.add(mtlSysCampConfig);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return list;
	}
	
	public Object getProperety(String key) {
		String sql = "select t.* from mcd_sys_dic t where t.DIC_KEY = ?";
		Map<String, Object> map = this.getJdbcTemplate().queryForMap(sql, new Object[] { key });
		return (String)map.get("DIC_DATA_VALUE");
	}
	
}


