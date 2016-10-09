package com.asiainfo.biapp.mcd.tactics.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.tactics.dao.ITmpProcessApproveInfoDao;

/**
 * 临时用于跳过审核的dao
 * 
 * @author AsiaInfo-wulg
 *
 */
@Repository("tempProcessApproveInfoDao")
public class TempProcessApproveInfoDaoImpl extends JdbcDaoBase implements ITmpProcessApproveInfoDao {

	@Override
	public void updateMcdCampDef(String campId) {
		String sql = "update mcd_camp_def a set a.approve_flow_id = ? where a.campseg_id = ? or a.campseg_pid = ?";

		this.getJdbcTemplate().update(sql, campId, campId, campId);
	}

	@Override
	public List<Map<String, Object>> queryChannels(String campId) {
		String sql = "select channel_id from mcd_camp_channel_list where campseg_id in (select campseg_id from mcd_camp_def where campseg_id = ? or campseg_pid=?)";

		return this.getJdbcTemplate().queryForList(sql, campId, campId);
	}

	@Override
	public List<Map<String, Object>> queryCamps(String campId) {
		String sql = "select campseg_id from mcd_camp_def a where a.campseg_id = ? or a.campseg_pid = ?";

		return this.getJdbcTemplate().queryForList(sql, campId, campId);
	}

}
