package com.asiainfo.biapp.mcd.common.dao.plan;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.common.vo.plan.DimPlanSrvType;
import com.asiainfo.biapp.mcd.common.vo.plan.McdDimPlanType;
import com.asiainfo.biapp.mcd.common.vo.plan.McdPlanDef;
import com.asiainfo.biapp.mcd.common.vo.plan.MtlStcPlanBean;
import com.asiainfo.biapp.mcd.tactics.exception.MpmException;
import com.asiainfo.biapp.mcd.tactics.vo.McdPlanChannelList;

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

	List<McdDimPlanType> initDimPlanType() throws Exception;
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
	public List<McdPlanChannelList> getChannelIds(String planIds);
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
	public List<Map<String,Object>> checkIsUserd(String planIds,String cityId);

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
    McdPlanDef getMtlStcPlanByPlanId(String planId);
    /**
     * 根据渠道类型ID获取渠道
     * @param planType
     * @return
     */
    McdDimPlanType getPlanTypeById(String planTypeId);
	/**
	 * 根据plan_id查询推荐业务
	 * @param planID
	 * @return
	 */
	public McdPlanDef getMtlStcPlanByPlanID(String planID);
	
	/**
	 * 根据父id获得所有的子类型
	 * @param pid
	 * @return
	 */
	List<McdDimPlanType> getChildrens(String pid);
	

	/**
	 * 查询所有产品类型
	 * @return
	 */
	List<McdDimPlanType> getAll();
	/**
	 * 执行查询sql 返回结果集
	 * @param sql sql语句
	 * @param params 对应的参数
	 * @return
	 */
	List<Map<String,Object>> execQuerySql(String sql, List<Object> params);
	/**
	 * 执行查询条件，返回记录总条数
	 * @param sql
	 * @param params
	 * @return
	 */
	int execQuerySqlCount(String sql, List<Object> params);
	/**
	 * 获取政策粒度
	 * @return
	 * @throws MpmException
	 */
	List<DimPlanSrvType> getGradeList() throws MpmException;
}
