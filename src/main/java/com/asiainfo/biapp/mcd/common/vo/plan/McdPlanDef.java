package com.asiainfo.biapp.mcd.common.vo.plan;

import java.util.Date;

import javax.persistence.Column;


/**
 * 产品信息
 */

public class McdPlanDef implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -6294398412949785065L;
	// Fields

	@Column(name="PLAN_ID")
	protected String planId; //主键

	@Column(name="PLAN_NAME")
	protected String planName;

	@Column(name="PLAN_STARTDATE")
	protected Date planStartdate;

	@Column(name="PLAN_ENDDATE")
	protected Date planEnddate;

	@Column(name="PLAN_DESC")
	protected String planDesc;

	@Column(name="STATUS")
	protected String status;

	@Column(name="CREATE_USERID")
	protected String createUserid;

	@Column(name="CREATE_DATE")
	protected Date createDate;

	@Column(name="BRAND_ID")
	private String brandId;

	@Column(name="CITY_ID")
	private String cityId;

	@Column(name="PLAN_TYPE")
	private String planType;

	protected String id;
	
	@Column(name="TYPE_ID")
	private String typeId;

	@Column(name="DEFAULT_CHANNEL_ID")
	private String defaultChannelId;
	
	private String levelId;

	// Constructors
	
	//陕西新添加的2个字段
	private Integer CHNL_SCORE;		

	private Integer SMS_SCORE;
	
	private String pictureName;//产品图片名称
	private String linkedPicAdr;//产品图片链接地址
	
	@Column(name="PLAN_SRV_TYPE")
	private String planSrvType;
	
	@Column(name="PLAN_PID")
	private String planPid;
	private String planDealRule;
	private String brandDesc;
	private String brandIds;
	private String cityDesc;
	private String cityIds;
	
	public String getPlanSrvType() {
		return planSrvType;
	}
	
	public void setPlanSrvType(String planSrvType) {
		this.planSrvType = planSrvType;
	}

	public String getPlanPid() {
		return planPid;
	}
	
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
	public McdPlanDef() {
	}

	/** minimal constructor */
	public McdPlanDef(String planId, String id, Date createDate) {
		this.planId = planId;
		this.id = id;
		this.createDate = createDate;
	}

	/** full constructor */
	public McdPlanDef(String planId, String id, String planName, Date planStartdate, Date planEnddate, String planDesc,
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
	
	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Date getPlanStartdate() {
		return planStartdate;
	}
	
	public void setPlanStartdate(Date planStartdate) {
		this.planStartdate = planStartdate;
	}

	public Date getPlanEnddate() {
		return planEnddate;
	}
	
	public void setPlanEnddate(Date planEnddate) {
		this.planEnddate = planEnddate;
	}

	public String getPlanDesc() {
		return planDesc;
	}
	
	public void setPlanDesc(String planDesc) {
		this.planDesc = planDesc;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateUserid() {
		return createUserid;
	}
	
	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}

	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getBrandId() {
		return brandId;
	}
	
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getCityId() {
		return cityId;
	}
	
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getPlanType() {
		return planType;
	}
	
	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getDefaultChannelId() {
		return defaultChannelId;
	}
	
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
