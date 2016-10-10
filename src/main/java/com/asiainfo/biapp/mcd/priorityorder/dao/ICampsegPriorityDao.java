package com.asiainfo.biapp.mcd.priorityorder.dao;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.priorityorder.vo.CampsegPriorityBean;

/**
 * 
 * Title: 
 * Description:
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2016-3-17 上午10:13:17
 * @version 1.0
 */

public interface ICampsegPriorityDao {
	/**
	 * 首页手动优先级调整
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
	 * 修改置顶策略的优先级   将自动策略标志位改为1，sort_num字段也改为1
	 * @param campsegId
	 * @param channelId
	 * @param cityId
	 */
	public void editPriorityCampseg(String campsegId,String channelId,String cityId);
	
	/**
	 * 当点击置顶  自动变手动的时候  先将原来的优先级全部加1
	 */
	public void eidtManualPriority(String channelId,String cityId,String chnAdivId);
	
	//当点击置顶  自动变手动的时候  先将原来的优先级全部加1  当自动超过十条的时候，将最后一条从手动改为自动，然后调用存储过程
	public void changeManualToAutoPriority(String campsegId,String channelId,String cityId,String chnAdivId);
	
	/**
	 * 修改手动策略优先级的内部排序
	 * @param campsegId0
	 * @param channelId0
	 * @param cityId0
	 * @param campsegId1
	 * @param channelId1
	 * @param cityId1
	 */
//	public void editManualPriorityCampseg(String campsegId,String channelId,String cityId,int priority,String chnAdivId);
	public void editManualPriorityCampseg(List<Map<String, Object>> list1,List<Map<String, Object>> list2);
	/**
	 * 取消置顶  先将手动改为自动
	 * @param campsegId
	 */
	public void cancleTopManualPriorityCampseg1(String campsegId,String cityId,String channelId,String chnAdivId);
	
	/**
	 * 取消置顶  对手动自动为序排序字段，然后调用存储过程
	 * @param campsegId
	 */
	public void cancleTopManualPriorityCampseg2(String campsegId,String cityId,String channelId,String chnAdivId);
}


