package com.asiainfo.biapp.mcd.common.vo.plan;

import java.util.Date;

/**
 * 
 * Title: 
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-7-17 上午09:53:49
 * @version 1.0
 */

public class MtlStcPlanBean {
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
	
	private String levelName;
	
	private String campName;
	
	private String campCode;
	
	private String reward;
	
	private String planChannelId;
	
	private String channelId;
	private String channelName;
	private String channelTypeId;
	
	private String typeName;
	
	private String campsegTypeId;
	
	private String planPid ;  //产品上一级编号
	private String planPname;   //产品上一级名称
	private String planSrvType;  //产品类别  营销案、档次
	
	private String adivId; //运营位
	private String adivResourceId;  //素材编号
	private String adivResourceName; //素材名称
	private String adivResourceDesc;  //素材描述
	private String adivContentURL;  //图片地址
	private String adivContentToRUL;  //图片跳转地址
	
	private String editURL; //采编路径
	private String handleURL;  //执行路径
	private String awardMount; //奖励金额
	private String execContent;  //推荐用语
	private String smsContent; //短信用语
	
	private String feeDesc;  //资费描述
	private String materialDesc;  // 物料描述
	
	private String planTypeName;  //政策粒度
	private String cityName;   //地市名称
	private String isUserd;   //是否已匹配
	
	
	public String getPlanTypeName() {
		return planTypeName;
	}

	public void setPlanTypeName(String planTypeName) {
		this.planTypeName = planTypeName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getIsUserd() {
		return isUserd;
	}

	public void setIsUserd(String isUserd) {
		this.isUserd = isUserd;
	}

	public String getChannelTypeId() {
		return channelTypeId;
	}

	public void setChannelTypeId(String channelTypeId) {
		this.channelTypeId = channelTypeId;
	}

	public String getCampsegTypeId() {
		return campsegTypeId;
	}

	public void setCampsegTypeId(String campsegTypeId) {
		this.campsegTypeId = campsegTypeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getDefaultChannelId() {
		return defaultChannelId;
	}

	public void setDefaultChannelId(String defaultChannelId) {
		this.defaultChannelId = defaultChannelId;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getCampName() {
		return campName;
	}

	public void setCampName(String campName) {
		this.campName = campName;
	}

	public String getCampCode() {
		return campCode;
	}

	public void setCampCode(String campCode) {
		this.campCode = campCode;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public String getPlanChannelId() {
		return planChannelId;
	}

	public void setPlanChannelId(String planChannelId) {
		this.planChannelId = planChannelId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getPlanPid() {
		return planPid;
	}

	public void setPlanPid(String planPid) {
		this.planPid = planPid;
	}

	public String getPlanPname() {
		return planPname;
	}

	public void setPlanPname(String planPname) {
		this.planPname = planPname;
	}

	public String getPlanSrvType() {
		return planSrvType;
	}

	public void setPlanSrvType(String planSrvType) {
		this.planSrvType = planSrvType;
	}

	public String getAdivId() {
		return adivId;
	}

	public void setAdivId(String adivId) {
		this.adivId = adivId;
	}

	public String getAdivResourceId() {
		return adivResourceId;
	}

	public void setAdivResourceId(String adivResourceId) {
		this.adivResourceId = adivResourceId;
	}

	public String getAdivResourceName() {
		return adivResourceName;
	}

	public void setAdivResourceName(String adivResourceName) {
		this.adivResourceName = adivResourceName;
	}

	public String getAdivResourceDesc() {
		return adivResourceDesc;
	}

	public void setAdivResourceDesc(String adivResourceDesc) {
		this.adivResourceDesc = adivResourceDesc;
	}

	public String getAdivContentURL() {
		return adivContentURL;
	}

	public void setAdivContentURL(String adivContentURL) {
		this.adivContentURL = adivContentURL;
	}

	public String getAdivContentToRUL() {
		return adivContentToRUL;
	}

	public void setAdivContentToRUL(String adivContentToRUL) {
		this.adivContentToRUL = adivContentToRUL;
	}

	public String getEditURL() {
		return editURL;
	}

	public void setEditURL(String editURL) {
		this.editURL = editURL;
	}

	public String getHandleURL() {
		return handleURL;
	}

	public void setHandleURL(String handleURL) {
		this.handleURL = handleURL;
	}

	public String getFeeDesc() {
		return feeDesc;
	}

	public void setFeeDesc(String feeDesc) {
		this.feeDesc = feeDesc;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public String getAwardMount() {
		return awardMount;
	}

	public void setAwardMount(String awardMount) {
		this.awardMount = awardMount;
	}


	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getExecContent() {
		return execContent;
	}

	public void setExecContent(String execContent) {
		this.execContent = execContent;
	}




}
