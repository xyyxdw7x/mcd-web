package com.asiainfo.biapp.mcd.common.plan.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.plan.dao.IMtlStcPlanDao;
import com.asiainfo.biapp.mcd.common.plan.service.IMtlStcPlanService;
import com.asiainfo.biapp.mcd.common.plan.vo.DimPlanSrvType;
import com.asiainfo.biapp.mcd.common.plan.vo.McdDimPlanType;
import com.asiainfo.biapp.mcd.common.plan.vo.McdPlanDef;
import com.asiainfo.biapp.mcd.common.plan.vo.PlanBean;
import com.asiainfo.biapp.mcd.common.util.DataBaseAdapter;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.exception.MpmException;

/**
 * 渠道相关Service
 * 
 * @author AsiaInfo-jie
 *
 */
@Service("mtlStcPlanService")
public class MtlStcPlanServiceImpl implements IMtlStcPlanService {

	@Resource(name = "mtlStcPlanDao")
	private IMtlStcPlanDao mtlStcPlanDao;

	/**
	 * 根据渠道类型ID获取渠道
	 * 
	 * @param planType
	 * @return
	 */
	@Override
	public McdDimPlanType getPlanTypeById(String planTypeId) {
		return mtlStcPlanDao.getPlanTypeById(planTypeId);
	}

	@Override
	public List<McdDimPlanType> initDimPlanType() {
		try {
			return mtlStcPlanDao.initDimPlanType();
		} catch (Exception e) {
			throw new MpmException("mcd.java.cshzclbsb");
		}
	}
	
	@Override
	public List<DimPlanSrvType> getGradeList() throws MpmException {
		return mtlStcPlanDao.getGradeList();
	}

	public McdPlanDef getMtlStcPlanByPlanID(String planID) {
		return mtlStcPlanDao.getPlanById(planID);
	}

	@Override
	public List<McdDimPlanType> getTreeList() {
		List<McdDimPlanType> allTypes = mtlStcPlanDao.getAll();
		List<McdDimPlanType> topTypes = new ArrayList<McdDimPlanType>();
		for (McdDimPlanType node : allTypes) {
			if ("0".equals(node.getTypePid())) {
				topTypes.add(node);
				this.setSubTypeList(allTypes, node);
			}

		}
		return topTypes;
	}

	private void setSubTypeList(List<McdDimPlanType> allTypes, McdDimPlanType node) {

		List<McdDimPlanType> childTypes = this.getChildrens(allTypes, node);
		if (childTypes != null && childTypes.size() > 0) {
			node.setSubTypes(childTypes);
			for (McdDimPlanType tmp : childTypes) {
				setSubTypeList(allTypes, tmp);
			}
		} else {
			node.setSubTypes(childTypes);
		}
	}

	private List<McdDimPlanType> getChildrens(List<McdDimPlanType> allTypes, McdDimPlanType node) {
		List<McdDimPlanType> subTypes = new ArrayList<>();
		for (int i = 0, size = allTypes.size(); i < size; i++) {
			McdDimPlanType menu = allTypes.get(i);
			if (node.getTypeId().equals(menu.getTypePid())) {
				subTypes.add(menu);
			}
		}
		return subTypes;
	}

	@Override
	public List<PlanBean> getPlanByCondition(String cityId, String planTypeId, String planSrvType, String channelId,String keyWords,Pager pager) {
		
		Map<String,Object> sqlClauseCount = getPlansByConditionSqlCount(cityId, planTypeId, planSrvType, channelId, keyWords);
		String sql = sqlClauseCount.get("sql").toString();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Object> param = (List) sqlClauseCount.get("params");
		int count =  mtlStcPlanDao.execQuerySqlCount(sql, param);
		pager.setTotalSize(count);
		
		Map<String,Object> sqlClause = this.getPlansByConditionSql(cityId, planTypeId, planSrvType, channelId, keyWords,pager);
		String sql2 = sqlClause.get("sql").toString();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<Object> param2 = (List) sqlClause.get("params");
		
		return mtlStcPlanDao.execQuerySql(sql2,param2);
	}
	
	private Map<String,Object> getPlansByConditionSql(String cityId, String planTypeId, String planSrvType, String channelId,String keyWords,Pager pager) {
		StringBuffer buffer = new StringBuffer("");
		Map<String,Object> result = new HashMap<String,Object>();
		List<Object> params = new ArrayList<Object>();
		buffer.append("SELECT A.PLAN_ID,A.PLAN_NAME,A.PLAN_COMMENT,A.PLAN_STARTDATE,A.PLAN_ENDDATE,A.STATUS,A.CREATE_USERID,")
				.append(" A.CREATE_DATE,A.PLAN_TYPE,A.PLAN_SRV_TYPE,B.TYPE_NAME,NVL2(CAMP.PLAN_ID,'是','否') IS_USED ")
				.append("  FROM MCD_PLAN_DEF A ")
				.append(" LEFT OUTER JOIN MCD_DIM_PLAN_TYPE B ").append(" ON A.PLAN_TYPE=B.TYPE_ID ");
		buffer.append("LEFT OUTER JOIN (SELECT distinct PLAN_ID FROM MCD_CAMP_DEF)  CAMP ")
		          .append(" ON A.PLAN_ID=CAMP.PLAN_ID ");
		buffer.append("WHERE 1=1 ");

		if (!"999".equals(cityId)) {//地市人员只能看到本地市的策略
			buffer.append(" AND A.PLAN_ID IN (SELECT DISTINCT PlAN_ID FROM mcd_plan_city_list  WHERE CITY_ID = ?)  ");
			params.add(cityId);
		}

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
		
		if (StringUtils.isNotEmpty(planTypeId)) { // 查询政策类别
			buffer.append(" and A.PLAN_TYPE =?");
			params.add(planTypeId);
		}
		if (StringUtils.isNotEmpty(planSrvType)) { // 查询粒度
			buffer.append(" and A.PLAN_SRV_TYPE =?");
			params.add(planSrvType);
		}
	
		if (StringUtils.isNotEmpty(channelId)) { // 渠道类型查询
			buffer.append(" and A.PLAN_ID in ( select unique PLAN_ID from mcd_plan_channel_list WHERE CHANNEL_ID = ?)");
			params.add(channelId);
		}
		
        //政策在开始和结束之间
		buffer.append(" AND sysdate BETWEEN nvl2(A.PLAN_STARTDATE,A.PLAN_STARTDATE,TO_DATE('19000101','YYYYMMDD'))  AND nvl2(A.PLAN_ENDDATE,A.PLAN_ENDDATE,TO_DATE('21000101','YYYYMMDD'))");
		buffer.append(" AND A.online_status='1' ");// 产品上线了
		buffer.append(" ORDER BY A.PLAN_STARTDATE DESC,A.PLAN_ID DESC");
		String sqlExt = DataBaseAdapter.getPagedSql(buffer.toString(), pager.getPageNum(), pager.getPageSize());
		result.put("sql", sqlExt);
		result.put("params", params);
		return result;
	}
	
	private Map<String,Object> getPlansByConditionSqlCount(String cityId, String planTypeId, String planSrvType, String channelId,String keyWords) {
		StringBuffer buffer = new StringBuffer("");
		Map<String,Object> result = new HashMap<String,Object>();
		List<Object> params = new ArrayList<Object>();
		buffer.append("SELECT COUNT(1) FROM MCD_PLAN_DEF A  where 1=1 ");

		if (!"999".equals(cityId)) {//地市人员只能看到本地市的策略
			buffer.append(" AND A.PLAN_ID IN (SELECT DISTINCT PlAN_ID FROM mcd_plan_city_list  WHERE CITY_ID = ?)  ");
			params.add(cityId);
		}

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
		
		if (StringUtils.isNotEmpty(planTypeId)) { // 查询政策类型
			buffer.append(" and A.PLAN_TYPE =?");
			params.add(planTypeId);
		}
		if (StringUtils.isNotEmpty(planSrvType)) { // 查询类别
			buffer.append(" and A.PLAN_SRV_TYPE =?");
			params.add(planSrvType);
		}
	
		if (StringUtils.isNotEmpty(channelId)) { // 渠道类型查询
			buffer.append(" and A.PLAN_ID in ( select unique PLAN_ID from mcd_plan_channel_list WHERE CHANNEL_ID = ?)");
			params.add(channelId);
		}
	
        //政策在开始和结束之间
		buffer.append(" AND sysdate BETWEEN nvl2(A.PLAN_STARTDATE,A.PLAN_STARTDATE,TO_DATE('19000101','YYYYMMDD'))  AND nvl2(A.PLAN_ENDDATE,A.PLAN_ENDDATE,TO_DATE('21000101','YYYYMMDD'))");
		buffer.append(" AND A.online_status='1' ");// 产品上线了
		result.put("sql", buffer.toString());
		result.put("params", params);
		return result;
	}
	
	/**
	 * 根根产品id、 获得某些渠道预览的产品酬金等相关信息
	 * @param planId 产品id
	 * @param cityId 地市id
	 * @return
	 */
	public Map<String, String> getPlanRewardAndScoreInfo(String planId, String cityId) throws Exception{
		return mtlStcPlanDao.getPlanRewardAndScoreInfo(planId, cityId);
	}
	
	/**
	 * 根根产品id、渠道id 查询上月的订购数、订购率查询
	 * @param planId 产品id
	 * @param channelId 渠道id
	 * @return
	 */
	public Map<String, Object> getLastMonthPlanOrderRateInfo(String planId, String channelId) throws Exception{
		return mtlStcPlanDao.getLastMonthPlanOrderRateInfo(planId, channelId);
	}
}
