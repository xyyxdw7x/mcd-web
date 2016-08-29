package com.asiainfo.biapp.mcd.model;

import java.io.Serializable;
import java.util.Set;

public class DimCtrlType implements Serializable {

	/**
	 * Fields
	 */
	private static final long serialVersionUID = -6833283494485895002L;
	private String ctrlTypeId;
	private String ctrlTypeName;
	private String ctrlTypeEname;
	private String descTxt;
	private Set<McdCvColDefine> mcdCvColDefines;// = new HashSet<CiTargetCvColDefine>(0);

	public String getCtrlTypeId() {
		return ctrlTypeId;
	}

	public void setCtrlTypeId(String ctrlTypeId) {
		this.ctrlTypeId = ctrlTypeId;
	}

	public String getCtrlTypeName() {
		return ctrlTypeName;
	}

	public void setCtrlTypeName(String ctrlTypeName) {
		this.ctrlTypeName = ctrlTypeName;
	}

	public String getCtrlTypeEname() {
		return ctrlTypeEname;
	}

	public void setCtrlTypeEname(String ctrlTypeEname) {
		this.ctrlTypeEname = ctrlTypeEname;
	}

	public String getDescTxt() {
		return descTxt;
	}

	public void setDescTxt(String descTxt) {
		this.descTxt = descTxt;
	}

	public Set<McdCvColDefine> getMcdCvColDefines() {
		return mcdCvColDefines;
	}

	public void setMcdCvColDefines(Set<McdCvColDefine> mcdCvColDefines) {
		this.mcdCvColDefines = mcdCvColDefines;
	}

}
