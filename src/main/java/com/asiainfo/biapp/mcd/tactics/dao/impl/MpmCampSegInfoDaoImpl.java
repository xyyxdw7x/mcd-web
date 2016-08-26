package com.asiainfo.biapp.mcd.tactics.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.framework.jdbc.VoPropertyRowMapper;
import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.mcd.common.constants.MpmCONST;
import com.asiainfo.biapp.mcd.common.util.DataBaseAdapter;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.tactics.dao.IMpmCampSegInfoDao;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampDrvType;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampsegStat;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfo;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampsegCiCustgroup;
import com.asiainfo.biframe.utils.config.Configure;
import com.asiainfo.biframe.utils.database.jdbc.Sqlca;
import com.asiainfo.biframe.utils.string.StringUtil;
/**
 * 策略管理相关dao
 * @author AsiaInfo-jie
 *
 */
@Repository("mpmCampSegInfoDao")
public class MpmCampSegInfoDaoImpl extends JdbcDaoBase  implements IMpmCampSegInfoDao {
    private static Logger log = LogManager.getLogger();

    @Override
    public List searchIMcdCampsegInfo(MtlCampSeginfo segInfo, Pager pager) {
        List parameterList = new ArrayList();
        JdbcTemplate jt = this.getJdbcTemplate();
        StringBuffer buffer = new StringBuffer();
        buffer.append("select msi.campseg_name as  \"campsegName\",msi.campseg_id as \"campsegId\" ,msi.start_date as \"startDate\",msi.end_date as \"endDate\",msi.campseg_stat_id as \"campsegStatId\",dcs.campseg_stat_name as \"campsegStatName\",create_username as \"createUserName\",decode(msi.create_userid,'")
        .append(segInfo.getCreateUserid())
        .append("',0,1) as \"isMy\",dcs.CAMPSEG_STAT_SITEID as \"campsegStatSiteId\",msi.pause_comment as  \"pauseComment\" ");
        
        if(segInfo.getIsSelectMy() != null && segInfo.getIsSelectMy() == 0){
            buffer.append(",(CASE  WHEN msi.campseg_stat_id = '54' AND TO_NUMBER(TO_DATE(msi.end_date, 'YYYY-MM-DD') - sysdate) * 24 <= 72 THEN 1 ELSE 0 END) AS \"DQ_1\",")
            .append("(CASE WHEN msi.campseg_stat_id = '40' AND TO_NUMBER(TO_DATE(msi.start_date, 'YYYY-MM-DD') - sysdate) * 24 <= 24 THEN 1 ELSE 0 END) AS \"DQ_2\",")
            .append("(CASE WHEN msi.campseg_stat_id IN ('40', '54', '59', '71') AND TO_NUMBER((select MIN(PLAN_ENDDATE) from mtl_stc_plan where PLAN_ENDDATE IS NOT NULL AND PLAN_ID = msi.Plan_Id) - sysdate) * 24 <= 72 THEN 1 ELSE 0 END) AS \"DQ_3\",")
            .append("(CASE WHEN msi.campseg_stat_id = '54' THEN TRUNC(TO_DATE(msi.end_date, 'YYYY-MM-DD') - trunc(sysdate ,'dd')) ELSE 0 END) AS \"DQ_D_1\",")
            .append("(CASE WHEN msi.campseg_stat_id = '40' THEN TRUNC(TO_DATE(msi.start_date, 'YYYY-MM-DD') - trunc(sysdate ,'dd')) ELSE 0 END) AS \"DQ_D_2\",")
            .append("(CASE WHEN msi.campseg_stat_id IN ('40', '54', '59', '71') THEN TRUNC((select MIN(PLAN_ENDDATE) from mtl_stc_plan where PLAN_ENDDATE IS NOT NULL AND PLAN_ID = msi.Plan_Id) - trunc(sysdate ,'dd')) ELSE 0 END) AS \"DQ_D_3\" ") ;
        } 
        
        buffer.append(" from mtl_camp_seginfo msi ")
//      buffer.append("select msi.* from mtl_camp_seginfo msi ")

               .append(" left join DIM_CAMPSEG_STAT dcs on msi.campseg_stat_id = dcs.campseg_stat_id ")
              .append("   where 1=1 ")
              .append("   and msi.campseg_pid ='0' ")
              .append("   and (msi.camp_class is null or msi.camp_class =1) and (msi.is_scene_template = 2 or msi.is_scene_template is null or msi.is_scene_template = 0)");
              
              
            //业务状态改为多选
        if (segInfo.getCampDrvIds() != null && !"".equals(segInfo.getCampDrvIds())) {
            buffer.append("  and  msi.campseg_id in (select mcdr.campseg_id from mtl_campseg_drv_rel mcdr where ")
            .append(" mcdr.camp_drv_id in ( "+ segInfo.getCampDrvIds() +")")
            .append(")");
        }
        
        if (StringUtil.isNotEmpty(segInfo.getKeywords())) {
            if(segInfo.getKeywords().contains("%")){
                buffer.append(" and (msi.campseg_name like ?  escape '\' or msi.campseg_desc like ?  escape '\' or msi.campseg_id like ?  escape '\') ");
                parameterList.add("%" + segInfo.getKeywords().replaceAll("%", "\\\\%") + "%");
                parameterList.add("%" + segInfo.getKeywords().replaceAll("%", "\\\\%") + "%");
                parameterList.add("%" + segInfo.getKeywords().replaceAll("%", "\\\\%") + "%");
            }else{
                buffer.append(" and (msi.campseg_name like ? or msi.campseg_desc like ? or msi.campseg_id like ?) ");
                parameterList.add("%" + segInfo.getKeywords() + "%");
                parameterList.add("%" + segInfo.getKeywords() + "%");
                parameterList.add("%" + segInfo.getKeywords() + "%");
            }

        }
        if(segInfo.getIsSelectMy() != null && segInfo.getIsSelectMy() == 0){
            buffer.append(" and msi.create_userid= ? ");
            parameterList.add(segInfo.getCreateUserid());
        }
        if(StringUtil.isNotEmpty(segInfo.getChannelId()) ){
            buffer.append(" and msi.campseg_id in(")
                  .append(" select unique mcs.campseg_pid from mtl_camp_seginfo mcs")
                  .append(" left join mtl_channel_def mcd on mcs.campseg_id=mcd.campseg_id")
                  .append(" where mcd.channel_id=? and mcs.campseg_pid<>'0')");
            parameterList.add(segInfo.getChannelId());
        }
        //省公司还是分公司人查看
        if(StringUtil.isNotEmpty(segInfo.getAreaId())){
            buffer.append(" and msi.area_id= ?");
            parameterList.add(segInfo.getAreaId());
        }
        
        //状态
        if (segInfo.getCampsegStatId() != null && !"".equals(segInfo.getCampsegStatId())) {
            buffer.append(" and msi.campseg_stat_id = ? ");
            parameterList.add(segInfo.getCampsegStatId());
        }
        buffer.append(" order by msi.create_time desc");
        
        System.out.println(buffer.toString());
        String sqlExt = DataBaseAdapter.getPagedSql(buffer.toString(), pager.getPageNum(),pager.getPageSize());
        List list = jt.queryForList(sqlExt.toString(), parameterList.toArray());
        List listSize = jt.queryForList(buffer.toString(), parameterList.toArray());
        pager.setTotalSize(listSize.size());  // 总记录数
        
        
        return list;
    }
    /**
     * gaowj3
     * JDBC查询业务状态
     * @return
     */
    @Override
    public List<DimCampDrvType> getDimCampSceneList() {
        String sql="select * from DIM_CAMP_DRV_TYPE where 1=1  order by CAMP_DRV_ID";
        List<DimCampDrvType> list = this.getJdbcTemplate().query(sql,new VoPropertyRowMapper<DimCampDrvType>(DimCampDrvType.class));
        return list;
    }
    /**
     * gaowj3
     * JDBC查询策略状态
     * @return
     */
    @Override
    public List getDimCampsegStatList() {
        String sql="select * from DIM_CAMPSEG_STAT where 1=1 and CAMPSEG_STAT_VISIBLE = 0 order by CAMPSEG_STAT_SITEID";
        List<DimCampsegStat> list = this.getJdbcTemplate().query(sql,new VoPropertyRowMapper<DimCampsegStat>(DimCampsegStat.class));
        return list;
    }
	/**
	 * 保存活动信息
	 */
	@Override
	public Serializable saveCampSegInfo(MtlCampSeginfo segInfo) throws Exception {
		//TODO:this.getHibernateTemplate().saveOrUpdate(segInfo);
		return segInfo.getCampsegId();
	}
	/**
	 * 根据活动编号取活动信息
	 */
	@Override
	public MtlCampSeginfo getCampSegInfo(String campSegId) throws Exception {
		MtlCampSeginfo obj = null;
		try {
			//TODO: obj = (MtlCampSeginfo) this.getHibernateTemplate().get(MtlCampSeginfo.class, campSegId);
		} catch (Exception e) {
			throw e;
		}
		return obj;
	}
    /**
     * 根据父策略ID获取子策略
     * @param campsegId
     * @return
     */
	@Override
	public List getChildCampSeginfo(String campsegId) throws Exception {
        final String sql = "select * from mtl_camp_seginfo seginfo where seginfo.campseg_pid = ? order by CREATE_TIME asc ";
        Object[] args=new Object[]{campsegId};
        int[] argTypes=new int[]{Types.VARCHAR};
        List<MtlCampSeginfo> list = this.getJdbcTemplate().query(sql,args,argTypes,new VoPropertyRowMapper<MtlCampSeginfo>(MtlCampSeginfo.class));
        return list;
	}
	/**
	 * 更新活动信息
	 */
	@Override
	public void updateCampSegInfo(MtlCampSeginfo segInfo) throws Exception {
		//TODO: this.getHibernateTemplate().update(segInfo);
	    final String sql = "update mtl_camp_seginfo set campseg_name=?,campseg_no=?,start_date=?,end_date=?,CAMPSEG_STAT_ID=?,campseg_type_id=?,camp_pri_id=?,approve_flow_id=?,"
	                    + "approve_result=?,approve_result_desc=?,create_username=?,create_userid=?,city_id=?,deptid=?,create_time=?,campseg_desc=?,PLAN_ID=?,contacted_user_nums=?,"
	                    + "contact_okuser_nums=?,received_okuser_nums=?,CAMPSEG_CONTACT_FLAG=?,campseg_contact_usernums=?,evaluate_comment=?,CUST_LIST_TAB_NAME=?,TIME_INTERVAL=?,"
	                    + "campseg_pid=?,camp_class=?,targer_user_nums=?,CURRENT_TASK_ID=?,avoid_bother_type_ids=?,TARGET_CUST_TYPE=?,select_templet_id=?,ACTIVE_TEMPLET_ID=?,"
	                    + "init_cust_list_tab = ?,event_rule_desc=?,approve_remind_time=?,is_filter_disturb=? where campseg_id = ?";
	    this.getJdbcTemplate().update(sql);
	    Object[] objects = new Object[]{segInfo.getCampsegName(),segInfo.getCampsegNo(),segInfo.getStartDate(),segInfo.getEndDate(),segInfo.getCampsegStatId(),
	                    segInfo.getCampsegTypeId(),segInfo.getCampPriId(),segInfo.getApproveFlowid(),segInfo.getApproveResult(),segInfo.getApproveResultDesc(),
	                    segInfo.getCreateUserName(),segInfo.getCreateUserid(),segInfo.getCityId(),segInfo.getDeptId(),segInfo.getCreateTime(),segInfo.getCampsegDesc(),
	                    segInfo.getPlanId(),segInfo.getContactedUserNums(),segInfo.getContactOkuserNums(),segInfo.getReceivedOkuserNums(),segInfo.getCampsegContactFlag(),
	                    segInfo.getCampsegContactUsernums(),segInfo.getEvaluateComment(),segInfo.getCustListTabName(),segInfo.getTimeInterval(),segInfo.getCampsegPid(),
	                    segInfo.getCampClass(),segInfo.getTargerUserNums(),segInfo.getCurrentTaskId(),segInfo.getAvoidBotherTypeIds(),segInfo.getTargetCustType(),
	                    segInfo.getSelectTempletId(),segInfo.getActiveTempletId(),segInfo.getInitCustListTab(),segInfo.getEventRuleDesc(),segInfo.getApproveRemindTime(),
	                    segInfo.getIsFileterDisturb(),segInfo.getCampsegId()};
	   
        this.getJdbcTemplate().update(sql,objects);

	}
	
    @Override
    public List getCustGroupSelectList(String campsegId) {
     // TODO 自动生成方法存根
        String sql="select * from MTL_CAMPSEG_CUSTGROUP where CAMPSEG_ID = ?";
        Object[] args=new Object[]{campsegId};
        int[] argTypes=new int[]{Types.VARCHAR};
        List<MtlCampsegCiCustgroup> list = this.getJdbcTemplate().query(sql,args,argTypes,new VoPropertyRowMapper<MtlCampsegCiCustgroup>(MtlCampsegCiCustgroup.class));
        return list;
    }
    
    /**
     * 根据营销活动父节点查询父节点下所有节点 营销活动编码
     * 2013-6-4 16:38:32
     * @author Mazh
     * @param campSegId
     * @param rList
     * @return
     */
    @Override
    public List<String> gettListAllCampSegByParentId(String campSegId, List<String> rList) {
        final String sql = " SELECT mcs.campsegId from mtl_camp_seginfo mcs WHERE mcs.campseg_pid='" + campSegId + "'";
        List list = this.getJdbcTemplate().queryForList(sql);
        return list;

    }
    
    /**
     * 根据活动编号删除活动信息
     */
    @Override
    public void deleteCampSegInfo(MtlCampSeginfo segInfo) throws Exception {
        String campsegId = segInfo.getCampsegId();
        try {
            // 1 取活动下属活动在属性设置阶段设置的活动模版编号
            String sql = "select t2.proform_result from mtl_campseg_progress t2 where t2.campseg_id=? and t2.step_id=?";
            log.debug(">>deleteCampSegInfo() 1. : {}", sql);
            List list = this.getJdbcTemplate().queryForList(sql, new Object[] { campsegId, MpmCONST.MPM_SYS_ACTSTEP_DEF_ACTIVE_TEMPLET });
            String templetIds = "";
            for(int i = 0 ;i < list.size() ; i ++){
                Map map = (Map)list.get(i);
                templetIds += "'" + map.get("proform_result").toString().trim() + "',";

            }
            if (templetIds.length() > 0) {
                templetIds = templetIds.substring(0, templetIds.length() - 1);
            }
            // 删除活动模版相关信息
            deleteCampsegTemplets( campsegId, templetIds);
            // 删除活动营销策划信息
            deleteCampsegPlanRules(campsegId);
            // 删除活动附件信息
            sql = "delete from mtl_attachment_info where camp_campseg_id=?";
            log.debug(">>deleteCampaignBaseInfo() 21. : {}", sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });
            // 删除活动的步骤信息
            sql = "delete from mtl_campseg_progress where campseg_id=?";
            log.debug(">>deleteCampSegInfo() 22. : {}", sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });
            // 删除活动相关的外部评估链接
            sql = "delete from MTL_EXT_EVALUATION_URL where camp_id=?";
            log.debug(">>deleteCampSegInfo() 23. : {}", sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            // 删除活动单列分析数据
            sql = "delete from mtl_analyse_single where campseg_id=?";
            log.debug(">>deleteCampSegInfo() 23.2  : {}", sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            // 删除活动的目标客户群运算设置
            sql = "delete from MTL_AGGREGATE_SELECT where campseg_id=?";
            log.debug(">>deleteCampSegInfo() 23.3  : {}", sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            // 删除活动的客户群选择信息
            sql = "delete from mtl_campseg_custgroup where campseg_id=?";
            log.debug(">>deleteCampaignBaseInfo() 25.7  : {}", sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            // 删除计划执行活动表
            sql = "delete from mtl_campseg_exec_list where campseg_id=?";
            log.debug(">>deleteCampSegInfo() 26. : {}", sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });
            //
            sql = "delete from mtl_campseg_export_cond where campseg_id =?";
            log.debug(">>deleteCampSegInfo() 26.8 : {}", sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            //TODO 添加删除渠道 mtl_channel_def
            sql = " DELETE FROM mtl_channel_def WHERE campseg_id=?";
            log.debug(">>deleteCampSegInfo() 删除渠道 : {}", sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            //TODO 添加接触规则表数据删除 mcd_activity_contact_rule_def
            sql = " DELETE FROM MCD_ACTIVITY_CONTACT_RULE_DEF WHERE ACTIVITY_CODE=?";
            log.debug(">>deleteCampSegInfo() 删除接触控制规则 : {}", sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });
            //TODO 添加营销活动执行时间表删除 mcd_activity_contact_rule_def
            sql = " DELETE FROM MTL_CAMP_EXEC_TIME WHERE ACTIVITY_CODE=?";
            log.debug(">>deleteCampSegInfo() 删除营销活动执行时间表 : {}", sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });
            // 删除活动信息
            sql = "delete from mtl_camp_seginfo where campseg_id=?";
            log.debug(">>deleteCampSegInfo() 27. : {}", sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            // 删去位置营销数据；
            sql = "delete from  mtl_campseg_realtime_area where campseg_id =?";
            log.debug(">>deleteCampSegInfo() 28. : {}", sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });
            // 删去规则参数；
            sql = "delete from Mtl_campseg_realtime_rule where campseg_id =?";
            log.debug(">>deleteCampSegInfo() 28. : {}", sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            // 删去营销营销活动信息；
            sql = "delete from mtl_camp_realtime_list where campseg_id =?";
            log.debug(">>deleteCampSegInfo() 28. : {}", sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });
            
            //浙江版，策略与业务状态可多选，关联删除
            if ("zhejiang".equalsIgnoreCase(Configure.getInstance().getProperty("PROVINCE"))) {
                sql = "delete from mtl_campseg_drv_rel where campseg_id = ?";
                this.getJdbcTemplate().update(sql, new Object[] { campsegId });
            }
            
            // 删除活动用户表
            try {
                String custListTableName = segInfo.getCustListTabName();
                if (custListTableName != null && !"".equals(custListTableName)) {
                    sql = "drop table " + custListTableName;
                    log.debug(">>deleteCampSegInfo() 000. : {}", sql);
                    this.getJdbcTemplate().update(sql);
                }
            } catch (Exception e) {
                // 用户表有可能不存在，错误暂不做处理
                // log.error("活动用户表不存在", e);
            }
        } catch (Exception e) {
            throw e;
        } finally {

        }
    }

    /**
     * 删除活动关联的模版信息
     *
     * @param sqlca
     * @param campsegId
     * @param templetIds
     * @throws Exception
     */
    private void deleteCampsegTemplets(String campsegId, String templetIds) throws Exception {
        StringBuffer sql;
        // 删除活动模版下的活动专用子模版
        if (templetIds.length() > 0) {
            // 删除活动专用筛选模版
            sql = new StringBuffer("delete from MTL_templet_select where active_templet_id in (");
            sql.append(templetIds)
                    .append(") and seltemplet_type=3")
                    .append(" and select_templet_id in (select proform_result from mtl_campseg_progress where campseg_id='")
                    .append(campsegId).append("' and step_id=").append(MpmCONST.MPM_SYS_ACTSTEP_DEF_CAMP_SELECT + ")");
            log.debug(">>deleteCampSegInfo() 2. : {}", sql);
            this.getJdbcTemplate().update(sql.toString());

            // 删除活动专用剔除模版
            sql = new StringBuffer("delete from MTL_templet_filter where active_templet_id in (")
                    .append(templetIds)
                    .append(") and ftemplet_type=3")
                    .append(" and filter_templet_id in (select proform_result from mtl_campseg_progress where campseg_id='")
                    .append(campsegId).append("' and step_id=").append(MpmCONST.MPM_SYS_ACTSTEP_DEF_CAMP_FILTER)
                    .append(")");
            log.debug(">>deleteCampSegInfo() 3. : {}", sql);
            this.getJdbcTemplate().update(sql.toString());

            // 删除活动专用分组模版
            sql = new StringBuffer("delete from MTL_templet_subsection where active_templet_id in (")
                    .append(templetIds)
                    .append(") and stemplet_type=3")
                    .append(" and subsection_templet_id in (select rule_ident from MTL_filter_rule_combination where campseg_id='")
                    .append(campsegId).append("')");
            log.debug(">>deleteCampSegInfo() 4. : {}", sql);
            this.getJdbcTemplate().update(sql.toString());
        }

        // 删除活动剔除规则
        String sqlTemp = "delete from MTL_filter_rule_combination where campseg_id=?";
        log.debug(">>deleteCampSegInfo() 5. : {}", sqlTemp);
        this.getJdbcTemplate().update(sqlTemp, new Object[] { campsegId });

        // 删除活动分组规则
        sqlTemp = "delete from MTL_subsection_rule_comb where campseg_id=?";
        log.debug(">>deleteCampSegInfo() 6. : {}", sqlTemp);
        this.getJdbcTemplate().update(sqlTemp, new Object[] { campsegId });

        // 判断哪些活动模版是活动专用模版，并且没有被其他波次使用，则可以删除
        String aTempletIds = "";
        if (templetIds.length() > 0) {
            sql = new StringBuffer(
                    "select active_templet_id from MTL_templet_active where atemplet_type=3 and active_templet_id in (")
                    .append(templetIds)
                    .append(")")
                    .append(" and active_templet_id not in (select proform_result from mtl_campseg_progress where campseg_id <> '")
                    .append(campsegId).append("' and step_id=").append(MpmCONST.MPM_SYS_ACTSTEP_DEF_ACTIVE_TEMPLET)
                    .append(")");
            log.debug(">>deleteCampSegInfo() 7. : {}", sql);
            List list = this.getJdbcTemplate().queryForList(sql.toString());
            for(int i = 0 ;i < list.size() ; i ++){
                Map map = (Map)list.get(i);
                aTempletIds += "'" + map.get("active_templet_id").toString() + "',";
            }
            if (aTempletIds.length() > 0) {
                aTempletIds = aTempletIds.substring(0, aTempletIds.length() - 1);
            }
        }
        if (aTempletIds.length() > 0) {
            // 删除活动专用模版下的筛选模版
            sql = new StringBuffer("delete from MTL_templet_select where active_templet_id in (").append(aTempletIds)
                    .append(")");
            log.debug(">>deleteCampSegInfo() 8. : {}", sql);
            this.getJdbcTemplate().update(sql.toString());

            // 删除活动专用模版下的剔除模版
            sql = new StringBuffer("delete from MTL_templet_filter where active_templet_id in (").append(aTempletIds)
                    .append(")");
            log.debug(">>deleteCampSegInfo() 9. : {}", sql);
            this.getJdbcTemplate().update(sql.toString());

            // 删除活动专用模版下的分组模版
            sql = new StringBuffer("delete from MTL_templet_subsection where active_templet_id in (").append(
                    aTempletIds).append(")");
            log.debug(">>deleteCampSegInfo() 10. : {}", sql);
            this.getJdbcTemplate().update(sql.toString());

            // 删除活动专用模版下的字段信息
            sql = new StringBuffer("delete from MTL_templet_active_field where active_templet_id in (").append(
                    aTempletIds).append(")");
            log.debug(">>deleteCampSegInfo() 11. : {}", sql);
            this.getJdbcTemplate().update(sql.toString());

            // 删除活动专用模版
            sql = new StringBuffer("delete from MTL_templet_active where atemplet_type=3 and active_templet_id in (")
                    .append(aTempletIds).append(")");
            log.debug(">>deleteCampSegInfo() 12. : {}", sql);
            this.getJdbcTemplate().update(sql.toString());
        }
    }

    /**
     * 删除活动关联的营销策划信息
     *
     * @param sqlca
     * @param campsegId
     * @throws Exception
     */
    private void deleteCampsegPlanRules(String campsegId) throws Exception {
        String sql;
        // 删除活动相关的营销设计信息
        if (campsegId.length() > 0) {
            // 删除短信彩信交互代码表
            sql = "delete from MTL_sms_mms_code_def where campseg_id=?";
            log.debug(">>deleteCampSegInfo() 13. : {}", sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            /*
             * //删除营销规则定义的活动实体成本计划 sql =
             * "delete from mtl_resource_cost_plan where cost_no in (select cost_no from mtl_channel_cost where campseg_id='"
             * + campsegId + "')"; log.debug(">>deleteCampSegInfo() 19." + sql);
             * sqlca.execute(sql);
             *
             * //删除宣传设计的活动实体成本计划 sql =
             * "delete from mtl_resource_cost_plan where cost_no in (select cost_no from mtl_publicize_reslist where campseg_id='"
             * + campsegId + "')"; log.debug(">>changeCampaignFlow() 19.1 " +
             * sql); sqlca.execute(sql);
             */

            // 删除渠道成本
            sql = "delete from mtl_channel_cost where campseg_id=?";
            log.debug(">>deleteCampSegInfo() 20. : {}", sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            // 删除营销渠道定义信息
            sql = "delete from mtl_channel_def where campseg_id=?";
            log.debug(">>deleteCampSegInfo() 20.1  : {}", sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            // 删除客户营销渠道设计
            sql = "delete from MTL_userseg_channel_plan where campseg_id=?";
            log.debug(">>deleteCampSegInfo() 14. : {}", sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            // 删除细分执行结果
            sql = "delete from MTL_subsection_result where campseg_id=?";
            log.debug(">>deleteCampSegInfo() 15. : {}", sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            /*
             * //删除用户筛选过程情况表 sql =
             * "delete from mtl_userfilter_process where campseg_id='" +
             * campsegId + "'"; log.debug(">>deleteCampSegInfo() 16." + sql);
             * sqlca.execute(sql);
             *
             *
             * //删除外部宣传方案物品 sql =
             * "delete from mtl_publicize_reslist where campseg_id='" +
             * campsegId + "'"; log.debug(">>deleteCampSegInfo() 17." + sql);
             * sqlca.execute(sql);
             */
        }
    }
    /**
     * 修改策略完成时间（延期）
     * @param campsegId
     * @param endDate
     */
    @Override
    public void updateCampSegInfoEndDate(String campsegId, String endDate) {
        String sql = "update mtl_camp_seginfo set end_date = ? where campseg_id=?";
        this.getJdbcTemplate().update(sql, new Object[] { endDate,campsegId });
    }

}
