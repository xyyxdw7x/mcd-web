package com.asiainfo.biapp.mcd.quota.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.asiainfo.biapp.mcd.quota.dao.QuotaMonthDeptUsedDao;
import com.asiainfo.biapp.mcd.quota.vo.QuotaMonthDeptUsed;

public class QuotaMonthDeptUsedDaoImp extends JdbcDaoSupport implements
		QuotaMonthDeptUsedDao {
	private static final Logger log = LogManager.getLogger();
	
	private static String TABLE = "mcd_quota_used_dept_m";
	
	@Override
	public QuotaMonthDeptUsed updateDayQuotaUsed(
			QuotaMonthDeptUsed depDayQuota, int newQuota) {
		return null;
	}

	@Override
	public void saveBatchSaveInMem(final List<QuotaMonthDeptUsed> list) {
		String sql ="insert into mcd_quota_used_dept_m(city_id,dept_id,data_date,used_num)values(?,?,?,?)";
		this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				ps.setString(1, list.get(index).getCityId());
				ps.setString(2, list.get(index).getDeptId());
				ps.setString(3, list.get(index).getDataDate());
				ps.setInt(4, list.get(index).getUsedNum());
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
	}

	@Override
	public int getByKeys(QuotaMonthDeptUsed depDayQuota) {
		return 0;
	}

	@Override
	public List<Map<String, Object>> getDeptMonthUsedInMem(String cityId, String month, String ordyBy)
			throws DataAccessException {

		List<Map<String, Object>> list = null;
		String sql = "select * from " + TABLE + " where CITY_ID=? and DATA_DATE=? order by " + ordyBy;
		Object[] parms = {cityId,month};
		try {
			list = this.getJdbcTemplate().queryForList(sql,parms);
		} catch (DataAccessException e) {
			log.error("查询各科室月使用额出错！！！");
			return null;
		}

		return list;
	}
	@Override
	public List<Map<String, Object>> getDeptMonthUsedInMem(String cityId, String month)
			throws DataAccessException {
		
		List<Map<String, Object>> list = null;
		String sql = "select * from " + TABLE + " where CITY_ID=? and DATA_DATE=? order by DEPT_ID";
		Object[] parms = {cityId,month};
		try {
			list = this.getJdbcTemplate().queryForList(sql,parms);
		} catch (DataAccessException e) {
			log.error("查询各科室月使用额出错！！！");
			return null;
		}
		return list;
	}

	
	

}
