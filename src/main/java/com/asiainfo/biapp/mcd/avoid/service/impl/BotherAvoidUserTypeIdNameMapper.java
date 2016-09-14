package com.asiainfo.biapp.mcd.avoid.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.framework.jdbc.DimIdNameMapper;
import com.asiainfo.biapp.mcd.avoid.dao.IDimBotherAvoidUserTypeDao;

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
@Service("botherAvoidUserTypeIdNameMapper")
public class BotherAvoidUserTypeIdNameMapper implements DimIdNameMapper {
	private static Logger log = LogManager.getLogger();

	@Resource(name="botherAvoidUserTypeDao")
	private IDimBotherAvoidUserTypeDao dao;
	
	public BotherAvoidUserTypeIdNameMapper() {
		super();
	}

	@Override
	public String queryDimNameById(String id) {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> queryDimAllData() {
		List<T> result = null;
		try {
			result = (List<T>)dao.getAllUserType();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public <T> List<T> queryDimNameListByCondition(List<String> ids) {
		return null;
	}

}
