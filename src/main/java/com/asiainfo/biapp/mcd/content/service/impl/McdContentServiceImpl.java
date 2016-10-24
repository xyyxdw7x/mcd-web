package com.asiainfo.biapp.mcd.content.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.plan.dao.IMtlStcPlanDao;
import com.asiainfo.biapp.mcd.common.util.DataBaseAdapter;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.content.dao.IMcdContentDao;
import com.asiainfo.biapp.mcd.content.service.IMcdContentService;
import com.asiainfo.biapp.mcd.exception.MpmException;
import com.asiainfo.biapp.mcd.plan.vo.McdDimPlanOnlineStatus;

import net.sf.json.JSONObject;

@Service("mcdContentService")
public class McdContentServiceImpl implements IMcdContentService {
	protected final Log log = LogFactory.getLog(getClass());
	Random random = new Random();
	//通用dao
	@Resource(name = "mtlStcPlanDao")
	private IMtlStcPlanDao mtlStcPlanDao;
	//内容dao
	@Resource(name = "mcdContentDao")
	private IMcdContentDao mcdContentDao;

	@Override
	public List<Map<String, Object>> getContentByCondition(String statusId, String timeId, String keyWords,
			Pager pager) {
		//计算总条数
		Map<String, Object> sqlClauseCount = getContentsByConditionSqlCount(statusId, timeId, keyWords);
		String sql = sqlClauseCount.get("sql").toString();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Object> param = (List) sqlClauseCount.get("params");
		int count =  mtlStcPlanDao.execQuerySqlCount(sql, param);
		pager.setTotalSize(count);//统计总条数
				
		//主sql
		Map<String, Object> sqlClause = this.getContentsByConditionSql(statusId, timeId, keyWords, pager);
		String sql2 = sqlClause.get("sql").toString();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Object> param2 = (List) sqlClause.get("params");
		return mtlStcPlanDao.execQrySql(sql2,param2);
	}


	@Override
	public List<McdDimPlanOnlineStatus> initDimContentStatus() throws Exception {
		try {
			return mcdContentDao.initDimContentStatus();
		} catch (Exception e) {
			throw new MpmException("mcd.java.cshzclbsb");
		}
	}

	@Override
	public JSONObject queryDetail(String contentId) throws Exception {
		JSONObject dataDetail=new JSONObject();
		//详情内容类别块
		List<Map<String,Object>> queryContentStatus=this.queryContentStatus(contentId);
		//详情内容总体查询
		List<Map<String,Object>> queryContentDef=this.queryContentDef(contentId);	
		//详情内容策略块
		List<Map<String,Object>> queryCampseg = this.queryCampseg(contentId);
	
		
	    dataDetail.put("queryContentStatus",queryContentStatus);
	    dataDetail.put("queryContentDef", queryContentDef);
	    dataDetail.put("queryCampseg", queryCampseg);
		return dataDetail;
	}


	
	@Override
	public Boolean saveContent(Map saveData) {
		Map<String,Object> map = new HashMap<String,Object>();
		for(java.util.Iterator iter = saveData.entrySet().iterator();iter.hasNext();){
			Map.Entry element = (Map.Entry)iter.next();
			String strkey = element.getKey().toString();
			String[] value = (String[])element.getValue();
			String values = "";
			map.put(strkey, value[0]);
			for(int i =0;i<value.length;i++){
				values = value[0];
			}
			map.put(strkey,values);
		}	
		
		//由于传过来的value值都不是数组，所以就默认取第一个
		String contentId = (String) map.get("saveData[contentId]");
		String typeId = (String) map.get("saveData[typeId]");
		String statusId = (String) map.get("saveData[statusId]");
		
		// 更新statusId
		Boolean updateContentDef = this.updateContentDef(contentId, typeId, statusId);
		Boolean insertResult = false;
		// 对最终的反馈结果进行处理
		if (updateContentDef == true) {
			insertResult = true;
		} else {
			insertResult = false;
		}
		return insertResult;
	}
	
	
	
	/**
	 * 获取table列表数据条数
	 * @param statusId
	 * @param timeId
	 * @param keyWords
	 * @return
	 */
	private Map<String, Object> getContentsByConditionSqlCount(String statusId, String timeId, String keyWords) {
	    StringBuffer buffer= new StringBuffer("");
	    Map<String , Object> result = new HashMap<String,Object>();
	    List<Object> params = new ArrayList<Object>();
	    buffer.append("SELECT COUNT(1)  ");
		buffer.append(" FROM MCD_CONTENT_DEF A   ");
		buffer.append(" WHERE 1=1  ");
		buffer.append(" AND A.CONTENT_TYPE  = '1' ");// 内容1、应用2
	
		
		 if (StringUtils.isNotEmpty(keyWords)) { // 关键字查询
				if (keyWords.equals("%")) {
			buffer.append("  AND (A.CONTENT_NAME like ").append("'%\\%%' escape '\\'")
					.append(" OR A.CONTENT_ID like ")
							.append("'%\\%%' escape '\\')");
				} else {
			buffer.append("  AND (A.CONTENT_NAME like ?)");
					params.add("%" + keyWords + "%");
				}
			}
		 
		 
		if (StringUtils.isNotEmpty(statusId)) { // 状态类型
			buffer.append(" AND A.ONLINE_STATUS =?");
			params.add(statusId);
		}
				
		
		if (StringUtils.isNotEmpty(timeId)) { // 发布时间(首页条件选择)
			if (timeId.equals("1")) {
				// 一天内
				buffer.append(" AND (sysdate-1)<=cast(A.PUB_TIME as timestamp)");
			}
			if (timeId.equals("2")) {
				// 如果是一周内
				buffer.append(" AND (sysdate-7)<=cast(A.PUB_TIME as timestamp)");
			}
			if (timeId.equals("3")) {
				// 如果是一月内
				buffer.append(" AND (sysdate-30)<=cast(A.PUB_TIME as timestamp)");
			}
		}
					
		
	    // 内容在开始和结束之间
		buffer.append(
				" AND sysdate BETWEEN nvl2(A.PUB_TIME,A.PUB_TIME,TO_DATE('19000101','YYYYMMDD'))  AND nvl2(A.INVALID_TIME,A.INVALID_TIME,TO_DATE('21000101','YYYYMMDD'))");
		buffer.append(" AND A.STATUS=1 ");// 内容上线了
			result.put("sql", buffer.toString());
			result.put("params", params);
			return result;
    }
	
	
	
	/**
	 * 获取table列表数据
	 * @param statusId
	 * @param timeId
	 * @param keyWords
	 * @param pager
	 * @return
	 */
	private Map<String, Object> getContentsByConditionSql(String statusId, String timeId, String keyWords,
			Pager pager) {
		StringBuffer buffer = new StringBuffer("");
		String  bufferSql = "";
		Map<String,Object> result = new HashMap<String,Object>();
		List<Object> params = new ArrayList<Object>();
		buffer.append("SELECT DISTINCT A.CONTENT_ID,CONTENT_NAME,CONTENT_TYPE,C.TYPE_NAME,CONTENT_CLASS,CONTENT_SOURCE, ");
		buffer.append(" TO_CHAR(A.PUB_TIME,'YYYY-MM-DD') AS PUB_TIME,");
		buffer.append(" TO_CHAR(A.INVALID_TIME,'YYYY-MM-DD') AS INVALID_TIME,");
		buffer.append(" CASE WHEN UNIT_PRICE IS NULL THEN '0' ELSE TO_CHAR(UNIT_PRICE) END AS UNIT_PRICE,  ");
		buffer.append(" CASE WHEN A.ONLINE_STATUS IS NULL THEN '0' ELSE A.ONLINE_STATUS END AS ONLINE_STATUS, ");
		buffer.append(" CASE WHEN  B.STATUS_NAME IS NULL THEN '未上线' ELSE  B.STATUS_NAME END AS STATUS_NAME  ");
		buffer.append(" FROM MCD_CONTENT_DEF A   ");
		buffer.append(" LEFT JOIN MCD_DIM_PLAN_ONLINE_STATUS B ON B.STATUS_ID=A.ONLINE_STATUS");
		buffer.append(" LEFT JOIN MCD_DIM_CONTENT_TYPE C ON C.TYPE_ID=A.CONTENT_TYPE");
		buffer.append(" WHERE 1=1  ");
		buffer.append(" AND A.CONTENT_TYPE  = '1' ");
		 if (StringUtils.isNotEmpty(keyWords)) { // 关键字查询
				if (keyWords.equals("%")) {
				buffer.append(" AND A.CONTENT_NAME LIKE ").append("'%\\%%' escape '\\'");
				} else {
				buffer.append(" AND A.CONTENT_NAME LIKE ?");
					params.add("%" + keyWords + "%");
				}
			}
			
		if (StringUtils.isNotEmpty(statusId)) { // 状态类型
			buffer.append(" AND A.ONLINE_STATUS =?");
			params.add(statusId);
		}
		if (StringUtils.isNotEmpty(timeId)) { // 发布时间
			if (timeId.equals("1")) {
				// 如果是一天内
				buffer.append(" AND (sysdate-1)<=cast(A.PUB_TIME as timestamp)");
			}
			if (timeId.equals("2")) {
				// 如果是一周内
				buffer.append(" AND (sysdate-7)<=cast(A.PUB_TIME as timestamp)");
			}
			if (timeId.equals("3")) {
				// 如果是一月内
				buffer.append(" AND (sysdate-30)<=cast(A.PUB_TIME as timestamp)");
			}
		}
					
		log.debug("--------统计内容sql：" + buffer + "---------");
		log.debug("-----传入参数数组：" + params.toString() + "-------");
		// 内容在发布和失效之间
		buffer.append(
				" AND sysdate BETWEEN nvl2(A.PUB_TIME,A.PUB_TIME,TO_DATE('19000101','YYYYMMDD'))  AND nvl2(A.INVALID_TIME,A.INVALID_TIME,TO_DATE('21000101','YYYYMMDD'))");
		buffer.append(" AND A.STATUS=1 ");// 内容上线了
			String sqlExt = DataBaseAdapter.getPagedSql(buffer.toString(), pager.getPageNum(), pager.getPageSize());
			result.put("sql", sqlExt);
			result.put("params", params);
		    return result;
	}

	
	
	/**
	 * 查询内容详情状态
	 * @param contentId
	 * @return
	 */
	private List<Map<String, Object>> queryContentStatus(String contentId) {
		StringBuffer querySql= new StringBuffer("");
		List<Object> queryParam = new ArrayList<Object>();
		querySql.append("select a.status_id,a.status_name,  ");
		querySql.append("case when b.CONTENT_ID  is null then ' ' else b.CONTENT_ID  end as CONTENT_ID    ");
		querySql.append("from  mcd_dim_plan_online_status a  ");
		querySql.append("left join (select CONTENT_ID,ONLINE_STATUS from MCD_CONTENT_DEF where CONTENT_ID =?) b  ");
		queryParam.add(contentId);
		querySql.append(" on a.status_id = b.ONLINE_STATUS  ");
		querySql.append(" order by a.status_id ");		
		return mtlStcPlanDao.execQrySql(querySql.toString(), queryParam);
	}
	
	
	/**
	 * 查询内容详情主要内容
	 * @param contentId
	 * @return
	 */
	private List<Map<String, Object>> queryContentDef(String contentId) {
		StringBuffer querySql= new StringBuffer("");
		List<Object> queryParam = new ArrayList<Object>();
		querySql.append("SELECT   ");
		querySql.append("A.CONTENT_ID,A.CONTENT_NAME,A.CONTENT_TYPE,C.TYPE_NAME,  ");
		querySql.append("A.CONTENT_CLASS,A.CONTENT_CLASS1,A.CONTENT_CLASS2,A.CONTENT_CLASS3,A.CONTENT_CLASS4,  ");
		querySql.append("A.UNIT_PRICE,TO_CHAR(A.PUB_TIME,'yyyy-MM-dd HH:mm:ss') AS PUB_TIME,   ");
		querySql.append("TO_CHAR(A.INVALID_TIME,'yyyy-MM-dd HH:mm:ss') AS INVALID_TIME,   ");
		querySql.append("CASE WHEN A.AWARD_MOUNT IS NULL THEN 0.00 ELSE A.AWARD_MOUNT END AS AWARD_MOUNT,   ");
		querySql.append("A.CONTENT_URL,A.MANAGER,   ");
		querySql.append("CASE WHEN A.CONTENT_SOURCE IS NULL THEN '无' ELSE A.CONTENT_SOURCE END AS CONTENT_SOURCE ,  ");
		querySql.append("A.ONLINE_STATUS,B.STATUS_NAME,   ");
		querySql.append("case when A.CONTENT_REMARK is null then '无' else A.CONTENT_REMARK end as CONTENT_REMARK,  ");
		querySql.append("CASE WHEN D.CITY_ID IS NULL THEN ' ' ELSE D.CITY_ID END as CITY_ID,   ");
		querySql.append("CASE WHEN E.CITY_NAME IS NULL THEN '无' ELSE E.CITY_NAME END AS CITY_NAME   ");
		querySql.append("FROM   ");
		querySql.append("MCD_CONTENT_DEF A   ");
		querySql.append("LEFT JOIN MCD_DIM_PLAN_ONLINE_STATUS B ON A.ONLINE_STATUS = B.STATUS_ID   ");
		querySql.append("LEFT JOIN MCD_DIM_CONTENT_TYPE C ON A.CONTENT_TYPE=C.TYPE_ID   ");
		querySql.append("LEFT JOIN MCD_CONTENT_CITY_LIST D ON A.CONTENT_ID = D.CONTENT_ID   ");
		querySql.append("LEFT JOIN  MCD_DIM_CITY E ON D.CITY_ID=E.CITY_ID   ");
		querySql.append("WHERE 1=1   ");
		querySql.append("AND A.CONTENT_ID =?   ");		
		queryParam.add(contentId);	
		return mtlStcPlanDao.execQrySql(querySql.toString(), queryParam);
	}
	
	/**
	 * 详情内容策略块
	 * @param contentId
	 * @return
	 */
	private List<Map<String, Object>> queryCampseg(String contentId) {
		StringBuffer querySql= new StringBuffer("");
		List<Object> queryParam = new ArrayList<Object>();
		querySql.append("SELECT   ");
		querySql.append("A.PLAN_ID,  ");
		querySql.append("CASE WHEN A.CAMPSEG_ID IS NULL THEN '0' ELSE A.CAMPSEG_ID END AS CAMPSEG_ID,   ");
		querySql.append("CASE WHEN A.CAMPSEG_NAME IS NULL THEN '无' ELSE A.CAMPSEG_NAME END AS CAMPSEG_NAME,   ");
		querySql.append("B.CAMPSEG_STAT_NAME    ");
		querySql.append("FROM   ");
		querySql.append("MCD_CAMP_DEF A   ");
		querySql.append("LEFT JOIN MCD_DIM_CAMP_STATUS B    ");
		querySql.append("ON A.CAMPSEG_STAT_ID = B.CAMPSEG_STAT_ID   ");
		querySql.append("WHERE 1=1    ");
		querySql.append("AND A.PLAN_ID=?   ");
		queryParam.add(contentId);	
		return mtlStcPlanDao.execQrySql(querySql.toString(), queryParam);
	}

	
	/**
	 * 更新statusId
	 * @param contentId
	 *            内容编号
	 * @param typeId
	 *            类别
	 * @param statusId
	 *            状态
	 * @return
	 */
	private Boolean updateContentDef(String contentId, String typeId, String statusId) {
		StringBuffer upSql = new StringBuffer("");
		List<Object> param = new ArrayList<Object>();
		upSql.append("UPDATE MCD_CONTENT_DEF ");
		upSql.append(" SET ONLINE_STATUS=?");
		param.add(statusId);
		upSql.append(" WHERE 1=1 ");
		upSql.append(" AND CONTENT_ID =? ");
		param.add(contentId);
		upSql.append(" AND CONTENT_TYPE=? ");
		param.add(typeId);
		return mcdContentDao.updateContent(upSql.toString(), param);
	}
	
	
}