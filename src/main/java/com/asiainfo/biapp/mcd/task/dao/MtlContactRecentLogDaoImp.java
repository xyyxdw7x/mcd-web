package com.asiainfo.biapp.mcd.task.dao;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
@Repository("mtlContactRecentLogDao")
public class MtlContactRecentLogDaoImp extends JdbcDaoBase implements MtlContactRecentLogDao{
	
	@Override
	public void batchDeleteContactInMem(String day){
		StringBuffer sqlClause = new StringBuffer(" delete from MTL_CONTACT_RECENT_LOG where 1=1 ");
		sqlClause.append("and to_char(LOG_TIME,'yyyyMMdd')<?");
		Object[] parm={day};
		this.getJdbcTemplate().update(sqlClause.toString(),parm);
	}
	@Override
	public void batchDeleteContactActivityInMem(String day){
		StringBuffer sqlClause = new StringBuffer(" delete from MTL_CONTACT_ACTIVITY_RCT_LOG where 1=1 ");
		sqlClause.append(" and to_char(LOG_TIME,'yyyyMMdd')<?");
		Object[] parm={day};
		this.getJdbcTemplate().update(sqlClause.toString(),parm);
	}

}
