package com.asiainfo.biapp.mcd.plan.service.impl;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.plan.dao.IMtlStcPlanDao;
import com.asiainfo.biapp.mcd.common.plan.vo.McdDimPlanType;
import com.asiainfo.biapp.mcd.common.util.DataBaseAdapter;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.exception.MpmException;
import com.asiainfo.biapp.mcd.plan.dao.IMcdPlanDao;
import com.asiainfo.biapp.mcd.plan.service.IMcdPlanService;
import com.asiainfo.biapp.mcd.plan.vo.McdDimPlanOnlineStatus;

import net.sf.json.JSONObject;

@Service("mcdPlanService")
public class McdPlanServiceImpl implements IMcdPlanService{
	private static Logger log = LogManager.getLogger();
	Random random = new Random();
	
	@Resource(name = "mtlStcPlanDao")
	private IMtlStcPlanDao mtlStcPlanDao;
	@Resource(name = "mcdPlanDao")
	private IMcdPlanDao mcdPlanDao;
	
	
	@Override
	public List<McdDimPlanOnlineStatus> initDimPlanStatus() throws Exception {
		try {
			return mcdPlanDao.initDimPlanStatus();
		} catch (Exception e) {
			throw new MpmException("mcd.java.cshzclbsb");
		}
	}

	@Override
	public List<McdDimPlanType> initDimPlanType() throws Exception {
		try{
			return mtlStcPlanDao.initDimPlanType();
		}catch(Exception e){
			throw new MpmException("mcd.java.cshzclbsb");
		}
	}
	
	
	@Override
	public List<Map<String, Object>> getPlanByCondition(String typeId, String statusId, String keyWords,
			Pager pager) {
		//计算总条数
		Map<String,Object> sqlClauseCount = getPlansByConditionSqlCount(typeId, statusId, keyWords, keyWords);
		String sql = sqlClauseCount.get("sql").toString();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Object> param = (List) sqlClauseCount.get("params");
		int count =  mtlStcPlanDao.execQuerySqlCount(sql, param);
		pager.setTotalSize(count);//统计总条数
		
		//主sql
		Map<String,Object> sqlClause = this.getPlansByConditionSql(typeId, statusId, keyWords, keyWords,pager);
		String sql2 = sqlClause.get("sql").toString();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Object> param2 = (List) sqlClause.get("params");
		return mtlStcPlanDao.execQrySql(sql2,param2);
	}

	

	@Override
	public JSONObject queryDetail(String planId) throws Exception {
		JSONObject dataDetail=new JSONObject();
		//类别查询
		List<Object> planTypes =(List)mtlStcPlanDao.initDimPlanType();
		//查询状态
		List<Map<String,Object>> planStatus =(List) mcdPlanDao.initDimPlanStatus();
		//详情内容总体查询
		List<Map<String,Object>> queryDefExt=this.queryDefExt(planId);
		//查询只用渠道
		List<Map<String,Object>> channel = this.queryChannel(planId);
		//查询营业侧归属业务类型
		List<Map<String,Object>> planBusiType = this.queryPlanBusiType(planId);
		//查询酬金积分
		List<Map<String,Object>> awardScore = this.queryAwardScore(planId);
		//查询策略
	    List<Map<String,Object>> campseg = this.queryCampseg(planId);
	    
	    dataDetail.put("queryDefExt",queryDefExt);
	    dataDetail.put("planTypes", planTypes);
	    dataDetail.put("planStatus", planStatus);
	    dataDetail.put("channel", channel);
	    dataDetail.put("planBusiType", planBusiType);
	    dataDetail.put("awardScore", awardScore);
	    dataDetail.put("campseg", campseg);
		return dataDetail;
	}
	
	@Override
	public Boolean savePlan(String planId, String typeId, String statusId, String channelId, String cityId,
			String manager, String planDesc, String planComment, String dealCode_10086, String dealCode_1008611,
			String urlForAndroid, String urlForIos, String cityIds, String scores, String awards) {
		// 更新dealCode内容快dealCode_10086,dealCode_1008611,urlForAndroid,urlForIos,MANAGER数据
				Boolean updateDealCode = this.updateDealCode(planId, dealCode_10086, dealCode_1008611, urlForAndroid, urlForIos,
						manager);

				// 更新typeId,statusId,planDesc
				Map<String, Object> updatePlanDef = this.updatePlanDef(planId, typeId, statusId, planDesc);
				String planDefSql = updatePlanDef.get("upSql").toString();
				@SuppressWarnings({ "rawtypes", "unchecked" })
				List<Object> planDefParam = (List) updatePlanDef.get("param");
				Boolean planDefaveResult = mcdPlanDao.updatePlan(planDefSql, planDefParam);

				// 更新推荐语
				Boolean updatePlanComment = this.updatePlanComment(planId, planComment);

				// 更新Channel适用渠道
				Boolean updateExecCon = this.updateExecCon(planId, channelId);

				// 更新酬金和积分
				Boolean updateScoreAward = this.updateScoreAward(planId, cityIds, scores, awards);

				log.info("dealCodeSaveResult:" + updateDealCode + ";planDefaveResult:" + planDefaveResult + ";updatePlanComment"
						+ updatePlanComment + ";updateExecCon" + updateExecCon + ";updateScoreAward:" + updateScoreAward);
				Boolean insertResult = false;

				// 对最终的反馈结果进行处理
				if (updateDealCode == true && updateExecCon == true && updateScoreAward == true && updatePlanComment == true
						&& planDefaveResult == true) {
					insertResult = true;
				} else {
					insertResult = false;
				}

				return insertResult;
	}
	
	
	
	/**
	 * 获取列表数据总条数
	 * @param typeId
	 * @param statusId
	 * @param keyWords
	 * @param keyWords2
	 * @return
	 */
	private Map<String, Object> getPlansByConditionSqlCount(String typeId, String statusId, String keyWords,
			String keyWords2) {
		    StringBuffer buffer= new StringBuffer("");
		    Map<String , Object> result = new HashMap<String,Object>();
		    List<Object> params = new ArrayList<Object>();
		    buffer.append("SELECT COUNT(1)  ");
			buffer.append("FROM MCD_PLAN_DEF A   ");
			buffer.append(" LEFT JOIN MCD_DIM_PLAN_TYPE B ON A.PLAN_TYPE=B.TYPE_ID ");
			//buffer.append("LEFT JOIN MCD_PLAN_CHANNEL_LIST C ON A.PLAN_ID=C.PLAN_ID  ");
			buffer.append(" LEFT JOIN mcd_dim_plan_online_status D ON D.STATUS_ID=A.ONLINE_STATUS  ");
			buffer.append("WHERE 1=1  ");
			buffer.append("AND A.PLAN_SRV_TYPE  = '1' ");
			 if (StringUtils.isNotEmpty(keyWords)) { // 关键字查询
					if (keyWords.equals("%")) {
						buffer.append(" and (A.PLAN_NAME like ").append("'%\\%%' escape '\\'").append(" OR A.PLAN_ID like ")
								.append("'%\\%%' escape '\\')");
					} else {
						buffer.append(" and (A.PLAN_NAME like ? OR A.PLAN_ID like ?)");
						params.add("%" + keyWords + "%");
						params.add("%" + keyWords + "%");
					}
				}
				
				if (StringUtils.isNotEmpty(typeId)) { // 查询政策类型
					buffer.append(" and B.TYPE_ID =?");
					params.add(typeId);
				}
				if (StringUtils.isNotEmpty(statusId)) { // 状态类型
					buffer.append(" and A.ONLINE_STATUS =?");
					params.add(statusId);
				}
						
		log.debug("--------统计政策sql：" + buffer + "---------");
		log.debug("-----传入参数数组：" + params.toString() + "-------");
	        //政策在开始和结束之间
			buffer.append(" AND sysdate BETWEEN nvl2(A.PLAN_STARTDATE,A.PLAN_STARTDATE,TO_DATE('19000101','YYYYMMDD'))  AND nvl2(A.PLAN_ENDDATE,A.PLAN_ENDDATE,TO_DATE('21000101','YYYYMMDD'))");
			buffer.append(" and A.PLAN_STATUS=1 ");// 产品上线了
			result.put("sql", buffer.toString());
			result.put("params", params);
			return result;
	}

	
	
	/**
     * 获取列表数据
     * @param typeId
     * @param statusId
     * @param keyWords
     * @param keyWords2
     * @param pager
     * @return
     */
	private Map<String, Object> getPlansByConditionSql(String typeId, String statusId, String keyWords,
			String keyWords2, Pager pager) {
		StringBuffer buffer = new StringBuffer("");
		String  bufferSql = "";
		Map<String,Object> result = new HashMap<String,Object>();
		List<Object> params = new ArrayList<Object>();
		buffer.append("SELECT DISTINCT A.PLAN_ID,PLAN_NAME,B.TYPE_NAME,B.TYPE_ID,A.PLAN_STATUS, ");
		buffer.append("a.PLAN_STARTDATE AS STARTDATE,a.PLAN_ENDDATE AS ENDDATE, ");
		buffer.append("to_char(a.PLAN_STARTDATE,'yyyy-MM-dd HH:mm:ss') as PLAN_STARTDATE,  ");
		buffer.append("to_char(a.PLAN_ENDDATE,'yyyy-MM-dd HH:mm:ss') as PLAN_ENDDATE, ");
		buffer.append("case when  a.PLAN_DESC is null then '无' else  a.PLAN_DESC end as PLAN_DESC,  ");
		buffer.append("case when a.PLAN_COMMENT is null then '无' else A.PLAN_COMMENT end as PLAN_COMMENT , ");
		buffer.append("CASE WHEN C.MANAGER IS NULL THEN '无'else C.MANAGER END AS MANAGER, ");
		buffer.append("case when A.ONLINE_STATUS is null then '0' else A.ONLINE_STATUS  end as ONLINE_STATUS , ");
		buffer.append("case when  D.STATUS_NAME is null then '未上线' else  D.STATUS_NAME end as STATUS_NAME  ");
		buffer.append("FROM MCD_PLAN_DEF A   ");
		buffer.append(" LEFT JOIN MCD_DIM_PLAN_TYPE B ON A.PLAN_TYPE=B.TYPE_ID ");
		buffer.append("LEFT JOIN mcd_plan_ext_jx C ON A.PLAN_ID=C.PLAN_ID  ");
		buffer.append(" LEFT JOIN mcd_dim_plan_online_status D ON D.STATUS_ID=A.ONLINE_STATUS  ");
		buffer.append("WHERE 1=1  ");
		buffer.append("AND A.PLAN_SRV_TYPE  = '1' ");
		 if (StringUtils.isNotEmpty(keyWords)) { // 关键字查询
				if (keyWords.equals("%")) {
					buffer.append(" and (A.PLAN_NAME like ").append("'%\\%%' escape '\\'").append(" OR A.PLAN_ID like ")
							.append("'%\\%%' escape '\\')");
				} else {
					buffer.append(" and (A.PLAN_NAME like ? OR A.PLAN_ID like ?)");
					params.add("%" + keyWords + "%");
					params.add("%" + keyWords + "%");
				}
			}
			
			if (StringUtils.isNotEmpty(typeId)) { // 查询政策类型
				buffer.append(" and B.TYPE_ID =?");
				params.add(typeId);
			}
			if (StringUtils.isNotEmpty(statusId)) { // 状态类型
				buffer.append(" and A.ONLINE_STATUS =?");
				params.add(statusId);
			}
					
		log.debug("--------统计政策sql：" + buffer + "---------");
		log.debug("-----传入参数数组：" + params.toString() + "-------");
	        //政策在开始和结束之间
			buffer.append(" AND sysdate BETWEEN nvl2(A.PLAN_STARTDATE,A.PLAN_STARTDATE,TO_DATE('19000101','YYYYMMDD'))  AND nvl2(A.PLAN_ENDDATE,A.PLAN_ENDDATE,TO_DATE('21000101','YYYYMMDD'))");
			buffer.append(" and A.PLAN_STATUS=1 ");// 产品上线了
			String sqlExt = DataBaseAdapter.getPagedSql(buffer.toString(), pager.getPageNum(), pager.getPageSize());
			result.put("sql", sqlExt);
			result.put("params", params);
		    return result;
	}

	/**
	 * 详情内容总体查询
	 * @param planId
	 * @return
	 */
	private List<Map<String, Object>> queryDefExt(String planId) {
		StringBuffer querySql= new StringBuffer("");
		List<Object> queryParam = new ArrayList<Object>();
		querySql.append("SELECT DISTINCT A.PLAN_ID,PLAN_NAME,B.TYPE_NAME,B.TYPE_ID,A.PLAN_STATUS, ");
		querySql.append("a.PLAN_STARTDATE AS STARTDATE,a.PLAN_ENDDATE AS ENDDATE,");
		querySql.append("to_char(a.PLAN_STARTDATE,'yyyy-MM-dd HH:mm:ss') as PLAN_STARTDATE, ");
		querySql.append("to_char(a.PLAN_ENDDATE,'yyyy-MM-dd HH:mm:ss') as PLAN_ENDDATE, ");
		querySql.append("case when  a.PLAN_DESC is null then '无' else  a.PLAN_DESC end as PLAN_DESC,");
		querySql.append("case when a.PLAN_COMMENT is null then '无' else A.PLAN_COMMENT end as PLAN_COMMENT , ");
		querySql.append("CASE WHEN C.MANAGER IS NULL THEN '无'else C.MANAGER END AS MANAGER,");
		querySql.append("case when A.ONLINE_STATUS is null then '0' else A.ONLINE_STATUS  end as ONLINE_STATUS , ");
		querySql.append("case when  D.STATUS_NAME is null then '未上线' else  D.STATUS_NAME end as STATUS_NAME , ");
		querySql.append("CASE WHEN A.CITY_ID IS NULL THEN '' else  A.CITY_ID end as CITY_ID, ");
		querySql.append("CASE WHEN E.CITY_NAME IS NULL THEN '无' else  E.CITY_NAME end as CITY_NAME, ");
		querySql.append("case when C.PLAN_BUSI_TYPE_SUBCODE is null then '无' else C.PLAN_BUSI_TYPE_SUBCODE end as PLAN_BUSI_TYPE_SUBCODE, ");
		querySql.append("case when C.DEAL_CODE_10086 is null then '无' else C.DEAL_CODE_10086 end as DEAL_CODE_10086,  ");
		querySql.append("case when C.DEAL_CODE_1008611 is null then '无' else C.DEAL_CODE_1008611 end as DEAL_CODE_1008611,  ");
		querySql.append("case when C.URL_FOR_ANDROID is null then '无' else C.URL_FOR_ANDROID end as URL_FOR_ANDROID,  ");
		querySql.append("case when C.URL_FOR_IOS is null then '无' else C.URL_FOR_IOS end as URL_FOR_IOS  ");
		querySql.append("FROM MCD_PLAN_DEF A  ");
		querySql.append(" LEFT JOIN MCD_DIM_PLAN_TYPE B ON A.PLAN_TYPE=B.TYPE_ID  ");
		querySql.append("LEFT JOIN mcd_plan_ext_jx C ON A.PLAN_ID=C.PLAN_ID  ");
		querySql.append(" LEFT JOIN mcd_dim_plan_online_status D ON D.STATUS_ID=A.ONLINE_STATUS   ");
		querySql.append("LEFT JOIN MCD_DIM_CITY E ON E.CITY_ID=A.CITY_ID ");
		querySql.append("WHERE 1=1 ");
		querySql.append("AND A.PLAN_SRV_TYPE  = '1' ");
		querySql.append("AND A.PLAN_ID=? ");
		queryParam.add(planId);
		return mtlStcPlanDao.execQrySql(querySql.toString(), queryParam);
	}
	
	
	/**
	 * 渠道查询
	 * @param planId
	 * @return
	 */
	private List<Map<String, Object>> queryChannel(String planId) {
		StringBuffer querySql= new StringBuffer("");
		List<Object> queryParam = new ArrayList<Object>();
		querySql.append("SELECT A.CHANNEL_ID,A.CHANNEL_NAME,  ");
		querySql.append("case when B.PLAN_ID  is null then ' ' else B.PLAN_ID end as PLAN_ID FROM MCD_DIM_CHANNEL  A ");
		querySql.append(" LEFT JOIN ( SELECT DISTINCT CHANNEL_ID,PLAN_ID FROM MCD_PLAN_CHANNEL_LIST WHERE 1=1  ");
		querySql.append(" AND PLAN_ID=? ");
		queryParam.add(planId);
		querySql.append(" )B ON A.CHANNEL_ID=B.CHANNEL_ID ");
		querySql.append(" ORDER BY  A.CHANNEL_ID ");
		return mtlStcPlanDao.execQrySql(querySql.toString(), queryParam);
	}
	
	
	/**
	 * 查询营业侧归属业务类型
	 * @param planId
	 * @return
	 */
	private List<Map<String, Object>> queryPlanBusiType(String planId) {
		StringBuffer querySql= new StringBuffer("");
		List<Object> queryParam = new ArrayList<Object>();
		querySql.append(" SELECT ");
		querySql.append(" case when PLAN_BUSI_TYPE is null then '0' else PLAN_BUSI_TYPE end as PLAN_BUSI_TYPE, ");
		querySql.append(" case when B.TYPE_NAME is null then '无' else B.TYPE_NAME end as TYPE_NAME ");
		querySql.append("  FROM mcd_plan_ext_jx A   ");
		querySql.append("   LEFT JOIN MCD_DIM_PLAN_EXT_BUSITYPE  B ON B.TYPE_ID=A.PLAN_BUSI_TYPE   ");
		querySql.append("  WHERE 1=1   ");
		querySql.append(" AND A.PLAN_ID=?");
		queryParam.add(planId);
		return mtlStcPlanDao.execQrySql(querySql.toString(), queryParam);
	}

	/**
	 * 查询酬金积分
	 * @param planId
	 * @return
	 */
	private List<Map<String, Object>> queryAwardScore(String planId) {
		StringBuffer querySql= new StringBuffer("");
		List<Object> queryParam = new ArrayList<Object>();
		querySql.append("SELECT A.CITY_ID,A.CITY_NAME, ");
		querySql.append("CASE WHEN B.AWARD IS NULL THEN 0.00 ELSE B.AWARD END AS AWARD, ");
		querySql.append("CASE WHEN C.SCORE IS NULL THEN 0.00 ELSE C.SCORE END AS SCORE  ");
		querySql.append("FROM MCD_DIM_CITY A ");
		querySql.append("LEFT JOIN (SELECT CITY_ID,AWARD FROM mcd_plan_award WHERE 1=1 ");
		querySql.append(" AND PLAN_ID=? ");
		queryParam.add(planId);
		querySql.append(" ) B ");
		querySql.append("ON A.CITY_ID=B.CITY_ID ");
		querySql.append("LEFT JOIN (SELECT CITY_ID,SCORE FROM mcd_plan_staffscore WHERE 1=1 ");
		querySql.append(" AND PLAN_ID=? ");
		queryParam.add(planId);
		querySql.append(" ) C ");
		querySql.append("ON A.CITY_ID=C.CITY_ID  ");
		querySql.append("WHERE 1=1 order BY A.CITY_ID ");
		return mtlStcPlanDao.execQrySql(querySql.toString(), queryParam);
	}
	
	/**
	 * 查询策略
	 * @param planId
	 * @return
	 */
	private List<Map<String, Object>> queryCampseg(String planId) {
		StringBuffer querySql= new StringBuffer("");
		List<Object> queryParam = new ArrayList<Object>();
		querySql.append("SELECT A.PLAN_ID,A.CAMPSEG_ID,A.CAMPSEG_NAME,B.CAMPSEG_STAT_NAME from  mcd_camp_def A ");
		querySql.append("LEFT JOIN MCD_DIM_CAMP_STATUS B ");
		querySql.append("ON A.CAMPSEG_STAT_ID = B.CAMPSEG_STAT_ID WHERE 1=1  ");
		querySql.append(" AND A.PLAN_ID=?");
		queryParam.add(planId);
		return mtlStcPlanDao.execQrySql(querySql.toString(), queryParam);
	}
	
	
	/**
	 * 更新10086、1008611、URL_FOR_ANDROID,URL_FOR_IOS,MANAGER; 1.先判断是否存在 2.存在则更新 3.不存在则插入
	 * 
	 * @param planId
	 * @param dealCode_10086
	 * @param dealCode_1008611
	 * @param urlForAndroid
	 * @param urlForIos
	 */
	private Boolean updateDealCode(String planId, String dealCode_10086, String dealCode_1008611, String urlForAndroid,
			String urlForIos, String manager) {

		// 查询判断是否存在
		StringBuffer sqlBf = new StringBuffer("");
		List<Object> paramBf = new ArrayList<Object>();
		sqlBf.append("select * from MCD_PLAN_EXT_JX  ");
		sqlBf.append("WHERE 1=1  ");
		sqlBf.append("AND  PLAN_ID =? ");
		paramBf.add(planId);
		List<Map<String, Object>> bfResult = mtlStcPlanDao.execQrySql(sqlBf.toString(), paramBf);

		// 对判断结果作相应的插入/更新操作
		if (bfResult == null || bfResult.size() == 0) {
			StringBuffer upSql1 = new StringBuffer("");
			List<Object> param1 = new ArrayList<Object>();
			upSql1.append(
					"insert into MCD_PLAN_EXT_JX(PLAN_ID,DEAL_CODE_10086,DEAL_CODE_1008611,URL_FOR_ANDROID,URL_FOR_IOS,MANAGER) ");
			upSql1.append("VALUES(?,?,?,?,?,?) ");
			param1.add(planId);
			param1.add(dealCode_10086);
			param1.add(dealCode_1008611);
			param1.add(urlForAndroid);
			param1.add(urlForIos);
			param1.add(manager);
			return mcdPlanDao.updatePlan(upSql1.toString(), param1);
		} else {
			StringBuffer upSql1 = new StringBuffer("");
			List<Object> param1 = new ArrayList<Object>();
			upSql1.append("UPDATE MCD_PLAN_EXT_JX ");
			upSql1.append("SET DEAL_CODE_10086 =?,DEAL_CODE_1008611=?,URL_FOR_ANDROID=?, URL_FOR_IOS=?,MANAGER=? ");
			param1.add(dealCode_10086);
			param1.add(dealCode_1008611);
			param1.add(urlForAndroid);
			param1.add(urlForIos);
			param1.add(manager);
			upSql1.append("where 1=1 ");
			upSql1.append("and PLAN_ID =? ");
			param1.add(planId);

			return mcdPlanDao.updatePlan(upSql1.toString(), param1);

		}
	}
	
	
	/**
	 * 更新typeId,statusId,planDesc
	 * 
	 * @param planId
	 * @param typeId
	 * @param statusId
	 * @param planDesc
	 * @return
	 */

	private Map<String, Object> updatePlanDef(String planId, String typeId, String statusId, String planDesc) {
		Map<String, Object> result = new HashMap<String, Object>();
		StringBuffer upSql = new StringBuffer("");
		List<String> param = new ArrayList<String>();
		upSql.append("UPDATE MCD_PLAN_DEF ");
		upSql.append("SET PLAN_TYPE =?,ONLINE_STATUS=?,PLAN_DESC=? ");
		param.add(typeId);
		param.add(statusId);
		param.add(planDesc);
		upSql.append("where 1=1 ");
		upSql.append("and PLAN_ID =? ");
		param.add(planId);
		result.put("upSql", upSql);
		result.put("param", param);
		return result;
	}
	
	/**
	 * 更新推荐语
	 * 
	 * @param planId
	 * @param planComment
	 * @return
	 */
	private Boolean updatePlanComment(String planId, String planComment) {
		StringBuffer planCommentSql = new StringBuffer("");
		List<Object> planCommentParam = new ArrayList<Object>();
		planCommentSql.append("");
		planCommentSql.append(" UPDATE MCD_PLAN_DEF SET PLAN_COMMENT =? ");
		planCommentParam.add(planComment);
		planCommentSql.append(" WHERE 1=1 ");
		planCommentSql.append(" AND PLAN_ID=? ");
		planCommentParam.add(planId);

		mcdPlanDao.updatePlan(planCommentSql.toString(), planCommentParam);
		return true;
	}
	
	/**
	 * 更新推荐语execContent
	 * 
	 * @param planId
	 * @param execContent
	 * @return
	 */
	private Boolean updateExecCon(String planId, String channelId) {
		String[] channel = channelId.split("/");

		// 先删除原来的数据
		Map<String, Object> result = new HashMap<String, Object>();
		StringBuffer deleteSql = new StringBuffer("");
		List<String> deleteParam = new ArrayList<String>();
		deleteSql.append("delete  FROM MCD_PLAN_CHANNEL_LIST ");
		deleteSql.append("where 1=1 ");
		deleteSql.append("and PLAN_ID =?");
		deleteParam.add(planId);
		result.put("upSql", deleteSql);
		result.put("param", deleteParam);

		String deleteChannel = result.get("upSql").toString();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Object> deleteParams = (List) result.get("param");
		mcdPlanDao.updatePlan(deleteChannel, deleteParams);

		// 插入数据
		for (int i = 0; i < channel.length; i++) {
			String idNum = Integer.toString(random.nextInt(300000000));
			List<Object> upResults = new ArrayList<Object>();
			StringBuffer sql3 = new StringBuffer("");
			sql3.append("insert into MCD_PLAN_CHANNEL_LIST(ID,PLAN_ID,CHANNEL_ID) ");
			sql3.append("values(?,?,?) ");
			upResults.add(idNum);
			upResults.add(planId);
			upResults.add(channel[i]);
			mcdPlanDao.updatePlan(sql3.toString(), upResults);

		}
		return true;
	}
	
	/**
	 * 更新酬金和积分
	 * 
	 * @param cityIds
	 * @param scores
	 * @param awards
	 * @return
	 */

	private Boolean updateScoreAward(String planId, String cityIds, String scores, String awards) {
		String[] cityId = cityIds.split("/");
		String[] score = scores.split("/");
		String[] award = awards.split("/");

		// 先删除原来积分的数据
		StringBuffer scoresDelete = new StringBuffer("");
		List<Object> scoresParam = new ArrayList<Object>();
		scoresDelete.append("");
		scoresDelete.append("DELETE mcd_plan_staffscore ");
		scoresDelete.append("WHERE 1=1 ");
		scoresDelete.append("AND PLAN_ID=? ");
		scoresParam.add(planId);
		mcdPlanDao.updatePlan(scoresDelete.toString(), scoresParam);

		// 先删除原来酬金的数据
		StringBuffer awardsDelete = new StringBuffer("");
		List<Object> awardsParam = new ArrayList<Object>();
		awardsDelete.append("");
		awardsDelete.append("DELETE mcd_plan_award ");
		awardsDelete.append("WHERE 1=1 ");
		awardsDelete.append("AND PLAN_ID=? ");
		awardsParam.add(planId);
		mcdPlanDao.updatePlan(awardsDelete.toString(), awardsParam);

		// 循环插入积分数据
		for (int i = 0; i < cityId.length; i++) {
			StringBuffer scoresInTo = new StringBuffer("");
			List<Object> scoresInToParam = new ArrayList<Object>();
			scoresInTo.append("");
			scoresInTo.append(" insert into mcd_plan_staffscore (PLAN_ID,CITY_ID,SCORE) ");
			scoresInTo.append(" VALUES(?,?,?) ");
			scoresInToParam.add(planId);
			scoresInToParam.add(cityId[i]);
			scoresInToParam.add(score[i]);

			mcdPlanDao.updatePlan(scoresInTo.toString(), scoresInToParam);

		}

		for (int i = 0; i < cityId.length; i++) {
			StringBuffer awardsInTo = new StringBuffer("");
			List<Object> awardsInToParam = new ArrayList<Object>();
			awardsInTo.append("");
			awardsInTo.append(" insert into mcd_plan_award (PLAN_ID,CITY_ID,AWARD) ");
			awardsInTo.append(" VALUES(?,?,?) ");
			awardsInToParam.add(planId);
			awardsInToParam.add(cityId[i]);
			awardsInToParam.add(award[i]);

			mcdPlanDao.updatePlan(awardsInTo.toString(), awardsInToParam);

		}

		return true;
	}


}
	

	

