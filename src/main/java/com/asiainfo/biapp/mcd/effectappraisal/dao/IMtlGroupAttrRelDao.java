package com.asiainfo.biapp.mcd.effectappraisal.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.effectappraisal.vo.DimMtlAdivInfo;
import com.asiainfo.biapp.mcd.effectappraisal.vo.MtlGroupAttrRel;
import com.asiainfo.biapp.mcd.effectappraisal.vo.RuleTimeTermLable;

/**
 * 
 * Title: 
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-7-21 下午02:00:18
 * @version 1.0
 */

public interface IMtlGroupAttrRelDao {
	/**
	 * 根据客户群id获取群发用于变量
	 * @param custGroupId
	 * @return
	 */
	public List<MtlGroupAttrRel> initTermLable(String custGroupId);
	
	/**
	 * 实时事件变量初始化 初始化弹出页面中的值
	 * @return
	 */
	public List<RuleTimeTermLable> initRuleTimeTermLable();
	
	public List<RuleTimeTermLable> getFunctionNameById(String functionId);
	/**
	 *  初始化实时事件变量，当点击弹出页面中的值，初始化真正的变量
	 * @return
	 */
	public List<RuleTimeTermLable> initRuleTimeTermSonLable(String sceneId);
	/**
	 * 初始化运营位参数
	 * @param channelId
	 * @return
	 */
	public List<DimMtlAdivInfo> initDimMtlAdivInfo(String channelId,String planId);
	
	public List<DimMtlAdivInfo> getAdivInfo(String adivids);
	

	/**
	 * 根据渠道id初始化运营位
	 * @param channelId
	 * @return
	 */
	public List initAdivInfoByChannelId(String cityId);
}
