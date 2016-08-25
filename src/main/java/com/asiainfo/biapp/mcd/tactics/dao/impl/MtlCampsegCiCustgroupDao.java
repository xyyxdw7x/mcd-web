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
	
}