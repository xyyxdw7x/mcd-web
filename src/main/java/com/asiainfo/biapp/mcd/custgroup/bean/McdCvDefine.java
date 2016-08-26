package com.asiainfo.biapp.mcd.custgroup.bean;

import java.util.Date;
import java.util.Set;

public class McdCvDefine implements java.io.Serializable {

	/**
	 * Fields
	 */
	private static final long serialVersionUID = 4720625657010637953L;
	private String cviewId;
	private String cviewName;
	private String creator;
	private Date createTime;
	private Date lastModifyTime;
	private String descTxt;
	private String mainTableName;
	private String relaPrimaryKey;

	private Set<DimAttrClass> dimAttrClasses;
	private Set<McdCvColDefine> mcdCvColDefines;

	public String getCviewId() {
		return cviewId;
	}

	public void setCviewId(String cviewId) {
		this.cviewId = cviewId;
	}

	public String getCviewName() {
		return cviewName;
	}

	public void setCviewName(String cviewName) {
		this.cviewName = cviewName;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getDescTxt() {
		return descTxt;
	}

	public void setDescTxt(String descTxt) {
		this.descTxt = descTxt;
	}

	public String getMainTableName() {
		return mainTableName;
	}

	public void setMainTableName(String mainTableName) {
		this.mainTableName = mainTableName;
	}

	public String getRelaPrimaryKey() {
		return relaPrimaryKey;
	}

	public void setRelaPrimaryKey(String relaPrimaryKey) {
		this.relaPrimaryKey = relaPrimaryKey;
	}

	public Set<DimAttrClass> getDimAttrClasses() {
		return dimAttrClasses;
	}

	public void setDimAttrClasses(Set<DimAttrClass> dimAttrClasses) {
		this.dimAttrClasses = dimAttrClasses;
	}

	public Set<McdCvColDefine> getMcdCvColDefines() {
		return mcdCvColDefines;
	}

	public void setMcdCvColDefines(Set<McdCvColDefine> mcdCvColDefines) {
		this.mcdCvColDefines = mcdCvColDefines;
	}

}
