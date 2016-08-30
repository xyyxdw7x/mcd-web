package com.asiainfo.biapp.mcd.jms.beans;

/**
 * 事件规则文件信息
 * @author zhangyb5
 *
 */
public class EventRuleFileBean {
	private String fileId;
	private String activityCode;
	private int eventId;
	private String interfaceCode;

	public EventRuleFileBean(String activityCode, int eventId, String fileId, String interfaceCode) {
		super();
		this.fileId = fileId;
		this.activityCode = activityCode;
		this.eventId = eventId;
		this.interfaceCode = interfaceCode;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

}
