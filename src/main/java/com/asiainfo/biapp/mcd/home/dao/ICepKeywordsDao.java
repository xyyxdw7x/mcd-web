package com.asiainfo.biapp.mcd.home.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

//import com.asiainfo.biapp.mcd.bean.zhejiang.PageResult;
//import com.asiainfo.biapp.mcd.model.CepKeyword;
//import com.asiainfo.biapp.mcd.model.CepKeywordsPkg;
//import com.asiainfo.biframe.exception.DaoException;

public interface ICepKeywordsDao {

//	List<CepKeywordsPkg> findHot() throws DaoException;
//
//	CepKeywordsPkg getById() throws DaoException;
//
//	List<CepKeywordsPkg> findUsual(String userId) throws DaoException;
//
//	boolean save(List<CepKeyword> words) throws DaoException;
//	
//	Serializable save(CepKeywordsPkg wordsPkg) throws DaoException;
//	
//	public Map<String, Integer> getRange(int pkgType) throws DaoException;
//
//	CepKeywordsPkg getById(String pkgId);
//
//	List<CepKeywordsPkg> findUsual(String userId, PageResult pr);
//
//	int findTotalCount(String userId);
//
//	int findTotalCount();
//
//	List<CepKeywordsPkg> findHot(PageResult pr);

	List<Map<String, Object>> queryData4Charts(String string, Object[] objects);

//	void delById(final String wpId);
}
