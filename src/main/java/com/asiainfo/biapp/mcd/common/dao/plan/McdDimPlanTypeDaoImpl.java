package com.asiainfo.biapp.mcd.common.dao.plan;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.framework.jdbc.VoPropertyRowMapper;
import com.asiainfo.biapp.mcd.common.vo.plan.McdDimPlanType;
@Repository("mcdDimPlanTypeDao")
public class McdDimPlanTypeDaoImpl extends JdbcDaoBase implements McdDimPlanTypeDao{
	@Override
	public List<McdDimPlanType> getChildrens(String pid){
		String sql = "select TYPE_ID,TYPE_NAME,TYPE_PID from MCD_DIM_PLAN_TYPE where TYPE_PID='"+pid+"'";
		List<McdDimPlanType> subTypes=this.getJdbcTemplate().query(sql, new VoPropertyRowMapper<McdDimPlanType>(McdDimPlanType.class));
		return subTypes;
	}
	
	@Override
	public List<McdDimPlanType> getAll(){
		String sql = "select TYPE_ID,TYPE_NAME,TYPE_PID from MCD_DIM_PLAN_TYPE2";
		List<McdDimPlanType> subTypes=this.getJdbcTemplate().query(sql, new VoPropertyRowMapper<McdDimPlanType>(McdDimPlanType.class));
		return subTypes;
	}

}
