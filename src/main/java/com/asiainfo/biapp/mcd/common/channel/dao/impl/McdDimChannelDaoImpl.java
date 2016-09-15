
package com.asiainfo.biapp.mcd.common.channel.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.framework.jdbc.VoPropertyRowMapper;
import com.asiainfo.biapp.mcd.common.channel.dao.IMcdDimChannelDao;
import com.asiainfo.biapp.mcd.common.vo.channel.McdDimChannel;
@Repository("mcdDimChannelDao")
public class McdDimChannelDaoImpl extends JdbcDaoBase implements IMcdDimChannelDao{
	
	@Override
	public List<McdDimChannel> getAll()  {
		String sql = "select channel_id,channel_name,display_order from mcd_dim_channel d order by d.display_order";
		List<McdDimChannel> list=this.getJdbcTemplate().query(sql, new VoPropertyRowMapper<McdDimChannel>(McdDimChannel.class));
		return list;
	} 
	
	@Override
	public List<McdDimChannel> getChannelMsg(String isDoubleSelect) {
		List<McdDimChannel> list = null;
		try {
			StringBuffer sbuffer = new StringBuffer();
			if("1".equals(isDoubleSelect)){//多选时只展示3个渠道：社会渠道，营业厅、手机APP支持多选
				sbuffer.append("select CHANNEL_ID,CHANNEL_NAME from mcd_dim_channel where channel_id in('902','903','906') ORDER BY ORDER_NUM ASC");
			}else{
				sbuffer.append("select CHANNEL_ID,CHANNEL_NAME from mcd_dim_channel ORDER BY ORDER_NUM ASC");
			}
			list = this.getJdbcTemplate().query(sbuffer.toString(),new VoPropertyRowMapper<McdDimChannel>(McdDimChannel.class));			
		} catch (Exception e) {
		}
		return list;
	}
	
	@Override
	public List<McdDimChannel> initChannel(boolean isOnLine,String cityId) {
		List<McdDimChannel> list = null;
		JdbcTemplate jt = this.getJdbcTemplate();
		try {
			StringBuffer sbuffer = new StringBuffer();
			sbuffer.append("select DMC.CHANNEL_ID,DMC.CHANNEL_NAME,TEMP.NUM ")
				   .append(" from mcd_dim_channel DMC left join")
				   .append(" (select count(1) num ,mcd_camp_order.channel_id from mcd_camp_order ")
				   .append("  left join (select unique campseg_id ,exec_status from mcd_camp_task) mct on mcd_camp_order.campseg_id = mct.campseg_id")
				   .append(" left join mcd_camp_def mcs on mcd_camp_order.campseg_id=mcs.campseg_id")
				   .append(" where mcd_camp_order.city_id='"+cityId+"'")
				   .append(" and  mct.exec_status in (50,51,59)")
				   .append(" and CEIL(to_date(mcs.end_date,'yyyy-mm-dd')-sysdate) >=0")  //失效的策略不统计
				   .append(" group by  mcd_camp_order.channel_id")
				   .append(" ) temp on DMC.CHANNEL_ID = TEMP.CHANNEL_ID");
			if(isOnLine){ //线上
				   sbuffer.append(" where DMC.channel_class=1");
			}else{//线下
				   sbuffer.append(" where DMC.channel_class=2");
			}
			list = jt.query(sbuffer.toString(), new RowMapper<McdDimChannel>() {

				@Override
				public McdDimChannel mapRow(ResultSet rs, int index)throws SQLException {
					McdDimChannel cc = new McdDimChannel();
					cc.setChannelId(rs.getString("CHANNEL_ID"));
					cc.setChannelName(rs.getString("CHANNEL_NAME"));
					if(!"null".equals(String.valueOf(rs.getShort("NUM")))){
						cc.setNum(String.valueOf(rs.getShort("NUM")));
					}else{
						cc.setNum("0");
					}
					return cc;
				}
			});
			
		} catch (Exception e) {
			logger.error("",e);
		}
		return list;
	}
	

	@Override
	public List<McdDimChannel> getMtlChannelByCondition(String isDoubleSelect){
		List<McdDimChannel> list=null;
		String sql = "select * from mcd_dim_channel dmc where 1=1 ";
		if("1".equals(isDoubleSelect)){
			sql += " and dmc.channel_id in (902,903,906)";
		}
		sql += " order by dmc.DISPLAY_ORDER";
		try {
			list = this.getJdbcTemplate().query(sql,new RowMapper<McdDimChannel>(){
				@Override
				public McdDimChannel mapRow(ResultSet rs, int arg1) throws SQLException {
					McdDimChannel dimMtlChannel=new McdDimChannel();
					dimMtlChannel.setChannelId(rs.getString("CHANNEL_ID"));
					dimMtlChannel.setChannelName(rs.getString("CHANNEL_NAME"));
					dimMtlChannel.setDisplayOrder(rs.getInt("DISPLAY_ORDER"));
					return dimMtlChannel;
				}
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
}
