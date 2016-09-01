package com.asiainfo.biapp.mcd.common.dao.custgroup;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.common.vo.custgroup.MtlGroupInfo;

public interface CustGroupInfoDao {
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
	
	public String isCustomDeletable(String customGrpId, String userId);
	
	public List searchCustomDetail(String customGrpId);
	
	public String getExistQueueCfgId(String group_into_id, String queue_id)throws Exception;
	
	public String getMaxQueueCfgId()throws Exception;
	
	public int insertQueue(String group_into_id, String group_cycle, String queue_id,String data_date, String cfg_id,String group_table_name)throws Exception;
	
	public void deleteCustom(String customGrpId);
	
	public List queryQueueInfo();
	/**
	 * 获取原始客户群数量
	 * @param custGroupId
	 * @return
	 */
	public int getOriginalCustGroupNum(String custGroupId);
	/**
	 * @return
	 */
	/**
	 * 周期性SQLLODER任务，更新任务信息
	 * @param fileNameCsv
	 * @param fileNameVerf
	 * @param customGroupName
	 * @param mtlCuserTableName
	 * @param ftpStorePath
	 * @param filenameTemp
	 * @param customGroupId
	 */
	public void updateSqlLoderISyncDataCfg(String fileNameCsv,String fileNameVerf, String customGroupName,String mtlCuserTableName, String ftpStorePath, String filenameTemp,String customGroupId);
	/**
	 * 根据客户群ID查询是否存在该客户群任务
	 * @param customGroupId
	 * @return
	 */
	public List getSqlLoderISyncDataCfg(String customGroupId);
	void insertSqlLoderISyncDataCfg(String fileName, String fileNameVerf, String customGroupName,
			String mtlCuserTableName, String ftpStorePath, String filenameTemp, String customGroupId);
    /**
     * add by jinl 20150717
     * @Title: getTargetCustomerbase
     * @Description: 获取"目标客户群"信息
     * @param @param campsegId
     * @param @return
     * @param @throws Exception    
     * @return List 
     * @throws
     */
    public List getTargetCustomerbase(String campsegId);
    /**
     * 根据客户群ID查找客户群信息
     */
    public MtlGroupInfo getMtlGroupInfo(String custgroupId);
    /**
     * @Title: getDataDateCustomNum
     * @Description: 最新数据日期，初始客户群规模
     * @param @param campsegId
     * @param @return    
     * @return List<Map<String,Object>> 
     * @throws
     */
    public List<Map<String, Object>> getDataDateCustomNum(String campsegId);
    /**
     * 详情页  查询原始客户群数量
     * @param custom_group_id
     * @return
     */
    public int getOriCustGroupNum(String custom_group_id);

}
