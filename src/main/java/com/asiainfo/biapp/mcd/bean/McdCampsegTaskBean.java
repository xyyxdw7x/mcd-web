package com.asiainfo.biapp.mcd.bean;

import java.util.Date;

/**
 * 任务业务bean
 * @author lixiangqian
 *
 */
public class McdCampsegTaskBean extends BaseBean implements java.io.Serializable {
	private static final long serialVersionUID = 5453221184725944949L;
	/**
	 * 任务id
	 */
	private String taskId;
	/**
	 * 活动id
	 */
	private String campsegId;
	/**
	 * 根活动id
	 */
	private String rootCampsegId;
	/**
	 * 根活动名称
	 */
	private String rootCampsegName;
	/**
	 * 活动策划人
	 */
	private String createUserid;
	/**
	 * 任务执行日期
	 */
	private Date execTime;
	/**
	 * 计划开始日期
	 */
	private Date taskStartTime;
	/**
	 * 计划结束日期
	 */
	private Date taskEndTime;
	private Short execStatus;
	private Integer ciCustgroupNumber;
	private Integer taskCustNumber;
	private Integer taskSendOddNumber;
	private String custListTabName;
	private String taskSendoddTabName;
	private String ciCustgroupTabName;
	private String execInfoDesc;

	/**
	 * 关联活动客户群名称
	 */
	private String custGroupName;

	/**
	 * 关联活动营销用语
	 */
	private String campContent;

	/**
	 * 执行渠道
	 */
	private String chanelName;
	
	/**
	 * 预计派单数
	 */
	private Integer planCount;
	/**
	 * 规则是否挂接热点业务 1挂接、0不挂接
	 */
	private Short isMountHotPlan;
	
	/**
	 * 热点业务ID
	 */
	private String hotPlanId;
	
	/**
	 * 挂载热点业务用语
	 */
	private String mountHotPlanContent;
	
	/**
	 * 热点业务用语(热点业务类型表中)
	 */
	private String hotPlanContent;
	
	/**
	 * 匹配类型，1-普适；2-精准营销
	 */
	private String matchHotPlanType;
	
	/**
	 * 匹配热点业务客户数
	 */
	private Integer matchCount;
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getCampsegId() {
		return campsegId;
	}

	public void setCampsegId(String campsegId) {
		this.campsegId = campsegId;
	}

	public String getCreateUserid() {
		return createUserid;
	}

	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}

	public Date getExecTime() {
		return execTime;
	}

	public void setExecTime(Date execTime) {
		this.execTime = execTime;
	}

	public Date getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

	public Date getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	public Short getExecStatus() {
		return execStatus;
	}

	public void setExecStatus(Short execStatus) {
		this.execStatus = execStatus;
	}

	public Integer getTaskCustNumber() {
		return taskCustNumber;
	}

	public void setTaskCustNumber(Integer taskCustNumber) {
		this.taskCustNumber = taskCustNumber;
	}

	public Integer getTaskSendOddNumber() {
		return taskSendOddNumber;
	}

	public void setTaskSendOddNumber(Integer taskSendOddNumber) {
		this.taskSendOddNumber = taskSendOddNumber;
	}

	public String getCustListTabName() {
		return custListTabName;
	}

	public void setCustListTabName(String custListTabName) {
		this.custListTabName = custListTabName;
	}

	public String getTaskSendoddTabName() {
		return taskSendoddTabName;
	}

	public void setTaskSendoddTabName(String taskSendoddTabName) {
		this.taskSendoddTabName = taskSendoddTabName;
	}

	public String getCiCustgroupTabName() {
		return ciCustgroupTabName;
	}

	public void setCiCustgroupTabName(String ciCustgroupTabName) {
		this.ciCustgroupTabName = ciCustgroupTabName;
	}

	public McdCampsegTaskBean() {
		super();
	}

	public String getRootCampsegId() {
		return rootCampsegId;
	}

	public void setRootCampsegId(String rootCampsegId) {
		this.rootCampsegId = rootCampsegId;
	}

	public String getRootCampsegName() {
		return rootCampsegName;
	}

	public void setRootCampsegName(String rootCampsegName) {
		this.rootCampsegName = rootCampsegName;
	}

	public Integer getCiCustgroupNumber() {
		return ciCustgroupNumber;
	}

	public void setCiCustgroupNumber(Integer ciCustgroupNumber) {
		this.ciCustgroupNumber = ciCustgroupNumber;
	}

	public String getExecInfoDesc() {
		return execInfoDesc;
	}

	public void setExecInfoDesc(String execInfoDesc) {
		this.execInfoDesc = execInfoDesc;
	}

	public String getCustGroupName() {
		return custGroupName;
	}

	public void setCustGroupName(String custGroupName) {
		this.custGroupName = custGroupName;
	}

	public String getCampContent() {
		return campContent;
	}

	public void setCampContent(String campContent) {
		this.campContent = campContent;
	}

	public String getChanelName() {
		return chanelName;
	}

	public void setChanelName(String chanelName) {
		this.chanelName = chanelName;
	}

	public Integer getPlanCount() {
		return planCount;
	}

	public void setPlanCount(Integer planCount) {
		this.planCount = planCount;
	}

	public Short getIsMountHotPlan() {
		return isMountHotPlan;
	}

	public void setIsMountHotPlan(Short isMountHotPlan) {
		this.isMountHotPlan = isMountHotPlan;
	}

	public String getHotPlanId() {
		return hotPlanId;
	}

	public void setHotPlanId(String hotPlanId) {
		this.hotPlanId = hotPlanId;
	}

	public String getMountHotPlanContent() {
		return mountHotPlanContent;
	}

	public void setMountHotPlanContent(String mountHotPlanContent) {
		this.mountHotPlanContent = mountHotPlanContent;
	}

	public String getMatchHotPlanType() {
		return matchHotPlanType;
	}

	public void setMatchHotPlanType(String matchHotPlanType) {
		this.matchHotPlanType = matchHotPlanType;
	}

	public String getHotPlanContent() {
		return hotPlanContent;
	}

	public void setHotPlanContent(String hotPlanContent) {
		this.hotPlanContent = hotPlanContent;
	}

	public Integer getMatchCount() {
		return matchCount;
	}

	public void setMatchCount(Integer matchCount) {
		this.matchCount = matchCount;
	}

	@Override
	public String toString() {
		return "McdCampsegTaskBean [taskId=" + taskId + ", campsegId=" + campsegId + ", rootCampsegId=" + rootCampsegId
				+ ", rootCampsegName=" + rootCampsegName + ", createUserid=" + createUserid + ", execTime=" + execTime
				+ ", taskStartTime=" + taskStartTime + ", taskEndTime=" + taskEndTime + ", execStatus=" + execStatus
				+ ", ciCustgroupNumber=" + ciCustgroupNumber + ", taskCustNumber=" + taskCustNumber
				+ ", taskSendOddNumber=" + taskSendOddNumber + ", custListTabName=" + custListTabName
				+ ", taskSendoddTabName=" + taskSendoddTabName + ", ciCustgroupTabName=" + ciCustgroupTabName
				+ ", execInfoDesc=" + execInfoDesc + ", custGroupName=" + custGroupName + ", campContent="
				+ campContent + ", chanelName=" + chanelName + ", planCount=" + planCount + ", isMountHotPlan="
				+ isMountHotPlan + ", hotPlanId=" + hotPlanId + ", mountHotPlanContent=" + mountHotPlanContent
				+ ", hotPlanContent=" + hotPlanContent + ", matchHotPlanType=" + matchHotPlanType + ", matchCount="
				+ matchCount + "]";
	}
}
