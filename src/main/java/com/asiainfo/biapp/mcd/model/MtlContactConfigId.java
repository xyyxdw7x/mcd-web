/*
 * Created Tue Jun 06 16:00:39 CST 2006 by MyEclipse Hibernate Tool.
 */
package com.asiainfo.biapp.mcd.model;

import java.io.Serializable;

/**
 * A class that represents a row in the 'DIM_PUB_BRAND' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class MtlContactConfigId implements Serializable {
	private Short campDrvId;

	private Short custCode;
	
	private Short contactType;
	/**
	 * Simple constructor of DimPubBrand instances.
	 */
	public MtlContactConfigId() {
	}

	/**
	 * Constructor of DimPubBrand instances given a simple primary key.
	 * @param brandId
	 */
	public MtlContactConfigId(java.lang.Short campDrvId,java.lang.Short custCode, java.lang.Short contactType) {
		this.campDrvId = campDrvId;
		this.custCode = custCode;
		this.contactType = contactType;
	}

	public Short getCampDrvId() {
		return this.campDrvId;
	}

	public void setCampDrvId(Short campDrvId) {
		this.campDrvId = campDrvId;
	}

	public Short getContactType() {
		return this.contactType;
	}

	public void setContactType(Short contactType) {
		this.contactType = contactType;
	}

	public Short getCustCode() {
		return custCode;
	}

	public void setCustCode(Short custCode) {
		this.custCode = custCode;
	}

	

}
