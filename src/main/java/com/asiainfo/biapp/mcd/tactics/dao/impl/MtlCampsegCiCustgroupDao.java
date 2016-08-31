package com.asiainfo.biapp.mcd.tactics.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.tactics.dao.IMtlCampsegCiCustDao;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampsegCiCustgroup;

/**
 * A data access object (DAO) providing persistence and search support for
 * MtlCampsegCiCustgroup entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 *
 * @see hibernate.MtlCampsegCiCustgroup
 * @author MyEclipse Persistence Tools
 */
                     
@Repository("mtlCampsegCiCustDao")
public class MtlCampsegCiCustgroupDao  extends JdbcDaoBase implements IMtlCampsegCiCustDao {
	private static Logger log = LogManager.getLogger();
	@Override
	public void save(MtlCampsegCiCustgroup transientInstance) {
		log.debug("saving MtlCampsegCiCustgroup instance");
		try {
			//TODO: getHibernateTemplate().saveOrUpdate(transientInstance);
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
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
}