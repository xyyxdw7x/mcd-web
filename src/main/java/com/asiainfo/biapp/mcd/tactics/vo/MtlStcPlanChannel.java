package com.asiainfo.biapp.mcd.tactics.vo;

/**
 * 
 * Title: 
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-7-17 上午10:02:16
 * @version 1.0
 */

public class MtlStcPlanChannel {
	private String id;
	private String planId;
	private String channelId;
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
	
	private boolean isChoose;  //该渠道在修改之前是否进行过操作  比如增删改查 
	
	private String messageType;   //boss运营位  引用语类型
	
	public String getId() {
		return id;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
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
	public boolean isChoose() {
		return isChoose;
	}
	public void setChoose(boolean isChoose) {
		this.isChoose = isChoose;
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
