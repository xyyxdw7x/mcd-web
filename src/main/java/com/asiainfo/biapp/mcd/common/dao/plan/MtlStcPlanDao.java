package com.asiainfo.biapp.mcd.common.dao.plan;

import java.util.List;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.common.vo.plan.DimPlanType;
import com.asiainfo.biapp.mcd.common.vo.plan.MtlStcPlan;
import com.asiainfo.biapp.mcd.common.vo.plan.MtlStcPlanBean;
import com.asiainfo.biapp.mcd.tactics.vo.MtlStcPlanChannel;

/*
 * Created on 4:00:43 PM
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */
public interface MtlStcPlanDao {

	List<DimPlanType> initDimPlanType() throws Exception;
	/**
	 * 添加规则弹出页面  产品关系选择  按照类型和关键字查询   只查询政策力度为营销档次的产品信息
	 * @param keyWords
	 * @param typeId
	 * @return
	 */
	public int searchPlanCount(String keyWords,String typeId,String cityId);
	/**
	 * 获取所有的渠道信息
	 * @return
	 */
	public List<MtlStcPlanChannel> getChannelIds(String planIds);
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
	public List checkIsUserd(String planIds,String cityId);

	/**
	 * add by lixq10 IMCD_ZJ 新建策略页面 根据条件查询政策列表
	 * @param keyWords
	 * @param typeId
	 * @param channelTypeId
	 * @return
	 */
	public List<MtlStcPlanBean> getMtlStcPlanByCondation(String keyWords,String typeId, String channelTypeId,String planTypeId,String cityId,String isDoubleSelect,Pager pager);
    /**
     * 根据渠道ID获取渠道信息
     * @param planId
     * @return
     */
    MtlStcPlan getMtlStcPlanByPlanId(String planId);
}