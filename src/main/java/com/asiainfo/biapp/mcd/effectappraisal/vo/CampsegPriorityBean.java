package com.asiainfo.biapp.mcd.effectappraisal.vo;

/**
 * 
 * Title: 
 * Description:
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2016-3-17 上午10:29:14
 * @version 1.0
 */

public class CampsegPriorityBean {
	private String campsegId;
	private int priOrderNum;
	private String campsegName;
	private String planId;
	private String planName;
	private int custgroupNumber;
	private int ineffectieDays;
	private String IsNewDays;
	private String cityId;
	private String channelId;
	private String chnAdivId;  //运营位
	private int campSuccNum;  //营销成功用户数
	private int campUserNum;  //营销用户数
	private float succRate;  //转化率
	
	public String getCampsegId() {
		return campsegId;
	}
	public void setCampsegId(String campsegId) {
		this.campsegId = campsegId;
	}
	public int getPriOrderNum() {
		return priOrderNum;
	}
	public void setPriOrderNum(int priOrderNum) {
		this.priOrderNum = priOrderNum;
	}
	public String getCampsegName() {
		return campsegName;
	}
	public void setCampsegName(String campsegName) {
		this.campsegName = campsegName;
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
	public int getCustgroupNumber() {
		return custgroupNumber;
	}
	public void setCustgroupNumber(int custgroupNumber) {
		this.custgroupNumber = custgroupNumber;
	}
	public int getIneffectieDays() {
		return ineffectieDays;
	}
	public void setIneffectieDays(int ineffectieDays) {
		this.ineffectieDays = ineffectieDays;
	}
	public String getIsNewDays() {
		return IsNewDays;
	}
	public void setIsNewDays(String isNewDays) {
		IsNewDays = isNewDays;
	}
	public int getCampSuccNum() {
		return campSuccNum;
	}
	public void setCampSuccNum(int campSuccNum) {
		this.campSuccNum = campSuccNum;
	}
	public int getCampUserNum() {
		return campUserNum;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public void setCampUserNum(int campUserNum) {
		this.campUserNum = campUserNum;
	}
	public float getSuccRate() {
		return succRate;
	}
	public void setSuccRate(float succRate) {
		this.succRate = succRate;
	}
	public String getChnAdivId() {
		return chnAdivId;
	}
	public void setChnAdivId(String chnAdivId) {
		this.chnAdivId = chnAdivId;
	} 
	
}


