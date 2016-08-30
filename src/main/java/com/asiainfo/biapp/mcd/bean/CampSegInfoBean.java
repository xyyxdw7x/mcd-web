package com.asiainfo.biapp.mcd.bean;

import java.util.List;

import com.asiainfo.biapp.mcd.model.McdCampsegPolicyRelation;

/**
 * 查询营销活动波次信息，dwr传输前台bean
 * 2013-5-31 15:17:06
 * @author Mazh
 *
 */
public class CampSegInfoBean {
	private String campSegId;
	private String campSegName;
	private String campSegStatId;
	private String campSegStatName;
	private String eventSourceId;
	private String custgroupId;
	private String custgroupName;
	private String custgroupNumber;
	private String promotionTypeId;
	private String promotionTypeName;
	private String chanelId;
	private String chanelTypeId;
	private String chanelName;
	private String campClass;
	private String campSegPId;
	private List<CampSegInfoBean> campSegList;
	private List<String> policyNameList;
	private List<McdCampsegPolicyRelation> policyList;
	private Integer levelCount;
	private String waveConId;
	private String waveConName;
	private String custListTabName;
	private String channelCampContent;//最新营销用语
	private String oldCampContent;//原始用语
	private String activeTempletId;
	private String eventTemplateId;
	private String planId;
	private String planName;
	
	private String execContent;
	
	//客户群评估类型ID
	private String custTypeEvelId;

	public String getCustTypeEvelId() {
		return custTypeEvelId;
	}
	public void setCustTypeEvelId(String custTypeEvelId) {
		this.custTypeEvelId = custTypeEvelId;
	}
	public String getExecContent() {
		return execContent;
	}
	public void setExecContent(String execContent) {
		this.execContent = execContent;
	}
	public List<String> getPolicyNameList() {
		return policyNameList;
	}
	public void setPolicyNameList(List<String> policyNameList) {
		this.policyNameList = policyNameList;
	}
	public List<McdCampsegPolicyRelation> getPolicyList() {
		return policyList;
	}
	public void setPolicyList(List<McdCampsegPolicyRelation> policyList) {
		this.policyList = policyList;
	}

	public String getEventSourceId() {
		return eventSourceId;
	}

	public void setEventSourceId(String eventSourceId) {
		this.eventSourceId = eventSourceId;
	}

	public String getChanelTypeId() {
		return chanelTypeId;
	}

	public void setChanelTypeId(String chanelTypeId) {
		this.chanelTypeId = chanelTypeId;
	}

	public String getOldCampContent() {
		return oldCampContent;
	}

	public void setOldCampContent(String oldCampContent) {
		this.oldCampContent = oldCampContent;
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

	public String getActiveTempletId() {
		return activeTempletId;
	}

	public void setActiveTempletId(String activeTempletId) {
		this.activeTempletId = activeTempletId;
	}

	public String getChannelCampContent() {
		return channelCampContent;
	}

	public void setChannelCampContent(String channelCampContent) {
		this.channelCampContent = channelCampContent;
	}

	public String getCustListTabName() {
		return custListTabName;
	}

	public void setCustListTabName(String custListTabName) {
		this.custListTabName = custListTabName;
	}

	public String getWaveConId() {
		return waveConId;
	}

	public void setWaveConId(String waveConId) {
		this.waveConId = waveConId;
	}

	public String getWaveConName() {
		return waveConName;
	}

	public void setWaveConName(String waveConName) {
		this.waveConName = waveConName;
	}

	public Integer getLevelCount() {
		return levelCount;
	}

	public void setLevelCount(Integer levelCount) {
		this.levelCount = levelCount;
	}

	public String getCampSegPId() {
		return campSegPId;
	}

	public void setCampSegPId(String campSegPId) {
		this.campSegPId = campSegPId;
	}

	public String getCampSegName() {
		return campSegName;
	}

	public void setCampSegName(String campSegName) {
		this.campSegName = campSegName;
	}

	public List<CampSegInfoBean> getCampSegList() {
		return campSegList;
	}

	public void setCampSegList(List<CampSegInfoBean> campSegList) {
		this.campSegList = campSegList;
	}

	public String getCampSegId() {
		return campSegId;
	}

	public void setCampSegId(String campSegId) {
		this.campSegId = campSegId;
	}

	public String getCampSegStatId() {
		return campSegStatId;
	}

	public void setCampSegStatId(String campSegStatId) {
		this.campSegStatId = campSegStatId;
	}

	public String getCampSegStatName() {
		return campSegStatName;
	}

	public void setCampSegStatName(String campSegStatName) {
		this.campSegStatName = campSegStatName;
	}

	public String getCustgroupId() {
		return custgroupId;
	}

	public void setCustgroupId(String custgroupId) {
		this.custgroupId = custgroupId;
	}

	public String getCustgroupName() {
		return custgroupName;
	}

	public void setCustgroupName(String custgroupName) {
		this.custgroupName = custgroupName;
	}

	public String getCustgroupNumber() {
		return custgroupNumber;
	}

	public void setCustgroupNumber(String custgroupNumber) {
		this.custgroupNumber = custgroupNumber;
	}

	public String getPromotionTypeId() {
		return promotionTypeId;
	}

	public void setPromotionTypeId(String promotionTypeId) {
		this.promotionTypeId = promotionTypeId;
	}

	public String getPromotionTypeName() {
		return promotionTypeName;
	}

	public void setPromotionTypeName(String promotionTypeName) {
		this.promotionTypeName = promotionTypeName;
	}

	public String getChanelId() {
		return chanelId;
	}

	public void setChanelId(String chanelId) {
		this.chanelId = chanelId;
	}

	public String getChanelName() {
		return chanelName;
	}

	public void setChanelName(String chanelName) {
		this.chanelName = chanelName;
	}

	public String getCampClass() {
		return campClass;
	}

	public void setCampClass(String campClass) {
		this.campClass = campClass;
	}

	public String getEventTemplateId() {
		return eventTemplateId;
	}

	public void setEventTemplateId(String eventTemplateId) {
		this.eventTemplateId = eventTemplateId;
	}
}
