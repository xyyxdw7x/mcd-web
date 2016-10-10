package com.asiainfo.biapp.mcd.quota.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.quota.dao.IQuotaConfigCityDayDao;
import com.asiainfo.biapp.mcd.quota.util.QuotaUtils;
import com.asiainfo.biapp.mcd.quota.vo.QuotaConfigCityDay;

@Repository(value="quotaConfigCityDayDao")
public class QuotaConfigCityDayDaoImp extends JdbcDaoBase implements IQuotaConfigCityDayDao {

	private static final Logger log = LogManager.getLogger();
	
	@Override
	public int queryCityDayQuotaInMem(String cityId,String dataDate){
		int num=0;
		String sql = "select DAY_QUOTA_NUM from mcd_quota_config_city_D where CITY_ID=? and DATA_DATE=?";
		String[] parms = {cityId,dataDate};
		try {
			num = this.getJdbcTemplate().queryForObject(  
                    sql, new Object[] { parms }, Integer.class);  
		} catch (DataAccessException e) {
			log.error("",e);
		}
		return num;
	}
	
	@Override
	public List<Map<String, Object>> queryCityDayQuotasInMem(String cityId,String monthDate) {
		//String sql = "select DATA_DATE, DAY_QUOTA_NUM, ALTERED from mcd_quota_config_city_D where CITY_ID = ? and DATA_DATE_M = ?";
	    StringBuffer sql = new StringBuffer("select a.DATA_DATE, DAY_QUOTA_NUM, ALTERED,");
	    sql.append(" nvl(DAY_QUOTA_NUM - b.used_num,DAY_QUOTA_NUM) as surplus ");
	    sql.append(" from mcd_quota_config_city_D a ");
	    sql.append(" left join mcd_quota_used_city_d b ");
	    sql.append(" on a.city_id = b.city_id and a.data_date = b.data_date ");
	    sql.append(" where a.CITY_ID =  ? and a.data_date_m = ? ");
		try {
			return this.getJdbcTemplate().queryForList(sql.toString(), new Object[] { cityId, monthDate });
		} catch (DataAccessException e) {
			log.error("", e);
		}
		return new ArrayList<Map<String, Object>>();
	}

	@Override
	public Map<String,Object> getCityStatisDayInMem(String cityId,String dataDate){
		
		Map<String,Object> map = null;
		String sql = "select dayC.*,dayU.Used_Num from mcd_quota_config_city_D dayC LEFT OUTER JOIN mcd_quota_used_city_d dayU " +
				"on dayC.City_Id=dayU.City_Id and dayC.Data_Date=dayU.Data_Date " +
				"where dayC.City_Id=? and dayC.Data_Date=?";
		Object[] parms = {cityId,dataDate};
		
		try {
			map = this.getJdbcTemplate().queryForMap(sql, parms);
		} catch (DataAccessException e) {
			log.error("","访问数据库异常或者未查询到数据，返回null！");
		}
		return map;
		
	}
	@Override
	public void addBatchAddUsedNumInMem(final List<QuotaConfigCityDay> list){
		 String sql="insert into mcd_quota_used_city_d(CITY_ID,DATA_DATE,USED_NUM)values(?,?,?)";
		 this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				ps.setString(1, list.get(index).getCityId());
				ps.setString(2, list.get(index).getDataDate());
				ps.setInt(3, list.get(index).getUsedNum());
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
		
	}
	@Override
	public List<Map<String,Object>> getCityUsedListInMem(String dataDate){
		List<Map<String,Object>> list = null;
		String sql="select c.CITY_ID,t.USED_NUM,t.DATA_DATE from mcd_quota_config_city c LEFT OUTER JOIN (select u.USED_NUM,u.DATA_DATE from mcd_quota_used_city_d u where u.DATA_DATE=?)t on c.CITY_ID=t.CITY_ID";
		Object[] parm={dataDate};
		try {
			list = this.getJdbcTemplate().queryForList(sql, parm);
		} catch (DataAccessException e) {
           log.debug("","获取数据异常或者获得的结果为空，返回null");
		}
		return list;
	}
	@Override
	public void addBatchAddCitysDayQuotaInMem(final List<QuotaConfigCityDay> list){
		String currentMonth = QuotaUtils.getDayMonth("yyyyMM");
		String delSql ="delete from mcd_quota_config_city_D where DATA_DATE_M='"+currentMonth+"'";
		this.getJdbcTemplate().update(delSql);//插入地市日配额之前首先删本月的所有地市的日配额
		
		String sql = "insert into mcd_quota_config_city_D(CITY_ID,DATA_DATE,DATA_DATE_M,DAY_QUOTA_NUM)values(?,?,?,?)";
		
		this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				ps.setString(1, list.get(index).getCityId());
				ps.setString(2, list.get(index).getDataDate());
				ps.setString(3, list.get(index).getDataDateM());
				ps.setInt(4, list.get(index).getDayQuotaNum());
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
	}

	/**
	 * 保存或更新单日地市配额
	 */
	@Override
	public int saveOrUpdateCityDayQyotaInMem(String cityId, String date, String quota,String quotaM) throws Exception {
		String selSql = "select count(*) from mcd_quota_config_city_D where CITY_ID = ? and DATA_DATE = ?";
		//修改日配额后 后面的日配额=月配额-已经过去的天数的配额-当日配额
		long count = this.getJdbcTemplate().queryForObject(selSql, new Object[] { cityId, date }, Long.class);
		if (count == 0) {//db中没有配置时插入
			String insSql = "insert into mcd_quota_config_city_D (DATA_DATE_M, DATA_DATE, CITY_ID, DAY_QUOTA_NUM) values (?, ?, ?, ?)";
			return this.getJdbcTemplate().update(insSql, new Object[] { QuotaUtils.getDayMonth("yyyyMM"), date, cityId, quota});
		} else {
			String updateSql = "update mcd_quota_config_city_D t set t.DAY_QUOTA_NUM = ?, t.ALTERED = ? where t.CITY_ID = ? and t.DATA_DATE = ?";
			String alteredFlag = "1";
			return this.getJdbcTemplate().update(updateSql, new Object[] { quota, alteredFlag, cityId, date});
		}
	}
	
	@Override
	public void delCityDayQuota4Month(String month){
		String sql = "delete  from mcd_quota_config_city_D where DATA_DATE_M = ?";
		Object[] param ={month};
		this.getJdbcTemplate().update(sql,param);
	}
	
	/**
	 * 获得今天之前的所有日配额之和
	 * @param cityid
	 * @param date
	 * @param quota
	 * @param quotaM
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getNowBeforeSumInMem(String cityid, String date, String quota,String quotaM) throws Exception{
		log.info("getNowBeforeSum cityid="+cityid+" date="+date);
		String sql="select sum(t.day_quota_num) from mcd_quota_config_city_D t where city_id=? and data_date_m=? and data_date<?";
		int sum = this.getJdbcTemplate().queryForObject(  
				sql, new Object[]{cityid,date.substring(0, 6),date}, Integer.class);
		log.info("sum="+sum);
		return sum;
	}
	
	/**
	 * 获得今天之前的所有日配额之和
	 * @param cityid
	 * @param date
	 * @param quota
	 * @param quotaM
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String,Object>> getNowAfterDataListInMem(String cityid, String date, String quota) throws Exception{
		log.info("getNowBeforeSum cityid="+cityid+" date="+date);
		String sql="select data_date from mcd_quota_config_city_D t where city_id=? and data_date_m=? and data_date>?";
		List<Map<String,Object>> list=this.getJdbcTemplate().queryForList(sql, new Object[]{cityid,date.substring(0, 6),date});
		log.info("list size="+list.size());
		return list;
	}

	@Override
	public int updateNowAfterDataInMem(final String cityid, final String date, final String quota,final String endQuota,final List<Map<String,Object>> list) throws Exception {
		log.info("updateNowAfterData cityid="+cityid+" date="+date+" quota="+quota+" endQuota="+endQuota);
		String updateSql = "update mcd_quota_config_city_D t set t.DAY_QUOTA_NUM = ?, t.ALTERED = ? where t.CITY_ID = ? and t.DATA_DATE = ? ";
		final String alteredFlag = "1";
		int[] suc=this.getJdbcTemplate().batchUpdate(updateSql, new BatchPreparedStatementSetter(){

			@Override
			public int getBatchSize() {
				return list.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				if(i<list.size()-1){
					ps.setInt(1, Integer.parseInt(quota));
				}else{
					ps.setInt(1, Integer.parseInt(endQuota));
				}
				ps.setString(2, alteredFlag);
				ps.setString(3, cityid);
				ps.setString(4, list.get(i).get("DATA_DATE").toString());
			}
		});
		return suc.length;
	}
    /**
     * 获取本月地址配置的配额之和
     * @param cityId
     * @param quotaM
     * @return
     */
    @Override
    public int getCityMonthQuotaSumInMem(String cityId, String month) {
        String sql="select sum(t.day_quota_num) from mcd_quota_config_city_D t where city_id=? and data_date_m=?";
        int sum = this.getJdbcTemplate().queryForObject(  
                sql, new Object[]{cityId,month}, Integer.class);  
        log.info("sum="+sum);
        return sum;
    }
    /**
     * 查询本日之前每天剩余配额之和
     * @param cityId
     * @param quotaM
     * @param nowDate
     * @return
     */
    @Override
    public int getSurplusQuotaSumInMem(String cityId, String month, String nowDate) {
        StringBuffer sql = new StringBuffer("select ");
        sql.append(" sum(nvl(DAY_QUOTA_NUM - b.used_num,DAY_QUOTA_NUM)) as surplusSum ");
        sql.append(" from mcd_quota_config_city_D a ");
        sql.append(" left join mcd_quota_used_city_d b ");
        sql.append(" on a.city_id = b.city_id and a.data_date = b.data_date ");
        sql.append(" where a.CITY_ID =  ? and a.data_date_m = ? and a.data_date < ?");
        int sum = this.getJdbcTemplate().queryForObject(  
        		sql.toString(), new Object[]{cityId,month,nowDate}, Integer.class);  
        log.info("sum="+sum);
        return sum;
    }
	
	
}
