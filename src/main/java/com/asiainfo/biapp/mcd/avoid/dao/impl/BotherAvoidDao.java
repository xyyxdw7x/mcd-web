package com.asiainfo.biapp.mcd.avoid.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.mcd.avoid.dao.IMcdMtlBotherAvoidDao;
import com.asiainfo.biapp.mcd.avoid.jms.util.SpringContext;
import com.asiainfo.biapp.mcd.avoid.model.MtlBotherAvoid;
import com.asiainfo.biapp.mcd.common.util.DataBaseAdapter;
import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.common.util.Pager;

@Repository(value="botherAvoidDao")
public class BotherAvoidDao extends JdbcDaoBase implements IMcdMtlBotherAvoidDao {
	private static Logger log = LogManager.getLogger();

	@Override
	@SuppressWarnings("unchecked")
	public List searchBotherAvoidUser(Pager pager, MtlBotherAvoid mtlBotherAvoid) {
		
		StringBuffer buffer = new StringBuffer();

		List plist = new ArrayList();

		buffer.append("SELECT A.AVOID_BOTHER_TYPE, A.AVOID_CUST_TYPE, A.PRODUCT_NO, to_char(A.ENTER_TIME, 'yyyy.mm.dd') as ENTER_TIME, A.USER_TYPE_ID, A.CREATE_USER_ID,")
		 .append(" A.CREATE_USER_NAME, B.NAME AS USER_TYPE_NAME,C.CAMPSEG_TYPE_NAME,D.CHANNELTYPE_NAME")
		 .append(" FROM MCD_ZJ_AD.MTL_BOTHER_AVOID A")
		 .append(" INNER JOIN DIM_BOTHER_AVOID_USER_TYPE B ON A.USER_TYPE_ID  = B.ID")
		 .append(" INNER JOIN DIM_CAMPSEG_TYPE C ON A.AVOID_CUST_TYPE = C.CAMPSEG_TYPE_ID")
		 .append(" INNER JOIN DIM_Mtl_Channeltype D ON A.AVOID_BOTHER_TYPE = D.CHANNELTYPE_ID");
		 
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
		List list = this.getJdbcTemplate().queryForList(sqlExt.toString(), plist.toArray());
		List listSize = this.getJdbcTemplate().queryForList(buffer.toString(), plist.toArray());
		pager.setTotalSize(listSize.size());  // 总记录数
		
		return list;
	}
	
	@Override
	public void addBotherAvoidUser(List<MtlBotherAvoid> list) throws Exception {
		StringBuffer insertSql = new StringBuffer("INSERT INTO MTL_BOTHER_AVOID ");
		insertSql
				.append("(AVOID_BOTHER_TYPE,AVOID_CUST_TYPE,PRODUCT_NO,ENTER_TIME,USER_TYPE_ID,CREATE_USER_ID,CREATE_USER_NAME)")
				.append(" VALUES(?,?,?,SYSTIMESTAMP,?,?,?)");
		long startTime=new Date().getTime();
		final List Locallist = list;
		JdbcTemplate sqlFireTemplate = SpringContext.getBean("sqlFireJdbcTemplate", JdbcTemplate.class);
		
		sqlFireTemplate.execute(insertSql.toString(), new PreparedStatementCallback() {
			@Override
			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				Iterator it = Locallist.iterator();
				int i = 0;
				while (it.hasNext()) {
					MtlBotherAvoid mtl = (MtlBotherAvoid) it.next();
			
					ps.setString(1,mtl.getAvoidBotherType());
					ps.setInt(2,mtl.getAvoidCustType());
					ps.setString(3,mtl.getProductNo());
					ps.setInt(4,mtl.getUserTypeId());
					ps.setString(5,mtl.getCreateUserId());
					ps.setString(6,mtl.getCreateUserName());

					ps.addBatch();
					i++;
				}
				int[] results = ps.executeBatch();
				log.info("免打扰客户新增结果:"+results.length);
				return null;
			}

		});
		
		long endTime=new Date().getTime();
		log.info("免打扰客户新增耗时:"+(endTime-startTime)+"毫秒");
	}

	@Override
	public int chkIsExist(MtlBotherAvoid mtl) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT(1) FROM MTL_BOTHER_AVOID WHERE ");
		sb.append(" AVOID_BOTHER_TYPE = '"+mtl.getAvoidBotherType()+"'");
		sb.append(" AND AVOID_CUST_TYPE = '"+mtl.getAvoidCustType()+"'");
		sb.append(" AND PRODUCT_NO = '"+mtl.getProductNo()+"'");
		log.info("检查免打扰用户是否已经存在："+sb.toString());
		
		JdbcTemplate sqlFireTemplate = SpringContext.getBean("sqlFireJdbcTemplate", JdbcTemplate.class);
		
		return sqlFireTemplate.queryForObject(sb.toString(), Integer.class).intValue();
	}
	
	@Override
	public void mdfBotherAvoidUser(List<MtlBotherAvoid> list) {
		MtlBotherAvoid oldMtl = list.get(0);//修改前
		MtlBotherAvoid newMtl = list.get(1);//修改后
		
		List<Object> plist = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		
		sb.append("UPDATE MTL_BOTHER_AVOID SET ")
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
		JdbcTemplate sqlFireTemplate = SpringContext.getBean("sqlFireJdbcTemplate", JdbcTemplate.class);
		sqlFireTemplate.update(sb.toString(),plist.toArray());
	}
	
	@Override
	public void delBotherAvoidUser(MtlBotherAvoid mtl) {
	
		List<Object> plist = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		
		sb.append("DELETE FROM MTL_BOTHER_AVOID ")
		.append(" WHERE AVOID_BOTHER_TYPE = ?")
		.append(" AND AVOID_CUST_TYPE = ?")
		.append(" AND PRODUCT_NO = ?");
		
		plist.add(mtl.getAvoidBotherType());
		plist.add(mtl.getAvoidCustType());
		plist.add(mtl.getProductNo());
		
		log.info("删除免打扰客户："+sb.toString());
		JdbcTemplate sqlFireTemplate = SpringContext.getBean("sqlFireJdbcTemplate", JdbcTemplate.class);
		sqlFireTemplate.update(sb.toString(),plist.toArray());
	}
	
	@Override
	public void batchDelBotherAvoidUser(List<MtlBotherAvoid> list) {
		StringBuffer deleteSql = new StringBuffer();
		deleteSql.append("DELETE FROM MTL_BOTHER_AVOID WHERE ")
		.append("AVOID_BOTHER_TYPE = ? ")
		.append("AND AVOID_CUST_TYPE = ? ")
		.append("AND PRODUCT_NO = ? ");
		
		long startTime=new Date().getTime();
		final List Locallist = list;
		JdbcTemplate sqlFireTemplate = SpringContext.getBean("sqlFireJdbcTemplate", JdbcTemplate.class);
		
		sqlFireTemplate.execute(deleteSql.toString(), new PreparedStatementCallback() {
			@Override
			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				Iterator it = Locallist.iterator();
				int i = 0;
				while (it.hasNext()) {
					MtlBotherAvoid mtl = (MtlBotherAvoid) it.next();
					ps.setString(1,mtl.getAvoidBotherType());
					ps.setInt(2,mtl.getAvoidCustType());
					ps.setString(3,mtl.getProductNo());
					ps.addBatch();
					i++;
				}
				int[] results = ps.executeBatch();
				log.info("批量免打扰客户刪除结果:"+results.length);
				return null;
			}
		});
		
		long endTime=new Date().getTime();
		log.info("免打扰客户批量刪除耗时:"+(endTime-startTime)+"毫秒");
	}
}