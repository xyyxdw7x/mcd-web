package com.asiainfo.biapp.mcd.form;


/*
 * Created on 6:32:29 PM
 * 
 * <p>Title: </p>
 * <p>Description: Form</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author zhoulb@asiainfo.com
 * @version 1.0
 */
public class MtlContactConfigForm extends SysBaseForm {

	private Short[] campDrvId;

	private Short campDrvId1;

	private Short contactType;
	
	private Object[] contactType1;
	
	
	private Short custCode;
	private String[] custCode1;
	
	

	public Object[] getContactType1() {
		return contactType1;
	}

	public void setContactType1(Object[] contactType1) {
		this.contactType1 = contactType1;
	}

	public String[] getCustCode1() {
		return custCode1;
	}

	public void setCustCode1(String[] custCode1) {
		this.custCode1 = custCode1;
	}

	private Integer contactTimes;

	private String contactTimeses;



	public String getContactTimeses() {
		return contactTimeses;
	}

	public void setContactTimeses(String contactTimeses) {
		this.contactTimeses = contactTimeses;
	}

	public Short getCustCode() {
		return custCode;
	}

	public void setCustCode(Short custCode) {
		this.custCode = custCode;
	}

	public Integer getContactTimes() {
		return contactTimes;
	}

	public void setContactTimes(Integer contactTimes) {
		this.contactTimes = contactTimes;
	}

	public Short[] getCampDrvId() {
		return this.campDrvId;
	}

	public void setCampDrvId(Short[] campDrvId) {
		this.campDrvId = campDrvId;
	}

	public Short getCampDrvId1() {
		return this.campDrvId1;
	}

	public void setCampDrvId1(Short campDrvId1) {
		this.campDrvId1 = campDrvId1;
	}

	public Short getContactType() {
		return this.contactType;
	}

	public void setContactType(Short contactType) {
		this.contactType = contactType;
	}

}
