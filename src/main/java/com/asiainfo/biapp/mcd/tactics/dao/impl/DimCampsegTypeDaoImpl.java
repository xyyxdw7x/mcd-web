/**   
 * @Title: DimCampsegTypeDaoImpl.java
 * @Package com.asiainfo.biapp.mcd.dao.impl
 * @Description:
 * @author A18ccms A18ccms_gmail_com   
 * @date 2015-7-17 上午10:32:56
 * @version V1.0   
 */
package com.asiainfo.biapp.mcd.tactics.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.tactics.dao.IDimCampsegTypeDao;
import com.asiainfo.biapp.mcd.tactics.vo.McdDimCampType;

/**
 * @ClassName: DimCampsegTypeDaoImpl
 * @Description: 营销类型Dao接口实现类
 * @author jinlong
 * @date 2015-7-17 上午10:32:56
 * 
 */
@Repository(value="campsegTypeDao")
public class DimCampsegTypeDaoImpl extends JdbcDaoBase  implements IDimCampsegTypeDao {

	private static Logger log = LogManager.getLogger();
	
	public DimCampsegTypeDaoImpl() {
		super();
	}
	@Override
	public McdDimCampType getDimCampsegType(Short campsegTypeId)
			throws Exception {
		McdDimCampType obj = null;
		try {
			String sql = "SELECT * FROM mcd_dim_camp_type WHERE CAMPSEG_TYPE_ID = ? order by CAMPSEG_TYPE_ID asc";
			
			Object args[] = new Object[]{campsegTypeId};  
	        return (McdDimCampType)this.getJdbcTemplate().queryForObject(sql,args,new BeanPropertyRowMapper<McdDimCampType>(McdDimCampType.class));
			
		} catch (Exception e) {
			log.error("",e);
		}
		return obj;
	}
	@Override
	public List<McdDimCampType> getAllDimCampsegType() throws Exception {
		List<McdDimCampType> list = null;
		try {
			String sql = "SELECT * FROM mcd_dim_camp_type order by CAMPSEG_TYPE_ID asc";
			return (List<McdDimCampType>) this.getJdbcTemplate().query(sql, 
					new RowMapperResultSetExtractor<McdDimCampType>(new RowMapper<McdDimCampType>() {
				@Override
				public McdDimCampType mapRow(ResultSet rs, int rowNum) throws SQLException {
					McdDimCampType campsegType = new McdDimCampType();
					campsegType.setCampsegTypeId(Short.parseShort(rs.getString("CAMPSEG_TYPE_ID")));
					campsegType.setCampsegTypeName(rs.getString("CAMPSEG_TYPE_NAME"));
					return campsegType;
				}
			}, 0));
		} catch (Exception e) {
			log.error("",e);
		}
		return list;
	}

}
