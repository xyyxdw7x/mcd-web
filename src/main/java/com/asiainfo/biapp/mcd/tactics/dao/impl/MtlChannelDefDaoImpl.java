package com.asiainfo.biapp.mcd.tactics.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.framework.jdbc.VoPropertyRowMapper;
import com.asiainfo.biapp.mcd.tactics.dao.IMtlChannelDefDao;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampChannelList;
import com.asiainfo.biapp.mcd.tactics.vo.MtlChannelDefCall;

/**
 * Created on Dec 1, 2006 11:20:05 AM
 *
 * <p>Title: </p>
 * <p>Description: 活动波次营销渠道定义表操作DAO实现类</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */
@Repository("mtlChannelDefDao")
public class MtlChannelDefDaoImpl extends JdbcDaoBase implements IMtlChannelDefDao {
    private static Logger log = LogManager.getLogger();

	public void save(McdCampChannelList def) throws Exception {
		this.getJdbcTemplateTool().save(def);
	}
	
	public void deleteMtlChannelDef(String campsegId) throws Exception {/*
		String sql = "from MtlChannelDef mcd where mcd.id.campsegId='" + campsegId + "'";
		this.getHibernateTemplate().deleteAll(this.getHibernateTemplate().find(sql));
		this.getHibernateTemplate().flush();*/
	}
	
    /**
     * 保存渠道对应表——外呼
     * @param mtlChannelDefCall
     */
	@Override
	public void save(MtlChannelDefCall mtlChannelDefCall) {
		try {
			this.getJdbcTemplateTool().save(mtlChannelDefCall);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取策略包下子策略所用营销渠道
	 * 2015-8-15 9:36:24
	 * @author gaowj3
	 * @param campsegId 策略ID
	 * @return
	 */
	@Override
	public List<Map<String,Object>> findChildChannelIdList(String campsegId) {
		StringBuffer sql = new StringBuffer("  select distinct mcd.channel_id from mcd_camp_channel_list mcd where mcd.campseg_id in (  "); 	
		sql.append(" select mcs.campseg_id from mcd_camp_def mcs where mcs.campseg_pid =  ?  ") ; 	
		sql.append(" )") ;
		List<Map<String,Object>> list= this.getJdbcTemplate().queryForList(sql.toString(), new Object[] { campsegId});
		return list;
	}
    /**
     * 根绝策略ID查询渠道信息
     * @param campsegId
     * @return
     */
    @Override
    public List<Map<String,Object>> getMtlChannelDefs(String campsegId) {
        StringBuffer sql = new StringBuffer("  select mcd.targer_user_nums,dmc.channel_name from mcd_camp_channel_list mcd  ");   
        sql.append(" left join mcd_dim_channel dmc on mcd.channel_id = dmc.channel_id   ") ;    
        sql.append(" where mcd.campseg_id = ? ") ;
        List<String> parameterList = new ArrayList<String>();
        parameterList.add(campsegId);
        return this.getJdbcTemplate().queryForList(sql.toString(),parameterList.toArray());
    }
    
    /**
     * 删除外呼渠道
     * @param campsegId
     * @param channelId
     */
	@Override
	public void deleteMtlChannelDefCall(String campsegId, String channelId) {
	}
    /**
     * add by jinl 20150717
     * 获取投放渠道
     * @param campsegId
     * @return
     */
    @Override
    public List<Map<String, Object>> getDeliveryChannel(String campsegId) {
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        try {
            StringBuffer buffer = new StringBuffer();
            buffer.append("select basic.*,mcd_dim_adiv_info.adiv_name,mcd_camp_def.CEP_EVENT_ID from (")
                  .append(" select dmc.channel_id,dmc.channel_name,mcd.EXEC_CONTENT,mcd.update_cycle,mcd.contact_type,mcd.campseg_id,mcd.channel_adiv_id,mcd.param_num,mcd.param_days,mcd.award_mount,mcd.edit_url,mcd.handle_url,mcd.send_sms,mcd.EXEC_TITLE,mcd.FILE_NAME ")
                  .append(" from mcd_camp_channel_list mcd, mcd_dim_channel dmc ")
                  .append(" where mcd.channel_id = dmc.channel_id   and mcd.campseg_id = ? ) basic")
                  .append(" left join  mcd_dim_adiv_info on basic.channel_adiv_id = mcd_dim_adiv_info.adiv_id and basic.channel_id=mcd_dim_adiv_info.channel_id")
                  .append(" left join mcd_camp_def on basic.campseg_id = mcd_camp_def.campseg_id");
            
            list= this.getJdbcTemplate().queryForList(buffer.toString(), new Object[] { campsegId });
        } catch (Exception e) {
            log.error("", e);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getDeliveryChannelCall(String campsegId) {
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        try {
            StringBuffer buffer = new StringBuffer();
            
            buffer.append("select basic.*,mcd_camp_def.CEP_EVENT_ID from ")
                  .append(" (select mcd.campseg_id,dmc.channel_id as channelId, dmc.channel_name as channelName, mcd.task_code as taskCode, mcd.task_name as taskName, mcd.demand,")
                  .append(" (select dcctc.CLASS_NAME || '-' || dcctsc.SUB_CLASS_NAME from DIM_CHN_CALL_TASK_CLASS dcctc left join  DIM_CHN_CALL_TASK_SUB_CLASS dcctsc on dcctc.CLASS_ID = dcctsc.CLASS_PID where dcctsc.SUB_CLASS_ID =mcd.task_class_id) as taskClassName,")
                  .append(" (select LEVEL1_NAME from DIM_CALL_CHN_TASK_REL where SUB_CLASS_ID = mcd.task_class_id and LEVEL1_ID=mcd.task_level1_id) as taskLevel1,")
                  .append(" (select LEVEL2_NAME from DIM_CALL_CHN_TASK_REL where SUB_CLASS_ID = mcd.task_class_id and LEVEL1_ID=mcd.task_level2_id) as taskLevel2,")
                  .append(" (select LEVEL3_NAME from DIM_CALL_CHN_TASK_REL where SUB_CLASS_ID = mcd.task_class_id and LEVEL1_ID=mcd.task_level3_id) as taskLevel3,")
                  .append(" (select LEVEL1_NAME from DIM_CALL_CHN_BUSI_REL where SUB_CLASS_ID = mcd.task_class_id and LEVEL1_ID=mcd.busi_level1_id) as busiLevel1,")
                  .append(" (select LEVEL2_NAME from DIM_CALL_CHN_BUSI_REL where SUB_CLASS_ID = mcd.task_class_id and LEVEL1_ID=mcd.busi_level2_id) as busiLevel2,")
                  .append(" DECODE(mcd.in_plan_flag,0,'否',1,'是') as inPlanFlag, ")
                  .append(" (select TASK_NAME from MTL_CHN_CALL_PLAN_MONTH_TASK where TASK_NO = mcd.month_plan_flag) as monthTaskName, ")
                  .append("  DECODE(mcd.call_cycle,1,'周期性',2,'一次性') as callCycle, ")
                  .append("  mcd.call_plan_num as callPlanNum , mcd.finish_date AS finishDate, mcd.task_comment as taskComment, mcd.user_lable_info as userLableInfo,")
                  .append(" mcd.call_question_url AS callQuestionUrl, mcd.call_no AS callNo,")
                  .append(" DECODE(mcd.avoid_filter_flag,0,'否',1,'是') as avoidFilterFlag, DECODE(mcd.call_test_flag,0,'否',1,'是') as callTestFlag,")
                  .append(" DECODE(mcd.fre_filter_flag,0,'否',1,'是') as freFilterFlag, ")
                  .append(" (select LOCAL_NAME from DIM_CHN_CALL_LOCAL where LOCAL_ID = mcd.call_form) as callFormName, ")
                  .append(" (select FORM_NAME from DIM_CHN_CALL_FORM where FORM_ID = mcd.call_city_type) as  callCityTypeName, ")
                  .append("mcd.call_question_name AS callQuestionName ")
                  .append("  from mtl_channel_def_call mcd, mcd_dim_channel dmc ")
                  .append(" where mcd.channel_id = dmc.channel_id and mcd.campseg_id = ?) basic left join mcd_camp_def on basic.campseg_id = mcd_camp_def.campseg_id");
                    
            list= this.getJdbcTemplate().queryForList(buffer.toString(), new Object[] { campsegId });
        } catch (Exception e) {
            log.error("", e);
        }
        return list;
    }
    
	public List<McdCampChannelList> findMtlChannelDef(String campsegId) throws Exception {/*
		String sql = "from MtlChannelDef mcd where mcd.id.campsegId='" + campsegId + "'";
		final String tmpSql = sql;
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(tmpSql);
				return query.list();
			}
		});
	*/
		String sql = "select * from mcd_camp_channel_list mcd where mcd.id.campseg_id='" + campsegId + "'";
		List<McdCampChannelList> mtlChannelDefs=this.getJdbcTemplate().query(sql, new VoPropertyRowMapper<McdCampChannelList>(McdCampChannelList.class));
		return mtlChannelDefs;
	}
	
	/**
	 * 根据策略ID，渠道ID获取相关信息（外呼渠道）
	 * @param campsegId
	 * @param channelDefCall
	 * @return
	 */
	@Override
	public Map<String,Object> searchMtlChnCallPlanMonthTask(String campsegId,String channelDefCall) {
		String sql = "select t.campseg_id as \"campsegId\",t.channel_id as \"channelId\",t.task_code as \"taskCode\",t.task_name as \"taskName\",t.demand as \"demand\",t.task_class_id as \"taskClassId\",t.task_level1_id as \"tasklevel1Id\","+
					"t.task_level2_id as \"taskLevel2Id\",t.task_level3_id as \"taskLevel3Id\",t.busi_level1_id as \"busiLevel1Id\",t.busi_level2_id as \"busiLevel2Id\",t.in_plan_flag as \"inPlanFlag\",t.month_plan_flag as \"monthPlanFlag\","+
					"t.call_cycle as \"callCycle\",t.call_plan_num as \"callPlanNum\",t.finish_date as \"finishDate\",t.task_comment as \"taskComment\",t.user_lable_info as \"userLableInfo\",t.call_question_url as \"callQuestionUrl\",t.call_no as \"callNo\","+
					"t.avoid_filter_flag as \"avoidFilterFlag\",t.call_test_flag as \"callTestFlag\",t.fre_filter_flag as \"freFilterFlag\",t.call_form as \"callForm\",t.call_city_type as \"callCityType\",t.channel_prio as \"channelPrio\","+
					"t.approve_result as \"approveResult\",t.approve_result_desc as \"approveResultDesc\"" +
					" from mcd_camp_channel_list_call t where campseg_id = ? and channel_id = ?";
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql,new Object[]{campsegId,channelDefCall});
		Map<String,Object> map = null;
		if(list != null && list.size() > 0){
			map = (Map<String,Object>)list.get(0);
		}
		return map;
	}
    /**
     *  add by gaowj3 20150815
     * @Title: updateMtlChannelDefApproveResilt
     * @Description:  根据工单编号，修改所有字策略（规则）下某渠道的审批状态
     * @param @param assing_id 工单编号
     * @return String 
     * @throws
     */
    @Override
    public void updateMtlChannelDefApproveResult(String assing_id,String approve_desc, String channel_id, short approveResult) {
        StringBuffer sql = new StringBuffer(" update mcd_camp_channel_list set approve_result = ?,approve_result_desc=? ");   
        sql.append(" where channel_id = ? and campseg_id in  ") ; 
        sql.append(" (select campseg_id from mcd_camp_def where approve_flow_id = ? and campseg_pid != '0') ") ; 
        this.getJdbcTemplate().update(sql.toString(), new Object[] {approveResult, approve_desc,channel_id,assing_id});
        
        
    }
    
    /**
     * 根据工单编号，修改所有子策略（规则）下某渠道的审批状态，因外呼渠道换表存了，故更改外呼渠道的审批状态
     * @param assing_id 工单编号
     * @param approve_desc
     * @param channel_id 渠道ID
     * @param approveResult
     */
    @Override
    public void updateMtlChannelDefCallApproveResult(String assing_id,
            String approve_desc, String channel_id, short approveResult) {
        StringBuffer sql = new StringBuffer(" update mcd_camp_channel_list_call set approve_result = ?,approve_result_desc=? ");  
        sql.append(" where channel_id = ? and campseg_id in  ") ; 
        sql.append(" (select campseg_id from mcd_camp_def where approve_flow_id = ? and campseg_pid != '0') ") ; 
        this.getJdbcTemplate().update(sql.toString(), new Object[] {approveResult, approve_desc,channel_id,assing_id});
        
    }
    
    /**
     *  add by gaowj3 20150815
     * @Title: updateMtlChannelDefApproveResilt
     * @Description:  根据子策略（规则）编号查询该策略下渠道的审批状态
     * @param @param assing_id 工单编号
     * @return String 
     * @throws
     */
    @Override
    public List<Map<String,Object>> getMtlChannelDefApproveFlowList(String childCampseg_id) {
        StringBuffer sql = new StringBuffer(" select distinct approve_result,approve_result_desc from mcd_camp_channel_list where campseg_id = ?  ");
        sql.append(" union ");
        sql.append("  select distinct approve_result,approve_result_desc from mcd_camp_channel_list_call where campseg_id = ?   ");
        List<Map<String,Object>> list= this.getJdbcTemplate().queryForList(sql.toString(), new Object[] { childCampseg_id,childCampseg_id});
        return list;
    }
    /**
     * 查找活动下的所有渠道
     * @param campsegId
     * @return
     */
    @Override
    public List<McdCampChannelList> getChannelByCampsegId(String campsegId) {
        StringBuffer sql = new StringBuffer(" select * from mcd_camp_channel_list mcd where mcd.id.campseg_id in (select campseg_id from mcd_camp_def where campseg_pid = ?) ");
        Object[] args=new Object[]{campsegId};
        int[] argTypes=new int[]{Types.VARCHAR};
        List<McdCampChannelList> mtlChannelDefs=this.getJdbcTemplate().query(sql.toString(),args, argTypes,new VoPropertyRowMapper<McdCampChannelList>(McdCampChannelList.class));
        return mtlChannelDefs;
    }
	
}
