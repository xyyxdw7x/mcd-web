package com.asiainfo.biapp.mcd.tactics.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.framework.jdbc.VoPropertyRowMapper;
import com.asiainfo.biapp.mcd.tactics.dao.IMcdPlanChannelListDao;
import com.asiainfo.biapp.mcd.tactics.vo.McdPlanChannelList;
@Repository("mcdPlanChannelListDao")
public class McdPlanChannelListDaoImpl extends JdbcDaoBase implements IMcdPlanChannelListDao{
@Override
public List<McdPlanChannelList> getChannelsByPlanId(String planId){
	StringBuffer sb = new StringBuffer();
	sb.append("SELECT * FROM mcd_plan_channel_list WHERE plan_id=?");
	List<McdPlanChannelList> list = this.getJdbcTemplate().query(sb.toString(),new Object[]{planId},new VoPropertyRowMapper<McdPlanChannelList>(McdPlanChannelList.class));
	return list;
	
}
	
}
