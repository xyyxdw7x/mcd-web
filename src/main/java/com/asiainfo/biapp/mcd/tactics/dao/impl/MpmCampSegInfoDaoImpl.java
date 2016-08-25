package com.asiainfo.biapp.mcd.tactics.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.common.util.DataBaseAdapter;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.tactics.dao.IMpmCampSegInfoDao;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfo;
import com.asiainfo.biframe.utils.string.StringUtil;
@Repository("mpmCampSegInfoDao")
public class MpmCampSegInfoDaoImpl extends JdbcDaoBase  implements IMpmCampSegInfoDao {

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
	@Override
	public List getChildCampSeginfo(String campsegId) throws Exception {
		final String sql = "select * from MTL_CAMP_SEGINFO seginfo where seginfo.campseg_pid = ? order by create_time asc ";
		return this.getJdbcTemplate().queryForList(sql, new Object[]{campsegId});
	}
	/**
	 * 更新活动信息
	 */
	@Override
	public void updateCampSegInfo(MtlCampSeginfo segInfo) throws Exception {
		//TODO: this.getHibernateTemplate().update(segInfo);
	}
}
