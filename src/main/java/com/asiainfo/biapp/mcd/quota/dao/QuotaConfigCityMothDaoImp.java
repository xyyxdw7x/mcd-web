package com.asiainfo.biapp.mcd.quota.dao;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;

@Repository(value="quotaConfigCityMothDao")
public class QuotaConfigCityMothDaoImp extends JdbcDaoBase implements
		QuotaConfigCityMothDao {
	private static final Logger log = LogManager.getLogger();
	private static final String TABLE = "MTL_QUOTA_CONFIG_CITY";

	@Override
	public int queryCityMonthQuotaInMem(String city)
			throws DataAccessException {
		// TODO Auto-generated method stub
		int ren = 0;
		String sql = "select MONTH_QUOTA_NUM" + " from " + TABLE + " where CITY_ID=?";
		try {
			ren = this.getJdbcTemplate().queryForObject(  
                    sql, new Object[] { city }, Integer.class);  
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("查询地市月配额出错！！！将返回0");
			return 0;
		}
		return ren;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> queryAllInMem(){
		List<Map<String,Object>> list=null;
		String sql = "select * from MTL_QUOTA_CONFIG_CITY";
		list = this.getJdbcTemplate().queryForList(sql);
		return list;
	}

}
