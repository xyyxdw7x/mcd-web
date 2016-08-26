package com.asiainfo.biapp.mcd.quota.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.jfree.util.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.quota.model.QuotaDayDeptUsed;
import com.asiainfo.biapp.mcd.quota.util.QuotaUtils;

@Repository(value="quotaDayDeptUsedDao")
public class QuotaDayDeptUsedDaoImp extends JdbcDaoBase implements
		QuotaDayDeptUsedDao {
	public static final String TABLE = "MTL_QUOTA_D_DEPT_USED";

	@Override
	public int getByKeys(String cityid, String deptid, String day)
			throws DataAccessException {
		int usedNum = 0;
		String sql = "select USED_NUM from " + TABLE + " where CITY_ID=? and DATA_DATE=? and DEPT_ID=?";
		String[] parms = { cityid, day, deptid };
		try {
			usedNum = this.getJdbcTemplate().queryForObject(  
                    sql, new Object[] { parms }, Integer.class);  
		} catch (DataAccessException e) {
			Log.error("根据cityId，deptId和day查询科室日使用额异常！！可能是+\"" + day
					+ "\"这天在科室日使用表中没有对应记录");
			return 0;
			// throw e;
		}
		return usedNum;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getUsed4DaysInMem(String cityId, String deptId,
			String fromDate, String toDate) throws DataAccessException {
		List<Map<String, Object>> list = null;
		String sql = "select * from "+ TABLE + " where CITY_ID=? and  DEPT_ID=? and DATA_DATE>=? and DATA_DATE<=?";
		Object[] parms = { cityId, deptId, fromDate, toDate };
		try {
			list = this.getJdbcTemplate().queryForList(sql, parms);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			Log.error("查询科室范围内数据出错！！！");
			throw e;
		}
		return list;

	}
	
	@Override
	public int getMonQutoaTotalUntilYesterdayInMem(String cityId, String deptId) {
		// TODO Auto-generated method stub
		int totalUsed=0;
		String fromDate,toDate;
		String month = QuotaUtils.getDayMonth("yyyyMM");//YYYYmm格式的日期
		fromDate=month+"01";
		int day = QuotaUtils.getCurrentDayOfMon();//当前的日期
		if(day==1){//如果当前日期是这个月的第一天
			return 0;
		}else{
			int yesterday=day-1;
			if(yesterday<10){
				toDate="0"+yesterday;
			}else{
				toDate=yesterday+"";
			}
			
		}
		toDate=month+toDate;
		List<Map<String,Object>> used = this.getUsed4DaysInMem(cityId, deptId, fromDate, toDate);
		if(used!=null&&used.size()>0){
			for(int i=0;i<used.size();i++){
				totalUsed+=Integer.parseInt(used.get(i).get("USED_NUM").toString());
			}
		}

		return totalUsed;
	}

	@Override
	public void batchSaveInMem(final List<QuotaDayDeptUsed> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		String sql="insert into MTL_QUOTA_D_DEPT_USED(city_id,dept_id,data_date,used_num)values(?,?,?,?)";
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


    
}
