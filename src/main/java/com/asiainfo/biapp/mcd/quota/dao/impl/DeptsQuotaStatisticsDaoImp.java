package com.asiainfo.biapp.mcd.quota.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.quota.dao.IDeptsQuotaStatisticsDao;
import com.asiainfo.biapp.mcd.quota.util.QuotaUtils;

@Repository(value="deptsQuotaStatisticsDao")
public class DeptsQuotaStatisticsDaoImp extends JdbcDaoBase implements IDeptsQuotaStatisticsDao {

	/**
     * 配额管理界面：展示科室月配额（科室月配额和科室月使用额连接查询）
     * @param cityId
     * @param month
     * @return
     * @throws DataAccessException
     */
	@Override
	public List<Map<String, Object>> getStatisticsInMem(String cityId, String month)throws DataAccessException {
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
			list = this.getJdbcTemplate().queryForList(sql.toString(), parm);
		} catch (DataAccessException e) {
			return null;
		}
		return list;

	}

	/**
	 * 查询地市月配额：主要用于群发管理展示页面上半部分（当月配额）的百分比
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

	
	//以下三个方法在定时任务中使用----------------------------------------------------------------------------------
	
    /**
     * 根据地市月限额配置查询地市月使用额，如果没有配置使用额，则使用额为0。（以地市月限额表为左表---city）
     * @param month
     * @return
     * @throws DataAccessException
     */
	@Override
	public List<Map<String, Object>> getConfigedCityUsedInMem(String month)throws DataAccessException {
		List<Map<String, Object>> list = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select cc.city_id,cu.used_num,cu.data_date from mcd_quota_config_city cc")
		.append(" LEFT OUTER JOIN (select city_id,data_date,used_num from mcd_quota_used_city where data_date=?)cu ")
		.append(" on cc.city_id=cu.city_id ");
		Object[] parm = { month };

		try {
			list = this.getJdbcTemplate().queryForList(sql.toString(), parm);
		} catch (DataAccessException e) {
			return null;
		}
		return list;
	}

   /**
     * 查询所有科室（地市科室表）的月配额和月使用额情况
     * @param month
     * @return
     */
	@Override
	public List<Map<String, Object>> getDeptQuotaStaticInMem(String month) {
		List<Map<String, Object>> list = null;
		// MTL_USER_DEPT为地市科室表（哪些地市下有哪些科室。。。所有科室都将记录在该表中）
		String sql = "select ud.city_id, ud.dept_id,t.month_quota_num,t.used_num,def.month_quota_num defNum "
				+ "from MTL_USER_DEPT ud LEFT OUTER JOIN "
				+ "(select dc.city_id,dc.dept_id,dc.data_date,dc.month_quota_num,du.used_num "
				+ "from mcd_quota_config_dept dc LEFT OUTER JOIN mcd_quota_used_dept_m du "
				+ "on dc.city_id = du.city_id and dc.dept_id = du.dept_id and dc.data_date = du.data_date "
				+ "where dc.data_date = ?) t on ud.city_id = t.city_id and ud.dept_id = t.dept_id "
				+ "LEFT OUTER JOIN MTL_QUOTA_DEPT_M_DEFAULT def on ud.city_id = def.city_id and ud.dept_id = def.dept_id";
		Object[] parm = { month };
		try {
			list = this.getJdbcTemplate().queryForList(sql, parm);
		} catch (DataAccessException e) {
			return null;
		}
		return list;
	}

    /**
     * 根据科室月限额配置查找科室对应的日使用额。（以科室月限额表为左表--deptId）
     * @param date
     * @return
     */
	@Override
	public List<Map<String, Object>> getConfigedDayInMem(String date) {
		List<Map<String, Object>> list = null;
		String month = date.substring(0, 6);
		String sql = "select dmc.city_id,dmc.dept_id,dmc.data_date data_date_m,t.data_date,t.day_quota_num,t.used_num "
				+ "from mcd_quota_config_dept dmc LEFT OUTER JOIN "
				+ "(select ddc.city_id,ddc.dept_id,ddc.data_date,ddc.day_quota_num,ddc.data_date_m,ddu.used_num "
				+ " from mcd_quota_config_dept_D ddc LEFT OUTER JOIN MTL_QUOTA_D_DEPT_USED ddu "
				+ "on ddc.city_id = ddu.city_id and ddc.dept_id = ddu.dept_id and ddc.data_date = ddu.data_date "
				+ "where ddc.data_date = ?) t "
				+ "on dmc.city_id = t.city_id and dmc.dept_id = t.dept_id "
				+ "where dmc.data_date = ?";
		Object[] parm = { date, month };
		try {
			list = this.getJdbcTemplate().queryForList(sql, parm);
		} catch (DataAccessException e) {
			return null;
		}

		return list;
	}

}
