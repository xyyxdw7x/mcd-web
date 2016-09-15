package com.asiainfo.biapp.mcd.common.custgroup.dao;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.common.vo.custgroup.McdCustgroupDef;

public interface ICustGroupInfoDao {
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
	/**
	 * 新建策略页面   选取营销人群    初始化我的客户群信息
	 * @param currentUserId
	 * @return
	 */
	public List<McdCustgroupDef> getMyCustGroup(String currentUserId);
	
	public List<Map<String,Object>> searchCustom(String contentType, Pager pager, String userId, String keywords);
	
	public String isCustomDeletable(String customGrpId, String userId);
	
	public List<Map<String,Object>> searchCustomDetail(String customGrpId);
	
	public String getExistQueueCfgId(String group_into_id, String queue_id)throws Exception;
	
	public String getMaxQueueCfgId()throws Exception;
	
	public int insertQueue(String group_into_id, String group_cycle, String queue_id,String data_date, String cfg_id,String group_table_name)throws Exception;
	
	public void deleteCustom(String customGrpId);
	
	public List<Map<String,Object>> queryQueueInfo();
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
	public List<Map<String,Object>> getSqlLoderISyncDataCfg(String customGroupId);
	void insertSqlLoderISyncDataCfg(String fileName, String fileNameVerf, String customGroupName,String mtlCuserTableName, String ftpStorePath, String filenameTemp, String customGroupId);
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
    public List<Map<String,Object>> getTargetCustomerbase(String campsegId);
    /**
     * 根据客户群ID查找客户群信息
     */
    public McdCustgroupDef getMtlGroupInfo(String custgroupId);
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
    
	
	List<Map<String,Object>> getMtlCustomListInfo(String customgroupid); 
	/**
	 * 
	 * @param bussinessLableSql 业务标签拼装的SQL  过滤黑名单
	 * @param basicEventSql  基础标签拼装的SQL
	 * @param channelId   渠道ID
	 * @param campsegTypeId  策略类型ID 
	 * @return
	 */
	public List<Map<String,Object>> getAfterFilterCustGroupListInMem(String bussinessLableSql,String basicEventSql,String channelId, int campsegTypeId,String customgroupid,String orderProductNo,String excludeProductNo);
	
	/**
	 * 免打扰  频次过滤
	 * @param bussinessLableSql
	 * @param basicEventSql
	 * @param channelId
	 * @param campsegTypeId
	 * @param customgroupid
	 * @param orderProductNo
	 * @param excludeProductNo
	 * @param cityType:地市接触频次：15天   省公司接触频次：30天
	 * @param frequencyTime:接触次数
	 * @param updateCycle：客户群周期
	 * @return
	 */
	public List<Map<String,Object>> getAfterBotherAvoid1InMem(String bussinessLableSql,String basicEventSql,String channelId, int campsegTypeId,String customgroupid,String orderProductNo,String excludeProductNo,int avoidBotherFlag,int contactControlFlag,String cityId,String cityType,String frequencyTime,int updateCycle,String campsegId);
	List<Map<String,Object>> getAfterBotherAvoid1(String bussinessLableSql, String basicEventSql, String channelId, int campsegTypeId,
			String customgroupid, String orderProductNo, String excludeProductNo, int avoidBotherFlag,
			int contactControlFlag, String cityId, String cityType, String frequencyTime, int updateCycle,String campsegId);
	
	 /**
     * 根据代替SQLFIRE内的表在MCD里创建表的同义词
     * @param mtlCuserTableName
     */
    public void createSynonymTableMcdBySqlFire(String mtlCuserTableName);
    
    /**
	 * 插入清单表新方式
	 * @param customgroupid
	 * @param bussinessLableSql
	 * @param ARPUSql
	 * @param orderProductNo
	 * @param excludeProductNo
	 * @return
	 */
	public void insertCustGroupNewWay(String customgroupid,String bussinessLableSql,String ARPUSql,String orderProductNo,String excludeProductNo,String tableName,boolean removeRepeatFlag);
	McdCustgroupDef getCustGroupInfoById(String custGroupId);
	/**
	 * 根据客户群清单表名，查询出项目客户群数量信息 add by zhanghy2 at 2015-12-06 because of huge custom group
	 */
	int getCustInfoCountInMem(String sql);
}
