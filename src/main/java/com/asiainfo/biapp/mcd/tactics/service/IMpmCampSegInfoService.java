package com.asiainfo.biapp.mcd.tactics.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.mcd.common.plan.vo.McdPlanDef;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.exception.MpmException;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampDrvType;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampCustgroupList;
import com.asiainfo.biapp.mcd.tactics.vo.McdDimCampStatus;
import com.asiainfo.biapp.mcd.tactics.vo.McdApproveLog;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampChannelList;
import com.asiainfo.biapp.mcd.tactics.vo.McdTempletForm;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampDef;
import com.asiainfo.biapp.mcd.tactics.vo.McdPlanChannelList;

/*
 * Created on 11:31:19 AM
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author 
 * @version 1.0
 */

public interface IMpmCampSegInfoService {
	/**
	 * gaowj3
	 * JDBC查询策略管理
	 * @param campsegId
	 * @return
	 */
	public List<Map<String,Object>> searchIMcdCampsegInfo(McdCampDef segInfo,Pager pager);
	/**
	 * 修改营销活动信息  浙江IMCD
	 * @param seginfoList
	 * @throws MpmException
	 */
	public String updateCampSegWaveInfoZJ(List<McdCampDef> seginfoList) throws MpmException;
	
    /**
     * gaowj3
     * JDBC查询业务状态
     * @return
     */
    public List<DimCampDrvType> getDimCampSceneList();
    /**
     * gaowj3
     * JDBC查询策略状态
     * @return
     */
    public List<McdDimCampStatus> getDimCampsegStatList();

	public String saveCampSegWaveInfoZJ(List<McdCampDef> campSegInfoList);

	void saveCampsegCustGroupZJ(String campsegId, String custGroupIdStr, String userId, McdCampDef segInfo,String flag) throws MpmException;


	/**
	 * add by gaowj3 20150722
	 * @Title: updateCampStat
	 * @Description: 提交审批XML
	 * @param campsegId   
	 * @return String 
	 * @throws
	 */
	public String submitApprovalXml(String campsegId);

	List<McdCampDef> getChildCampSeginfo(String campsegId);
    /**
     * 根据渠道ID获取渠道
     * @param planId
     * @return
     */
    public McdPlanDef getMtlStcPlanByPlanId(String planId);
    /**
     * 取客户群选择（目标客户群”及“对比客户群”信息）
     * @param campsegId
     * @return
     * @throws MpmException
     */
    public List<McdCampCustgroupList> getCustGroupSelectList(String campsegId) throws MpmException;
    /**
     * 根据策略ID查询渠道信息
     * @param campsegId
     * @return
     */
    public List<Map<String,Object>> getMtlChannelDefs(String campsegId);
    /**
     * 根据编号删除策略信息
     * @param campSegId
     * @throws MpmException
     */
    public void deleteCampSegInfo(String campSegId) throws MpmException;

    public String addCustGroupTabAsCustTable1(String tabPrefix,String custGroupId)throws MpmException;

    /**
     * 活动延期
     * @param campSegId
     * @param endDate
     * @return
     * @throws Exception
     */
    public void updateCampsegEndDate(String campsegId, String endDate) throws Exception;
    /**
     * 营销活动主键查询
     * @param campSegId
     * @return
     * @throws MpmException
     */
    public McdCampDef getCampSegInfo(String campSegId) throws MpmException;
    /**
     * 撤销工单
     * @param campsegId  策略ID
     * @param ampsegStatId  撤消后的住哪个台
     * @param approve_desc  处理结果描述
     */
    public void cancelAssignment(String campsegId, short ampsegStatId,String approve_desc);
    /**
     * 保存暂停/停止原因
     * @param campsegId  父策略ID
     * @param pauseComment  暂停原因
     */
    public void updatMtlCampSeginfoPauseComment(String campsegId, String pauseComment);
    /**
     * add by gaowj3 20150721
     * @Title: updateCampStat
     * @Description: 更改策略状态（浙江版）
     * @param @param campsegId
     * @param @return
     * @param @throws Exception    
     * @return List 
     * @throws
     */
    public void updateCampStat(String campsegId, String type);
    /**
     * 获取有营销用语的渠道的营销用语
     * @param campsegId
     * @return
     */
    public List<Map<String,Object>> getExecContentList(String campsegId);
    /**
     * 获取营销用语变量
     * @param campsegId
     * @return
     */
    public List<Map<String,Object>> getExecContentVariableList(String campsegId);
    /**
     * 保存营销用语
     * @param campsegId
     * @param channelId
     * @param execContent
     * @param ifHasVariate 
     */
    public void saveExecContent(String campsegId, String channelId, String execContent, String ifHasVariate);
    /**
	 * 根据活动编号查出自身及所有波次(递归方式，包括自己)
	 * @param campsegId
	 * @return
	 * @throws MpmException
	 */
	public List<McdCampDef> getCampSeginfoListByCampsegId(String campsegId) throws MpmException;
	
	/**
	 * 根据campsegId删除业务标签或者基础标签 add by lixq10  只删除时机对应的数据
	 * @param campsegId
	 * @return
	 */
	public boolean deleteLableByCampsegId(String campsegId);
	void updateCampsegInfo(McdCampDef segInfo);
	/**
	 * 根据营销状态ID获取营销状态
	 * @param string
	 * @return
	 */
    public McdDimCampStatus getDimCampsegStat(String dimCampsegStatID);
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
    public List<Map<String,Object>> getTargetCustomerbase(String campsegId) throws Exception;
    /**
     * 获取细分规则信息（时机）
     * @param campsegId
     * @return
     */
    public List<Map<String, Object>> getrule(String campsegId);
    /**
     * 查询本地审批日志
     * @param approveFlowid
     * @return
     */
    public McdApproveLog getLogByFlowId(String approveFlowid);
    /**
     * 根据策略id获得策略的所有渠道
     * @param campsegId
     * @return
     */
    public List<Map<String,Object>> getChannelsByCampIds(String campsegIds);
    /**
     * 查询指定策略指定渠道在指定时间段内的营销情况
     * @param campsegId
     * @param channelId
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Map<String,Object>> getCampChannelDetail(String campsegId, String channelId, String startDate, String endDate);
    /**
     * 查询某策略某个指定渠道的所有子策略某天的执行情况   
     * @param campsegIds
     * @param channelId
     * @param statDate
     * @return
     */
    public List<Map<String,Object>> getCampsChannelSituation(String campsegIds, String channelId, String statDate);
    
    /**
	 * 根据产品id查询适配渠道
	 * @param planId
	 * @return
	 */
	public List<McdPlanChannelList> getStcPlanChannel(String planId);
    /**
     * 浙江处理策略审批结束（通过或退回）后的信息
     * @param campsegId 营销活动编号
     * @param approveResult 最终审批结果(1 - 审批通过；2 - 退回)
     */
    public String processApproveInfo(String approveInfoXML);
    
	/**
	 * 根据客户群清单表字段进行创建
	 * @param tabPrefix
	 * @return
	 * @throws MpmException
	 */
	public String createCustGroupTabAsCustTable(String tabPrefix,String custGroupId)throws MpmException;
	
	/**
	 * 为Duser表创建索引
	 * @param tableName
	 */
	public void createDuserIndex(String tableName,String custGroupId);
	
	/*
	 * 根据业务标签  ARPU 计算我的客户群数量  added by zhanghy2 2015-12-06
	 */
	public int excuteCustGroupCount(String customgroupid,McdTempletForm bussinessLableTemplate,McdTempletForm basicEventTemplate, Locale local,String orderProductNo, String excludeProductNo);
	
	/**
	 * 新的方式插入清单表数据
	 */
	public void insertCustGroupNewWay(String customgroupid,McdTempletForm bussinessLableTemplate,McdTempletForm basicEventTemplate,Locale local,String orderProductNo,String excludeProductNo,String tableName,boolean removeRepeatFlag);
	/**
	 * 保存策略
	 * @param user 当前用户
	 * @param campSegInfoList 策略列表
	 * @throws Exception 
	 */
	public String saveOrUpdateCampInfo(User user, List<McdCampDef> campSegInfoList,Boolean isModify) throws Exception;
	/**
	 * 根据父策略，查询子策略
	 * @param pid
	 * @return
	 * @throws MpmException
	 */
	public McdCampDef getCampByPid(String pid) throws MpmException;
    /**
     * 根据客户群ID查找用到该客户群的处于（待执行）的策略
     * @param customGroupId
     * @return 
     */
    public List<Map<String,Object>>  getCustGroupSelectByGroupIdList(String customGroupId);
    /**
     * 查询策略下所有 
     * @param campsegId渠道
     * @return
     */
    public List<McdCampChannelList> getChannelByCampsegId(String campsegId);
	
	
	
}
