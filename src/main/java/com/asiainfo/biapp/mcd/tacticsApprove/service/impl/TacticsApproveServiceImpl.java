package com.asiainfo.biapp.mcd.tacticsApprove.service.impl;


import com.asiainfo.biapp.mcd.common.util.DataBaseAdapter;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.tacticsApprove.dao.TacticsApproveDao;
import com.asiainfo.biapp.mcd.tacticsApprove.service.TacticsApproveService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 
 * Title: 
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author zhaokai 2016-8-2 上午9:21:52
 * @version 1.0
 */
@Service("tacticsApproveService")
public class TacticsApproveServiceImpl implements TacticsApproveService {
	
	private static Logger log = LogManager.getLogger();
	

	@Resource(name = "tacticsApproveDao")
	private TacticsApproveDao tacticsApproveDao;
	
	@Override
	public List<Map<String,Object>> getTacticsApproveInfo(Pager pager) {
		
		List<Map<String,Object>> data = null;
		try {
			
			Map<String,Object> sqlClause = this.getSql(pager);
			
			String sql = sqlClause.get("sql").toString();
			String countSql = sqlClause.get("countSql").toString();
			
			data = tacticsApproveDao.searchApproveInfo(countSql);
			pager.setTotalSize(data.size());
			
			data = tacticsApproveDao.searchApproveInfo(sql);

		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	private  Map<String,Object> getSql(Pager pager){
		StringBuffer buffer = new StringBuffer("");
		Map<String,Object> result = new HashMap<String,Object>();

		buffer.append("SELECT CAMPSEG_ID,CAMPSEG_NAME,B.USERNAME,A.CREATE_TIME")
		.append(" FROM MCD_CAMP_DEF A INNER JOIN USER_USER B ON A.CREATE_USERID=B.USERID")
		.append(" WHERE CAMPSEG_STAT_ID='40' AND CAMPSEG_PID ='0'")
		.append(" ORDER BY CREATE_TIME DESC");
	
		log.info("执行sql="+buffer);
		
		String sqlExt = DataBaseAdapter.getPagedSql(buffer.toString(), pager.getPageNum(),pager.getPageSize());
		result.put("sql", sqlExt);
		result.put("countSql", buffer.toString());
		return result;
	}
}
