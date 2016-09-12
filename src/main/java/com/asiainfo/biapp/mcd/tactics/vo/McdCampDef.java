package com.asiainfo.biapp.mcd.tactics.vo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author AsiaInfo-jie
 *
 */
@Table(name = "mcd_camp_def")
public class McdCampDef {
	@Id
	@Column(name = "CAMPSEG_ID")
	private String campId; // 活动编号

	@Transient
	private Integer pageNum; // 当前页
	@Transient
	private Integer isSelectMy; // 查询的是否是自己的 0 ：是 1 ：不是
	@Transient
	private Boolean isZJ;// 是否是浙江
	@Transient
	private Integer isMy;// 是否是自己创建的 0 ：是 1 ：不是
	@Transient
	private String channelId;
	@Transient
	private String keywords;// 关键字
	@Transient
	private boolean isFatherNode; // 是否为策略基础信息；true:是基本信息；false:子规则
	@Transient
	private String isApprove;
	@Transient
	private String custgroupId; // 客户群
	@Transient
	private List<MtlChannelDef> mtlChannelDefList; // 渠道执行
	

	@Column(name = "CAMPSEG_STAT_ID")
	private Short statId; // 营销活动状态ID
	@Column(name = "CREATE_USERID")
	private String createUserid; // 活动策划人
	@Column(name = "CAMP_PRI_ID")
	private Short priId;// 活动优先级,见维表:dim_camp_pri
	@Column(name = "CAMPSEG_NAME")
	private String campName;
	@Column(name = "START_DATE")
	private String startDate; // 开始时间
	@Column(name = "END_DATE")
	private String endDate; // 结束时间
	@Column(name = "CAMPSEG_PID")
	private String pid; // 活动编号父ID
	@Column(name = "CAMPSEG_TYPE_ID")
	private Short typeId; // 活动营销类型
	@Column(name = "PLAN_ID")
	private String planId; // 产品编码
	@Column(name = "IS_FILTER_DISTURB")
	private Integer isFileterDisturb;// 是否免打扰
	@Column(name = "DEPTID")
	private Integer deptId; // 策划人部门id
	@Column(name = "CREATE_TIME")
	private Date createTime; // 本活动定义时间

	@Column(name = "campseg_no")
	private String campNo; // 多规则时，规则序号
	@Column(name = "CEP_EVENT_ID")
	private String cepEventId; // 复杂事件ID
	@Column(name = "event_rule_desc")
	private String eventRuleDesc; // 实时事件名称
	@Column(name = "CITY_ID")
	private String cityId; // 策划人所属地市
	@Column(name = "APPROVE_FLOW_ID")
	private String approveFlowid; // 默认内部审批流程
	
	@Transient
	private Short approveResult; // 审批结果
	@Transient
	private String approveResultDesc;// 审批结果描述
	@Transient
	private String selectTempletId; // 筛选规则模板id
	@Transient
	private String custGroupAttrId;
	@Transient
	private Integer waveContactType; // 规则接触类型
	@Transient
	private Integer waveContactCount;
	@Column(name = "CAMP_CLASS")
	private Integer campClass; // 活动实际含义1：活动基本信息,2：规则,3：波次
	@Transient
	private Integer isRelativeExecTime; // 执行时间是否相对时间
	@Transient
	private String absoluteDates; // 绝对时间串，逗号分隔
	@Transient
	private String siteCategoryIdClassId;// 活动规则的渠道执行为综合网关时的内容站点、网站分类ID的关联(json字符串)
	@Transient
	private String channelCampContent; // 营销用语
	@Transient
	private String filePath;
	@Transient
	private MtlChannelDefCall mtlChannelDefCall;// 渠道对应表_外呼
	@Transient
	private String eventActiveTempletId;// 事件规则模板ID
	@Transient
	private String custBaseDay;
	@Transient
	private String custBaseMonth;
	@Column(name = "CREATE_USERNAME")
	private String createUserName;
	@Transient
	private String campsegDesc; // 活动描述
	@Transient
	private Integer contactedUserNums; // 联系客户数
	@Transient
	private Integer contactOkuserNums; // 联系成功客户数
	@Transient
	private Integer receivedOkuserNums; // 营销成功客户数
	@Transient
	private Integer campsegContactUsernums; // 活动被接触控制的用户数
	@Transient
	private Short campsegContactFlag; // 是否进行接触控制
	@Transient
	private String evaluateComment; // 活动中止原因
	@Transient
	private String custListTabName;// 客户群清单表名
	@Transient
	private String timeInterval;
	@Column(name = "TARGER_USER_NUMS")
	private Integer targerUserNums; // 目标客户数
	@Transient
	private String currentTaskId;// 当前任务编号
	@Transient
	private String avoidBotherTypeIds;// 免打扰客户类型ID
	@Transient
	private Integer targetCustType;// 客户群类型(0:无 ,1:客户群 2:来自其他活动规则的反馈)
	@Transient
	private String activeTempletId;// 时机规则模板ID
	@Column(name = "INIT_CUST_LIST_TAB")
	private String initCustListTab;// 策略初始清单表
	@Column(name = "APPROVE_REMIND_TIME")
	private Date approveRemindTime;
	@Transient
	private String eventSourceId; // 外部事件源
	@Transient
	private String planName;

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public boolean getIsFatherNode() {
		return isFatherNode;
	}

	public void setFatherNode(boolean isFatherNode) {
		this.isFatherNode = isFatherNode;
	}

	public String getCustBaseMonth() {
		return custBaseMonth;
	}

	public void setCustBaseMonth(String custBaseMonth) {
		this.custBaseMonth = custBaseMonth;
	}

	public String getCustBaseDay() {
		return custBaseDay;
	}

	public void setCustBaseDay(String custBaseDay) {
		this.custBaseDay = custBaseDay;
	}

	public String getEventActiveTempletId() {
		return eventActiveTempletId;
	}

	public void setEventActiveTempletId(String eventActiveTempletId) {
		this.eventActiveTempletId = eventActiveTempletId;
	}

	public MtlChannelDefCall getMtlChannelDefCall() {
		return mtlChannelDefCall;
	}

	public void setMtlChannelDefCall(MtlChannelDefCall mtlChannelDefCall) {
		this.mtlChannelDefCall = mtlChannelDefCall;
	}

	public String getSiteCategoryIdClassId() {
		return siteCategoryIdClassId;
	}

	public void setSiteCategoryIdClassId(String siteCategoryIdClassId) {
		this.siteCategoryIdClassId = siteCategoryIdClassId;
	}

	public String getChannelCampContent() {
		return channelCampContent;
	}

	public void setChannelCampContent(String channelCampContent) {
		this.channelCampContent = channelCampContent;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getAbsoluteDates() {
		return absoluteDates;
	}

	public void setAbsoluteDates(String absoluteDates) {
		this.absoluteDates = absoluteDates;
	}

	public Integer getIsRelativeExecTime() {
		return isRelativeExecTime;
	}

	public void setIsRelativeExecTime(Integer isRelativeExecTime) {
		this.isRelativeExecTime = isRelativeExecTime;
	}

	public Short getCampPriId() {
		return priId;
	}

	public void setCampPriId(Short campPriId) {
		this.priId = campPriId;
	}

	public Integer getCampClass() {
		return campClass;
	}

	public void setCampClass(Integer campClass) {
		this.campClass = campClass;
	}

	public Integer getWaveContactCount() {
		return waveContactCount;
	}

	public void setWaveContactCount(Integer waveContactCount) {
		this.waveContactCount = waveContactCount;
	}

	public Integer getWaveContactType() {
		return waveContactType;
	}

	public void setWaveContactType(Integer waveContactType) {
		this.waveContactType = waveContactType;
	}

	public String getCustGroupAttrId() {
		return custGroupAttrId;
	}

	public void setCustGroupAttrId(String custGroupAttrId) {
		this.custGroupAttrId = custGroupAttrId;
	}

	public String getCampsegId() {
		return campId;
	}

	public void setCampsegId(String campsegId) {
		this.campId = campsegId;
	}

	public String getSelectTempletId() {
		return selectTempletId;
	}

	public void setSelectTempletId(String selectTempletId) {
		this.selectTempletId = selectTempletId;
	}

	public String getApproveFlowid() {
		return approveFlowid;
	}

	public void setApproveFlowid(String approveFlowid) {
		this.approveFlowid = approveFlowid;
	}

	public Short getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(Short approveResult) {
		this.approveResult = approveResult;
	}

	public String getApproveResultDesc() {
		return approveResultDesc;
	}

	public void setApproveResultDesc(String approveResultDesc) {
		this.approveResultDesc = approveResultDesc;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public List<MtlChannelDef> getMtlChannelDefList() {
		return mtlChannelDefList;
	}

	public void setMtlChannelDefList(List<MtlChannelDef> mtlChannelDefList) {
		this.mtlChannelDefList = mtlChannelDefList;
	}

	public String getCustgroupId() {
		return custgroupId;
	}

	public void setCustgroupId(String custgroupId) {
		this.custgroupId = custgroupId;
	}

	public String getEventRuleDesc() {
		return eventRuleDesc;
	}

	public void setEventRuleDesc(String eventRuleDesc) {
		this.eventRuleDesc = eventRuleDesc;
	}

	public String getCepEventId() {
		return cepEventId;
	}

	public void setCepEventId(String cepEventId) {
		this.cepEventId = cepEventId;
	}

	public String getCampsegNo() {
		return campNo;
	}

	public void setCampsegNo(String campsegNo) {
		this.campNo = campsegNo;
	}

	public String getIsApprove() {
		return isApprove;
	}

	public void setIsApprove(String isApprove) {
		this.isApprove = isApprove;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCampsegPid() {
		return pid;
	}

	public void setCampsegPid(String campsegPid) {
		this.pid = campsegPid;
	}

	public Short getCampsegTypeId() {
		return typeId;
	}

	public void setCampsegTypeId(Short campsegTypeId) {
		this.typeId = campsegTypeId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public Integer getIsFileterDisturb() {
		return isFileterDisturb;
	}

	public void setIsFileterDisturb(Integer isFileterDisturb) {
		this.isFileterDisturb = isFileterDisturb;
	}

	public String getCampsegName() {
		return campName;
	}

	public void setCampsegName(String campsegName) {
		this.campName = campsegName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getIsSelectMy() {
		return isSelectMy;
	}

	public void setIsSelectMy(Integer isSelectMy) {
		this.isSelectMy = isSelectMy;
	}

	public Boolean getIsZJ() {
		return isZJ;
	}

	public void setIsZJ(Boolean isZJ) {
		this.isZJ = isZJ;
	}

	public Integer getIsMy() {
		return isMy;
	}

	public void setIsMy(Integer isMy) {
		this.isMy = isMy;
	}

	public Short getCampsegStatId() {
		return statId;
	}

	public void setCampsegStatId(Short campsegStatId) {
		this.statId = campsegStatId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getCreateUserid() {
		return createUserid;
	}

	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCampsegDesc() {
		return campsegDesc;
	}

	public void setCampsegDesc(String campsegDesc) {
		this.campsegDesc = campsegDesc;
	}

	public Integer getContactedUserNums() {
		return contactedUserNums;
	}

	public void setContactedUserNums(Integer contactedUserNums) {
		this.contactedUserNums = contactedUserNums;
	}

	public Integer getContactOkuserNums() {
		return contactOkuserNums;
	}

	public void setContactOkuserNums(Integer contactOkuserNums) {
		this.contactOkuserNums = contactOkuserNums;
	}

	public Integer getReceivedOkuserNums() {
		return receivedOkuserNums;
	}

	public void setReceivedOkuserNums(Integer receivedOkuserNums) {
		this.receivedOkuserNums = receivedOkuserNums;
	}

	public Short getCampsegContactFlag() {
		return campsegContactFlag;
	}

	public void setCampsegContactFlag(Short campsegContactFlag) {
		this.campsegContactFlag = campsegContactFlag;
	}

	public Integer getCampsegContactUsernums() {
		return campsegContactUsernums;
	}

	public void setCampsegContactUsernums(Integer campsegContactUsernums) {
		this.campsegContactUsernums = campsegContactUsernums;
	}

	public String getEvaluateComment() {
		return evaluateComment;
	}

	public void setEvaluateComment(String evaluateComment) {
		this.evaluateComment = evaluateComment;
	}

	public String getCustListTabName() {
		return custListTabName;
	}

	public void setCustListTabName(String custListTabName) {
		this.custListTabName = custListTabName;
	}

	public String getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}

	public Integer getTargerUserNums() {
		return targerUserNums;
	}

	public void setTargerUserNums(Integer targerUserNums) {
		this.targerUserNums = targerUserNums;
	}

	public String getCurrentTaskId() {
		return currentTaskId;
	}

	public void setCurrentTaskId(String currentTaskId) {
		this.currentTaskId = currentTaskId;
	}

	public String getAvoidBotherTypeIds() {
		return avoidBotherTypeIds;
	}

	public void setAvoidBotherTypeIds(String avoidBotherTypeIds) {
		this.avoidBotherTypeIds = avoidBotherTypeIds;
	}

	public Integer getTargetCustType() {
		return targetCustType;
	}

	public void setTargetCustType(Integer targetCustType) {
		this.targetCustType = targetCustType;
	}

	public String getActiveTempletId() {
		return activeTempletId;
	}

	public void setActiveTempletId(String activeTempletId) {
		this.activeTempletId = activeTempletId;
	}

	public String getInitCustListTab() {
		return initCustListTab;
	}

	public void setInitCustListTab(String initCustListTab) {
		this.initCustListTab = initCustListTab;
	}

	public Date getApproveRemindTime() {
		return approveRemindTime;
	}

	public void setApproveRemindTime(Date approveRemindTime) {
		this.approveRemindTime = approveRemindTime;
	}

	public String getEventSourceId() {
		return eventSourceId;
	}

	public void setEventSourceId(String eventSourceId) {
		this.eventSourceId = eventSourceId;
	}

}
