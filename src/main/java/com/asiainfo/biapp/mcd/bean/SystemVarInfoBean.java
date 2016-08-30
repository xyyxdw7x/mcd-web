package com.asiainfo.biapp.mcd.bean;
/**
 * 时机和事件所需要字段 缓存Bean
 * @author liyz
 *
 */
public class SystemVarInfoBean {
   
	private String varId;
	
	private String madId;
	
	private String columnName;
	
	private String relTable;
	
	private String relTableKey;
	
	private String relTableValue;
	
	private String varSource;

	public String getVarId() {
		return varId;
	}

	public void setVarId(String varId) {
		this.varId = varId;
	}

	public String getMadId() {
		return madId;
	}

	public void setMadId(String madId) {
		this.madId = madId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getRelTable() {
		return relTable;
	}

	public void setRelTable(String relTable) {
		this.relTable = relTable;
	}

	public String getRelTableKey() {
		return relTableKey;
	}

	public void setRelTableKey(String relTableKey) {
		this.relTableKey = relTableKey;
	}

	public String getRelTableValue() {
		return relTableValue;
	}

	public void setRelTableValue(String relTableValue) {
		this.relTableValue = relTableValue;
	}

	public String getVarSource() {
		return varSource;
	}

	public void setVarSource(String varSource) {
		this.varSource = varSource;
	}

	@Override
	public String toString() {
		return "madId:"+madId+",columnName:"+columnName+",relTable:"+relTable+",relTableKey:"+relTableKey+",relTableValue:"+relTableValue+",varSource:"+varSource;
	}
	
	
}
