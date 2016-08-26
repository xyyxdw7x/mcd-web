package com.asiainfo.biapp.mcd.quota.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.quota.dao.IMtlSysCampConfigDao;
import com.asiainfo.biapp.mcd.quota.model.MtlSysCampConfig;

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
	public List<MtlSysCampConfig> getAll() {
		List<MtlSysCampConfig> list = new ArrayList<MtlSysCampConfig>();
		try {
			List<Map> listTemp = null;
			StringBuffer buffer = new StringBuffer();
			buffer.append(" select * from MTL_SYS_CAMP_CONFIG");
			listTemp = this.jdbcTemplate.queryForList(buffer.toString(),Map.class);
			for (Map map : listTemp) {
				MtlSysCampConfig mtlSysCampConfig = new MtlSysCampConfig();
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
		String sql = "select t.* from MTL_SYS_CAMP_CONFIG t where t.CONFIG_KEY = ?";
		@SuppressWarnings("unchecked")
		Map<String, Object> map = this.getJdbcTemplate().queryForMap(sql, new Object[] { key });
//		Iterator<Entry<String, String>> ite = map.entrySet().iterator();
//		while (ite.hasNext()) {
//			
//		}
		return (String)map.get("CONFIG_VALUE");
	}
	
}

