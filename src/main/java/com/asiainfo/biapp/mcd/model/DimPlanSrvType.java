package com.asiainfo.biapp.mcd.model;

/**
 * 
 * Title: 
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-8-3 下午08:53:40
 * @version 1.0
 */

public class DimPlanSrvType {
	private String planTypeId;
	private String planTypeName;
	
	//IMCD_ZJ 为了统一使用前台模板  使用别名
	private String typeId;
	private String TypeName;
	
	
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return TypeName;
	}
	public void setTypeName(String typeName) {
		TypeName = typeName;
	}
	public String getPlanTypeId() {
		return planTypeId;
	}
	public void setPlanTypeId(String planTypeId) {
		this.planTypeId = planTypeId;
	}
	public String getPlanTypeName() {
		return planTypeName;
	}
	public void setPlanTypeName(String planTypeName) {
		this.planTypeName = planTypeName;
	}
}
