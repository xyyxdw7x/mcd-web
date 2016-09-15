package com.asiainfo.biapp.mcd.tactics.namemaper;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.tactics.dao.IDimCampsegTypeDao;
import com.asiainfo.biapp.framework.jdbc.DimIdNameMapper;

/*
 * Created on 2016-8-2 15:42:05
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author zhaokai
 * @version 1.0
 */
@Service("dimCampsegTypeIdNameMapper")
public class DimCampsegTypeIdNameMapper implements DimIdNameMapper {
	private static Logger log = LogManager.getLogger();
	
	@Resource(name="campsegTypeDao")
	private IDimCampsegTypeDao dao;

	public DimCampsegTypeIdNameMapper() {
		super();
	}
	@Override
	public String queryDimNameById(String id) {
		return null;
	}


	@Override
	public <T> List<T> queryDimNameListByCondition(List<String> ids) {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> queryDimAllData() {
		List<T> result = null;
		try {
			result = (List<T>) dao.getAllDimCampsegType();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}
}
