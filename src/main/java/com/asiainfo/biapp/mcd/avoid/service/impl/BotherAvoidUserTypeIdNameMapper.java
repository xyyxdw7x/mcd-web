package com.asiainfo.biapp.mcd.avoid.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.avoid.dao.IDimBotherAvoidUserTypeDao;
import com.asiainfo.biapp.mcd.avoid.service.IMcdMtlBotherAvoidService;
import com.asiainfo.biframe.service.IdNameMapper;

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
public class BotherAvoidUserTypeIdNameMapper implements IdNameMapper {
	private static Logger log = LogManager.getLogger();

	@Resource(name="botherAvoidUserTypeDao")
	private IDimBotherAvoidUserTypeDao dao;
	
	public BotherAvoidUserTypeIdNameMapper() {
		super();
	}

	public String getNameById(Object id) {
		return null;
	}
	
	public List getAll() {
		List result = null;
		try {
			result = dao.getAllUserType();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	public List getNameListByCondition(List ids) {
		return null;
	}

	public IDimBotherAvoidUserTypeDao getDao() {
		return dao;
	}

	public void setDao(IDimBotherAvoidUserTypeDao dao) {
		this.dao = dao;
	}

}
