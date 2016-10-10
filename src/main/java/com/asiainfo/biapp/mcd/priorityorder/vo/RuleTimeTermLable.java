package com.asiainfo.biapp.mcd.priorityorder.vo;

/**
 * 
 * Title: 
 * Description:
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2016-2-24 下午2:44:21
 * @version 1.0
 */

public class RuleTimeTermLable {
	//实时事件弹出页变量初始化
	private String catId;
	private String catName;
	private String functionId;
	private String functionNameDesc;
	private String functionType;
	
//	点击弹出页后初始化的变量
	private String paramId;
	private String paramName;
	
	private String productNo;
	
	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getFunctionId() {
		return functionId;
	}
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	public String getFunctionNameDesc() {
		return functionNameDesc;
	}
	public void setFunctionNameDesc(String functionNameDesc) {
		this.functionNameDesc = functionNameDesc;
	}
	public String getParamId() {
		return paramId;
	}
	public void setParamId(String paramId) {
		this.paramId = paramId;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getFunctionType() {
		return functionType;
	}
	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}
	
}


