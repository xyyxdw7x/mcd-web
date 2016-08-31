package com.asiainfo.biapp.mcd.tactics.service;

import java.util.List;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.common.vo.plan.MtlStcPlanBean;

public interface IMtlStcPlanManagementService {
	/**
	 * 添加规则弹出页面  产品关系选择  按照类型和关键字查询   只查询政策力度为营销档次的产品信息数量
	 * @param keyWords
	 * @param typeId
	 * @return
	 */
	public int searchPlanCount(String keyWords,String typeId,String cityId);
	/**
	 * 添加规则弹出页面  产品关系选择  按照类型和关键字查询   只查询政策力度为营销档次的产品信息
	 * @param keyWords
	 * @param typeId
	 * @return
	 */
	public List<MtlStcPlanBean> searchPlan(String keyWords, String typeId,String cityId,Pager pager);
	/**
	 * 获取条数
	 * @param keyWords
	 * @param typeId
	 * @param channelTypeId
	 * @return
	 */
	public int getMtlStcPlanByCondationCount(String keyWords,String typeId,String channelTypeId,String planTypeId,String cityId,String isDoubleSelect);

	/**
	 * 
	 * @param keyWords  关键字
	 * @param typeId	政策类别id
	 * @param channelTypeId   渠道类型id
	 * @return
	 */
	public List<MtlStcPlanBean> getMtlStcPlanByCondation(String keyWords,String typeId,String channelTypeId,String planTypeId,String cityId,String isDoubleSelect,Pager pager);
	
}
