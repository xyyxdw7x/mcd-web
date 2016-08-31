package com.asiainfo.biapp.mcd.custgroup.service;

import java.util.List;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.tactics.vo.MtlGroupInfo;

/**
 * 
 * Title: 
 * Description: 客户群信息
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-7-18 上午10:54:56
 * @version 1.0
 */

public interface CustGroupInfoService {
	/**
	 * 点击更多按钮  关键字查询，分页方式展示我的客户群 
	 * @param currentUserId
	 * @return
	 */
	public int getMoreMyCustomCount(String currentUserId,String keyWords);
	/**
	 * 点击更多按钮  关键字查询，分页方式展示我的客户群 
	 * @param currentUserId
	 * @return
	 */
	public List<MtlGroupInfo> getMoreMyCustom(String currentUserId,String keyWords,Pager pager);
	/**
	 * 新建策略页面   选取营销人群    初始化我的客户群信息
	 * @param currentUserId
	 * @return
	 */
	public List<MtlGroupInfo> getMyCustGroup(String currentUserId);
	
	public List searchCustom(String contentType, Pager pager, String userId, String keywords);

	public List queryQueueInfo();
	
	/**
	 *
	 * @param group_into_id
	 * @param group_cycle
	 * @param queue_id
	 */
	public int saveQueue(String group_into_id,String group_cycle,String queue_id, String data_date,String group_table_name);

	public void deleteCustom(String customGrpId);
	
	public List searchCustomDetail(String customGrpId);
	
	/**
	 * 获取原始客户群数量
	 * @param custGroupId
	 * @return
	 */
	public int getOriginalCustGroupNum(String custGroupId);
	
}
