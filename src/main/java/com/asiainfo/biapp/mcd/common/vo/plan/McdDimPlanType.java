package com.asiainfo.biapp.mcd.common.vo.plan;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;

/**
 * McdDimPlanType entity. @author MyEclipse Persistence Tools
 */

public class McdDimPlanType implements java.io.Serializable {

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
	@Transient
	private List<McdDimPlanType> subTypes;


	public List<McdDimPlanType> getSubTypes() {
		return subTypes;
	}

	public void setSubTypes(List<McdDimPlanType> subTypes) {
		this.subTypes = subTypes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSortNum() {
		return sortNum;
	}
	
	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}

	/** default constructor */
	public McdDimPlanType() {
	}

	/** full constructor */
	public McdDimPlanType(String typeName, String typePid) {
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