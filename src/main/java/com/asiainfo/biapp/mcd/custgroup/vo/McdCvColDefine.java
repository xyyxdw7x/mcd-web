package com.asiainfo.biapp.mcd.custgroup.vo;

import java.io.Serializable;
import java.util.List;

public class McdCvColDefine implements Serializable {

	/**
	 * Fields
	 */
	private static final long serialVersionUID = -6928964011170149822L;
	private String attrId;
	private String attrMetaId;
	private String attrAlias;
	private String pattrId;
	private String sattrId;
	private Integer sortNum;

	private String attrDesc;
	private String transExpr;

	private McdCvDefine mcdCvDefine;
	private DimAttrClass dimAttrClass;
	private DimCtrlType dimCtrlType;
	private Integer columnDataType;
	private MdaSysTableColumn mdaSysTableColumn;
	
	private String AttrClassId;
	private String cViewId;
	private String ctrlTypeId;
	private String attrClassName;
	
	private String dimTransId; //维表id
	private int lableId;
	private String busiCuliber;
	private int updateCycle;
	private String dataDate;
	private String custGroupUpdateCycle;
	private String valueUnit; //单位：元、角、分等

//	IMCD_ZJ为了前台统一使用模板   别名
	private String typeId;
	private String typeName;
	private List result;
	
	public String getValueUnit() {
		return valueUnit;
	}

	public void setValueUnit(String valueUnit) {
		this.valueUnit = valueUnit;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getLableId() {
		return lableId;
	}

	public void setLableId(int lableId) {
		this.lableId = lableId;
	}

	public String getBusiCuliber() {
		return busiCuliber;
	}

	public void setBusiCuliber(String busiCuliber) {
		this.busiCuliber = busiCuliber;
	}

	public int getUpdateCycle() {
		return updateCycle;
	}

	public void setUpdateCycle(int updateCycle) {
		this.updateCycle = updateCycle;
	}

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public String getDimTransId() {
		return dimTransId;
	}

	public void setDimTransId(String dimTransId) {
		this.dimTransId = dimTransId;
	}

	public String getAttrClassName() {
		return attrClassName;
	}

	public void setAttrClassName(String attrClassName) {
		this.attrClassName = attrClassName;
	}

	public String getAttrId() {
		return attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}

	public String getAttrMetaId() {
		return attrMetaId;
	}

	public void setAttrMetaId(String attrMetaId) {
		this.attrMetaId = attrMetaId;
	}

	public String getAttrAlias() {
		return attrAlias;
	}

	public void setAttrAlias(String attrAlias) {
		this.attrAlias = attrAlias;
	}

	public String getPattrId() {
		return pattrId;
	}

	public void setPattrId(String pattrId) {
		this.pattrId = pattrId;
	}

	public String getSattrId() {
		return sattrId;
	}

	public void setSattrId(String sattrId) {
		this.sattrId = sattrId;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getAttrDesc() {
		return attrDesc;
	}

	public void setAttrDesc(String attrDesc) {
		this.attrDesc = attrDesc;
	}

	public String getTransExpr() {
		return transExpr;
	}

	public void setTransExpr(String transExpr) {
		this.transExpr = transExpr;
	}

	public McdCvDefine getMcdCvDefine() {
		return mcdCvDefine;
	}

	public void setMcdCvDefine(McdCvDefine mcdCvDefine) {
		this.mcdCvDefine = mcdCvDefine;
	}

	public DimAttrClass getDimAttrClass() {
		return dimAttrClass;
	}

	public void setDimAttrClass(DimAttrClass dimAttrClass) {
		this.dimAttrClass = dimAttrClass;
	}

	public DimCtrlType getDimCtrlType() {
		return dimCtrlType;
	}

	public void setDimCtrlType(DimCtrlType dimCtrlType) {
		this.dimCtrlType = dimCtrlType;
	}

	public Integer getColumnDataType() {
		return columnDataType;
	}

	public void setColumnDataType(Integer columnDataType) {
		this.columnDataType = columnDataType;
	}

	public MdaSysTableColumn getMdaSysTableColumn() {
		return mdaSysTableColumn;
	}

	public void setMdaSysTableColumn(MdaSysTableColumn mdaSysTableColumn) {
		this.mdaSysTableColumn = mdaSysTableColumn;
	}

	public String getAttrClassId() {
		return AttrClassId;
	}

	public void setAttrClassId(String attrClassId) {
		AttrClassId = attrClassId;
	}

	public String getcViewId() {
		return cViewId;
	}

	public void setcViewId(String cViewId) {
		this.cViewId = cViewId;
	}

	public String getCtrlTypeId() {
		return ctrlTypeId;
	}

	public void setCtrlTypeId(String ctrlTypeId) {
		this.ctrlTypeId = ctrlTypeId;
	}

	public String getCustGroupUpdateCycle() {
		return custGroupUpdateCycle;
	}

	public void setCustGroupUpdateCycle(String custGroupUpdateCycle) {
		this.custGroupUpdateCycle = custGroupUpdateCycle;
	}

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}
	
	
}
