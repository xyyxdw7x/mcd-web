package com.asiainfo.biapp.mcd.model;



/**
 * DimDrvRoleManager generated by MyEclipse Persistence Tools
 */

public class DimDrvRoleManager implements java.io.Serializable {

	// Fields

	private DimDrvRoleManagerId id;
	private Short accessToken;
	private Short roleFlag;

	// Constructors

	/** default constructor */
	public DimDrvRoleManager() {
	}

	/** minimal constructor */
	public DimDrvRoleManager(DimDrvRoleManagerId id) {
		this.id = id;
	}

	/** full constructor */
	public DimDrvRoleManager(DimDrvRoleManagerId id, Short accessToken,
			Short roleFlag) {
		this.id = id;
		this.accessToken = accessToken;
		this.roleFlag = roleFlag;
	}

	// Property accessors

	public DimDrvRoleManagerId getId() {
		return this.id;
	}

	public void setId(DimDrvRoleManagerId id) {
		this.id = id;
	}

	public Short getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(Short accessToken) {
		this.accessToken = accessToken;
	}

	public Short getRoleFlag() {
		return this.roleFlag;
	}

	public void setRoleFlag(Short roleFlag) {
		this.roleFlag = roleFlag;
	}


}
