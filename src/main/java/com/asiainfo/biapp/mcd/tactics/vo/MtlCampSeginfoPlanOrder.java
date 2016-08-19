package com.asiainfo.biapp.mcd.tactics.vo;

/**
 * 
 * Title: 
 * Description: 营销策略产品订购和产品剔除
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-8-8 下午03:40:05
 * @version 1.0
 */

public class MtlCampSeginfoPlanOrder {
	private String campsegId;
	private String orderPlanIds;
	private String excludePlanIds;
	
	public String getCampsegId() {
		return campsegId;
	}
	public void setCampsegId(String campsegId) {
		this.campsegId = campsegId;
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
}
