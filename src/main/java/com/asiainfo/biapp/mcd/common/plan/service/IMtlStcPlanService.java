package com.asiainfo.biapp.mcd.common.plan.service;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.common.plan.vo.DimPlanSrvType;
import com.asiainfo.biapp.mcd.common.plan.vo.McdDimPlanType;
import com.asiainfo.biapp.mcd.common.plan.vo.McdPlanDef;
import com.asiainfo.biapp.mcd.common.plan.vo.McdStcPlanOrderAttr;
import com.asiainfo.biapp.mcd.common.plan.vo.PlanBean;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.exception.MpmException;

public interface IMtlStcPlanService {
    
    /**
     * 根据渠道类型ID获取渠道
     * @param planType
     * @return
     */
    McdDimPlanType getPlanTypeById(String planType);
	/**
	 * 根据plan_id查询推荐业务
	 * @param planID
	 * @return
	 */
	public McdPlanDef getMtlStcPlanByPlanID(String planID);
	
	/**
	 *  新建策略页面---初始化产品类型
	 * @return
	 */
	public  List<McdDimPlanType> initDimPlanType();
	
	/**
	 * 查询所有的产品类型，并将查询的产品类型集合转换成tree结构的list（每个元素的subType都被填充）
	 * 
	 * @return
	 */
	List<McdDimPlanType> getTreeList();
	/**
	 * 创建产品页面，根据用户选择条件查询产品列表
	 * @param cityId
	 * @param planTypeId
	 * @param planSrvType
	 * @param channelId
	 * @param keyWords
	 * @param pager
	 * @return
	 */
	List<PlanBean> getPlanByCondition(String cityId, String planTypeId, String planSrvType, String channelId, String keyWords,Pager pager);
	/**
	 * 查询政策粒度 ---浙江
	 * @return
	 * @throws MpmException
	 */
	List<DimPlanSrvType> getGradeList() throws MpmException;
	
	/**
	 * 根根产品id、 获得某些渠道预览的产品酬金等相关信息
	 * @param planId 产品id
	 * @param cityId 地市id
	 * @return
	 */
	public Map<String, String> getPlanRewardAndScoreInfo(String planId, String cityId) throws Exception;
	
	/**
	 * 根根产品id、渠道id 查询上月的订购数、订购率查询
	 * @param planId 产品id
	 * @param channelId 渠道id
	 * @return
	 */
	public Map<String, Object> getLastMonthPlanOrderRateInfo(String planId, String channelId) throws Exception;
}
