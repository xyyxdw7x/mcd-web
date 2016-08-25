package com.asiainfo.biapp.mcd.avoid.form;

/**
 * 
 * Title: 
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-7-16 下午05:53:43
 * @version 1.0
 */

public class MtlStcPlanForm{
	private String keyWords;//关键字
	private String typeId;//政策类别id
	private String channelTypeId;//渠道类型id
	private int currentPage;//当前页
	
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getChannelTypeId() {
		return channelTypeId;
	}
	public void setChannelTypeId(String channelTypeId) {
		this.channelTypeId = channelTypeId;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}
