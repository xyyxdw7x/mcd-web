package com.asiainfo.biapp.mcd.quota.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.asiainfo.biapp.mcd.quota.model.QuotaMonthCityUsed;

public class QuotaMothCityUsedDaoImp extends JdbcDaoSupport implements
		QuotaMothCityUsedDao {
	private static final Logger log = LogManager.getLogger();
	
	private  JdbcTemplate sqlFireJdbcTemplate;

	public void setSqlFireJdbcTemplate(JdbcTemplate sqlFireJdbcTemplate) {
		this.sqlFireJdbcTemplate = sqlFireJdbcTemplate;
	}

	@Override
	public void batchSave(final List<QuotaMonthCityUsed> list) {

		String sql = "insert into MTL_QUOTA_CITY_USED(city_id,data_date,used_num)values(?,?,?)";

		try {
			this.sqlFireJdbcTemplate.batchUpdate(sql,
					new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps, int index)
								throws SQLException {
							// TODO Auto-generated method stub
							ps.setString(1, list.get(index).getCityId());
							ps.setString(2, list.get(index).getDataDate());
							ps.setInt(3, list.get(index).getUsedNum());
						}

						@Override
						public int getBatchSize() {
							// TODO Auto-generated method stub
							return list.size();
						}
					});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("插入地市月使用额出错！！！");
			throw e;
		}
	}
	
	

}
