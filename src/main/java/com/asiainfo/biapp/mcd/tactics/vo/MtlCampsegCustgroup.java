package com.asiainfo.biapp.mcd.tactics.vo;

/**
 * MtlCampsegCustgroup entity. @author MyEclipse Persistence Tools
 */

public class MtlCampsegCustgroup implements java.io.Serializable {

	// Fields
	@javax.persistence.Column(name="CAMPSEG_CUSTGROUP_ID")
	private String campsegCustgroupId;
	@javax.persistence.Column(name="CAMPSEG_ID")
	private String campsegId;
	@javax.persistence.Column(name="CUSTGROUP_TYPE")
	private String custgroupType;
	@javax.persistence.Column(name="CUSTGROUP_ID")
	private String custgroupId;
	@javax.persistence.Column(name="CUSTGROUP_NAME")
	private String custgroupName;
	@javax.persistence.Column(name="CUSTGROUP_NUMBER")
	private Integer custgroupNumber;
	@javax.persistence.Column(name="CUST_BASE_MONTH")
	private String custBaseMonth;
	@javax.persistence.Column(name="CUST_BASE_DAY")
	private String custBaseDay;
	@javax.persistence.Column(name="CUSTGROUP_DESC")
	private String custgroupDesc;
	@javax.persistence.Column(name="ATTR_CLASS_ID")
	private String attrClassId;
	
	private String attrMetaId;
	private String columnCnName;
	private String columnTypeId;
	private String attrId;
	private String cviewId;
	private String ctrlTypeId;
	
	private String elementValue;
	private String elementValueId;

	// Constructors

	/** default constructor */
	public MtlCampsegCustgroup() {
	}

	/** minimal constructor */
	public MtlCampsegCustgroup(String campsegId, String custgroupType,String custgroupId, String custgroupName) {
		this.campsegId = campsegId;
		this.custgroupType = custgroupType;
		this.custgroupId = custgroupId;
		this.custgroupName = custgroupName;
	}

	/** full constructor */
	public MtlCampsegCustgroup(String campsegId, String custgroupType,
			String custgroupId, String custgroupName, Integer custgroupNumber,
			String custBaseMonth, String custBaseDay, String custgroupDesc) {
		this.campsegId = campsegId;
		this.custgroupType = custgroupType;
		this.custgroupId = custgroupId;
		this.custgroupName = custgroupName;
		this.custgroupNumber = custgroupNumber;
		this.custBaseMonth = custBaseMonth;
		this.custBaseDay = custBaseDay;
		this.custgroupDesc = custgroupDesc;
	}

	// Property accessors

	public String getCampsegCustgroupId() {
		return campsegCustgroupId;
	}

	public void setCampsegCustgroupId(String campsegCustgroupId) {
		this.campsegCustgroupId = campsegCustgroupId;
	}

	public String getCampsegId() {
		return campsegId;
	}

	public void setCampsegId(String campsegId) {
		this.campsegId = campsegId;
	}

	public String getCustgroupType() {
		return custgroupType;
	}

	public void setCustgroupType(String custgroupType) {
		this.custgroupType = custgroupType;
	}

	public String getCustgroupId() {
		return custgroupId;
	}

	public void setCustgroupId(String custgroupId) {
		this.custgroupId = custgroupId;
	}

	public String getCustgroupName() {
		return custgroupName;
	}

	public void setCustgroupName(String custgroupName) {
		this.custgroupName = custgroupName;
	}

	public Integer getCustgroupNumber() {
		return custgroupNumber;
	}

	public void setCustgroupNumber(Integer custgroupNumber) {
		this.custgroupNumber = custgroupNumber;
	}

	public String getCustBaseMonth() {
		return custBaseMonth;
	}

	public void setCustBaseMonth(String custBaseMonth) {
		this.custBaseMonth = custBaseMonth;
	}

	public String getCustBaseDay() {
		return custBaseDay;
	}

	public void setCustBaseDay(String custBaseDay) {
		this.custBaseDay = custBaseDay;
	}

	public String getCustgroupDesc() {
		return custgroupDesc;
	}

	public void setCustgroupDesc(String custgroupDesc) {
		this.custgroupDesc = custgroupDesc;
	}

	public String getAttrClassId() {
		return attrClassId;
	}

	public void setAttrClassId(String attrClassId) {
		this.attrClassId = attrClassId;
	}

	public String getAttrMetaId() {
		return attrMetaId;
	}

	public void setAttrMetaId(String attrMetaId) {
		this.attrMetaId = attrMetaId;
	}

	public String getColumnCnName() {
		return columnCnName;
	}

	public void setColumnCnName(String columnCnName) {
		this.columnCnName = columnCnName;
	}

	public String getColumnTypeId() {
		return columnTypeId;
	}

	public void setColumnTypeId(String columnTypeId) {
		this.columnTypeId = columnTypeId;
	}

	public String getAttrId() {
		return attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}

	public String getCviewId() {
		return cviewId;
	}

	public void setCviewId(String cviewId) {
		this.cviewId = cviewId;
	}

	public String getCtrlTypeId() {
		return ctrlTypeId;
	}

	public void setCtrlTypeId(String ctrlTypeId) {
		this.ctrlTypeId = ctrlTypeId;
	}

	public String getElementValue() {
		return elementValue;
	}

	public void setElementValue(String elementValue) {
		this.elementValue = elementValue;
	}

	public String getElementValueId() {
		return elementValueId;
	}

	public void setElementValueId(String elementValueId) {
		this.elementValueId = elementValueId;
	}
	
}