package com.asiainfo.biapp.mcd.tactics.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
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
}