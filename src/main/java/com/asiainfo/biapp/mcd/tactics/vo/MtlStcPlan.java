package com.asiainfo.biapp.mcd.tactics.vo;

import java.util.Date;

import com.asiainfo.biapp.framework.jdbc.annotation.Column;

/**
 * 产品信息
 */

public class MtlStcPlan implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -6294398412949785065L;
	// Fields

	protected String planId; //主键

	protected String planName;

	protected Date planStartdate;

	protected Date planEnddate;

	protected String planDesc;

	protected String status;

	protected String createUserid;

	protected Date createDate;

	private String brandId;

	private String cityId;

	private String planType;

	protected String id;
	
	private String typeId;

	private String defaultChannelId;
	
	private String levelId;

	// Constructors
	
	//陕西新添加的2个字段
	private Integer CHNL_SCORE;		

	private Integer SMS_SCORE;
	
	private String pictureName;//产品图片名称
	private String linkedPicAdr;//产品图片链接地址
	
	private String planSrvType;
	private String planPid;
	private String planDealRule;
	private String brandDesc;
	private String brandIds;
	private String cityDesc;
	private String cityIds;
	
	public String getPlanSrvType() {
		return planSrvType;
	}
	@Column(name="PLAN_SRV_TYPE")
	public void setPlanSrvType(String planSrvType) {
		this.planSrvType = planSrvType;
	}

	public String getPlanPid() {
		return planPid;
	}
	@Column(name="PLAN_PID")
	public void setPlanPid(String planPid) {
		this.planPid = planPid;
	}

	public String getPlanDealRule() {
		return planDealRule;
	}

	public void setPlanDealRule(String planDealRule) {
		this.planDealRule = planDealRule;
	}

	public String getBrandDesc() {
		return brandDesc;
	}

	public void setBrandDesc(String brandDesc) {
		this.brandDesc = brandDesc;
	}

	public String getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(String brandIds) {
		this.brandIds = brandIds;
	}

	public String getCityDesc() {
		return cityDesc;
	}

	public void setCityDesc(String cityDesc) {
		this.cityDesc = cityDesc;
	}

	public String getCityIds() {
		return cityIds;
	}

	public void setCityIds(String cityIds) {
		this.cityIds = cityIds;
	}

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	public String getAuthLevel() {
		return authLevel;
	}

	public void setAuthLevel(String authLevel) {
		this.authLevel = authLevel;
	}

	public int getCampsegTypeId() {
		return campsegTypeId;
	}

	public void setCampsegTypeId(int campsegTypeId) {
		this.campsegTypeId = campsegTypeId;
	}

	public String getPrevDealRule() {
		return prevDealRule;
	}

	public void setPrevDealRule(String prevDealRule) {
		this.prevDealRule = prevDealRule;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public int getDataSource() {
		return dataSource;
	}

	public void setDataSource(int dataSource) {
		this.dataSource = dataSource;
	}

	private String planStatus;
	private String authLevel;
	private int campsegTypeId;
	private String prevDealRule;
	private String reward;
	private String resourceType;
	private String resourceId;
	private int dataSource;
	
	
	
	/** default constructor */
	public MtlStcPlan() {
	}

	/** minimal constructor */
	public MtlStcPlan(String planId, String id, Date createDate) {
		this.planId = planId;
		this.id = id;
		this.createDate = createDate;
	}

	/** full constructor */
	public MtlStcPlan(String planId, String id, String planName, Date planStartdate, Date planEnddate, String planDesc,
			String status, String createUserid, Date createDate, String planType) {
		this.planId = planId;
		this.id = id;
		this.planName = planName;
		this.planStartdate = planStartdate;
		this.planEnddate = planEnddate;
		this.planDesc = planDesc;
		this.status = status;
		this.createUserid = createUserid;
		this.createDate = createDate;
		this.planType = planType;
	}

	// Property accessors
	public String getPlanId() {
		return planId;
	}
	@Column(name="PLAN_ID")
	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlanName() {
		return planName;
	}
	@Column(name="PLAN_NAME")
	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Date getPlanStartdate() {
		return planStartdate;
	}
	@Column(name="PLAN_STARTDATE")
	public void setPlanStartdate(Date planStartdate) {
		this.planStartdate = planStartdate;
	}

	public Date getPlanEnddate() {
		return planEnddate;
	}
	@Column(name="PLAN_ENDDATE")
	public void setPlanEnddate(Date planEnddate) {
		this.planEnddate = planEnddate;
	}

	public String getPlanDesc() {
		return planDesc;
	}
	@Column(name="PLAN_DESC")
	public void setPlanDesc(String planDesc) {
		this.planDesc = planDesc;
	}

	public String getStatus() {
		return status;
	}
	@Column(name="STATUS")
	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateUserid() {
		return createUserid;
	}
	@Column(name="CREATE_USERID")
	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}

	public Date getCreateDate() {
		return createDate;
	}
	@Column(name="CREATE_DATE")
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getBrandId() {
		return brandId;
	}
	@Column(name="BRAND_ID")
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getCityId() {
		return cityId;
	}
	@Column(name="CITY_ID")
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getPlanType() {
		return planType;
	}
	@Column(name="PLAN_TYPE")
	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getDefaultChannelId() {
		return defaultChannelId;
	}
	@Column(name="DEFAULT_CHANNEL_ID")
	public void setDefaultChannelId(String defaultChannelId) {
		this.defaultChannelId = defaultChannelId;
	}
	
	
	public Integer getCHNL_SCORE() {
		return CHNL_SCORE;
	}

	public void setCHNL_SCORE(Integer cHNL_SCORE) {
		CHNL_SCORE = cHNL_SCORE;
	}

	public Integer getSMS_SCORE() {
		return SMS_SCORE;
	}

	public void setSMS_SCORE(Integer sMS_SCORE) {
		SMS_SCORE = sMS_SCORE;
	}

	public String getTypeId() {
		return typeId;
	}
	@Column(name="TYPE_ID")
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	public String getLinkedPicAdr() {
		return linkedPicAdr;
	}

	public void setLinkedPicAdr(String linkedPicAdr) {
		this.linkedPicAdr = linkedPicAdr;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

}
