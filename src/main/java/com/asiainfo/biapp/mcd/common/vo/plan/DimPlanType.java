package com.asiainfo.biapp.mcd.common.vo.plan;

import javax.persistence.Column;

/**
 * DimPlanType entity. @author MyEclipse Persistence Tools
 */

public class DimPlanType implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 9179852004030619975L;
	
	@Column(name="TYPE_ID")
	private String typeId;
	
	@Column(name="TYPE_NAME")
	private String typeName;
	
	@Column(name="TYPE_PID")
	private String typePid;
	
	@Column(name="CHANNEL_TYPE")
	private String channelType;
	
	@Column(name="SORT_NUM")
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