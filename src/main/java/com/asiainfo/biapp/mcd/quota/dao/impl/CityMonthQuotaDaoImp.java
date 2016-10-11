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
import com.asiainfo.biapp.mcd.quota.dao.ICityMonthQuotaDao;
import com.asiainfo.biapp.mcd.quota.vo.CityMonthQuota;

@Repository(value="quotaConfigCityMothDao")
public class CityMonthQuotaDaoImp extends JdbcDaoBase implements ICityMonthQuotaDao {
	private static final Logger log = LogManager.getLogger();
	private static final String TABLE = "mcd_quota_config_city";

	@Override
	public int queryCityMonthQuotaInMem(String city)throws DataAccessException {
		int ren = 0;
		List<Map<String,Object>> list=null;;
		String sql = "select nvl(MONTH_QUOTA_NUM,0) MONTH_QUOTA_NUM " + " from " + TABLE + " where CITY_ID=?";
		try {
			list = this.getJdbcTemplate().queryForList(sql, new Object[] { city });  
			if(list!=null && list.size()>0){
				ren = Integer.parseInt(list.get(0).get("MONTH_QUOTA_NUM").toString());
			}
		} catch (DataAccessException e) {
			log.error("查询地市月配额出错！！！将返回0",e);
		}
		return ren;
	}
	@Override
	public List<Map<String,Object>> queryAllInMem(){
		List<Map<String,Object>> list=null;
		String sql = "select * from mcd_quota_config_city";
		list = this.getJdbcTemplate().queryForList(sql);
		return list;
	}
	
	@Override
	public void saveBatchSaveCityMonthUsed(final List<CityMonthQuota> list) {
		String sql = "insert into mcd_quota_used_city(city_id,data_date,used_num)values(?,?,?)";
		try {
			this.getJdbcTemplate().batchUpdate(sql,new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps, int index)throws SQLException {
							ps.setString(1, list.get(index).getCityId());
							ps.setString(2, list.get(index).getDataDate());
							ps.setInt(3, list.get(index).getUsedNum());
						}

						@Override
						public int getBatchSize() {
							return list.size();
						}
					});
		} catch (DataAccessException e) {
			log.error("插入地市月使用额出错！！！");
			throw e;
		}
	}

}
