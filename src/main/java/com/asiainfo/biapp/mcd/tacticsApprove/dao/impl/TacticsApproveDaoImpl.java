package com.asiainfo.biapp.mcd.tacticsApprove.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.tacticsApprove.dao.TacticsApproveDao;

/**
 * 获取待审批信息Dao。
 */
@Repository("tacticsApproveDao")
public class TacticsApproveDaoImpl extends JdbcDaoBase implements TacticsApproveDao {

	/**
	 * 获取待审批信息。
	 */
	@Override
	public List<Map<String,Object>> searchApproveInfo(String sql) {
	
		return this.getJdbcTemplate().queryForList(sql.toString());
		
	}
	
}