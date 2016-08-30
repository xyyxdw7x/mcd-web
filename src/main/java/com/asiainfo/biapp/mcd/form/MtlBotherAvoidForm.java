package com.asiainfo.biapp.mcd.form;

import java.util.TreeMap;

/**
 * Created on 2008-6-18
 * 
 * <p>Title: sForm</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author qixf
 * @version 1.0
 */
public class MtlBotherAvoidForm extends SysBaseForm {
	private String productNo;

	private String avoidBotherType;

	private Short avoidCustType;

	private Short cityId;

	private Integer countyId;

	private String deptId;

	private String avoidBotherDesc;

	private java.util.Date enterTime;

	private int ruleType;

	private String filterFileUrl;

	private TreeMap avoidBotherTypeMap = new TreeMap();//б

	/**
	 * @param avoidBotherTypeMap ~o avoidBotherTypeMap
	 */
	public void setAvoidBotherTypeMap(TreeMap avoidBotherTypeMap) {
		this.avoidBotherTypeMap = avoidBotherTypeMap;
	}

	/**
	 * @return avoidBotherDesc
	 */
	public String getAvoidBotherDesc() {
		return avoidBotherDesc;
	}

	/**
	 * @param avoidBotherDesc ~o avoidBotherDesc
	 */
	public void setAvoidBotherDesc(String avoidBotherDesc) {
		this.avoidBotherDesc = avoidBotherDesc;
	}

	/**
	 * @return avoidBotherType
	 */
	public String getAvoidBotherType() {
		return avoidBotherType;
	}

	/**
	 * @param avoidBotherType ~o avoidBotherType
	 */
	public void setAvoidBotherType(String avoidBotherType) {
		this.avoidBotherType = avoidBotherType;
	}

	/**
	 * @return avoidCustType
	 */
	public Short getAvoidCustType() {
		return avoidCustType;
	}

	/**
	 * @param avoidCustType ~o avoidCustType
	 */
	public void setAvoidCustType(Short avoidCustType) {
		this.avoidCustType = avoidCustType;
	}

	/**
	 * @return cityId
	 */
	public Short getCityId() {
		return cityId;
	}

	/**
	 * @param cityId ~o cityId
	 */
	public void setCityId(Short cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return countyId
	 */
	public Integer getCountyId() {
		return countyId;
	}

	/**
	 * @param countyId ~o countyId
	 */
	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}

	/**
	 * @return deptId
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId ~o deptId
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * @return enterTime
	 */
	public java.util.Date getEnterTime() {
		return enterTime;
	}

	/**
	 * @param enterTime ~o enterTime
	 */
	public void setEnterTime(java.util.Date enterTime) {
		this.enterTime = enterTime;
	}

	/**
	 * @return productNo
	 */
	public String getProductNo() {
		return productNo;
	}

	/**
	 * @param productNo ~o productNo
	 */
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	/**
	 * @return ruleType
	 */
	public int getRuleType() {
		return ruleType;
	}

	/**
	 * @param ruleType ~o ruleType
	 */
	public void setRuleType(int ruleType) {
		this.ruleType = ruleType;
	}

	/**
	 * @return filterFileUrl
	 */
	public String getFilterFileUrl() {
		return filterFileUrl;
	}

	/**
	 * @param filterFileUrl ~o filterFileUrl
	 */
	public void setFilterFileUrl(String filterFileUrl) {
		this.filterFileUrl = filterFileUrl;
	}

}
