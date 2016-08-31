package com.asiainfo.biapp.mcd.quota.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.asiainfo.biapp.mcd.quota.dao.QuotaMonthDeptUsedDao;
import com.asiainfo.biapp.mcd.quota.vo.QuotaMonthDeptUsed;

public class QuotaMonthDeptUsedDaoImp extends JdbcDaoSupport implements
		QuotaMonthDeptUsedDao {
	private static final Logger log = LogManager.getLogger();
	
	private static String TABLE = "MTL_QUOTA_M_DEPT_USED";
	
	@Override
	public QuotaMonthDeptUsed updateDayQuotaUsed(
			QuotaMonthDeptUsed depDayQuota, int newQuota) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveBatchSaveInMem(final List<QuotaMonthDeptUsed> list) {
		// TODO Auto-generated method stub
		String sql ="insert into MTL_QUOTA_M_DEPT_USED(city_id,dept_id,data_date,used_num)values(?,?,?,?)";
		this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				// TODO Auto-generated method stub
				ps.setString(1, list.get(index).getCityId());
				ps.setString(2, list.get(index).getDeptId());
				ps.setString(3, list.get(index).getDataDate());
				ps.setInt(4, list.get(index).getUsedNum());
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return list.size();
			}
		});
	}

	@Override
	public int getByKeys(QuotaMonthDeptUsed depDayQuota) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Map<String, Object>> getDeptMonthUsedInMem(String cityId, String month, String ordyBy)
			throws DataAccessException {
		// TODO Auto-generated method stub

		List<Map<String, Object>> list = null;
		String sql = "select * from " + TABLE + " where CITY_ID=? and DATA_DATE=? order by " + ordyBy;
		String[] parms = {cityId,month};
		try {
			list = this.getJdbcTemplate().queryForList(sql,parms);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("查询各科室月使用额出错！！！");
			return null;
		}

		return list;
	}
	@Override
	public List<Map<String, Object>> getDeptMonthUsedInMem(String cityId, String month)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<Map<String, Object>> list = null;
		String sql = "select * from " + TABLE + " where CITY_ID=? and DATA_DATE=? order by DEPT_ID";
		Object[] parms = {cityId,month};
		try {
			list = this.getJdbcTemplate().queryForList(sql,parms);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("查询各科室月使用额出错！！！");
			return null;
		}
		return list;
	}

	
	

}
