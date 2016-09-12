package com.asiainfo.biapp.mcd.tactics.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.framework.jdbc.VoPropertyRowMapper;
import com.asiainfo.biapp.mcd.tactics.dao.IMtlCallwsUrlDao;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCallwsUrl;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampCustgroupList;

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
		String sql = "select * from mcd_sys_interface_def  a where 1=1 ";
		if (mtlCallwsUrl.getCallwsUrlCode() != null) {
			sql = sql + " and a.callws_url_code = '" + mtlCallwsUrl.getCallwsUrlCode() + "'";
		}
		List<MtlCallwsUrl>	result = this.getJdbcTemplate().query(sql,new VoPropertyRowMapper<MtlCallwsUrl>(MtlCallwsUrl.class));

		return result;
	}
	
}
