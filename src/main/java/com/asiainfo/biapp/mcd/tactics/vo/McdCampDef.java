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

	@Column(name = "CAMPSEG_STAT_ID")
	private Short statId; // 营销活动状态ID
	@Column(name = "CREATE_USERID")
	private String createUserId; // 活动策划人
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
	@Transient
	private String eventRuleDesc; // 实时事件名称
	@Column(name = "CAMP_CLASS")
	private Integer campClass; // 活动实际含义1：活动基本信息,2：规则,3：波次
	
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
	private List<McdCampChannelList> mtlChannelDefList; // 渠道执行
	@Transient
	private MtlChannelDefCall mtlChannelDefCall;// 渠道对应表_外呼
	@Column(name = "APPROVE_RESULT")
	private Short approveResult; // 审批结果
	@Transient
	private String approveResultDesc;// 审批结果描述
	public String getCampId() {
		return campId;
	}
	public void setCampId(String campId) {
		this.campId = campId;
	}
	public Short getStatId() {
		return statId;
	}
	public void setStatId(Short statId) {
		this.statId = statId;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public Short getPriId() {
		return priId;
	}
	public void setPriId(Short priId) {
		this.priId = priId;
	}
	public String getCampName() {
		return campName;
	}
	public void setCampName(String campName) {
		this.campName = campName;
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
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public Short getTypeId() {
		return typeId;
	}
	public void setTypeId(Short typeId) {
		this.typeId = typeId;
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
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getApproveFlowId() {
		return approveFlowId;
	}
	public void setApproveFlowId(String approveFlowId) {
		this.approveFlowId = approveFlowId;
	}
	public Integer getTargerUserNums() {
		return targerUserNums;
	}
	public void setTargerUserNums(Integer targerUserNums) {
		this.targerUserNums = targerUserNums;
	}
	public String getCustListTab() {
		return custListTab;
	}
	public void setCustListTab(String custListTab) {
		this.custListTab = custListTab;
	}
	public Date getLastRemindTime() {
		return lastRemindTime;
	}
	public void setLastRemindTime(Date lastRemindTime) {
		this.lastRemindTime = lastRemindTime;
	}
	public String getCampNo() {
		return campNo;
	}
	public void setCampNo(String campNo) {
		this.campNo = campNo;
	}
	public String getCepEventId() {
		return cepEventId;
	}
	public void setCepEventId(String cepEventId) {
		this.cepEventId = cepEventId;
	}
	public String getEventRuleDesc() {
		return eventRuleDesc;
	}
	public void setEventRuleDesc(String eventRuleDesc) {
		this.eventRuleDesc = eventRuleDesc;
	}
	public Integer getCampClass() {
		return campClass;
	}
	public void setCampClass(Integer campClass) {
		this.campClass = campClass;
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
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public boolean getIsFatherNode() {
		return isFatherNode;
	}
	public void setIsFatherNode(boolean isFatherNode) {
		this.isFatherNode = isFatherNode;
	}
	public String getIsApprove() {
		return isApprove;
	}
	public void setIsApprove(String isApprove) {
		this.isApprove = isApprove;
	}
	public String getCustgroupId() {
		return custgroupId;
	}
	public void setCustgroupId(String custgroupId) {
		this.custgroupId = custgroupId;
	}
	public List<McdCampChannelList> getMtlChannelDefList() {
		return mtlChannelDefList;
	}
	public void setMtlChannelDefList(List<McdCampChannelList> mtlChannelDefList) {
		this.mtlChannelDefList = mtlChannelDefList;
	}
	public MtlChannelDefCall getMtlChannelDefCall() {
		return mtlChannelDefCall;
	}
	public void setMtlChannelDefCall(MtlChannelDefCall mtlChannelDefCall) {
		this.mtlChannelDefCall = mtlChannelDefCall;
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

}