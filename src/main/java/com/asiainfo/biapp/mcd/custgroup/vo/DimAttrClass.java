package com.asiainfo.biapp.mcd.custgroup.vo;

import java.io.Serializable;
import java.util.Set;

public class DimAttrClass implements Serializable {

	/**
	 * Fields
	 */
	private static final long serialVersionUID = -482052147250625708L;
	private String attrClassId;
	private String attrClassName;
	private String parentAttrClassId;
	private String descTxt;
	private int sortNum;
	private Integer isDraggable;
	private McdCvDefine mcdCvDefine;
	private Set<McdCvColDefine> mcdCvColDefines;

	public String getAttrClassId() {
		return attrClassId;
	}

	public void setAttrClassId(String attrClassId) {
		this.attrClassId = attrClassId;
	}

	public String getAttrClassName() {
		return attrClassName;
	}

	public void setAttrClassName(String attrClassName) {
		this.attrClassName = attrClassName;
	}

	public String getParentAttrClassId() {
		return parentAttrClassId;
	}

	public void setParentAttrClassId(String parentAttrClassId) {
		this.parentAttrClassId = parentAttrClassId;
	}

	public String getDescTxt() {
		return descTxt;
	}

	public void setDescTxt(String descTxt) {
		this.descTxt = descTxt;
	}

	public int getSortNum() {
		return sortNum;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}

	public Integer getIsDraggable() {
		return isDraggable;
	}

	public void setIsDraggable(Integer isDraggable) {
		this.isDraggable = isDraggable;
	}

	public McdCvDefine getMcdCvDefine() {
		return mcdCvDefine;
	}

	public void setMcdCvDefine(McdCvDefine mcdCvDefine) {
		this.mcdCvDefine = mcdCvDefine;
	}

	public Set<McdCvColDefine> getMcdCvColDefines() {
		return mcdCvColDefines;
	}

	public void setMcdCvColDefines(Set<McdCvColDefine> mcdCvColDefines) {
		this.mcdCvColDefines = mcdCvColDefines;
	}

}
