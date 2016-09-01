package com.asiainfo.biapp.mcd.bull.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.bean.CampSegInfoBean;
import com.asiainfo.biapp.mcd.bean.MtlCampSeginfoBean;
import com.asiainfo.biapp.mcd.exception.MpmException;
import com.asiainfo.biapp.mcd.form.MpmCampSelectForm;
import com.asiainfo.biapp.mcd.model.DimCustEvelType;
import com.asiainfo.biapp.mcd.model.McdCampsegPolicyRelation;
import com.asiainfo.biapp.mcd.model.MtlCampBaseinfo;
import com.asiainfo.biapp.mcd.model.MtlCampSegCostRelation;
import com.asiainfo.biapp.mcd.model.MtlCampsegCustGroup;
import com.asiainfo.biapp.mcd.model.MtlCampsegExecList;
import com.asiainfo.biapp.mcd.model.MtlPlanExecType;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfo;
import com.asiainfo.biapp.mcd.util.jdbcPage.Pager;

public interface IMpmCampSegInfoDao {

	/**
	 * 查询用户已经确认过的营销活动
	 * @param segInfo
	 * @param curPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Map findConfirmedCampsegInfo(MtlCampSeginfo segInfo, Integer curPage, Integer pageSize) throws Exception;

	/**
	 * 查询用户已经审批过的营销活动
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Map findApprovedCampsegInfo(MtlCampSeginfo segInfo, Integer curPage, Integer pageSize) throws Exception;

	/**
	 * 更新用户营销活动的渠道信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public void updateCampsegChannelInfo(String campId, String campsegId, short channelTypeId, String channelId)
			throws Exception;

	/**
	 * 按分页方式查询影响活动信息
	 * @param segInfo
	 * @param curPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Map searchCampsegInfo(MtlCampSeginfo segInfo, Integer curPage, Integer pageSize) throws Exception;

	/**
	 * 按分页方式查询影响活动信息给派单页面
	 * @param segInfo
	 * @param curPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Map searchCampsegInfoForSendOdd(MtlCampSeginfo segInfo, Integer curPage, Integer pageSize) throws Exception;

	/**
	 * 查询营销活动信息
	 * @param segInfo
	 * @return
	 * @throws Exception
	 */
	public List searchCampsegInfoWithUserseg(MtlCampSeginfo segInfo) throws Exception;

	/**
	 * 取所有活动信息
	 * @param userIds TODO
	 * @return List (value=MtlCampsegInfo)
	 */
	public List getCampSegObjList(String cityIds) throws Exception;

	/**
	 * 通过营销活动父ID查询直接子波次列表
	 * @param campsegPid
	 * @return
	 * @throws MpmException
	 */
	public List<MtlCampSeginfo> findCampsegListByPid(String campsegPid) throws Exception;

	/**
	 * 获取所有活动模版列表
	 * @param isCityAdminUser 
	 * @return
	 * @throws Exception
	 */
	public List<MtlCampSeginfo> getCampSegTemplateList(String createUser, String cityId, boolean isCityAdminUser)
			throws Exception;

	/**
	 * 获取所有场景模版列表
	 * @param isCityAdminUser 
	 * @return
	 * @throws Exception
	 */
	public List<MtlCampSeginfo> getCampSegSceneTemplateList(String createUser, String cityId, boolean isCityAdminUser,
			String sceneType) throws Exception;

	/**
	 *
	 */
	public List getCampSegObjListbyact(String cityIds, String userId) throws Exception;

	public MtlCampBaseinfo findCampByCampsegId(String campsegId) throws Exception;

	/**
	 *
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List getCampSegObjListbyfile(String userId) throws Exception;

	/**
	 * 取指定活动编号下的所有活动波次信息
	 * @param campId
	 * @return List (value=MtlCampsegInfo)
	 * @throws Exception
	 */
	public List getCampSegByCampId(String campId) throws Exception;

	/**
	 * 获取活动下的所有波次信息
	 * @param campsegId
	 * @return
	 */
	public List<MtlCampSeginfo> getCampSeginfoListByCampsegId(String campsegId);

	/**
	 * 获取指定活动ID下的所有子节点（向下递归包含自身）
	 * @param savedCampsegId
	 * @return
	 */
	public List<String> getSubCampsegInfoTreeList(String campsegId) throws Exception;

	/**
	 * 取某个活动下具有某些波次状态的波次信息
	 * @param campId
	 * @param citys TODO
	 * @param campDrvId TODO
	 * @param stepId TODO
	 * @return
	 * @throws Exception
	 */
	public List findCampSeg(String campId, Short[] campsegStats, String citys, Short campDrvId, Short stepId)
			throws Exception;

	/**
	 * 根据编号取具体的某个活动波次信息
	 * @param campSegId
	 * @return
	 * @throws Exception
	 */
	public MtlCampSeginfo getCampSegInfo(String campSegId) throws Exception;

	/**
	 * 保存活动波次信息
	 * @param segInfo
	 * @throws Exception
	 */
	public Serializable saveCampSegInfo(MtlCampSeginfo segInfo) throws Exception;
	
	/**
	 * 根据活动波次编号删除活动波次信息（包括活动波次模版、活动波次步骤、活动波次分组等信息）
	 * @param campSegId
	 * @throws Exception
	 */
	public void deleteCampSegInfo(String campSegId) throws Exception;

	/**
	 * 2013-6-2 15:39:52
	 * @author Mazh
	 * 根据活动波次编号删除活动波次信息（包括活动波次模版、活动波次步骤、活动波次分组等信息）
	 * @param campSegId
	 * @throws Exception
	 */
	public void deleteIMcdCampSegInfo(String campSegId) throws Exception;

	/**
	 * 根据编号删除活动波次的附件
	 * @param campId
	 * @throws Exception
	 */
	public void deleteCampSegAttach(String campId) throws Exception;

	/**
	 * 更新活动波次信息
	 * @param segInfo
	 * @throws Exception
	 */
	public void updateCampSegInfo(MtlCampSeginfo segInfo) throws Exception;

	/**
	 * 更新活动波次审批信息
	 * @param campId
	 * @param approveFlag
	 * @throws Exception
	 */
	public void updateCampSegApproveFlag(String campId, short approveFlag) throws Exception;

	/**
	 * 取某个活动下当前最大的活动波次序列号（从1开始递增）
	 * @param campId
	 * @return
	 * @throws Exception
	 */
	public int getMaxSegNo(String campId) throws Exception;

	/**
	 * 更新活动波次的对比用户筛选条件
	 * @param campsegId
	 * @param contrastSql
	 * @param contrastCn
	 * @throws Exception
	 */
	public void updateCampSegContrastSql(String campsegId, String contrastSql, String contrastCn) throws Exception;

	/**
	 * 更新活动波次的用户排序条件
	 * @param campsegId
	 * @param orderSql
	 * @param orderSqlCn
	 * @throws Exception
	 */
	public void updateCampSegOrderSql(String campsegId, String orderSql, String orderSqlCn) throws Exception;

	/**
	 * 取某个活动下有多少活动波次处于正在审批或审批通过或已派单
	 * @param campId
	 * @return
	 * @throws MpmException
	 */
	public int getCampSegAuditOrRunCnt(String campId) throws Exception;

	/**
	 *取波次前一个波次的状态
	 * @param campId
	 * @return
	 * @throws MpmException
	 */
	public boolean getPreCampSegStatus(String campId, String campsegNo) throws Exception;

	/**
	 * 判断活动波次是否可以删除
	 * @param campId
	 * @return
	 * @throws Exception
	 */
	public boolean isCampsegCanDelete(String campsegId) throws Exception;

	/**
	 * 判断活动是否是自己或admin创建
	 * @param campId
	 * @return
	 * @throws Exception
	 */
	public boolean isCampSegBySelfOrAdmin(String campsegId, String userId) throws Exception;

	/**
	 * 修改和审批有关的活动波次的状态
	 * @param campsegId
	 * @param approveResult
	 * @param campsegStatId
	 * @param approveReceiveNums
	 * @throws Exception
	 */
	public void updateCampsegApproveStatus(String campsegId, short approveResult, String campsegStatId)
			throws Exception;

	/**
	 * 更新活动波次随机抽取标志
	 * @param campsegId
	 * @param campsegRandomFlag
	 */
	public void updateCampSegRandomFlag(MpmCampSelectForm selForm) throws Exception;

	/**
	 * 取活动下属波次的最后结束日期
	 * @param campId
	 * @return
	 * @throws Exception
	 */
	public String getLastCampsegEndDate(String campId) throws Exception;

	/**
	 * 判断该用户建立的活动是不是正在被审批
	 * @param createUserid
	 * @return
	 * @throws Exception
	 */
	public boolean isCampsegApproveExist(String createUserid) throws Exception;

	/**
	 * 更新活动波次状态
	 * @param campid
	 * @param campsegId
	 * @param campsegStatId
	 * @throws Exceptin
	 */
	public void updateCampsegStatus(String campid, String campsegId, Short campsegStatId) throws Exception;

	/**
	 * 更新活动波次总结信息
	 * @param campsegId
	 * @param comment
	 * @throws Exception
	 */
	public void updateCampsegEvaluateComment(String campsegId, String comment) throws Exception;

	/**
	 * 查询需要审批提醒的活动波次
	 * @return
	 * @throws Exception
	 */
	public List getCampsegByDate() throws Exception;

	/**
	 * 更改活动波次的最大用户数
	 * @param campsegId
	 * @param campsegMaxUsers
	 * @throws Exception
	 */
	public void updateCampsegMaxUsers(String campsegId, Integer campsegMaxUsers) throws Exception;

	/**
	 * 取客户群选择（目标客户群”及“对比客户群”信息）
	 * @param campsegId
	 * @return
	 * @throws MpmException
	 */
	public List getCustGroupSelectList(String campsegId) throws MpmException;

	/**
	 * 取可用的客户群定义
	 * @param campsegId
	 * @return
	 * @throws MpmException
	 */
	public List getCustGroupList() throws MpmException;

	/**
	 * 保存客户群选择信息
	 * @param mtlCampsegCustGroup
	 * @throws MpmException
	 */
	public void saveCampsegCustGroup(MtlCampsegCustGroup mtlCampsegCustGroup) throws MpmException;

	public void updateCampsegApproveFlag(String campId, Short approveFlag) throws Exception;

	public void deleteCampsegCustGroup(String campsegId) throws MpmException;

	//	caihao add
	public void updateCampApproveStatus(String campId, short campStatus, short approveResult) throws Exception;

	public void updateCampsegReceiveNums(String campsegId, short receiveNums

	) throws Exception;

	public void updateCampReceiveNums(String campId, short receiveNums) throws Exception;

	public List findAllCampsegNeedConfirm(String campsegIds) throws Exception;

	public void updateAllCampsegStatus(List campsegList, Short campsegStatId) throws Exception;

	public void updateCampsegConfirmFlag(String campsegId, short confirmFlag) throws Exception;

	public String findCampIdByCampsegId(String campsegId) throws Exception;

	/**
	 * 辽宁个性化接口表
	 * @return
	 * @throws MpmException
	 */
	public List findInterfaceTable() throws MpmException;

	/**
	 * 修改营销活动的审批结果
	 * @param campId
	 * @param approveResult
	 * @throws Exception
	 */
	public void updateCampSegApproveResult(String campId, String approveResult) throws Exception;

	/**
	 * 通过审批结果状态查看营销活动。
	 * @param approveResult
	 * @return
	 * @throws Exception
	 */
	public List getCampSegByApproveresult(String approveResult) throws Exception;

	/**
	 * 获取活动完成的
	 * @return
	 * @throws Exception
	 */
	public List getCampSegObjListbyExec(String campsegStatId) throws Exception;

	/**
	 * 获取需要外呼类派单的营销活动
	 * @param cityIds
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List getCampSegObjListbyCall(String cityIds, String userId) throws Exception;

	/**
	 * 获取需要短信类派单的营销活动
	 * @param cityIds
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List getCampSegObjListbySMS(String cityIds, String userId) throws Exception;

	/**
	 * 通过营销活动ID查询营销活动类型
	 * 判断是否为2，实时营销
	 * added by jianglc
	 * @param campsegid
	 * @return
	 * @throws MpmException
	 */
	public short findCampTypeByCampSegId(String campid) throws MpmException;

	/**
	 * 获取需要网站类派单的营销活动
	 * @param cityIds
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List getCampSegObjListbyWeb(String cityIds, String userId) throws Exception;

	/**
	 * 取活动执行类型
	 * @param planExecId
	 * @return
	 * @throws MpmException
	 */
	public MtlPlanExecType getMtlPlanExecType(String planExecId) throws MpmException;

	/**
	 * 保存计划执行活动信息
	 * @param mtlCampsegExecList
	 * @throws MpmException
	 */
	public void saveMtlPlanExecType(MtlCampsegExecList mtlCampsegExecList) throws MpmException;

	public List getAllChannel() throws Exception;

	/**
	 * params 日历查询参数；
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<String[]> getCalendarCampseg(String[] params) throws Exception;

	public List<String[]> getCampsegNumberByType(Integer typeId) throws Exception;

	public List<String[]> getCampsegNumberByStatus(Integer StatusId) throws Exception;

	/**
	 * 取得活动列表供外部使用；
	 * @param segInfo
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws Exception
	 */
	public List searchCampsegInfoExtenal(MtlCampSeginfo segInfo, final Integer firstResult, final Integer maxResults)
			throws Exception;

	/**
	 * 获取所有(符合条件的)营销活动集合
	 * @return
	 */
	public List<MtlCampSeginfo> getAllCampsegInfo();

	/**
	 * 按分页方式查询影响活动信息
	 * 2013-5-30 14:20:54
	 * @author Mazh
	 * @param segInfo
	 * @param baseList
	 * @param curPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Map searchIMcdCampsegInfo(MtlCampSeginfo segInfo, Map map, Integer curPage, Integer pageSize)
			throws Exception;

	/**
	 * 查询营销活动波次信息
	 * 2013-5-31 13:03:49
	 * @author Mazh
	 * @param campSegId
	 * @return
	 * @throws Exception
	 */
	public Map queryCampSegDetail(String campSegId) throws Exception;

	/**
	 * 修改营销活动以及波次状态
	 * 2013-6-2 15:16:58
	 * @author Mazh
	 * @param campSegId
	 * @param pType
	 */
	public String updateCampStat(List<String> pList, String pType) throws Exception;

	/**
	 * 修改营销活动任务状态
	 * 2013-6-8 16:53:33
	 * @author Mazh
	 * @param campSegId
	 * @param pType
	 */
	public String updateTaskStat(List<String> rList, String pType) throws Exception;

	/**
	 * 根据父活动Id查询一下层次规则或波次
	 * @param p_campSegId
	 * @param levelCount
	 * @return
	 * @throws Exception
	 */
	public List<MtlCampSeginfoBean> queryCampSegInfoDetailByPid(String p_campSegId, Integer levelCount)
			throws Exception;

	/**
	 * 查询营销活动客户群或波次信息
	 * @param campSegId
	 * @return MtlCampSeginfo
	 * @throws Exception
	 */
	public MtlCampSeginfoBean queryCampSegInfoDetailById(String campSegId) throws Exception;

	/**
	 * @author Mazh
	 * @param custListTabName
	 * @return
	 */
	public List<Map<String, Object>> getCustListTabCount(String custListTabName) throws Exception;

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
	 * 查询营销活动的规则
	 * 2013-6-4 18:04:41
	 * @author Mazh
	 * @param campSegId
	 * @return
	 */
	public List<CampSegInfoBean> getCampSegInfoOfClass2(String campSegId, boolean eventSourceFlag) throws Exception;

	public List getAllCampWaveCondition() throws Exception;

	/**
	 * 查询营销活动的波次
	 * 2013-6-4 18:39:16
	 * @author Mazh
	 * @param campSegId
	 * @return
	 */
	public List<CampSegInfoBean> getCampSegInfoOfClass3(String campSegId) throws Exception;

	/**
	 * IMCD删除营销活动
	 * 2013-6-9 17:59:26
	 * @author Mazh
	 * @param segInfo
	 */
	public void deleteCampSegInfo(MtlCampSeginfo segInfo) throws Exception;

	/**
	 *  当为第一波次时获取基础客户群信息
	 * @param campSeginfo
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getAllCustGroupNumberByMtlCampSeginfo(MtlCampSeginfo campSeginfo) throws Exception;

	/**
	 * 获取任务中客户群信息
	 * @param campSeginfo
	 * @param rList
	 */
	public List<Map<String, Object>> getCampSegTaskCustNumber(MtlCampSeginfo campSeginfo,
			List<Map<String, Object>> rList) throws Exception;

	/**
	 * 根据活动对象获得活动信息
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public List getCampSegByObject(MtlCampSeginfo object) throws Exception;

	/**
	 * 获取当前用户的历史发送信息
	 * @param user_id
	 * @return
	 */
	public List getHisCampContent(String user_id);

	/**
	 * 获取直接子活动
	 * @param campsegId
	 * @return
	 */
	public List getSubCampsegInfo(String campsegId);

	/**
	 * 跟新活动或营销案下所有子活动的状态
	 * @param campId
	 * @param campsegId
	 * @param campsegStatId
	 * @return
	 * @throws Exception
	 */
	public boolean updateAllCampsegStatus(String campId, String campsegId, String campsegStatId) throws Exception;

	public List<MtlCampSeginfo> getCampSegByCampIds(String ids) throws Exception;

	public List<MtlCampSeginfo> getMainCampSegListByDrvType(Short campDrvId) throws Exception;

	/**
	 * 查询用户下所有基本活动信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List getCampSegBaseInfo(String userId) throws Exception;

	/**
	 * 查询活动下所有子活下一级活动信息
	 * @param campsegId
	 * @return
	 * @throws Exception
	 */
	public List getChildCampSeginfo(String campsegId) throws Exception;

	/**
	 * 根据反馈状态获取活动的客户列表
	 * @param campsegId
	 * @param feedBackStatus
	 * @return
	 * @throws Exception
	 */
	public List getFeedBackCustList(String campsegId, String feedBackStatus) throws Exception;

	public int getFeedBackCustNum(String campsegId, String feedBackStatus);

	/**
	 * 根据渠道被当前哪些活动使用，或预定; 排除指定活动
	 * @param channelId
	 * @return
	 */
	public List<MtlCampSeginfo> getChannelUsed(String channelId, String campsegId);

	/**
	 * 获取已经被使用的渠道
	 * @param channelType
	 * @return
	 */
	public List<String> getChannelHaveUsed(String channelType);

	/**
	 * 根据营销案编号获取营销活动
	 * @param campId
	 * @return
	 * @throws Exception
	 */
	public List findCampSegsByCampId(String campId) throws Exception;

	/**
	 * 创建活动评估清单表
	 * @param campsegId
	 */
	public void createCampsegEvaluateTable(String campsegId);

	/**
	 * 获取所有已经占用的端口
	 * @param channelType
	 * @return
	 */
	public List<String> getChannelHaveUsedAll(String channelType);

	/**
	 * 活动活动信息(剔除模板)的
	 * @param subCampsegId
	 * @return
	 */
	public MtlCampSeginfo getCampsegBaseInfoBySubIdRejectOfTemple(String subCampsegId);

	/**
	 * 通过活动id,获得规则Id及规则名称(短信渠道)
	 * @param CampsegPid
	 * @return
	 * **/
	public Map<String, Map<String, String>> getChannelHaveUsedAll(String channelType, String channelId);

	/**
	 * 通过活动ID,向上递归拿到最顶父活动
	 * @param campsegId
	 * @return
	 */
	public MtlCampSeginfo getCampsegTopId(String campsegId) throws Exception;

	/**
	 * 获取需要网站类派单的营销活动(不包含模板)
	 * @param cityIds
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List getCampSegObjListForHD(String cityIds) throws Exception;

	/**
	 * 获取活动已派单的客户数
	 * @param campsegId
	 * @return
	 */
	public Double getHaveSendOddCustNum(String campsegId);

	/**
	 * 获取活动的历史任务
	 * @param campsegId
	 * @return
	 */
	public List findCampsegTaskHis(String campsegId, String status);

	/**2013-06-25
	 * 根据编号取得客户群的信息
	 *
	 */
	public List<String[]> getCampsegCustGroup(String campId) throws Exception;

	//根据营销活动主键获取营销案审批时间
	public List getApproveList(String campSegId) throws Exception;

	//获取营销活动策略列表
	public List<McdCampsegPolicyRelation> findCampsegPlicyList(String campsegId);

	//根据活动状态，事件源获取活动列表
	public List<MtlCampSeginfo> findCampSeginfoList(String campsegStatId, String eventSource);

	/**
	 * 检查属于外部数据源的非终止状态活动个数
	 * @return
	 */
	public int checkEventsourceCampsegNums();

	//获取所有有效的根活动
	public List<MtlCampSeginfo> getAllNormalBaseCampSeginfo();

	/**
	 * 递归获取所有波次的活动信息
	 * @param baseCampsegId——根活动ID
	 * @return
	 */
	public List<MtlCampSeginfo> getAllWaveCampsegs(String baseCampsegId) throws Exception;

	/**
	 * 获取引用营销规则的活动（子活动）
	 * @param ruleId
	 * @return
	 */
	public List getCampsegInfoOfRule(String ruleId);

	/**
	 * 获取当前子活动可终止的根活动ID，如果不可终止则返回null
	 * @param campsegId
	 * @return
	 * @throws Exception
	 */
	public String getCanFinishRootCampsegId(String campsegId) throws Exception;

	public void updateTargerUserById(String campsegId, Integer record) throws Exception;

	/**
	 * 获取指定状态营销活动
	 * @param campsegInfo	
	 * @param status
	 * @return
	 */
	public List<MtlCampSeginfo> searchCampsegInfo(MtlCampSeginfo campsegInfo, Short[] status) throws Exception;

	public long searchFeedbackInfo(String[] strings, String status) throws Exception;

	/**
	 * 
	 * saveCampSegCostInfo:保存营销活动成本
	 * @param campSegCostList
	 * @param campsegId
	 * @param is_containFee
	 * @throws Exception 
	 * @return void
	 */
	public void saveCampSegCostInfo(List<MtlCampSegCostRelation> campSegCostList, String campsegId,
			Integer is_containFee) throws Exception;

	/**
	 * 
	 * findMtlCampSegCostRelationByCampSegId:根据活动ID查询活动成本
	 * @param campsegId
	 * @return
	 * @throws Exception 
	 * @return List<MtlCampSegCostRelation>
	 */
	public List<MtlCampSegCostRelation> findMtlCampSegCostRelationByCampSegId(String campsegId) throws Exception;

	/**
	 * 活动活动信息
	 * @param subCampsegId
	 * @return
	 */
	public MtlCampSeginfo getCampsegBaseInfoBySubId(String subCampsegId);

	/**
	 * 按分页方式查询场景活动信息
	 * @param segInfo
	 * @param curPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Map sceneSearchIMcdCampsegInfo(MtlCampSeginfo segInfo, Map baseMap, Integer curPage, Integer pageSize)
			throws Exception;

	/**
	 * 查询客户群反馈类型
	 * @return
	 */
	public List<DimCustEvelType> getCustEvelType();

	/**
	 * 查询客户群反馈类型名字
	 * @return
	 */
	public String getCustEvelTypeName(String custEvelTypeId);

	/**
	 * 查询待派单的营销活动
	 * @param segInfo
	 * @param currUserId
	 * @return
	 */
	public List<MtlCampSeginfo> searchCampsegInfoForSendOdd(MtlCampSeginfo segInfo, String currUserId);
	
	/**
	 * 根据客户群ID获取营销活动信息   add by lixq10
	 * @param custGroupId
	 * @return
	 */
	public List<MtlCampSeginfo> getMtlcampSegInfo(String custGroupId);
	/**
	 * 策略管理查询分页
	 * @param 
	 * @return
	 */
	public List searchIMcdCampsegInfo(MtlCampSeginfo segInfo, Pager pager);

	/**
	 *  add by jinl 20150721
	 * @Title: getPlanDetailsByPlanId
	 * @Description:  匹配的政策，获取“产品详情”
	 * @param @param planid
	 * @param @return    
	 * @return String 
	 * @throws
	 */
	public String getPlanDetailsByPlanId(String planid);
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
	 * IMCD_ZJ 清单表生成成功的时候，更新策略信息清单表表名
	 * @param tableName
	 * @param campsegId
	 */
	public void updateCampsegInfo(MtlCampSeginfo segInfo);
	/**
	 *  add by gaowj3 20150805
	 * @Title: updateCampsegApproveStatusZJ
	 * @Description:  根据工单 编号获取策略
	 * @param @param assing_id 工单编号
	 * @return String 
	 * @throws
	 */
	public List getCampSegInfoByApproveFlowId(String assing_id);
	/**
	 *  add by gaowj3 20150805
	 * @Title: updateCampsegApproveStatusZJ
	 * @Description:  根据工单 编号获取字策略
	 * @param @param assing_id 工单编号
	 * @return String 
	 * @throws
	 */
	public List getChildCampSeginfoByAssingId(String assing_id);
    /**
     * 撤销工单
     * @param campsegId  策略ID
     * @param ampsegStatId  撤消后的住哪个台
     * @param approve_desc  处理结果描述
     */
	public void cancelAssignment(String campsegId, short ampsegStatId,
			String approve_desc);
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
	public void saveExecContent(String campsegId, String channelId,String execContent,String ifHasVariate);

	/**
	 * 根据子策略id更新策略基本信息以及本身的状态   add by lixq10
	 * @param campsegId
	 * @param campsegStatId
	 * @throws Exception
	 */
	public void updateCampsegStatusByCampsegId(String campsegId, Short campsegStatId) throws Exception;
	
	/**
	 * 将目标客户群数量、D表名称更新至策略主表  add by lixq10 2016年5月31日15:19:48
	 * @param campsegId
	 * @param tableName
	 * @param state
	 */
	public void updateCampsegById(String campsegId,String tableName,int targetUserNum);
	
	/**
	 * 根据策略id查询出策略信息
	 * @param campsegId
	 * @return
	 */
	public List getCampsegInfoById(String campsegId);
	
	/**
	 * 根据策略ID及类型，查找对应客户群或时机规则信息
	 * @param campsegId
	 * @param custGroupType
	 * @return
	 */
	public List getMtlCampsegCustGroup(String campsegId, String custGroupType);
    /**
     * 保存暂停/停止原因
     * @param campsegId  父策略ID
     * @param pauseComment  暂停原因
     */
	public void updatMtlCampSeginfoPauseComment(String campsegId,String pauseComment);

	/**
	 * 获取投放渠道(外呼渠道)
	 * @param campsegId
	 * @return
	 */
	public List getDeliveryChannelCall(String campsegId);
    /**
     * 更新策略的最后催单时间
     * @param campsegId
     * @param date
     */
	void updateRemindTime(String campsegId, Date date);

	public int getWhTaskSequence(String cityid ) throws Exception ;
    /**
     * 根据表明读取表结构（SQLFire）
     * @param tableName
     * @return
     */
    public List getSqlFireTableColumns(String tableName);
}
