package com.asiainfo.biapp.mcd.tactics.vo;

/**
 * DimPlanType entity. @author MyEclipse Persistence Tools
 */

public class DimPlanType implements java.io.Serializable {

	// Fields

	private String typeId;
	private String typeName;
	private String typePid;
	private String channelType;
	private String sortNum;

	// Constructors

	public String getSortNum() {
		return sortNum;
	}

	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}

	/** default constructor */
	public DimPlanType() {
	}

	/** full constructor */
	public DimPlanType(String typeName, String typePid) {
		this.typeName = typeName;
		this.typePid = typePid;
	}

	// Property accessors

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypePid() {
		return typePid;
	}

	public void setTypePid(String typePid) {
		this.typePid = typePid;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

}