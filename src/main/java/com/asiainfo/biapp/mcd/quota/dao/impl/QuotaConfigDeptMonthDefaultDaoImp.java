package com.asiainfo.biapp.mcd.quota.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.quota.dao.IQuotaConfigDeptMonthDefaultDao;
import com.asiainfo.biapp.mcd.quota.vo.DeptMonQuotaDefault;

@Repository(value="quotaConfigDeptMonthDefaultDao")
public class QuotaConfigDeptMonthDefaultDaoImp extends JdbcDaoBase implements
		IQuotaConfigDeptMonthDefaultDao {

	private static final Logger log = LogManager.getLogger();

	private static final String TABLE = "MTL_QUOTA_DEPT_M_DEFAULT";

	@Override
	public void saveBatchSaveInMem(final List<DeptMonQuotaDefault> list, String cityId)
			throws DataAccessException {
		String delSql = "DELETE FROM " + TABLE + " WHERE CITY_ID=?";
		Object[] delPparm = { cityId };
		String saveSql = "insert into " + TABLE + "(CITY_ID,DEPT_ID,MONTH_QUOTA_NUM) values (?,?,?)";

		try {
			this.getJdbcTemplate().update(delSql, delPparm);

			this.getJdbcTemplate().batchUpdate(saveSql,
					new BatchPreparedStatementSetter() {
						@Override
						public int getBatchSize() {
							// TODO Auto-generated method stub
							return list.size();
						}

						@Override
						public void setValues(PreparedStatement ps, int index)
								throws SQLException {
							// TODO Auto-generated method stub
							ps.setString(1, list.get(index).getCityId());
							ps.setString(2, list.get(index).getDeptId());
							ps.setInt(3, list.get(index).getMonthQuotaNum());

						}

					});
		} catch (DataAccessException e) {
			log.error("删除或者插入数据时出错");
			throw e;
		}

	}

	@Override
	public List<Map<String, Object>> getTempletConfigsInMem(String cityId) {
		List<Map<String, Object>> list = null;
		String sql = "select u.DEPT_ID,u.DEPT_NAME,u.CITY_ID,m.MONTH_QUOTA_NUM from MTL_USER_DEPT u LEFT OUTER JOIN MTL_QUOTA_DEPT_M_DEFAULT m on u.DEPT_ID=m.DEPT_ID where u.CITY_ID=?";
		Object[] parm = { cityId };
		try {
			list = this.getJdbcTemplate().queryForList(sql, parm);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("查询模板出错！！！");
			throw e;
		}
		return list;

	}
}
