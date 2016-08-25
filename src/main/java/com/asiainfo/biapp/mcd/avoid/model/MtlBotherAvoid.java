package com.asiainfo.biapp.mcd.avoid.model;

import java.util.Date;

/**
 * 
 * Title: 
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author zhaokai 2016-8-2 20:56:35
 * @version 1.0
 */

public class MtlBotherAvoid implements java.io.Serializable {
	private String avoidBotherType;
	private Short avoidCustType;
	private String productNo;
	private Short cityId;
	private Short countyId;
	private String deptId;
	private String avoidBotherDesc;
	private String enterTime;
	private Short userTypeId;
	private String createUserId;
	private String createUserName;
	private String keywords;
	private String sortColumn;
	private String sortOrderBy;

	public String getAvoidBotherType(){
	    return avoidBotherType;
	}
	public Short getAvoidCustType(){
	    return avoidCustType;
	}
	public String getProductNo(){
	    return productNo;
	}
	public Short getCityId(){
	    return cityId;
	}
	public Short getCountyId(){
	    return countyId;
	}
	public String getDeptId(){
	    return deptId;
	}
	public String getAvoidBotherDesc(){
	    return avoidBotherDesc;
	}
	public String getEnterTime(){
	    return enterTime;
	}
	public Short getUserTypeId(){
	    return userTypeId;
	}
	public String getCreateUserId(){
	    return createUserId;
	}
	public String getCreateUserName(){
	    return createUserName;
	}
	public String getKeywords(){
	    return keywords;
	}
	public String getSortColumn(){
	    return sortColumn;
	}
	public String getSortOrderBy(){
	    return sortOrderBy;
	}
	
	public void setAvoidBotherType(String avoidBotherType){
	    this.avoidBotherType=avoidBotherType;
	}
	public void setAvoidCustType(Short avoidCustType){
	    this.avoidCustType=avoidCustType;
	}
	public void setProductNo(String productNo){
	    this.productNo=productNo;
	}
	public void setCityId(Short cityId){
	    this.cityId=cityId;
	}
	public void setCountyId(Short countyId){
	    this.countyId=countyId;
	}
	public void setDeptId(String deptId){
	    this.deptId=deptId;
	}
	public void setAvoidBotherDesc(String avoidBotherDesc){
	    this.avoidBotherDesc=avoidBotherDesc;
	}
	public void setEnterTime(String enterTime){
	    this.enterTime=enterTime;
	}
	public void setUserTypeId(Short userTypeId){
	    this.userTypeId=userTypeId;
	}
	public void setCreateUserId(String createUserId){
	    this.createUserId=createUserId;
	}
	public void setCreateUserName(String createUserName){
	    this.createUserName=createUserName;
	}
	public void setKeywords(String keywords){
	    this.keywords=keywords;
	}
	public void setSortColumn(String sortColumn){
	    this.sortColumn=sortColumn;
	}
	public void setSortOrderBy(String sortOrderBy){
	    this.sortOrderBy=sortOrderBy;
	}
}
