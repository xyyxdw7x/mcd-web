package com.asiainfo.biapp.mcd.effectappraisal.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jfree.util.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.jms.util.SpringContext;
import com.asiainfo.biapp.mcd.effectappraisal.vo.DimMtlAdivInfo;
import com.asiainfo.biapp.mcd.effectappraisal.vo.MtlGroupAttrRel;
import com.asiainfo.biapp.mcd.effectappraisal.vo.RuleTimeTermLable;

/**
 * 
 * Title: 
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-7-21 下午02:02:28
 * @version 1.0
 */
@Repository(value="mtlGroupAttrRelDao")
public class MtlGroupAttrRelDaoImpl extends JdbcDaoBase implements IMtlGroupAttrRelDao {

	@Override
	public List<MtlGroupAttrRel> initTermLable(String custGroupId) {
		List<Map> list = null;
		List<MtlGroupAttrRel> mtlGroupAttrRelList = new ArrayList<MtlGroupAttrRel>();
		try {
			StringBuffer sbuffer = new StringBuffer();
			sbuffer.append("SELECT  * FROM mcd_custgroup_attr_list WHERE CUSTOM_GROUP_ID=?")
				   .append(" and list_table_name=(select max(list_table_name) from mcd_custgroup_attr_list WHERE CUSTOM_GROUP_ID=?)");
			list = this.getJdbcTemplate().queryForList(sbuffer.toString(), new String[]{custGroupId,custGroupId}, Map.class);
			for (Map map : list) {
				MtlGroupAttrRel mtlGroupAttrRel = new MtlGroupAttrRel();
				mtlGroupAttrRel.setAttrCol((String) map.get("ATTR_COL"));
				mtlGroupAttrRel.setAttrColName((String) map.get("ATTR_COL_NAME"));
				mtlGroupAttrRel.setAttrColLength((String) map.get("ATTR_COL_LENGTH"));
				mtlGroupAttrRel.setAttrColType((String) map.get("ATTR_COL_TYPE"));
				mtlGroupAttrRel.setCustomGroupId((String) map.get("CUSTOM_GROUP_ID"));
				mtlGroupAttrRel.setCustomSourceId((String) map.get("CUSTOM_SOURCE_ID"));
				mtlGroupAttrRel.setListTableName((String) map.get("LIST_TABLE_NAME"));
				mtlGroupAttrRelList.add(mtlGroupAttrRel);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		return mtlGroupAttrRelList;
	}
	
	@Override
	public List<DimMtlAdivInfo> initDimMtlAdivInfo(String channelId,String planId) {
		List<Map> list = null;
		List<DimMtlAdivInfo> resultList = new ArrayList<DimMtlAdivInfo>();
		try {
			StringBuffer sbuffer = new StringBuffer();
			/**
			 * select mcd_plan_channel_list.*,mtl_stc_plan_resource.adiv_id,dim_mtl_adiv_info.* from mcd_plan_channel_list 
				left join mtl_stc_plan_resource on mcd_plan_channel_list.plan_id=mtl_stc_plan_resource.plan_id
				left join dim_mtl_adiv_info on mtl_stc_plan_resource.adiv_id = dim_mtl_adiv_info.adiv_id
				--left join dim_mtl_adiv_resouce on mtl_stc_plan_resource.chn_resource_id=dim_mtl_adiv_resouce.adiv_resource_id
				where mcd_plan_channel_list.plan_id in ('600000274748','600000282454','600000282453','600000298809','600000298808')
				and ((PLAN_CHN_STARTDATE is null and PLAN_CHN_ENDDATE is null) 
				or SYSDATE BETWEEN nvl2(PLAN_CHN_STARTDATE,PLAN_CHN_STARTDATE,TO_DATE('19000101', 'YYYYMMDD')) AND
                       nvl2(PLAN_CHN_STARTDATE, PLAN_CHN_STARTDATE, TO_DATE('21000101', 'YYYYMMDD')))
			 */
			sbuffer.append("select mcd_plan_channel_list.*,mtl_stc_plan_resource.adiv_id,mtl_stc_plan_resource.chn_resource_id,mtl_stc_plan_resource.chn_resource_desc," +
					"dim_mtl_adiv_resouce.adiv_resource_name,dim_mtl_adiv_resouce.adiv_content_url,dim_mtl_adiv_resouce.adiv_content_to_url,dim_mtl_adiv_info.* from mcd_plan_channel_list ")
				   .append(" left join mtl_stc_plan_resource on mcd_plan_channel_list.plan_id=mtl_stc_plan_resource.plan_id")
				   .append(" left join dim_mtl_adiv_info on mtl_stc_plan_resource.adiv_id = dim_mtl_adiv_info.adiv_id")
				   .append(" left join dim_mtl_adiv_resouce on mtl_stc_plan_resource.chn_resource_id = dim_mtl_adiv_resouce.adiv_resource_id")
				   .append(" where mcd_plan_channel_list.plan_id=? and mcd_plan_channel_list.channel_id=?")
				   .append(" and mcd_plan_channel_list.channel_id = dim_mtl_adiv_info.channel_id")
				   .append(" and ((PLAN_CHN_STARTDATE is null and PLAN_CHN_ENDDATE is null) ")
				   .append(" or SYSDATE BETWEEN nvl2(PLAN_CHN_STARTDATE,PLAN_CHN_STARTDATE,TO_DATE('19000101', 'YYYYMMDD')) AND")
				   .append(" nvl2(PLAN_CHN_ENDDATE, PLAN_CHN_ENDDATE, TO_DATE('21000101', 'YYYYMMDD')))");
//			sbuffer.append("SELECT  * FROM dim_mtl_adiv_info WHERE channel_id=?");
			list = this.getJdbcTemplate().queryForList(sbuffer.toString(), new String[]{planId,channelId}, Map.class);
			for (Map map : list) {
				DimMtlAdivInfo temp = new DimMtlAdivInfo();
				temp.setAdivId((String) map.get("adiv_id"));
				temp.setAdivName((String) map.get("adiv_name"));
				temp.setAdivSize((String) map.get("adiv_size"));
				temp.setAdivDesc((String) map.get("adiv_desc"));
				temp.setChannelId((String) map.get("channel_id"));
				temp.setAdivBgPicUrl((String) map.get("adiv_bg_pic_url"));		
				temp.setChnResourceId((String) map.get("chn_resource_id"));
				temp.setChnResourceDesc((String) map.get("chn_resource_desc"));
//				temp.setAdivName((String) map.get("adiv_resource_name"));
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
	
	@Override
	public List<DimMtlAdivInfo> getAdivInfo(String adivids){
		List<Map> list = null;
		List<DimMtlAdivInfo> resultList = new ArrayList<DimMtlAdivInfo>();
		try {
			StringBuffer sbuffer = new StringBuffer();
			sbuffer.append("SELECT  * FROM dim_mtl_adiv_info WHERE adiv_id in("+adivids+")");
			list = this.getJdbcTemplate().queryForList(sbuffer.toString(), Map.class);
			for (Map map : list) {
				DimMtlAdivInfo temp = new DimMtlAdivInfo();
				temp.setAdivId((String) map.get("adiv_id"));
				temp.setAdivName((String) map.get("adiv_name"));
				temp.setAdivSize((String) map.get("adiv_size"));
				temp.setAdivDesc((String) map.get("adiv_desc"));
				temp.setChannelId((String) map.get("channel_id"));
				temp.setAdivBgPicUrl((String) map.get("adiv_bg_pic_url"));		
//				temp.setChnResourceId((String) map.get("chn_resource_id"));
//				temp.setChnResourceDesc((String) map.get("chn_resource_desc"));
////				temp.setAdivName((String) map.get("adiv_resource_name"));
//				temp.setAdivResourceName((String) map.get("adiv_resource_name"));
//				temp.setAdivContentURL((String) map.get("adiv_content_url"));
//				temp.setAdivContentToURL((String) map.get("adiv_content_to_url"));
				resultList.add(temp);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		return resultList;
	}

	@Override
	public List<RuleTimeTermLable> initRuleTimeTermLable() {
		List<Map> list = null;
		List<RuleTimeTermLable> resultList = new ArrayList<RuleTimeTermLable>();
		
		JdbcTemplate jt = this.getJdbcTemplate();
		StringBuffer sbuffer = new StringBuffer();
//		sbuffer.append("select a.cat_id,b.cat_name,a.function_id,c.function_name_desc from dim_function_map_zj a ")
//			   .append(" left join dim_epl_category_zj b on a.cat_id = b.cat_id")
//			   .append(" left join cep_streams_function c on a.function_id = c.id");
		sbuffer.append("select a.cat_id,b.cat_name,a.function_id,a.function_name function_name_desc,a.function_type from dim_function_map_zj a ")
			   .append(" left join dim_epl_category_zj b on a.cat_id = b.cat_id order by a.function_type");
		list = jt.queryForList(sbuffer.toString(), Map.class);
		for (Map map : list) {
			RuleTimeTermLable temp = new RuleTimeTermLable();
			temp.setCatId((String) map.get("cat_id"));
			temp.setCatName((String) map.get("cat_name"));
			temp.setFunctionId((String) map.get("function_id"));
			temp.setFunctionNameDesc((String) map.get("function_name_desc"));
			temp.setFunctionType((String) map.get("function_type"));
			resultList.add(temp);
		}
		return resultList;
	}
	
	@Override
	public List<RuleTimeTermLable> getFunctionNameById(String functionId){
		List<Map> list = null;
		List<RuleTimeTermLable> resultList = new ArrayList<RuleTimeTermLable>();
		JdbcTemplate jt = this.getJdbcTemplate();
		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append("select * from dim_function_map_zj where function_id=? ");
		list = jt.queryForList(sbuffer.toString(),new Object[]{functionId}, Map.class);
		for (Map map : list) {
			RuleTimeTermLable temp = new RuleTimeTermLable();
			temp.setFunctionId((String) map.get("function_id"));
			temp.setFunctionNameDesc((String) map.get("function_name"));
			resultList.add(temp);
		}
		return resultList;
	}
	
	@Override
	public List<RuleTimeTermLable> initRuleTimeTermSonLable(String sceneId){
		List<Map> list = null;
		List<RuleTimeTermLable> resultList = new ArrayList<RuleTimeTermLable>();
		JdbcTemplate jt = this.getJdbcTemplate();
		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append("select b.param_id,b.param_name from MTL_CEP_PARAM_MAP a left join DIM_MTL_CEP_PARAM b on a.param_id = b.param_id where scene_id=? ");
		list = jt.queryForList(sbuffer.toString(),new String[]{sceneId}, Map.class);
		for (Map map : list) {
			RuleTimeTermLable temp = new RuleTimeTermLable();
			temp.setParamId((String) map.get("param_id"));
			temp.setParamName((String) map.get("param_name"));
			resultList.add(temp);
		}
		return resultList;
	}

	@Override
	public List initAdivInfoByChannelId(String cityId) {
		JdbcTemplate jt = this.getJdbcTemplate();
		StringBuffer sb = new StringBuffer();
		sb.append("select dmai.*,num,case when num is null then 0 else num end sortNum from dim_mtl_adiv_info dmai left join (")
		  .append(" select count(1) num ,mcd_camp_order.channel_id,mcd_camp_order.Chn_Adiv_Id ")
		  .append(" from mcd_camp_order  ")
		  .append(" left join (select unique campseg_id ,exec_status from mcd_camp_task) mct on mcd_camp_order.campseg_id = mct.campseg_id")
		  .append(" left join mcd_camp_def mcs on mcd_camp_order.campseg_id=mcs.campseg_id")
		  .append(" where mcd_camp_order.city_id='"+cityId+"' ")
		  .append(" and  mct.exec_status in (50,51,59) ")
		  .append(" and CEIL(to_date(mcs.end_date,'yyyy-mm-dd')-sysdate) >=0")
		  .append(" group by  mcd_camp_order.channel_id,mcd_camp_order.Chn_Adiv_Id ")
		  .append(" ) basic on basic.channel_id=dmai.channel_id and basic.Chn_Adiv_Id=dmai.adiv_id")
		  .append(" where dmai.channel_id <> '910'")
		  .append(" order by sortNum desc");
		
		Log.info("根据渠道id查询运营位信息："+sb.toString());
		List list  = jt.queryForList(sb.toString());
		return list;
	}
}
