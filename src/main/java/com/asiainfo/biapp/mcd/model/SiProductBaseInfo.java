package com.asiainfo.biapp.mcd.model;

import java.sql.Timestamp;

/**
 * VGOP 产品基础信息表
 * @author FUYU
 *
 */
public class SiProductBaseInfo {

	//产品信息标识
	private String productId;
	//所属平台产品编码
	private String platProductCode;
	//业务平台编码
	private String svcPlatCode;
	//业务分类标识
	private String svcCategoryId;
	//产品信息名称
	private String productName;
	//产品信息描述
	private String productDesc;
	//业务平台标签
	private String svcPlatTag;
	//信息格式
	private String infoFormat;	
	//产品类别
	private String productClass;
	//信息类型
	private String infoType;
	//语种
	private String language;
	//作者
	private String author;
	//发布状态
	private String issueStatus;
	//发布时间
	private Timestamp issueTime;
	//更新时间
	private Timestamp updateTime;
	//失效日期
	private Timestamp expireDate;
	//计费值
	private Double billVal;
	//计费方式编码
	private String billCode;
	//提供商编码
	private String providerCode;
	//内容标签
	private String productTag;
	//地市组合标识
	private String cityGroupId;
	//品牌组合标识
	private String brandGroupId;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getPlatProductCode() {
		return platProductCode;
	}
	public void setPlatProductCode(String platProductCode) {
		this.platProductCode = platProductCode;
	}
	public String getSvcPlatCode() {
		return svcPlatCode;
	}
	public void setSvcPlatCode(String svcPlatCode) {
		this.svcPlatCode = svcPlatCode;
	}
	public String getSvcCategoryId() {
		return svcCategoryId;
	}
	public void setSvcCategoryId(String svcCategoryId) {
		this.svcCategoryId = svcCategoryId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getSvcPlatTag() {
		return svcPlatTag;
	}
	public void setSvcPlatTag(String svcPlatTag) {
		this.svcPlatTag = svcPlatTag;
	}
	public String getInfoFormat() {
		return infoFormat;
	}
	public void setInfoFormat(String infoFormat) {
		this.infoFormat = infoFormat;
	}
	public String getProductClass() {
		return productClass;
	}
	public void setProductClass(String productClass) {
		this.productClass = productClass;
	}
	public String getInfoType() {
		return infoType;
	}
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getIssueStatus() {
		return issueStatus;
	}
	public void setIssueStatus(String issueStatus) {
		this.issueStatus = issueStatus;
	}
	public Timestamp getIssueTime() {
		return issueTime;
	}
	public void setIssueTime(Timestamp issueTime) {
		this.issueTime = issueTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public Timestamp getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Timestamp expireDate) {
		this.expireDate = expireDate;
	}
	public Double getBillVal() {
		return billVal;
	}
	public void setBillVal(Double billVal) {
		this.billVal = billVal;
	}
	public String getBillCode() {
		return billCode;
	}
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	public String getProviderCode() {
		return providerCode;
	}
	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}
	public String getProductTag() {
		return productTag;
	}
	public void setProductTag(String productTag) {
		this.productTag = productTag;
	}
	public String getCityGroupId() {
		return cityGroupId;
	}
	public void setCityGroupId(String cityGroupId) {
		this.cityGroupId = cityGroupId;
	}
	public String getBrandGroupId() {
		return brandGroupId;
	}
	public void setBrandGroupId(String brandGroupId) {
		this.brandGroupId = brandGroupId;
	}
	
	
}
