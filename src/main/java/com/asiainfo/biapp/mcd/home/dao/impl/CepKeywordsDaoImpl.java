package com.asiainfo.biapp.mcd.home.dao.impl;

//import java.io.Serializable;
//import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.hibernate.HibernateException;
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.criterion.Order;
//import org.hibernate.criterion.Projections;
//import org.hibernate.criterion.Restrictions;
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.orm.hibernate3.HibernateCallback;
//import org.springframework.orm.hibernate3.HibernateTemplate;
//import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.home.dao.ICepKeywordsDao;

//import com.asiainfo.biapp.mcd.bean.zhejiang.PageResult;
//import com.asiainfo.biapp.mcd.dao.cep.ICepKeywordsDao;
//import com.asiainfo.biapp.mcd.echartBean.Constants;
//import com.asiainfo.biapp.mcd.model.CepKeyword;
//import com.asiainfo.biapp.mcd.model.CepKeywordsPkg;
//import com.asiainfo.biframe.exception.DaoException;

@Repository("CepKeywordsDao")
@SuppressWarnings("unchecked")
public class CepKeywordsDaoImpl extends JdbcDaoBase implements ICepKeywordsDao {

	public static Logger log = LogManager.getLogger();

//	@Override
//	public List<CepKeywordsPkg> findHot() {
//		try {
//			String sql = "from CepKeywordsPkg t where t.isSystem = ? order by t.orderNum";
//			return this.getHibernateTemplate().find(sql,new Object[] { Constants.CEP_IN_MCD.IS_SYSTEM });
//		} catch (DataAccessException e) {
//			e.printStackTrace();
//			return new ArrayList<CepKeywordsPkg>();
//		}
//	}
//
//	@Override
//	public CepKeywordsPkg getById() {
//		return null;
//	}
//
//	@Override
//	public List<CepKeywordsPkg> findHot(PageResult pr) {
//		try {
//			return this.getSession().createCriteria(CepKeywordsPkg.class).add(Restrictions.eq(CepKeywordsPkg.COL_ISSYSTEM, Constants.CEP_IN_MCD.IS_SYSTEM)).addOrder(Order.asc(CepKeywordsPkg.COL_PACK_ID)).setFirstResult(pr.getFirstResult()).setMaxResults(pr.getMaxResult()).list();
//		} catch (DataAccessException e) {
//			e.printStackTrace();
//			return new ArrayList<CepKeywordsPkg>();
//		}
//	}
//
//	@Override
//	public List<CepKeywordsPkg> findUsual(String userId, PageResult pr) {
//		try {
//
//			return this
//					.getSession()
//					.createCriteria(CepKeywordsPkg.class)
//					.add(Restrictions.eq(CepKeywordsPkg.COL_ISSYSTEM,Constants.CEP_IN_MCD.IS_USUALLY))
//					.add(Restrictions.eq(CepKeywordsPkg.COL_USERID, userId))
//					.addOrder(Order.asc(CepKeywordsPkg.COL_PACK_ID))
//					.setFirstResult(pr.getFirstResult())
//					.setMaxResults(pr.getMaxResult()).list();
//			
//		} catch (DataAccessException e) {
//			e.printStackTrace();
//			return new ArrayList<CepKeywordsPkg>();
//		}
//	}
//
//	@Override
//	public boolean save(List<CepKeyword> words) {
//		boolean flag = true;
//		for (CepKeyword cepKeyword : words) {
//			try {
//				this.getHibernateTemplate().save(cepKeyword);
//			} catch (DataAccessException e) {
//				e.printStackTrace();
//				flag = false;
//			}
//		}
//		return flag;
//	}
//
//	@Override
//	public Serializable save(CepKeywordsPkg wordsPkg) {
//		HibernateTemplate ht = getHibernateTemplate();
//		ht.setFlushMode(HibernateTemplate.FLUSH_EAGER);
//		return ht.save(wordsPkg);
//	}
//	
//	@Override
//	public void delById(final String wpId) {
//		final String hql = "delete  CepKeywordsPkg ckp where ckp.pkgId=?";
//		try {
//			getHibernateTemplate().execute(new HibernateCallback() {
//				@Override
//				public Object doInHibernate(Session session) throws HibernateException, SQLException {
//					Query query = session.createQuery(hql);
//					query.setString(0, wpId);
//					int affectRecords = query.executeUpdate();
//					return affectRecords;
//				}
//
//			});
//		} catch (DataAccessException ex) {
//			logger.error("查询时发生错误");
//			throw ex;
//		}
//	}
//
//	private JdbcTemplate jdbcTemplate;
//
//	@Override
//	public Map<String, Integer> getRange(int pkgType) throws DaoException {
//		String sql = "SELECT T.ID, T.MIN_QTT, T.MAX_QTT FROM DIM_KEYWORDS_QUANTITY T WHERE T.PKG_TYPE = ?";
//
//		Map<String, Integer> result = jdbcTemplate.queryForMap(sql, new Object[] { pkgType });
//		// super.getHibernateTemplate().execute(new HibernateCallback() {
//
//		// @Override
//		// public Object doInHibernate(Session s) throws HibernateException,
//		// SQLException {
//		// Map<String, Integer> result = new HashMap<String, Integer>();
//		// SQLQuery query = s.createSQLQuery(sql);
//		// query.setInteger(0, pkgType);
//		// Object obj = query.uniqueResult();
//		// return null;
//		// }
//		// });
//		return result;
//	}
//
//	@Override
//	public CepKeywordsPkg getById(String pkgId) {
//		return (CepKeywordsPkg) super.getHibernateTemplate().get(
//				CepKeywordsPkg.class, pkgId);
//	}
//
//	public JdbcTemplate getJdbcTemplate() {
//		return jdbcTemplate;
//	}
//
//	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//		this.jdbcTemplate = jdbcTemplate;
//	}
//
//	@Override
//	public List<CepKeywordsPkg> findUsual(String userId) throws DaoException {
//		String sql = "from CepKeywordsPkg t where t.isSystem = ? and t.userId = ? order by t.orderNum";
//		return this.getHibernateTemplate().find(sql,
//				new Object[] { Constants.CEP_IN_MCD.IS_USUALLY, userId });
//	}
//
//	@Override
//	public int findTotalCount(String userId) {
//		return (Integer) super.getSession().createCriteria(CepKeywordsPkg.class).add(Restrictions.eq(CepKeywordsPkg.COL_ISSYSTEM, Constants.CEP_IN_MCD.IS_USUALLY)).add(Restrictions.eq(CepKeywordsPkg.COL_USERID, userId)).setProjection(Projections.rowCount()).uniqueResult();
//	}
//
//	@Override
//	public int findTotalCount() {
//		return (Integer) super.getSession().createCriteria(CepKeywordsPkg.class).add(Restrictions.eq(CepKeywordsPkg.COL_ISSYSTEM, Constants.CEP_IN_MCD.IS_SYSTEM)).setProjection(Projections.rowCount()).uniqueResult();
//	}
//
	@Override
	public List<Map<String, Object>> queryData4Charts(String sql, Object[] params) {
		return this.getJdbcTemplate().queryForList(sql, params);
	}

}
