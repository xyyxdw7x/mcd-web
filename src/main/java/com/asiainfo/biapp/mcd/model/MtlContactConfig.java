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
public class MtlContactConfig implements Serializable {
	private MtlContactConfigId id;

	private Integer contactTimes;

	/**
	 * Simple constructor of DimPubBrand instances.
	 */
	public MtlContactConfig() {
	}

	/**
	 * Constructor of DimPubBrand instances given a simple primary key.
	 * @param brandId
	 */
	public MtlContactConfig(MtlContactConfigId id) {
		this.id = id;
	}

	public MtlContactConfigId getId() {
		return this.id;
	}

	public void setId(MtlContactConfigId id) {
		this.id = id;
	}

	public Integer getContactTimes() {
		return contactTimes;
	}

	public void setContactTimes(Integer contactTimes) {
		this.contactTimes = contactTimes;
	}

}
