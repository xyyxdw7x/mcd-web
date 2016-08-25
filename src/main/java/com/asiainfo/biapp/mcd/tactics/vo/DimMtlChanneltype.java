package com.asiainfo.biapp.mcd.tactics.vo;

/**
 * DimMtlChanneltype entity.
 *
 * @author MyEclipse Persistence Tools
 */

public class DimMtlChanneltype implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Fields
	private String channelId;
	
	private Short activeFlag;

	private Short channeltypeId;

	private String channeltypeName;

	private Integer contactType;

	private Integer autoSendOdd;

	private Integer initiativeType;
	
	private Integer displayOrder;
	
	private boolean flag; //浙江IMCD 判断渠道和产品是否关联标识   true：已关联  false ：未关联
	// Constructors

//	IMCD_ZJ 为了统一前台模板使用  起别名
	private String typeId;
	private String typeName;
	
	private String num;
	
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

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

	/** default constructor */
	public DimMtlChanneltype() {
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/** minimal constructor */
	public DimMtlChanneltype(Short channeltypeId, String channeltypeName) {
		this.channeltypeId = channeltypeId;
		this.channeltypeName = channeltypeName;
	}

	/** full constructor */
	public DimMtlChanneltype(Short channeltypeId, String channeltypeName, Short activeFlag) {
		this.channeltypeId = channeltypeId;
		this.channeltypeName = channeltypeName;
		this.activeFlag = activeFlag;
	}

	// Property accessors

	public Short getActiveFlag() {
		return activeFlag;
	}

	public Integer getContactType() {
		return contactType;
	}

	public void setContactType(Integer contactType) {
		this.contactType = contactType;
	}

	public Short getChanneltypeId() {
		return channeltypeId;
	}

	public String getChanneltypeName() {
		return channeltypeName;
	}

	public void setActiveFlag(Short activeFlag) {
		this.activeFlag = activeFlag;
	}

	public void setChanneltypeId(Short channeltypeId) {
		this.channeltypeId = channeltypeId;
	}

	public void setChanneltypeName(String channeltypeName) {
		this.channeltypeName = channeltypeName;
	}

	public Integer getAutoSendOdd() {
		return autoSendOdd;
	}

	public void setAutoSendOdd(Integer autoSendOdd) {
		this.autoSendOdd = autoSendOdd;
	}

	public Integer getInitiativeType() {
		return initiativeType;
	}

	public void setInitiativeType(Integer initiativeType) {
		this.initiativeType = initiativeType;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

}
