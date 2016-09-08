package com.asiainfo.biapp.mcd.tactics.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.common.vo.custgroup.MtlGroupInfo;
import com.asiainfo.biapp.mcd.tactics.dao.MtlCampsegCustgroupDao;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampsegCustgroup;

/**
 * A data access object (DAO) providing persistence and search support for
 * MtlCampsegCiCustgroup entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 *
 * @see MtlCampsegCustgroup.MtlCampsegCiCustgroup
 * @author MyEclipse Persistence Tools
 */
                     
@Repository("mtlCampsegCustgroupDao")
public class MtlCampsegCustgroupDaoImpl  extends JdbcDaoBase implements MtlCampsegCustgroupDao {
	private static Logger log = LogManager.getLogger();
	@Override
	public void save(MtlCampsegCustgroup transientInstance) {
		log.debug("saving MtlCampsegCiCustgroup instance");
		try {
			//TODO: getHibernateTemplate().saveOrUpdate(transientInstance);
			this.getJdbcTemplateTool().save(transientInstance);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public boolean deleteLableByCampsegId(String campsegId) {
		boolean flag = true;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("delete from MCD_TEMPLET_SELECT where active_templet_id in ( ")
				  .append(" select custgroup_id from MTL_CAMPSEG_CUSTGROUP where campseg_id = '"+campsegId+"' and custgroup_type='CGT'")
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
				  .append(" select custgroup_id from MTL_CAMPSEG_CUSTGROUP where campseg_id = '"+campsegId+"' and custgroup_type='CGT'")
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
				  .append("select custgroup_id from MTL_CAMPSEG_CUSTGROUP where campseg_id = '"+campsegId+"' and custgroup_type='CGT'))");
			this.getJdbcTemplate().equals(buffer.toString());
		} catch (Exception e) {
			log.error("删除标签存在异常:"+e);
		}
	}
	
	public void deleteByCampsegId(String campsegId) {/*
		log.debug("deleteByCampsegId MtlCampsegCiCustgroup instance");
		try {
			String sql = "delete from MtlCampsegCiCustgroup group where group.campsegId= ? ";
			this.getHibernateTemplate().deleteAll(this.getHibernateTemplate().find(sql, campsegId));
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	*/}
	
	@Override
	public List<MtlGroupInfo> getChoiceCustom(String campsegId) {
		List<Map<String,Object>> list = null;
		List<MtlGroupInfo> result = new ArrayList<MtlGroupInfo>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("select MTL_CAMPSEG_CUSTGROUP.*,MTL_GROUP_INFO.Custom_Group_Name,MTL_GROUP_INFO.Custom_Num,MTL_GROUP_INFO.Create_User_Id,MTL_GROUP_INFO.Custom_Status_Id,MTL_GROUP_INFO.Update_Cycle from MTL_CAMPSEG_CUSTGROUP ")
				  .append(" left join MTL_GROUP_INFO on MTL_CAMPSEG_CUSTGROUP.Custgroup_Id = MTL_GROUP_INFO.Custom_Group_Id")
				  .append(" where CUSTGROUP_TYPE='CG' AND MTL_CAMPSEG_CUSTGROUP.CAMPSEG_id = ?");
			list = this.getJdbcTemplate().queryForList(buffer.toString(),new Object[] { campsegId });
			
			for (Map map : list) {
				MtlGroupInfo mtlGroupInfo = new MtlGroupInfo();
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
			log.error(e);
		}
		return result;
	}
	
	@Override
	public List getCustInfoByCampsegId(String campsegId) {
		List list = new ArrayList();
		try {
			StringBuffer sql = new StringBuffer(" select *  from MTL_CAMPSEG_CUSTGROUP mcc where mcc.CAMPSEG_ID = ?"); 
			list= this.getJdbcTemplate().queryForList(sql.toString(), new Object[] { campsegId,});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}