package com.asiainfo.biapp.mcd.tactics.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.framework.jdbc.VoPropertyRowMapper;
import com.asiainfo.biapp.mcd.common.constants.MpmCONST;
import com.asiainfo.biapp.mcd.common.util.DataBaseAdapter;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.tactics.dao.IMpmCampSegInfoDao;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampDrvType;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampsegStat;
import com.asiainfo.biapp.mcd.tactics.vo.McdApproveLog;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampDef;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampsegCustgroup;
import com.asiainfo.biframe.utils.config.Configure;
import com.asiainfo.biframe.utils.string.StringUtil;
/**
 * 策略管理相关dao
 * @author AsiaInfo-jie
 *
 */
@Repository("mpmCampSegInfoDao")
public class MpmCampSegInfoDaoImpl extends JdbcDaoBase  implements IMpmCampSegInfoDao {
	protected final Log log = LogFactory.getLog(getClass());
    @Override
    public List searchIMcdCampsegInfo(McdCampDef segInfo, Pager pager) {
        List parameterList = new ArrayList();
        JdbcTemplate jt = this.getJdbcTemplate();
        StringBuffer buffer = new StringBuffer();
        buffer.append("select msi.campseg_name as  \"campsegName\",msi.campseg_id as \"campsegId\" ,msi.start_date as \"startDate\",msi.end_date as \"endDate\",msi.campseg_stat_id as \"campsegStatId\",dcs.campseg_stat_name as \"campsegStatName\",create_username as \"createUserName\",decode(msi.create_userid,'")
        .append(segInfo.getCreateUserid())
        .append("',0,1) as \"isMy\",dcs.CAMPSEG_STAT_SITEID as \"campsegStatSiteId\",msi.pause_comment as  \"pauseComment\" ");
        
        if(segInfo.getIsSelectMy() != null && segInfo.getIsSelectMy() == 0){
            buffer.append(",(CASE  WHEN msi.campseg_stat_id = '54' AND TO_NUMBER(TO_DATE(msi.end_date, 'YYYY-MM-DD') - sysdate) * 24 <= 72 THEN 1 ELSE 0 END) AS \"DQ_1\",")
            .append("(CASE WHEN msi.campseg_stat_id = '40' AND TO_NUMBER(TO_DATE(msi.start_date, 'YYYY-MM-DD') - sysdate) * 24 <= 24 THEN 1 ELSE 0 END) AS \"DQ_2\",")
            .append("(CASE WHEN msi.campseg_stat_id IN ('40', '54', '59', '71') AND TO_NUMBER((select MIN(PLAN_ENDDATE) from mcd_plan_def where PLAN_ENDDATE IS NOT NULL AND PLAN_ID = msi.Plan_Id) - sysdate) * 24 <= 72 THEN 1 ELSE 0 END) AS \"DQ_3\",")
            .append("(CASE WHEN msi.campseg_stat_id = '54' THEN TRUNC(TO_DATE(msi.end_date, 'YYYY-MM-DD') - trunc(sysdate ,'dd')) ELSE 0 END) AS \"DQ_D_1\",")
            .append("(CASE WHEN msi.campseg_stat_id = '40' THEN TRUNC(TO_DATE(msi.start_date, 'YYYY-MM-DD') - trunc(sysdate ,'dd')) ELSE 0 END) AS \"DQ_D_2\",")
            .append("(CASE WHEN msi.campseg_stat_id IN ('40', '54', '59', '71') THEN TRUNC((select MIN(PLAN_ENDDATE) from mcd_plan_def where PLAN_ENDDATE IS NOT NULL AND PLAN_ID = msi.Plan_Id) - trunc(sysdate ,'dd')) ELSE 0 END) AS \"DQ_D_3\" ") ;
        } 
        
        buffer.append(" from mcd_camp_def msi ")
               .append(" left join mcd_dim_camp_status dcs on msi.campseg_stat_id = dcs.campseg_stat_id ")
              .append("   where 1=1 ")
              .append("   and msi.campseg_pid ='0' ")
              .append("   and (msi.camp_class is null or msi.camp_class =1) ");
              
              
            //业务状态改为多选
       /* if (segInfo.getCampDrvIds() != null && !"".equals(segInfo.getCampDrvIds())) {
            buffer.append("  and  msi.campseg_id in (select mcdr.campseg_id from mtl_campseg_drv_rel mcdr where ")
            .append(" mcdr.camp_drv_id in ( "+ segInfo.getCampDrvIds() +")")
            .append(")");
        }*/
        
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
                  .append(" select unique mcs.campseg_pid from mcd_camp_def mcs")
                  .append(" left join mcd_camp_channel_list mcd on mcs.campseg_id=mcd.campseg_id")
                  .append(" where mcd.channel_id=? and mcs.campseg_pid<>'0')");
            parameterList.add(segInfo.getChannelId());
        }
        //省公司还是分公司人查看
     /*   if(StringUtil.isNotEmpty(segInfo.getAreaId())){
            buffer.append(" and msi.area_id= ?");
            parameterList.add(segInfo.getAreaId());
        }*/
        
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
        String sql="select * from mcd_dim_camp_status where 1=1 and CAMPSEG_STAT_VISIBLE = 0 order by CAMPSEG_STAT_SITEID";
        List<DimCampsegStat> list = this.getJdbcTemplate().query(sql,new VoPropertyRowMapper<DimCampsegStat>(DimCampsegStat.class));
        return list;
    }
	@Override
	public void updateCampsegInfo(McdCampDef segInfo) {
		//TODO:this.getHibernateTemplate().saveOrUpdate(segInfo);
	}
	/**
	 * 保存活动信息
	 */
	@Override
	public Serializable saveCampSegInfo(McdCampDef segInfo) throws Exception {
		//TODO:this.getHibernateTemplate().saveOrUpdate(segInfo);
		this.getJdbcTemplateTool().save(segInfo);
		return segInfo.getCampsegId();
	}
	/**
	 * 根据活动编号取活动信息
	 */
	@Override
	public McdCampDef getCampSegInfo(String campSegId) throws Exception {
        McdCampDef obj = null;
        try {
            final String sql = "select * from mcd_camp_def seginfo where seginfo.campseg_id = ? ";
            Object[] args=new Object[]{campSegId};
            int[] argTypes=new int[]{Types.VARCHAR};
            List<McdCampDef> list = this.getJdbcTemplate().query(sql,args,argTypes,new VoPropertyRowMapper<McdCampDef>(McdCampDef.class));
            if(list != null && list.size() > 0){
                obj = list.get(0);
            }
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
        final String sql = "select * from mcd_camp_def seginfo where seginfo.campseg_pid = ? order by CREATE_TIME asc ";
        Object[] args=new Object[]{campsegId};
        int[] argTypes=new int[]{Types.VARCHAR};
        List<McdCampDef> list = this.getJdbcTemplate().query(sql,args,argTypes,new VoPropertyRowMapper<McdCampDef>(McdCampDef.class));
        return list;
	}
	/**
	 * 更新活动信息
	 */
	@Override
	public void updateCampSegInfo(McdCampDef segInfo) throws Exception {
		//TODO: this.getHibernateTemplate().update(segInfo);
	    final String sql = "update mcd_camp_def set campseg_name=?,campseg_no=?,start_date=?,end_date=?,CAMPSEG_STAT_ID=?,campseg_type_id=?,camp_pri_id=?,approve_flow_id=?,"
	                    + "create_username=?,create_userid=?,city_id=?,deptid=?,create_time=?,PLAN_ID=?,"
	                    + "campseg_pid=?,camp_class=?,targer_user_nums=?,"
	                    + "init_cust_list_tab = ?,event_rule_desc=?,approve_remind_time=?,is_filter_disturb=? where campseg_id = ?";
	    this.getJdbcTemplate().update(sql);
	    Object[] objects = new Object[]{segInfo.getCampsegName(),segInfo.getCampsegNo(),segInfo.getStartDate(),segInfo.getEndDate(),segInfo.getCampsegStatId(),
	                    segInfo.getCampsegTypeId(),segInfo.getCampPriId(),segInfo.getApproveFlowid(),
	                    segInfo.getCreateUserName(),segInfo.getCreateUserid(),segInfo.getCityId(),segInfo.getDeptId(),segInfo.getCreateTime(),
	                    segInfo.getPlanId(),segInfo.getCampsegPid(),
	                    segInfo.getCampClass(),segInfo.getTargerUserNums(),
	                    segInfo.getInitCustListTab(),segInfo.getEventRuleDesc(),segInfo.getApproveRemindTime(),
	                    segInfo.getIsFileterDisturb(),segInfo.getCampsegId()};
	   
        this.getJdbcTemplate().update(sql,objects);

	}
	
    @Override
    public List getCustGroupSelectList(String campsegId) {
     // TODO 自动生成方法存根
        String sql="select * from mcd_camp_custgroup_list where CAMPSEG_ID = ?";
        Object[] args=new Object[]{campsegId};
        int[] argTypes=new int[]{Types.VARCHAR};
        List<MtlCampsegCustgroup> list = this.getJdbcTemplate().query(sql,args,argTypes,new VoPropertyRowMapper<MtlCampsegCustgroup>(MtlCampsegCustgroup.class));
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
        final String sql = " SELECT mcs.campseg_id from mcd_camp_def mcs WHERE mcs.campseg_pid='" + campSegId + "'";
        List list = this.getJdbcTemplate().queryForList(sql);
        List<String> listStr = new ArrayList<String>();
        for(int i = 0 ; i < list.size() ; i ++){
            Map map = (Map)list.get(i);
            String campseg_id = map.get("campseg_id").toString();
            listStr.add(campseg_id);
        }
        return listStr;

    }
    
    /**
     * 根据活动编号删除活动信息
     */
    @Override
    public void deleteCampSegInfo(McdCampDef segInfo) throws Exception {
        String campsegId = segInfo.getCampsegId();
        try {
            // 1 取活动下属活动在属性设置阶段设置的活动模版编号
            String sql = "select t2.proform_result from mtl_campseg_progress t2 where t2.campseg_id=? and t2.step_id=?";
            log.debug(sql);
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
            log.debug(sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });
            // 删除活动的步骤信息
            sql = "delete from mtl_campseg_progress where campseg_id=?";
            log.debug(sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });
            // 删除活动相关的外部评估链接
            sql = "delete from MTL_EXT_EVALUATION_URL where camp_id=?";
            log.debug(sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            // 删除活动单列分析数据
            sql = "delete from mtl_analyse_single where campseg_id=?";
            log.debug(sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            // 删除活动的目标客户群运算设置
            sql = "delete from MTL_AGGREGATE_SELECT where campseg_id=?";
            log.debug(sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            // 删除活动的客户群选择信息
            sql = "delete from mcd_camp_custgroup_list where campseg_id=?";
            log.debug(sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            // 删除计划执行活动表
            sql = "delete from mtl_campseg_exec_list where campseg_id=?";
            log.debug(sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });
            //
            sql = "delete from mtl_campseg_export_cond where campseg_id =?";
            log.debug(sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            //TODO 添加删除渠道 mcd_camp_channel_list
            sql = " DELETE FROM mcd_camp_channel_list WHERE campseg_id=?";
            log.debug(sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            //TODO 添加接触规则表数据删除 mcd_activity_contact_rule_def
            sql = " DELETE FROM MCD_ACTIVITY_CONTACT_RULE_DEF WHERE ACTIVITY_CODE=?";
            log.debug(sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });
            //TODO 添加营销活动执行时间表删除 mcd_activity_contact_rule_def
            sql = " DELETE FROM MTL_CAMP_EXEC_TIME WHERE ACTIVITY_CODE=?";
            log.debug(sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });
            // 删除活动信息
            sql = "delete from mcd_camp_def where campseg_id=?";
            log.debug(sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            // 删去位置营销数据；
            sql = "delete from  mtl_campseg_realtime_area where campseg_id =?";
            log.debug(sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });
            // 删去规则参数；
            sql = "delete from Mtl_campseg_realtime_rule where campseg_id =?";
            log.debug(sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            // 删去营销营销活动信息；
            sql = "delete from mtl_camp_realtime_list where campseg_id =?";
            log.debug(sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });
            
            //浙江版，策略与业务状态可多选，关联删除
            if ("zhejiang".equalsIgnoreCase(Configure.getInstance().getProperty("PROVINCE"))) {
                sql = "delete from mtl_campseg_drv_rel where campseg_id = ?";
                this.getJdbcTemplate().update(sql, new Object[] { campsegId });
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
            log.debug(sql);
            this.getJdbcTemplate().update(sql.toString());

            // 删除活动专用剔除模版
            sql = new StringBuffer("delete from MTL_templet_filter where active_templet_id in (")
                    .append(templetIds)
                    .append(") and ftemplet_type=3")
                    .append(" and filter_templet_id in (select proform_result from mtl_campseg_progress where campseg_id='")
                    .append(campsegId).append("' and step_id=").append(MpmCONST.MPM_SYS_ACTSTEP_DEF_CAMP_FILTER)
                    .append(")");
            log.debug(sql);
            this.getJdbcTemplate().update(sql.toString());

            // 删除活动专用分组模版
            sql = new StringBuffer("delete from MTL_templet_subsection where active_templet_id in (")
                    .append(templetIds)
                    .append(") and stemplet_type=3")
                    .append(" and subsection_templet_id in (select rule_ident from MTL_filter_rule_combination where campseg_id='")
                    .append(campsegId).append("')");
            log.debug(sql);
            this.getJdbcTemplate().update(sql.toString());
        }

        // 删除活动剔除规则
        String sqlTemp = "delete from MTL_filter_rule_combination where campseg_id=?";
        log.debug(sqlTemp);
        this.getJdbcTemplate().update(sqlTemp, new Object[] { campsegId });

        // 删除活动分组规则
        sqlTemp = "delete from MTL_subsection_rule_comb where campseg_id=?";
        log.debug(sqlTemp);
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
            log.debug(sql);
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
            log.debug(sql);
            this.getJdbcTemplate().update(sql.toString());

            // 删除活动专用模版下的剔除模版
            sql = new StringBuffer("delete from MTL_templet_filter where active_templet_id in (").append(aTempletIds)
                    .append(")");
            log.debug(sql);
            this.getJdbcTemplate().update(sql.toString());

            // 删除活动专用模版下的分组模版
            sql = new StringBuffer("delete from MTL_templet_subsection where active_templet_id in (").append(
                    aTempletIds).append(")");
            log.debug(sql);
            this.getJdbcTemplate().update(sql.toString());

            // 删除活动专用模版下的字段信息
            sql = new StringBuffer("delete from MTL_templet_active_field where active_templet_id in (").append(
                    aTempletIds).append(")");
            log.debug(sql);
            this.getJdbcTemplate().update(sql.toString());

            // 删除活动专用模版
            sql = new StringBuffer("delete from MTL_templet_active where atemplet_type=3 and active_templet_id in (")
                    .append(aTempletIds).append(")");
            log.debug(sql);
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
            log.debug(sql);
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
            log.debug(sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            // 删除营销渠道定义信息
            sql = "delete from mcd_camp_channel_list where campseg_id=?";
            log.debug(sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            // 删除客户营销渠道设计
            sql = "delete from MTL_userseg_channel_plan where campseg_id=?";
            log.debug(sql);
            this.getJdbcTemplate().update(sql, new Object[] { campsegId });

            // 删除细分执行结果
            sql = "delete from MTL_subsection_result where campseg_id=?";
            log.debug(sql);
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
        String sql = "update mcd_camp_def set end_date = ? where campseg_id=?";
        this.getJdbcTemplate().update(sql, new Object[] { endDate,campsegId });
    }
    /**
     * 撤销工单
     * @param campsegId  策略ID
     * @param ampsegStatId  撤消后的住哪个台
     * @param approve_desc  处理结果描述
     */
    @Override
    public void cancelAssignment(String campsegId, short ampsegStatId, String approve_desc) {
        String sql = "update mcd_camp_def set CAMPSEG_STAT_ID = ?,approve_result_desc = ?  where campseg_id=?";
        this.getJdbcTemplate().update(sql, new Object[] {ampsegStatId,approve_desc,campsegId });
        
    }
    @Override
    public void updatMtlCampSeginfoPauseComment(String campsegId, String pauseComment) {
        try {
            StringBuffer buffer = new StringBuffer();
            buffer.append(" update mcd_camp_def set pause_comment  = ? where campseg_pid = ? ")
                  .append(" or campseg_id =? ");
            this.getJdbcTemplate().update(buffer.toString(), new Object[] {pauseComment,campsegId,campsegId});
            
        } catch (Exception e) {
            log.error("更新策略状态失败！");
        }
        
    }
    /**
     * 修改营销活动任务状态
     * 2013-6-8 16:53:33
     * @author Mazh
     * @param campSegId
     * @param pType
     */
    @Override
    public void updateCampStat(List<String> rList, String type) {
        StringBuffer sqlB = new StringBuffer();
        for (String campSegId : rList) {
            sqlB = new StringBuffer();
            sqlB.append(" UPDATE mcd_camp_def A ");
            sqlB.append(" SET ");
            sqlB.append(" A.campseg_stat_id=" + type + " ");
            sqlB.append(" WHERE ");
            sqlB.append(" A.Campseg_id='");
            sqlB.append(campSegId);
            sqlB.append("'");
            this.getJdbcTemplate().execute(sqlB.toString());
            log.debug(sqlB.toString());
        }
        
    }
    /**
     * 获取有营销用语的渠道的营销用语
     * @param campsegId
     * @return
     */
    @Override
    public List getExecContentList(String campsegId) {
        String sql = "select d.channel_id,d.channel_name,s.exec_content from mcd_camp_channel_list s left join mcd_dim_channel d on s.channel_id = d.channel_id " +
        "where s.campseg_id = ?  and d.exec_content_flag = 1";
        
        return this.getJdbcTemplate().queryForList(sql, new Object[] {campsegId });
    }
    /**
     * 获取营销用语变量
     * @param campsegId
     * @return
     */
    @Override
    public List getExecContentVariableList(String campsegId) {
        String sql = "select attr_col_name,attr_col from mcd_custgroup_attr_list mgar WHERE mgar.custom_group_id = " +
        "(select mcc.CUSTGROUP_ID from mcd_camp_custgroup_list mcc where mcc.CUSTGROUP_TYPE = 'CG' and mcc.campseg_id = ?)" +
        " and mgar.list_table_name=(select max(list_table_name) from mcd_custgroup_attr_list WHERE CUSTOM_GROUP_ID=(" +
        " select mcc.CUSTGROUP_ID from mcd_camp_custgroup_list mcc where mcc.CUSTGROUP_TYPE = 'CG' and mcc.campseg_id = ?))" ;
        
        return this.getJdbcTemplate().queryForList(sql, new Object[] {campsegId,campsegId });
    }
    /**
     * 保存营销用语
     * @param campsegId
     * @param channelId
     * @param execContent
     * @param ifHasVariate 
     */
    @Override
    public void saveExecContent(String campsegId, String channelId, String execContent, String ifHasVariate) {
        String sql = "update mcd_camp_channel_list s set s.exec_content = ?,if_have_var = ? where s.campseg_id = ? and s.channel_id = ?" ;    
        this.getJdbcTemplate().update(sql, new Object[] {execContent,Integer.parseInt(ifHasVariate),campsegId,channelId });
    }
    
	@Override
	public List getSubCampsegInfo(String campsegId) {
		List list = null;
		/*try {
			StringBuffer hql = new StringBuffer(" from MtlCampSeginfo seginfo where seginfo.campsegPid = ? ");
			list = this.getHibernateTemplate().find(hql.toString(), new String[] { campsegId });
		} catch (Exception e) {
			log.error("getSubCampsegInfo({}) error:", campsegId, e);
		}*/
		return list;
	}
    /**
   * 根据营销状态ID获取营销状态
   * @param string
   * @return
   */
    @Override
    public DimCampsegStat getDimCampsegStat(String dimCampsegStatID) {
        String sql="select * from mcd_dim_camp_status where campseg_stat_ID = ?";
        Object[] args=new Object[]{dimCampsegStatID};
        int[] argTypes=new int[]{Types.VARCHAR};
        List<DimCampsegStat> list = this.getJdbcTemplate().query(sql,args,argTypes,new VoPropertyRowMapper<DimCampsegStat>(DimCampsegStat.class));
        if(list != null &&list.size() > 0){
            return list.get(0);

        }else{
            return null;
        }
    }
    
    /**
     * 获取细分规则信息（时机）
     */
    @Override
    public List<Map<String, Object>> getrule(String campsegId) {
        List<Map<String,Object>> list = new ArrayList();
        try {
            
            //edit by lixq10 begin
            StringBuffer buffer = new StringBuffer();
            buffer.append("select basicData.*，MCD_TEMPLET_ACTIVE_FIELD.ELEMENT_VALUE,MCD_TEMPLET_ACTIVE_FIELD.ELEMENT_VALUE_ID,MDA_SYS_TABLE_COLUMN.COLUMN_CN_NAME,MCD_CV_COL_DEFINE.CTRL_TYPE_ID from ( ")
                  .append(" select mts.SHOW_SQL, mts.SELECT_TEMPLET_ID from mcd_camp_custgroup_list mcc, MCD_TEMPLET_SELECT mts")
                  .append(" where mts.ACTIVE_TEMPLET_ID = mcc.CUSTGROUP_ID and mcc.campseg_Id = ?")
                  .append(" ) basicData left join MCD_TEMPLET_ACTIVE_FIELD on basicData.SELECT_TEMPLET_ID = MCD_TEMPLET_ACTIVE_FIELD.SELECT_TEMPLET_ID")
                  .append(" LEFT JOIN MDA_SYS_TABLE_COLUMN ON MDA_SYS_TABLE_COLUMN.COLUMN_ID = MCD_TEMPLET_ACTIVE_FIELD.ELEMENT_ID")
                  .append(" LEFT JOIN MCD_CV_COL_DEFINE ON MDA_SYS_TABLE_COLUMN.COLUMN_ID = MCD_CV_COL_DEFINE.ATTR_META_ID");
            //end
            list= this.getJdbcTemplate().queryForList(buffer.toString(), new String[] { campsegId });
            //return !CollectionUtils.isEmpty(list);
        } catch (Exception e) {
            log.error("", e);
        }
        return list;
    }
    /**
     * 查询本地审批日志
     * @param approveFlowid
     * @return
     */
    @Override
    public McdApproveLog getLogByFlowId(String flowId) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" select * from MCD_APPROVE_LOG where APPROVE_FLOW_ID =?");
        log.info("*****************查询审批日志："+buffer.toString());
        List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(buffer.toString(),new String[]{flowId});
        McdApproveLog mcdApproveLog = new McdApproveLog();
        for (Map map : list) {
            mcdApproveLog.setApproveFlowId((String) map.get("APPROVE_FLOW_ID"));
            mcdApproveLog.setApproveResult(String.valueOf(map.get("APPROVE_RESULT")));
        }
        return mcdApproveLog;
    }
    /**
     * 根据策略id获得策略的所有渠道
     * @param campsegId
     * @return
     */
    @Override
    public List getChannelsByCampIds(String campsegIds) {
        StringBuffer sb = new StringBuffer();
        sb.append("select distinct d.channel_id,d.channel_name from mcd_camp_channel_list c,mcd_dim_channel d where c.channel_id=d.channel_id and c.campseg_id in (").append(campsegIds).append(")");
        log.info("getChannelsByCampIds执行sql="+sb);
        return this.getJdbcTemplate().queryForList(sb.toString());
    }
    /**
     * 查询指定策略指定渠道在指定时间段内的营销情况
     * @param campsegId
     * @param channelId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List getCampChannelDetail(String campsegId, String channelId, String startDate, String endDate) {
        StringBuffer sb = new StringBuffer("SELECT  ");
        List<String> params = new ArrayList<String>();
        sb.append("DATA_DATE,")
            .append("SEND_TOTAL TARGET_NUMS,") //当天目标用户数
            .append("SEND_CNT SEND_NUMS,")//已发生量
            .append("PV_NUM  SAIL_NUMS, ")//当天营销数
            .append("PV_NUM_TOTAL CUMULAT_SAIL_NUMS,")// 累计营销数
            .append("SUCC_NUM SAILSUCC_NUMS,")//营销成功用户数
            .append("SUCC_NUM_TOTAL  CUMULAT_SAILSUCC_NUMS,")//累计营销成功数
            .append("SEND_CNT||'/'||LEFT_CNT EXCUTE_SURPLUS,")
            .append("CK_CNT CK_NUMS, ")//当天点击数
            .append("CK_CNT_TOTAL CUMULAT_CK_NUMS,")//累计点击数
            .append("PV_CNT EXPOSURE_NUMS, ")//当天曝光数
            .append("PV_CNT_TOTAL CUMULAT_EXPOSURE_NUMS ");//累计曝光数
        sb.append("FROM mcd_log_camp_exec ");
        sb.append("WHERE CAMPSEG_TYPE='1' AND CAMPSEG_ID=? AND CHANNEL_ID=? ");
        
        
        params.add(campsegId);
        params.add(channelId);
        
        if(!StringUtils.isEmpty(startDate)){
            sb.append(" AND DATA_DATE>=? ");
            params.add(startDate);
        }
       if(!StringUtils.isEmpty(endDate)){
           sb.append(" AND DATA_DATE<=? ");
           params.add(endDate);
        }
       sb.append(" ORDER BY DATA_DATE");
       
        log.info("getCampChannelDetail执行sql="+sb);
        return this.getJdbcTemplate().queryForList(sb.toString(),params.toArray());
    }
    /**
     * 查询某策略某个指定渠道的所有子策略某天的执行情况   
     * @param campsegId
     * @param channelId
     * @param statDate
     * @return
     */
    @Override
    public Map getCampChannelSituation(String campsegId, String channelId, String statDate) {
     Map map = null;
        
        StringBuffer sb = new StringBuffer("SELECT  ");
        //sb.append("DATA_DATE,")
        sb.append("PLAN_NAME,")
            .append("SEND_TOTAL TARGET_NUMS,") //目标用户数
            .append("SEND_CNT SEND_NUMS,")//已发生量
            .append("PV_NUM  SAIL_NUMS, ")//接触（营销）用户数
            .append("PV_NUM_TOTAL  CUMULAT_SAIL_NUMS,")//累计营销数
            .append("SUCC_NUM SAILSUCC_NUMS,")//营销成功用户数     
            .append("SUCC_NUM_TOTAL  CUMULAT_SAILSUCC_NUMS,")//累计营销成功数
            .append("SEND_CNT||'/'||LEFT_CNT EXCUTE_SURPLUS,")
            .append("CK_CNT CK_NUMS, ")//当天点击数
            .append("CK_CNT_TOTAL CUMULAT_CK_NUMS,")//累计点击数
            .append("PV_CNT EXPOSURE_NUMS, ")//当天曝光数
            .append("PV_CNT_TOTAL CUMULAT_EXPOSURE_NUMS ");//累计曝光数
        
        sb.append("FROM mcd_log_camp_exec ");
        sb.append("WHERE CAMPSEG_ID=? AND CHANNEL_ID=? AND DATA_DATE=? ");
        
        
        log.info("getCampChannelSituation执行sql="+sb);
        try {
            map =this.getJdbcTemplate().queryForMap(sb.toString(), new Object[]{campsegId,channelId,statDate});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return map;
    }

    /**
     * 根据工单 编号获取子策略（规则）
     */
    @Override
    public List getChildCampSeginfoByAssingId(String assing_id) {
        String sql = "select campseg_id from mcd_camp_def where approve_flow_id=? and campseg_pid != '0'";
        List list = this.getJdbcTemplate().queryForList(sql, new Object[] {assing_id });
        return list;
    }
    /**
     * 根据工单 编号获取策略（规则）
     */
    @Override
    public List getCampSegInfoByApproveFlowId(String assing_id) {
        String sql = "select start_date,end_date,campseg_id,APPROVE_FLOW_ID,CREATE_USERNAME from mcd_camp_def where approve_flow_id=? and campseg_pid = '0'";
        List list = this.getJdbcTemplate().queryForList(sql, new Object[] {assing_id });
        return list;

    }
    
    /***
	 * 通过活动ID,向上递归拿到最顶父活动
	 * @throws Exception
	 * */
	@Override
	public McdCampDef getCampsegTopId(String campsegId) throws Exception {
		McdCampDef mtlCampSeginfo = getCampSegInfo(campsegId);
		if (mtlCampSeginfo != null && !StringUtils.isEmpty(mtlCampSeginfo.getCampsegPid())) {
			if ("0".equals(mtlCampSeginfo.getCampsegPid())) {
				return mtlCampSeginfo;
			} else {
				return getCampsegTopId(mtlCampSeginfo.getCampsegPid());
			}
		}
		return null;
	}

    /**
     *  add by gaowj3 20150728
     * @Title: updateCampsegApproveStatusZJ
     * @Description:  外部审批结束后修改状态方法
     * @param @param assing_id 工单编号
     * @param approve_desc   审批结果描述
     *  @param campsegStatId   策略状态
     * @return String 
     * @throws
     */
    @Override
    public void updateCampsegApproveStatusZJ(String assing_id,String approve_desc,short approveResult, String campsegStatId) throws Exception {
        Short statid = Short.valueOf(campsegStatId);
        short state = statid.shortValue();
        String sql = "update mcd_camp_def set approve_result_desc=?,campseg_stat_id=?,approve_result = ? where approve_flow_id=? and campseg_pid = '0'";
        this.getJdbcTemplate().update(sql, new Object[] { approve_desc, Short.parseShort(campsegStatId),approveResult, assing_id }); 
    }
    
    /**
     * 通过策略编码
     * 更新策略信息表的状态
     * @throws SQLException 
     */
    @Override
    public void updateCampsegInfoState(String campseg_id, String status) {
        // TODO Auto-generated method stub
        
        String sql = " update mcd_camp_def a set a.campseg_stat_id = ? where a.campseg_id = ? or a.campseg_id =(select campseg_pid from mcd_camp_def where campseg_id = ?)";
        this.getJdbcTemplate().update(sql, new Object[] { Short.parseShort(status), campseg_id,campseg_id });
        
    }
    /**
     * 根据策略获取策略相关客户群ID
     * @param campsegId
     * @return
     */
    @Override
    public String getMtlCampsegCustGroupId(String campsegId) {
        
        String sql = "select CUSTGROUP_ID from mcd_camp_custgroup_list where campseg_id = ?";
        Map map = this.getJdbcTemplate().queryForMap(sql, new Object[] {campsegId });
        String id = null;
        if(map != null){
            id = map.get("CUSTGROUP_ID").toString();
        }
        return id;
    }

	@Override
	public List getCampsegInfoById(String campsegId){
		List<Map<String, Object>> list = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("select * from mcd_camp_def  ")
				  .append(" where campseg_id = ( select campseg_pid from mcd_camp_def where campseg_id=?)")
				  .append(" or campseg_pid = ( select campseg_pid from mcd_camp_def where campseg_id=?)");
			log.info("查询策略组："+buffer.toString());
			list = this.getJdbcTemplate().queryForList(buffer.toString(), new Object[] {campsegId,campsegId });
		} catch (Exception e) {
			log.error(e);
		}
		return list;
	}
	
	@Override
	public List getSqlFireTableColumnsInMem(String tableName) {
	    List listResult = null;
	    try {                                        
	        StringBuffer buffer = new StringBuffer();
	        buffer.append("select * from user_tab_columns where Table_Name=UPPER(?)  order by column_id");
	        listResult = this.getJdbcTemplate().queryForList(buffer.toString(), new Object[]{tableName});
	    } catch (Exception e) {
	        log.error("读取表结构异常："+e);
	    }
	    return listResult;
	}
	
	@Override
	public void excSqlInMcdAdInMem(String sqlStr) {
		if(StringUtil.isNotEmpty(sqlStr)){
			this.getJdbcTemplate().execute(sqlStr);
		}
	} 

	@Override
	public void excSqlInMcd(String sqlStr) {
		if(StringUtil.isNotEmpty(sqlStr)){
			try {
				this.getJdbcTemplate().execute(sqlStr);
			} catch (Exception e) {
				log.error(e);
			}
		}
	}
	
	@Override
	public void updateCampsegById(String campsegId,String tableName,int targetUserNum){
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" update mcd_camp_def set targer_user_nums = ?,init_cust_list_tab=? where campseg_id=? or campseg_pid=?");
			log.info("更新mcd_camp_def表sql:"+buffer.toString()+"campsegId="+campsegId);
			this.getJdbcTemplate().update(buffer.toString(), new Object[] {targetUserNum,tableName,campsegId,campsegId });
		} catch (Exception e) {
			log.error("更新策略目标客户群数量和清单表名称异常："+e);
		}
	}
	
}
