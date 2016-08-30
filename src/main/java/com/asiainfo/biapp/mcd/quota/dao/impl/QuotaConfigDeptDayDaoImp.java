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
import com.asiainfo.biapp.mcd.quota.dao.QuotaConfigDeptDayDao;
import com.asiainfo.biapp.mcd.quota.model.QuotaConfigDeptDay;
@Repository(value="quotaConfigDeptDayDao")
public class QuotaConfigDeptDayDaoImp extends JdbcDaoBase implements
		QuotaConfigDeptDayDao {
	private static final Logger log = LogManager.getLogger();

	private static final String TABLE = "MTL_QUOTA_CONFIG_DEPT_D";

	@Override
	public void updateDepDayQuotaInMem(QuotaConfigDeptDay depDayQuota, int newQuota)
			throws DataAccessException {
		// TODO Auto-generated method stub
		String sql = "update " + TABLE + "  set day_num=" + newQuota + " where city_id=? and dept_id=? and data_date=?";
		Object[] para = { depDayQuota.getCityId(), depDayQuota.getDeptId(),depDayQuota.getDataDate() };
		try {
			this.getJdbcTemplate().update(sql, para);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("更新日配额时出错！！");
			throw e;
		}
	}

	@Override
	public QuotaConfigDeptDay getByKeysInMem(QuotaConfigDeptDay depDayQuota)
			throws DataAccessException {
		// TODO Auto-generated method stub
		QuotaConfigDeptDay renObj = null;
		String sql = "select * from " + TABLE + " where city_id=? and dept_id=? and data_date=?";
		Object[] para = { depDayQuota.getCityId(), depDayQuota.getDeptId(),depDayQuota.getDataDate() };
		renObj = (QuotaConfigDeptDay) this.getJdbcTemplate().queryForObject(
				sql, para, new RowMapper() {
					@Override
					public QuotaConfigDeptDay mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						QuotaConfigDeptDay quotaDeptDay = new QuotaConfigDeptDay();
						quotaDeptDay.setCityId(rs.getString("CITY_ID"));
						quotaDeptDay.setDataDate(rs.getString("DATA_DATE"));
						quotaDeptDay.setDeptId(rs.getString("DEPT_ID"));
						quotaDeptDay.setDayQuotaNum(rs.getInt("DAY_QUOTA_NUM"));
						quotaDeptDay.setDataDateM("DATA_DATE_M");
						return quotaDeptDay;
					}

				});
		return renObj;
	}

	@Override
	public QuotaConfigDeptDay getByKeysInMem(String cityID, String deptId,
			String dataDate) throws DataAccessException {
		// TODO Auto-generated method stub
		QuotaConfigDeptDay renObj = null;
		String sql = "select * from " + TABLE + " where city_id=? and dept_id=? and data_date=?";
		Object[] para = { cityID, deptId, dataDate };

		try {
			renObj = (QuotaConfigDeptDay) this.getJdbcTemplate()
					.queryForObject(sql, para, new RowMapper() {
						@Override
						public QuotaConfigDeptDay mapRow(ResultSet rs,int rowNum) throws SQLException {
							QuotaConfigDeptDay quotaDeptDay = new QuotaConfigDeptDay();
							quotaDeptDay.setCityId(rs.getString("CITY_ID"));
							quotaDeptDay.setDataDate(rs.getString("DATA_DATE"));
							quotaDeptDay.setDeptId(rs.getString("DEPT_ID"));
							quotaDeptDay.setDayQuotaNum(rs.getInt("DAY_QUOTA_NUM"));
							return quotaDeptDay;
						}

					});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("查询部门日配额时出错！！！");
			throw e;
		}
		return renObj;
	}
	
	@Override
	public int getDayConfNumByKeys(String cityID, String deptId, String date)
			throws DataAccessException {
		// TODO Auto-generated method stub
		int num=0;
		String sql = "select DAY_QUOTA_NUM from " + TABLE + " where city_id=? and dept_id=? and data_date=?";
		String[] parm ={cityID,deptId,date};
		
		try {
			num=this.getJdbcTemplate().queryForObject(  
                    sql, new Object[] { parm }, Integer.class);  
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("查询数据出错");
			return 0;
		}
		return num;
	}

	@Override
	public List<Map<String, Object>> getMonthDaysQuotaInMem(String cityID,
			String deptId, String month) throws DataAccessException {
		// TODO Auto-generated method stub

		List<Map<String, Object>> list = null;
		String sql = "select * from " + TABLE + " where CITY_ID=? and DEPT_ID=? and DATA_DATE_M=?";
		Object[] param = { cityID, deptId, month };
		try {
			list = this.getJdbcTemplate().queryForList(sql, param);
		} catch (DataAccessException e) {
			log.error("查询数据出错！！！");
			throw e;
		}
		return list;
	}

	@Override
	public int getTotal4DaysInMem(String cityId, String deptId, String month,List<Map<String, Object>> list) {
		int totals = 0;
		for (int i = 0; i < list.size(); i++) {
			@SuppressWarnings("rawtypes")
			Map temp = list.get(i);
			totals += Integer.parseInt(temp.get("DAY_QUOTA_NUM").toString());
		}

		return totals;

	}

	@Override
	public void updateDepDayQuotaInMem(String cityId, String deptId, String date,
			int newQuota) throws DataAccessException {
		// TODO Auto-generated method stub
		String sql = "update " + TABLE + "set DAY_QUOTA_NUM=" + newQuota + " where CITY_ID=? and DEPT_ID=? and DATA_DATE=?";
		Object[] param = { cityId, deptId, date };

		try {
			this.getJdbcTemplate().update(sql, param);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("更新+\"" + date + "\"的日配额时出错！！");
			throw e;
		}

	}

	@Override
	public int getDayQuota(String cityID, String deptId, String date)
			throws DataAccessException {
		// TODO Auto-generated method stub
		int dayConf = 0;

		String sql = "select DAY_QUOTA_NUM from " + TABLE + " where CITY_ID=? and DEPT_ID=? and DATA_DATE=?";
		String[] parms = { cityID, deptId, date };
		try {
			dayConf = this.getJdbcTemplate().queryForObject(  
                    sql, new Object[] { parms }, Integer.class);  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("查询日限额+“" + date + "”出错！！！将返回默认值0");
			return 0;
		}

		return dayConf;
	}

	@Override
	public void batchSaveOrUpdateInMem(final List<QuotaConfigDeptDay> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		String delSql = "delete from " + TABLE
				+ " where CITY_ID=? and DEPT_ID=? and DATA_DATE=?";
		try {
			this.getJdbcTemplate().batchUpdate(delSql,
					new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps, int index)
								throws SQLException {
							// TODO Auto-generated method stub
							ps.setString(1, list.get(index).getCityId());
							ps.setString(2, list.get(index).getDeptId());
							ps.setString(3, list.get(index).getDataDate());
						}

						@Override
						public int getBatchSize() {
							// TODO Auto-generated method stub
							return list.size();
						}
					});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("批量删除数据时异常");
			throw e;
		}

		String saveSql = "insert into "
				+ TABLE
				+ "(CITY_ID,DEPT_ID,DATA_DATE,DATA_DATE_M,DAY_QUOTA_NUM)values(?,?,?,?,?)";

		try {
			this.getJdbcTemplate().batchUpdate(saveSql,
					new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps, int index)
								throws SQLException {
							// TODO Auto-generated method stub
							ps.setString(1, list.get(index).getCityId());
							ps.setString(2, list.get(index).getDeptId());
							ps.setString(3, list.get(index).getDataDate());
							ps.setString(4, list.get(index).getDataDateM());
							ps.setInt(5, list.get(index).getDayQuotaNum());
						}

						@Override
						public int getBatchSize() {
							// TODO Auto-generated method stub
							return list.size();
						}
					});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("批量增加数据时异常");
			throw e;
		}

	}

	@Override
    public void batchUpdateDayConfNumInMem(final List<QuotaConfigDeptDay> list){
		String sql = "update MTL_QUOTA_CONFIG_DEPT_D set DAY_QUOTA_NUM=? where CITY_ID=? and DEPT_ID=? and DATA_DATE=?";
        this.getJdbcTemplate().batchUpdate(sql,new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, list.get(index).getDayQuotaNum());
				ps.setString(2, list.get(index).getCityId());
				ps.setString(3, list.get(index).getDeptId());
				ps.setString(4, list.get(index).getDataDate());
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return list.size();
			}
		});   
   }
	@Override
	public List<Map<String,Object>> getDayConfInMem(String cityId,String deptId,String fromDate,String toDate){
		List<Map<String,Object>> list=null;
		String sql="select * from MTL_QUOTA_CONFIG_DEPT_D where city_Id=? and dept_id=? and data_date>=? and data_date<=?";
		Object[] parm = {cityId,deptId,fromDate,toDate};
		
		try {
			list = this.getJdbcTemplate().queryForList(sql, parm);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return list;
	}
    @Override
    public void batchSaveInMem(final List<QuotaConfigDeptDay> list){
    	String sql="insert into MTL_QUOTA_CONFIG_DEPT_D(CITY_ID,DEPT_ID,DATA_DATE,DAY_QUOTA_NUM,DATA_DATE_M)values(?,?,?,?,?)";
    	this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				// TODO Auto-generated method stub
				ps.setString(1, list.get(index).getCityId());
				ps.setString(2,list.get(index).getDeptId());
				ps.setString(3,list.get(index).getDataDate());
				ps.setInt(4, list.get(index).getDayQuotaNum());
				ps.setString(5, list.get(index).getDataDateM());
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return list.size();
			}
		});
    	
    }
	@Override
    public List<Map<String,Object>> queryConfigDeptInMem(String month){
    	List<Map<String,Object>> list = null;
    	String sql = "select DEPT_ID from MTL_QUOTA_CONFIG_DEPT_D  where data_date_m=? group by DEPT_ID";
    	Object[] args ={month};
    	try {
			list = this.getJdbcTemplate().queryForList(sql, args);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    	return list;
    }
}
