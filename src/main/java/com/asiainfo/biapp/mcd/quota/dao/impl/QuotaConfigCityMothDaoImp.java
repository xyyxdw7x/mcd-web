package com.asiainfo.biapp.mcd.quota.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.quota.dao.IQuotaConfigCityMothDao;

@Repository(value="quotaConfigCityMothDao")
public class QuotaConfigCityMothDaoImp extends JdbcDaoBase implements IQuotaConfigCityMothDao {
	private static final Logger log = LogManager.getLogger();
	private static final String TABLE = "mcd_quota_config_city";

	@Override
	public int queryCityMonthQuotaInMem(String city)throws DataAccessException {
		int ren = 0;
		@SuppressWarnings("rawtypes")
		List list=null;;
		String sql = "select MONTH_QUOTA_NUM" + " from " + TABLE + " where CITY_ID=?";
		try {
			list = this.getJdbcTemplate().queryForList(sql, new Object[] { city });  
			if(list!=null && list.size()>0){
				ren = Integer.parseInt(list.get(0).toString());
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

}
