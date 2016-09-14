package com.asiainfo.biapp.mcd.common.dao.channel;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.framework.jdbc.VoPropertyRowMapper;
import com.asiainfo.biapp.mcd.common.vo.channel.McdDimChannel;
@Repository("mcdDimChannelDao")
public class McdDimChannelDaoImpl extends JdbcDaoBase implements McdDimChannelDao{
	
	@Override
	public List<McdDimChannel> getAll()  {
		String sql = "select channel_id,channel_name,display_order from mcd_dim_channel d order by d.display_order";
		List<McdDimChannel> list=this.getJdbcTemplate().query(sql, new VoPropertyRowMapper<McdDimChannel>(McdDimChannel.class));
		return list;
	} 
	
}
