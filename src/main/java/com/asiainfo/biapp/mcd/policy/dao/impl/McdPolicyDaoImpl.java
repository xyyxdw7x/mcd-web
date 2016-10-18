package com.asiainfo.biapp.mcd.policy.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.framework.jdbc.VoPropertyRowMapper;
import com.asiainfo.biapp.mcd.plan.vo.McdDimPlanOnlineStatus;
import com.asiainfo.biapp.mcd.policy.dao.IMcdPolicyDao;

@Service("mcdPolicyDao")
public class McdPolicyDaoImpl extends JdbcDaoBase implements IMcdPolicyDao {
	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * 获取政策状态
	 */
	@Override
	public List<McdDimPlanOnlineStatus> initDimPolicyStatus() throws Exception {
		String sql = "select status_id, status_name from mcd_dim_plan_online_status d order by d.status_id  ";
		log.debug("-------------状态口径：" + sql + "--------------");
		List<McdDimPlanOnlineStatus> list = this.getJdbcTemplate().query(sql,
				new VoPropertyRowMapper<McdDimPlanOnlineStatus>(McdDimPlanOnlineStatus.class));
		log.debug("---------状态查询结果：" + list + "----------");
		return list;
	}

	@Override
	public Boolean updatePolicy(String sql, List<Object> params) {
		log.info("执行sql=" + sql);
		int res = 0;
		if (params == null || params.size() == 0) {
			res = this.getJdbcTemplate().update(sql);
		} else {
			res = this.getJdbcTemplate().update(sql, params.toArray());
		}
		if (res > 0) {
			return true;
		} else {
			return false;
		}
	}

}
