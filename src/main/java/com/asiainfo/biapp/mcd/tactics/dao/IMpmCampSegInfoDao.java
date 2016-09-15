package com.asiainfo.biapp.mcd.tactics.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampDrvType;
import com.asiainfo.biapp.mcd.tactics.vo.McdDimCampStatus;
import com.asiainfo.biapp.mcd.tactics.vo.McdApproveLog;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampCustgroupList;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampDef;

public interface IMpmCampSegInfoDao {
    /**
     * 策略管理查询分页
     * @param 
     * @return
     */
    public List<Map<String,Object>> searchIMcdCampsegInfo(McdCampDef segInfo, Pager pager);
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
	/**
	 * IMCD_ZJ 清单表生成成功的时候，更新策略信息清单表表名
	 * @param tableName
	 * @param campsegId
	 */
	public void updateCampsegInfo(McdCampDef segInfo);
    
	/**
	 * 保存活动波次信息
	 * @param segInfo
	 * @throws Exception
	 */
	public Serializable saveCampSegInfo(McdCampDef segInfo) throws Exception;
	/**
	 * 根据编号取具体的某个活动波次信息
	 * @param campSegId
	 * @return
	 * @throws Exception
	 */
	public McdCampDef getCampSegInfo(String campSegId) throws Exception;
	/**
	 * 查询活动下所有子活下一级活动信息
	 * @param campsegId
	 * @return
	 * @throws Exception
	 */
	public List<McdCampDef> getChildCampSeginfo(String campsegId) throws Exception;
    /**
     * 取客户群选择（目标客户群”及“对比客户群”信息）
     * @param campsegId
     * @return
     * @throws MpmException
     */
    public List<McdCampCustgroupList> getCustGroupSelectList(String campsegId);
	/**
	 * 更新活动波次信息
	 * @param segInfo
	 * @throws Exception
	 */
	public void updateCampSegInfo(McdCampDef segInfo) throws Exception;
    /**
     * 根据营销活动父节点查询父节点下所有节点 营销活动编码
     * 2013-6-4 16:38:32
     * @author Mazh
     * @param campSegId
     * @param rList
     * @return
     */
    public List<String> gettListAllCampSegByParentId(String campSegId, List<String> rList) throws Exception;
    /**
     * IMCD删除营销活动
     * 2013-6-9 17:59:26
     * @author Mazh
     * @param segInfo
     */
    public void deleteCampSegInfo(McdCampDef segInfo) throws Exception;
    /**
     * 修改策略完成时间（延期）
     * @param campsegId
     * @param endDate
     */
    public void updateCampSegInfoEndDate(String campsegId, String endDate);
    /**
     * 撤销工单
     * @param campsegId  策略ID
     * @param ampsegStatId  撤消后的住哪个台
     * @param approve_desc  处理结果描述
     */
    public void cancelAssignment(String campsegId, short ampsegStatId, String approve_desc);
    /**
     * 保存暂停/停止原因
     * @param campsegId  父策略ID
     * @param pauseComment  暂停原因
     */
    public void updatMtlCampSeginfoPauseComment(String campsegId, String pauseComment);
    /**
     * 修改营销活动任务状态
     * 2013-6-8 16:53:33
     * @author Mazh
     * @param campSegId
     * @param pType
     */
    public void updateCampStat(List<String> rList, String type);
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
	 * 获取直接子活动
	 * @param campsegId
	 * @return
	 */
	public List<McdCampDef> getSubCampsegInfo(String campsegId);
    /**
   * 根据营销状态ID获取营销状态
   * @param string
   * @return
   */
    public McdDimCampStatus getDimCampsegStat(String dimCampsegStatID);
    /**
     * 获取细分规则信息（时机）
     */
    public List<Map<String, Object>> getrule(String campsegId);
    /**
     * 查询本地审批日志
     * @param approveFlowid
     * @return
     */
    public McdApproveLog getLogByFlowId(String flowId);
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
     * @param campsegId
     * @param channelId
     * @param statDate
     * @return
     */
    public Map<String,Object> getCampChannelSituation(String campsegId, String channelId, String statDate);
    
	/**
	 * 通过活动ID,向上递归拿到最顶父活动
	 * @param campsegId
	 * @return
	 */
	public McdCampDef getCampsegTopId(String campsegId) throws Exception;
    /**
     * 根据工单 编号获取子策略（规则）
     * @param assing_id 审批工单号
     * @return
     */
    public List<Map<String,Object>> getChildCampSeginfoByAssingId(String assing_id);
    /**
     * 根据工单 编号获取策略（规则）
     * @param assing_id 审批工单号
     * @return
     */
    public List<Map<String,Object>> getCampSegInfoByApproveFlowId(String assing_id);
    /**
     *  add by gaowj3 20150728
     * @Title: updateCampsegApproveStatusZJ
     * @Description:  外部审批结束后修改状态方法
     * @param @param assing_id 工单编号
     * @param approve_desc   审批结果描述
     *  @param campsegStatId   策略状态
     * @return String 
     * @throws
     */
    public void updateCampsegApproveStatusZJ(String assing_id,String approve_desc,short approveResult, String campsegStatId)  throws Exception ;
    /**
     * 更改规则的执行状态为测试中  
     * 通过campseg——id
     * @param campseg_id
     * @param status
     */
    public void updateCampsegInfoState(String campsegPid, String mpmCampsegStatHdcs);
    /**
     * 根据策略获取策略相关客户群ID
     * @param campsegId
     * @return
     */
    public String getMtlCampsegCustGroupId(String campsegId);
    
	/**
	 * 根据策略id查询出策略信息
	 * @param campsegId
	 * @return
	 */
	public List<Map<String,Object>> getCampsegInfoById(String campsegId);
    /**
     * 根据表明读取表结构（SQLFire）
     * @param tableName
     * @return
     */
    public List<Map<String,Object>> getSqlFireTableColumnsInMem(String tableName);
    
    /**
     * 
     * @param sqlStr   在mcd_ad库执行
     */
    public void excSqlInMcdAdInMem(String sqlStr);
    
    /**
     * 
     * @param sqlStr:待执行的sql语句  在mcd库执行
     */
    public void excSqlInMcd(String sqlStr);
    
	/**
	 * 将目标客户群数量、D表名称更新至策略主表  add by lixq10 2016年5月31日15:19:48
	 * @param campsegId
	 * @param tableName
	 * @param state
	 */
	public void updateCampsegById(String campsegId,String tableName,int targetUserNum);
}
