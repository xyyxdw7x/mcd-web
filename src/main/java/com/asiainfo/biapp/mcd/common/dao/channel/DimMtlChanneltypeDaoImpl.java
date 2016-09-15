package com.asiainfo.biapp.mcd.common.dao.channel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.framework.jdbc.VoPropertyRowMapper;
import com.asiainfo.biapp.mcd.common.util.DataBaseAdapter;
import com.asiainfo.biapp.mcd.common.vo.channel.McdDimChannel;
import com.asiainfo.biapp.mcd.common.vo.channel.DimMtlChanneltype;
import com.asiainfo.biapp.mcd.common.vo.plan.DimPlanSrvType;
import com.asiainfo.biapp.mcd.tactics.exception.MpmException;
import com.asiainfo.biapp.mcd.tactics.vo.McdDimCampType;

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
public class DimMtlChanneltypeDaoImpl  extends JdbcDaoBase implements DimMtlChanneltypeDao {
	

	private static Logger log = LogManager.getLogger();
	
	@Override
	public List<DimPlanSrvType> getGradeList() throws MpmException {
		List<Map<String, Object>> list = null;
		List<DimPlanSrvType> dimPlanSrvTypeList = new ArrayList<DimPlanSrvType>();
		try {
			StringBuffer sbuffer = new StringBuffer();
			sbuffer.append("select * from DIM_PLAN_SRV_TYPE");
			list = this.getJdbcTemplate().queryForList(sbuffer.toString());
			for (Map<String, Object> map : list) {
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
	public List<McdDimChannel> getMtlChannelByCondition(String isDoubleSelect){
		List<McdDimChannel> list=null;
		String sql = "select * from mcd_dim_channel dmc where 1=1 ";
		if("1".equals(isDoubleSelect)){
			sql += " and dmc.channel_id in (902,903,906)";
		}
		sql += " order by dmc.DISPLAY_ORDER";
		try {
			list = this.getJdbcTemplate().query(sql,new RowMapper<McdDimChannel>(){
				@Override
				public McdDimChannel mapRow(ResultSet rs, int arg1) throws SQLException {
					McdDimChannel dimMtlChannel=new McdDimChannel();
					dimMtlChannel.setChannelId(rs.getString("CHANNEL_ID"));
					dimMtlChannel.setChannelName(rs.getString("CHANNEL_NAME"));
					dimMtlChannel.setDisplayOrder(rs.getInt("DISPLAY_ORDER"));
					return dimMtlChannel;
				}
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	
		private ResultSetExtractor<List<DimMtlChanneltype>> resultSetExtractorDimMtlChanneltype = new ResultSetExtractor<List<DimMtlChanneltype>>() {
		@Override
		public List<DimMtlChanneltype> extractData(ResultSet rs) throws SQLException, DataAccessException {  
			List<DimMtlChanneltype> list = new ArrayList<DimMtlChanneltype>();
			if (null != rs) {
				while (rs.next()) {
					DimMtlChanneltype dimMtlChanneltype = new DimMtlChanneltype();
					dimMtlChanneltype.setChannelId(rs.getString("CHANNELTYPE_ID"));
					dimMtlChanneltype.setChanneltypeName(rs.getString("CHANNELTYPE_NAME"));
					dimMtlChanneltype.setActiveFlag(rs.getShort("ACTIVE_FLAG"));
					dimMtlChanneltype.setContactType(rs.getInt("CONTACT_TYPE"));
					dimMtlChanneltype.setAutoSendOdd(rs.getInt("AUTO_SENDODD"));
					dimMtlChanneltype.setInitiativeType(rs.getInt("INITIATIVE_TYPE"));
					dimMtlChanneltype.setDisplayOrder(rs.getInt("DISPLAY_ORDER"));
					list.add(dimMtlChanneltype);
				}
			}
			
			return list;
		}
	};

	public DimMtlChanneltype getMtlChanneltype(Short channeltypeId) throws Exception {
		String sql = "select * from mcd_dim_channeltype where CHANNELTYPE_ID = ?";
		Object[] params = new String[1];
		params[0] = channeltypeId;
		
		return this.getJdbcTemplate().queryForObject(sql, params, DimMtlChanneltype.class);
	}
	public List<DimMtlChanneltype> findMtlChanneltype(DimMtlChanneltype type) throws Exception {
		String sql = "select * from mcd_dim_channeltype dmc where 1=1 ";
		Object[] params = new Object[0];
		if (type != null) {
			if (type.getChanneltypeId() != null) {
				sql += " and dmc.CHANNELTYPE_ID=? ";
				params = new String[1];
				params[0] = type.getChanneltypeId();
			}
		}
		sql += " order by dmc.CHANNELTYPE_ID";
		final String strSql = sql;
		return this.getJdbcTemplate().queryForList(strSql, DimMtlChanneltype.class);
	}

	public void delete(Object channelTypeId) {
		if (null != channelTypeId) {
			Object[] params = new String[1];
			params[0] = channelTypeId;
			String sql = "delete from mcd_dim_channeltype a where a.CHANNELTYPE_ID=?";
			List<Object[]> list = new ArrayList<Object[]>(1);
			list.add(params);
			this.getJdbcTemplate().batchUpdate(sql, list);
		}
	}
	public void save(DimMtlChanneltype dimMtlChanneltype) throws MpmException {
		if (null != dimMtlChanneltype && null != dimMtlChanneltype.getChanneltypeId()) {
			this.delete(dimMtlChanneltype.getChanneltypeId());
			String sql = "insert into mcd_dim_channeltype(CHANNELTYPE_ID, CHANNELTYPE_NAME, ACTIVE_FLAG, CONTACT_TYPE, AUTO_SENDODD, INITIATIVE_TYPE, DISPLAY_ORDER) values (?,?,?,?,?,?,?)";
			Object[] args = new Object[7];
			args[0] = dimMtlChanneltype.getChanneltypeId();
			args[1] = dimMtlChanneltype.getChanneltypeName();
			args[2] = dimMtlChanneltype.getActiveFlag();
			args[3] = dimMtlChanneltype.getContactType();
			args[4] = dimMtlChanneltype.getAutoSendOdd();
			args[5] = dimMtlChanneltype.getInitiativeType();
			args[6] = dimMtlChanneltype.getDisplayOrder();
			this.getJdbcTemplate().update(sql, args);
		}
	}

	public Integer findContactTypeByChannelType(final Integer channelTypeId) throws MpmException {
		final String sql = "select a.CONTACT_TYPE from mcd_dim_channelTYPE a where a.CHANNELTYPE_ID=?";
		Object[] args = new String[1];
		args[0] = channelTypeId;
		Long contactType = this.getJdbcTemplate().query(sql, args, this.longResultSetExtractor);
		return contactType.intValue();
	}

	public Short findContactTypeByChannelTypeAndId(final Short channelTypeId, final String channelId) throws MpmException {
		Short contactType = Short.valueOf((short)1);

		//先按照渠道类型取；
		final String allSql = "select a.CONTACT_TYPE from MTL_DIM_CONTACT_CONTROL_TYPE a where a.CHANNELTYPE_ID=?";
		Object[] args = new String[1];
		args[0] = channelTypeId;
		List<Short> contactList = this.getJdbcTemplate().query(allSql, args, new RowMapper<Short>() {
			@Override
			public Short mapRow(ResultSet rs, int index)
					throws SQLException {
				Short contactType = rs.getShort("CONTACT_TYPE");
				return contactType;
			}
		});

		//如果该渠道类型对应多个接触控制类型
		if(contactList != null && contactList.size() > 1) {
			final String sql = "select a.CONTACT_TYPE from MTL_DIM_CONTACT_CONTROL_TYPE a where a.CHANNELTYPE_ID=? and a.CHANNEL_ID=? ";
			args = new String[2];
			args[0] = channelTypeId;
			args[1] = channelId;
			
			List<Short> contactListMuti = this.getJdbcTemplate().query(sql, args, new RowMapper<Short>() {
				@Override
				public Short mapRow(ResultSet rs, int index)
						throws SQLException {
					Short contactType = rs.getShort("CONTACT_TYPE");
					return contactType;
				}
			});
			
			//如果按照渠道类型和渠道能取到接触类型，就是用这种方式获取。
			if(contactListMuti != null && contactListMuti.size() > 0) {
				contactList.clear();
				contactList.addAll(contactListMuti);
			}

			//否则仍按照渠道类型取。

		}

		if(contactList != null && contactList.size() > 0) {
			contactType = (Short) contactList.get(0);
		}

		return contactType;
	}

	public Integer getSendOddTypeByChannelType(final Integer channelTypeId)
			throws MpmException {
		final String sql = "select a.AUTO_SENDODD from mcd_dim_channelTYPE a where a.CHANNELTYPE_ID=?" ;
		Object[] args = new String[1];
		args[0] = channelTypeId;
		Integer autoSendOdd = this.getJdbcTemplate().query(sql, this.longResultSetExtractor).intValue();
		return autoSendOdd;
	}

	public List<DimMtlChanneltype> getAllChannelType(String containsEvent) throws MpmException {
		String sql = "select * from mcd_dim_channelTYPE where 1 = 1 ";
		Object[] args = new String[0];
		if(StringUtils.isNotEmpty(containsEvent) && "1".equals(containsEvent)){
			sql += " and INITIATIVE_TYPE = ? order by DISPLAY_ORDER ";
			args = new String[1];
			args[0] = containsEvent;
		}else{
			sql += " order by DISPLAY_ORDER ";
		}
		
		return this.getJdbcTemplate().query(sql, args, this.resultSetExtractorDimMtlChanneltype);
	}
	
	
	public List<DimMtlChanneltype> getAllChannelType(String containsEvent,String isRejectWebGateWay) throws MpmException {
		StringBuffer sql = new StringBuffer("select * from mcd_dim_channelTYPE where 1 = 1 ");
		if(StringUtils.isNotEmpty(isRejectWebGateWay) && "1".equals(isRejectWebGateWay)){
			sql.append(" and CHANNELTYPE_ID !=914");
		}
		Object[] args = new String[0];
		if(StringUtils.isNotEmpty(containsEvent) && "1".equals(containsEvent)){
			sql.append(" and INITIATIVE_TYPE = ? order by DISPLAY_ORDER ");
			args = new String[1];
			args[0] = containsEvent;
			
		}else{
			sql.append(" order by DISPLAY_ORDER ");
			
		}
		
		return this.getJdbcTemplate().query(sql.toString(), args, this.resultSetExtractorDimMtlChanneltype);
	}
	
	public List<DimMtlChanneltype> getAllChannelTypeForSys(String SysId) throws MpmException {
		String sql = "select distinct dim.* from mcd_dim_channelTYPE dim,MTL_SYS_CHANNEL_RELATION b where 1 = 1 and dim.CHANNELTYPE_ID=b.CHANNEL_TYPE_ID and b.system_id=?";
		Object[] args = new String[1];
		args[0] = SysId;
		return this.getJdbcTemplate().query(sql,args, this.resultSetExtractorDimMtlChanneltype);
	}
	
	/**
	 * 从mcd_user_channel_relation表（存放用户偏好渠道）获取所有渠道类型
	 * @return
	 * @throws Exception
	 */
	public List<String> getAllChannelTypeFromUserChannelRelation() throws MpmException {
		
		String sql = "select distinct(cr.prefer_channel) as prefer_channel from mcd_user_channel_relation cr order by cr.prefer_channel";
		List<String> list = new ArrayList<String>();

		try {
			String sqlExt = DataBaseAdapter.getPagedSql(sql, 1,1000);
			list = this.getJdbcTemplate().queryForList(sqlExt,String.class);
		} catch (Exception e) {
			log.error("", e);
		} 
		return list;
	}
	@Override
	public List<McdDimCampType> getAllDimCampsegType() throws Exception {
		List<McdDimCampType> list = null;
		String sql = "select * from mcd_dim_camp_type order by CAMPSEG_TYPE_ID asc";
		try {
			list = this.getJdbcTemplate().query(sql,new RowMapper<McdDimCampType>(){

				@Override
				public McdDimCampType mapRow(ResultSet rs, int arg1) throws SQLException {
					McdDimCampType tmp = new McdDimCampType();
					tmp.setCampsegTypeDesc(rs.getString("CAMPSEG_TYPE_DESC"));
					tmp.setCampsegTypeId(rs.getShort("CAMPSEG_TYPE_ID"));
					tmp.setCampsegTypeName(rs.getString("CAMPSEG_TYPE_NAME"));
					return tmp;
				}
				
			});
		} catch (Exception e) {
			log.error("",e);
		}
		return list;
	}
	

}
	