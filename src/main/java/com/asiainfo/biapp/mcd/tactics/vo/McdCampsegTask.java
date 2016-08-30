package com.asiainfo.biapp.mcd.tactics.vo;

import java.util.Date;

/**
 * McdCampsegTask entity. @author MyEclipse Persistence Tools
 */

public class McdCampsegTask implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5227959451803088550L;
	private String taskId;
	private String campsegId;
	private Short execStatus;
	private String execInfoDesc;
	private Date execTime;
	private Date taskStartTime;
	private Date taskEndTime;
	private String custListTabName;
	private String taskSendoddTabName;
	private String ciCustgroupTabName;
	private int retry = 0;
	private int sendCount = 0;//实际派发到渠道的客户数
	private int planCount = 0;//客户群运算后客户数
	private String channelId;//新增渠道ID
	private int intGroupNum;//客户群数目
	private int botherAvoidNum;//免打扰控制数目
	private int contactControlNum;//频次控制数目
	private short cycleType;//周期类型


	// Constructors
	/** default constructor */
	public McdCampsegTask() {
	}

	/** minimal constructor */
	public McdCampsegTask(String campsegId) {
		this.campsegId = campsegId;
	}

	/** full constructor */
	public McdCampsegTask(String campsegId, Short execStatus, String execInfoDesc, Date execTime, Date taskStartTime,
			Date taskEndTime, String custListTabName, String taskSendoddTabName,String channelId) {
		this.campsegId = campsegId;
		this.execStatus = execStatus;
		this.execInfoDesc = execInfoDesc;
		this.execTime = execTime;
		this.taskStartTime = taskStartTime;
		this.taskEndTime = taskEndTime;
		this.custListTabName = custListTabName;
		this.taskSendoddTabName = taskSendoddTabName;
		this.channelId = channelId;
	}

	// Property accessors

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

	public Short getExecStatus() {
		return execStatus;
	}

	public void setExecStatus(Short execStatus) {
		this.execStatus = execStatus;
	}

	public String getExecInfoDesc() {
		return execInfoDesc;
	}

	public void setExecInfoDesc(String execInfoDesc) {
		this.execInfoDesc = execInfoDesc;
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

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		McdCampsegTask other = (McdCampsegTask) obj;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		return true;
	}

	public String getCiCustgroupTabName() {
		return ciCustgroupTabName;
	}

	public void setCiCustgroupTabName(String ciCustgroupTabName) {
		this.ciCustgroupTabName = ciCustgroupTabName;
	}

	public int getSendCount() {
		return sendCount;
	}

	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}

	public int getPlanCount() {
		return planCount;
	}

	public void setPlanCount(int planCount) {
		this.planCount = planCount;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public int getIntGroupNum() {
		return intGroupNum;
	}

	public void setIntGroupNum(int intGroupNum) {
		this.intGroupNum = intGroupNum;
	}

	public int getBotherAvoidNum() {
		return botherAvoidNum;
	}

	public void setBotherAvoidNum(int botherAvoidNum) {
		this.botherAvoidNum = botherAvoidNum;
	}

	public int getContactControlNum() {
		return contactControlNum;
	}

	public void setContactControlNum(int contactControlNum) {
		this.contactControlNum = contactControlNum;
	}

	public short getCycleType() {
		return cycleType;
	}

	public void setCycleType(short cycleType) {
		this.cycleType = cycleType;
	}
	

}