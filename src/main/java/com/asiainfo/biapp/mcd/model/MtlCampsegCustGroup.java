package com.asiainfo.biapp.mcd.model;

/**
 * MtlCampsegCustGroup generated by MyEclipse Persistence Tools
 */

public class MtlCampsegCustGroup implements java.io.Serializable {

	// Fields

	private MtlCampsegCustGroupId id;

	private short custGroupType;

	private String custGroupId;

	private String custBaseMonth;

	private String custBaseDay;
	
	// Constructors

	/** default constructor */
	public MtlCampsegCustGroup() {
	}

	/** full constructor */
	public MtlCampsegCustGroup(MtlCampsegCustGroupId id, short custGroupType, String custGroupId) {
		this.id = id;
		this.custGroupType = custGroupType;
		this.custGroupId = custGroupId;
	}

	// Property accessors

	public MtlCampsegCustGroupId getId() {
		return this.id;
	}

	public void setId(MtlCampsegCustGroupId id) {
		this.id = id;
	}

	public short getCustGroupType() {
		return this.custGroupType;
	}

	public void setCustGroupType(short custGroupType) {
		this.custGroupType = custGroupType;
	}

	public String getCustGroupId() {
		return this.custGroupId;
	}

	public void setCustGroupId(String custGroupId) {
		this.custGroupId = custGroupId;
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

}
