package com.asiainfo.biapp.mcd.tactics.vo;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * MtlCampsegCustgroup entity. @author MyEclipse Persistence Tools
 */
@Table(name="mcd_camp_custgroup_list")
public class McdCampCustgroupList implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="CAMPSEG_ID")
	private String campId;
	@Column(name="CUSTGROUP_ID")
	private String custgroupId;
	
	// Fields
    @Transient
	private String campsegCustgroupId;
	@Transient
	private String custgroupType;
	@Transient
	private String custgroupName;
	@Transient
	private Integer custgroupNumber;
	@Transient
	private String custBaseMonth;
	@Transient
	private String custBaseDay;
	@Transient
	private String custgroupDesc;
	@Transient
	private String attrClassId;
	@Transient
	private String attrMetaId;
	@Transient
	private String columnCnName;
	@Transient
	private String columnTypeId;
	@Transient
	private String attrId;
	@Transient
	private String cviewId;
	@Transient
	private String ctrlTypeId;
	@Transient
	private String elementValue;
	@Transient
	private String elementValueId;

	// Constructors

	/** default constructor */
	public McdCampCustgroupList() {
	}

	/** minimal constructor */
	public McdCampCustgroupList(String campsegId, String custgroupType,String custgroupId, String custgroupName) {
		this.campId = campsegId;
		this.custgroupType = custgroupType;
		this.custgroupId = custgroupId;
		this.custgroupName = custgroupName;
	}

	/** full constructor */
	public McdCampCustgroupList(String campsegId, String custgroupType,
			String custgroupId, String custgroupName, Integer custgroupNumber,
			String custBaseMonth, String custBaseDay, String custgroupDesc) {
		this.campId = campsegId;
		this.custgroupType = custgroupType;
		this.custgroupId = custgroupId;
		this.custgroupName = custgroupName;
		this.custgroupNumber = custgroupNumber;
		this.custBaseMonth = custBaseMonth;
		this.custBaseDay = custBaseDay;
		this.custgroupDesc = custgroupDesc;
	}

	public String getCampId() {
		return campId;
	}

	public void setCampId(String campId) {
		this.campId = campId;
	}

	public String getCustgroupId() {
		return custgroupId;
	}

	public void setCustgroupId(String custgroupId) {
		this.custgroupId = custgroupId;
	}

	public String getCampsegCustgroupId() {
		return campsegCustgroupId;
	}

	public void setCampsegCustgroupId(String campsegCustgroupId) {
		this.campsegCustgroupId = campsegCustgroupId;
	}

	public String getCustgroupType() {
		return custgroupType;
	}

	public void setCustgroupType(String custgroupType) {
		this.custgroupType = custgroupType;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	
}