package com.asiainfo.biapp.mcd.tactics.vo;

@SuppressWarnings("serial")
public class MtlChannelDefCall implements java.io.Serializable {

	// Fields

	public short getApproveResult() {
		return approveResult;
	}
	public void setApproveResult(short approveResult) {
		this.approveResult = approveResult;
	}
	public String getApproveResultDesc() {
		return approveResultDesc;
	}
	public void setApproveResultDesc(String approveResultDesc) {
		this.approveResultDesc = approveResultDesc;
	}
	public Integer getChannelPrio() {
		return channelPrio;
	}
	public void setChannelPrio(Integer channelPrio) {
		this.channelPrio = channelPrio;
	}
	@javax.persistence.Column(name="CAMPSEG_ID")
	private String  campsegId;
	@javax.persistence.Column(name="CHANNEL_ID")
	private String  channelId;
	@javax.persistence.Column(name="TASK_CODE")
	private String taskCode;//任务编码
	@javax.persistence.Column(name="TASK_NAME")
	private String taskName;//任务名称
	@javax.persistence.Column(name="DEMAND")
	private String demand;//需求方
	@javax.persistence.Column(name="TASK_CLASS_ID")
	private String taskClassId;//任务分类
	@javax.persistence.Column(name="TASK_LEVEL1_ID")
	private String tasklevel1Id;//任务类型一级
	@javax.persistence.Column(name="TASK_LEVEL2_ID")
	private String taskLevel2Id;//任务类型二级
	@javax.persistence.Column(name="TASK_LEVEL3_ID")
	private String taskLevel3Id;//任务类型三级
	@javax.persistence.Column(name="BUSI_LEVEL1_ID")
	private String busiLevel1Id;//业务类型一级
	@javax.persistence.Column(name="BUSI_LEVEL2_ID")
	private String busiLevel2Id;//业务类型二级	
	@javax.persistence.Column(name="IN_PLAN_FLAG")
	private Integer inPlanFlag;//是否计划内
	@javax.persistence.Column(name="MONTH_PLAN_FLAG")
	private Integer monthPlanFlag;//关联月度计划
	@javax.persistence.Column(name="CALL_CYCLE")
	private Integer callCycle;//外呼周期
	@javax.persistence.Column(name="CALL_PLAN_NUM")
	private Integer callPlanNum;//计划外呼量
	@javax.persistence.Column(name="FINISH_DATE")
	private String finishDate;//要求完成时间
	@javax.persistence.Column(name="TASK_COMMENT")
	private String taskComment;//任务描述
	@javax.persistence.Column(name="USER_LABLE_INFO")
	private String 	userLableInfo;//客户标签信息
	@javax.persistence.Column(name="CALL_QUESTION_URL")
	private String 	callQuestionUrl;//外呼问卷地址	
	@javax.persistence.Column(name="CALL_QUESTION_NAME")
	private String 	callQuestionName;//外呼问卷名称	
	
	public String getCampsegId() {
		return campsegId;
	}
	public void setCampsegId(String campsegId) {
		this.campsegId = campsegId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getCallQuestionName() {
		return callQuestionName;
	}
	public void setCallQuestionName(String callQuestionName) {
		this.callQuestionName = callQuestionName;
	}
	private String callNo;//主叫号码
	private Integer avoidFilterFlag;//是否要清洗黑红白名单
	private Integer callTestFlag;//是否拨测
	private Integer freFilterFlag;//是否需要进行频次清洗
	private String 	callForm;//外外呼形式问卷地址	
	private String callCityType;//外呼属地
	
	private short approveResult;//审批结果
	private String  approveResultDesc;//审批结果描述
	private Integer channelPrio;//优先级
	
	
	
	

	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getDemand() {
		return demand;
	}
	public void setDemand(String demand) {
		this.demand = demand;
	}
	public String getTaskClassId() {
		return taskClassId;
	}
	public void setTaskClassId(String taskClassId) {
		this.taskClassId = taskClassId;
	}
	public String getTasklevel1Id() {
		return tasklevel1Id;
	}
	public void setTasklevel1Id(String tasklevel1Id) {
		this.tasklevel1Id = tasklevel1Id;
	}
	public String getTaskLevel2Id() {
		return taskLevel2Id;
	}
	public void setTaskLevel2Id(String taskLevel2Id) {
		this.taskLevel2Id = taskLevel2Id;
	}
	public String getTaskLevel3Id() {
		return taskLevel3Id;
	}
	public void setTaskLevel3Id(String taskLevel3Id) {
		this.taskLevel3Id = taskLevel3Id;
	}
	public String getBusiLevel1Id() {
		return busiLevel1Id;
	}
	public void setBusiLevel1Id(String busiLevel1Id) {
		this.busiLevel1Id = busiLevel1Id;
	}
	public String getBusiLevel2Id() {
		return busiLevel2Id;
	}
	public void setBusiLevel2Id(String busiLevel2Id) {
		this.busiLevel2Id = busiLevel2Id;
	}
	public Integer getInPlanFlag() {
		return inPlanFlag;
	}
	public void setInPlanFlag(Integer inPlanFlag) {
		this.inPlanFlag = inPlanFlag;
	}
	public Integer getMonthPlanFlag() {
		return monthPlanFlag;
	}
	public void setMonthPlanFlag(Integer monthPlanFlag) {
		this.monthPlanFlag = monthPlanFlag;
	}
	public Integer getCallCycle() {
		return callCycle;
	}
	public void setCallCycle(Integer callCycle) {
		this.callCycle = callCycle;
	}
	public Integer getCallPlanNum() {
		return callPlanNum;
	}
	public void setCallPlanNum(Integer callPlanNum) {
		this.callPlanNum = callPlanNum;
	}
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	public String getTaskComment() {
		return taskComment;
	}
	public void setTaskComment(String taskComment) {
		this.taskComment = taskComment;
	}
	public String getUserLableInfo() {
		return userLableInfo;
	}
	public void setUserLableInfo(String userLableInfo) {
		this.userLableInfo = userLableInfo;
	}
	
	public String getCallQuestionUrl() {
		return callQuestionUrl;
	}
	public void setCallQuestionUrl(String callQuestionUrl) {
		this.callQuestionUrl = callQuestionUrl;
	}
	public String getCallNo() {
		return callNo;
	}
	public void setCallNo(String callNo) {
		this.callNo = callNo;
	}
	public Integer getAvoidFilterFlag() {
		return avoidFilterFlag;
	}
	public void setAvoidFilterFlag(Integer avoidFilterFlag) {
		this.avoidFilterFlag = avoidFilterFlag;
	}
	public Integer getCallTestFlag() {
		return callTestFlag;
	}
	public void setCallTestFlag(Integer callTestFlag) {
		this.callTestFlag = callTestFlag;
	}
	public Integer getFreFilterFlag() {
		return freFilterFlag;
	}
	public void setFreFilterFlag(Integer freFilterFlag) {
		this.freFilterFlag = freFilterFlag;
	}
	public String getCallForm() {
		return callForm;
	}
	public void setCallForm(String callForm) {
		this.callForm = callForm;
	}
	public String getCallCityType() {
		return callCityType;
	}
	public void setCallCityType(String callCityType) {
		this.callCityType = callCityType;
	}
	

	
	
}
