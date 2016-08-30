package com.asiainfo.biapp.mcd.bean;

import java.util.List;
import java.util.Set;

import com.asiainfo.biapp.mcd.model.McdTargetCustFeedback;

public class MtlCampSeginfoBean {

	private String campsegId; //活动编号
	private String campName; //营销案名称
	private String campId; //营销案编号
	private String campsegName; //活动名称
	private String campsegDesc; //活动描述
	private String scenesId; //场景标识
	private Short campsegTypeId; //活动营销类型
	private String startDate; //开始时间
	private String endDate; //结束时间
	private String sceneType; //活动场景类型
	private Integer contactType; //活动级接触类型
	private Integer contactCount;
	private Integer campDrvId; //活动类型维表 dim_camp_drv_type(parentId=5)
	private String campDrvName; //活动类型名称
	private Integer waveContactType; //规则接触类型
	private Integer waveContactCount;
	private String campsegPid; //活动编号父ID
	private Integer campClass; //活动实际含义1：活动基本信息,2：规则,3：波次
	private String waveConId; //波次响应条件
	private String planId; //资费编码

	private String custgroupId; //客户群
	private String custgroupName;
	private String custgroupNumber;
	private String custgroupType;//客户群类型

	private String channelId;
	private String channelName; //渠道名称
	private String channelTypeId;

	private String channelCampContent; //营销用语
	private String oldChannelCampContent; //原营销用语
	private String planName; //资费名称
	private String waveConName; //波次响应条件名称
	private Integer levelCount;
	private Integer campsegStatId;
	private String timeRange; //接触时段
	private Integer isRelativeExecTime; //执行时间是否相对时间
	private String absoluteDates; //绝对时间串，当多个时以英文逗号分割
	private Integer campWaveDelayFrom; //多波次执行延迟相对时间开始
	private Integer campWaveDelayTo; //波次执行延迟相对时间结束
	private String activeTempletId; //时机ID
	private String eventActiveTempletId; //事件ID
	private String areaId; //所属地市
	private String areaName; //所属地市名称
	private String campsegNo; //活动编码
	private String campPriId; //活动优先级
	private String avoidTimeRange; //免打扰时间
	private String avoidTimeRangeTmp;
	private boolean isLast; //本节点是否是存储在列表中的最后一个节点
	private boolean parentIsLast; //父节点是否是存储在列表中的最后一个节点
	/**
	 * 云南的活动规则设置中渠道执行为综合网关时，规则Id与网站分类、内容的站点Id的关联(json串)
	 */
	private String webClassIdSiteCategoryId;

	private Short isTemplate;
	private String rejectCampsegIds; //剔除活动
	private String rejectCampsegName; //剔除活动名称
	private Set campsegAttachs; //附件
	private String typeName;
	private String campPriName;
	private String contactCountShow;
	private List<MtlCampSeginfoBean> campSegList;
	private String avoidBotherTypeIds;

	private String consultContentHisAll;

	private String consultContentHisLast;
	private String execContent; //辅助营销用语
	private String channelExtendInfo;//辅助执行渠道
	private String channelExtendInfoText;//辅助执行渠道显示
	
	private String testNumber;
	private String eventSourceId;
	private String cepEventId; //复杂事件ID
	private String cepEventShow; //复杂事件展示

	private String drvDeptCode; //复杂事件展示
	private String recommendDesc;
	//陕西网上营业厅url
	private String onlineServiceUrl;

	private String ruleIdAndNames;//营销规则json串

	//客户群类型(0:无 ,1:客户群 2:来自其他活动规则的反馈)
	private Integer targetCustType;
	//客户群 来自其他活动规则的反馈 信息
	private McdTargetCustFeedback mcdTargetCustFeedback;
	
	//派单时是否按客户偏好自动选择渠道
	private short isSplitCustgroup; 
	
	private Integer is_containFee;//是否包含费用
	
	private String exeContentType;//营销用语类型
	private String campsegInstruct;//营销指令
	
	
	//是否场景模板
	private Integer isSceneTemplate;
    //短信派发时限字段，用于限定渠道短信派发的时间	
	private Integer sendTimeLimit;
	
	//客户群评估类型id
	private String custEvelTypeId;
	
	private String custEvelTypeName;
	
	private String campsegBussiCode;
	
	//是否加挂热点业务字段
	private Short isMountHotPlan; 
 
	//指令名称
	private String instruct;

	//广告ids
	private String ad_ids;
	//广告名称
	private String ad_names;
	
	//是否按照客户群周期性发放
	private Integer isPeriodicity;
	
	private String pictureName;//产品图片名称
	private String linkedPicAdr;//产品图片链接地址
	
	
	
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
	
	public Integer getIsPeriodicity() {
		return isPeriodicity;
	}

	public void setIsPeriodicity(Integer isPeriodicity) {
		this.isPeriodicity = isPeriodicity;
	}
	
	public String getInstruct() {
		return instruct;
	}

	public void setInstruct(String instruct) {
		this.instruct = instruct;
	}

	public String getCampsegBussiCode() {
		return campsegBussiCode;
	}

	public void setCampsegBussiCode(String campsegBussiCode) {
		this.campsegBussiCode = campsegBussiCode;
	}

	public String getCustEvelTypeName() {
		return custEvelTypeName;
	}

	public void setCustEvelTypeName(String custEvelTypeName) {
		this.custEvelTypeName = custEvelTypeName;
	}

	public String getCustEvelTypeId() {
		return custEvelTypeId;
	}

	public void setCustEvelTypeId(String custEvelTypeId) {
		this.custEvelTypeId = custEvelTypeId;
	}

	public Integer getIsSceneTemplate() {
		return isSceneTemplate;
	}

	public void setIsSceneTemplate(Integer isSceneTemplate) {
		this.isSceneTemplate = isSceneTemplate;
	}

	public Integer getSendTimeLimit() {
		return sendTimeLimit;
	}

	public void setSendTimeLimit(Integer sendTimeLimit) {
		this.sendTimeLimit = sendTimeLimit;
	}

	public String getExeContentType() {
		return exeContentType;
	}

	public void setExeContentType(String exeContentType) {
		this.exeContentType = exeContentType;
	}

	public String getCampsegInstruct() {
		return campsegInstruct;
	}

	public void setCampsegInstruct(String campsegInstruct) {
		this.campsegInstruct = campsegInstruct;
	}
	public Integer getIs_containFee() {
		return is_containFee;
	}

	public void setIs_containFee(Integer is_containFee) {
		this.is_containFee = is_containFee;
	}
	
	public String getRuleIdAndNames() {
		return ruleIdAndNames;
	}

	public void setRuleIdAndNames(String ruleIdAndNames) {
		this.ruleIdAndNames = ruleIdAndNames;
	}

	public String getAvoidTimeRangeTmp() {
		return avoidTimeRangeTmp;
	}

	public void setAvoidTimeRangeTmp(String avoidTimeRangeTmp) {
		this.avoidTimeRangeTmp = avoidTimeRangeTmp;
	}

	public String getDrvDeptCode() {
		return drvDeptCode;
	}

	public void setDrvDeptCode(String drvDeptCode) {
		this.drvDeptCode = drvDeptCode;
	}

	public String getCepEventId() {
		return cepEventId;
	}

	public void setCepEventId(String cepEventId) {
		this.cepEventId = cepEventId;
	}

	public String getEventSourceId() {
		return eventSourceId;
	}

	public void setEventSourceId(String eventSourceId) {
		this.eventSourceId = eventSourceId;
	}

	public String getTestNumber() {
		return testNumber;
	}

	public void setTestNumber(String testNumber) {
		this.testNumber = testNumber;
	}

	public String getConsultContentHisLast() {
		return consultContentHisLast;
	}

	public void setConsultContentHisLast(String consultContentHisLast) {
		this.consultContentHisLast = consultContentHisLast;
	}

	public String getConsultContentHisAll() {
		return consultContentHisAll;
	}

	public void setConsultContentHisAll(String consultContentHisAll) {
		this.consultContentHisAll = consultContentHisAll;
	}

	public String getAvoidBotherTypeIds() {
		return avoidBotherTypeIds;
	}

	public void setAvoidBotherTypeIds(String avoidBotherTypeIds) {
		this.avoidBotherTypeIds = avoidBotherTypeIds;
	}

	public String getOldChannelCampContent() {
		return oldChannelCampContent;
	}

	public void setOldChannelCampContent(String oldChannelCampContent) {
		this.oldChannelCampContent = oldChannelCampContent;
	}

	public String getContactCountShow() {
		return contactCountShow;
	}

	public void setContactCountShow(String contactCountShow) {
		this.contactCountShow = contactCountShow;
	}

	public String getCampPriName() {
		return campPriName;
	}

	public String getChannelTypeId() {
		return channelTypeId;
	}

	public void setChannelTypeId(String channelTypeId) {
		this.channelTypeId = channelTypeId;
	}

	public void setCampPriName(String campPriName) {
		this.campPriName = campPriName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCampsegId() {
		return campsegId;
	}

	public void setCampsegId(String campsegId) {
		this.campsegId = campsegId;
	}

	public String getCampsegName() {
		return campsegName;
	}

	public void setCampsegName(String campsegName) {
		this.campsegName = campsegName;
	}

	public String getCampsegDesc() {
		return campsegDesc;
	}

	public void setCampsegDesc(String campsegDesc) {
		this.campsegDesc = campsegDesc;
	}

	public String getScenesId() {
		return scenesId;
	}

	public void setScenesId(String scenesId) {
		this.scenesId = scenesId;
	}

	public Short getCampsegTypeId() {
		return campsegTypeId;
	}

	public void setCampsegTypeId(Short campsegTypeId) {
		this.campsegTypeId = campsegTypeId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getContactType() {
		return contactType;
	}

	public void setContactType(Integer contactType) {
		this.contactType = contactType;
	}

	public Integer getContactCount() {
		return contactCount;
	}

	public void setContactCount(Integer contactCount) {
		this.contactCount = contactCount;
	}

	public Integer getCampDrvId() {
		return campDrvId;
	}

	public void setCampDrvId(Integer campDrvId) {
		this.campDrvId = campDrvId;
	}

	public Integer getWaveContactType() {
		return waveContactType;
	}

	public void setWaveContactType(Integer waveContactType) {
		this.waveContactType = waveContactType;
	}

	public Integer getWaveContactCount() {
		return waveContactCount;
	}

	public void setWaveContactCount(Integer waveContactCount) {
		this.waveContactCount = waveContactCount;
	}

	public String getCampsegPid() {
		return campsegPid;
	}

	public void setCampsegPid(String campsegPid) {
		this.campsegPid = campsegPid;
	}

	public Integer getCampClass() {
		return campClass;
	}

	public void setCampClass(Integer campClass) {
		this.campClass = campClass;
	}

	public String getWaveConId() {
		return waveConId;
	}

	public void setWaveConId(String waveConId) {
		this.waveConId = waveConId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
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

	public String getChannelCampContent() {
		return channelCampContent;
	}

	public void setChannelCampContent(String channelCampContent) {
		this.channelCampContent = channelCampContent;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
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

	public Integer getCampsegStatId() {
		return campsegStatId;
	}

	public void setCampsegStatId(Integer campsegStatId) {
		this.campsegStatId = campsegStatId;
	}

	public String getTimeRange() {
		return timeRange;
	}

	public void setTimeRange(String timeRange) {
		this.timeRange = timeRange;
	}

	public Integer getIsRelativeExecTime() {
		return isRelativeExecTime;
	}

	public void setIsRelativeExecTime(Integer isRelativeExecTime) {
		this.isRelativeExecTime = isRelativeExecTime;
	}

	public String getAbsoluteDates() {
		return absoluteDates;
	}

	public void setAbsoluteDates(String absoluteDates) {
		this.absoluteDates = absoluteDates;
	}

	public Integer getCampWaveDelayFrom() {
		return campWaveDelayFrom;
	}

	public void setCampWaveDelayFrom(Integer campWaveDelayFrom) {
		this.campWaveDelayFrom = campWaveDelayFrom;
	}

	public Integer getCampWaveDelayTo() {
		return campWaveDelayTo;
	}

	public void setCampWaveDelayTo(Integer campWaveDelayTo) {
		this.campWaveDelayTo = campWaveDelayTo;
	}

	public String getActiveTempletId() {
		return activeTempletId;
	}

	public void setActiveTempletId(String activeTempletId) {
		this.activeTempletId = activeTempletId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public Set getCampsegAttachs() {
		return campsegAttachs;
	}

	public void setCampsegAttachs(Set campsegAttachs) {
		this.campsegAttachs = campsegAttachs;
	}

	public List<MtlCampSeginfoBean> getCampSegList() {
		return campSegList;
	}

	public void setCampSegList(List<MtlCampSeginfoBean> campSegList) {
		this.campSegList = campSegList;
	}

	public String getCampsegNo() {
		return campsegNo;
	}

	public void setCampsegNo(String campsegNo) {
		this.campsegNo = campsegNo;
	}

	public String getCampPriId() {
		return campPriId;
	}

	public void setCampPriId(String campPriId) {
		this.campPriId = campPriId;
	}

	public String getAvoidTimeRange() {
		return avoidTimeRange;
	}

	public void setAvoidTimeRange(String avoidTimeRange) {
		this.avoidTimeRange = avoidTimeRange;
	}

	public boolean isParentIsLast() {
		return parentIsLast;
	}

	public void setParentIsLast(boolean parentIsLast) {
		this.parentIsLast = parentIsLast;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

	public Short getIsTemplate() {
		return isTemplate;
	}

	public void setIsTemplate(Short isTemplate) {
		this.isTemplate = isTemplate;
	}

	public String getRejectCampsegIds() {
		return rejectCampsegIds;
	}

	public void setRejectCampsegIds(String rejectCampsegIds) {
		this.rejectCampsegIds = rejectCampsegIds;
	}

	public String getRejectCampsegName() {
		return rejectCampsegName;
	}

	public void setRejectCampsegName(String rejectCampsegName) {
		this.rejectCampsegName = rejectCampsegName;
	}

	public String getCampId() {
		return campId;
	}

	public void setCampId(String campId) {
		this.campId = campId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCampName() {
		return campName;
	}

	public void setCampName(String campName) {
		this.campName = campName;
	}

	public String getEventActiveTempletId() {
		return eventActiveTempletId;
	}

	public void setEventActiveTempletId(String eventActiveTempletId) {
		this.eventActiveTempletId = eventActiveTempletId;
	}

	public String getCampDrvName() {
		return campDrvName;
	}

	public void setCampDrvName(String campDrvName) {
		this.campDrvName = campDrvName;
	}

	public String getExecContent() {
		return execContent;
	}

	public void setExecContent(String execContent) {
		this.execContent = execContent;
	}

	public String getCepEventShow() {
		return cepEventShow;
	}

	public void setCepEventShow(String cepEventShow) {
		this.cepEventShow = cepEventShow;
	}

	public String getRecommendDesc() {
		return recommendDesc;
	}

	public void setRecommendDesc(String recommendDesc) {
		this.recommendDesc = recommendDesc;
	}

	public String getOnlineServiceUrl() {
		return onlineServiceUrl;
	}

	public void setOnlineServiceUrl(String onlineServiceUrl) {
		this.onlineServiceUrl = onlineServiceUrl;
	}

	public String getChannelExtendInfo() {
		return channelExtendInfo;
	}

	public void setChannelExtendInfo(String channelExtendInfo) {
		this.channelExtendInfo = channelExtendInfo;
	}

	public String getChannelExtendInfoText() {
		return channelExtendInfoText;
	}

	public void setChannelExtendInfoText(String channelExtendInfoText) {
		this.channelExtendInfoText = channelExtendInfoText;
	}

	public String getCustgroupType() {
		return custgroupType;
	}

	public void setCustgroupType(String custgroupType) {
		this.custgroupType = custgroupType;
	}


	/**
	 * 客户群类型(0:无 ,1:客户群 2:来自其他活动规则的反馈)
	 * @return
	 */
	public Integer getTargetCustType() {
		return targetCustType;
	}

	/**
	 * 客户群类型(0:无 ,1:客户群 2:来自其他活动规则的反馈)
	 * @param targetCustType
	 */
	public void setTargetCustType(Integer targetCustType) {
		this.targetCustType = targetCustType;
	}
	
	/**
	 * 来自其他活动规则的反馈
	 * @return
	 */
	public McdTargetCustFeedback getMcdTargetCustFeedback() {
		return mcdTargetCustFeedback;
	}

	/**
	 * 来自其他活动规则的反馈
	 * @param mcdTargetCustFeedback
	 */
	public void setMcdTargetCustFeedback(McdTargetCustFeedback mcdTargetCustFeedback) {
		this.mcdTargetCustFeedback = mcdTargetCustFeedback;
	}
	public String getWebClassIdSiteCategoryId() {
		return webClassIdSiteCategoryId;
	}

	public void setWebClassIdSiteCategoryId(String webClassIdSiteCategoryId) {
		this.webClassIdSiteCategoryId = webClassIdSiteCategoryId;
	}

	public short getIsSplitCustgroup() {
		return isSplitCustgroup;
	}

	public void setIsSplitCustgroup(short isSplitCustgroup) {
		this.isSplitCustgroup = isSplitCustgroup;
	}

	public Short getIsMountHotPlan() {
		return isMountHotPlan;
	}

	public void setIsMountHotPlan(Short isMountHotPlan) {
		this.isMountHotPlan = isMountHotPlan;
	}

	public String getSceneType() {
		return sceneType;
	}

	public void setSceneType(String sceneType) {
		this.sceneType = sceneType;
	}

	/**
	 * @return the ad_ids
	 */
	public String getAd_ids() {
		return ad_ids;
	}

	/**
	 * @param ad_ids the ad_ids to set
	 */
	public void setAd_ids(String ad_ids) {
		this.ad_ids = ad_ids;
	}

	/**
	 * @return the ad_names
	 */
	public String getAd_names() {
		return ad_names;
	}

	/**
	 * @param ad_names the ad_names to set
	 */
	public void setAd_names(String ad_names) {
		this.ad_names = ad_names;
	}

	
}
