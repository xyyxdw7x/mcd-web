package com.asiainfo.biapp.mcd.model;

/**
 * 产品信息字典表
 * @author FUYU
 *
 */
public class SiProductDic {

	//字典标识
	private String dicId;
	//编码类型
	private Short codeType;
	//编码名称
	private String codeName;
	//编码值
	private String codeVal;
	//参数顺序
	private Integer sortNum;
	
	public String getDicId() {
		return dicId;
	}
	public void setDicId(String dicId) {
		this.dicId = dicId;
	}
	public Short getCodeType() {
		return codeType;
	}
	public void setCodeType(Short codeType) {
		this.codeType = codeType;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getCodeVal() {
		return codeVal;
	}
	public void setCodeVal(String codeVal) {
		this.codeVal = codeVal;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	
	
}
