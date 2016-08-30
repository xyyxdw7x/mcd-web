package com.asiainfo.biapp.mcd.model;

import java.util.Set;

/**
 * MdaSysTable entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class MdaSysTable implements java.io.Serializable {

	// Fields

	/**  */
	private static final long serialVersionUID = 1L;

	private String tableId;

	private String tableName;

	private String tableCnName;

	private String tableDatePostfix;

	private Set<MdaSysTableColumn> mdaSysTableColumn;

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableCnName() {
		return tableCnName;
	}

	public void setTableCnName(String tableCnName) {
		this.tableCnName = tableCnName;
	}

	public String getTableDatePostfix() {
		return tableDatePostfix;
	}

	public void setTableDatePostfix(String tableDatePostfix) {
		this.tableDatePostfix = tableDatePostfix;
	}

	public Set<MdaSysTableColumn> getMdaSysTableColumn() {
		return mdaSysTableColumn;
	}

	public void setMdaSysTableColumn(Set<MdaSysTableColumn> mdaSysTableColumn) {
		this.mdaSysTableColumn = mdaSysTableColumn;
	}

}