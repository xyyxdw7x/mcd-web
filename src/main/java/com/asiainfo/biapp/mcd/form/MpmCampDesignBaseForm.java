package com.asiainfo.biapp.mcd.form;


/*
 * Created on 6:29:46 PM
 * 
 * <p>Title: </p>
 * <p>Description: Form()</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */
public class MpmCampDesignBaseForm extends SysBaseForm {
	protected String campId;

	protected String flowId;

	protected String campsegId;

	protected String campsegName;

	protected Short stepId;

	protected String canModify;

	///~ng嵽Σ1-0-
	protected String saveTempletToCurrCampseg;

	//"yн
	protected String stepPerformFlag;

	protected short campDrvId;

	protected Short campType;

	protected String toBeginDate; // ’

	protected String toEndDate; // 	

	//added by wwl 2006-11-16 "yi
	protected String preStepId;

	protected String nextStepId;

	//added by wwl 2007-10-24 s
	protected String custGroupId;

	protected Short custGroupType;

	//  protected String publicizeCombId;
	public MpmCampDesignBaseForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCampId() {
		return campId;
	}

	public void setCampId(String campId) {
		this.campId = campId;
	}

	public String getCampsegId() {
		return campsegId;
	}

	public void setCampsegId(String campsegId) {
		this.campsegId = campsegId;
	}

	public Short getStepId() {
		return stepId;
	}

	public void setStepId(Short stepId) {
		this.stepId = stepId;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getCanModify() {
		return canModify;
	}

	public void setCanModify(String canModify) {
		this.canModify = canModify;
	}

	public String getCampsegName() {
		return campsegName;
	}

	public void setCampsegName(String campsegName) {
		this.campsegName = campsegName;
	}

	public String getToBeginDate() {
		return toBeginDate;
	}

	public void setToBeginDate(String toBeginDate) {
		this.toBeginDate = toBeginDate;
	}

	public String getToEndDate() {
		return toEndDate;
	}

	public void setToEndDate(String toEndDate) {
		this.toEndDate = toEndDate;
	}

	public String getSaveTempletToCurrCampseg() {
		return saveTempletToCurrCampseg;
	}

	public void setSaveTempletToCurrCampseg(String saveTempletToCurrCampseg) {
		this.saveTempletToCurrCampseg = saveTempletToCurrCampseg;
	}

	public String getStepPerformFlag() {
		return stepPerformFlag;
	}

	public void setStepPerformFlag(String stepPerformFlag) {
		this.stepPerformFlag = stepPerformFlag;
	}

	public short getCampDrvId() {
		return campDrvId;
	}

	public void setCampDrvId(short campDrvId) {
		this.campDrvId = campDrvId;
	}

	public Short getCampType() {
		return campType;
	}

	public void setCampType(Short campType) {
		this.campType = campType;
	}

	public String getNextStepId() {
		return nextStepId;
	}

	public void setNextStepId(String nextStepId) {
		this.nextStepId = nextStepId;
	}

	public String getPreStepId() {
		return preStepId;
	}

	public void setPreStepId(String preStepId) {
		this.preStepId = preStepId;
	}

	public String getCustGroupId() {
		return custGroupId;
	}

	public void setCustGroupId(String custGroupId) {
		this.custGroupId = custGroupId;
	}

	public Short getCustGroupType() {
		return custGroupType;
	}

	public void setCustGroupType(Short custGroupType) {
		this.custGroupType = custGroupType;
	}

	//	public String getPublicizeCombId() {
	//		return publicizeCombId;
	//	}
	//
	//	public void setPublicizeCombId(String publicizeCombId) {
	//		this.publicizeCombId = publicizeCombId;
	//	}

}
