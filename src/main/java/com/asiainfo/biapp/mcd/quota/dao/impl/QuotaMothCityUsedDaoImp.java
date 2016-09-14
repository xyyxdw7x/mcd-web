package com.asiainfo.biapp.mcd.quota.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.quota.dao.QuotaMothCityUsedDao;
import com.asiainfo.biapp.mcd.quota.vo.QuotaMonthCityUsed;

public class QuotaMothCityUsedDaoImp extends JdbcDaoBase implements
		QuotaMothCityUsedDao {
	private static final Logger log = LogManager.getLogger();
	
	@Override
	public void saveBatchSave(final List<QuotaMonthCityUsed> list) {

		String sql = "insert into mcd_quota_used_city(city_id,data_date,used_num)values(?,?,?)";

		try {
			this.getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps, int index)
								throws SQLException {
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
