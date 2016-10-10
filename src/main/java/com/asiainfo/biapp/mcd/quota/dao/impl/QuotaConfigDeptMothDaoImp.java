package com.asiainfo.biapp.mcd.quota.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.quota.dao.IQuotaConfigDeptMothDao;
import com.asiainfo.biapp.mcd.quota.vo.DeptMonthQuota;
import com.asiainfo.biapp.mcd.quota.vo.QuotaConfigDeptMoth;

@Repository(value="quotaConfigDeptMothDao")
public class QuotaConfigDeptMothDaoImp extends JdbcDaoBase implements IQuotaConfigDeptMothDao {

	private static Logger log = LogManager.getLogger();

	private static final String TABLE = "mcd_quota_config_dept";
	private static final String COL_CITY_ID = "CITY_ID";
	private static final String COL_DEPT_ID = "DEPT_ID";
	private static final String COL_DATA_DATE = "DATA_DATE";
	private static final String COL_DEPT_MONTH_QUOTA = "MONTH_QUOTA_NUM";

	@Override
	public void updateDepMonthQuotaInMem(QuotaConfigDeptMoth depMonthQuota,int newQuota) throws DataAccessException {
		String sql = "update " + TABLE + "  set day_num=" + newQuota+ " where city_id=? and dept_id=? and data_date=?";
		Object[] para = { depMonthQuota.getCityId(), depMonthQuota.getDeptId(),depMonthQuota.getDataDate() };
		this.getJdbcTemplate().update(sql, para);
	}

	@Override
	public QuotaConfigDeptMoth getByKeysInMem(QuotaConfigDeptMoth depDayQuota)throws DataAccessException {
		QuotaConfigDeptMoth renObj = null;
		String sql = "select * from " + TABLE + " where city_id=? and dept_id=? and data_date=?";
		Object[] para = { depDayQuota.getCityId(), depDayQuota.getDeptId(),depDayQuota.getDataDate() };

		try {
			renObj = (QuotaConfigDeptMoth) this.getJdbcTemplate()
					.queryForObject(sql, para, new RowMapper<QuotaConfigDeptMoth>() {
						@Override
						public QuotaConfigDeptMoth mapRow(ResultSet rs,int rowNum) throws SQLException {
							QuotaConfigDeptMoth quotaDeptMonth = new QuotaConfigDeptMoth();
							quotaDeptMonth.setCityId(rs.getString(COL_CITY_ID));
							quotaDeptMonth.setDataDate(rs.getString(COL_DATA_DATE));
							quotaDeptMonth.setDeptId(rs.getString(COL_DEPT_ID));
							quotaDeptMonth.setMonthQuotaNum(rs.getInt(COL_DEPT_MONTH_QUOTA));
							return quotaDeptMonth;
						}

					});
		} catch (DataAccessException e) {
			log.error("根据主键查询数据出错");
			throw e;

		}
		return renObj;
	}

	@Override
	public QuotaConfigDeptMoth getByKeysInMem(String cityID, String deptId,String dataDate) throws DataAccessException {
		QuotaConfigDeptMoth renObj = null;
		String sql = "select * from " + TABLE + " where city_id=? and dept_id=? and data_date=?";
		Object[] para = { cityID, deptId, dataDate };
		try {
			renObj = (QuotaConfigDeptMoth)this.getJdbcTemplate()
					.queryForObject(sql, para, new RowMapper<QuotaConfigDeptMoth>() {
						@Override
						public QuotaConfigDeptMoth mapRow(ResultSet rs,int rowNum) throws SQLException {
							QuotaConfigDeptMoth quotaDeptMonth = new QuotaConfigDeptMoth();
							quotaDeptMonth.setCityId(rs.getString(COL_CITY_ID));
							quotaDeptMonth.setDataDate(rs.getString(COL_DATA_DATE));
							quotaDeptMonth.setDeptId(rs.getString(COL_DEPT_ID));
							quotaDeptMonth.setMonthQuotaNum(rs.getInt(COL_DEPT_MONTH_QUOTA));
							return quotaDeptMonth;
						}

					});
		} catch (DataAccessException e) {
			log.error("根据主键查询部门月配额出错");
			throw e;
		}
		return renObj;
	}



	// 获得地市所有科室的月限额之和
	@Override
	public int getTotal4CityDeptMonthInMem(String cityId, String month)throws DataAccessException {
		int total = 0;
		String sql = "select MONTH_QUOTA_NUM from " + TABLE + " where CITY_ID=? and DATA_DATE=?";
		Object[] parms = { cityId, month };

		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql, parms);
		
		for (Map<String,Object> map : list) {
			int tempQuota = Integer.parseInt(map.get("MONTH_QUOTA_NUM").toString());
			total += tempQuota;
		}
		return total;
	}

	@Override
	public void updateBatchUpdateInMem(final List<QuotaConfigDeptMoth> list)throws DataAccessException {
		String sql = "update " + TABLE + " set MONTH_QUOTA_NUM=? where CITY_ID=? and DATA_DATE=? and DEPT_ID=?";

		try {
			this.getJdbcTemplate().batchUpdate(sql,new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps, int index)throws SQLException {
							ps.setInt(1, list.get(index).getMonthQuotaNum());
							ps.setString(2, list.get(index).getCityId());
							ps.setString(3, list.get(index).getDataDate());
							ps.setString(4, list.get(index).getDeptId());

						}

						@Override
						public int getBatchSize() {
							return list.size();
						}
					});
		} catch (DataAccessException e) {
			log.error("批量保存数据异常");
			throw e;
		}

	}

	@Override
	public void saveBatchSaveOrUpdateInMem(final List<DeptMonthQuota> list) {
		String delSql = "delete from " + TABLE + " where CITY_ID=? and DATA_DATE=?";
		String saveSql = "insert into " + TABLE + "(MONTH_QUOTA_NUM,CITY_ID,DATA_DATE,DEPT_ID) values(?,?,?,?)";
		try {
			this.getJdbcTemplate().batchUpdate(delSql,new BatchPreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps, int index)throws SQLException {
							ps.setString(1, list.get(index).getCityId());
							ps.setString(2, list.get(index).getDataDate());
						}

						@Override
						public int getBatchSize() {
							return list.size();
						}
					});
		} catch (DataAccessException e) {
			log.error("批量删除时报错....");
			throw e;
		}

		try {
			this.getJdbcTemplate().batchUpdate(saveSql,new BatchPreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps, int index)throws SQLException {
							ps.setLong(1, list.get(index).getMonthQuotaNum());
							ps.setString(2, list.get(index).getCityId());
							ps.setString(3, list.get(index).getDataDate());
							ps.setString(4, list.get(index).getDeptId());

						}

						@Override
						public int getBatchSize() {
							return list.size();
						}
					});
		} catch (DataAccessException e) {
			log.error("批量保存时报错....");
			throw e;
		}

	}

	@Override
	public int getQuotaByKeysInMem(String cityID, String deptId, String dataDate)throws DataAccessException {
		int num = 0;
		String sql = "select MONTH_QUOTA_NUM from " + TABLE + " where city_id=? and dept_id=? and data_date=?";
		Object[] para = { cityID, deptId, dataDate };
		try {
			num = this.getJdbcTemplate().queryForObject(sql, new Object[] { para }, Integer.class);  
		} catch (DataAccessException e) {
			log.error("未查询到指定的科室，将返回0");
			return 0;
		}

		return num;
	}
	@Override
	public void saveBatchSaveInMem(final List<QuotaConfigDeptMoth> list){
		String saveSql = "insert into " + TABLE + "(MONTH_QUOTA_NUM,CITY_ID,DATA_DATE,DEPT_ID) values(?,?,?,?)";

		try {
			this.getJdbcTemplate().batchUpdate(saveSql,new BatchPreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps, int index)throws SQLException {
							ps.setInt(1, list.get(index).getMonthQuotaNum());
							ps.setString(2, list.get(index).getCityId());
							ps.setString(3, list.get(index).getDataDate());
							ps.setString(4, list.get(index).getDeptId());
						}
						@Override
						public int getBatchSize() {
							return list.size();
						}
					});
		} catch (DataAccessException e) {
			log.error("批量保存时报错....",e);
			throw e;
		}
		
	}

	@Override
	public List<Map<String, Object>> getQuotas4DeptsInMem(String cityId, String date) throws Exception {
		String sql = "select * from mtl_user_dept t right join mcd_quota_config_dept t1 on t.city_id = t1.city_id and t.dept_id = t1.dept_id where t1.city_id = ? and t1.data_date = ? order by t1.dept_id";
		return this.getJdbcTemplate().queryForList(sql, new Object[] { cityId, date });
	}

	
}
