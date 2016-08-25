package com.asiainfo.biapp.mcd.tactics.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.tactics.dao.IDimMtlChanneltypeDao;
import com.asiainfo.biapp.mcd.tactics.exception.MpmException;
import com.asiainfo.biapp.mcd.tactics.vo.DimMtlChanneltype;
import com.asiainfo.biapp.mcd.tactics.vo.DimPlanSrvType;

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
@Repository("dimMtlChanneltypeDao")
public class DimMtlChanneltypeDaoImpl  extends JdbcDaoBase implements IDimMtlChanneltypeDao {
	@Override
	public List<DimPlanSrvType> getGradeList() throws MpmException {
		List<Map<String, Object>> list = null;
		List<DimPlanSrvType> dimPlanSrvTypeList = new ArrayList<DimPlanSrvType>();
		try {
			StringBuffer sbuffer = new StringBuffer();
			sbuffer.append("select * from DIM_PLAN_SRV_TYPE");
			list = this.getJdbcTemplate().queryForList(sbuffer.toString());
			for (Map map : list) {
				DimPlanSrvType dimPlanSrvType = new DimPlanSrvType();
				dimPlanSrvType.setTypeId((String) map.get("PLAN_TYPE_ID"));
				dimPlanSrvType.setTypeName((String) map.get("PLAN_TYPE_NAME"));
				dimPlanSrvTypeList.add(dimPlanSrvType);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		return dimPlanSrvTypeList;
	}
	@SuppressWarnings("unchecked")
	public List<DimMtlChanneltype> getMtlChanneltypeByCondition(String isDoubleSelect){
		List<DimMtlChanneltype> list=null;
		String sql = "select * from DIM_MTL_CHANNELTYPE dmc where 1=1 ";
		if("1".equals(isDoubleSelect)){
			sql += " and dmc.channeltype_id in (902,903,906)";
		}
		sql += " order by dmc.display_order";
		list = this.getJdbcTemplate().query(sql,new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				DimMtlChanneltype dimMtlChanneltype=new DimMtlChanneltype();
				dimMtlChanneltype.setActiveFlag(rs.getShort("ACTIVE_FLAG"));
				dimMtlChanneltype.setAutoSendOdd(rs.getInt("AUTO_SENDODD"));
				dimMtlChanneltype.setChanneltypeId(rs.getShort("CHANNELTYPE_ID"));
				dimMtlChanneltype.setChanneltypeName(rs.getString("CHANNELTYPE_NAME"));
				dimMtlChanneltype.setContactType(rs.getInt("CONTACT_TYPE"));
				dimMtlChanneltype.setDisplayOrder(rs.getInt("DISPLAY_ORDER"));
				dimMtlChanneltype.setInitiativeType(rs.getInt("INITIATIVE_TYPE"));
				return dimMtlChanneltype;
			}
			
		});
		return list;
	}
}
