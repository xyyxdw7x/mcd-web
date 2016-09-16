package com.asiainfo.biapp.mcd.tactics.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.tactics.dao.IPlanChannelAdivResourceDao;
import com.asiainfo.biapp.mcd.tactics.vo.McdDimAdivInfo;
@Repository("planChannelAdivResourceDao")
public class PlanChannelAdivResourceDaoImpl extends JdbcDaoBase implements IPlanChannelAdivResourceDao{

	@Override
	public List<McdDimAdivInfo> getAdivByPlanChannel(String planId,String channelId){
		List<Map<String, Object>> list = null;
		List<McdDimAdivInfo> resultList = new ArrayList<McdDimAdivInfo>();
		try {
			StringBuffer sbuffer = new StringBuffer();
		
			sbuffer.append("select mcd_plan_channel_list.*,mcd_plan_resource_list.adiv_id,mcd_plan_resource_list.chn_resource_id,mcd_plan_resource_list.chn_resource_desc," +
					"mcd_dim_adiv_resouce.adiv_resource_name,mcd_dim_adiv_resouce.adiv_content_url,mcd_dim_adiv_resouce.adiv_content_to_url,mcd_dim_adiv_info.* from mcd_plan_channel_list ")
				   .append(" left join mcd_plan_resource_list on mcd_plan_channel_list.plan_id=mcd_plan_resource_list.plan_id")
				   .append(" left join mcd_dim_adiv_info on mcd_plan_resource_list.adiv_id = mcd_dim_adiv_info.adiv_id")
				   .append(" left join mcd_dim_adiv_resouce on mcd_plan_resource_list.chn_resource_id = mcd_dim_adiv_resouce.adiv_resource_id")
				   .append(" where mcd_plan_channel_list.plan_id=? and mcd_plan_channel_list.channel_id=?")
				   .append(" and mcd_plan_channel_list.channel_id = mcd_dim_adiv_info.channel_id")
				   .append(" and ((PLAN_CHN_STARTDATE is null and PLAN_CHN_ENDDATE is null) ")
				   .append(" or SYSDATE BETWEEN nvl2(PLAN_CHN_STARTDATE,PLAN_CHN_STARTDATE,TO_DATE('19000101', 'YYYYMMDD')) AND")
				   .append(" nvl2(PLAN_CHN_ENDDATE, PLAN_CHN_ENDDATE, TO_DATE('21000101', 'YYYYMMDD')))");
			list = this.getJdbcTemplate().queryForList(sbuffer.toString(), new String[]{planId,channelId});
			for (Map map : list) {
				McdDimAdivInfo temp = new McdDimAdivInfo();
				temp.setAdivId((String) map.get("adiv_id"));
				temp.setAdivName((String) map.get("adiv_name"));
				temp.setAdivSize((String) map.get("adiv_size"));
				temp.setAdivDesc((String) map.get("adiv_desc"));
				temp.setChannelId((String) map.get("channel_id"));
				temp.setAdivBgPicUrl((String) map.get("adiv_bg_pic_url"));		
				temp.setChnResourceId((String) map.get("chn_resource_id"));
				temp.setChnResourceDesc((String) map.get("chn_resource_desc"));
				temp.setAdivResourceName((String) map.get("adiv_resource_name"));
				temp.setAdivContentURL((String) map.get("adiv_content_url"));
				temp.setAdivContentToURL((String) map.get("adiv_content_to_url"));
				resultList.add(temp);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		return resultList;
	}
}
