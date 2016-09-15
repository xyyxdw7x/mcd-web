package com.asiainfo.biapp.mcd.common.custgroup.service;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.common.custgroup.vo.McdCustgroupDef;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.custgroup.vo.McdBotherContactConfig;

/**
 * 
 * Title: 
 * Description: 客户群信息
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-7-18 上午10:54:56
 * @version 1.0
 */

public interface ICustGroupInfoService {
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

	public List<Map<String,Object>> queryQueueInfo();
	
	/**
	 *
	 * @param group_into_id
	 * @param group_cycle
	 * @param queue_id
	 */
	public int saveQueue(String group_into_id,String group_cycle,String queue_id, String data_date,String group_table_name);

	public void deleteCustom(String customGrpId);
	
	public List<Map<String,Object>> searchCustomDetail(String customGrpId);
	
	/**
	 * 获取原始客户群数量
	 * @param custGroupId
	 * @return
	 */
	public int getOriginalCustGroupNum(String custGroupId);
    /**
     * 
     * @param fileNameCsv  导入文件名称
     * @param fileNameVerf 验证文件民称
     * @param customGroupName  客户群名称
     * @param mtlCuserTableName 客户群所存表名称
     * @param filenameTemp 验证文件地址
     * @param ftpStorePath FTP需要导入的文件地址
     * @param customGroupId 客户群ID
     */
	public void insertSqlLoderISyncDataCfg(String fileNameCsv, String fileNameVerf, String customGroupName, String mtlCuserTableName, String ftpStorePath, String filenameTemp, String customGroupId);
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
     * 根据客户群ID查找客户群
     * @param custgroupId
     * @return
     */
    McdCustgroupDef getMtlGroupInfo(String custgroupId);
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
	/**
	 * 根据客户群清单表名，查询出项目客户群数量  add by zhanghy2 at 2015-12-06 because of huge custom group
	 */
	public int getCustInfoCount(String customgroupid, String bussinessLableSql,String ARPUSql, String orderProductNo, String excludeProductNo);

	/**
	 * 
	 * @param bussinessLableSql 业务标签拼装的SQL   过滤黑名单
	 * @param basicEventSql  基础标签拼装的SQL
	 * @param channelId   渠道ID
	 * @param campsegTypeId  策略类型ID 
	 * @return
	 */
	public List<Map<String,Object>> getAfterFilterCustGroupList(String bussinessLableSql,String basicEventSql,String channelId, int campsegTypeId,String customgroupid,String orderProductNo,String excludeProductNo);

	/**
	 * 免打扰  频次过滤
	 * @param bussinessLableSql
	 * @param basicEventSql
	 * @param channelId
	 * @param campsegTypeId
	 * @param customgroupid
	 * @param orderProductNo
	 * @param excludeProductNo
	 * @return
	 */
	public List<Map<String,Object>> getAfterBotherAvoid(String bussinessLableSql,String basicEventSql,String channelId, int campsegTypeId,String customgroupid,String orderProductNo,String excludeProductNo,String cityId,String campsegId,int avoidBotherFlag,int flag);
	McdBotherContactConfig getMtlBotherContactConfig(String campsegTypeId, String channelId, int campsegCityType);
	
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
	
	
	
}

