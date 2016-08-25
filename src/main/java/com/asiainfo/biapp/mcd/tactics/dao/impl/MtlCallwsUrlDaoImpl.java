package com.asiainfo.biapp.mcd.tactics.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.tactics.dao.IMtlCallwsUrlDao;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCallwsUrl;

/**
 * Created on Oct 23, 2007 10:50:07 AM
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author libin.zhou zhoulb@asiainfo.com
 * @version 1.0
 */
@Repository("mtlCallwsUrlDao")
public class MtlCallwsUrlDaoImpl extends JdbcDaoBase implements IMtlCallwsUrlDao {
	private static Logger log = LogManager.getLogger();
	public List findByCond(MtlCallwsUrl mtlCallwsUrl) throws Exception {
		List result = new ArrayList();
		try {
			String sql = "select * from MTL_CALLWS_URL  a where 1=1 ";
			if (mtlCallwsUrl.getCallwsUrlCode() != null) {
				sql = sql + " and a.callws_url_code = '" + mtlCallwsUrl.getCallwsUrlCode() + "'";
			}
			result = this.getJdbcTemplate().queryForList(sql);
		} catch (Exception e) {
			log.error("", e);
		}
		return result;
	}
	
}
