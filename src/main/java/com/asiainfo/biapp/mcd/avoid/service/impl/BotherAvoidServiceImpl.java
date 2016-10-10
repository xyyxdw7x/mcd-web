package com.asiainfo.biapp.mcd.avoid.service.impl;

import com.asiainfo.biapp.mcd.avoid.dao.IMcdMtlBotherAvoidDao;
import com.asiainfo.biapp.mcd.avoid.service.IMcdMtlBotherAvoidService;
import com.asiainfo.biapp.mcd.avoid.vo.McdBotherAvoid;
import com.asiainfo.biapp.mcd.common.util.DataBaseAdapter;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.custgroup.vo.McdBotherContactConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
@Service("botherAvoidService")
public class BotherAvoidServiceImpl implements IMcdMtlBotherAvoidService {
	
	private static Logger log = LogManager.getLogger();
	
	@Resource(name = "botherAvoidDao")
	private IMcdMtlBotherAvoidDao mcdMtlBotherAvoidDao;
	
	@Override
	public List<Map<String,Object>> searchBotherAvoidUser(Pager pager, McdBotherAvoid mtlBotherAvoid) {
		
		List<Map<String,Object>> data = null;
		try {
			
			Map<String,Object> sqlClause = this.getSql(pager, mtlBotherAvoid);
			
			
			String sql = sqlClause.get("sql").toString();
			String countSql = sqlClause.get("countSql").toString();
			@SuppressWarnings("unchecked")
			List<Object> param = (List<Object>) sqlClause.get("params");
			
			data = mcdMtlBotherAvoidDao.searchBotherAvoidUser(countSql, param);
			pager.setTotalSize(data.size());
			
			data = mcdMtlBotherAvoidDao.searchBotherAvoidUser(sql, param);

		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	private Map<String,Object> getSql(Pager pager, McdBotherAvoid mtlBotherAvoid) {
		StringBuffer buffer = new StringBuffer("");
		Map<String,Object> result = new HashMap<String,Object>();
		List<Object> params = new ArrayList<Object>();

		buffer.append("SELECT A.AVOID_BOTHER_TYPE, A.AVOID_CUST_TYPE, A.PRODUCT_NO, to_char(A.ENTER_TIME, 'yyyy.mm.dd') as ENTER_TIME, A.USER_TYPE_ID, A.CREATE_USER_ID,")
		 .append(" A.CREATE_USER_NAME, B.NAME AS USER_TYPE_NAME,C.CAMPSEG_TYPE_NAME,D.CHANNEL_NAME")
		 .append(" FROM MCD_CORE_AD.mcd_bother_avoid A")
		 .append(" INNER JOIN DIM_BOTHER_AVOID_USER_TYPE B ON A.USER_TYPE_ID  = B.ID")
		 .append(" INNER JOIN mcd_dim_camp_type C ON A.AVOID_CUST_TYPE = C.CAMPSEG_TYPE_ID")
		 .append(" INNER JOIN mcd_dim_channel D ON A.AVOID_BOTHER_TYPE = D.CHANNEL_ID");
		 
		if(mtlBotherAvoid.getKeywords() != null && !"".equals(mtlBotherAvoid.getKeywords())) {
			buffer.append(" and (A.PRODUCT_NO like '%" + mtlBotherAvoid.getKeywords() + "%')");
		}
		//客户类型
		if (mtlBotherAvoid.getUserTypeId() != null && !"".equals(mtlBotherAvoid.getUserTypeId())) {
			buffer.append(" and A.USER_TYPE_ID = ? ");
			params.add(mtlBotherAvoid.getUserTypeId());
		}
		//通用渠道
		if (mtlBotherAvoid.getAvoidBotherType() != null && !"".equals(mtlBotherAvoid.getAvoidBotherType())) {
			buffer.append(" and A.AVOID_BOTHER_TYPE = ? ");
			params.add(mtlBotherAvoid.getAvoidBotherType());
		}
		//营销类型
		if (mtlBotherAvoid.getAvoidCustType() != null && !"".equals(mtlBotherAvoid.getAvoidCustType())) {
			buffer.append(" and A.AVOID_CUST_TYPE = ? ");
			params.add(mtlBotherAvoid.getAvoidCustType());
		}
		//排序字段
		if (mtlBotherAvoid.getSortColumn() != null && !"".equals(mtlBotherAvoid.getSortColumn())) {
			if ("ENTER_TIME".equals(mtlBotherAvoid.getSortColumn())) {
				buffer.append(" ORDER BY A.ENTER_TIME ");
				if ("asc".equalsIgnoreCase(mtlBotherAvoid.getSortOrderBy())) {
					buffer.append(" ASC ");
				} else if ("desc".equalsIgnoreCase(mtlBotherAvoid.getSortOrderBy())) {
					buffer.append(" DESC ");
				}
				buffer.append(" , A.PRODUCT_NO");
			}
		}
		
		log.info("执行sql="+buffer);
		
		String sqlExt = DataBaseAdapter.getPagedSql(buffer.toString(), pager.getPageNum(),pager.getPageSize());
		result.put("sql", sqlExt);
		result.put("countSql", buffer.toString());
		result.put("params", params);
		return result;
	}
	
	@Override
	public void addBotherAvoidUser(List<McdBotherAvoid> list) {
		
		try {
			
			mcdMtlBotherAvoidDao.addBotherAvoidUserInMem(list);

		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public IMcdMtlBotherAvoidDao getMcdMtlBotherAvoidDao() {
		return mcdMtlBotherAvoidDao;
	}

	public void setMcdMtlBotherAvoidDao(
			IMcdMtlBotherAvoidDao mcdMtlBotherAvoidDao) {
		this.mcdMtlBotherAvoidDao = mcdMtlBotherAvoidDao;
	}

	@Override
	public int chkIsExist(McdBotherAvoid mtl) {
		
		int isExist = 0;
		try {
			
			isExist = mcdMtlBotherAvoidDao.findBotherAvoidUserInMem(mtl);
	
		} catch(Exception e) {
			e.printStackTrace();
		}
		return isExist;
	}
	
	@Override
	public void mdfBotherAvoidUser(List<McdBotherAvoid> list) {
		
		try {
			
			mcdMtlBotherAvoidDao.updateBotherAvoidUserInMem(list);
	
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void delBotherAvoidUser(McdBotherAvoid mtl) {
		
		try {
			
			mcdMtlBotherAvoidDao.updateDelBotherAvoidUserInMem(mtl);
	
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void batchDelBotherAvoidUser(List<McdBotherAvoid> list) {
		
		try {
			mcdMtlBotherAvoidDao.updatebBatchDelBotherAvoidUserInMem(list);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public McdBotherContactConfig getMtlBotherContactConfig(String campsegTypeId,String channelId,int campsegCityType){
		return this.mcdMtlBotherAvoidDao.getBotherContactConfig(campsegTypeId, channelId,campsegCityType);
	}
}
