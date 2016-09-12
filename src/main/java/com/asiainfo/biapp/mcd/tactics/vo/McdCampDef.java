package com.asiainfo.biapp.mcd.tactics.vo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author AsiaInfo-jie
 *
 */
@Table(name = "mcd_camp_def")
public class McdCampDef {
	@Id
	@Column(name = "CAMPSEG_ID")
	private String campId; // 活动编号

	@Transient
	private Integer pageNum; // 当前页
	@Transient
	private Integer isSelectMy; // 查询的是否是自己的 0 ：是 1 ：不是
	@Transient
	private Boolean isZJ;// 是否是浙江
	@Transient
	private Integer isMy;// 是否是自己创建的 0 ：是 1 ：不是
	@Transient
	private String channelId;
	@Transient
	private String keywords;// 关键字
	@Transient
	private boolean isFatherNode; // 是否为策略基础信息；true:是基本信息；false:子规则
	@Transient
	private String isApprove;
	@Transient
	private String custgroupId; // 客户群
	@Transient
	private List<MtlChannelDef> mtlChannelDefList; // 渠道执行
	@Transient
	private MtlChannelDefCall mtlChannelDefCall;// 渠道对应表_外呼
	

	@Column(name = "CAMPSEG_STAT_ID")
	private Short statId; // 营销活动状态ID
	@Column(name = "CREATE_USERID")
	private String createUserid; // 活动策划人
	@Column(name = "CAMP_PRI_ID")
	private Short priId;// 活动优先级,见维表:dim_camp_pri
	@Column(name = "CAMPSEG_NAME")
	private String campName;
	@Column(name = "START_DATE")
	private String startDate; // 开始时间
	@Column(name = "END_DATE")
	private String endDate; // 结束时间
	@Column(name = "CAMPSEG_PID")
	private String pid; // 活动编号父ID
	@Column(name = "CAMPSEG_TYPE_ID")
	private Short typeId; // 活动营销类型
	@Column(name = "PLAN_ID")
	private String planId; // 产品编码
	@Column(name = "IS_FILTER_DISTURB")
	private Integer isFileterDisturb;// 是否免打扰
	@Column(name = "DEPTID")
	private Integer deptId; // 策划人部门id
	@Column(name = "CREATE_TIME")
	private Date createTime; // 本活动定义时间
	@Column(name = "CREATE_USERNAME")
	private String createUserName;
	@Column(name = "CITY_ID")
	private String cityId; // 策划人所属地市
	@Column(name = "APPROVE_FLOW_ID")
	private String approveFlowId; // 默认内部审批流程
	@Column(name = "TARGER_USER_NUMS")
	private Integer targerUserNums; // 目标客户数
	@Column(name = "INIT_CUST_LIST_TAB")
	private String custListTab;// 策略初始清单表
	@Column(name = "APPROVE_REMIND_TIME")
	private Date lastRemindTime;
	
	@Column(name = "campseg_no")
	private String campNo; // 多规则时，规则序号
	@Column(name = "CEP_EVENT_ID")
	private String cepEventId; // 复杂事件ID
	@Column(name = "event_rule_desc")
	private String eventRuleDesc; // 实时事件名称
	@Column(name = "CAMP_CLASS")
	private Integer campClass; // 活动实际含义1：活动基本信息,2：规则,3：波次
	
	@Transient
	private Short approveResult; // 审批结果
	@Transient
	private String approveResultDesc;// 审批结果描述
	

	@Transient
	private String avoidBotherTypeIds;// 免打扰客户类型ID


	@Transient
	private String planName;

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public boolean getIsFatherNode() {
		return isFatherNode;
	}

	public void setFatherNode(boolean isFatherNode) {
		this.isFatherNode = isFatherNode;
	}


	public MtlChannelDefCall getMtlChannelDefCall() {
		return mtlChannelDefCall;
	}

	public void setMtlChannelDefCall(MtlChannelDefCall mtlChannelDefCall) {
		this.mtlChannelDefCall = mtlChannelDefCall;
	}


	public Short getCampPriId() {
		return priId;
	}

	public void setCampPriId(Short campPriId) {
		this.priId = campPriId;
	}

	public Integer getCampClass() {
		return campClass;
	}

	public void setCampClass(Integer campClass) {
		this.campClass = campClass;
	}

	public String getCampsegId() {
		return campId;
	}

	public void setCampsegId(String campsegId) {
		this.campId = campsegId;
	}

	public String getApproveFlowid() {
		return approveFlowId;
	}

	public void setApproveFlowid(String approveFlowid) {
		this.approveFlowId = approveFlowid;
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
		return campNo;
	}

	public void setCampsegNo(String campsegNo) {
		this.campNo = campsegNo;
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
		return pid;
	}

	public void setCampsegPid(String campsegPid) {
		this.pid = campsegPid;
	}

	public Short getCampsegTypeId() {
		return typeId;
	}

	public void setCampsegTypeId(Short campsegTypeId) {
		this.typeId = campsegTypeId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public Integer getIsFileterDisturb() {
		return isFileterDisturb;
	}

	public void setIsFileterDisturb(Integer isFileterDisturb) {
		this.isFileterDisturb = isFileterDisturb;
	}

	public String getCampsegName() {
		return campName;
	}

	public void setCampsegName(String campsegName) {
		this.campName = campsegName;
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

	public Short getCampsegStatId() {
		return statId;
	}

	public void setCampsegStatId(Short campsegStatId) {
		this.statId = campsegStatId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
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

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}


	public Integer getTargerUserNums() {
		return targerUserNums;
	}

	public void setTargerUserNums(Integer targerUserNums) {
		this.targerUserNums = targerUserNums;
	}



	public String getAvoidBotherTypeIds() {
		return avoidBotherTypeIds;
	}

	public void setAvoidBotherTypeIds(String avoidBotherTypeIds) {
		this.avoidBotherTypeIds = avoidBotherTypeIds;
	}



	public String getInitCustListTab() {
		return custListTab;
	}

	public void setInitCustListTab(String initCustListTab) {
		this.custListTab = initCustListTab;
	}

	public Date getApproveRemindTime() {
		return lastRemindTime;
	}

	public void setApproveRemindTime(Date approveRemindTime) {
		this.lastRemindTime = approveRemindTime;
	}



}
