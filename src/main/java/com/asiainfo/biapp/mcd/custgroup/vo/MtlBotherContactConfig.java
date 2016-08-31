package com.asiainfo.biapp.mcd.custgroup.vo;

/**
 * 
 * Title: 
 * Description: 免打扰和频次控制系统配置
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-8-18 上午20:10:38
 * @version 1.0
 */

public class MtlBotherContactConfig {
	
	private int campsegTypeId;
	private String channelId;
	private int avoidBotherFlag;
	private int contactControlFlag;
	private int paramDays;
	private int paramNum;
	
	public int getCampsegTypeId() {
		return campsegTypeId;
	}
	public void setCampsegTypeId(int campsegTypeId) {
		this.campsegTypeId = campsegTypeId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public int getAvoidBotherFlag() {
		return avoidBotherFlag;
	}
	public void setAvoidBotherFlag(int avoidBotherFlag) {
		this.avoidBotherFlag = avoidBotherFlag;
	}
	public int getContactControlFlag() {
		return contactControlFlag;
	}
	public void setContactControlFlag(int contactControlFlag) {
		this.contactControlFlag = contactControlFlag;
	}
	public int getParamDays() {
		return paramDays;
	}
	public void setParamDays(int paramDays) {
		this.paramDays = paramDays;
	}
	public int getParamNum() {
		return paramNum;
	}
	public void setParamNum(int paramNum) {
		this.paramNum = paramNum;
	}
}
