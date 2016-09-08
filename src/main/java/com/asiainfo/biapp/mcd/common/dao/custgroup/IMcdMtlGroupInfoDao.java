package com.asiainfo.biapp.mcd.common.dao.custgroup;

import java.util.List;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.common.vo.custgroup.MtlGroupInfo;

public interface IMcdMtlGroupInfoDao {

    /**
     * 根据客户群ID查找客户群
     * @param custgroupId
     * @return
     */
    MtlGroupInfo getMtlGroupInfo(String custgroupId);

	/**
	 * 新建策略页面   选取营销人群    初始化我的客户群信息
	 * @param currentUserId
	 * @return
	 */
	public List<MtlGroupInfo> getMyCustGroup(String currentUserId);
	
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
	
	public List searchCustom(String contentType, Pager pager, String userId, String keywords);
	
	public String isCustomDeletable(String customGrpId, String userId);
	
	public List queryQueueInfo();
	
	public String getExistQueueCfgId(String group_into_id, String queue_id)throws Exception;
	
	public String getMaxQueueCfgId()throws Exception;
	
	public int insertQueue(String group_into_id, String group_cycle, String queue_id,String data_date, String cfg_id,String group_table_name)throws Exception;

	public void deleteCustom(String customGrpId);
	
	public List searchCustomDetail(String customGrpId);
    /**
     * 根据客户群编码获取客户群清单信息
     * @param customgroupid
     * @return
     */
    List getMtlCustomListInfo(String custgroupId);

}
