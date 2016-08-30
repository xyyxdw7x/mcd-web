package com.asiainfo.biapp.mcd.effectappraisal.service;

import java.util.List;

import com.asiainfo.biapp.mcd.bean.CampsegPriorityBean;
import com.asiainfo.biapp.mcd.util.jdbcPage.Pager;

/**
 * 
 * Title: 
 * Description:
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2016-3-17 上午10:10:06
 * @version 1.0
 */

public interface IcampsegPriorityService {
	/**
	 * 首页手动优先级配置
	 * @param channelId
	 * @param cityId
	 * @return
	 */
	public List<CampsegPriorityBean> initManualPriorityCampseg(String channelId,String adivId,String cityId);
	
	/**
	 * 首页自动优先级配置
	 * @param channelId
	 * @param cityId
	 * @return
	 */
	public List<CampsegPriorityBean> initAutoPriorityCampseg(String channelId,String adivId,String cityId,String keyWords,Pager pager);
	
	/**
	 * 查询自动优先级数量
	 * @param channelId
	 * @param cityId
	 * @return
	 */
	public int getAutoPriorityCampsegNum(String channelId,String adivId,String cityId,String keyWords);
	
	/**
	 * 修改从自动到手动策略的优先级  
	 * @param campsegId
	 * @param channelId
	 * @param cityId
	 */
	public void editPriorityCampseg(String campsegId,String channelId,String cityId,String chnAdivId);
	
	/**
	 * 修改手动策略优先级的内部排序
	 * @param campsegId0
	 * @param channelId0
	 * @param cityId0
	 * @param campsegId1
	 * @param channelId1
	 * @param cityId1
	 */
	public void editManualPriorityCampseg(String priOrderNumStr);
	
	/**
	 * 取消置顶
	 * @param campsegId
	 */
	public void cancleTopManualPriorityCampseg(String campsegId,String cityId,String channelId,String chnAdivId);
}


