package com.asiainfo.biapp.mcd.model;

/**
 * VGOP 业务分类维表
 * @author FUYU
 *
 */
public class DimSvcCategory {
	
	//业务分类标识
	private String svcCategoryId;
	//业务平台编码
	private String svcPlatCode;
	//业务分类名称
	private String svcCategoryName;
	//业务分类父编码
	private String svcCategoryParentCode;
	//业务分类编码
	private String svcCategoryCode;
	
	public String getSvcCategoryId() {
		return svcCategoryId;
	}
	public void setSvcCategoryId(String svcCategoryId) {
		this.svcCategoryId = svcCategoryId;
	}
	public String getSvcPlatCode() {
		return svcPlatCode;
	}
	public void setSvcPlatCode(String svcPlatCode) {
		this.svcPlatCode = svcPlatCode;
	}
	public String getSvcCategoryName() {
		return svcCategoryName;
	}
	public void setSvcCategoryName(String svcCategoryName) {
		this.svcCategoryName = svcCategoryName;
	}
	public String getSvcCategoryParentCode() {
		return svcCategoryParentCode;
	}
	public void setSvcCategoryParentCode(String svcCategoryParentCode) {
		this.svcCategoryParentCode = svcCategoryParentCode;
	}
	public String getSvcCategoryCode() {
		return svcCategoryCode;
	}
	public void setSvcCategoryCode(String svcCategoryCode) {
		this.svcCategoryCode = svcCategoryCode;
	}
	
}
