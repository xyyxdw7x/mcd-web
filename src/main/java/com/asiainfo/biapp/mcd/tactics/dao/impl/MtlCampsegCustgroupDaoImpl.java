package com.asiainfo.biapp.mcd.tactics.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.common.constants.McdCONST;
import com.asiainfo.biapp.mcd.common.custgroup.vo.McdCustgroupDef;
import com.asiainfo.biapp.mcd.tactics.dao.ICampsegCustgroupDao;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampCustgroupList;

/**
 * A data access object (DAO) providing persistence and search support for
 * MtlCampsegCiCustgroup entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 *
 * @see McdCampCustgroupList.MtlCampsegCiCustgroup
 * @author MyEclipse Persistence Tools
 */
                     
@Repository("mtlCampsegCustgroupDao")
public class MtlCampsegCustgroupDaoImpl  extends JdbcDaoBase implements ICampsegCustgroupDao {
	private static Logger log = LogManager.getLogger();
	@Override
	public void save(McdCampCustgroupList transientInstance) {
		log.debug("saving MtlCampsegCiCustgroup instance");
		try {
			this.getJdbcTemplateTool().save(transientInstance);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public boolean deleteLableByCampsegId(String campsegId) {
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("delete from MCD_TEMPLET_SELECT where active_templet_id in ( ")
				  .append(" select custgroup_id from mcd_camp_custgroup_list where campseg_id = '"+campsegId+"' and custgroup_type='CGT'")
				  .append(")");
			this.getJdbcTemplate().equals(buffer.toString());
			this.deleteMcdTempletActive(campsegId);
			this.deleteMcdTempletActiveField(campsegId);
		} catch (Exception e) {
			log.error("删除标签存在异常:"+e);
		}
		return false;
	}
	private void deleteMcdTempletActive(String campsegId){
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("delete from MCD_TEMPLET_ACTIVE where active_templet_id in ( ")
				  .append(" select custgroup_id from mcd_camp_custgroup_list where campseg_id = '"+campsegId+"' and custgroup_type='CGT'")
				  .append(")");
			this.getJdbcTemplate().equals(buffer.toString());
		} catch (Exception e) {
			log.error("删除标签存在异常:"+e);
		}
	}
	private void deleteMcdTempletActiveField(String campsegId){
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("delete from MCD_TEMPLET_ACTIVE_FIELD where select_templet_id in ( ")
				  .append(" select select_templet_id from MCD_TEMPLET_SELECT where active_templet_id in (")
				  .append("select custgroup_id from mcd_camp_custgroup_list where campseg_id = ? ))");
			this.getJdbcTemplate().update(buffer.toString(), new Object[] { campsegId });
		} catch (Exception e) {
			log.error("删除标签存在异常:"+e);
		}
	}
	
	public void deleteByCampsegId(final String campsegId) {
		final String sql = "delete from mcd_camp_custgroup_list where campseg_id = ? ";
		this.getJdbcTemplate().update(sql, new Object[] { campsegId });
	}
	
	
	@Override
	public List<McdCustgroupDef> getChoiceCustom(String campsegId) {
		List<Map<String,Object>> list = null;
		List<McdCustgroupDef> result = new ArrayList<McdCustgroupDef>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("select mcd_camp_custgroup_list.*,mcd_custgroup_def.Custom_Group_Name,mcd_custgroup_def.Custom_Num,mcd_custgroup_def.Create_User_Id,mcd_custgroup_def.Custom_Status_Id,mcd_custgroup_def.Update_Cycle from mcd_camp_custgroup_list ")
				  .append(" left join mcd_custgroup_def on mcd_camp_custgroup_list.Custgroup_Id = mcd_custgroup_def.Custom_Group_Id")
				  .append(" where  mcd_camp_custgroup_list.CAMPSEG_id = ?");
			list = this.getJdbcTemplate().queryForList(buffer.toString(),new Object[] { campsegId });
			
			for (Map<String,Object> map : list) {
				McdCustgroupDef mtlGroupInfo = new McdCustgroupDef();
				mtlGroupInfo.setCampsegCustGroupId((String) map.get("CAMPSEG_CUSTGROUP_ID"));
				mtlGroupInfo.setCustomGroupId((String) map.get("CUSTGROUP_ID"));
				mtlGroupInfo.setCreateUserId((String) map.get("CREATE_USER_ID"));
				mtlGroupInfo.setCustomGroupName((String) map.get("Custom_Group_Name"));
				if(null != map.get("CUSTOM_NUM")){
					mtlGroupInfo.setCustomNum(Integer.parseInt(String.valueOf(map.get("CUSTOM_NUM"))));
				}
				if(null != map.get("CUSTOM_STATUS_ID")){
					mtlGroupInfo.setCustomStatusId(Integer.parseInt(String.valueOf(map.get("CUSTOM_STATUS_ID"))));
				}
				if(null != map.get("UPDATE_CYCLE")){
					mtlGroupInfo.setUpdateCycle(Integer.parseInt(String.valueOf(map.get("UPDATE_CYCLE"))));
				}
				result.add(mtlGroupInfo);
			}
		} catch (Exception e) {
			log.error("",e);
		}
		return result;
	}
	
	@Override
	public List<Map<String,Object>> getCustInfoByCampsegId(String campsegId) {
		List<Map<String,Object>> list = null;
		try {
			StringBuffer sql = new StringBuffer(" select *  from mcd_camp_custgroup_list mcc where mcc.CAMPSEG_ID = ?"); 
			list= this.getJdbcTemplate().queryForList(sql.toString(), new Object[] { campsegId,});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
    /**
     * 根据客户群ID查找用到该客户群的处于（待执行）的策略
     * @param customGroupId
     * @return 
     */
    @Override
    public List<Map<String,Object>> getCustGroupSelectByGroupIdList(String customGroupId) {
        List<Map<String,Object>>  list = new ArrayList<Map<String,Object>> ();
        try {
            //改变查询策略的方式，查询策略状态不为已完成和终止并且在有效期内的策略 , 不为已完成和终止的周期性策略当有新的客户群推送过来的时候都要去更新D表数据    modify by lixq10 2015年12月16日14:08:18
            StringBuffer buffer = new StringBuffer();
            buffer.append("select mcc.*  from mcd_camp_custgroup_list mcc  ")
                  .append("  left join mcd_camp_def mcs on mcc.CAMPSEG_ID = mcs.campseg_id ")
                  .append(" where  mcc.CUSTGROUP_ID = ?  ")
                  .append(" and mcs.campseg_stat_id not in(?,?)")
                  .append(" AND SYSDATE BETWEEN nvl2(TO_DATE(mcs.start_date,'YYYY-MM-DD'),TO_DATE(mcs.start_date,'YYYY-MM-DD'),TO_DATE('19000101','YYYY-MM-DD'))  AND nvl2(TO_DATE(mcs.end_date,'YYYY-MM-DD'),TO_DATE(mcs.end_date,'YYYY-MM-DD'),TO_DATE('21000101','YYYY-MM-DD'))");
            list= this.getJdbcTemplate().queryForList(buffer.toString(), new Object[] { customGroupId,McdCONST.MPM_CAMPSEG_STAT_HDWC,McdCONST.MPM_CAMPSEG_STAT_HDZZ});
            
        } catch (Exception e) {
            
        }
        return list;
    }
    /**
     * 根据策略ID及类型，查找对应客户群或时机规则信息
     * @param campsegId
     * @return
     */
    @Override
    public List<Map<String, Object>> getMtlCampsegCustGroup(String campsegId) {
        String sql = "select * from mcd_camp_custgroup_list mcc where mcc.CAMPSEG_ID =?";
        List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql,new Object[]{campsegId});
        return list;

    }
}