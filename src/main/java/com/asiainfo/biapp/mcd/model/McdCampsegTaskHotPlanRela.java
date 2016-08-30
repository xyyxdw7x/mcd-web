package com.asiainfo.biapp.mcd.model;

/**
 * 派单任务与热点业务关系
 * @author zhangyb5
 *
 */
public class McdCampsegTaskHotPlanRela {
	private String taskHotPlanId;
	private String taskId;
	private String hotPlanId;
	private Short matchHotPlanType;
	private Integer matchCount;
	private String mountHotPlanContent;

	public McdCampsegTaskHotPlanRela() {
		super();
	}

	public McdCampsegTaskHotPlanRela(String taskHotPlanId, String taskId, String hotPlanId, Short matchHotPlanType,
			Integer matchCount, String mountHotPlanContent) {
		super();
		this.taskHotPlanId = taskHotPlanId;
		this.taskId = taskId;
		this.hotPlanId = hotPlanId;
		this.matchHotPlanType = matchHotPlanType;
		this.matchCount = matchCount;
		this.mountHotPlanContent = mountHotPlanContent;
	}

	public String getTaskHotPlanId() {
		return taskHotPlanId;
	}

	public void setTaskHotPlanId(String taskHotPlanId) {
		this.taskHotPlanId = taskHotPlanId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getHotPlanId() {
		return hotPlanId;
	}

	public void setHotPlanId(String hotPlanId) {
		this.hotPlanId = hotPlanId;
	}

	public Short getMatchHotPlanType() {
		return matchHotPlanType;
	}

	public void setMatchHotPlanType(Short matchHotPlanType) {
		this.matchHotPlanType = matchHotPlanType;
	}

	public Integer getMatchCount() {
		return matchCount;
	}

	public void setMatchCount(Integer matchCount) {
		this.matchCount = matchCount;
	}

	public String getMountHotPlanContent() {
		return mountHotPlanContent;
	}

	public void setMountHotPlanContent(String mountHotPlanContent) {
		this.mountHotPlanContent = mountHotPlanContent;
	}

}
