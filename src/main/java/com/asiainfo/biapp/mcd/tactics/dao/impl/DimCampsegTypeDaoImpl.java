/**   
 * @Title: DimCampsegTypeDaoImpl.java
 * @Package com.asiainfo.biapp.mcd.dao.impl
 * @Description: TODO(用一句话描述该文件做什么)
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
import com.asiainfo.biapp.mcd.tactics.vo.DimCampsegType;

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
	/* (non-Javadoc)
	 * @see com.asiainfo.biapp.mcd.dao.IDimCampsegTypeDao#getDimCampsegType(java.lang.Short)
	 */
	@Override
	public DimCampsegType getDimCampsegType(Short campsegTypeId)
			throws Exception {
		// TODO Auto-generated method stub
		DimCampsegType obj = null;
		try {
			String sql = "SELECT * FROM mcd_dim_camp_type WHERE CAMPSEG_TYPE_ID = ? order by CAMPSEG_TYPE_ID asc";
			
			Object args[] = new Object[]{campsegTypeId};  
	        return (DimCampsegType)this.getJdbcTemplate().queryForObject(sql,args,new BeanPropertyRowMapper(DimCampsegType.class));
			
		} catch (Exception e) {
			log.error("",e);
		}
		return obj;
	}
	@Override
	public List<DimCampsegType> getAllDimCampsegType() throws Exception {
		List<DimCampsegType> list = null;
		try {
			String sql = "SELECT * FROM mcd_dim_camp_type order by CAMPSEG_TYPE_ID asc";
			return (List) this.getJdbcTemplate().query(sql, new RowMapperResultSetExtractor(new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					DimCampsegType campsegType = new DimCampsegType();
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
