package com.asiainfo.biapp.mcd.tactics.service;

import java.util.List;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.common.vo.plan.MtlStcPlan;
import com.asiainfo.biapp.mcd.tactics.exception.MpmException;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampDrvType;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfo;

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
	public List searchIMcdCampsegInfo(MtlCampSeginfo segInfo,Pager pager);
	/**
	 * 修改营销活动信息  浙江IMCD
	 * @param seginfoList
	 * @throws MpmException
	 */
	public String updateCampSegWaveInfoZJ(List<MtlCampSeginfo> seginfoList) throws MpmException;
	
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
    public List getDimCampsegStatList();

	public String saveCampSegWaveInfoZJ(List<MtlCampSeginfo> campSegInfoList);

	void saveCampsegCustGroupZJ(String campsegId, String custGroupIdStr, String userId, MtlCampSeginfo segInfo,String flag) throws MpmException;


	/**
	 * add by gaowj3 20150722
	 * @Title: updateCampStat
	 * @Description: 提交审批XML
	 * @param campsegId   
	 * @return String 
	 * @throws
	 */
	public String submitApprovalXml(String campsegId);

	List<MtlCampSeginfo> getChildCampSeginfo(String campsegId);
    /**
     * 根据渠道ID获取渠道
     * @param planId
     * @return
     */
    public MtlStcPlan getMtlStcPlanByPlanId(String planId);
    /**
     * 取客户群选择（目标客户群”及“对比客户群”信息）
     * @param campsegId
     * @return
     * @throws MpmException
     */
    public List getCustGroupSelectList(String campsegId) throws MpmException;
    /**
     * 根据策略ID查询渠道信息
     * @param campsegId
     * @return
     */
    public List getMtlChannelDefs(String campsegId);
    /**
     * 根据编号删除策略信息
     * @param campSegId
     * @throws MpmException
     */
    public void deleteCampSegInfo(String campSegId) throws MpmException;

    public String createCustGroupTabAsCustTable1(String tabPrefix,String custGroupId)throws MpmException;

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
    public MtlCampSeginfo getCampSegInfo(String campSegId) throws MpmException;
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
    public List getExecContentList(String campsegId);
    /**
     * 获取营销用语变量
     * @param campsegId
     * @return
     */
    public List getExecContentVariableList(String campsegId);
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
	public List<MtlCampSeginfo> getCampSeginfoListByCampsegId(String campsegId) throws MpmException;
	
	/**
	 * 根据campsegId删除业务标签或者基础标签 add by lixq10  只删除时机对应的数据
	 * @param campsegId
	 * @return
	 */
	public boolean deleteLableByCampsegId(String campsegId);
	void updateCampsegInfo(MtlCampSeginfo segInfo);
}
