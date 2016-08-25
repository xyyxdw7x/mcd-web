package com.asiainfo.biapp.mcd.custgroup.bean;

import java.util.Date;

public class CustInfoBean  extends BaseBean {
	private String customGroupId;//	客户群ID
	private String	customGroupName;//客户群名称
	private String	customGroupDesc;//客户群描述
	private String createUserId;//创建人ID
	private Date	createtime;//创建时间
	private String	ruleDesc;//客户群规则描述
	private String customSourceId;//客户群来源
	private Integer customNum;//客户群数量
	private Integer customStatusId;//客户群状态
	private Date effectiveTime	;//生效时间
	private Date failTime;//失效时间
	private Integer updateCycle;//客户群生成周期:1,一次性;2,月周期;3,日周期
	private Integer isPushOther;
	private String createUserName;  // 客户群创建人名称
	
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCustomGroupId() {
		return customGroupId;
	}
	public void setCustomGroupId(String customGroupId) {
		this.customGroupId = customGroupId;
	}
	public String getCustomGroupName() {
		return customGroupName;
	}
	public void setCustomGroupName(String customGroupName) {
		this.customGroupName = customGroupName;
	}
	public String getCustomGroupDesc() {
		return customGroupDesc;
	}
	public void setCustomGroupDesc(String customGroupDesc) {
		this.customGroupDesc = customGroupDesc;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getRuleDesc() {
		return ruleDesc;
	}
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	public String getCustomSourceId() {
		return customSourceId;
	}
	public void setCustomSourceId(String customSourceId) {
		this.customSourceId = customSourceId;
	}
	public Integer getCustomNum() {
		return customNum;
	}
	public void setCustomNum(Integer customNum) {
		this.customNum = customNum;
	}
	public Integer getCustomStatusId() {
		return customStatusId;
	}
	public void setCustomStatusId(Integer customStatusId) {
		this.customStatusId = customStatusId;
	}
	public Date getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	public Date getFailTime() {
		return failTime;
	}
	public void setFailTime(Date failTime) {
		this.failTime = failTime;
	}
	public Integer getUpdateCycle() {
		return updateCycle;
	}
	public void setUpdateCycle(Integer updateCycle) {
		this.updateCycle = updateCycle;
	}
	public Integer getIsPushOther() {
		return isPushOther;
	}
	public void setIsPushOther(Integer isPushOther) {
		this.isPushOther = isPushOther;
	}  
}
