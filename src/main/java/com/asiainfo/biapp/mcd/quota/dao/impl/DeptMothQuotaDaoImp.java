package com.asiainfo.biapp.mcd.quota.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.quota.dao.IDeptMonthQuotaDao;
import com.asiainfo.biapp.mcd.quota.util.QuotaUtils;
import com.asiainfo.biapp.mcd.quota.vo.DeptMonthQuota;

@Repository(value="quotaConfigDeptMothDao")
public class DeptMothQuotaDaoImp extends JdbcDaoBase implements IDeptMonthQuotaDao {

	private static Logger log = LogManager.getLogger();


	/**
     * 配额管理界面：查询某地市所有科室的某个月配额（科室月配额和科室月使用额连接查询）
     * @param cityId
     * @param month
     * @return
     * @throws DataAccessException
     */
	@Override
	public List<Map<String, Object>> queryInMemCityDeptsMonthQuota(String cityId, String month)throws DataAccessException {
		List<Map<String, Object>> list = null;
        StringBuffer sql = new StringBuffer();
        sql.append("select dmq.CITY_ID,dmq.DEPT_ID,nvl(dmq.MONTH_QUOTA_NUM,0) MONTH_QUOTA_NUM,"
        		+ "nvl(dmu.USED_NUM,0) USED_NUM,dmq.DATA_DATE ");
        sql.append(" FROM mcd_quota_config_dept dmq ");
        sql.append("  LEFT OUTER JOIN mcd_quota_used_dept_m dmu ")
            .append("  on dmq.dept_id = dmu.dept_id ").
            append("  and dmq.city_id = dmu.city_id ").append(" and dmq.data_date = dmu.data_date ");
        sql.append("  where dmq.data_date = ? ")
            .append(" and dmq.city_id=?");
        
		Object[] parm = { month, cityId };

		try {
			this.getJdbcTemplate().getDataSource().getConnection();
			list = this.getJdbcTemplate().queryForList(sql.toString(), parm);
		} catch (DataAccessException e) {
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
     * 查询所有（地市的）科室某个月配额
     * @param cityId
     * @param month
     * @return
     * @throws DataAccessException
     */
	@Override
	public List<Map<String, Object>> getAllDeptMonthQuotaInMem(String month)throws DataAccessException {
		List<Map<String, Object>> list = null;
        StringBuffer sql = new StringBuffer();
        sql.append("select dmq.CITY_ID,dmq.DEPT_ID,dmq.MONTH_QUOTA_NUM,dmu.USED_NUM,dmq.DATA_DATE ");
        sql.append(" FROM mcd_quota_config_dept dmq ");
        sql.append("  LEFT OUTER JOIN mcd_quota_used_dept_m dmu ")
            .append("  on dmq.dept_id = dmu.dept_id ").
            append("  and dmq.city_id = dmu.city_id ").append(" and dmq.data_date = dmu.data_date ");
        sql.append("  where dmq.data_date = ? ");
        
		Object[] parm = { month };

		try {
			list = this.getJdbcTemplate().queryForList(sql.toString(), parm);
		} catch (DataAccessException e) {
			return null;
		}
		return list;
	}

	/**
	 * 查询某个地市的月配额信息
	 * @param cityId
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public Map<String, Object> getCityStatisInMem(String cityId)throws DataAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		String currentMonth = QuotaUtils.getDayMonth("yyyyMM");
		StringBuffer sql = new StringBuffer();
		sql.append("select cq.*,t.USED_NUM from mcd_quota_config_city cq ")
				.append(" LEFT OUTER JOIN (select * from mcd_quota_used_city where DATA_DATE=?) t ")
				.append(" on cq.city_id=t.city_id ")
				.append(" where cq.CITY_ID=? ");
		Object[] parm = { currentMonth, cityId };
		try {
			map = this.getJdbcTemplate().queryForMap(sql.toString(), parm);
		} catch (DataAccessException e) {
			return null;
		}
		return map;
	}

	/**
	 * 批量保存或者更新科室月配额：配额管理界面
	 */
	@Override
	public void saveBatchSaveOrUpdateConfigInMem(final List<DeptMonthQuota> list) {
		String delSql = "delete from mcd_quota_config_dept where CITY_ID=? and DATA_DATE=?";
		String saveSql = "insert into mcd_quota_config_dept(MONTH_QUOTA_NUM,CITY_ID,DATA_DATE,DEPT_ID) values(?,?,?,?)";
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

	/**
	 * 获得地市所有科室的月限额之和
	 */
	@Override
	public int getTotal4CityDeptMonthInMem(String cityId, String month)throws DataAccessException {
		int total = 0;
		String sql = "select MONTH_QUOTA_NUM from mcd_quota_config_dept where CITY_ID=? and DATA_DATE=?";
		Object[] parms = { cityId, month };
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql, parms);
		for (Map<String,Object> map : list) {
			int tempQuota = Integer.parseInt(map.get("MONTH_QUOTA_NUM").toString());
			total += tempQuota;
		}
		return total;
	}

	/**
	 * 批量保存科室月配额
	 */
	@Override
	public void saveBatchSaveDeptMonConfInMem(final List<DeptMonthQuota> list){
		String saveSql = "insert into mcd_quota_config_dept(MONTH_QUOTA_NUM,CITY_ID,DATA_DATE,DEPT_ID) values(?,?,?,?)";
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
			log.error("批量保存时报错....",e);
			throw e;
		}
		
	}

/**
 * 批量保存科室月使用额
 */
	@Override
	public void saveBatchSaveUsedInMem(final List<DeptMonthQuota> list) {
		String sql ="insert into mcd_quota_used_dept_m(city_id,dept_id,data_date,used_num)values(?,?,?,?)";
		this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				ps.setString(1, list.get(index).getCityId());
				ps.setString(2, list.get(index).getDeptId());
				ps.setString(3, list.get(index).getDataDate());
				ps.setLong(4, list.get(index).getUsedNum());
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
	}
	/**
	 * 批量保存或更新科室默认配额
	 */
	@Override
	public void saveBatchSaveDefaultInMem(final List<DeptMonthQuota> list, String cityId)throws DataAccessException {
		String delSql = "DELETE FROM MTL_QUOTA_DEPT_M_DEFAULT WHERE CITY_ID=?";
		Object[] delPparm = { cityId };
		String saveSql = "insert into MTL_QUOTA_DEPT_M_DEFAULT(CITY_ID,DEPT_ID,MONTH_QUOTA_NUM) values (?,?,?)";

		try {
			this.getJdbcTemplate().update(delSql, delPparm);

			this.getJdbcTemplate().batchUpdate(saveSql,new BatchPreparedStatementSetter() {
						@Override
						public int getBatchSize() {
							return list.size();
						}

						@Override
						public void setValues(PreparedStatement ps, int index)
								throws SQLException {
							ps.setString(1, list.get(index).getCityId());
							ps.setString(2, list.get(index).getDeptId());
							ps.setLong(3, list.get(index).getMonthQuotaNum());

						}

					});
		} catch (DataAccessException e) {
			log.error("删除或者插入数据时出错");
			throw e;
		}

	}
	
	
	/**
	 * 查询所有（地市的）科室的默认配额
	 */
	@Override
	public List<Map<String, Object>> queryAllDeptDefQuotaInMem() throws DataAccessException {
		String sql = "select CITY_ID,DEPT_ID,MONTH_QUOTA_NUM  DEFAULT_MONTH_QUOTA_NUM FROM MTL_QUOTA_DEPT_M_DEFAULT";
		List<Map<String, Object>> list = null;
		try {
			list = this.getJdbcTemplate().queryForList(sql);
		} catch (DataAccessException e) {
			log.error("删除或者插入数据时出错");
			throw e;
		}
		return list;

	}
	
}
