package com.asiainfo.biapp.mcd.tactics.vo;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.asiainfo.biapp.framework.jdbc.annotation.Column;

/**
 * @author AsiaInfo-jie
 *
 */
public class MtlCampSeginfo {
	private String campsegId; // 活动编号
    private Integer pageNum; //当前页
    private Integer isSelectMy; //查询的是否是自己的 0 ：是     1 ：不是
    private Boolean isZJ;//是否是浙江 
    private Integer isMy;//是否是自己创建的  0 ：是     1 ：不是
    private Integer custgroupNumber;//客户群规模
    private String campDrvIds; //业务类型可多选情况
	private String campDrvName;// 活动类型名称
    private String campsegStatName; //状态名称
    private Short campsegStatId; // 营销活动状态ID
    private String channelId;
    private String channelTypeId;
    private String createUserid; // 活动策划人
    private Short campPriId; // 活动优先级,见维表:dim_camp_pri
    private String keywords;// 关键字
    private String areaId; // 营销活动所属地区
    private String campsegName;
    private String startDate; // 开始时间
    private String endDate; // 结束时间
    private String campsegPid; // 活动编号父ID
    private Short campsegTypeId; // 活动营销类型
    private String planId; // 产品编码
    private boolean isFatherNode;  //是否为策略基础信息；true:是基本信息；false:子规则
    private Integer isFileterDisturb;
    private Integer deptId; // 策划人部门id
    private Locale requestLocal;
    private Date createTime; // 本活动定义时间
    private String isApprove;
    private String campsegNo; // 多规则时，规则序号
    private String cepEventId; // 复杂事件ID
    private String eventRuleDesc;  //复杂事件描述
    private String custgroupId; // 客户群
    private String updatecycle;  //客户群更新周期
	private String orderPlanIds;//产品订购ID
	private String excludePlanIds;//剔除产品ID
	private List<MtlChannelDef> mtlChannelDefList;   //渠道执行
	private String cityId; // 策划人所属地市	
	private int splitCampSegInfo;// 是否按渠道类型拆分活动规则
	private String approveFlowid; // 默认内部审批流程
	private Short approveResult; // 审批结果
	private String approveResultDesc;//审批结果描述
	private String selectTempletId; // 筛选规则模板id
	private String custGroupAttrId;
	private Integer waveContactType; // 规则接触类型
	private Integer waveContactCount;
	private Integer campClass; // 活动实际含义1：活动基本信息,2：规则,3：波次
	private Integer isRelativeExecTime; // 执行时间是否相对时间
	private String absoluteDates; // 绝对时间串，逗号分隔
	private String siteCategoryIdClassId;//活动规则的渠道执行为综合网关时的内容站点、网站分类ID的关联(json字符串)
	private String channelCampContent; // 营销用语
	private String filePath;
	private MtlChannelDefCall mtlChannelDefCall;//渠道对应表_外呼
	private String eventActiveTempletId;// 事件规则模板ID
	private String custBaseDay;
	private String custBaseMonth;
	private String createUserName;
    private String campsegDesc; // 活动描述
    private Integer contactedUserNums; // 联系客户数
    private Integer contactOkuserNums; // 联系成功客户数
    private Integer receivedOkuserNums; // 营销成功客户数
    private Integer campsegContactUsernums; // 活动被接触控制的用户数

    private Short campsegContactFlag; // 是否进行接触控制
    private String evaluateComment; // 活动中止原因
    private String custListTabName;// 客户群清单表名
    private String timeInterval;
    private Integer targerUserNums; // 目标客户数
    private String currentTaskId;// 当前任务编号
    private String avoidBotherTypeIds;// 免打扰客户类型ID
    // 客户群类型(0:无 ,1:客户群 2:来自其他活动规则的反馈)
    private Integer targetCustType;
    private String activeTempletId;// 时机规则模板ID
    private String initCustListTab;//策略初始清单表
    private Date approveRemindTime;
    private String eventSourceId; // 外部事件源

	public String getCampDrvName() {
		return campDrvName;
	}
	public void setCampDrvName(String campDrvName) {
		this.campDrvName = campDrvName;
	}
	public String getCustBaseMonth() {
		return custBaseMonth;
	}
	public void setCustBaseMonth(String custBaseMonth) {
		this.custBaseMonth = custBaseMonth;
	}
	public String getCustBaseDay() {
		return custBaseDay;
	}
	public void setCustBaseDay(String custBaseDay) {
		this.custBaseDay = custBaseDay;
	}
	public Integer getCustgroupNumber() {
		return custgroupNumber;
	}
	public void setCustgroupNumber(Integer custgroupNumber) {
		this.custgroupNumber = custgroupNumber;
	}
	public String getEventActiveTempletId() {
		return eventActiveTempletId;
	}
	public void setEventActiveTempletId(String eventActiveTempletId) {
		this.eventActiveTempletId = eventActiveTempletId;
	}
	public MtlChannelDefCall getMtlChannelDefCall() {
		return mtlChannelDefCall;
	}
	public void setMtlChannelDefCall(MtlChannelDefCall mtlChannelDefCall) {
		this.mtlChannelDefCall = mtlChannelDefCall;
	}

	public String getSiteCategoryIdClassId() {
		return siteCategoryIdClassId;
	}
	public void setSiteCategoryIdClassId(String siteCategoryIdClassId) {
		this.siteCategoryIdClassId = siteCategoryIdClassId;
	}
	public String getChannelCampContent() {
		return channelCampContent;
	}
	public void setChannelCampContent(String channelCampContent) {
		this.channelCampContent = channelCampContent;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getAbsoluteDates() {
		return absoluteDates;
	}
	public void setAbsoluteDates(String absoluteDates) {
		this.absoluteDates = absoluteDates;
	}
	public Integer getIsRelativeExecTime() {
		return isRelativeExecTime;
	}
	public void setIsRelativeExecTime(Integer isRelativeExecTime) {
		this.isRelativeExecTime = isRelativeExecTime;
	}
	
	public Short getCampPriId() {
        return campPriId;
    }
    public void setCampPriId(Short campPriId) {
        this.campPriId = campPriId;
    }
    public Integer getCampClass() {
		return campClass;
	}
	public void setCampClass(Integer campClass) {
		this.campClass = campClass;
	}
	public Integer getWaveContactCount() {
		return waveContactCount;
	}
	public void setWaveContactCount(Integer waveContactCount) {
		this.waveContactCount = waveContactCount;
	}
	public Integer getWaveContactType() {
		return waveContactType;
	}
	public void setWaveContactType(Integer waveContactType) {
		this.waveContactType = waveContactType;
	}
	public String getCustGroupAttrId() {
		return custGroupAttrId;
	}
	public void setCustGroupAttrId(String custGroupAttrId) {
		this.custGroupAttrId = custGroupAttrId;
	}
	public String getCampsegId() {
		return campsegId;
	}
	@Column(name="campseg_id")
	public void setCampsegId(String campsegId) {
		this.campsegId = campsegId;
	}
	public String getSelectTempletId() {
		return selectTempletId;
	}
	public void setSelectTempletId(String selectTempletId) {
		this.selectTempletId = selectTempletId;
	}
	public String getApproveFlowid() {
		return approveFlowid;
	}
	public void setApproveFlowid(String approveFlowid) {
		this.approveFlowid = approveFlowid;
	}
	public Short getApproveResult() {
		return approveResult;
	}
	public void setApproveResult(Short approveResult) {
		this.approveResult = approveResult;
	}

	public String getApproveResultDesc() {
        return approveResultDesc;
    }
    public void setApproveResultDesc(String approveResultDesc) {
        this.approveResultDesc = approveResultDesc;
    }
    public int getSplitCampSegInfo() {
		return splitCampSegInfo;
	}
	public void setSplitCampSegInfo(int splitCampSegInfo) {
		this.splitCampSegInfo = splitCampSegInfo;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public List<MtlChannelDef> getMtlChannelDefList() {
		return mtlChannelDefList;
	}
	public void setMtlChannelDefList(List<MtlChannelDef> mtlChannelDefList) {
		this.mtlChannelDefList = mtlChannelDefList;
	}
	public String getOrderPlanIds() {
		return orderPlanIds;
	}
	public void setOrderPlanIds(String orderPlanIds) {
		this.orderPlanIds = orderPlanIds;
	}
	public String getExcludePlanIds() {
		return excludePlanIds;
	}
	public void setExcludePlanIds(String excludePlanIds) {
		this.excludePlanIds = excludePlanIds;
	}
	public String getUpdatecycle() {
		return updatecycle;
	}
	public void setUpdatecycle(String updatecycle) {
		this.updatecycle = updatecycle;
	}
	public String getCustgroupId() {
		return custgroupId;
	}
	public void setCustgroupId(String custgroupId) {
		this.custgroupId = custgroupId;
	}
	public String getEventRuleDesc() {
		return eventRuleDesc;
	}
	public void setEventRuleDesc(String eventRuleDesc) {
		this.eventRuleDesc = eventRuleDesc;
	}
	public String getCepEventId() {
		return cepEventId;
	}
	public void setCepEventId(String cepEventId) {
		this.cepEventId = cepEventId;
	}
	public String getCampsegNo() {
		return campsegNo;
	}
	public void setCampsegNo(String campsegNo) {
		this.campsegNo = campsegNo;
	}
	public String getIsApprove() {
		return isApprove;
	}
	public void setIsApprove(String isApprove) {
		this.isApprove = isApprove;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Locale getRequestLocal() {
		return requestLocal;
	}
	public void setRequestLocal(Locale requestLocal) {
		this.requestLocal = requestLocal;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCampsegPid() {
		return campsegPid;
	}
	@Column(name="campseg_pid")
	public void setCampsegPid(String campsegPid) {
		this.campsegPid = campsegPid;
	}
	public Short getCampsegTypeId() {
		return campsegTypeId;
	}
	public void setCampsegTypeId(Short campsegTypeId) {
		this.campsegTypeId = campsegTypeId;
	}
	public String getPlanId() {
		return planId;
	}
	@Column(name="PLAN_ID")
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public boolean isFatherNode() {
		return isFatherNode;
	}
	public void setFatherNode(boolean isFatherNode) {
		this.isFatherNode = isFatherNode;
	}
	public Integer getIsFileterDisturb() {
		return isFileterDisturb;
	}
	public void setIsFileterDisturb(Integer isFileterDisturb) {
		this.isFileterDisturb = isFileterDisturb;
	}
	public String getCampsegName() {
		return campsegName;
	}
	public void setCampsegName(String campsegName) {
		this.campsegName = campsegName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public Integer getPageNum() {
        return pageNum;
    }
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
    public Integer getIsSelectMy() {
        return isSelectMy;
    }
    public void setIsSelectMy(Integer isSelectMy) {
        this.isSelectMy = isSelectMy;
    }
    public Boolean getIsZJ() {
        return isZJ;
    }
    public void setIsZJ(Boolean isZJ) {
        this.isZJ = isZJ;
    }
    public Integer getIsMy() {
        return isMy;
    }
    public void setIsMy(Integer isMy) {
        this.isMy = isMy;
    }
    public String getCampDrvIds() {
        return campDrvIds;
    }
    public void setCampDrvIds(String campDrvIds) {
        this.campDrvIds = campDrvIds;
    }
    public String getCampsegStatName() {
        return campsegStatName;
    }
    public void setCampsegStatName(String campsegStatName) {
        this.campsegStatName = campsegStatName;
    }
    public Short getCampsegStatId() {
        return campsegStatId;
    }
    public void setCampsegStatId(Short campsegStatId) {
        this.campsegStatId = campsegStatId;
    }
    public String getChannelId() {
        return channelId;
    }
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
    public String getChannelTypeId() {
        return channelTypeId;
    }
    public void setChannelTypeId(String channelTypeId) {
        this.channelTypeId = channelTypeId;
    }
    public String getCreateUserid() {
        return createUserid;
    }
    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid;
    }
    public String getKeywords() {
        return keywords;
    }
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    public String getAreaId() {
        return areaId;
    }
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
    public String getCreateUserName() {
        return createUserName;
    }
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
    public String getCampsegDesc() {
        return campsegDesc;
    }
    public void setCampsegDesc(String campsegDesc) {
        this.campsegDesc = campsegDesc;
    }
    public Integer getContactedUserNums() {
        return contactedUserNums;
    }
    public void setContactedUserNums(Integer contactedUserNums) {
        this.contactedUserNums = contactedUserNums;
    }
    public Integer getContactOkuserNums() {
        return contactOkuserNums;
    }
    public void setContactOkuserNums(Integer contactOkuserNums) {
        this.contactOkuserNums = contactOkuserNums;
    }
    public Integer getReceivedOkuserNums() {
        return receivedOkuserNums;
    }
    public void setReceivedOkuserNums(Integer receivedOkuserNums) {
        this.receivedOkuserNums = receivedOkuserNums;
    }
    public Short getCampsegContactFlag() {
        return campsegContactFlag;
    }
    public void setCampsegContactFlag(Short campsegContactFlag) {
        this.campsegContactFlag = campsegContactFlag;
    }
    public Integer getCampsegContactUsernums() {
        return campsegContactUsernums;
    }
    public void setCampsegContactUsernums(Integer campsegContactUsernums) {
        this.campsegContactUsernums = campsegContactUsernums;
    }
    public String getEvaluateComment() {
        return evaluateComment;
    }
    public void setEvaluateComment(String evaluateComment) {
        this.evaluateComment = evaluateComment;
    }
    public String getCustListTabName() {
        return custListTabName;
    }
    public void setCustListTabName(String custListTabName) {
        this.custListTabName = custListTabName;
    }
    public String getTimeInterval() {
        return timeInterval;
    }
    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }
    public Integer getTargerUserNums() {
        return targerUserNums;
    }
    public void setTargerUserNums(Integer targerUserNums) {
        this.targerUserNums = targerUserNums;
    }
    public String getCurrentTaskId() {
        return currentTaskId;
    }
    public void setCurrentTaskId(String currentTaskId) {
        this.currentTaskId = currentTaskId;
    }
    public String getAvoidBotherTypeIds() {
        return avoidBotherTypeIds;
    }
    public void setAvoidBotherTypeIds(String avoidBotherTypeIds) {
        this.avoidBotherTypeIds = avoidBotherTypeIds;
    }
    public Integer getTargetCustType() {
        return targetCustType;
    }
    public void setTargetCustType(Integer targetCustType) {
        this.targetCustType = targetCustType;
    }
    public String getActiveTempletId() {
        return activeTempletId;
    }
    @Column(name="ACTIVE_TEMPLET_ID")
    public void setActiveTempletId(String activeTempletId) {
        this.activeTempletId = activeTempletId;
    }
    public String getInitCustListTab() {
        return initCustListTab;
    }
    public void setInitCustListTab(String initCustListTab) {
        this.initCustListTab = initCustListTab;
    }
    public Date getApproveRemindTime() {
        return approveRemindTime;
    }
    public void setApproveRemindTime(Date approveRemindTime) {
        this.approveRemindTime = approveRemindTime;
    }
    public String getEventSourceId() {
        return eventSourceId;
    }
    @Column(name="EVENT_SOURCE")
    public void setEventSourceId(String eventSourceId) {
        this.eventSourceId = eventSourceId;
    }
    
}
