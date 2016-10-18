package com.asiainfo.biapp.mcd.plan.vo;

import javax.persistence.Column;

/**
 * 活动状态
 *
 */
public class McdDimPlanOnlineStatus implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	/**状态值*/
	@Column(name="STATUS_ID")
	private String statusId; 
	
	/**活动id*/
	@Column(name="STATUS_NAME")
	private String statusName;

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	
     
}
