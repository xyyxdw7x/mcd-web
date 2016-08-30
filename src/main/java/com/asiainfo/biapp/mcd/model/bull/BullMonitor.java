package com.asiainfo.biapp.mcd.model.bull;

public class BullMonitor {
	
	private String taskId;//任务编号
	private String campsegId;//策略编号
	private String campsegName;//策略名称
	private String showCampsegName;//策略名称------------------
	private String channelId;//渠道id (任务表)
	private short execStatus;//发送状态（任务在执行状态--任务表）
	private String execStatusName;//发送状态（任务在执行状态--任务表）--------------
	private String createTime;//提交时间（策略创建时间--策略表）------------------
	private String campPriId;//策略优先级
	private String filteredNum;//发送成功量
	private String  srcCustNum;//要发送数据
	
	private String pauseComment; //暂停理由
	
	private String createUserid;
	private String createUsername;
	
	private String taskSendoddTabName;//策略发送成功对应的表	
	private String deptId;  //科室id
	private String deptName;//科室名称
	private int sendNum;  //发送总量
	private String cityId;
	
	private String campsegNo;//规则序号
	
	private String campsegPid;
	
	public String getSrcCustNum() {
		return srcCustNum;
	}
	public void setSrcCustNum(String srcCustNum) {
		this.srcCustNum = srcCustNum;
	}

	public String getFilteredNum() {
		return filteredNum;
	}
	public void setFilteredNum(String filteredNum) {
		this.filteredNum = filteredNum;
	}
	public String getPauseComment() {
		return pauseComment;
	}
	public void setPauseComment(String pauseComment) {
		this.pauseComment = pauseComment;
	}
	
	public String getCampsegPid() {
		return campsegPid;
	}
	public void setCampsegPid(String campsegPid) {
		this.campsegPid = campsegPid;
	}
	public String getCampsegNo() {
		return campsegNo;
	}
	public void setCampsegNo(String campsegNo) {
		campsegNo = campsegNo;
	}
	public int getSendNum() {
		return sendNum;
	}
	public void setSendNum(int sendNum) {
		this.sendNum = sendNum;
	}
	public short getExecStatus() {
		return execStatus;
	}
	public void setExecStatus(short execStatus) {
		this.execStatus = execStatus;
	}
	public String getShowCampsegName() {
		return showCampsegName;
	}
	public void setShowCampsegName(String showCampsegName) {
		this.showCampsegName = showCampsegName;
	}
	public String getExecStatusName() {
		return execStatusName;
	}
	public void setExecStatusName(String execStatusName) {
		this.execStatusName = execStatusName;
	}
	public String getCreateUserid() {
		return createUserid;
	}
	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}
	public String getCreateUsername() {
		return createUsername;
	}
	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}
	
	public String getTaskSendoddTabName() {
		return taskSendoddTabName;
	}
	public void setTaskSendoddTabName(String taskSendoddTabName) {
		this.taskSendoddTabName = taskSendoddTabName;
	}
	public String getCampPriId() {
		return campPriId;
	}
	public void setCampPriId(String campPriId) {
		this.campPriId = campPriId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
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
	public String getCampsegName() {
		return campsegName;
	}
	public void setCampsegName(String campsegName) {
		this.campsegName = campsegName;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	
}
