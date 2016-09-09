package com.asiainfo.biapp.mcd.bull.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;

@Repository(value="sendType4CitysDao")
public class SendType4CitysDaoImp extends JdbcDaoBase implements SendType4CitysDao{
	private static final Logger log = LogManager.getLogger();
	@Override
	public void updateType(String cityId,String sendType){
		String sql = "update mcd_sms_send_city_config t set t.send_type=? where city_id=?";
		String[] parm ={sendType,cityId};
		try {
			log.info("执行sql=" + sql);
			this.getJdbcTemplate().update(sql, parm);
		} catch (DataAccessException e) {
			log.error("更新失败！！！");
			throw e;
		}
	}
	@Override
	public int getCitySendType(String city){
		String sql="select send_type from mcd_sms_send_city_config where city_id=?";
		String[] parm = {city};
		int type = 0;
		try {
			log.info("执行sql=" + sql);
			type = this.getJdbcTemplate().queryForObject(sql,parm, Integer.class);
		} catch (DataAccessException e) {
			log.error("根据科室查询发送方式失败！！！");
			return 0;
		}
		return type;
	}
	@Override
	public void add(String cityId, int sendType) {
		String sql="insert into mcd_sms_send_city_config(city_id,send_type)values(?,?)";
		Object[] parms={cityId,sendType};
		log.info("执行sql=" + sql);
		this.getJdbcTemplate().update(sql, parms);
	}

}
