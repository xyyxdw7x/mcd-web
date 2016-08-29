package com.asiainfo.biapp.mcd.model;

/**
 * DimCampsegStat generated by MyEclipse - Hibernate Tools
 */

public class DimCampsegStat implements java.io.Serializable {

	// Fields    

	private Short campsegStatId;

	private String campsegStatName;

	private String campsegStatDesc;
	/** 0：可见;1：程序控制;2：暂不使用或等待开发' */
	private short campsegStatVisible;

	// Constructors

	/** default constructor */
	public DimCampsegStat() {
	}

	/** minimal constructor */
	public DimCampsegStat(Short campsegStatId) {
		this.campsegStatId = campsegStatId;
	}

	/** full constructor */
	public DimCampsegStat(Short campsegStatId, String campsegStatName, short visible, String campsegStatDesc) {
		this.campsegStatId = campsegStatId;
		this.campsegStatName = campsegStatName;
		this.campsegStatDesc = campsegStatDesc;
		this.campsegStatVisible = visible;
	}

	// Property accessors

	public Short getCampsegStatId() {
		return this.campsegStatId;
	}

	public void setCampsegStatId(Short campsegStatId) {
		this.campsegStatId = campsegStatId;
	}

	public String getCampsegStatName() {
		return this.campsegStatName;
	}

	public void setCampsegStatName(String campsegStatName) {
		this.campsegStatName = campsegStatName;
	}

	public String getCampsegStatDesc() {
		return this.campsegStatDesc;
	}

	public void setCampsegStatDesc(String campsegStatDesc) {
		this.campsegStatDesc = campsegStatDesc;
	}

	public short getCampsegStatVisible() {
		return campsegStatVisible;
	}

	public void setCampsegStatVisible(short campsegStatVisible) {
		this.campsegStatVisible = campsegStatVisible;
	}

}
