package com.asiainfo.biapp.mcd.common.vo.plan;

import com.asiainfo.biapp.framework.jdbc.annotation.Column;

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
	@Column(name="SORT_NUM")
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
    @Column(name="TYPE_ID")
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}
    @Column(name="TYPE_NAME")
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypePid() {
		return typePid;
	}
	@Column(name="TYPE_PID")
	public void setTypePid(String typePid) {
		this.typePid = typePid;
	}

	public String getChannelType() {
		return channelType;
	}
	@Column(name="CHANNEL_TYPE")
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

}