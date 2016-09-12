package com.asiainfo.biapp.mcd.index.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfree.util.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.asiainfo.biapp.mcd.index.vo.MtlMcdUserInfo;

public class MtlMcdUserInfoDaoImpl extends  JdbcDaoSupport  implements MtlMcdUserInfoDao{
	@Override
	public MtlMcdUserInfo getMtlMcdUserInfo(String userId){
		MtlMcdUserInfo mtlMcdUserInfo = null;
		String sql = "select t.user_id,t.user_name,t.user_gender,t.user_type,t.user_city_id from MTL_MCD_USER_INFO t where t.user_id='"+userId+"'";
		try {
			mtlMcdUserInfo = (MtlMcdUserInfo) this.getJdbcTemplate().queryForObject(sql, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int index) throws SQLException {
					MtlMcdUserInfo mtlMcdUserInfo = new MtlMcdUserInfo();
					mtlMcdUserInfo.setUserCityId(rs.getString("user_city_id"));
					mtlMcdUserInfo.setUserName(rs.getString("user_name"));
					mtlMcdUserInfo.setUserGender(rs.getString("user_gender"));
					mtlMcdUserInfo.setUserType(rs.getString("user_type"));
					mtlMcdUserInfo.setUserId(rs.getString("user_id"));
					return mtlMcdUserInfo;
				}
			});
		} catch (DataAccessException e) {
			Log.info("", e);
			return null;
		}
		return mtlMcdUserInfo;
	}

}
