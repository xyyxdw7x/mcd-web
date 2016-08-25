package com.asiainfo.biapp.mcd.tactics.vo;

import com.asiainfo.biapp.framework.jdbc.annotation.Column;

/**
 * DimCampDrvType generated by MyEclipse - Hibernate Tools
 */

public class DimCampDrvType implements java.io.Serializable {

	// Fields    

	private Short campDrvId;

	private Short parentId;

	private String flowId;

	private String planExecId;

	private String campDrvName;

	private String campDrvDesc;
	
	private Short accessControlFlag;
	
	private Short campDrvClass;
	
	
	
	private Integer dcpPageId;
	/** 该驱动类型是否禁用，0：正常不禁用； 1：禁用 */
	private Short drvDesabled;
	
	private Short campDrvAutoFlag;


	// Constructors

	/** default constructor */
	public DimCampDrvType() {
	}

	/** minimal constructor */
	public DimCampDrvType(Short campDrvId) {
		this.campDrvId = campDrvId;
	}

	/** full constructor */
	public DimCampDrvType(Short campDrvId, String campDrvName, String campDrvDesc) {
		this.campDrvId = campDrvId;
		this.campDrvName = campDrvName;
		this.campDrvDesc = campDrvDesc;
	}

	// Property accessors

	public Short getCampDrvId() {
		return this.campDrvId;
	}
	@Column(name="CAMP_DRV_ID")
	public void setCampDrvId(Short campDrvId) {
		this.campDrvId = campDrvId;
	}

	public String getCampDrvName() {
		return this.campDrvName;
	}
	@Column(name="CAMP_DRV_NAME")
	public void setCampDrvName(String campDrvName) {
		this.campDrvName = campDrvName;
	}

	public String getCampDrvDesc() {
		return this.campDrvDesc;
	}
	@Column(name="CAMP_DRV_DESC")
	public void setCampDrvDesc(String campDrvDesc) {
		this.campDrvDesc = campDrvDesc;
	}

	public Short getParentId() {
		return parentId;
	}
	@Column(name="PARENT_ID")
	public void setParentId(Short parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return flowId
	 */
	public String getFlowId() {
		return flowId;
	}

	@Column(name="FLOW_ID")
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	/**
	 * @return planExecId
	 */
	public String getPlanExecId() {
		return planExecId;
	}

	public Integer getDcpPageId() {
		return dcpPageId;
	}
	@Column(name="dcp_page_id")
	public void setDcpPageId(Integer dcpPageId) {
		this.dcpPageId = dcpPageId;
	}

	/**
	 * @param planExecId 要设置的 planExecId
	 */
	@Column(name="plan_exec_id")
	public void setPlanExecId(String planExecId) {
		this.planExecId = planExecId;
	}

	public Short getAccessControlFlag() {
		return accessControlFlag;
	}
	@Column(name="access_control_flag")
	public void setAccessControlFlag(Short accessControlFlag) {
		this.accessControlFlag = accessControlFlag;
	}
	
	public Short getCampDrvClass() {
		return campDrvClass;
	}
	@Column(name="camp_drv_class")
	public void setCampDrvClass(Short campDrvClass) {
		this.campDrvClass = campDrvClass;
	}

	public Short getDrvDesabled() {
		return drvDesabled;
	}
	@Column(name="DRV_DISABLED")
	public void setDrvDesabled(Short drvDesabled) {
		this.drvDesabled = drvDesabled;
	}

	public Short getCampDrvAutoFlag() {
		return campDrvAutoFlag;
	}
	@Column(name="CAMP_DRV_AUTO_FLAG")
	public void setCampDrvAutoFlag(Short campDrvAutoFlag) {
		this.campDrvAutoFlag = campDrvAutoFlag;
	}
	
	
}
