package com.asiainfo.biapp.mcd.common.vo.custgroup;

import java.util.Date;

/**
 * 
 * Title: 
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-7-18 上午10:08:35
 * @version 1.0
 */

public class McdCustgroupDef implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -540338883022707256L;
	private String customGroupId;
	private String customGroupName;
	private String customGroupDesc;
	private String createUserId;
	private Date createTime;
	private String ruleDesc;
	private String customSourceId;
	private int customNum;
	private int customStatusId;
	private Date effectiveTime;
	private Date failTime;
	private int updateCycle;
	private String custGroupUpdateCycle;
	
	private String dataDate;
	
	private String dataTime;  //数据生成日期
	
//	IMCD_ZJ为了前台统一使用模板   别名
	private String typeId;
	private String typeName;
	
	private String campsegCustGroupId;  //客户群和策略关联关系表信息主键
	
	private String createUserName;  //创建客户群用户名称
	
	
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCampsegCustGroupId() {
		return campsegCustGroupId;
	}
	public void setCampsegCustGroupId(String campsegCustGroupId) {
		this.campsegCustGroupId = campsegCustGroupId;
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
	public String getDataDate() {
		return dataDate;
	}
	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public int getCustomNum() {
		return customNum;
	}
	public void setCustomNum(int customNum) {
		this.customNum = customNum;
	}
	public int getCustomStatusId() {
		return customStatusId;
	}
	public void setCustomStatusId(int customStatusId) {
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
	public int getUpdateCycle() {
		return updateCycle;
	}
	public void setUpdateCycle(int updateCycle) {
		this.updateCycle = updateCycle;
	}
	public String getCustGroupUpdateCycle() {
		return custGroupUpdateCycle;
	}
	public void setCustGroupUpdateCycle(String custGroupUpdateCycle) {
		this.custGroupUpdateCycle = custGroupUpdateCycle;
	}
	public String getDataTime() {
		return dataTime;
	}
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}
	
}
