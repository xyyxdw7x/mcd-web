package com.asiainfo.biapp.mcd.avoid.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.avoid.dao.IMcdMtlBotherAvoidDao;
import com.asiainfo.biapp.mcd.avoid.vo.McdBotherAvoid;
import com.asiainfo.biapp.mcd.common.util.DataBaseAdapter;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.custgroup.vo.McdBotherContactConfig;

/**
 * 免打扰页面的增删改查Dao。
 */
@Repository("botherAvoidDao")
public class BotherAvoidDaoImpl extends JdbcDaoBase implements IMcdMtlBotherAvoidDao {
	private static Logger log = LogManager.getLogger();

	/**
	 * 查询免打扰客户群。
	 * @param pager 分页控制
	 * @param mtlBotherAvoid 免打扰客户的查询条件
	 */
	@Override
	public List<Map<String,Object>> searchBotherAvoidUser(Pager pager, McdBotherAvoid mtlBotherAvoid) {
		
		StringBuffer buffer = new StringBuffer();

		List<Object> plist = new ArrayList<Object>();

		buffer.append("SELECT A.AVOID_BOTHER_TYPE, A.AVOID_CUST_TYPE, A.PRODUCT_NO, to_char(A.ENTER_TIME, 'yyyy.mm.dd') as ENTER_TIME, A.USER_TYPE_ID, A.CREATE_USER_ID,")
		 .append(" A.CREATE_USER_NAME, B.NAME AS USER_TYPE_NAME,C.CAMPSEG_TYPE_NAME,D.CHANNEL_NAME")
		 .append(" FROM MCD_CORE_AD.mcd_bother_avoid A")
		 .append(" INNER JOIN DIM_BOTHER_AVOID_USER_TYPE B ON A.USER_TYPE_ID  = B.ID")
		 .append(" INNER JOIN mcd_dim_camp_type C ON A.AVOID_CUST_TYPE = C.CAMPSEG_TYPE_ID")
		 .append(" INNER JOIN mcd_dim_channel D ON A.AVOID_BOTHER_TYPE = D.CHANNEL_ID");
		 
		if(mtlBotherAvoid.getKeywords() != null && !"".equals(mtlBotherAvoid.getKeywords())) {
			buffer.append(" and (A.PRODUCT_NO like '%" + mtlBotherAvoid.getKeywords() + "%')");
		}
		//客户类型
		if (mtlBotherAvoid.getUserTypeId() != null && !"".equals(mtlBotherAvoid.getUserTypeId())) {
			buffer.append(" and A.USER_TYPE_ID = ? ");
			plist.add(mtlBotherAvoid.getUserTypeId());
		}
		//通用渠道
		if (mtlBotherAvoid.getAvoidBotherType() != null && !"".equals(mtlBotherAvoid.getAvoidBotherType())) {
			buffer.append(" and A.AVOID_BOTHER_TYPE = ? ");
			plist.add(mtlBotherAvoid.getAvoidBotherType());
		}
		//营销类型
		if (mtlBotherAvoid.getAvoidCustType() != null && !"".equals(mtlBotherAvoid.getAvoidCustType())) {
			buffer.append(" and A.AVOID_CUST_TYPE = ? ");
			plist.add(mtlBotherAvoid.getAvoidCustType());
		}
		//排序字段
		if (mtlBotherAvoid.getSortColumn() != null && !"".equals(mtlBotherAvoid.getSortColumn())) {
			if ("ENTER_TIME".equals(mtlBotherAvoid.getSortColumn())) {
				buffer.append(" ORDER BY A.ENTER_TIME ");
				if ("asc".equalsIgnoreCase(mtlBotherAvoid.getSortOrderBy())) {
					buffer.append(" ASC ");
				} else if ("desc".equalsIgnoreCase(mtlBotherAvoid.getSortOrderBy())) {
					buffer.append(" DESC ");
				}
				buffer.append(" , A.PRODUCT_NO");
			}
		}
		
		log.info("执行sql="+buffer);
		
		String sqlExt = DataBaseAdapter.getPagedSql(buffer.toString(), pager.getPageNum(),pager.getPageSize());
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sqlExt.toString(), plist.toArray());
		List<Map<String,Object>> listSize = this.getJdbcTemplate().queryForList(buffer.toString(), plist.toArray());
		pager.setTotalSize(listSize.size());  // 总记录数
		
		return list;
	}
	
	/**
	 * 新增免打扰客户。
	 * @param mtl 免打扰客户vo
	 */
	@Override
	public void addBotherAvoidUserInMem(List<McdBotherAvoid> list) throws Exception {
		StringBuffer insertSql = new StringBuffer("INSERT INTO mcd_bother_avoid ");
		insertSql
				.append("(AVOID_BOTHER_TYPE,AVOID_CUST_TYPE,PRODUCT_NO,ENTER_TIME,USER_TYPE_ID,CREATE_USER_ID,CREATE_USER_NAME)")
				.append(" VALUES(?,?,?,SYSTIMESTAMP,?,?,?)");
		long startTime=new Date().getTime();
		final List<McdBotherAvoid> Locallist = list;
		
		this.getJdbcTemplate().execute(insertSql.toString(), new PreparedStatementCallback<Object>() {
			@Override
			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				Iterator<McdBotherAvoid> it = Locallist.iterator();
				while (it.hasNext()) {
					McdBotherAvoid mtl = (McdBotherAvoid) it.next();
			
					ps.setString(1,mtl.getAvoidBotherType());
					ps.setInt(2,mtl.getAvoidCustType());
					ps.setString(3,mtl.getProductNo());
					ps.setInt(4,mtl.getUserTypeId());
					ps.setString(5,mtl.getCreateUserId());
					ps.setString(6,mtl.getCreateUserName());

					ps.addBatch();
				}
				int[] results = ps.executeBatch();
				log.info("免打扰客户新增结果:"+results.length);
				return null;
			}

		});
		
		long endTime=new Date().getTime();
		log.info("免打扰客户新增耗时:"+(endTime-startTime)+"毫秒");
	}

	/**
	 * 新增免打扰客户群之前，确认新增对象是否已经存在。
	 * @param mtl 免打扰客户vo
	 */
	@Override
	public int findBotherAvoidUserInMem(McdBotherAvoid mtl) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT(1) FROM mcd_bother_avoid WHERE ");
		sb.append(" AVOID_BOTHER_TYPE = '"+mtl.getAvoidBotherType()+"'");
		sb.append(" AND AVOID_CUST_TYPE = '"+mtl.getAvoidCustType()+"'");
		sb.append(" AND PRODUCT_NO = '"+mtl.getProductNo()+"'");
		log.info("检查免打扰用户是否已经存在："+sb.toString());
		
		return this.getJdbcTemplate().queryForObject(sb.toString(), Integer.class).intValue();
	}
	
	/**
	 * 更新免打扰客户。
	 * @param mtl 更新前后的免打扰客户vo
	 */
	@Override
	public void updateBotherAvoidUserInMem(List<McdBotherAvoid> list) {
		McdBotherAvoid oldMtl = list.get(0);//修改前
		McdBotherAvoid newMtl = list.get(1);//修改后
		
		List<Object> plist = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		
		sb.append("UPDATE mcd_bother_avoid SET ")
		.append(" AVOID_BOTHER_TYPE = ?")
		.append(" , AVOID_CUST_TYPE = ?")
		.append(" , USER_TYPE_ID = ?")
		.append(" , ENTER_TIME = SYSTIMESTAMP")
		.append(" WHERE AVOID_BOTHER_TYPE = ?")
		.append(" AND AVOID_CUST_TYPE = ?")
		.append(" AND PRODUCT_NO = ?");
		
		plist.add(newMtl.getAvoidBotherType());
		plist.add(newMtl.getAvoidCustType());
		plist.add(newMtl.getUserTypeId());
		plist.add(oldMtl.getAvoidBotherType());
		plist.add(oldMtl.getAvoidCustType());
		plist.add(oldMtl.getProductNo());
		
		log.info("更新免打扰客户："+sb.toString());
		this.getJdbcTemplate().update(sb.toString(),plist.toArray());
	}
	
	/**
	 * 删除免打扰客户群。
	 * @param mtl 免打扰客户vo
	 */
	@Override
	public void updateDelBotherAvoidUserInMem(McdBotherAvoid mtl) {
	
		List<Object> plist = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		
		sb.append("DELETE FROM mcd_bother_avoid ")
		.append(" WHERE AVOID_BOTHER_TYPE = ?")
		.append(" AND AVOID_CUST_TYPE = ?")
		.append(" AND PRODUCT_NO = ?");
		
		plist.add(mtl.getAvoidBotherType());
		plist.add(mtl.getAvoidCustType());
		plist.add(mtl.getProductNo());
		
		log.info("删除免打扰客户："+sb.toString());
		this.getJdbcTemplate().update(sb.toString(),plist.toArray());
	}
	
	/**
	 * 批量删除免打扰客户群。
	 * @param mtl 免打扰客户vo的list
	 */
	@Override
	public void updatebBatchDelBotherAvoidUserInMem(List<McdBotherAvoid> list) {
		StringBuffer deleteSql = new StringBuffer();
		deleteSql.append("DELETE FROM mcd_bother_avoid WHERE ")
		.append("AVOID_BOTHER_TYPE = ? ")
		.append("AND AVOID_CUST_TYPE = ? ")
		.append("AND PRODUCT_NO = ? ");
		
		long startTime=new Date().getTime();
		final List<McdBotherAvoid> Locallist = list;
		
		this.getJdbcTemplate().execute(deleteSql.toString(), new PreparedStatementCallback<Object>() {
			@Override
			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				Iterator<McdBotherAvoid> it = Locallist.iterator();
				while (it.hasNext()) {
					McdBotherAvoid mtl = (McdBotherAvoid) it.next();
					ps.setString(1,mtl.getAvoidBotherType());
					ps.setInt(2,mtl.getAvoidCustType());
					ps.setString(3,mtl.getProductNo());
					ps.addBatch();
				}
				int[] results = ps.executeBatch();
				log.info("批量免打扰客户刪除结果:"+results.length);
				return null;
			}
		});
		
		long endTime=new Date().getTime();
		log.info("免打扰客户批量刪除耗时:"+(endTime-startTime)+"毫秒");
	}
	
	@Override
	public McdBotherContactConfig getBotherContactConfig(String campsegTypeId,String channelId,int campsegCityType) {
		List<Map<String,Object>> list = null;
		McdBotherContactConfig mtlBotherContactConfig = new McdBotherContactConfig();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT CAMPSEG_TYPE_ID,CHANNEL_ID,AVOID_BOTHER_FLAG,CONTACT_CONTROL_FLAG,PARAM_DAYS,PARAM_NUM FROM mcd_bother_contact_config WHERE CAMPSEG_TYPE_ID = ? AND CHANNEL_ID = ? AND CAMPSEG_CITY_TYPE=?");
			list = this.getJdbcTemplate().queryForList(buffer.toString(), new Object[] { campsegTypeId,channelId,campsegCityType });
			if(CollectionUtils.isNotEmpty(list)){
				for (Map<String, Object> map : list) {
					mtlBotherContactConfig.setChannelId((String) map.get("CHANNEL_ID"));
					mtlBotherContactConfig.setCampsegTypeId(Integer.parseInt(String.valueOf(map.get("CAMPSEG_TYPE_ID"))));
					mtlBotherContactConfig.setAvoidBotherFlag(Integer.parseInt(String.valueOf(map.get("AVOID_BOTHER_FLAG"))));
					mtlBotherContactConfig.setContactControlFlag(Integer.parseInt(String.valueOf(map.get("CONTACT_CONTROL_FLAG"))));
					mtlBotherContactConfig.setParamDays(Integer.parseInt(String.valueOf(map.get("PARAM_DAYS"))));
					mtlBotherContactConfig.setParamNum(Integer.parseInt(String.valueOf(map.get("PARAM_NUM"))));
				}
			}
		} catch (Exception e) {
			log.error("",e);
		}
		return mtlBotherContactConfig;
	}
}