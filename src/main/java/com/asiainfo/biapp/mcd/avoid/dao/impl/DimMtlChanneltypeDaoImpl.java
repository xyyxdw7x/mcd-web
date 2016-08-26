package com.asiainfo.biapp.mcd.avoid.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.avoid.dao.IDimMtlChanneltypeDao;
import com.asiainfo.biapp.mcd.avoid.model.DimCampsegType;
import com.asiainfo.biapp.mcd.tactics.vo.DimMtlChanneltype;
/**
 * Created on Jan 4, 2008 5:03:41 PM
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */
@Repository(value="channeltypeDao")
public class DimMtlChanneltypeDaoImpl extends JdbcDaoBase implements IDimMtlChanneltypeDao {

	/* (non-Javadoc)
	 * @see com.asiainfo.biapp.mcd.dao.IDimMtlChanneltypeDao#getMtlChanneltype(java.lang.Short)
	 */
	private static Logger log = LogManager.getLogger();

	public List getMtlChanneltypeByCondition(String isDoubleSelect){
		
		List<DimCampsegType> list = null;
		try {
			String sql = "select * from Dim_Mtl_Channeltype dmc where 1=1 ";
			if("1".equals(isDoubleSelect)){
				sql += " and dmc.CHANNELTYPE_ID in (902,903,906)";
			}
			sql += " order by dmc.DISPLAY_ORDER";
			
			return (List<DimCampsegType>)this.getJdbcTemplate().query(sql, new BeanPropertyRowMapper(DimMtlChanneltype.class));
			
		} catch (Exception e) {
			log.error("",e);
		}
		return list;
	}
}
