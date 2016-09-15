package com.asiainfo.biapp.mcd.effectappraisal.dao.impl;


import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.common.util.DataBaseAdapter;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.effectappraisal.dao.ICampsegPriorityDao;
import com.asiainfo.biapp.mcd.effectappraisal.vo.CampsegPriorityBean;

/**
 * 
 * Title: 
 * Description:
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2016-3-17 上午10:13:59
 * @version 1.0
 */
@Repository(value="campsegPriorityDao")
public class CampsegPriorityDaoImpl extends JdbcDaoBase implements ICampsegPriorityDao{
	private static Logger log = LogManager.getLogger();
	
	/*
	 * 手动优先级
	 * (non-Javadoc)
	 * @see com.asiainfo.biapp.mcd.dao.IcampsegPriorityDao#initManualPriorityCampseg(java.lang.String, java.lang.String, boolean)
	 */
	public List<CampsegPriorityBean> initManualPriorityCampseg(String channelId,String adivId,String cityId){
		List<CampsegPriorityBean> listResult = new ArrayList<CampsegPriorityBean>();
		StringBuffer sb = new StringBuffer();
		long startTime = new Date().getTime();
		sb.append("select * from (")	
		  .append(" select mcoa.campseg_id,mcoa.pri_order_num,mcoa.city_id,mcoa.channel_id,mcoa.chn_adiv_id, mcs.campseg_name,msp.plan_id,msp.plan_name,mgi.custom_num,")
		  .append(" CEIL(to_date(mcs.end_date,'yyyy-mm-dd')-sysdate) ineffectieDays,meicd.camp_succ_rate ")
		  .append(" from mcd_camp_order mcoa")
		  .append(" left join mcd_camp_def mcs on mcoa.campseg_id=mcs.campseg_id")
		  .append(" left join mcd_plan_def msp on mcs.plan_id=msp.plan_id")
		  .append(" left join mcd_camp_custgroup_list mcc on mcc.campseg_id=mcs.campseg_id")
		  .append(" left join mcd_custgroup_def mgi on mcc.custgroup_id = mgi.custom_group_id")
		  .append(" left join (select unique campseg_id ,exec_status from mcd_camp_task) mct on mcoa.campseg_id = mct.campseg_id")
//		  .append(" left join (select * from mtl_campseg_sort  where stat_date = (select max(stat_date) from mtl_campseg_sort)) meicd on mcoa.campseg_id=meicd.campseg_id and mcoa.channel_id=meicd.channel_id and mcoa.city_id=meicd.city_id and meicd.CAMPSEG_TYPE=0")
		  .append(" left join (")
		  .append(" select campseg.city_id,campseg.campseg_id,case when nvl(sum(mcs.camp_user_num_total),0)=0 then 0")
		  .append(" else round(sum(mcs.camp_succ_num_total)/sum(mcs.camp_user_num_total),4)  end camp_succ_rate")
		  .append(" from mcd_camp_def campseg left join (select campseg_id,camp_user_num_total,camp_succ_num_total")
		  .append(" from mtl_campseg_sort where stat_date = (select max(stat_date) from mtl_campseg_sort) and CAMPSEG_TYPE=0) mcs on mcs.campseg_id= campseg.campseg_id ")
		  .append(" group by campseg.city_id,campseg.campseg_id")
		  .append(" ) meicd on mcoa.campseg_id=meicd.campseg_id and mcoa.city_id=meicd.city_id ")
		  .append(" where mcoa.is_manual=1  and mct.exec_status in (50,51,59)")
		  .append(" and CEIL(to_date(mcs.end_date,'yyyy-mm-dd')-sysdate) >=0");  //失效的策略不显示
//		  .append(" where mcc.custgroup_type='CG' and mcoa.is_manual=1 ");
		if(StringUtils.isNotEmpty(channelId)){
			sb.append(" and mcoa.channel_id='"+channelId+"'");
		}
		if(StringUtils.isNotEmpty(cityId)){
			sb.append(" and mcoa.city_id='"+cityId+"'");
		}
		if(StringUtils.isNotEmpty(adivId)){
			sb.append(" and mcoa.chn_adiv_id='"+adivId+"'");
		}
		sb.append(" order by mcoa.pri_order_num")
//		  .append(" ) where rownum<=10 "); 
		  .append(" ) "); //去掉只查询10条的限制
		log.info("查询手动优先级策略"+sb.toString());
		List<Map<String, Object>> list  = this.getJdbcTemplate().queryForList(sb.toString());
		for (Map<String, Object> map : list) {
			CampsegPriorityBean campsegPriorityBean = new CampsegPriorityBean();
			campsegPriorityBean.setCampsegId((String) map.get("campseg_id"));
			campsegPriorityBean.setPriOrderNum(Integer.parseInt(String.valueOf(map.get("pri_order_num"))));
			campsegPriorityBean.setCampsegName((String) map.get("campseg_name")+"_"+(String) map.get("plan_name"));
			campsegPriorityBean.setPlanId((String) map.get("plan_id"));
			campsegPriorityBean.setPlanName((String) map.get("plan_name"));
			campsegPriorityBean.setCustgroupNumber(0);
			campsegPriorityBean.setCityId((String) map.get("city_id"));
			campsegPriorityBean.setChannelId((String) map.get("channel_id"));
			campsegPriorityBean.setChnAdivId((String) map.get("chn_adiv_id"));
			
			String campSuccRate = String.valueOf(map.get("camp_succ_rate"));
			float successRate = 0f;
			if(StringUtils.isEmpty(campSuccRate) || "null".equals(campSuccRate)){
				campsegPriorityBean.setSuccRate(0.0000f);
			}else{
				successRate = Float.parseFloat(campSuccRate);
				java.text.DecimalFormat df = new java.text.DecimalFormat("#.####");   
				df.format(successRate); 
				campsegPriorityBean.setSuccRate(Float.parseFloat(df.format(successRate)));
			}
			
			if(null != map.get("custom_num")){
				campsegPriorityBean.setCustgroupNumber(Integer.parseInt(String.valueOf(map.get("custom_num"))));
			}
			campsegPriorityBean.setIneffectieDays(0);
			if(null != map.get("ineffectieDays")){
				campsegPriorityBean.setIneffectieDays(Integer.parseInt(String.valueOf(map.get("ineffectieDays"))));
			}
			listResult.add(campsegPriorityBean);
		}
		long endTime = new Date().getTime();
		log.info("*********************select result cost time ="+(endTime-startTime));
		return listResult;
	}

	
	@Override
	public List<CampsegPriorityBean> initAutoPriorityCampseg(String channelId,String adivId,
			String cityId,String keyWords,Pager pager) {
		List<CampsegPriorityBean> listResult = new ArrayList<CampsegPriorityBean>();
		StringBuffer sb = new StringBuffer();
		sb.append("select * from(")
		  .append(" select mcoa.campseg_id,mcoa.pri_order_num, mcoa.chn_adiv_id,mcs.campseg_name,msp.plan_id,msp.plan_name,mgi.custom_num,")
		  .append(" CEIL(to_date(mcs.end_date,'yyyy-mm-dd')-sysdate) ineffectieDays,")
		  .append(" CEIL(sysdate-TO_DATE(to_char(mcs.create_time,'yyyy-mm-dd'), 'YYYY-MM-DD ')) IsNewDays,meicd.camp_succ_rate")
		  .append(" from mcd_camp_order mcoa")
		  .append(" left join mcd_camp_def mcs on mcoa.campseg_id=mcs.campseg_id")
		  .append(" left join mcd_plan_def msp on mcs.plan_id=msp.plan_id")
		  .append(" left join mcd_camp_custgroup_list mcc on mcc.campseg_id=mcs.campseg_id")
		  .append(" left join mcd_custgroup_def mgi on mcc.custgroup_id = mgi.custom_group_id")
		  .append(" left join (select unique campseg_id ,exec_status from mcd_camp_task) mct on mcoa.campseg_id = mct.campseg_id")
		  .append(" left join (")
		  .append(" select campseg.city_id,campseg.campseg_id,case when nvl(sum(mcs.camp_user_num_total),0)=0 then 0")
		  .append(" else round(sum(mcs.camp_succ_num_total)/sum(mcs.camp_user_num_total),4)  end camp_succ_rate")
		  .append(" from mcd_camp_def campseg left join (select campseg_id,camp_user_num_total,camp_succ_num_total")
		  .append(" from mtl_campseg_sort where stat_date = (select max(stat_date) from mtl_campseg_sort) and CAMPSEG_TYPE=0) mcs on mcs.campseg_id= campseg.campseg_id ")
		  .append(" group by campseg.city_id,campseg.campseg_id")
		  .append(" ) meicd on mcoa.campseg_id=meicd.campseg_id and mcoa.city_id=meicd.city_id ")
		  //		  .append(" where mcc.custgroup_type='CG' and mcoa.is_manual=0 AND (MCS.CAMPSEG_STAT_ID="+MpmCONST.MPM_CAMPSEG_STAT_DDCG+" OR MCS.CAMPSEG_STAT_ID="+MpmCONST.MPM_CAMPSEG_STAT_DDZX+" OR MCS.CAMPSEG_STAT_ID="+MpmCONST.MPM_CAMPSEG_STAT_PAUSE+")");
		  .append(" where mcoa.is_manual=0  and mct.exec_status in (50,51,59)")
		  .append(" and CEIL(to_date(mcs.end_date,'yyyy-mm-dd')-sysdate) >=0");  //失效的策略不显示
//		  .append(" where mcc.custgroup_type='CG' and mcoa.is_manual=0");
		if(StringUtils.isNotEmpty(channelId)){
			sb.append(" and mcoa.channel_id='"+channelId+"'");
		}
		if(StringUtils.isNotEmpty(cityId)){
			sb.append(" and mcoa.city_id='"+cityId+"'");
		}
		if(StringUtils.isNotEmpty(adivId)){
			sb.append(" and mcoa.chn_adiv_id='"+adivId+"'");
		}
		if(StringUtils.isNotEmpty(keyWords)){
			sb.append(" and (mcs.campseg_id like '%"+keyWords+"%' or mcs.campseg_name like '%"+keyWords+"%' or msp.plan_name like '%"+keyWords+"%')");
		}
		sb.append(") order by pri_order_num ");
//		sb.append(" ) A LEFT JOIN (")
//		  .append(" select campseg_id, sum(camp_succ_num) camp_succ_num,sum(camp_user_num) camp_user_num from mtl_eval_info_campseg_m group by campseg_id")
//		  .append(" )B ON A.CAMPSEG_ID = B.CAMPSEG_ID order by A.pri_order_num ");

		 String sqlExt = DataBaseAdapter.getPagedSql(sb.toString(), pager.getPageNum(),pager.getPageSize());
		log.info("查询自动优先级策略"+sqlExt);
		List<Map<String, Object>> list  = this.getJdbcTemplate().queryForList(sqlExt.toString());
		for (Map<String, Object> map : list) {
			CampsegPriorityBean campsegPriorityBean = new CampsegPriorityBean();
			campsegPriorityBean.setCampsegId((String) map.get("campseg_id"));
			campsegPriorityBean.setPriOrderNum(Integer.parseInt(String.valueOf(map.get("pri_order_num"))));
			campsegPriorityBean.setCampsegName((String) map.get("campseg_name")+"_"+(String) map.get("plan_name"));
			campsegPriorityBean.setPlanId((String) map.get("plan_id"));
			campsegPriorityBean.setPlanName((String) map.get("plan_name"));
			campsegPriorityBean.setChnAdivId((String) map.get("chn_adiv_id"));
			campsegPriorityBean.setCustgroupNumber(0);
			if(null != map.get("custom_num")){
				campsegPriorityBean.setCustgroupNumber(Integer.parseInt(String.valueOf(map.get("custom_num"))));
			}
			campsegPriorityBean.setIneffectieDays(0);
			if(null != map.get("ineffectieDays")){
				campsegPriorityBean.setIneffectieDays(Integer.parseInt(String.valueOf(map.get("ineffectieDays"))));
			}
			
			int IsNewDays = 0;
			if(null != map.get("IsNewDays")){
				IsNewDays = Integer.parseInt(String.valueOf(map.get("IsNewDays")));
			}
			campsegPriorityBean.setIsNewDays("否");
			if(IsNewDays<10){
				campsegPriorityBean.setIsNewDays("是");
			}
//			float campSuccNum = 0;
//			float campUserNum = 0;
//			if(null != map.get("camp_succ_num")){
//				campSuccNum = Integer.parseInt(String.valueOf(map.get("camp_succ_num")));
//			}
//			if(null != map.get("camp_user_num")){
//				campUserNum = Integer.parseInt(String.valueOf(map.get("camp_user_num")));
//			}
//			float successRate = 0f;
//			if(campSuccNum == 0 || campUserNum == 0){
//				campsegPriorityBean.setSuccRate(0.00f);
//			}else{
//				successRate = campSuccNum/campUserNum;
//				java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");   
//				df.format(successRate); 
//				campsegPriorityBean.setSuccRate(Float.parseFloat(df.format(successRate)));
//			}
			
			String campSuccRate = String.valueOf(map.get("camp_succ_rate"));
			float successRate = 0f;
			if(StringUtils.isEmpty(campSuccRate) || "null".equals(campSuccRate)){
				campsegPriorityBean.setSuccRate(0.0000f);
			}else{
				successRate = Float.parseFloat(campSuccRate);
				java.text.DecimalFormat df = new java.text.DecimalFormat("#.####");   
				df.format(successRate); 
				campsegPriorityBean.setSuccRate(Float.parseFloat(df.format(successRate)));
			}
			
			listResult.add(campsegPriorityBean);
		}
		return listResult;
	}

	
	@Override
	public int getAutoPriorityCampsegNum(String channelId,String adivId, String cityId,String keyWords) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(1) from (")
		  .append(" select mcoa.campseg_id,mcoa.pri_order_num, mcs.campseg_name,msp.plan_id,msp.plan_name,mgi.custom_num,")
		  .append(" CEIL(to_date(mcs.end_date,'yyyy-mm-dd')-sysdate) ineffectieDays")
		  .append(" from mcd_camp_order mcoa")
		  .append(" left join mcd_camp_def mcs on mcoa.campseg_id=mcs.campseg_id")
		  .append(" left join mcd_plan_def msp on mcs.plan_id=msp.plan_id")
		  .append(" left join mcd_camp_custgroup_list mcc on mcc.campseg_id=mcs.campseg_id")
		  .append(" left join mcd_custgroup_def mgi on mcc.custgroup_id = mgi.custom_group_id")
		   .append(" left join (select unique campseg_id ,exec_status from mcd_camp_task) mct on mcoa.campseg_id = mct.campseg_id")
//		  .append(" where mcc.custgroup_type='CG' and mcoa.is_manual=0 AND (MCS.CAMPSEG_STAT_ID="+MpmCONST.MPM_CAMPSEG_STAT_DDCG+" OR MCS.CAMPSEG_STAT_ID="+MpmCONST.MPM_CAMPSEG_STAT_DDZX+" OR MCS.CAMPSEG_STAT_ID="+MpmCONST.MPM_CAMPSEG_STAT_PAUSE+")");
		  .append(" where  mcoa.is_manual=0  and mct.exec_status in (50,51,59) ")
		  .append(" and CEIL(to_date(mcs.end_date,'yyyy-mm-dd')-sysdate) >=0");  //失效的策略不显示
		if(StringUtils.isNotEmpty(channelId)){
			sb.append(" and mcoa.channel_id='"+channelId+"'");
		}
		if(StringUtils.isNotEmpty(cityId)){
			sb.append(" and mcoa.city_id='"+cityId+"'");
		}
		if(StringUtils.isNotEmpty(adivId)){
			sb.append(" and mcoa.chn_adiv_id='"+adivId+"'");
		}
		if(StringUtils.isNotEmpty(keyWords)){
			sb.append(" and (mcs.campseg_id like '%"+keyWords+"%' or mcs.campseg_name like '%"+keyWords+"%' or msp.plan_name like '%"+keyWords+"%')");
		}
		sb.append(" order by mcoa.pri_order_num")
		  .append(")");
		log.info("查询自动优先级策略数量"+sb.toString());
		return this.getJdbcTemplate().query(sb.toString(), this.longResultSetExtractor).intValue();
	}


	@Override
	public void editPriorityCampseg(String campsegId, String channelId,
			String cityId) {
		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append("update mcd_camp_order mcoa set mcoa.is_manual = 1  ")
			   .append(" where mcoa.campseg_id='"+campsegId+"'")
			   .append(" and mcoa.city_id='"+cityId+"' and mcoa.channel_id='"+channelId+"'");
		this.getJdbcTemplate().update(sbuffer.toString());
	}
	
	/**
	 * 当点击置顶  自动变手动的时候  先将原来的优先级全部加1
	 */
	@Override
	public void eidtManualPriority(String channelId,String cityId,String chnAdivId){
		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append("update mcd_camp_order mcoa set mcoa.pri_order_num = (mcoa.pri_order_num+1) ")
			   .append(" where mcoa.city_id='"+cityId+"' and mcoa.channel_id='"+channelId+"' and mcoa.is_manual=1")
			   .append(" and exists (select 1 from mcd_camp_def mcs where CEIL(to_date(mcs.end_date,'yyyy-mm-dd')-sysdate) >=0 and mcoa.campseg_id=mcs.campseg_id)");  //不对失效的策略进行计算
//			   .append(" and mcoa.campseg_id in (select campseg_id from mcd_camp_def mcs where CEIL(to_date(mcs.end_date,'yyyy-mm-dd')-sysdate) >=0");
		if(StringUtils.isNotEmpty(chnAdivId)){
			sbuffer.append(" and mcoa.chn_adiv_id ='"+chnAdivId+"'");
		}
		this.getJdbcTemplate().update(sbuffer.toString());
	}
	
	//当点击置顶  自动变手动的时候  先将原来的优先级全部加1  当自动超过十条的时候，将最后一条从手动改为自动，然后调用存储过程
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void changeManualToAutoPriority(String campsegId,String channelId,String cityId,String chnAdivId){
//		添加注释begin edit by lixq10 2016年6月24日09:35:43  PS:去掉前台手动优先级只显示10条的限制
//		StringBuffer sbuffer = new StringBuffer();
//		sbuffer.append("select count(1) from  mcd_camp_order mcoa ")
//			   .append(" where mcoa.city_id='"+cityId+"' and mcoa.channel_id='"+channelId+"' and mcoa.is_manual=1");
//		int num = this.getJdbcTemplate().queryForInt(sbuffer.toString());
//		if(num > 9){ //当置顶优先级中策略大于十条的时候，取出最后一条策略，并且将该策略改成系统自动优先级
//			StringBuffer sbuffer2 = new StringBuffer();
//			sbuffer2.append("select * from  mcd_camp_order mcoa ")
//			   		.append(" where mcoa.city_id='"+cityId+"' and mcoa.channel_id='"+channelId+"' and mcoa.is_manual=1 order by mcoa.pri_order_num ");
//			List<Map> list  = this.getJdbcTemplate().queryForList(sbuffer2.toString());
//			String campsegIdTemp = "";
//			for (Map map : list) {
//				int priOrderNum = Integer.parseInt(String.valueOf(map.get("pri_order_num")));
//				if(priOrderNum == 11){
//					campsegIdTemp = (String) map.get("campseg_id");
//					this.changeManualToAutoCampseg(campsegIdTemp, channelId, cityId);
//				}
//			}
//		}
//		添加注释end edit by lixq10 2016年6月24日09:35:43
		
		//将要置顶的策略状态就行修改
		StringBuffer sbuffer3 = new StringBuffer();
		sbuffer3.append("update mcd_camp_order mcoa set mcoa.pri_order_num = 1, mcoa.is_manual = 1")
		.append(" where mcoa.city_id='"+cityId+"' and mcoa.channel_id='"+channelId+"' and mcoa.campseg_id='"+campsegId+"'");
		if(StringUtils.isNotEmpty(chnAdivId)){
			sbuffer3.append(" and mcoa.chn_adiv_id='"+chnAdivId+"'");
		}
		this.getJdbcTemplate().update(sbuffer3.toString());
		//调用存储过程，重排序
//		this.getJdbcTemplate().execute("CALL UPDATE_CAMPSEG_PRIORITY()");
		
		String sql1 = "call UPDATE_CAMPSEG_PRIORITY()";
		this.getJdbcTemplate().execute(sql1,new CallableStatementCallback(){
	        public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
	        	cs.execute(); 
	            return null; 
	        }
	    });
	}
	
	/**
	 * 当手动优先级中超过10条，第11条要自动改为自动优先级
	 * @param campsegId
	 * @param channelId
	 * @param cityId
	 */
	@SuppressWarnings("unused")
	private void changeManualToAutoCampseg(String campsegId,String channelId,String cityId){
		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append("update mcd_camp_order mcoa set mcoa.is_manual = 0")
			   .append(" where mcoa.campseg_id='"+campsegId+"' and mcoa.city_id='"+cityId+"' and mcoa.channel_id='"+channelId+"'");
		this.getJdbcTemplate().update(sbuffer.toString());
	}

	@Override
	public void editManualPriorityCampseg(final List<Map<String, Object>> list1,final List<Map<String, Object>> list2) {
		StringBuffer sbuffer1 = new StringBuffer();   //针对有运营位的list
		sbuffer1.append("update mcd_camp_order mcoa set mcoa.pri_order_num =?")
				.append(" where mcoa.city_id=? and mcoa.channel_id=? and mcoa.campseg_id=? and mcoa.chn_adiv_id=?");
		
		StringBuffer sbuffer2 = new StringBuffer();   //针对有运营位的list
		sbuffer2.append("update mcd_camp_order mcoa set mcoa.pri_order_num =?")
				.append(" where mcoa.city_id=? and mcoa.channel_id=? and mcoa.campseg_id=?");
		//将两个策略的优先级字段互换
//		this.getJdbcTemplate().update(sbuffer0.toString());
		
		//先更新有运营位的的
		this.getJdbcTemplate().batchUpdate(sbuffer1.toString(), new BatchPreparedStatementSetter(){
			@Override
			public int getBatchSize() {
				return list1.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				Map<String,Object> data=list1.get(i);
				ps.setString(1,String.valueOf(data.get("priority")));
				ps.setString(2,(String)data.get("cityId"));
				ps.setString(3,(String)data.get("channelId"));
				ps.setString(4,(String)data.get("campsegId"));
				ps.setString(5,(String)data.get("chnAdivId"));
			}
		});
		
		//更新没有运营位的
		this.getJdbcTemplate().batchUpdate(sbuffer2.toString(), new BatchPreparedStatementSetter(){
			@Override
			public int getBatchSize() {
				return list2.size();
			}
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				Map<String,Object> data=list2.get(i);
				ps.setString(1,String.valueOf(data.get("priority")));
				ps.setString(2,(String)data.get("cityId"));
				ps.setString(3,(String)data.get("channelId"));
				ps.setString(4,(String)data.get("campsegId"));
			}
		});
		//调用存储过程，重排序
//		this.getJdbcTemplate().execute("CALL UPDATE_CAMPSEG_PRIORITY()");
	}


	@Override
	public void cancleTopManualPriorityCampseg1(String campsegId,String cityId,String channelId,String chnAdivId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("update mcd_camp_order mcoa set mcoa.is_manual=0 where campseg_id='"+campsegId+"' and mcoa.city_id='"+cityId+"' and mcoa.channel_id='"+channelId+"'");
		if(StringUtils.isNotEmpty(chnAdivId)){
			buffer.append(" and mcoa.chn_adiv_id='"+chnAdivId+"'");
		}
		log.info("将手动改为自动："+buffer.toString());
		this.getJdbcTemplate().update(buffer.toString());
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void cancleTopManualPriorityCampseg2(String campsegId,String cityId,String channelId,String chnAdivId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select mcoa.campseg_id,mcoa.channel_id,mcoa.chn_adiv_id,mcoa.city_id from mcd_camp_order mcoa ");
		buffer.append(" left join (select unique campseg_id ,exec_status from mcd_camp_task) mct on mcoa.campseg_id = mct.campseg_id")
			  .append(" left join mcd_camp_def mcs on mcoa.campseg_id=mcs.campseg_id ")
		  	  .append(" where  mcoa.is_manual=1 and mcoa.city_id='"+cityId+"' and mcoa.channel_id='"+channelId+"'and mct.exec_status in (50,51,59)")
		  	  .append(" and CEIL(to_date(mcs.end_date,'yyyy-mm-dd')-sysdate) >=0");
		if(StringUtils.isNotEmpty(chnAdivId)){
			buffer.append(" and mcoa.chn_adiv_id='"+chnAdivId+"'");
		}
		buffer.append(" order by mcoa.pri_order_num");
		log.info("************************************sql====="+buffer.toString());
		List<Map<String, Object>> list  = this.getJdbcTemplate().queryForList(buffer.toString());
		for(int i = 0;i<list.size();i++){
			StringBuffer sbuffer = new StringBuffer();
			String campsegIdT = String.valueOf(list.get(i).get("campseg_id"));
			String cityIdT = String.valueOf(list.get(i).get("city_id"));
			String channelIdT = String.valueOf(list.get(i).get("channel_id"));
			sbuffer.append("update mcd_camp_order mcoa set mcoa.pri_order_num = "+(i+1)+" where campseg_id='"+campsegIdT+"' and mcoa.city_id='"+cityIdT+"' and mcoa.channel_id='"+channelIdT+"' AND mcoa.chn_adiv_id='"+chnAdivId+"'");
			log.info("********************sql="+sbuffer.toString());
			this.getJdbcTemplate().update(sbuffer.toString());
		}
		
//		this.getJdbcTemplate().execute("CALL UPDATE_CAMPSEG_PRIORITY()");
		String sql1 = "call UPDATE_CAMPSEG_PRIORITY()";
		this.getJdbcTemplate().execute(sql1,new CallableStatementCallback(){
	        public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
	        	
	        	cs.execute(); 
	            return null; 
	        }
	    });
	}
	
}


