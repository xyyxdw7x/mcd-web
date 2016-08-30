package com.asiainfo.biapp.mcd.bo;

import java.io.Serializable;

/**
 * FileName:	MetaDataElement.java
 * Description:	
 * Copyright:	Copyright (c)2010
 * @author wanglei
 * @date 2010-12-27
 */

public class MetaDataElement implements Serializable {

	private static final long serialVersionUID = 3272162753875658498L;

	private String attrId;
	private String attrMetaId;
	private String attrAlias;
	private String pattrId;
	private String sattrId;
	private Integer sortNum;
	//添加其他属性：控件类型名称，属性分类ID，所属视图ID
	private String dimCtrlTypeName;
	private String dimCtrlTypeId;
	private String dimClassId;
	private String dimClassName;
	private String cviewId;
	private Integer classDimTypeId;
	private String mdaColumnId;
	private String mdaColumnName;
	private String mdaColumnCnName;
	private Integer mdaColumnDataType;
	private Long mdaColumnLength;
	private String mdaColumnAnalysisType;
	private Long mdaColumnIsPK;
	private String mdaColumnDesc;
	private String mdaColumnDimRansId;
	private String mdaTableName;
	private String mdaTableCnName;
	private String mdaTableDatePostfix;
	private String mdaColumnCalculateRule;
	private String mdaTableDblink;
	private String mdaTableId;
	private String transExpr;
	private String pattrMetaId;
	private Long mdaColumnTransLength;
	private int cycleTypeId;
	private String activeTempletId;
	
	private String elementValueId; //用户选择的id
	private String elementValue;  //用户手动输入的数值

	public String getActiveTempletId() {
		return activeTempletId;
	}

	public void setActiveTempletId(String activeTempletId) {
		this.activeTempletId = activeTempletId;
	}

	public int getCycleTypeId() {
		return cycleTypeId;
	}

	public void setCycleTypeId(int cycleTypeId) {
		this.cycleTypeId = cycleTypeId;
	}
	public String getDimClassName() {
		return dimClassName;
	}

	public void setDimClassName(String dimClassName) {
		this.dimClassName = dimClassName;
	}

	public Long getMdaColumnTransLength() {
		return mdaColumnTransLength;
	}

	public void setMdaColumnTransLength(Long mdaColumnTransLength) {
		this.mdaColumnTransLength = mdaColumnTransLength;
	}

	public String getTransExpr() {
		return transExpr;
	}

	public void setTransExpr(String transExpr) {
		this.transExpr = transExpr;
	}

	public String getPattrMetaId() {
		return pattrMetaId;
	}

	public void setPattrMetaId(String pattrMetaId) {
		this.pattrMetaId = pattrMetaId;
	}

	public String getMdaTableId() {
		return mdaTableId;
	}

	public void setMdaTableId(String mdaTableId) {
		this.mdaTableId = mdaTableId;
	}

	/**
	 * @return the mdaTableDblink
	 */
	public String getMdaTableDblink() {
		return mdaTableDblink;
	}

	/**
	 * @param mdaTableDblink the mdaTableDblink to set
	 */
	public void setMdaTableDblink(String mdaTableDblink) {
		this.mdaTableDblink = mdaTableDblink;
	}

	public Integer getClassDimTypeId() {
		return classDimTypeId;
	}

	public void setClassDimTypeId(Integer classDimTypeId) {
		this.classDimTypeId = classDimTypeId;
	}

	public String getDimCtrlTypeName() {
		return dimCtrlTypeName;
	}

	public void setDimCtrlTypeName(String dimCtrlTypeName) {
		this.dimCtrlTypeName = dimCtrlTypeName;
	}

	public String getDimClassId() {
		return dimClassId;
	}

	public void setDimClassId(String dimClassId) {
		this.dimClassId = dimClassId;
	}

	public String getCviewId() {
		return cviewId;
	}

	public void setCviewId(String cviewId) {
		this.cviewId = cviewId;
	}

	public String getMdaColumnId() {
		return mdaColumnId;
	}

	public void setMdaColumnId(String mdaColumnId) {
		this.mdaColumnId = mdaColumnId;
	}

	public String getMdaColumnName() {
		return mdaColumnName;
	}

	public void setMdaColumnName(String mdaColumnName) {
		this.mdaColumnName = mdaColumnName;
	}

	public String getMdaColumnCnName() {
		return mdaColumnCnName;
	}

	public void setMdaColumnCnName(String mdaColumnCnName) {
		this.mdaColumnCnName = mdaColumnCnName;
	}

	public Integer getMdaColumnDataType() {
		return mdaColumnDataType;
	}

	public void setMdaColumnDataType(Integer mdaColumnDataType) {
		this.mdaColumnDataType = mdaColumnDataType;
	}

	public Long getMdaColumnLength() {
		return mdaColumnLength;
	}

	public void setMdaColumnLength(Long mdaColumnLength) {
		this.mdaColumnLength = mdaColumnLength;
	}

	public String getMdaColumnAnalysisType() {
		return mdaColumnAnalysisType;
	}

	public void setMdaColumnAnalysisType(String mdaColumnAnalysisType) {
		this.mdaColumnAnalysisType = mdaColumnAnalysisType;
	}

	public Long getMdaColumnIsPK() {
		return mdaColumnIsPK;
	}

	public void setMdaColumnIsPK(Long mdaColumnIsPK) {
		this.mdaColumnIsPK = mdaColumnIsPK;
	}

	public String getMdaColumnDesc() {
		return mdaColumnDesc;
	}

	public void setMdaColumnDesc(String mdaColumnDesc) {
		this.mdaColumnDesc = mdaColumnDesc;
	}

	public String getMdaColumnDimRansId() {
		return mdaColumnDimRansId;
	}

	public void setMdaColumnDimRansId(String mdaColumnDimRansId) {
		this.mdaColumnDimRansId = mdaColumnDimRansId;
	}

	public String getMdaTableName() {
		return mdaTableName;
	}

	public void setMdaTableName(String mdaTableName) {
		this.mdaTableName = mdaTableName;
	}

	public String getMdaTableCnName() {
		return mdaTableCnName;
	}

	public void setMdaTableCnName(String mdaTableCnName) {
		this.mdaTableCnName = mdaTableCnName;
	}

	public String getMdaTableDatePostfix() {
		return mdaTableDatePostfix;
	}

	public void setMdaTableDatePostfix(String mdaTableDatePostfix) {
		this.mdaTableDatePostfix = mdaTableDatePostfix;
	}

	public String getMdaColumnCalculateRule() {
		return mdaColumnCalculateRule;
	}

	public void setMdaColumnCalculateRule(String mdaColumnCalculateRule) {
		this.mdaColumnCalculateRule = mdaColumnCalculateRule;
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

	public String getDimCtrlTypeId() {
		return dimCtrlTypeId;
	}

	public void setDimCtrlTypeId(String dimCtrlTypeId) {
		this.dimCtrlTypeId = dimCtrlTypeId;
	}

	public String getElementValueId() {
		return elementValueId;
	}

	public void setElementValueId(String elementValueId) {
		this.elementValueId = elementValueId;
	}

	public String getElementValue() {
		return elementValue;
	}

	public void setElementValue(String elementValue) {
		this.elementValue = elementValue;
	}
	
}
