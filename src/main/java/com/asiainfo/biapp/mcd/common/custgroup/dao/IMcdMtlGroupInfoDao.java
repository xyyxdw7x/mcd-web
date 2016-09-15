package com.asiainfo.biapp.mcd.common.custgroup.dao;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.common.vo.custgroup.McdCustgroupDef;

public interface IMcdMtlGroupInfoDao {

    /**
     * 根据客户群ID查找客户群
     * @param custgroupId
     * @return
     */
    McdCustgroupDef getMtlGroupInfo(String custgroupId);

	/**
	 * 新建策略页面   选取营销人群    初始化我的客户群信息
	 * @param currentUserId
	 * @return
	 */
	public List<McdCustgroupDef> getMyCustGroup(String currentUserId);
	
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
	public List<McdCustgroupDef> getMoreMyCustom(String currentUserId,String keyWords,Pager pager);
	
	public List<Map<String,Object>> searchCustom(String contentType, Pager pager, String userId, String keywords);
	
	public String isCustomDeletable(String customGrpId, String userId);
	
	public List<Map<String,Object>> queryQueueInfo();
	
	public String getExistQueueCfgId(String group_into_id, String queue_id)throws Exception;
	
	public String getMaxQueueCfgId()throws Exception;
	
	public int insertQueue(String group_into_id, String group_cycle, String queue_id,String data_date, String cfg_id,String group_table_name)throws Exception;

	public void deleteCustom(String customGrpId);
	
	public List<Map<String,Object>> searchCustomDetail(String customGrpId);
    /**
     * 根据客户群编码获取客户群清单信息
     * @param customgroupid
     * @return
     */
	public List<Map<String,Object>> getMtlCustomListInfo(String custgroupId);
	/**
	 * 根据客户群id查询客户群信息
	 * @param custGroupId
	 * @return
	 */
	public McdCustgroupDef getCustGroupInfoById(String custGroupId);

}
